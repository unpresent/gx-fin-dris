package ru.gx.fin.common.dris.converters;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.gx.core.data.NotAllowedObjectUpdateException;
import ru.gx.core.data.edlinking.AbstractDtoFromEntityConverter;
import ru.gx.fin.common.dris.entities.ProviderTypeEntity;
import ru.gx.fin.common.dris.out.ProviderType;

public class ProviderTypeDtoFromEntityConverter extends AbstractDtoFromEntityConverter<ProviderType, ProviderTypeEntity> {
    @Override
    @Nullable
    public ProviderType findDtoBySource(@Nullable final ProviderTypeEntity source) {
        return null;
    }

    @Override
    @NotNull
    public ProviderType createDtoBySource(@NotNull final ProviderTypeEntity source) {
        var sourceRootType = source.getRootType();
        var sourceParent = source.getParent();

        return new ProviderType(
                sourceRootType != null ? sourceRootType.getCode() : null,
                sourceParent != null ? sourceParent.getCode() : null,
                source.getCode(),
                source.getName()
        );
    }

    @Override
    public boolean isDestinationUpdatable(@NotNull ProviderType destination) {
        return false;
    }

    @Override
    public void updateDtoBySource(@NotNull ProviderType destination, @NotNull ProviderTypeEntity source) throws NotAllowedObjectUpdateException {
        throw new NotAllowedObjectUpdateException(ProviderType.class, null);
    }
}
