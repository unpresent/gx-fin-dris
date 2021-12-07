package ru.gx.fin.common.dris.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import ru.gx.fin.common.dris.converters.*;
import ru.gx.fin.common.dris.datacontroller.DataController;
import ru.gx.fin.common.dris.datacontroller.DataControllerSettingsContainer;
import ru.gx.fin.common.dris.memdata.InstrumentTypesMemoryRepository;
import ru.gx.fin.common.dris.memdata.PlacesMemoryRepository;
import ru.gx.fin.common.dris.memdata.ProviderTypesMemoryRepository;
import ru.gx.fin.common.dris.memdata.ProvidersMemoryRepository;

@EnableJpaRepositories("ru.gx.fin.common.dris.repository")
@EntityScan({"ru.gx.fin.common.dris.entities"})
@EnableConfigurationProperties({ConfigurationPropertiesServiceRedis.class})
public abstract class CommonConfig {
    // -----------------------------------------------------------------------------------------------------------------
    // <editor-fold desc="Common">

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper().registerModule(new JavaTimeModule());
    }
    // </editor-fold>
    // -----------------------------------------------------------------------------------------------------------------
    // <editor-fold desc="DataController">
    @Bean
    public DataControllerSettingsContainer dataControllerSettingsContainer() {
        return new DataControllerSettingsContainer();
    }

    @Bean
    public DataController dataController() {
        return new DataController();
    }

    // </editor-fold>
    // -----------------------------------------------------------------------------------------------------------------
    // <editor-fold desc="MemoryRepositories">
    @Bean
    public InstrumentTypesMemoryRepository instrumentTypesMemoryRepository() {
        return new InstrumentTypesMemoryRepository();
    }

    @Bean
    public PlacesMemoryRepository placesMemoryRepository() {
        return new PlacesMemoryRepository();
    }

    @Bean
    public ProviderTypesMemoryRepository providerTypesMemoryRepository() {
        return new ProviderTypesMemoryRepository();
    }

    @Bean
    public ProvidersMemoryRepository providersMemoryRepository() {
        return new ProvidersMemoryRepository();
    }
    // </editor-fold>
    // -----------------------------------------------------------------------------------------------------------------
    // <editor-fold desc="Converters">
    @Bean
    public InstrumentTypeDtoFromEntityConverter instrumentTypeDtoFromEntityConverter() {
        return new InstrumentTypeDtoFromEntityConverter();
    }

    @Bean
    public InstrumentTypeEntityFromDtoConverter instrumentTypeEntityFromDtoConverter() {
        return new InstrumentTypeEntityFromDtoConverter();
    }

    @Bean
    public PlaceDtoFromEntityConverter placeDtoFromEntityConverter() {
        return new PlaceDtoFromEntityConverter();
    }

    @Bean
    public PlaceEntityFromDtoConverter placeEntityFromDtoConverter() {
        return new PlaceEntityFromDtoConverter();
    }

    @Bean
    public ProviderDtoFromEntityConverter providerDtoFromEntityConverter() {
        return new ProviderDtoFromEntityConverter();
    }

    @Bean
    public ProviderEntityFromDtoConverter providerEntityFromDtoConverter() {
        return new ProviderEntityFromDtoConverter();
    }

    @Bean
    public ProviderTypeDtoFromEntityConverter providerTypeDtoFromEntityConverter() {
        return new ProviderTypeDtoFromEntityConverter();
    }

    @Bean
    public ProviderTypeEntityFromDtoConverter providerTypeEntityFromDtoConverter() {
        return new ProviderTypeEntityFromDtoConverter();
    }
    // </editor-fold>
    // -----------------------------------------------------------------------------------------------------------------
    // <editor-fold desc="Redis">

    @Bean
    EntitiesDtoLinksConfig entitiesDtoLinksConfig() {
        return new EntitiesDtoLinksConfig();
    }

    @Bean
    DrisChannelsConfigurator drisChannelsConfigurator() {
        return new DrisChannelsConfigurator();
    }

    // </editor-fold>
    // -----------------------------------------------------------------------------------------------------------------
    //    @Bean
    //    public GroupedOpenApi publicUserApi() {
    //        return GroupedOpenApi.builder()
    //                .group("data")
    //                .pathsToMatch("/data/**")
    //                .build();
    //    }


    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(
                        new Info()
                                .title("Test Api")
                                .version("1.0.0")
                                .contact(
                                        new Contact()
                                                .email("vladimir.gagarkin@gmail.com")
                                                .url("https://gagarkin.ru")
                                                .name("Vladimir Gagarkin")
                                )
                );
    }
}
