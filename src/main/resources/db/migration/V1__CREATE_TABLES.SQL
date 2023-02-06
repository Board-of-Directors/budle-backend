DROP TABLE IF EXISTS users cascade;

CREATE TABLE users
(
    id           bigserial,
    "name"       varchar(30) not null,
    phone_number varchar(30) not null unique,
    password     varchar     not null,
    primary key (id)

);

create type worker_status as ENUM ('absent', 'on_work');

DROP TABLE IF EXISTS workers cascade;
create table workers
(
    id          bigserial,
    user_id     bigserial     not null,
    worker_type varchar(1)    not null,
    status      worker_status not null,
    foreign key (user_id) references users (id),
    primary key (id)
);

DROP TABLE IF EXISTS establishments cascade;
create table establishments
(

    id               bigserial,
    "name"           varchar(100) NOT NULL,
    description      varchar,
    address          varchar      not null,
    has_map          boolean default false,
    has_card_payment boolean default false,
    owner_id         bigserial    not null,
    image            varchar,
    category         varchar(10)  not null,
    foreign key (owner_id) references users (id),
    primary key (id),
    unique ("name", address)
);

