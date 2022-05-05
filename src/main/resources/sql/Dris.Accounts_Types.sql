-- Table: Dris.Accounts_Types
-- DROP TABLE "Dris"."Accounts_Types";

CREATE TABLE IF NOT EXISTS "Dris"."Accounts_Types"
(
    "Id"            smallint                                            NOT NULL,
    "Parent_Id"     smallint                                                NULL,
    "Code"          varchar(50)     COLLATE pg_catalog."default"        NOT NULL,
    "Name"          varchar(500)    COLLATE pg_catalog."default"            NULL,
    CONSTRAINT "Accounts_Types_pk" PRIMARY KEY ("Id"),
    CONSTRAINT "Accounts_Types_fk(Parent_Id)" FOREIGN KEY ("Parent_Id") REFERENCES "Dris"."Accounts_Types" ("Id"),
    CONSTRAINT "Accounts_Types_uq(Code)+(Name)" UNIQUE ("Code") INCLUDE ("Name")
) TABLESPACE pg_default;

ALTER TABLE "Dris"."Accounts_Types" OWNER to gxfin;

COMMENT ON TABLE "Dris"."Accounts_Types" IS 'Типы счетов';
COMMENT ON COLUMN "Dris"."Accounts_Types"."Parent_Id" IS 'Родительский тип';
COMMENT ON COLUMN "Dris"."Accounts_Types"."Code" IS 'Код';
COMMENT ON COLUMN "Dris"."Accounts_Types"."Name" IS 'Название';

INSERT INTO "Dris"."Accounts_Types" ("Id", "Parent_Id", "Code", "Name")
VALUES
    (1,     NULL,   'BANK',         'Расчетный счет'),
    (2,     NULL,   'BROK',         'Брокерский счет'),
    (3,     NULL,   'DEPO',         'Депозитарный счет'),
    (21,    2,      'BROK:CLIENT',  'Клиентский'),
    (22,    2,      'BROK:IIS',     'ИИС')
ON CONFLICT ("Id") DO UPDATE SET
    "Code" = EXCLUDED."Code",
    "Name" = EXCLUDED."Name";
