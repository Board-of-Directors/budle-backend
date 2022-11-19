drop table if exists establishment_working_hours;

create table establishment_working_hours
(
    establishment_id bigserial  not null,
    day              varchar(3) not null,
    start_working    varchar(5) not null,
    end_working      varchar(5) not null,
    booking_duration varchar(5) not null,
    break_duration   varchar(5),
    foreign key (establishment_id) references establishment_table (id)

);