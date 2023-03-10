create table working_hours
(
    id               bigserial not null primary key,
    establishment_id bigserial not null,
    day_of_week      int4      not null,
    start_time       time,
    end_time         time,
    foreign key (establishment_id) references establishments (id)


)