DROP SEQUENCE IF EXISTS tblClientsIdSeq;
DROP SEQUENCE IF EXISTS tblAttractionsIdSeq;
DROP SEQUENCE IF EXISTS tblOffersIdSeq;
DROP SEQUENCE IF EXISTS tblReservedItemsIdSeq;
DROP FUNCTION IF EXISTS addClient(varchar,varchar,varchar,varchar);
DROP FUNCTION IF EXISTS retrieveClients();
DROP FUNCTION IF EXISTS addAttraction(varchar,text,float);
DROP FUNCTION IF EXISTS addOffer(varchar,text,float,date,date);
DROP FUNCTION IF EXISTS retrieveAttractions();
DROP FUNCTION IF EXISTS retrieveOffers();
DROP FUNCTION IF EXISTS addReservation(integer, timestamp);
DROP FUNCTION IF EXISTS deleteClient(integer);
DROP FUNCTION IF EXISTS deleteAttraction(integer);
DROP FUNCTION IF EXISTS deleteOffer(integer);
DROP FUNCTION IF EXISTS retrieveOffer(integer);
DROP FUNCTION IF EXISTS retrieveAttraction(integer);
DROP FUNCTION IF EXISTS addReservedOfferItem(integer,integer);
DROP FUNCTION IF EXISTS addReservedAttrItem(integer,integer);
DROP FUNCTION IF EXISTS retrieveExistingReservations();
DROP FUNCTION IF EXISTS retrieveReservedItems(integer);

DROP VIEW IF EXISTS existingReservation;
DROP VIEW IF EXISTS reservedItems;
DROP TABLE IF EXISTS tblReservedItems;
DROP TABLE IF EXISTS tblReservation;
DROP TABLE IF EXISTS tblClients;
DROP TABLE IF EXISTS tblAttractions;
DROP TABLE IF EXISTS tblOffers;

 
 CREATE TABLE tblClients (
 	uniqueId integer PRIMARY KEY,
 	clientName varchar(100),
 	adres varchar(100),
 	nip varchar(15),
 	zip varchar(15)
 );
 
 
 
 CREATE TABLE tblAttractions (
 	uniqueId integer PRIMARY KEY,
 	attrName varchar(50),
 	description text,
 	price float
 );
 

 
 CREATE TABLE tblOffers(
 	uniqueId integer PRIMARY KEY,
 	offerName varchar(50),
 	description text,
 	price float,
 	startDate date,
 	endData date
 );
 

 
 CREATE TABLE tblReservation(
 	uniqueId integer PRIMARY KEY,
 	clientId integer REFERENCES tblClients(uniqueId)  ON DELETE CASCADE,
 	reservationDate timestamp
 );
 
 CREATE TABLE tblReservedItems(
 	uniqueId integer PRIMARY KEY,
 	reservationId integer REFERENCES tblReservation(uniqueId)  ON DELETE CASCADE,
 	attractionId integer REFERENCES tblAttractions(uniqueId)  ON DELETE CASCADE,
 	offerId integer REFERENCES tblOffers(uniqueId)  ON DELETE CASCADE
 );
 
CREATE VIEW existingReservation AS 
 SELECT tblreservation.uniqueId, tblreservation.reservationdate, tblclients.clientname 
FROM tblreservation INNER JOIN tblclients ON tblreservation.clientid = tblclients.uniqueid;

CREATE VIEW reservedItems AS
 SELECT  tblreserveditems.uniqueID, tblreserveditems.reservationid, tblAttractions.attrname, tbloffers.offername 
FROM tblreserveditems 
LEFT JOIN tbloffers ON tblreserveditems.offerid = tbloffers.uniqueid 
LEFT JOIN tblattractions ON tblreserveditems.attractionid = tblattractions.uniqueid;
 
CREATE SEQUENCE tblClientsIdSeq INCREMENT BY 1 OWNED BY tblClients.uniqueId;
CREATE SEQUENCE tblAttractionsIdSeq INCREMENT BY 1 OWNED BY tblAttractions.uniqueId;
CREATE SEQUENCE tblOffersIdSeq INCREMENT BY 1 OWNED BY tblOffers.uniqueId;
CREATE SEQUENCE tblReservationsIdSeq INCREMENT BY 1 OWNED BY tblReservation.uniqueId;
CREATE SEQUENCE tblReservedItemsIdSeq INCREMENT BY 1 OWNED BY tblReservedItems.uniqueId;
 
CREATE FUNCTION addClient(varchar,varchar,varchar,varchar) RETURNS void AS 'INSERT INTO tblClients VALUES (nextval(''tblClientsIdSeq''),$1,$2,$3,$4)' LANGUAGE SQL;

