package ru.gx.fin.common.dris.converters;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.gx.core.data.NotAllowedObjectUpdateException;
import ru.gx.core.data.edlinking.AbstractDtoFromEntityConverter;
import ru.gx.fin.common.dris.entities.PlaceEntity;
import ru.gx.fin.common.dris.out.Place;

public class PlaceDtoFromEntityConverter extends AbstractDtoFromEntityConverter<Place, PlaceEntity> {
    @Override
    @Nullable
    public Place findDtoBySource(@Nullable final PlaceEntity source) {
        return null;
    }

    @Override
    @NotNull
    public Place createDtoBySource(@NotNull PlaceEntity source) {
        return new Place(
                source.getCode(),
                source.getName()
        );
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
