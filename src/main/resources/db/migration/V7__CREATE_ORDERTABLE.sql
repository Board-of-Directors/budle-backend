drop table if exists orders cascade;

create table orders
(
    id               bigserial not null,
    user_id          bigserial not null,
    establishment_id bigserial not null,
    spot_id          bigserial not null,
    date             timestamp not null,
    time             time      not null,
    status           int4      not null,
    guest_count      int       not null,
    primary key (id),
    foreign key (user_id) references users (id),
    foreign key (establishment_id) references establishments (id),
    foreign key (spot_id) references spots (id)

)