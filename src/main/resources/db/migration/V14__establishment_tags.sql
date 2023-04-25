create table establishment_tags
(
    establishment_id bigserial references establishments (id),
    tag_name         varchar not null,
    primary key (establishment_id, tag_name)
);


insert into establishment_tags(establishment_id, tag_name)
values (1, 'power'),
       (1, 'wifi'),
       (1, 'quite'),
       (1, 'kitchen'),
       (1, 'dance'),
       (1, 'television'),
       (2, 'kitchen'),
       (3, 'dance'),
       (4, 'television'),
       (5, 'power'),
       (6, 'power'),
       (7, 'power'),
       (8, 'kitchen'),
       (9, 'wifi'),
       (10, 'quite'),
       (11, 'quite'),
       (12, 'dance')