package ru.gx.fin.common.dris.memdata;

import org.jetbrains.annotations.NotNull;
import ru.gx.core.data.AbstractMemoryRepository;
import ru.gx.fin.common.dris.out.ProviderType;
import ru.gx.fin.common.dris.out.ProviderTypesPackage;

public class ProviderTypesMemoryRepository extends AbstractMemoryRepository<ProviderType, ProviderTypesPackage> {
    @Override
    @NotNull
    public Object extractKey(@NotNull final ProviderType providerType) {
        return providerType.getCode();
    }

    public static class IdResolver extends AbstractIdResolver<ProviderTypesMemoryRepository> {
    }
}
