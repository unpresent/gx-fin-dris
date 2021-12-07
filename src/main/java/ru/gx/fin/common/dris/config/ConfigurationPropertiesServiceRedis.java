package ru.gx.fin.common.dris.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

@ConfigurationProperties(prefix = "service.redis")
@Getter
@Setter
public class ConfigurationPropertiesServiceRedis {
    public static final String OUTCOME_COLLECTION_PLACES = "service.redis.outcome-collections.places";
    public static final String OUTCOME_COLLECTION_PROVIDER_TYPES = "service.redis.outcome-collections.provider-types";
    public static final String OUTCOME_COLLECTION_PROVIDERS = "service.redis.outcome-collections.providers";
    public static final String OUTCOME_COLLECTION_INSTRUMENT_TYPES = "service.redis.outcome-collections.instrument-types";

    public static final String OUTCOME_COLLECTION_PLACES_DEFAULT_VALUE = "dris.places";
    public static final String OUTCOME_COLLECTION_PROVIDER_TYPES_DEFAULT_VALUE = "dris.provider-types";
    public static final String OUTCOME_COLLECTION_PROVIDERS_DEFAULT_VALUE = "dris.providers";
    public static final String OUTCOME_COLLECTION_INSTRUMENT_TYPES_DEFAULT_VALUE = "dris.instrumentTypes";

    @NestedConfigurationProperty
    private OutcomeCollections outcomeCollections = new OutcomeCollections();

    @Getter
    @Setter
    private static class OutcomeCollections {
        private String places = OUTCOME_COLLECTION_PLACES_DEFAULT_VALUE;
        private String providerTypes = OUTCOME_COLLECTION_PROVIDER_TYPES_DEFAULT_VALUE;
        private String providers = OUTCOME_COLLECTION_PROVIDERS_DEFAULT_VALUE;
        private String instrumentTypes = OUTCOME_COLLECTION_INSTRUMENT_TYPES_DEFAULT_VALUE;
    }
}
