package ru.gx.fin.common.dris.repository;

import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.gx.fin.common.dris.entities.InstrumentTypeEntity;
import ru.gx.fin.common.dris.entities.ProviderEntity;

import java.util.List;
import java.util.Optional;

@Repository
public interface InstrumentTypesRepository extends JpaRepository<InstrumentTypeEntity, Short> {
    @Query(value = """
            SELECT
                "Id",
                "RootType_Id",
                "Parent_Id",
                "Code",
                "NameShort",
                "NameFull"
            FROM "Dris"."Instruments_Types" AS  T
            ORDER BY
                T."Parent_Id" NULLS FIRST,
                T."Id"
            """, nativeQuery = true)
    @Override
    @NotNull
    List<InstrumentTypeEntity> findAll();

    Optional<InstrumentTypeEntity> findByCode(String code);
}
