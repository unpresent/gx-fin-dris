-- Table: Dris.Accounts:Parts_Types
-- DROP TABLE "Dris"."Accounts:Parts_Types";

CREATE TABLE IF NOT EXISTS "Dris"."Accounts:Parts_Types"
(
    "Id"            smallint                                            NOT NULL,
    "Parent_Id"     smallint                                                NULL,
    "Code"          varchar(50)     COLLATE pg_catalog."default"        NOT NULL,
    "Name"          varchar(500)    COLLATE pg_catalog."default"            NULL,
    CONSTRAINT "Accounts:Parts_Types_pk" PRIMARY KEY ("Id"),
    CONSTRAINT "Accounts:Parts_Types_fk(Parent_Id)" FOREIGN KEY ("Parent_Id") REFERENCES "Dris"."Instruments_Types" ("Id"),
    CONSTRAINT "Accounts:Parts_Types_uq(Code)+(Name)" UNIQUE ("Code") INCLUDE ("Name")
) TABLESPACE pg_default;

ALTER TABLE "Dris"."Accounts:Parts_Types" OWNER to gxfin;

COMMENT ON TABLE "Dris"."Accounts:Parts_Types" IS 'Типы разделов счетов';
COMMENT ON COLUMN "Dris"."Accounts:Parts_Types"."Parent_Id" IS 'Родительский тип';
COMMENT ON COLUMN "Dris"."Accounts:Parts_Types"."Code" IS 'Код';
COMMENT ON COLUMN "Dris"."Accounts:Parts_Types"."Name" IS 'Название';

INSERT INTO "Dris"."Accounts:Parts_Types" ("Id", "Parent_Id", "Code", "Name")
VALUES
    (1,     NULL,   'UNI',          'Единый брокерский счёт'),
    (2,     NULL,   'STOCK',        'Фондовый рынок'),
    (3,     NULL,   'DER',          'Срочный рынок'),
    (4,     NULL,   'CUR',          'Валютный рынок'),
    (5,     NULL,   'OTC',          'Внебиржевой'),
    (11,    1,      'UNI:VTB',      'ВТБ'),
    (12,    1,      'UNI:OPEN',     'Открытие')
ON CONFLICT ("Id") DO UPDATE SET
    "Code" = EXCLUDED."Code",
    "Name" = EXCLUDED."Name";
