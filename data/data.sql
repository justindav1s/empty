CREATE USER u760245 SUPERUSER LOGIN PASSWORD 'u760245';
CREATE DATABASE selling with OWNER u760245;
\connect DBNAME
CREATE SEQUENCE BA_SEQ INCREMENT BY 1 MINVALUE 1000 MAXVALUE 999999 START WITH 1000 CYCLE;
CREATE TABLE persons(PersonID int,lastName varchar(255),firstName varchar(255),address varchar(255),city varchar(255), PRIMARY KEY (PersonID));
CREATE TABLE bookings (personID int,bookingId int,flightnum varchar(255),tickets int,cabin  varchar(255), PRIMARY KEY (bookingId));
CREATE TABLE flights(flightnum varchar(255),origin varchar(255),destination varchar(255),PRIMARY KEY (flightnum));
CREATE TABLE loyalty(PersonID int, tier varchar(255), points int);