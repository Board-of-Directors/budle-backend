DROP TABLE IF EXISTS users;

CREATE TABLE users
(
    id           bigserial,
    first_name   varchar(30) not null,
    last_name    varchar(30),
    phone_number varchar(30) not null unique,
    pass         varchar     not null,
    primary key (id)

);

DROP TABLE IF EXISTS workers;
create table workers
(
    id          bigserial,
    user_id     bigserial  not null,
    worker_type varchar(1) not null,
    on_work     boolean    not null,
    foreign key (user_id) references users (id),
    primary key (id)
);

DROP TABLE IF EXISTS establishments;
create table establishments
(

    id          bigserial,
    "name"      varchar(100) NOT NULL,
    description varchar,
    address     varchar      not null,
    has_map     boolean default false,
    has_card_payment boolean default false,
    owner_id    bigserial    not null,
    foreign key (owner_id) references users (id),
    primary key (id)
);

