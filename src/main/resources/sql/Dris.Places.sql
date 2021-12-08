-- Table: Dris.Places

-- DROP TABLE "Dris"."Places";

CREATE TABLE IF NOT EXISTS "Dris"."Places"
(
    "Id"            smallint                                            NOT NULL,
    "Code"          varchar(50)     COLLATE pg_catalog."default"        NOT NULL,
    "Name"          varchar(500)    COLLATE pg_catalog."default"            NULL,
    CONSTRAINT "Places_Types_pkey" PRIMARY KEY ("Id")
) TABLESPACE pg_default;

ALTER TABLE "Dris"."Places" OWNER to gxfin;

INSERT INTO "Dris"."Places" ("Id", "Code", "Name")
VALUES
    (1,     'MOEX:SEC',     'ФР МБ'),
    (2,     'MOEX:CUR',     'ФР МБ'),
    (3,     'MOEX:DER',     'ФР МБ'),
    (5,     'SPB',          'СПБ')
ON CONFLICT ("Id") DO UPDATE SET
    "Code" = EXCLUDED."Code",
    "Name" = EXCLUDED."Name";
