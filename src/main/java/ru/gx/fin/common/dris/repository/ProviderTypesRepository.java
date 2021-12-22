package ru.gx.fin.common.dris.repository;

import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.gx.fin.common.dris.entities.ProviderEntity;
import ru.gx.fin.common.dris.entities.ProviderTypeEntity;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProviderTypesRepository extends JpaRepository<ProviderTypeEntity, Short> {
    @Query(value = """
            SELECT
                "Id",
                "RootType_Id",
                "Parent_Id",
                "Code",
                "Name"
            FROM "Dris"."Providers_Types" AS T
            ORDER BY
                T."Parent_Id" NULLS FIRST,
                T."Id"
            """, nativeQuery = true)
    @Override
    @NotNull
    List<ProviderTypeEntity> findAll();

    Optional<ProviderTypeEntity> findByCode(String code);
}
