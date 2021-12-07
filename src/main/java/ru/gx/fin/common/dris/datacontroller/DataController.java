package ru.gx.fin.common.dris.datacontroller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.SessionFactory;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.data.redis.core.RedisTemplate;
import ru.gx.core.channels.ChannelConfigurationException;
import ru.gx.core.data.ActiveSessionsContainer;
import ru.gx.core.data.DataObject;
import ru.gx.core.data.DataPackage;
import ru.gx.core.data.edlinking.EntitiesDtoLinksConfigurationException;
import ru.gx.core.data.edlinking.EntitiesDtosLinksConfiguration;
import ru.gx.core.data.edlinking.EntityDtoLinkDescriptor;
import ru.gx.core.data.entity.EntitiesPackage;
import ru.gx.core.data.entity.EntityObject;
import ru.gx.core.redis.upload.RedisOutcomeCollectionLoadingDescriptor;
import ru.gx.core.redis.upload.RedisOutcomeCollectionsUploader;
import ru.gx.core.redis.upload.SimpleRedisOutcomeCollectionsConfiguration;
import ru.gx.core.simpleworker.SimpleWorker;
import ru.gx.core.simpleworker.SimpleWorkerOnIterationExecuteEvent;
import ru.gx.core.simpleworker.SimpleWorkerOnStartingExecuteEvent;
import ru.gx.core.simpleworker.SimpleWorkerOnStoppingExecuteEvent;
import ru.gx.fin.common.dris.out.InstrumentType;
import ru.gx.fin.common.dris.out.Place;
import ru.gx.fin.common.dris.out.Provider;
import ru.gx.fin.common.dris.out.ProviderType;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.security.InvalidParameterException;

import static lombok.AccessLevel.PROTECTED;

@Slf4j
public class DataController {
    // -----------------------------------------------------------------------------------------------------------------
    // <editor-fold desc="Fields">
    @Getter(PROTECTED)
    @Setter(value = PROTECTED, onMethod_ = @Autowired)
    private ObjectMapper objectMapper;

    @Getter(PROTECTED)
    @Setter(value = PROTECTED, onMethod_ = @Autowired)
    private DataSource dataSource;

    @Getter(PROTECTED)
    @Setter(value = PROTECTED, onMethod_ = @Autowired)
    private SimpleWorker simpleWorker;

    @Getter(PROTECTED)
    @Setter(value = PROTECTED, onMethod_ = @Autowired)
    private DataControllerSettingsContainer settings;

    @Getter(PROTECTED)
    @Setter(value = PROTECTED, onMethod_ = @Autowired)
    private EntitiesDtosLinksConfiguration entitiesDtosLinksConfiguration;

    @Getter(PROTECTED)
    @Setter(value = PROTECTED, onMethod_ = @Autowired)
    private EntityManagerFactory entityManagerFactory;

    @Getter(PROTECTED)
    @Setter(value = PROTECTED, onMethod_ = @Autowired)
    private ActiveSessionsContainer activeSessionsContainer;

    private SessionFactory sessionFactory;

    @Getter(PROTECTED)
    @Setter(value = PROTECTED, onMethod_ = @Autowired)
    private RedisOutcomeCollectionsUploader redisUploader;

    @Getter(PROTECTED)
    @Setter(value = PROTECTED, onMethod_ = @Autowired)
    private SimpleRedisOutcomeCollectionsConfiguration redisOutcomeTopicsConfiguration;

    // </editor-fold>
    // -----------------------------------------------------------------------------------------------------------------
    // <editor-fold desc="Обработка событий Worker-а">

