/*==============================================================*/
/* Table: i18n                                                  */
/*==============================================================*/
create table i18n  (
   lang                 VARCHAR2(20)                    not null,
   id                   VARCHAR2(255)                   not null,
   lbl                  VARCHAR2(255)                   not null,
   constraint PK_I18N primary key (lang, id)
);