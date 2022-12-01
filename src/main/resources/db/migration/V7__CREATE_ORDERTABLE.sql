drop table if exists orders;

create table orders(
    id bigserial not null,
    user_id bigserial not null,
    spot_id bigserial not null,
    primary key (id),
    foreign key (user_id) references users(id),
    foreign key (spot_id) references spots(id)

)