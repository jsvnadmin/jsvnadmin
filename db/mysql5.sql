/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     2013/1/16 14:32:28                           */
/*==============================================================*/


/*==============================================================*/
/* Table: i18n                                                  */
/*==============================================================*/
create table i18n
(
   lang                 varchar(20) not null,
   id                   varchar(200) not null,
   lbl                  varchar(200) not null,
   primary key (lang, id)
);

/*==============================================================*/
/* Table: pj                                                    */
/*==============================================================*/
create table pj
(
   pj                   varchar(50) not null,
   path                 varchar(200) not null,
   url                  varchar(200) not null,
   type                 varchar(10) not null,
   des                  varchar(200),
   primary key (pj)
);

/*==============================================================*/
/* Table: pj_gr                                                 */
/*==============================================================*/
create table pj_gr
(
   pj                   varchar(50) not null,
   gr                   varchar(50) not null,
   des                  varchar(100),
   primary key (pj, gr)
);

/*==============================================================*/
/* Table: pj_gr_auth                                            */
/*==============================================================*/
create table pj_gr_auth
(
   pj                   varchar(50) not null,
   gr                   varchar(50) not null,
   res                  varchar(200) not null,
   rw                   varchar(10),
   primary key (pj, res, gr)
);

/*==============================================================*/
/* Table: pj_gr_usr                                             */
/*==============================================================*/
create table pj_gr_usr
(
   pj                   varchar(50) not null,
   gr                   varchar(50) not null,
   usr                  varchar(50) not null,
   primary key (pj, usr, gr)
);

/*==============================================================*/
/* Table: pj_usr                                                */
/*==============================================================*/
create table pj_usr
(
   pj                   varchar(50) not null,
   usr                  varchar(50) not null,
   psw                  varchar(50) not null,
   primary key (usr, pj)
);

/*==============================================================*/
/* Table: pj_usr_auth                                           */
/*==============================================================*/
create table pj_usr_auth
(
   pj                   varchar(50) not null,
   usr                  varchar(50) not null,
   res                  varchar(200) not null,
   rw                   varchar(10),
   primary key (pj, res, usr)
);

/*==============================================================*/
/* Table: usr                                                   */
/*==============================================================*/
create table usr
(
   usr                  varchar(50) not null,
   psw                  varchar(50) not null,
   name                 varchar(50),
   role                 varchar(10),
   primary key (usr)
);

alter table pj_gr add constraint FK_Relationship_2 foreign key (pj)
      references pj (pj) on delete restrict on update restrict;

alter table pj_gr_auth add constraint FK_Reference_6 foreign key (pj, gr)
      references pj_gr (pj, gr) on delete restrict on update restrict;

alter table pj_gr_usr add constraint FK_Reference_10 foreign key (pj, gr)
      references pj_gr (pj, gr) on delete restrict on update restrict;

alter table pj_gr_usr add constraint FK_Reference_9 foreign key (usr)
      references usr (usr) on delete restrict on update restrict;

alter table pj_usr add constraint FK_Reference_5 foreign key (pj)
      references pj (pj) on delete restrict on update restrict;

alter table pj_usr add constraint FK_Reference_7 foreign key (usr)
      references usr (usr) on delete restrict on update restrict;

alter table pj_usr_auth add constraint FK_Reference_11 foreign key (pj)
      references pj (pj) on delete restrict on update restrict;

alter table pj_usr_auth add constraint FK_Reference_8 foreign key (usr)
      references usr (usr) on delete restrict on update restrict;

