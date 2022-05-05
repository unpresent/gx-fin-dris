-- Table: Dris.Persons_Types
-- DROP TABLE "Dris"."Persons_Types" CASCADE;

CREATE TABLE IF NOT EXISTS "Dris"."Persons_Types"
(
    "Id"            char(1)                                             NOT NULL,
    "Name"          varchar(500)    COLLATE pg_catalog."default"            NULL,
    CONSTRAINT "Persons_Types_pk" PRIMARY KEY ("Id")
) TABLESPACE pg_default;

ALTER TABLE "Dris"."Persons_Types" OWNER to gxfin;

INSERT INTO "Dris"."Persons_Types" ("Id", "Name")
VALUES
    ('P',   'Физическое лицо'),
    ('L',   'Юридическое лицо'),
    ('I',   'Инвестиционный фонд'),
    ('V',   'Виртаульное лицо')
ON CONFLICT ("Id") DO UPDATE SET
    "Name" = EXCLUDED."Name";
