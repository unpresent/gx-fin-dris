package ru.gx.fin.common.dris.config;

import lombok.Setter;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import ru.gx.core.data.edlinking.EntitiesDtoLinksConfigurator;
import ru.gx.core.data.edlinking.EntitiesDtosLinksConfiguration;
import ru.gx.fin.common.dris.converters.InstrumentTypeDtoFromEntityConverter;
import ru.gx.fin.common.dris.converters.PlaceDtoFromEntityConverter;
import ru.gx.fin.common.dris.converters.ProviderDtoFromEntityConverter;
import ru.gx.fin.common.dris.converters.ProviderTypeDtoFromEntityConverter;
import ru.gx.fin.common.dris.entities.*;
import ru.gx.fin.common.dris.memdata.InstrumentTypesMemoryRepository;
import ru.gx.fin.common.dris.memdata.PlacesMemoryRepository;
import ru.gx.fin.common.dris.memdata.ProviderTypesMemoryRepository;
import ru.gx.fin.common.dris.memdata.ProvidersMemoryRepository;
import ru.gx.fin.common.dris.out.*;
import ru.gx.fin.common.dris.repository.InstrumentTypesRepository;
import ru.gx.fin.common.dris.repository.PlacesRepository;
import ru.gx.fin.common.dris.repository.ProviderTypesRepository;
import ru.gx.fin.common.dris.repository.ProvidersRepository;

import static lombok.AccessLevel.PROTECTED;

public class EntitiesDtoLinksConfig implements EntitiesDtoLinksConfigurator {
    // -----------------------------------------------------------------------------------------------------------------
    // <editor-fold desc="Fields">
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

    @Setter(value = PROTECTED, onMethod_ = @Autowired)
    private PlacesMemoryRepository placesMemoryRepository;

    @Setter(value = PROTECTED, onMethod_ = @Autowired)
    private ProviderTypesMemoryRepository providerTypesMemoryRepository;

    @Setter(value = PROTECTED, onMethod_ = @Autowired)
    private ProvidersMemoryRepository providersMemoryRepository;

    @Setter(value = PROTECTED, onMethod_ = @Autowired)
    private InstrumentTypesMemoryRepository instrumentTypesMemoryRepository;

    // </editor-fold>
    // -----------------------------------------------------------------------------------------------------------------
    // <editor-fold desc="implements EntitiesDtoLinksConfigurator">

    @Override
    public void configureLinks(@NotNull final EntitiesDtosLinksConfiguration configuration) {
        configuration
                .<PlaceEntity, PlaceEntitiesPackage, Short, Place, PlacesPackage>
                        newDescriptor(PlaceEntity.class, Place.class)
                .setDtoPackageClass(PlacesPackage.class)
                .setRepository(this.placesRepository)
                .setMemoryRepository(this.placesMemoryRepository)
                .setDtoFromEntityConverter(this.placeDtoFromEntityConverter);
        configuration
                .<ProviderTypeEntity, ProviderTypeEntitiesPackage, Short, ProviderType, ProviderTypesPackage>
                        newDescriptor(ProviderTypeEntity.class, ProviderType.class)
                .setDtoPackageClass(ProviderTypesPackage.class)
                .setRepository(this.providerTypesRepository)
                .setMemoryRepository(this.providerTypesMemoryRepository)
                .setDtoFromEntityConverter(this.providerTypeDtoFromEntityConverter);
        configuration
                .<ProviderEntity, ProviderEntitiesPackage, Short, Provider, ProvidersPackage>
                        newDescriptor(ProviderEntity.class, Provider.class)
                .setDtoPackageClass(ProvidersPackage.class)
                .setRepository(this.providersRepository)
                .setMemoryRepository(this.providersMemoryRepository)
                .setDtoFromEntityConverter(this.providerDtoFromEntityConverter);
        configuration
                .<InstrumentTypeEntity, InstrumentTypeEntitiesPackage, Short, InstrumentType, InstrumentTypesPackage>
                        newDescriptor(InstrumentTypeEntity.class, InstrumentType.class)
                .setDtoPackageClass(InstrumentTypesPackage.class)
                .setRepository(this.instrumentTypesRepository)
                .setMemoryRepository(this.instrumentTypesMemoryRepository)
                .setDtoFromEntityConverter(this.instrumentTypeDtoFromEntityConverter);
    }
    // </editor-fold>
    // -----------------------------------------------------------------------------------------------------------------
}
