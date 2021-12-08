package ru.gx.fin.common.dris.config;

import org.jetbrains.annotations.NotNull;
import ru.gx.core.channels.ChannelsConfiguration;
import ru.gx.core.channels.ChannelsConfigurator;
import ru.gx.core.channels.SerializeMode;
import ru.gx.core.redis.upload.RedisOutcomeCollectionLoadingDescriptor;
import ru.gx.core.redis.upload.SimpleRedisOutcomeCollectionsConfiguration;
import ru.gx.fin.common.dris.out.InstrumentType;
import ru.gx.fin.common.dris.out.Place;
import ru.gx.fin.common.dris.out.Provider;
import ru.gx.fin.common.dris.out.ProviderType;

public class ChannelsConfiguratorImpl implements ChannelsConfigurator {
    @SuppressWarnings("unchecked")
    @Override
    public void configureChannels(@NotNull ChannelsConfiguration channelsConfiguration) {
        if (channelsConfiguration instanceof final SimpleRedisOutcomeCollectionsConfiguration config) {
            config.getDescriptorsDefaults()
                    .setSerializeMode(SerializeMode.JsonString);
            config
                    .newDescriptor(ChannelsNames.DrisSnapshots.PLACES, RedisOutcomeCollectionLoadingDescriptor.class)
                    .setDataObjectClass(Place.class)
                    .init();

            config
                    .newDescriptor(ChannelsNames.DrisSnapshots.PROVIDER_TYPES, RedisOutcomeCollectionLoadingDescriptor.class)
                    .setDataObjectClass(ProviderType.class)
                    .init();

            config
                    .newDescriptor(ChannelsNames.DrisSnapshots.PROVIDERS, RedisOutcomeCollectionLoadingDescriptor.class)
                    .setDataObjectClass(Provider.class)
                    .init();

            config
                    .newDescriptor(ChannelsNames.DrisSnapshots.INSTRUMENT_TYPES, RedisOutcomeCollectionLoadingDescriptor.class)
                    .setDataObjectClass(InstrumentType.class)
                    .init();
        }
    }
}
