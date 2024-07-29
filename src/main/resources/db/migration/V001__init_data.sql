create table clients
(
    id           SERIAL PRIMARY KEY,
    name         VARCHAR(100),
    phone_number VARCHAR(15),
    balance      DOUBLE PRECISION
);

create table payments
(
    id       SERIAL PRIMARY KEY,
    date_time TIMESTAMP,
    amount      DOUBLE PRECISION,
    sender_id INT,
    recipient_id INT,
    message VARCHAR(255)
);


