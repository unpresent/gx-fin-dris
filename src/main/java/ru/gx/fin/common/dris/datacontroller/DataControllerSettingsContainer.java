package ru.gx.fin.common.dris.datacontroller;

import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import ru.gx.core.settings.StandardSettingsController;
import ru.gx.core.settings.UnknownApplicationSettingException;
import ru.gx.fin.common.dris.config.ConfigurationPropertiesServiceRedis;

import javax.annotation.PostConstruct;

import static lombok.AccessLevel.PROTECTED;

public class DataControllerSettingsContainer {
    @Getter(PROTECTED)
    @Setter(value = PROTECTED, onMethod_ = @Autowired)
    @NotNull
    private StandardSettingsController standardSettingsController;

    @PostConstruct
    public void init() {
        this.standardSettingsController.loadStringSetting(ConfigurationPropertiesServiceRedis.OUTCOME_COLLECTION_PLACES, ConfigurationPropertiesServiceRedis.OUTCOME_COLLECTION_PLACES_DEFAULT_VALUE);
        this.standardSettingsController.loadStringSetting(ConfigurationPropertiesServiceRedis.OUTCOME_COLLECTION_PROVIDER_TYPES, ConfigurationPropertiesServiceRedis.OUTCOME_COLLECTION_PROVIDER_TYPES_DEFAULT_VALUE);
        this.standardSettingsController.loadStringSetting(ConfigurationPropertiesServiceRedis.OUTCOME_COLLECTION_PROVIDERS, ConfigurationPropertiesServiceRedis.OUTCOME_COLLECTION_PROVIDERS_DEFAULT_VALUE);
        this.standardSettingsController.loadStringSetting(ConfigurationPropertiesServiceRedis.OUTCOME_COLLECTION_INSTRUMENT_TYPES, ConfigurationPropertiesServiceRedis.OUTCOME_COLLECTION_INSTRUMENT_TYPES_DEFAULT_VALUE);
    }

    public String getOutcomeCollectionPlaces() {
        return this.standardSettingsController.getStringSetting(ConfigurationPropertiesServiceRedis.OUTCOME_COLLECTION_PLACES);
    }

    public String getOutcomeCollectionProviderTypes() {
        return this.standardSettingsController.getStringSetting(ConfigurationPropertiesServiceRedis.OUTCOME_COLLECTION_PROVIDER_TYPES);
    }

    public String getOutcomeCollectionProviders() {
        return this.standardSettingsController.getStringSetting(ConfigurationPropertiesServiceRedis.OUTCOME_COLLECTION_PROVIDERS);
    }

    public String getOutcomeCollectionInstrumentTypes() {
        return this.standardSettingsController.getStringSetting(ConfigurationPropertiesServiceRedis.OUTCOME_COLLECTION_INSTRUMENT_TYPES);
    }
}
