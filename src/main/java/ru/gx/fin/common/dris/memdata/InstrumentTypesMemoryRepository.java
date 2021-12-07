package ru.gx.fin.common.dris.memdata;

import org.jetbrains.annotations.NotNull;
import ru.gx.core.data.AbstractMemoryRepository;
import ru.gx.fin.common.dris.out.InstrumentType;
import ru.gx.fin.common.dris.out.InstrumentTypesPackage;

public class InstrumentTypesMemoryRepository extends AbstractMemoryRepository<InstrumentType, InstrumentTypesPackage> {
    @Override
    @NotNull
    public Object extractKey(@NotNull final InstrumentType instrumentType) {
        return instrumentType.getCode();
    }

    public static class IdResolver extends AbstractIdResolver<InstrumentTypesMemoryRepository> {
    }
}
