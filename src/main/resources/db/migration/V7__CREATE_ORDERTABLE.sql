drop table if exists orders;

create table orders(
    order_id bigserial not null,
    user_id bigserial not null,
    table_id bigserial not null,
    primary key (order_id),
    foreign key (user_id) references users(id),
    foreign key (table_id) references spots(id)

)