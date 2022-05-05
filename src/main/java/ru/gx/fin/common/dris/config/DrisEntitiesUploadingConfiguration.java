package ru.gx.fin.common.dris.config;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Configuration;
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

@RequiredArgsConstructor
@Configuration
public class DrisEntitiesUploadingConfiguration extends AbstractEntitiesUploadingConfiguration {
    // -----------------------------------------------------------------------------------------------------------------
    // <editor-fold desc="Fields">
    @NotNull
    private final DrisSnapshotPlaceDataPublishChannelApiV1 placeDataPublishChannelApiV1;

    @NotNull
    private final DrisSnapshotProviderTypeDataPublishChannelApiV1 providerTypeDataPublishChannelApiV1;

    @NotNull
    private final DrisSnapshotProviderDataPublishChannelApiV1 providerDataPublishChannelApiV1;

    @NotNull
    private final DrisSnapshotInstrumentTypeDataPublishChannelApiV1 instrumentTypeDataPublishChannelApiV1;

    @NotNull
    private final PlaceKeyExtractor placeKeyExtractor;

    @NotNull
    private final ProviderTypeKeyExtractor ProviderTypeKeyExtractor;

    @NotNull
    private final ProviderKeyExtractor providerKeyExtractor;

    @NotNull
    private final InstrumentTypeKeyExtractor instrumentTypeKeyExtractor;

    @NotNull
    private final PlaceDtoFromEntityConverter placeDtoFromEntityConverter;

    @NotNull
    private final ProviderTypeDtoFromEntityConverter providerTypeDtoFromEntityConverter;

    @NotNull
    private final ProviderDtoFromEntityConverter providerDtoFromEntityConverter;

    @NotNull
    private final InstrumentTypeDtoFromEntityConverter instrumentTypeDtoFromEntityConverter;

    @NotNull
    private final PlacesRepository placesRepository;

    @NotNull
    private final ProviderTypesRepository providerTypesRepository;

    @NotNull
    private final ProvidersRepository providersRepository;

    @NotNull
    private final InstrumentTypesRepository instrumentTypesRepository;
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
