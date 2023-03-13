INSERT INTO establishments (NAME, DESCRIPTION, ADDRESS, OWNER_ID, CATEGORY, rating, price, image, cuisine_country)
VALUES ('Горячий цех', 'Элитный ресторан в центре Новосибирска', 'Новосибирск, ул. Ильича 17', 11, 'restaurant', 5,
        1500, '/ceh.jpg', 1);
INSERT INTO establishments (NAME, DESCRIPTION, ADDRESS, OWNER_ID, CATEGORY, rating, price, image, cuisine_country)
VALUES ('Республика', 'Бар-ресторан в центре Новосибирска', 'Novosibirsk, Lenin St. 6', 11, 'restaurant', 4, 1000,
        '/resp.jpg', 1);



INSERT INTO establishments(NAME, description, address, owner_id, category, rating, price, image, stars_count)
values ('Мариотт', '5-звездочный ресторан в центре Новосибирска', 'Улица Пушкина, дом колотушкина', 11, 'hotel', 4,
        5000, '/mar.jpg', 5);

insert into establishments(name, description, address, has_map, has_card_payment, price, rating, owner_id, image,
                           category, stars_count)
values ('River Park', 'Комфортабельный отель на левом берегу', 'Самаракандийский проспект', true, false, 2500, 5, 11,
        '/river.jpg', 'hotel', 4);