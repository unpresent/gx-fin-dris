package ru.gx.fin.common.dris.converters;

import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import ru.gx.core.data.edlinking.AbstractEntityFromDtoConverter;
import ru.gx.fin.common.dris.out.Provider;
import ru.gx.fin.common.dris.entities.ProviderEntity;
import ru.gx.fin.common.dris.repository.PlacesRepository;
import ru.gx.fin.common.dris.repository.ProviderTypesRepository;
import ru.gx.fin.common.dris.repository.ProvidersRepository;

import static lombok.AccessLevel.PROTECTED;

public class ProviderEntityFromDtoConverter extends AbstractEntityFromDtoConverter<ProviderEntity, Provider> {
    @Getter(PROTECTED)
    @Setter(value = PROTECTED, onMethod_ = @Autowired)
    private ProvidersRepository providersRepository;

    @Getter(PROTECTED)
    @Setter(value = PROTECTED, onMethod_ = @Autowired)
    private ProviderTypesRepository providerTypesRepository;

    @Getter(PROTECTED)
    @Setter(value = PROTECTED, onMethod_ = @Autowired)
    private PlacesRepository placesRepository;

    @Override
    @Nullable
    public ProviderEntity findDtoBySource(@Nullable Provider source) {
        if (source == null) {
            return null;
        }
        return this.providersRepository.findByCode(source.getCode()).orElse(null);
    }

    @Override
    @NotNull
    public ProviderEntity createDtoBySource(@NotNull Provider source) {
        final var result = new ProviderEntity();
        updateDtoBySource(result, source);
        return result;
    }

    @Override
    public boolean isDestinationUpdatable(@NotNull ProviderEntity destination) {
        return true;
    }

    @Override
    public void updateDtoBySource(@NotNull ProviderEntity destination, @NotNull Provider source) {
        final var type = this.providerTypesRepository
                .findByCode(source.getType())
                .orElse(null);

        final var place = this.placesRepository
                .findByCode(source.getPlace())
                .orElse(null);

        destination
                .setCode(source.getCode())
                .setName(source.getName())
                .setType(type)
                .setPlace(place);
    }
}