CREATE FUNCTION retrieveClients() RETURNS SETOF tblClients AS $$ 
	SELECT * FROM tblClients; 
$$LANGUAGE SQL;

CREATE FUNCTION addAttraction(varchar,text,float) RETURNS void AS $$		
	INSERT INTO tblAttractions VALUES (nextval('tblAttractionsIdSeq'),$1,$2,$3);
$$LANGUAGE SQL;

CREATE FUNCTION retrieveAttractions() RETURNS SETOF tblAttractions AS $$
	SELECT * FROM tblAttractions;
$$LANGUAGE SQL;

CREATE FUNCTION addOffer(varchar,text,float,date,date) RETURNS void AS $$
	INSERT INTO tblOffers VALUES (nextval('tblOffersIdSeq'),$1,$2,$3,$4,$5);
$$LANGUAGE SQL;

CREATE FUNCTION retrieveOffers() RETURNS SETOF tblOffers AS $$
	SELECT * FROM tblOffers;
$$LANGUAGE SQL;

CREATE FUNCTION addReservation(integer, timestamp) RETURNS void AS $$
	INSERT INTO tblReservation VALUES (nextval('tblReservationsIdSeq'),$1, $2);
$$LANGUAGE SQL;

CREATE FUNCTION deleteClient(integer) RETURNS void AS $$
	DELETE FROM tblClients WHERE uniqueId = $1;
$$LANGUAGE SQL;

CREATE FUNCTION deleteAttraction(integer) RETURNS void AS $$
	DELETE FROM tblAttractions WHERE uniqueId = $1;
$$LANGUAGE SQL;

CREATE FUNCTION deleteOffer(integer) RETURNS void AS $$
	DELETE FROM tblOffers WHERE uniqueId = $1;
$$LANGUAGE SQL;

CREATE FUNCTION retrieveOffer(integer) RETURNS SETOF tblOffers AS $$
	SELECT * FROM tblOffers WHERE uniqueId = $1;
$$LANGUAGE SQL;

CREATE FUNCTION retrieveAttraction(integer) RETURNS SETOF tblAttractions AS $$
	SELECT * FROM tblAttractions WHERE uniqueId = $1;
$$LANGUAGE SQL;

CREATE FUNCTION addReservedOfferItem(integer,integer) RETURNS void AS $$
	INSERT INTO tblReservedItems VALUES (nextval('tblReservedItemsIdSeq'),$1,NULL,$2);
$$LANGUAGE SQL;

CREATE FUNCTION addReservedAttrItem(integer,integer) RETURNS void AS $$
	INSERT INTO tblReservedItems VALUES (nextval('tblReservedItemsIdSeq'),$1,$2,NULL);
$$LANGUAGE SQL;

CREATE FUNCTION retrieveExistingReservations() RETURNS SETOF existingReservation AS $$
	SELECT * from existingReservation;
$$LANGUAGE SQL;

CREATE FUNCTION retrieveReservedItems(integer) RETURNS SETOF reservedItems AS $$
	SELECT * from reservedItems where reservationid = $1;
$$LANGUAGE SQL;


INSERT INTO tblClients VALUES (nextval('tblClientsIdSeq'),'Pawel Malczyk','Kraków','665465432','30-444');
INSERT INTO tblClients VALUES (nextval('tblClientsIdSeq'),'Grzegorz Chmiel','Katwice','62322432','36-421');
INSERT INTO tblClients VALUES (nextval('tblClientsIdSeq'),'Grzegorz Zych','Łódź','7627432','32-4214');
INSERT INTO tblAttractions VALUES (nextval('tblAttractionsIdSeq'),'Basen kryty','Kryty podgrzewany basen','50');
INSERT INTO tblAttractions VALUES (nextval('tblAttractionsIdSeq'),'Sauna','Sauna','20');
INSERT INTO tblAttractions VALUES (nextval('tblAttractionsIdSeq'),'Siłownia','Godzina na siłowni', '12');
INSERT INTO tblOffers VALUES (nextval('tblOffersIdSeq'),'Wyjazd do Egiptu','15 dniowy wyjazd do Egiptu','2000','2008-12-01','2008-12-16');
INSERT INTO tblOffers VALUES (nextval('tblOffersIdSeq'),'Wyjazd do Norwegii','20 dniowy wyjazd do Egiptu','3500','2009-06-01','2008-06-21');
INSERT INTO tblOffers VALUES (nextval('tblOffersIdSeq'),'Wyjazd do Hiszpanii','10 dniowy wyjazd do Egiptu', '2200','2009-04-12','2009-04-22');