    /**
     * Обработка события о начале работы цикла итераций.
     *
     * @param event Объект-событие с параметрами.
     */
    @SuppressWarnings("unused")
    @EventListener(SimpleWorkerOnStartingExecuteEvent.class)
    public void startingExecute(SimpleWorkerOnStartingExecuteEvent event) throws Exception {
        log.debug("Starting startingExecute()");

        if (this.sessionFactory == null) {
            if (entityManagerFactory.unwrap(SessionFactory.class) == null) {
                throw new NullPointerException("entityManagerFactory is not a hibernate factory!");
            }
            this.sessionFactory = entityManagerFactory.unwrap(SessionFactory.class);
        }

        publishAllOnStart();

        log.debug("Finished startingExecute()");

    }

    /**
     * Обработка события об окончании работы цикла итераций.
     *
     * @param event Объект-событие с параметрами.
     */
    @SuppressWarnings("unused")
    @EventListener(SimpleWorkerOnStoppingExecuteEvent.class)
    public void stoppingExecute(SimpleWorkerOnStoppingExecuteEvent event) {
        log.debug("Starting stoppingExecute()");
        log.debug("Finished stoppingExecute()");
    }

    /**
     * Обработчик итераций.
     *
     * @param event Объект-событие с параметрами итерации.
     */
    @EventListener(SimpleWorkerOnIterationExecuteEvent.class)
    public void iterationExecute(SimpleWorkerOnIterationExecuteEvent event) {
        log.debug("Starting iterationExecute()");
        try {
            this.simpleWorker.runnerIsLifeSet();
            event.setImmediateRunNextIteration(false);

            final var session = this.sessionFactory.openSession();
            try (session) {
                this.activeSessionsContainer.putCurrent(session);
                final var tran = session.beginTransaction();

                try {


                    tran.commit();
                } catch (Exception e) {
                    tran.rollback();
                    internalTreatmentExceptionOnDataRead(event, e);
                }
            } finally {
                this.activeSessionsContainer.putCurrent(null);
            }

        } catch (Exception e) {
            internalTreatmentExceptionOnDataRead(event, e);
        } finally {
            log.debug("Finished iterationExecute()");
        }
    }

    /**
     * Обработка ошибки при выполнении итерации.
     *
     * @param event Объект-событие с параметрами итерации.
     * @param e     Ошибка, которую требуется обработать.
     */
    private void internalTreatmentExceptionOnDataRead(SimpleWorkerOnIterationExecuteEvent event, Exception e) {
        log.error("", e);
        if (e instanceof InterruptedException) {
            log.info("event.setStopExecution(true)");
            event.setStopExecution(true);
        } else {
            log.info("event.setNeedRestart(true)");
            event.setNeedRestart(true);
        }
    }

    // </editor-fold>
    // -----------------------------------------------------------------------------------------------------------------
    // <editor-fold desc="Вспомогательные сервисы">
    // TODO: Убрать нахер (заменить конфигурацией)
    @NotNull
    public String getTopicNameByDtoClass(@NotNull final Class<? extends DataObject> dtoClass) {
        if (dtoClass == Place.class) {
            return settings.getOutcomeCollectionPlaces();
        } else if (dtoClass == ProviderType.class) {
            return settings.getOutcomeCollectionProviderTypes();
        } else if (dtoClass == Provider.class) {
            return settings.getOutcomeCollectionProviders();
        } else if (dtoClass == InstrumentType.class) {
            return settings.getOutcomeCollectionInstrumentTypes();
        } else {
            throw new InvalidParameterException("Unknown dto class " + dtoClass.getSimpleName());
        }
    }

