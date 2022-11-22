drop table if exists tables;

create table spot_table(
    id bigserial not null,
    establishment_id bigserial not null,
    tags varchar(255),
    status varchar(1) not null,
    primary key (id),
    foreign key (establishment_id) references establishment_table(id)
)