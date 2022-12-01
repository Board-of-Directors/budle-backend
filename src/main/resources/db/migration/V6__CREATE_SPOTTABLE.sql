drop table if exists spots cascade;

create table spots(
    id bigserial not null,
    establishment_id bigserial not null,
    tags varchar(255),
    status varchar(1) not null,
    primary key (id),
    foreign key (establishment_id) references establishments(id)
)