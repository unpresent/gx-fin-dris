package ru.gx.fin.common.dris.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.gx.fin.common.dris.entities.PlaceEntity;

import java.util.Optional;

@Repository
public interface PlacesRepository extends JpaRepository<PlaceEntity, Short> {
    Optional<PlaceEntity> findByCode(String code);
}
