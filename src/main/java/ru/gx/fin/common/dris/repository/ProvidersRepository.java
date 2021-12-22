package ru.gx.fin.common.dris.repository;

import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.gx.fin.common.dris.entities.ProviderEntity;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProvidersRepository extends JpaRepository<ProviderEntity, Short> {
    @Query(value = """
            SELECT
                "Id",
                "Parent_Id",
                "Type_Id",
                "Code",
                "Name",
                "Place_Id"
            FROM "Dris"."Providers" AS T
            ORDER BY
                T."Parent_Id" NULLS FIRST,
                T."Id"
            """, nativeQuery = true)
    @Override
    @NotNull
    List<ProviderEntity> findAll();

    Optional<ProviderEntity> findByCode(String code);
}
