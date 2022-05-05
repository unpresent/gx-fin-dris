-- Table: Dris.Instruments_Types

-- DROP TABLE "Dris"."Instruments_Types";

CREATE TABLE IF NOT EXISTS "Dris"."Instruments_Types"
(
    "Id"            smallint                                            NOT NULL,
    "RootType_Id"   smallint                                                NULL,
    "Parent_Id"     smallint                                                NULL,
    "Code"          varchar(50)     COLLATE pg_catalog."default"        NOT NULL,
    "NameShort"     varchar(100)    COLLATE pg_catalog."default"            NULL,
    "NameFull"      varchar(500)    COLLATE pg_catalog."default"            NULL,
    CONSTRAINT "Instruments_Types_pk" PRIMARY KEY ("Id"),
    CONSTRAINT "Instruments_Types_fk(Parent_Id)" FOREIGN KEY ("Parent_Id") REFERENCES "Dris"."Instruments_Types" ("Id"),
    CONSTRAINT "Instruments_Types_fk(Root_Id)" FOREIGN KEY ("RootType_Id") REFERENCES "Dris"."Instruments_Types" ("Id"),
    CONSTRAINT "Instruments_Types_uq(Code)" UNIQUE ("Code")
) TABLESPACE pg_default;

ALTER TABLE "Dris"."Instruments_Types" OWNER to gxfin;

COMMENT ON TABLE "Dris"."Instruments_Types" IS 'Типы счетов';
COMMENT ON COLUMN "Dris"."Instruments_Types"."Code" IS 'Код';
COMMENT ON COLUMN "Dris"."Instruments_Types"."RootType_Id" IS 'Корневой тип';
COMMENT ON COLUMN "Dris"."Instruments_Types"."Parent_Id" IS 'Родительский тип';
COMMENT ON COLUMN "Dris"."Instruments_Types"."NameShort" IS 'Название краткое';
COMMENT ON COLUMN "Dris"."Instruments_Types"."NameFull" IS 'Название полное';

INSERT INTO "Dris"."Instruments_Types" ("Id", "RootType_Id", "Parent_Id", "Code", "NameShort", "NameFull")
VALUES
    (1,     NULL,   NULL,   'CUR',              'Валюта',       'Валюта'),
    (2,     NULL,   NULL,   'SEC',              'ЦБ',           'Ценная бумага'),
    (3,     NULL,   NULL,   'DER',              'ПФИ',          'Дериватив'),
    (9,     NULL,   NULL,   'UNKNOWN',          'Unknown',      'Неопределённый'),
    (11,    2,      2,      'SEC:SHARE',        'Акция',        'Акция'),
    (12,    2,      11,     'SEC:SHARE:COM',    'АО',           'Акция обыкновенная'),
    (13,    2,      11,     'SEC:SHARE:PREF',   'АО',           'Акция привилегированная'),
    (21,    2,      2,      'SEC:BOND',         'Облигация',    'Облигация'),
    (31,    2,      2,      'SEC:DEP',          'ДР',           'Депозитарная расписка'),
    (201,   3,      3,      'DER:FUT',          'Фьючерс',      'Фьючерс'),
    (211,   3,      1,      'DER:OPT',          'Опцион',       'Опцион'),
    (212,   3,      211,    'DER:OPT:CALL',     'Call опцион',  'Call опцион'),
    (213,   3,      211,    'DER:OPT:PUT',      'Put опцион',   'Put опцион')
ON CONFLICT ("Id") DO UPDATE SET
    "RootType_Id" = EXCLUDED."RootType_Id",
    "Parent_Id" = EXCLUDED."Parent_Id",
    "Code" = EXCLUDED."Code",
    "NameShort" = EXCLUDED."NameShort",
    "NameFull" = EXCLUDED."NameFull";
