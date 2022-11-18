DROP TABLE IF EXISTS user_table;

CREATE TABLE user_table
(
    id           bigserial,
    first_name   varchar(30) not null,
    last_name    varchar(30),
    phone_number varchar(30) not null unique,
    pass         varchar     not null,
    user_type    smallint    not null default 0,
    primary key (id)

);

DROP TABLE IF EXISTS establishment_table;
create table establishment_table
(

    id          bigserial,
    "name"      varchar(100) NOT NULL,
    description varchar,
    address     varchar      not null,
    owner_id    bigserial     not null references user_table (id),
    primary key (id)
);