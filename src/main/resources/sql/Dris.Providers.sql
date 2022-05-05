-- Table: Dris.Providers

-- DROP TABLE "Dris"."Providers";

CREATE TABLE IF NOT EXISTS "Dris"."Providers"
(
    "Id"            smallint                                            NOT NULL,
    "Parent_Id"     smallint                                                NULL,
    "Type_Id"       smallint                                            NOT NULL,
    "Code"          varchar(50)     COLLATE pg_catalog."default"        NOT NULL,
    "Name"          varchar(500)    COLLATE pg_catalog."default"            NULL,
    "Place_Id"      smallint                                                NULL,
    CONSTRAINT "Providers_pk" PRIMARY KEY ("Id"),
    CONSTRAINT "Providers_fk(Parent_Id)" FOREIGN KEY ("Parent_Id") REFERENCES "Dris"."Providers" ("Id"),
    CONSTRAINT "Providers_fk(Type_Id)" FOREIGN KEY ("Type_Id") REFERENCES "Dris"."Providers_Types" ("Id"),
    CONSTRAINT "Providers_fk(Place_Id)" FOREIGN KEY ("Place_Id") REFERENCES "Dris"."Places" ("Id"),
    CONSTRAINT "Providers_uq(Code)" UNIQUE ("Code")
) TABLESPACE pg_default;

ALTER TABLE "Dris"."Providers" OWNER to gxfin;

COMMENT ON TABLE "Dris"."Providers" IS 'Провайдеры (поставщики информации)';
COMMENT ON COLUMN "Dris"."Providers"."Parent_Id" IS 'Родительский провайдера (группа)';
COMMENT ON COLUMN "Dris"."Providers"."Type_Id" IS 'Тип провайдера';
COMMENT ON COLUMN "Dris"."Providers"."Code" IS 'Код';
COMMENT ON COLUMN "Dris"."Providers"."Name" IS 'Название';
COMMENT ON COLUMN "Dris"."Providers"."Place_Id" IS 'Площадка (одновременно и провайдер)';

INSERT INTO "Dris"."Providers" ("Id", "Parent_Id", "Type_Id", "Code", "Name", "Place_Id")
VALUES
    (1,     NULL,   1,  'MOEX',         'МБ',               1),
    (2,     1,      1,  'MOEX:SEC',     'ФР МБ',            2),
    (3,     1,      1,  'MOEX:CUR',     'ВР МБ',            3),
    (4,     1,      1,  'MOEX:DER',     'СР МБ',            3),
    (11,    NULL,   1,  'SPB',          'СПБ',              5),
    (21,    NULL,   2,  'QUIK:VTB',     'Quik ВТБ',         NULL),
    (22,    NULL,   2,  'QUIK:OPEN',    'Quik Открытие',    NULL)
ON CONFLICT ("Id") DO UPDATE SET
    "Parent_Id" = EXCLUDED."Parent_Id",
    "Type_Id" = EXCLUDED."Type_Id",
    "Code" = EXCLUDED."Code",
    "Name" = EXCLUDED."Name",
    "Place_Id" = EXCLUDED."Place_Id";
