CREATE TABLE users(
    u_id NUMBER(4) PRIMARY KEY,
    username VARCHAR2(64) UNIQUE NOT NULL,
    password VARCHAR2(64) NOT NULL,
    role VARCHAR2(32) DEFAULT 'anonymous'
);
-- user has many cars
-- user has many offers
-- user has many payments

CREATE TABLE cars(
    c_id NUMBER(4) PRIMARY KEY,
    model VARCHAR2(64) NOT NULL,
    year NUMBER(5) NOT NULL,
    price NUMBER(10) NOT NULL,
    location VARCHAR2(20) DEFAULT 'lot',
    u_id NUMBER(10) CONSTRAINT fk_cars_usrs REFERENCES users(u_id)
);
-- car has one user
-- car has many offers
-- car has many payments

CREATE TABLE offers(
    o_id NUMBER(4) PRIMARY KEY,
    amount NUMBER(10) NOT NULL,
    months_offered NUMBER(4) NOT NULL,
    status VARCHAR2(32) DEFAULT 'unevaluated',
    u_id NUMBER(10) CONSTRAINT fk_ofrs_usrs REFERENCES users(u_id)
);
-- offer has one user
-- offer has one car

CREATE TABLE payments(
    p_id NUMBER(4) PRIMARY KEY,
    amount_paid NUMBER(10) NOT NULL,
    months_left NUMBER(4) NOT NULL,
    c_id NUMBER(10) CONSTRAINT fk_pays_cars REFERENCES cars(c_id)
);
-- payment has one user
-- payment has one car
