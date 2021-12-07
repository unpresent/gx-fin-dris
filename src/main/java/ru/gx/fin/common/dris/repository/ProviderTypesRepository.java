package ru.gx.fin.common.dris.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.gx.fin.common.dris.entities.ProviderTypeEntity;

import java.util.Optional;

@Repository
public interface ProviderTypesRepository extends JpaRepository<ProviderTypeEntity, Short> {
    Optional<ProviderTypeEntity> findByCode(String code);
}
