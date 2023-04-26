INSERT INTO establishments (NAME, DESCRIPTION, ADDRESS, OWNER_ID, CATEGORY, rating, price, image, cuisine_country)
VALUES ('Горячий цех', 'Элитный ресторан в центре Новосибирска', 'Новосибирск, ул. Ильича 17', 11, 'restaurant', 5,
        1500, '/ceh.jpg', 2),
       ('Республика', 'Бар-ресторан в центре Новосибирска', 'Новосибирск, ул.Ленина 6', 11, 'restaurant', 4, 1000,
        '/resp.jpg', 4),
       ('Maverick',
        'Бар-первопроходчик в своем деле, вкусные бургеры, веселое настроение, крутая живая музыка! Мы самые лучшие!',
        'Новосибирск, ул.Кошурникова 49', 11, 'restaurant', 2.9,
        1900, '/mav.jpg', 1),
       ('Весна', 'Бар с дешевыми настойками и зубодробительными коктейлями! По праздникам скидка 25%!',
        'Санкт-Петербург, ул. Галкина 69', 11, 'restaurant', 4.9, 500,
        '/vesna.jpg', 3);

insert into establishments(name, description, address, has_map, has_card_payment, price, rating, owner_id, image,
                           category, stars_count)
values ('СталиHotel', 'Бюджетный отель с видом на пятиэтажки', 'г. Австралийск, Самурайская улица, дом 10', true, false,
        1800, 3.5, 11,
        '/naut.jpg', 'hotel', 3),
       ('Отель Элеон', 'Выдуманный отель из Сериала на СТС', 'г. Москва, ул. Пушкинская, д.49', true, false, 5000, 4.99,
        11,
        '/eleon.jpg', 'hotel', 4),
       ('Park Inn By Radison', 'Самый необычный отель, с божественным видом из окна, прекрасными пейзажами,' ||
                               ' волшебными сотрудниками и идеальными номерами. ', 'г. Калуга, ул. Рокенролла, д.45',
        true, false, 10000, 4.55, 11,
        '/inn.jpg', 'hotel', 3),
       ('Парк отель шишка', 'Лесной отель, незабываемые ощущения, свежий воздух, атмосферные места! Бронируйте! ',
        'г. Новосибирск, ул. Заельцовская', true, false, 699, 4.9, 11,
        '/shish.jpg', 'hotel', 4),
       ('Мариотт', '5-звездочный ресторан в центре Новосибирска', 'Улица Пушкина, дом колотушкина', true, false, 500,
        4.4, 1,
        '/mar.jpg', 'hotel', 5);



insert into establishments(name, description, address, has_map, has_card_payment, price, rating, owner_id, image,
                           category)
values ('Апельсин', 'Самые крутые прически - только у нас. Цены дешевле чем при коммунизме!',
        'г. Москва, ул. Мартина, д.491', true, false, 600, 4.87,
        11,
        '/orange.jpg', 'barbershop');

insert into establishments(name, description, address, has_map, has_card_payment, price, rating, owner_id, image,
                           category)
values ('Папа дома', 'Барбершоп для крутых мужиков! Виски, кола, королева танцпола!',
        'г. Трепета, ул. классической музыки, д.45',
        true, false, 124, 4.65, 11,
        '/papa.jpg', 'barbershop');

insert into establishments(name, description, address, has_map, has_card_payment, price, rating, owner_id, image,
                           category)
values ('Цирюльникъ', 'Самые дешевые цены в Новосибирске, профессиональные парикмахеры, адски-крутое обслуживание!',
        'г. Новосибирск, ул. Заельцовская', true, false, 699, 4.9, 11,
        '/cir.jpg', 'barbershop');



insert into establishments(name, description, address, has_map, has_card_payment, price, rating, owner_id, image,
                           category)
values ('GameXSCopeR',
        'CS:GO, Dota2, PUBG, Mobile Legends - если узнал что-то из этого, то приходи к нам, будем играть в крутые игры!',
        'г. Новосибирск, ул. Дзержинского, д.491', true, false, 200, 4.17,
        11,
        '/scope.jpg', 'game_club');

insert into establishments(name, description, address, has_map, has_card_payment, price, rating, owner_id, image,
                           category)
values ('VR Shape',
        'Самые крутые ощущения - только у нас! Поиграй в хорроры, покатайся на каруселях, сделай что угодно!',
        'г. Тверь, ул. Чехова, д.45',
        true, false, 333, 4.10, 11,
        '/vr.jpg', 'game_club');

insert into establishments(name, description, address, has_map, has_card_payment, price, rating, owner_id, image,
                           category)
values ('MasterGameDevStudio', 'Попробуй себя в роли разработчика! Попробуй SQL, C++, и возможно даже что-то покрепче!',
        'г. Новосибирск, ул. Трегубова 99', true, false, 111, 2.9, 11,
        '/dev.jpg', 'game_club');