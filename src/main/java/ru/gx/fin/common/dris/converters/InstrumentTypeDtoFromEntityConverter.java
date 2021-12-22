package ru.gx.fin.common.dris.converters;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.gx.core.data.NotAllowedObjectUpdateException;
import ru.gx.core.data.edlinking.AbstractDtoFromEntityConverter;
import ru.gx.fin.common.dris.entities.InstrumentTypeEntity;
import ru.gx.fin.common.dris.out.InstrumentType;

public class InstrumentTypeDtoFromEntityConverter extends AbstractDtoFromEntityConverter<InstrumentType, InstrumentTypeEntity> {

    @Override
    @Nullable
    public InstrumentType findDtoBySource(@Nullable final InstrumentTypeEntity source) {
        return null;
    }

    @Override
    @NotNull
    public InstrumentType createDtoBySource(@NotNull final InstrumentTypeEntity source) {
        final var sourceRootType = source.getRootType();
        final var sourceParent = source.getParent();

        return new InstrumentType(
                sourceRootType != null ? sourceRootType.getCode() : null,
                sourceParent != null ? sourceParent.getCode() : null,
                source.getCode(),
                source.getNameShort(),
                source.getNameFull()
        );
    }

    @Override
    public boolean isDestinationUpdatable(@NotNull final InstrumentType destination) {
        return false;
    }

    @Override
    public void updateDtoBySource(@NotNull final InstrumentType destination, @NotNull final InstrumentTypeEntity source) throws NotAllowedObjectUpdateException {
        throw new NotAllowedObjectUpdateException(InstrumentType.class, null);
    }
}
