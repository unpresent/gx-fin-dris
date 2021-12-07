package ru.gx.fin.common.dris.converters;

import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import ru.gx.core.data.edlinking.AbstractEntityFromDtoConverter;
import ru.gx.fin.common.dris.out.ProviderType;
import ru.gx.fin.common.dris.entities.ProviderTypeEntity;
import ru.gx.fin.common.dris.repository.ProviderTypesRepository;

import static lombok.AccessLevel.PROTECTED;

public class ProviderTypeEntityFromDtoConverter extends AbstractEntityFromDtoConverter<ProviderTypeEntity, ProviderType> {
    @Getter(PROTECTED)
    @Setter(value = PROTECTED, onMethod_ = @Autowired)
    private ProviderTypesRepository providerTypesRepository;

    @Override
    @Nullable
    public ProviderTypeEntity findDtoBySource(@Nullable final ProviderType source) {
        if (source == null) {
            return null;
        }
        return this.providerTypesRepository.findByCode(source.getCode()).orElse(null);
    }

    @Override
    @NotNull
    public ProviderTypeEntity createDtoBySource(@NotNull final ProviderType source) {
        final var result = new ProviderTypeEntity();
        updateDtoBySource(result, source);
        return result;
    }

    @Override
    public boolean isDestinationUpdatable(@NotNull ProviderTypeEntity destination) {
        return true;
    }

    @Override
    public void updateDtoBySource(@NotNull ProviderTypeEntity destination, @NotNull ProviderType source) {
        final var parent = this.findDtoBySource(source.getParent());
        final var rootType = this.findDtoBySource(source.getRootType());
        destination
                .setCode(source.getCode())
                .setName(source.getName())
                .setRootType(rootType)
                .setParent(parent);
    }
}
