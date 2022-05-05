package ru.gx.fin.common.dris.config;

import lombok.Getter;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import ru.gx.core.redis.upload.AbstractRedisOutcomeCollectionsConfiguration;
import ru.gx.core.redis.upload.RedisOutcomeCollectionUploadingDescriptor;
import ru.gx.fin.common.dris.channels.DrisSnapshotInstrumentTypeDataPublishChannelApiV1;
import ru.gx.fin.common.dris.channels.DrisSnapshotPlaceDataPublishChannelApiV1;
import ru.gx.fin.common.dris.channels.DrisSnapshotProviderDataPublishChannelApiV1;
import ru.gx.fin.common.dris.channels.DrisSnapshotProviderTypeDataPublishChannelApiV1;

import javax.annotation.PostConstruct;

import static lombok.AccessLevel.PROTECTED;

@Configuration
public class RedisOutcomeCollectionsConfiguration extends AbstractRedisOutcomeCollectionsConfiguration {

    @Getter(PROTECTED)
    @NotNull
    private final DrisSnapshotPlaceDataPublishChannelApiV1 placeChannelApiV1;

    @Getter(PROTECTED)
    @NotNull
    private final DrisSnapshotProviderTypeDataPublishChannelApiV1 providerTypeChannelApiV1;

    @Getter(PROTECTED)
    @NotNull
    private final DrisSnapshotProviderDataPublishChannelApiV1 providerChannelApiV1;

    @Getter(PROTECTED)
    @NotNull
    private final DrisSnapshotInstrumentTypeDataPublishChannelApiV1 instrumentTypeChannelApiV1;

    public RedisOutcomeCollectionsConfiguration(
            @NotNull final RedisConnectionFactory connectionFactory,
            @NotNull final DrisSnapshotPlaceDataPublishChannelApiV1 placeChannelApiV1,
            @NotNull final DrisSnapshotProviderTypeDataPublishChannelApiV1 providerTypeChannelApiV1,
            @NotNull final DrisSnapshotProviderDataPublishChannelApiV1 providerChannelApiV1,
            @NotNull final DrisSnapshotInstrumentTypeDataPublishChannelApiV1 instrumentTypeChannelApiV1
    ) {
        super("redis-outcome-config", connectionFactory);
        this.placeChannelApiV1 = placeChannelApiV1;
        this.providerTypeChannelApiV1 = providerTypeChannelApiV1;
        this.providerChannelApiV1 = providerChannelApiV1;
        this.instrumentTypeChannelApiV1 = instrumentTypeChannelApiV1;
    }

    @SuppressWarnings("unchecked")
    @PostConstruct
    public void init() {
        // this.getDescriptorsDefaults();

        this
                .newDescriptor(this.placeChannelApiV1, RedisOutcomeCollectionUploadingDescriptor.class)
                .setPriority(0)
                .init();

        this
                .newDescriptor(this.providerTypeChannelApiV1, RedisOutcomeCollectionUploadingDescriptor.class)
                .setPriority(1)
                .init();

        this
                .newDescriptor(this.providerChannelApiV1, RedisOutcomeCollectionUploadingDescriptor.class)
                .setPriority(2)
                .init();

        this
                .newDescriptor(this.instrumentTypeChannelApiV1, RedisOutcomeCollectionUploadingDescriptor.class)
                .setPriority(3)
                .init();
    }
}
