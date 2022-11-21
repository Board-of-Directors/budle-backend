drop table if exists tables;

create table tables(
    id bigserial not null,
    establishment_id bigserial not null,
    tags varchar(255),
    coordinates point,
    status varchar(1) not null,
    primary key (id),
    foreign key (establishment_id) references establishment_table(id)
)