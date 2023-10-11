create table menu_category
(
    id                 bigserial primary key,
    name               varchar,
    establishment_id   bigint references establishments (id),
    parent_category_id bigint references menu_category (id)
);


create table product
(
    id          bigserial primary key,
    name        varchar,
    description varchar,
    price       varchar,
    weight_g    varchar,
    is_on_sale  bool,
    category_id bigint references menu_category (id)
)