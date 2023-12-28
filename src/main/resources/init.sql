CREATE TABLE IF NOT EXISTS app_user
(
    id SERIAL NOT NULL,
    phone INTEGER,
    email VARCHAR(255),
    first_name VARCHAR(255),
    last_name VARCHAR(255),
    password VARCHAR(255),
    role VARCHAR(255) CHECK (role IN ('USER', 'ADMIN')),
    PRIMARY KEY (id)
    );

CREATE TABLE IF NOT EXISTS category
(
    id SERIAL NOT NULL,
    name VARCHAR(255),
    PRIMARY KEY (id)
    );

CREATE TABLE IF NOT EXISTS hotel
(
    id SERIAL NOT NULL,
    address VARCHAR(255),
    name VARCHAR(255),
    PRIMARY KEY (id)
    );

CREATE TABLE IF NOT EXISTS room
(
    hotel_id INTEGER,
    id SERIAL NOT NULL,
    price FLOAT(53),
    description VARCHAR(255),
    name VARCHAR(255),
    room_classification VARCHAR(255) CHECK (room_classification IN ('LUX', 'DELUXE', 'STANDARD', 'ECONOMY')),
    room_type VARCHAR(255) CHECK (room_type IN ('DOUBLE', 'FAMILY', 'SINGLE')),
    PRIMARY KEY (id),
    CONSTRAINT fk_room_hotel_id FOREIGN KEY (hotel_id) REFERENCES hotel
    );

CREATE TABLE IF NOT EXISTS reservation
(
    created_at DATE,
    id SERIAL NOT NULL,
    user_id INTEGER,
    PRIMARY KEY (id),
    CONSTRAINT fk_reservation_user_id FOREIGN KEY (user_id) REFERENCES app_user
    );

CREATE TABLE IF NOT EXISTS payment
(
    amount FLOAT(53),
    bill_number INTEGER,
    date DATE,
    id SERIAL NOT NULL,
    reservation_id INTEGER UNIQUE,
    PRIMARY KEY (id),
    CONSTRAINT fk_payment_reservation_id FOREIGN KEY (reservation_id) REFERENCES reservation
    );

CREATE TABLE IF NOT EXISTS room_item
(
    id SERIAL NOT NULL,
    room_id INTEGER,
    room_number INTEGER,
    PRIMARY KEY (id),
    CONSTRAINT fk_room_item_room_id FOREIGN KEY (room_id) REFERENCES room
    );

CREATE TABLE IF NOT EXISTS category_room
(
    category_id INTEGER NOT NULL,
    room_id INTEGER NOT NULL,
    CONSTRAINT fk_category_room_category_id FOREIGN KEY (category_id) REFERENCES category,
    CONSTRAINT fk_category_room_room_id FOREIGN KEY (room_id) REFERENCES room
    );

CREATE TABLE IF NOT EXISTS room_cart
(
    id SERIAL NOT NULL,
    reservation_id INTEGER,
    reserved_from DATE,
    reserved_to DATE,
    room_item_id INTEGER,
    PRIMARY KEY (id),
    CONSTRAINT fk_room_cart_reservation_id FOREIGN KEY (reservation_id) REFERENCES reservation,
    CONSTRAINT fk_room_cart_room_item_id FOREIGN KEY (room_item_id) REFERENCES room_item
    );

insert into app_user (phone, email, first_name, last_name, password, role)
VALUES (
           937429874,
           'root',
           'root',
           'root',
           'root',
           'USER'
       );
