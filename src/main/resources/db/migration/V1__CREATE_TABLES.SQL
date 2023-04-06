DROP TABLE IF EXISTS users cascade;

CREATE TABLE users
(
    id           bigserial,
    username     varchar(30) not null unique,
    phone_number varchar(30) not null unique,
    password     varchar     not null,
    primary key (id)

);


DROP TABLE IF EXISTS workers cascade;
create table workers
(
    id      bigserial,
    user_id bigserial not null,
    status  int4      not null default 0,
    foreign key (user_id) references users (id),
    primary key (id)
);

DROP TABLE IF EXISTS establishments cascade;
create table establishments
(

    id               bigserial,
    "name"           varchar(100) NOT NULL,
    description      varchar      not null,
    address          varchar      not null,
    has_map          boolean default false,
    has_card_payment boolean default false,
    price            int4         not null,
    rating           float        not null,
    owner_id         bigserial    not null,
    image            varchar      not null,
    map              varchar,
    category         varchar      not null,
    stars_count      integer,
    cuisine_country  int4,
    foreign key (owner_id) references users (id),
    primary key (id),
    unique ("name", address)
);

