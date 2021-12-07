package ru.gx.fin.common.dris.converters;

import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import ru.gx.core.data.NotAllowedObjectUpdateException;
import ru.gx.core.data.edlinking.AbstractDtoFromEntityConverter;
import ru.gx.fin.common.dris.entities.InstrumentTypeEntity;
import ru.gx.fin.common.dris.memdata.InstrumentTypesMemoryRepository;
import ru.gx.fin.common.dris.out.InstrumentType;

import static lombok.AccessLevel.PROTECTED;

public class InstrumentTypeDtoFromEntityConverter extends AbstractDtoFromEntityConverter<InstrumentType, InstrumentTypeEntity> {
    @Getter(PROTECTED)
    @Setter(value = PROTECTED, onMethod_ = @Autowired)
    private InstrumentTypesMemoryRepository instrumentTypesMemoryRepository;

    @Override
    @Nullable
    public InstrumentType findDtoBySource(@Nullable final InstrumentTypeEntity source) {
        if (source == null) {
            return null;
        }
        final var sourceCode = source.getCode();
        if (sourceCode == null) {
            return null;
        }
        return this.instrumentTypesMemoryRepository.getByKey(sourceCode);
    }

    @Override
    @NotNull
    public InstrumentType createDtoBySource(@NotNull final InstrumentTypeEntity source) {
        final var rootType = this.findDtoBySource(source.getRootType());
        final var parent = this.findDtoBySource(source.getParent());
        return new InstrumentType(
                rootType,
                parent,
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
