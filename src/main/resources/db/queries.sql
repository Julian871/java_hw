-- a. SELECT с сортировкой (ORDER BY) и фильтрацией (WHERE).

SELECT book_name, price
FROM books
WHERE price > 490
ORDER BY price DESC;

SELECT book_name, price
FROM books
WHERE author_id = 1
ORDER BY book_name ASC;

-- b Группировка (GROUP BY) с агрегатными функциями.
SELECT r.reader_name, COUNT(rt.rent_id) AS total_rents
FROM readers r
         LEFT JOIN rents rt ON r.reader_id = rt.reader_id
GROUP BY r.reader_id, r.reader_name
ORDER BY total_rents DESC;

SELECT r.reader_name, COUNT(rt.rent_id) AS active_rents
FROM readers r
         LEFT JOIN rents rt ON r.reader_id = rt.reader_id AND rt.return_date IS NULL
GROUP BY r.reader_id, r.reader_name
ORDER BY active_rents DESC;

-- c Несколько JOIN: например, подтянуть название товара и имя клиента к
-- таблице заказов, показать список книг и соответствующих авторов.

SELECT
    rt.rent_id,
    r.reader_name AS reader,
    b.book_name AS book,
    a.name AS author,
    rt.rent_date,
    rt.return_date
FROM rents rt
         JOIN readers r ON rt.reader_id = r.reader_id
         JOIN books b ON rt.book_id = b.book_id
         JOIN authors a ON b.author_id = a.author_id;

SELECT
    r.reader_name,
    b.book_name,
    a.name AS author_name,
    rt.rent_date AS borrowed_date
FROM rents rt
         JOIN readers r ON rt.reader_id = r.reader_id
         JOIN books b ON rt.book_id = b.book_id
         JOIN authors a ON b.author_id = a.author_id
WHERE rt.return_date IS NULL
ORDER BY r.reader_name;

-- d Обновление (UPDATE): измените поле (например, цену товара или название книги).

UPDATE authors
SET email = 'orwell_new@example.com'
WHERE name = 'Джордж Оруэлл';

UPDATE rents
SET return_date = CURRENT_DATE
WHERE rent_id = 3;


-- e Удаление (DELETE): удалите 1–2 записи, чтобы проверить целостность (особенно, если есть FOREIGN KEY).
DELETE FROM rents WHERE book_id IN (SELECT book_id FROM books WHERE author_id = 5);
DELETE FROM books WHERE author_id = 5;
DELETE FROM authors WHERE author_id = 5;
