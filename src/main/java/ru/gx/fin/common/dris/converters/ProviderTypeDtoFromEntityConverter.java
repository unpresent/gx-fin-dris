package ru.gx.fin.common.dris.converters;

import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import ru.gx.core.data.NotAllowedObjectUpdateException;
import ru.gx.core.data.edlinking.AbstractDtoFromEntityConverter;
import ru.gx.fin.common.dris.out.ProviderType;
import ru.gx.fin.common.dris.entities.ProviderTypeEntity;
import ru.gx.fin.common.dris.memdata.ProviderTypesMemoryRepository;

import static lombok.AccessLevel.PROTECTED;

public class ProviderTypeDtoFromEntityConverter extends AbstractDtoFromEntityConverter<ProviderType, ProviderTypeEntity> {
    @Getter(PROTECTED)
    @Setter(value = PROTECTED, onMethod_ = @Autowired)
    private ProviderTypesMemoryRepository providerTypesMemoryRepository;

    @Override
    @Nullable
    public ProviderType findDtoBySource(@Nullable final ProviderTypeEntity source) {
        if (source == null) {
            return null;
        }
        final var sourceCode = source.getCode();
        if (sourceCode == null) {
            return null;
        }
        return this.providerTypesMemoryRepository.getByKey(sourceCode);
    }

    @Override
    @NotNull
    public ProviderType createDtoBySource(@NotNull final ProviderTypeEntity source) {
        var rootType = this.findDtoBySource(source.getRootType());
        if (source.getRootType() != null && rootType == null) {
            rootType = this.createDtoBySource(source);
        }

        var parent = this.findDtoBySource(source.getParent());
        if (source.getParent() != null && parent == null) {
            parent = this.createDtoBySource(source);
        }

        final var result = new ProviderType(
                rootType,
                parent,
                source.getCode(),
                source.getName()
        );
        this.providerTypesMemoryRepository.put(result);
        return result;
    }

    @Override
    public boolean isDestinationUpdatable(@NotNull ProviderType destination) {
        return false;
    }

    @Override
    public void updateDtoBySource(@NotNull ProviderType destination, @NotNull ProviderTypeEntity source) throws NotAllowedObjectUpdateException {
        throw new NotAllowedObjectUpdateException(ProviderType.class, null);
    }
}
