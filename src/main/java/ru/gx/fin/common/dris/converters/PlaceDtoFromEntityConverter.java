package ru.gx.fin.common.dris.converters;

import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import ru.gx.core.data.NotAllowedObjectUpdateException;
import ru.gx.core.data.edlinking.AbstractDtoFromEntityConverter;
import ru.gx.fin.common.dris.out.Place;
import ru.gx.fin.common.dris.entities.PlaceEntity;
import ru.gx.fin.common.dris.memdata.PlacesMemoryRepository;

import static lombok.AccessLevel.PROTECTED;

public class PlaceDtoFromEntityConverter extends AbstractDtoFromEntityConverter<Place, PlaceEntity> {
    @Getter(PROTECTED)
    @Setter(value = PROTECTED, onMethod_ = @Autowired)
    private PlacesMemoryRepository placesMemoryRepository;

    @Override
    @Nullable
    public Place findDtoBySource(@Nullable final PlaceEntity source) {
        if (source == null) {
            return null;
        }
        final var sourceCode = source.getCode();
        if (sourceCode == null) {
            return null;
        }
        return this.placesMemoryRepository.getByKey(sourceCode);
    }

    @Override
    @NotNull
    public Place createDtoBySource(@NotNull PlaceEntity source) {
        final var result = new Place(
                source.getCode(),
                source.getName()
        );
        this.placesMemoryRepository.put(result);
        return result;
    }

    @Override
    public boolean isDestinationUpdatable(@NotNull Place destination) {
        return false;
    }

    @Override
    public void updateDtoBySource(@NotNull Place place, @NotNull PlaceEntity placeEntity) throws NotAllowedObjectUpdateException {
        throw new NotAllowedObjectUpdateException(Place.class, null);
    }
}
