-- -----------------------------------------------------------------------------
-- Table: Dris.Accounts:Parts
-- -----------------------------------------------------------------------------
-- DROP TABLE "Dris"."Accounts:Parts" CASCADE
-- DROP SEQUENCE "Dris"."Accounts:Parts:Sequence(Id)" CASCADE

CREATE SEQUENCE IF NOT EXISTS "Dris"."Accounts:Parts:Sequence(Id)"
    INCREMENT BY 1
    MINVALUE 1
    START 201
    CACHE 1
    NO CYCLE;

ALTER SEQUENCE "Dris"."Accounts:Parts:Sequence(Id)" OWNER TO gxfin;
CREATE TABLE IF NOT EXISTS "Dris"."Accounts:Parts"
(
    "Id"                int             DEFAULT nextval('"Dris"."Accounts:Parts:Sequence(Id)"')     NOT NULL,
    "Account_Id"        int                                                                         NOT NULL,
    "Type_Id"           smallint                                                                    NOT NULL,
    "Number"            varchar(100)    COLLATE pg_catalog."default"                                NOT NULL,
    CONSTRAINT "Accounts:Parts_pkey" PRIMARY KEY ("Id"),
    CONSTRAINT "Accounts:Parts_fkey(Account_Id)" FOREIGN KEY ("Account_Id") REFERENCES "Dris"."Accounts" ("Id"),
    CONSTRAINT "Accounts:Parts_fkey(Type_Id)" FOREIGN KEY ("Type_Id") REFERENCES "Dris"."Accounts:Parts_Types" ("Id"),
    CONSTRAINT "Accounts:Parts_uq(Account_Id,Type_Id,Number)" UNIQUE ("Account_Id", "Type_Id", "Number")
) TABLESPACE pg_default;

ALTER SEQUENCE "Dris"."Accounts:Parts:Sequence(Id)" OWNED BY "Dris"."Accounts:Parts"."Id";

ALTER TABLE "Dris"."Accounts:Parts" OWNER to gxfin;

INSERT INTO "Dris"."Accounts:Parts" ("Id", "Account_Id", "Type_Id", "Number")
VALUES
    (11,    1,      11,     'Основной'),
    (11,    1,      3,      'СР МБ'),

    (21,    11,     2,      'ФР СПБ'),
    (22,    11,     2,      'ФР МБ'),
    (23,    11,     3,      'СР МБ'),
    (24,    11,     4,      'ВР МБ'),
    (25,    11,     5,      'ВНБ'),

    (31,    12,     12,     'UNI'),

    (41,    11,     2,      'ФР СПБ'),
    (42,    11,     2,      'ФР МБ'),
    (43,    11,     3,      'СР МБ'),
    (44,    11,     4,      'ВР МБ'),
    (45,    11,     5,      'ВНБ')
ON CONFLICT ("Id") DO UPDATE SET
    "Account_Id"        = EXCLUDED."Account_Id",
    "Type_Id"           = EXCLUDED."Type_Id",
    "Number"            = EXCLUDED."Number";

-- -----------------------------------------------------------------------------
-- Table: Dris.Accounts:Codes
-- -----------------------------------------------------------------------------
-- DROP TABLE "Dris"."Accounts:Codes";

CREATE TABLE IF NOT EXISTS "Dris"."Accounts:Parts:Codes"
(
    "Account_Id"    int                                       NOT NULL,
    "Provider_Id"   smallint                                  NOT NULL,
    "Index"         smallint                                  NOT NULL,
    "Code"          varchar(50) COLLATE pg_catalog."default"  NOT NULL,
    "Name"          varchar(250) COLLATE pg_catalog."default" NULL,
    "DateFrom"      date                                      NOT NULL,
    "DateTo"        date                                      NULL,
    CONSTRAINT "Accounts:Codes_pk" PRIMARY KEY ("Account_Id", "Provider_Id", "Index"),
    CONSTRAINT "Accounts:Codes_fk(Account_Id)" FOREIGN KEY ("Account_Id") REFERENCES "Dris"."Accounts" ("Id"),
    CONSTRAINT "Accounts:Codes_fk(Provider_Id)" FOREIGN KEY ("Provider_Id") REFERENCES "Dris"."Providers" ("Id")
) TABLESPACE pg_default;

ALTER TABLE "Dris"."Accounts:Parts:Codes" OWNER to gxfin;

CREATE INDEX IF NOT EXISTS "Accounts:Parts:Codes_ix(Provider_Id,Code,Index)+(ALL)"
    ON "Dris"."Accounts:Parts:Codes" ("Provider_Id", "Code", "Index") INCLUDE ("DateFrom", "DateTo", "Name");

-- -----------------------------------------------------------------------------
-- Table: Dris.Accounts:Parts:Guids
-- -----------------------------------------------------------------------------
-- DROP TABLE "Dris"."Accounts:Parts:Guids";

CREATE TABLE IF NOT EXISTS "Dris"."Accounts:Parts:Guids"
(
    "AccountPart_Id"    int      NOT NULL,
    "Index"             smallint NOT NULL,
    "Guid"              uuid     NOT NULL,
    CONSTRAINT "Accounts:Parts:Guids_pk" PRIMARY KEY ("AccountPart_Id", "Index"),
    CONSTRAINT "Accounts:Parts:Guids_fk(Accounts_Id)" FOREIGN KEY ("AccountPart_Id") REFERENCES "Dris"."Accounts:Parts" ("Id")
) TABLESPACE pg_default;

ALTER TABLE "Dris"."Accounts:Parts:Guids" OWNER to gxfin;

INSERT INTO "Dris"."Accounts:Parts:Guids" ("AccountPart_Id", "Index", "Guid")
SELECT
    P."Id",
    1,
    uuid_generate_v4()
FROM "Dris"."Accounts:Parts" AS P
WHERE   NOT EXISTS (
            SELECT * FROM "Dris"."Accounts:Parts:Guids" AS G
            WHERE G."AccountPart_Id" = P."Id"
        );