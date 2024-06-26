CREATE TABLE IF NOT EXISTS app_user
(
    id SERIAL NOT NULL,
    phone VARCHAR(255),
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

delete from app_user where email = 'root@root.com';
delete from app_user where email = 'test@test.com';
delete from app_user where email = 'dan@new.com';

delete from hotel;
delete from room;
delete from reservation;
delete from room_item;
delete from room_cart;


insert into app_user (phone, email, first_name, last_name, password, role)
VALUES (
           '937429874',
           'root@root.com',
           'root',
           'root',
           '$2a$10$RzWzQ5t2xZq.i94.DRDIt.wS.2rQndo33gIq3ISfLu9DRdxK9Vjha' /*root*/,
           'USER'
       );
insert into app_user (id, phone, email, first_name, last_name, password, role)
VALUES (
                    1,
           '38000000000',
           'test@test.com',
           'test',
           'test',
           '$2a$10$1ID9XNtUgrzsNU2n28E/0uO2/i9sXxLWR/wkS2ZE6UR5x19zDKwjq', /*test*/
           'ADMIN'
       );
insert into app_user (id, phone, email, first_name, last_name, password, role)
VALUES (
           20,
           '38000837640',
           'dan@new.com',
           'dan',
           'dan',
           'password',
           'USER'
       );
insert into hotel (id, name, address)
VALUES (
           1,
           'Hotel Royal',
           'New-York'
       );
INSERT INTO Room (id, name, price, description, room_classification, room_type, hotel_id)
VALUES (1, 'Deluxe Room', 250.0, 'Spacious and luxurious', 'DELUXE', 'DOUBLE', 1);

INSERT INTO Room (id, name, price, description, room_classification, room_type, hotel_id)
VALUES (2, 'Standard family', 250.0, 'Please do not be strict:)', 'DELUXE', 'FAMILY', 1);

INSERT INTO  reservation (created_at, id, user_id)
VALUES ('2024.01.01', 1, 1);

INSERT INTO room_item (id, room_id, room_number)
VALUES(1,1,101);


INSERT INTO room_cart(id,reservation_id,reserved_from,reserved_to,room_item_id)
   VALUES (1,1,'2024.01.01','2024.01.07',1);