package ru.gx.fin.common.dris.converters;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.stereotype.Component;
import ru.gx.core.data.edlinking.AbstractEntityFromDtoConverter;
import ru.gx.fin.common.dris.entities.PlaceEntity;
import ru.gx.fin.common.dris.out.Place;
import ru.gx.fin.common.dris.repository.PlacesRepository;

import static lombok.AccessLevel.PROTECTED;

@RequiredArgsConstructor
@Component
public class PlaceEntityFromDtoConverter extends AbstractEntityFromDtoConverter<PlaceEntity, Place> {

    @Getter(PROTECTED)
    @NotNull
    private final PlacesRepository placesRepository;

    @Override
    @Nullable
    public PlaceEntity findEntityBySource(@Nullable Place source) {
        if (source == null) {
            return null;
        }
        return this.placesRepository.findByCode(source.getCode()).orElse(null);
    }

    @Override
    @NotNull
    public PlaceEntity createEntityBySource(@NotNull Place source) {
        final var result = new PlaceEntity();
        updateDtoBySource(result, source);
        return result;
    }

    @Override
    public boolean isDestinationUpdatable(@NotNull PlaceEntity destination) {
        return true;
    }

    @Override
    public void updateDtoBySource(@NotNull PlaceEntity destination, @NotNull Place source) {
        destination
                .setCode(source.getCode())
                .setName(source.getName());
    }
}
