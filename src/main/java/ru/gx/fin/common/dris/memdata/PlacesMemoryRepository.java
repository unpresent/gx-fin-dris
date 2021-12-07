package ru.gx.fin.common.dris.memdata;

import org.jetbrains.annotations.NotNull;
import ru.gx.core.data.AbstractMemoryRepository;
import ru.gx.fin.common.dris.out.Place;
import ru.gx.fin.common.dris.out.PlacesPackage;

public class PlacesMemoryRepository extends AbstractMemoryRepository<Place, PlacesPackage> {
    @Override
    @NotNull
    public Object extractKey(@NotNull final Place place) {
        return place.getCode();
    }

    public static class IdResolver extends AbstractIdResolver<PlacesMemoryRepository> {
    }
}
