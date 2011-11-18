/*==============================================================*/
/* Table: i18n                                                  */
/*==============================================================*/
create table i18n (
   lang                 varchar(20)          not null,
   id                   varchar(255)         not null,
   lbl                  varchar(255)         not null,
   constraint PK_I18N primary key nonclustered (lang, id)
)
go
