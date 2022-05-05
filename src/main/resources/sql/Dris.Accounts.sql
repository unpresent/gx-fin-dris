-- -----------------------------------------------------------------------------
-- Table: Dris.Accounts
-- -----------------------------------------------------------------------------
-- DROP TABLE "Dris"."Accounts" CASCADE
-- DROP SEQUENCE "Dris"."Accounts:Sequence(Id)" CASCADE

CREATE SEQUENCE IF NOT EXISTS "Dris"."Accounts:Sequence(Id)"
    INCREMENT BY 1
    MINVALUE 1
    START 201
    CACHE 1
    NO CYCLE;

ALTER SEQUENCE "Dris"."Accounts:Sequence(Id)" OWNER TO gxfin;
CREATE TABLE IF NOT EXISTS "Dris"."Accounts"
(
    "Id"                int             DEFAULT nextval('"Dris"."Accounts:Sequence(Id)"')   NOT NULL,
    "Type_Id"           smallint                                                            NOT NULL,
    "Organization_Id"   int                                                                     NULL,
    "Owner_Id"          int                                                                 NOT NULL,
    "NameInternal"      varchar(200)    COLLATE pg_catalog."default"                        NOT NULL,
    "Number"            varchar(100)    COLLATE pg_catalog."default"                        NOT NULL,
    CONSTRAINT "Accounts_pk" PRIMARY KEY ("Id"),
    CONSTRAINT "Accounts_fk(Type_Id)" FOREIGN KEY ("Type_Id") REFERENCES "Dris"."Accounts_Types" ("Id"),
    CONSTRAINT "Accounts_fk(Organization_Id)" FOREIGN KEY ("Organization_Id") REFERENCES "Dris"."Persons" ("Id"),
    CONSTRAINT "Accounts_fk(Owner_Id)" FOREIGN KEY ("Owner_Id") REFERENCES "Dris"."Persons" ("Id"),
    CONSTRAINT "Accounts_uq(Type_Id,Organization_Id,Number)" UNIQUE ("Type_Id", "Organization_Id", "Number")
) TABLESPACE pg_default;

ALTER SEQUENCE "Dris"."Accounts:Sequence(Id)" OWNED BY "Dris"."Accounts"."Id";

ALTER TABLE "Dris"."Accounts" OWNER to gxfin;

INSERT INTO "Dris"."Accounts" ("Id", "Type_Id", "Organization_Id", "Owner_Id", "NameInternal", "Number")
VALUES
    (1,     21,     112,    1,  'ВТБ-1',        '1025CU'),
    (11,    21,     111,    1,  'Открытие-1',   '88418'),
    (12,    22,     111,    1,  'Открытие-ИИС', '88418i'),
    (13,    21,     111,    1,  'Открытие-2',   '201239')
ON CONFLICT ("Id") DO UPDATE SET
    "Type_Id"           = EXCLUDED."Type_Id",
    "Organization_Id"   = EXCLUDED."Organization_Id",
    "Owner_Id"          = EXCLUDED."Owner_Id",
    "NameInternal"      = EXCLUDED."NameInternal",
    "Number"            = EXCLUDED."Number";

-- -----------------------------------------------------------------------------
-- Table: Dris.Accounts:Codes
-- -----------------------------------------------------------------------------
-- DROP TABLE "Dris"."Accounts:Codes";

CREATE TABLE IF NOT EXISTS "Dris"."Accounts:Codes"
(
    "Account_Id"    int                                       NOT NULL,
    "Provider_Id"   smallint                                  NOT NULL,
    "Index"         smallint                                  NOT NULL,
    "Code"          varchar(50) COLLATE pg_catalog."default"  NOT NULL,
    "Name"          varchar(250) COLLATE pg_catalog."default"     NULL,
    "DateFrom"      date                                      NOT NULL,
    "DateTo"        date                                          NULL,
    CONSTRAINT "Accounts:Codes_pk" PRIMARY KEY ("Account_Id", "Provider_Id", "Index"),
    CONSTRAINT "Accounts:Codes_fk(Account_Id)" FOREIGN KEY ("Account_Id") REFERENCES "Dris"."Accounts" ("Id"),
    CONSTRAINT "Accounts:Codes_fk(Provider_Id)" FOREIGN KEY ("Provider_Id") REFERENCES "Dris"."Providers" ("Id")
) TABLESPACE pg_default;

ALTER TABLE "Dris"."Accounts:Codes" OWNER to gxfin;

CREATE INDEX IF NOT EXISTS "Accounts:Codes_ix(Provider_Id,Code,Index)+(ALL)"
ON "Dris"."Accounts:Codes" ("Provider_Id", "Code", "Index") INCLUDE ("DateFrom", "DateTo", "Name");

-- -----------------------------------------------------------------------------
-- Table: Dris.Accounts:Guids
-- -----------------------------------------------------------------------------
-- DROP TABLE "Dris"."Accounts:Guids";

CREATE TABLE IF NOT EXISTS "Dris"."Accounts:Guids"
(
    "Account_Id"    int      NOT NULL,
    "Index"         smallint NOT NULL,
    "Guid"          uuid     NOT NULL,
    CONSTRAINT "Accounts:Guids_pk" PRIMARY KEY ("Account_Id", "Index"),
    CONSTRAINT "Accounts:Guids_fk(Accounts_Id)" FOREIGN KEY ("Account_Id") REFERENCES "Dris"."Accounts" ("Id"),
    CONSTRAINT "Accounts:Guids_uq(Guid)" UNIQUE ("Guid")
) TABLESPACE pg_default;

ALTER TABLE "Dris"."Accounts:Guids" OWNER to gxfin;

INSERT INTO "Dris"."Accounts:Guids" ("Account_Id", "Index", "Guid")
SELECT
    P."Id",
    1,
    uuid_generate_v4()
FROM "Dris"."Accounts" AS P
WHERE   NOT EXISTS (
            SELECT * FROM "Dris"."Accounts:Guids" AS G
            WHERE G."Account_Id" = P."Id"
        );