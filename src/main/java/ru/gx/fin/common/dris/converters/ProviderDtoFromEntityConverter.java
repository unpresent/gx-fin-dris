package ru.gx.fin.common.dris.converters;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.stereotype.Component;
import ru.gx.core.data.NotAllowedObjectUpdateException;
import ru.gx.core.data.edlinking.AbstractDtoFromEntityConverter;
import ru.gx.fin.common.dris.entities.ProviderEntity;
import ru.gx.fin.common.dris.out.Provider;

@RequiredArgsConstructor
@Component
public class ProviderDtoFromEntityConverter extends AbstractDtoFromEntityConverter<Provider, ProviderEntity> {
    @Override
    @Nullable
    public Provider findDtoBySource(@Nullable final ProviderEntity source) {
        return null;
    }

    @SneakyThrows(Exception.class)
    @Override
    @NotNull
    public Provider createDtoBySource(@NotNull ProviderEntity source) {
        final var sourceType = source.getType();
        if (sourceType == null) {
            throw new Exception("It isn't allowed create Provider with null type; source = " + source);
        }

        final var sourcePlace = source.getPlace();

        return new Provider(
                source.getCode(),
                source.getName(),
                sourceType.getCode(),
                sourcePlace != null ? sourcePlace.getCode() : null
        );
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
