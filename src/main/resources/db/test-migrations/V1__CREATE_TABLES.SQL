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

DROP TABLE if exists worker_establishments cascade;

create table worker_establishments
(
    worker_id        bigserial not null,
    establishment_id bigserial not null,
    foreign key (worker_id) references workers (id),
    foreign key (establishment_id) references establishments (id)
);

drop table if exists establishment_working_hours cascade;

create table establishment_working_hours
(
    establishment_id bigserial  not null,
    day              varchar(3) not null,
    start_working    varchar(5) not null,
    end_working      varchar(5) not null,
    booking_duration varchar(5) not null,
    break_duration   varchar(5),
    foreign key (establishment_id) references establishments (id)

);

drop table if exists spots cascade;

create table spots
(
    id               bigserial not null,
    establishment_id bigserial not null,
    local_id         int8      not null,
    primary key (id),
    foreign key (establishment_id) references establishments (id)
);

drop table if exists orders_with_map cascade;
drop table if exists orders cascade;


create table orders
(
    id               bigserial   not null,
    user_id          bigserial   not null,
    establishment_id bigserial   not null,
    spot_id          bigint,
    date             date        not null,
    start_time       time        not null,
    end_time         time        not null,
    status           int4 default 0,
    guest_count      int         not null,
    primary key (id),
    foreign key (user_id) references users (id),
    foreign key (establishment_id) references establishments (id)
);

drop table if exists user_codes cascade;

create type code_type as enum ('registration', 'booking');

create table user_codes
(
    id           bigserial,
    code         varchar(4),
    phone_number varchar(100),
    created_at   timestamp default now(),
    type         varchar not null
);

CREATE TABLE role
(
    id   bigserial PRIMARY KEY,
    name VARCHAR(255) UNIQUE NOT NULL
);

CREATE TABLE account_role
(
    id      INT NOT NULL,
    role_id INT NOT NULL,
    PRIMARY KEY (id, role_id),
    FOREIGN KEY (role_id) REFERENCES role (id),
    FOREIGN KEY (id) REFERENCES users (id)
);

INSERT INTO role (name)
VALUES ('ROLE_ADMIN');
INSERT INTO role (name)
VALUES ('ROLE_USER');
INSERT INTO role (name)
VALUES ('ROLE_WORKER');
INSERT INTO role (name)
VALUES ('ROLE_OWNER');


create table working_hours
(
    id               bigserial not null primary key,
    establishment_id bigserial not null,
    day_of_week      int4      not null,
    start_time       time,
    end_time         time,
    foreign key (establishment_id) references establishments (id)


);


create table establishment_tags
(
    establishment_id bigserial references establishments (id),
    tag_name         varchar not null,
    primary key (establishment_id, tag_name)
);


create table photo
(
    id               bigserial primary key,
    establishment_id bigserial references establishments (id),
    filename         varchar not null
);