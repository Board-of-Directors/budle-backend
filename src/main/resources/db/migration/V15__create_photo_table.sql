create table photo
(
    id               bigserial primary key,
    establishment_id bigserial references establishments (id),
    filename         varchar not null
)