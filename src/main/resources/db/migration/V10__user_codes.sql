drop table if exists user_codes;

create table user_codes(
    id bigserial,
    code varchar(4),
    phone_number varchar(100),
    created_at timestamp default current_timestamp
)