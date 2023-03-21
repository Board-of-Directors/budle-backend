drop table if exists spots cascade;

create table spots
(
    id               bigserial not null,
    establishment_id bigserial not null,
    local_id         int8      not null,
    primary key (id),
    foreign key (establishment_id) references establishments (id)
)