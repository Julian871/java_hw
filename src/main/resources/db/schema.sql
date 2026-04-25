CREATE TABLE authors (
                         author_id SERIAL PRIMARY KEY,
                         name VARCHAR(100) NOT NULL,
                         email VARCHAR(100) UNIQUE
);

CREATE TABLE books (
                       book_id SERIAL PRIMARY KEY,
                       book_name VARCHAR(100) NOT NULL,
                       price NUMERIC(10,2) CHECK (price > 0),
                       author_id INT NOT NULL REFERENCES authors(author_id) ON DELETE CASCADE
);

CREATE TABLE readers (
                         reader_id SERIAL PRIMARY KEY,
                         reader_name VARCHAR(100) NOT NULL
);

CREATE TABLE rents (
                       rent_id SERIAL PRIMARY KEY,
                       rent_date DATE DEFAULT CURRENT_DATE,
                       reader_id INT NOT NULL REFERENCES readers(reader_id) ON DELETE CASCADE,
                       book_id INT NOT NULL REFERENCES books(book_id) ON DELETE CASCADE,
                       return_date DATE,

                       CONSTRAINT unique_rent UNIQUE (reader_id, book_id, rent_date),
                       CONSTRAINT check_dates CHECK (rent_date <= CURRENT_DATE)
);