    // TODO: Убрать нахер (заменить конфигурацией)
    @SuppressWarnings("unchecked")
    @NotNull
    public <E extends EntityObject, EP extends EntitiesPackage<E>, ID, O extends DataObject, P extends DataPackage<O>>
    EntityDtoLinkDescriptor<E, EP, ID, O, P> getDescriptorByTopicName(@NotNull final String topicName) throws EntitiesDtoLinksConfigurationException {
        if (settings.getOutcomeCollectionPlaces().equals(topicName)) {
            return (EntityDtoLinkDescriptor<E, EP, ID, O, P>) this.entitiesDtosLinksConfiguration.getByDtoClass(Place.class);
        } else if (settings.getOutcomeCollectionProviderTypes().equals(topicName)) {
            return (EntityDtoLinkDescriptor<E, EP, ID, O, P>) this.entitiesDtosLinksConfiguration.getByDtoClass(ProviderType.class);
        } else if (settings.getOutcomeCollectionProviders().equals(topicName)) {
            return (EntityDtoLinkDescriptor<E, EP, ID, O, P>) this.entitiesDtosLinksConfiguration.getByDtoClass(Provider.class);
        } else if (settings.getOutcomeCollectionInstrumentTypes().equals(topicName)) {
            return (EntityDtoLinkDescriptor<E, EP, ID, O, P>) this.entitiesDtosLinksConfiguration.getByDtoClass(InstrumentType.class);
        } else {
            throw new InvalidParameterException("Unknown topicName " + topicName);
        }
    }

    protected void publishAllOnStart() throws Exception {
        String topic = settings.getOutcomeCollectionPlaces();
        publishSnapshot(
                topic,
                this.getDescriptorByTopicName(topic)
        );

        topic = settings.getOutcomeCollectionProviderTypes();
        publishSnapshot(
                topic,
                this.getDescriptorByTopicName(topic)
        );

        topic = settings.getOutcomeCollectionProviders();
        publishSnapshot(
                topic,
                this.getDescriptorByTopicName(topic)
        );

        topic = settings.getOutcomeCollectionInstrumentTypes();
        publishSnapshot(
                topic,
                this.getDescriptorByTopicName(topic)
        );
    }

    @SuppressWarnings("unchecked")
    @NotNull
    private <O extends DataObject, P extends DataPackage<O>>
    RedisOutcomeCollectionLoadingDescriptor<O, P> getRedisOutcomeDescriptorByCollectionName(@NotNull final String collectionName) {
        for (final var descriptor : this.redisOutcomeTopicsConfiguration.getAll()) {
            if (collectionName.equals(descriptor.getName())) {
                return (RedisOutcomeCollectionLoadingDescriptor<O, P>)descriptor;
            }
        }
        throw new ChannelConfigurationException("Unknown collection name: " + collectionName);
    }

    public <E extends EntityObject, EP extends EntitiesPackage<E>, ID, O extends DataObject, P extends DataPackage<O>>
    void publishSnapshot(@NotNull final String topicName, @NotNull final EntityDtoLinkDescriptor<E, EP, ID, O, P> linkDescriptor) throws Exception {
        final var repository = linkDescriptor.getRepository();
        if (repository == null) {
            throw new EntitiesDtoLinksConfigurationException("Can't get CrudRepository by dtoClass " + linkDescriptor.getDtoClass().getName());
        }

        final var memoryRepository = linkDescriptor.getMemoryRepository();
        if (memoryRepository == null) {
            throw new EntitiesDtoLinksConfigurationException("Can't get MemoryRepository by dtoClass " + linkDescriptor.getDtoClass().getName());
        }

        final var converter = linkDescriptor.getDtoFromEntityConverter();
        if (converter == null) {
            throw new EntitiesDtoLinksConfigurationException("Can't get Converter by dtoClass " + linkDescriptor.getDtoClass().getName());
        }

        // Загружаем данные из БД:
        final var entityObjects = repository.findAll();

        // Преобразуем в DTO
        final var dataPackage = linkDescriptor.createDtoPackage();
        final var dataObjects = dataPackage.getObjects();
        converter.fillDtoCollectionFromSource(dataObjects, entityObjects);

        // TODO: !!!!
        memoryRepository.putAll(dataObjects);

        // Выгружаем данные
        dataObjects.forEach(System.out::println);

        final var redisDescriptor = this.<O, P>getRedisOutcomeDescriptorByCollectionName(topicName);

        this.redisUploader.uploadDataObjects(redisDescriptor, dataObjects, memoryRepository);
    }
    // </editor-fold>
    // -----------------------------------------------------------------------------------------------------------------
}
