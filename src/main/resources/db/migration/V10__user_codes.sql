drop table if exists user_codes cascade;

create type code_type as enum ('registration', 'booking');

create table user_codes
(
    id           bigserial,
    code         varchar(4),
    phone_number varchar(100),
    created_at   timestamp default now(),
    type         code_type not null
)