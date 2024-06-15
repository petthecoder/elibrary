
CREATE TABLE users (
    user_id SERIAL PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    full_name VARCHAR(100),
    role VARCHAR(10),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE books (
    book_id SERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    publication_year INTEGER,
    genre VARCHAR(400),
    isbn VARCHAR(17) UNIQUE,
    format VARCHAR(50),
    synopsis TEXT,
    pages INTEGER,
    publisher VARCHAR(100),
    image VARCHAR(300),
    series_id INTEGER,
    ordinal INTEGER,
    CONSTRAINT fk_series
        FOREIGN KEY(series_id) 
        REFERENCES series(series_id)
);

CREATE TABLE authors (
    author_id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    photo_url TEXT
);

CREATE TABLE book_authors (
    book_id INTEGER REFERENCES books(book_id),
    author_id INTEGER REFERENCES authors(author_id),
    PRIMARY KEY (book_id, author_id)
);

CREATE TABLE series (
    series_id SERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    description TEXT
);

CREATE TABLE user_books (
    user_id INTEGER REFERENCES users(user_id),
    book_id INTEGER REFERENCES books(book_id),
    status VARCHAR(50), -- 'want_to_read', 'currently_reading', 'read'
    rating INTEGER CHECK (rating >= 1 AND rating <= 5),
    purchase_date DATE,
    start_date DATE,
    finish_date DATE,
    PRIMARY KEY (user_id, book_id)
);

CREATE TABLE lists (
    list_id SERIAL PRIMARY KEY,
    user_id INTEGER REFERENCES users(user_id),
    title VARCHAR(255) NOT NULL,
    description TEXT,
    is_public BOOLEAN DEFAULT FALSE
);

CREATE TABLE list_books (
    list_id INTEGER REFERENCES lists(list_id),
    book_id INTEGER REFERENCES books(book_id),
    PRIMARY KEY (list_id, book_id)
);

ALTER TABLE lists
DROP COLUMN created_at;

ALTER TABLE books
ADD CONSTRAINT fk_series
        FOREIGN KEY(series_id) 
        REFERENCES series(series_id);
       
       ALTER TABLE books
ADD COLUMN synopsis TEXT;


drop table book_series;
