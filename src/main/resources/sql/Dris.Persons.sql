-- -----------------------------------------------------------------------------
-- Table: Dris.Persons
-- -----------------------------------------------------------------------------
-- DROP TABLE "Dris"."Persons" CASCADE
-- DROP SEQUENCE "Dris"."Persons:Sequence(Id)" CASCADE

CREATE SEQUENCE IF NOT EXISTS "Dris"."Persons:Sequence(Id)"
    INCREMENT BY 1
    MINVALUE 1
    START 201
    CACHE 1
    NO CYCLE;

ALTER SEQUENCE "Dris"."Persons:Sequence(Id)" OWNER TO gxfin;
CREATE TABLE IF NOT EXISTS "Dris"."Persons"
(
    "Id"            int             DEFAULT nextval('"Dris"."Persons:Sequence(Id)"')    NOT NULL,
    "Type_Id"       char(1)         COLLATE pg_catalog."default"                        NOT NULL,
    "NameInternal"  varchar(500)    COLLATE pg_catalog."default"                        NOT NULL,
    CONSTRAINT "Persons_pkey" PRIMARY KEY ("Id"),
    CONSTRAINT "Persons_fkey(Type_Id)" FOREIGN KEY ("Type_Id") REFERENCES "Dris"."Persons_Types" ("Id")
) TABLESPACE pg_default;

ALTER SEQUENCE "Dris"."Persons:Sequence(Id)" OWNED BY "Dris"."Persons"."Id";

ALTER TABLE "Dris"."Persons" OWNER to gxfin;

INSERT INTO "Dris"."Persons" ("Id", "Type_Id", "NameInternal")
VALUES
    (1,     'P',    'Гагаркин Владимир Геннадьевич'),
    (101,   'L',    'СПБ'),
    (102,   'L',    'МБ'),
    (111,   'L',    'Открытие Брокер'),
    (112,   'L',    'ВТБ'),
    (113,   'L',    'Тинькофф')
ON CONFLICT ("Id") DO UPDATE SET
    "Type_Id"       = EXCLUDED."Type_Id",
    "NameInternal"  = EXCLUDED."NameInternal";

-- -----------------------------------------------------------------------------
-- Table: Dris.Persons:Codes
-- -----------------------------------------------------------------------------
-- DROP TABLE "Dris"."Persons:Codes";

CREATE TABLE IF NOT EXISTS "Dris"."Persons:Codes"
(
    "Person_Id"     int                                       NOT NULL,
    "Provider_Id"   smallint                                  NOT NULL,
    "Index"         smallint                                  NOT NULL,
    "Code"          varchar(50) COLLATE pg_catalog."default"  NOT NULL,
    "Name"          varchar(250) COLLATE pg_catalog."default" NULL,
    "DateFrom"      date                                      NOT NULL,
    "DateTo"        date                                      NULL,
    CONSTRAINT "Persons:Codes_pk" PRIMARY KEY ("Person_Id", "Provider_Id", "Index"),
    CONSTRAINT "Persons:Codes_fk(Person_Id)" FOREIGN KEY ("Person_Id") REFERENCES "Dris"."Persons" ("Id"),
    CONSTRAINT "Persons:Codes_fk(Provider_Id)" FOREIGN KEY ("Provider_Id") REFERENCES "Dris"."Providers" ("Id")
) TABLESPACE pg_default;

ALTER TABLE "Dris"."Persons:Codes" OWNER to gxfin;

CREATE INDEX IF NOT EXISTS "Persons:Codes_ix(Provider_Id,Code,Index)+(ALL)"
    ON "Dris"."Persons:Codes" ("Provider_Id", "Code", "Index") INCLUDE ("DateFrom", "DateTo", "Name");

-- -----------------------------------------------------------------------------
-- Table: Dris.Persons:Guids
-- -----------------------------------------------------------------------------
-- DROP TABLE "Dris"."Persons:Guids";

CREATE TABLE IF NOT EXISTS "Dris"."Persons:Guids"
(
    "Person_Id"     int      NOT NULL,
    "Index"         smallint NOT NULL,
    "Guid"          uuid     NOT NULL,
    CONSTRAINT "Persons:Guids_pk" PRIMARY KEY ("Person_Id", "Index"),
    CONSTRAINT "Persons:Guids_fk(Person_Id)" FOREIGN KEY ("Person_Id") REFERENCES "Dris"."Persons" ("Id"),
    CONSTRAINT "Persons:Guids_uq(Guid)" UNIQUE ("Guid")
) TABLESPACE pg_default;

ALTER TABLE "Dris"."Persons:Guids" OWNER to gxfin;

INSERT INTO "Dris"."Persons:Guids" ("Person_Id", "Index", "Guid")
SELECT
    P."Id",
    1,
    uuid_generate_v4()
FROM "Dris"."Persons" AS P
WHERE   NOT EXISTS (
            SELECT * FROM "Dris"."Persons:Guids" AS G
            WHERE G."Person_Id" = P."Id"
        );