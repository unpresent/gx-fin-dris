package ru.gx.fin.common.dris.converters;

import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import ru.gx.core.data.NotAllowedObjectUpdateException;
import ru.gx.core.data.edlinking.AbstractDtoFromEntityConverter;
import ru.gx.fin.common.dris.out.Provider;
import ru.gx.fin.common.dris.entities.ProviderEntity;
import ru.gx.fin.common.dris.memdata.ProvidersMemoryRepository;

import static lombok.AccessLevel.PROTECTED;

public class ProviderDtoFromEntityConverter extends AbstractDtoFromEntityConverter<Provider, ProviderEntity> {
    @Getter(PROTECTED)
    @Setter(value = PROTECTED, onMethod_ = @Autowired)
    private ProvidersMemoryRepository providersMemoryRepository;

    @Getter(PROTECTED)
    @Setter(value = PROTECTED, onMethod_ = @Autowired)
    private ProviderTypeDtoFromEntityConverter providerTypeDtoFromEntityConverter;

    @Getter(PROTECTED)
    @Setter(value = PROTECTED, onMethod_ = @Autowired)
    private PlaceDtoFromEntityConverter placeDtoFromEntityConverter;

    @Override
    @Nullable
    public Provider findDtoBySource(@Nullable final ProviderEntity source) {
        if (source == null) {
            return null;
        }
        var sourceCode = source.getCode();
        if (sourceCode == null) {
            return null;
        }
        return this.providersMemoryRepository.getByKey(sourceCode);
    }

    @SneakyThrows(Exception.class)
    @Override
    @NotNull
    public Provider createDtoBySource(@NotNull ProviderEntity source) {
        final var sourceType = source.getType();
        if (sourceType == null) {
            throw new Exception("It isn't allowed create Provider with null type; source = " + source);
        }
        final var type = this.providerTypeDtoFromEntityConverter.findDtoBySource(sourceType);
        if (type == null) {
            throw new Exception("Can't find in memory ProviderType by ProviderTypeEntity; sourceType = " + sourceType);
        }

        final var place = this.placeDtoFromEntityConverter.findDtoBySource(source.getPlace());

        final var result = new Provider(
                source.getCode(),
                source.getName(),
                type,
                place
        );
        this.providersMemoryRepository.put(result);
        return result;
    }

    @Override
    public boolean isDestinationUpdatable(@NotNull Provider destination) {
        return false;
    }

    @Override
    public void updateDtoBySource(@NotNull Provider destination, @NotNull ProviderEntity source) throws NotAllowedObjectUpdateException {
        throw new NotAllowedObjectUpdateException(Provider.class, null);
    }
}
