package ru.gx.fin.common.dris.converters;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.stereotype.Component;
import ru.gx.core.data.edlinking.AbstractEntityFromDtoConverter;
import ru.gx.fin.common.dris.entities.InstrumentTypeEntity;
import ru.gx.fin.common.dris.out.InstrumentType;
import ru.gx.fin.common.dris.repository.InstrumentTypesRepository;

import static lombok.AccessLevel.PROTECTED;

@RequiredArgsConstructor
@Component
public class InstrumentTypeEntityFromDtoConverter extends AbstractEntityFromDtoConverter<InstrumentTypeEntity, InstrumentType> {
    @Getter(PROTECTED)
    @NotNull
    private final InstrumentTypesRepository instrumentTypesRepository;

    @Override
    @Nullable
    public InstrumentTypeEntity findEntityBySource(@Nullable final InstrumentType source) {
        if (source == null) {
            return null;
        }
        return this.instrumentTypesRepository.findByCode(source.getCode()).orElse(null);
    }

    @Override
    @NotNull
    public InstrumentTypeEntity createEntityBySource(@NotNull final InstrumentType source) {
        final var result = new InstrumentTypeEntity();
        updateDtoBySource(result, source);
        return result;
    }

    @Override
    public boolean isDestinationUpdatable(@NotNull final InstrumentTypeEntity destination) {
        return true;
    }

    @Override
    public void updateDtoBySource(@NotNull InstrumentTypeEntity destination, @NotNull InstrumentType source) {
        final var rootType = this.instrumentTypesRepository
                .findByCode(source.getRootType())
                .orElse(null);

        final var parent = this.instrumentTypesRepository
                .findByCode(source.getParent())
                .orElse(null);

        destination
                .setCode(source.getCode())
                .setNameShort(source.getNameShort())
                .setNameFull(source.getNameFull())
                .setRootType(rootType)
                .setParent(parent);
    }
}
