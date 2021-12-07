package ru.gx.fin.common.dris.config;

import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import ru.gx.core.channels.ChannelsConfiguration;
import ru.gx.core.channels.ChannelsConfigurator;
import ru.gx.core.channels.SerializeMode;
import ru.gx.core.redis.upload.RedisOutcomeCollectionLoadingDescriptor;
import ru.gx.core.redis.upload.SimpleRedisOutcomeCollectionsConfiguration;
import ru.gx.fin.common.dris.datacontroller.DataControllerSettingsContainer;

import static lombok.AccessLevel.PROTECTED;

public class DrisChannelsConfigurator implements ChannelsConfigurator {
    @Getter(PROTECTED)
    @Setter(value = PROTECTED, onMethod_ = @Autowired)
    private DataControllerSettingsContainer settings;

    @Override
    public void configureChannels(@NotNull ChannelsConfiguration channelsConfiguration) {
        if (channelsConfiguration instanceof final SimpleRedisOutcomeCollectionsConfiguration config) {
            config.getDescriptorsDefaults()
                    .setSerializeMode(SerializeMode.JsonString);
            config
                    .newDescriptor(settings.getOutcomeCollectionPlaces(), RedisOutcomeCollectionLoadingDescriptor.class)
                    .init();

            config
                    .newDescriptor(settings.getOutcomeCollectionProviderTypes(), RedisOutcomeCollectionLoadingDescriptor.class)
                    .init();

            config
                    .newDescriptor(settings.getOutcomeCollectionProviders(), RedisOutcomeCollectionLoadingDescriptor.class)
                    .init();

            config
                    .newDescriptor(settings.getOutcomeCollectionInstrumentTypes(), RedisOutcomeCollectionLoadingDescriptor.class)
                    .init();
        }
    }
}
