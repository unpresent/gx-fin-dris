package ru.gx.fin.common.dris.config;

import lombok.Setter;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import ru.gx.core.data.edlinking.AbstractEntitiesUploadingConfiguration;
import ru.gx.core.data.edlinking.EntitiesDtoLinksConfigurationException;
import ru.gx.fin.common.dris.channels.DrisSnapshotInstrumentTypeDataPublishChannelApiV1;
import ru.gx.fin.common.dris.channels.DrisSnapshotPlaceDataPublishChannelApiV1;
import ru.gx.fin.common.dris.channels.DrisSnapshotProviderDataPublishChannelApiV1;
import ru.gx.fin.common.dris.channels.DrisSnapshotProviderTypeDataPublishChannelApiV1;
import ru.gx.fin.common.dris.converters.InstrumentTypeDtoFromEntityConverter;
import ru.gx.fin.common.dris.converters.PlaceDtoFromEntityConverter;
import ru.gx.fin.common.dris.converters.ProviderDtoFromEntityConverter;
import ru.gx.fin.common.dris.converters.ProviderTypeDtoFromEntityConverter;
import ru.gx.fin.common.dris.entities.InstrumentTypeEntity;
import ru.gx.fin.common.dris.entities.PlaceEntity;
import ru.gx.fin.common.dris.entities.ProviderEntity;
import ru.gx.fin.common.dris.entities.ProviderTypeEntity;
import ru.gx.fin.common.dris.keyextractors.InstrumentTypeKeyExtractor;
import ru.gx.fin.common.dris.keyextractors.PlaceKeyExtractor;
import ru.gx.fin.common.dris.keyextractors.ProviderKeyExtractor;
import ru.gx.fin.common.dris.keyextractors.ProviderTypeKeyExtractor;
import ru.gx.fin.common.dris.out.*;
import ru.gx.fin.common.dris.repository.InstrumentTypesRepository;
import ru.gx.fin.common.dris.repository.PlacesRepository;
import ru.gx.fin.common.dris.repository.ProviderTypesRepository;
import ru.gx.fin.common.dris.repository.ProvidersRepository;

import javax.annotation.PostConstruct;

import static lombok.AccessLevel.PROTECTED;

public class DrisEntitiesUploadingConfiguration extends AbstractEntitiesUploadingConfiguration {
    // -----------------------------------------------------------------------------------------------------------------
    // <editor-fold desc="Fields">
    @Setter(value = PROTECTED, onMethod_ = @Autowired)
    private DrisSnapshotPlaceDataPublishChannelApiV1 placeDataPublishChannelApiV1;

    @Setter(value = PROTECTED, onMethod_ = @Autowired)
    private DrisSnapshotProviderTypeDataPublishChannelApiV1 providerTypeDataPublishChannelApiV1;

    @Setter(value = PROTECTED, onMethod_ = @Autowired)
    private DrisSnapshotProviderDataPublishChannelApiV1 providerDataPublishChannelApiV1;

    @Setter(value = PROTECTED, onMethod_ = @Autowired)
    private DrisSnapshotInstrumentTypeDataPublishChannelApiV1 instrumentTypeDataPublishChannelApiV1;

    @Setter(value = PROTECTED, onMethod_ = @Autowired)
    private PlaceKeyExtractor placeKeyExtractor;

    @Setter(value = PROTECTED, onMethod_ = @Autowired)
    private ProviderTypeKeyExtractor ProviderTypeKeyExtractor;

    @Setter(value = PROTECTED, onMethod_ = @Autowired)
    private ProviderKeyExtractor providerKeyExtractor;

    @Setter(value = PROTECTED, onMethod_ = @Autowired)
    private InstrumentTypeKeyExtractor instrumentTypeKeyExtractor;

    @Setter(value = PROTECTED, onMethod_ = @Autowired)
    private PlaceDtoFromEntityConverter placeDtoFromEntityConverter;

    @Setter(value = PROTECTED, onMethod_ = @Autowired)
    private ProviderTypeDtoFromEntityConverter providerTypeDtoFromEntityConverter;

    @Setter(value = PROTECTED, onMethod_ = @Autowired)
    private ProviderDtoFromEntityConverter providerDtoFromEntityConverter;

    @Setter(value = PROTECTED, onMethod_ = @Autowired)
    private InstrumentTypeDtoFromEntityConverter instrumentTypeDtoFromEntityConverter;

    @Setter(value = PROTECTED, onMethod_ = @Autowired)
    private PlacesRepository placesRepository;

    @Setter(value = PROTECTED, onMethod_ = @Autowired)
    private ProviderTypesRepository providerTypesRepository;

    @Setter(value = PROTECTED, onMethod_ = @Autowired)
    private ProvidersRepository providersRepository;

    @Setter(value = PROTECTED, onMethod_ = @Autowired)
    private InstrumentTypesRepository instrumentTypesRepository;

    // </editor-fold>
    // -----------------------------------------------------------------------------------------------------------------
    // <editor-fold desc="implements EntitiesDtoLinksConfigurator">

    @SneakyThrows(EntitiesDtoLinksConfigurationException.class)
    @PostConstruct
    public void init() {
        this
                .newDescriptor(this.placeDataPublishChannelApiV1, PlaceEntity.class, Place.class)
                .setDtoPackageClass(PlacesPackage.class)
                .setRepository(this.placesRepository)
                .setDtoFromEntityConverter(this.placeDtoFromEntityConverter)
                .setKeyExtractor(this.placeKeyExtractor);
        this
                .newDescriptor(this.providerTypeDataPublishChannelApiV1, ProviderTypeEntity.class, ProviderType.class)
                .setDtoPackageClass(ProviderTypesPackage.class)
                .setRepository(this.providerTypesRepository)
                .setDtoFromEntityConverter(this.providerTypeDtoFromEntityConverter)
                .setKeyExtractor(this.ProviderTypeKeyExtractor);
        this
                .newDescriptor(this.providerDataPublishChannelApiV1, ProviderEntity.class, Provider.class)
                .setDtoPackageClass(ProvidersPackage.class)
                .setRepository(this.providersRepository)
                .setDtoFromEntityConverter(this.providerDtoFromEntityConverter)
                .setKeyExtractor(this.providerKeyExtractor);
        this
                .newDescriptor(this.instrumentTypeDataPublishChannelApiV1, InstrumentTypeEntity.class, InstrumentType.class)
                .setDtoPackageClass(InstrumentTypesPackage.class)
                .setRepository(this.instrumentTypesRepository)
                .setDtoFromEntityConverter(this.instrumentTypeDtoFromEntityConverter)
                .setKeyExtractor(this.instrumentTypeKeyExtractor);
    }
    // </editor-fold>
    // -----------------------------------------------------------------------------------------------------------------
}
