package ru.gx.fin.common.dris.memdata;

import org.jetbrains.annotations.NotNull;
import ru.gx.core.data.AbstractMemoryRepository;
import ru.gx.fin.common.dris.out.Provider;
import ru.gx.fin.common.dris.out.ProvidersPackage;

public class ProvidersMemoryRepository extends AbstractMemoryRepository<Provider, ProvidersPackage> {
    @Override
    @NotNull
    public Object extractKey(@NotNull final Provider provider) {
        return provider.getCode();
    }

    public static class IdResolver extends AbstractIdResolver<ProvidersMemoryRepository> {
    }
}
