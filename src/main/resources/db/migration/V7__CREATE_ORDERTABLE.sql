drop table if exists order_table;

create table order_table(
    order_id bigserial not null,
    user_id bigserial,
    table_id bigserial,
    primary key (order_id),
    foreign key (user_id) references user_table(id),
    foreign key (table_id) references tables(id)

)