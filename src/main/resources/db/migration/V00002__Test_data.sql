-- Забивка данных в таблицу Enclosures
INSERT INTO Enclosures (size, temperature, description)
VALUES
    (500.25, 22.5, 'Тропическая зона для приматов'),
    (1000.00, 15.0, 'Зона для хищников'),
    (250.50, 24.0, 'Зона для рептилий'),
    (400.75, 18.5, 'Зона для птиц'),
    (300.00, 20.0, 'Зона для мелких млекопитающих');

-- Забивка данных в таблицу Animals
INSERT INTO Animals (name, species, date_of_birth, arrival_date, origin, enclosure_id)
VALUES
    ('Лео', 'Лев', '2015-03-15', '2018-06-20', 'Саванна, Африка', 2),
    ('Тина', 'Тигр', '2017-05-10', '2019-09-05', 'Тайга, Россия', 2),
    ('Коко', 'Горилла', '2012-12-05', '2016-03-12', 'Тропический лес, Африка', 1),
    ('Рекс', 'Крокодил', '2010-07-20', '2013-11-15', 'Нил, Африка', 3),
    ('Петя', 'Попугай ара', '2019-01-25', '2020-08-18', 'Амазонка, Южная Америка', 4);

-- Забивка данных в таблицу AnimalDiets
INSERT INTO AnimalDiets (animal_id, diet_type)
VALUES
    (1, 'Мясо'),
    (2, 'Мясо'),
    (3, 'Фрукты, овощи'),
    (4, 'Рыба, мясо'),
    (5, 'Семена, фрукты');

-- Забивка данных в таблицу Staff
INSERT INTO Staff (first_name, last_name, position, assigned_enclosure_id)
VALUES
    ('Иван', 'Петров', 'Уход за хищниками', 2),
    ('Мария', 'Смирнова', 'Уход за приматами', 1),
    ('Алексей', 'Кузнецов', 'Ветеринар', NULL),
    ('Елена', 'Иванова', 'Уход за птицами', 4),
    ('Ольга', 'Соколова', 'Администратор', NULL);

-- Забивка данных в таблицу Visitors
INSERT INTO Visitors (name, visit_date, feedback)
VALUES
    ('Анна Сергеева', '2024-12-01', 'Очень понравились львы и тигры!'),
    ('Максим Кузьмин', '2024-12-02', 'Хороший зоопарк, но мало зон с птицами.'),
    ('Светлана Романова', '2024-12-03', 'Прекрасный день с семьей.'),
    ('Дмитрий Федоров', '2024-12-04', 'Отлично организовано.'),
    ('Екатерина Лебедева', '2024-12-05', 'Дети в восторге от попугаев.');

-- Забивка данных в таблицу Tickets
INSERT INTO Tickets (visitor_id, ticket_type, price, purchase_date)
VALUES
    (1, 'Взрослый', 500.00, '2024-12-01'),
    (2, 'Взрослый', 500.00, '2024-12-02'),
    (3, 'Семейный', 1200.00, '2024-12-03'),
    (4, 'Детский', 300.00, '2024-12-04'),
    (5, 'Детский', 300.00, '2024-12-05');

-- Забивка данных в таблицу Medical_checkups
INSERT INTO Medical_checkups (animal_id, staff_id, checkup_date, diagnosis, treatment)
VALUES
    (1, 3, '2024-11-20', 'Профилактический осмотр', 'Не требуется'),
    (2, 3, '2024-11-21', 'Мелкая травма лапы', 'Обработка раны'),
    (3, 3, '2024-11-22', 'Профилактический осмотр', 'Не требуется'),
    (4, 3, '2024-11-23', 'Незначительное переохлаждение', 'Прогревание'),
    (5, 3, '2024-11-24', 'Профилактический осмотр', 'Не требуется');
