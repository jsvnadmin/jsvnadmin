drop table if exists lang;

drop table if exists lang_lbl;

/*==============================================================*/
/* Table: lang                                                  */
/*==============================================================*/
create table lang
(
   lang                 varchar(20) not null,
   des                  varchar(128) not null,
   primary key (lang)
);

/*==============================================================*/
/* Table: lang_lbl                                              */
/*==============================================================*/
create table lang_lbl
(
   lang                 varchar(20) not null,
   id                  varchar(255) not null,
   lbl                  varchar(255) not null,
   primary key (lang, id)
);

alter table lang_lbl add constraint FK_Reference_12 foreign key (lang)
      references lang (lang) on delete restrict on update restrict;
