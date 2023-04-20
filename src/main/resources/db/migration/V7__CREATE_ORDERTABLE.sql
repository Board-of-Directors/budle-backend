drop table if exists orders_with_map cascade;
drop table if exists orders cascade;


create table orders
(
    id               bigserial   not null,
    user_id          bigserial   not null,
    establishment_id bigserial   not null,
    spot_id          bigserial,
    date             timestamp   not null,
    time             time        not null,
    status           int4 default 0,
    guest_count      int         not null,
    dtype            varchar(31) not null,
    primary key (id),
    foreign key (user_id) references users (id),
    foreign key (establishment_id) references establishments (id)
);
