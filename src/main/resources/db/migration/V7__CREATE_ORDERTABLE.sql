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
