DROP TABLE if exists worker_establishments cascade;

create table worker_establishments
(
    worker_id        bigserial not null,
    establishment_id bigserial not null,
    foreign key (worker_id) references workers (id),
    foreign key (establishment_id) references establishments (id)
);