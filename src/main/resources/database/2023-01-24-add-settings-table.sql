create table app_settings(
    id serial primary key,
    name varchar(100) not null unique,
    value boolean not null
);

insert into app_settings(id,name,value) values (1,'automatically.insert.student.subjects.when.add', false);
insert into app_settings(id, name, value) values (2,'allow.to.create.student.accounts.independent', false);