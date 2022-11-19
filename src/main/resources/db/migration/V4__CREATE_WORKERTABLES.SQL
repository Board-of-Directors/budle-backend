DROP TABLE IF EXISTS worker_table;
create table worker_table
(
    id          bigserial,
    user_id     bigserial  not null,
    worker_type varchar(1) not null,
    on_work     boolean,
    foreign key (user_id) references user_table,
    primary key (id)
);

DROP TABLE if exists worker_establishment;
create table worker_establishment
(
    worker_id        bigserial not null,
    establishment_id bigserial not null,
    foreign key (worker_id) references worker_table (id),
    foreign key (establishment_id) references establishment_table (id)
);