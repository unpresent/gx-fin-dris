package ru.gx.fin.common.dris.config;

import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import ru.gx.core.redis.upload.AbstractRedisOutcomeCollectionsConfiguration;
import ru.gx.core.redis.upload.RedisOutcomeCollectionUploadingDescriptor;
import ru.gx.fin.common.dris.channels.DrisSnapshotInstrumentTypeDataPublishChannelApiV1;
import ru.gx.fin.common.dris.channels.DrisSnapshotPlaceDataPublishChannelApiV1;
import ru.gx.fin.common.dris.channels.DrisSnapshotProviderDataPublishChannelApiV1;
import ru.gx.fin.common.dris.channels.DrisSnapshotProviderTypeDataPublishChannelApiV1;

import javax.annotation.PostConstruct;

import static lombok.AccessLevel.PROTECTED;

public class RedisOutcomeCollectionsConfiguration extends AbstractRedisOutcomeCollectionsConfiguration {
    @Getter(PROTECTED)
    @Setter(value = PROTECTED, onMethod_ = @Autowired)
    private DrisSnapshotPlaceDataPublishChannelApiV1 placeChannelApiV1;

    @Getter(PROTECTED)
    @Setter(value = PROTECTED, onMethod_ = @Autowired)
    private DrisSnapshotProviderTypeDataPublishChannelApiV1 providerTypeChannelApiV1;

    @Getter(PROTECTED)
    @Setter(value = PROTECTED, onMethod_ = @Autowired)
    private DrisSnapshotProviderDataPublishChannelApiV1 providerChannelApiV1;

    @Getter(PROTECTED)
    @Setter(value = PROTECTED, onMethod_ = @Autowired)
    private DrisSnapshotInstrumentTypeDataPublishChannelApiV1 instrumentTypeChannelApiV1;

    public RedisOutcomeCollectionsConfiguration(@NotNull String configurationName) {
        super(configurationName);
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
