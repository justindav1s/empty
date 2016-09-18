CREATE USER ndc SUPERUSER LOGIN PASSWORD 'ndc';
CREATE DATABASE ndc with OWNER ndc;
\connect ndc
CREATE SEQUENCE BA_SEQ INCREMENT BY 1 MINVALUE 1000 MAXVALUE 999999 START WITH 1000 CYCLE;
CREATE TABLE persons(PersonID int,lastName varchar(255),firstName varchar(255),address varchar(255),city varchar(255), PRIMARY KEY (PersonID));
CREATE TABLE bookings (personID int,bookingId int,flightnum varchar(255),tickets int,cabin  varchar(255), PRIMARY KEY (bookingId));
CREATE TABLE flights(flightnum varchar(255),origin varchar(255),destination varchar(255),PRIMARY KEY (flightnum));
CREATE TABLE loyalty(PersonID int, tier varchar(255), points int);
GRANT ALL PRIVILEGES ON SEQUENCE BA_SEQ TO ndc;
GRANT ALL PRIVILEGES ON TABLE persons TO ndc;
GRANT ALL PRIVILEGES ON TABLE bookings TO ndc;
GRANT ALL PRIVILEGES ON TABLE flights TO ndc;
GRANT ALL PRIVILEGES ON TABLE loyalty TO ndc;
INSERT INTO FLIGHTS (flightnum, origin, destination) values ('BA009', 'LHR', 'BKK');
INSERT INTO FLIGHTS (flightnum, origin, destination) values ('BA010', 'BKK', 'LHR');
INSERT INTO PERSONS (PersonID, lastName, firstName, address, city) values (2001, 'Davis', 'Justin', 'Salamanca', 'Madrid');
INSERT INTO PERSONS (PersonID, lastName, firstName, address, city) values (2002, 'Davis', 'Justin', 'Twickenham', 'London');