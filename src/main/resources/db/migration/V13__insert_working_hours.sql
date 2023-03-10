insert into working_hours(id, establishment_id, day_of_week, start_time, end_time, around_the_clock, break_time)
values (1, 1, 0, '12:00', '13:00', false, '15:00');

insert into working_hours(id, establishment_id, day_of_week, around_the_clock)
values (2, 1, 2, true);