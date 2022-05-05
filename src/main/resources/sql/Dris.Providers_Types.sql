-- Table: Dris.Providers_Types

-- DROP TABLE "Dris"."Providers_Types";

CREATE TABLE IF NOT EXISTS "Dris"."Providers_Types"
(
    "Id"            smallint                                            NOT NULL,
    "RootType_Id"   smallint                                                NULL,
    "Parent_Id"     smallint                                                NULL,
    "Code"          varchar(50)     COLLATE pg_catalog."default"        NOT NULL,
    "Name"          varchar(500)    COLLATE pg_catalog."default"            NULL,
    CONSTRAINT "Providers_Types_pk" PRIMARY KEY ("Id"),
    CONSTRAINT "Providers_Types_fk(Parent_Id)" FOREIGN KEY ("Parent_Id") REFERENCES "Dris"."Providers_Types" ("Id"),
    CONSTRAINT "Providers_Types_fk(Root_Id)" FOREIGN KEY ("RootType_Id") REFERENCES "Dris"."Providers_Types" ("Id"),
    CONSTRAINT "Providers_Types_uq(Code)" UNIQUE ("Code")
) TABLESPACE pg_default;

ALTER TABLE "Dris"."Providers_Types" OWNER to gxfin;

COMMENT ON TABLE "Dris"."Providers_Types" IS 'Типы провайдеров';
COMMENT ON COLUMN "Dris"."Providers_Types"."RootType_Id" IS 'Корневой тип';
COMMENT ON COLUMN "Dris"."Providers_Types"."Parent_Id" IS 'Родительский тип';
COMMENT ON COLUMN "Dris"."Providers_Types"."Code" IS 'Код';
COMMENT ON COLUMN "Dris"."Providers_Types"."Name" IS 'Название';

INSERT INTO "Dris"."Providers_Types" ("Id", "RootType_Id", "Parent_Id", "Code", "Name")
VALUES
    (1, NULL, NULL, 'EX', 'Биржа'),
    (2, NULL, NULL, 'TS', 'Торговая система')
ON CONFLICT ("Id") DO UPDATE SET
    "RootType_Id" = EXCLUDED."RootType_Id",
    "Parent_Id" = EXCLUDED."Parent_Id",
    "Code" = EXCLUDED."Code",
    "Name" = EXCLUDED."Name";
