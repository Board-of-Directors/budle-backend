DROP TABLE if exists worker_establishment;
create table worker_establishment
(
    worker_id        bigserial not null,
    establishment_id bigserial not null,
    foreign key (worker_id) references worker_table (id),
    foreign key (establishment_id) references establishment_table (id)
);