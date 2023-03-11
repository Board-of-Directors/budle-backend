create table establishment_tags
(
    establishment_id bigserial references establishments (id),
    tag_name         varchar not null,
    primary key (establishment_id, tag_name)
)