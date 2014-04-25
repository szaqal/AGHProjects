CONNECT TO EWPP;



---------------------------------
-- SEKWENCJE

-- Szczegoly
-- http://publib.boulder.ibm.com/infocenter/dzichelp/v2r2/index.jsp?topic=/com.ibm.db29.doc.sqlref/db2z_sql_createsequence.htm
---------------------------------

-- Elementy konfiguracji
CREATE SEQUENCE "DB2INST1"."CONF_SEQ" AS INTEGER MINVALUE 1 MAXVALUE 2147483647
	START WITH 1 INCREMENT BY 1 CACHE 20 NO CYCLE NO ORDER;

-- Iteracje etapu projektu
CREATE SEQUENCE "DB2INST1"."ITER_SEQ" AS INTEGER MINVALUE 1 MAXVALUE 2147483647
	START WITH 1 INCREMENT BY 1 CACHE 20 NO CYCLE NO ORDER;

-- Wiadomosci
CREATE SEQUENCE "DB2INST1"."MESSAGE_SEQ" AS INTEGER MINVALUE 1 MAXVALUE 2147483647
	START WITH 1 INCREMENT BY 1 CACHE 20 NO CYCLE NO ORDER;

-- Projekty
CREATE SEQUENCE "DB2INST1"."PROJECT_SEQ" AS INTEGER MINVALUE 1 MAXVALUE 2147483647
	START WITH 1 INCREMENT BY 1 CACHE 20 NO CYCLE NO ORDER;

-- Element projektu (zawartosc)
CREATE SEQUENCE "DB2INST1"."PR_ITEM_CONTENT_SEQ" AS INTEGER MINVALUE 1 MAXVALUE 2147483647
	START WITH 1 INCREMENT BY 1 CACHE 20 NO CYCLE NO ORDER;

-- Element projektu
CREATE SEQUENCE "DB2INST1"."PR_ITEM_SEQ" AS INTEGER MINVALUE 1 MAXVALUE 2147483647
	START WITH 1 INCREMENT BY 1 CACHE 20 NO CYCLE NO ORDER;

-- Etapy projektu
CREATE SEQUENCE "DB2INST1"."PR_STAGE_SEQ" AS INTEGER MINVALUE 1 MAXVALUE 2147483647
	START WITH 1 INCREMENT BY 1 CACHE 20 NO CYCLE NO ORDER;

-- Grupy projektowe
CREATE SEQUENCE "DB2INST1"."PR_TEAM_SEQ" AS INTEGER MINVALUE 1 MAXVALUE 2147483647
	START WITH 1 INCREMENT BY 1 CACHE 20 NO CYCLE NO ORDER;

-- Uzytkownicy
CREATE SEQUENCE "DB2INST1"."USER_SEQ" AS INTEGER MINVALUE 1 MAXVALUE 2147483647
	START WITH 1 INCREMENT BY 1 CACHE 20 NO CYCLE NO ORDER;



------------------------------------------------
-- TABELE
------------------------------------------------
 
--Pliki
CREATE TABLE "DB2INST1"."FILES"  (
		  "UNIQUEID" INTEGER NOT NULL , 
		  "ISPUBLIC" SMALLINT , 
		  "TITLE" VARCHAR(255) , 
		  "CONTENT" BLOB(1000000) LOGGED NOT COMPACT , 
		  "CONTENTTYPE" VARCHAR(255) , 
		  "FILENAME" VARCHAR(255) , 
		  "MD5HASH" VARCHAR(255) , 
		  "OWNER" VARCHAR(255) FOR BIT DATA , 
		  "PROJECTITEM_UNIQUEID" INTEGER )   
		 IN "USERSPACE1" ; 


-- PK
ALTER TABLE "DB2INST1"."FILES" ADD PRIMARY KEY ("UNIQUEID");


--Notatki
CREATE TABLE "DB2INST1"."NOTES"  (
		  "UNIQUEID" INTEGER NOT NULL , 
		  "ISPUBLIC" SMALLINT , 
		  "TITLE" VARCHAR(255) , 
		  "CONTENT" VARCHAR(255) , 
		  "PROJECTITEM_UNIQUEID" INTEGER , 
		  "OWNER_UNIQUEID" INTEGER )   
		 IN "USERSPACE1" ; 


-- PK
ALTER TABLE "DB2INST1"."NOTES" ADD PRIMARY KEY ("UNIQUEID");



-- Elementy projektu
CREATE TABLE "DB2INST1"."PROJECTITEMS"  (
		  "UNIQUEID" INTEGER NOT NULL , 
		  "CREATEDATE" TIMESTAMP , 
		  "PROJECTITEMTYPE" VARCHAR(255) , 
		  "PROJECT_UNIQUEID" INTEGER , 
		  "PROJECTSTAGEITERATION_UNIQUEID" INTEGER )   
		 IN "USERSPACE1" ; 


-- PK
ALTER TABLE "DB2INST1"."PROJECTITEMS"  ADD PRIMARY KEY ("UNIQUEID");



-- Projekty
CREATE TABLE "DB2INST1"."PROJECTS"  (
		  "UNIQUEID" INTEGER NOT NULL , 
		  "DESCRIPTION" VARCHAR(255) , 
		  "PROJECTSTATUS" VARCHAR(255) , 
		  "TITLE" VARCHAR(255) , 
		  "PROJECTTEAM_UNIQUEID" INTEGER )   
		 IN "USERSPACE1" ; 


-- PK
ALTER TABLE "DB2INST1"."PROJECTS" ADD PRIMARY KEY ("UNIQUEID");


-- Etapy tabela lacznikowa
CREATE TABLE "DB2INST1"."PROJECTS_PROJECTSTAGES"  (
		  "PROJECTS_UNIQUEID" INTEGER NOT NULL , 
		  "PROJECTSTAGES_UNIQUEID" INTEGER NOT NULL )   
		 IN "USERSPACE1" ; 


-- PK
ALTER TABLE "DB2INST1"."PROJECTS_PROJECTSTAGES"  ADD UNIQUE ("PROJECTSTAGES_UNIQUEID");


-- Etapy projektu
CREATE TABLE "DB2INST1"."PROJECTSTAGES"  (
		  "UNIQUEID" INTEGER NOT NULL , 
		  "DESCRIPTION" VARCHAR(255) , 
		  "STAGESTATUS" VARCHAR(255) , 
		  "TITLE" VARCHAR(255) , 
		  "PROJECT_UNIQUEID" INTEGER )   
		 IN "USERSPACE1" ; 


--PK
ALTER TABLE "DB2INST1"."PROJECTSTAGES"  ADD PRIMARY KEY ("UNIQUEID");




CREATE TABLE "DB2INST1"."PROJECTTEAM"  (
		  "UNIQUEID" INTEGER NOT NULL , 
		  "LECTURER_UNIQUEID" INTEGER )   
		 IN "USERSPACE1" ; 


-- PK
ALTER TABLE "DB2INST1"."PROJECTTEAM" ADD PRIMARY KEY ("UNIQUEID");



-- Iteracje etapu projektu
CREATE TABLE "DB2INST1"."STAGEITERATION"  (
		  "UNIQUEID" INTEGER NOT NULL , 
		  "COMMENT" VARCHAR(255) , 
		  "PROJECTITEM_UNIQUEID" INTEGER , 
		  "PROJECTSTAGE_UNIQUEID" INTEGER )   
		 IN "USERSPACE1" ; 


-- PK
ALTER TABLE "DB2INST1"."STAGEITERATION" ADD PRIMARY KEY ("UNIQUEID");



CREATE TABLE "DB2INST1"."TEAM_USER"  (
		  "PROJECTTEAM_UNIQUEID" INTEGER NOT NULL , 
		  "MEMBERS_UNIQUEID" INTEGER NOT NULL )   
		 IN "USERSPACE1" ; 






-- Uzytkownicy
CREATE TABLE "DB2INST1"."USERS"  (
		  "UNIQUEID" INTEGER NOT NULL , 
		  "DELETED" SMALLINT NOT NULL , 
		  "EMAIL" VARCHAR(255) , 
		  "FIRSTNAME" VARCHAR(255) , 
		  "LASTNAME" VARCHAR(255) , 
		  "LOGIN" VARCHAR(255) , 
		  "MIDDLENAME" VARCHAR(255) , 
		  "PASSWD" VARCHAR(255) , 
		  "PHONENR" VARCHAR(255) , 
		  "USERTYPE" VARCHAR(255) )   
		 IN "USERSPACE1" ; 


-- PK
ALTER TABLE "DB2INST1"."USERS" ADD PRIMARY KEY ("UNIQUEID");



-- Elementy konfiguracji
CREATE TABLE "DB2INST1"."CONFIGURATION"  (
		  "UNIQUEID" INTEGER NOT NULL , 
		  "KEY" VARCHAR(255) , 
		  "VALUE" VARCHAR(255) )   
		 IN "USERSPACE1" ; 


-- PK
ALTER TABLE "DB2INST1"."CONFIGURATION" ADD PRIMARY KEY ("UNIQUEID");



-- Wiadomosci
CREATE TABLE "DB2INST1"."MESSAGES"  (
		  "UNIQUEID" INTEGER NOT NULL , 
		  "CONTENT" VARCHAR(255) , 
		  "CREATEDATE" TIMESTAMP , 
		  "MESSAGESTATUS" VARCHAR(255) , 
		  "RECIPIENTDELETED" SMALLINT NOT NULL , 
		  "SENDERDELETED" SMALLINT NOT NULL , 
		  "TITLE" VARCHAR(255) , 
		  "RECIPIENT_UNIQUEID" INTEGER , 
		  "SENDER_UNIQUEID" INTEGER )   
		 IN "USERSPACE1" ; 


--PK 
ALTER TABLE "DB2INST1"."MESSAGES" ADD PRIMARY KEY ("UNIQUEID");


-- ENABLE QUERY OPTIMIZATION zezala na przepisanie zapytania przez DB2 na bardziej optymalne
-- FK 
ALTER TABLE "DB2INST1"."FILES" 
	ADD CONSTRAINT "FILES_FK" FOREIGN KEY
		("PROJECTITEM_UNIQUEID")
	REFERENCES "DB2INST1"."PROJECTITEMS"
		("UNIQUEID")
	ON DELETE NO ACTION
	ON UPDATE NO ACTION
	ENFORCED
	ENABLE QUERY OPTIMIZATION;

-- FK
ALTER TABLE "DB2INST1"."NOTES" 
	ADD CONSTRAINT "NOTES_FK_USERS" FOREIGN KEY
		("OWNER_UNIQUEID")
	REFERENCES "DB2INST1"."USERS"
		("UNIQUEID")
	ON DELETE NO ACTION
	ON UPDATE NO ACTION
	ENFORCED
	ENABLE QUERY OPTIMIZATION;

-- FK
ALTER TABLE "DB2INST1"."NOTES" 
	ADD CONSTRAINT "NOTES_FK_PROJECT_ITEMS" FOREIGN KEY
		("PROJECTITEM_UNIQUEID")
	REFERENCES "DB2INST1"."PROJECTITEMS"
		("UNIQUEID")
	ON DELETE NO ACTION
	ON UPDATE NO ACTION
	ENFORCED
	ENABLE QUERY OPTIMIZATION;

-- FK
ALTER TABLE "DB2INST1"."PROJECTITEMS" 
	ADD CONSTRAINT "FK_PROJECTITEMS" FOREIGN KEY
		("PROJECT_UNIQUEID")
	REFERENCES "DB2INST1"."PROJECTS"
		("UNIQUEID")
	ON DELETE NO ACTION
	ON UPDATE NO ACTION
	ENFORCED
	ENABLE QUERY OPTIMIZATION;

-- FK
ALTER TABLE "DB2INST1"."PROJECTITEMS" 
	ADD CONSTRAINT "FK_STAGE_ITERATION" FOREIGN KEY
		("PROJECTSTAGEITERATION_UNIQUEID")
	REFERENCES "DB2INST1"."STAGEITERATION"
		("UNIQUEID")
	ON DELETE NO ACTION
	ON UPDATE NO ACTION
	ENFORCED
	ENABLE QUERY OPTIMIZATION;

--FK
ALTER TABLE "DB2INST1"."PROJECTS" 
	ADD CONSTRAINT "FK_PROJECTS_TEAM" FOREIGN KEY
		("PROJECTTEAM_UNIQUEID")
	REFERENCES "DB2INST1"."PROJECTTEAM"
		("UNIQUEID")
	ON DELETE NO ACTION
	ON UPDATE NO ACTION
	ENFORCED
	ENABLE QUERY OPTIMIZATION;

-- FK
ALTER TABLE "DB2INST1"."PROJECTS_PROJECTSTAGES" 
	ADD CONSTRAINT "FK_PROJECTS_STAGES" FOREIGN KEY
		("PROJECTS_UNIQUEID")
	REFERENCES "DB2INST1"."PROJECTS"
		("UNIQUEID")
	ON DELETE NO ACTION
	ON UPDATE NO ACTION
	ENFORCED
	ENABLE QUERY OPTIMIZATION;

--FK
ALTER TABLE "DB2INST1"."PROJECTS_PROJECTSTAGES" 
	ADD CONSTRAINT "FK_PROJECT_STAGE" FOREIGN KEY
		("PROJECTSTAGES_UNIQUEID")
	REFERENCES "DB2INST1"."PROJECTSTAGES"
		("UNIQUEID")
	ON DELETE NO ACTION
	ON UPDATE NO ACTION
	ENFORCED
	ENABLE QUERY OPTIMIZATION;

-- FK
ALTER TABLE "DB2INST1"."PROJECTSTAGES" 
	ADD CONSTRAINT "FK_STAGES_PROJECT" FOREIGN KEY
		("PROJECT_UNIQUEID")
	REFERENCES "DB2INST1"."PROJECTS"
		("UNIQUEID")
	ON DELETE NO ACTION
	ON UPDATE NO ACTION
	ENFORCED
	ENABLE QUERY OPTIMIZATION;

-- FK
ALTER TABLE "DB2INST1"."PROJECTTEAM" 
	ADD CONSTRAINT "FK_TEAM_USER" FOREIGN KEY
		("LECTURER_UNIQUEID")
	REFERENCES "DB2INST1"."USERS"
		("UNIQUEID")
	ON DELETE NO ACTION
	ON UPDATE NO ACTION
	ENFORCED
	ENABLE QUERY OPTIMIZATION;

-- FK
ALTER TABLE "DB2INST1"."STAGEITERATION" 
	ADD CONSTRAINT "FK_ITER_FILES" FOREIGN KEY
		("PROJECTITEM_UNIQUEID")
	REFERENCES "DB2INST1"."FILES"
		("UNIQUEID")
	ON DELETE NO ACTION
	ON UPDATE NO ACTION
	ENFORCED
	ENABLE QUERY OPTIMIZATION;

-- FK
ALTER TABLE "DB2INST1"."STAGEITERATION" 
	ADD CONSTRAINT "FK_ITERATION_STAGE" FOREIGN KEY
		("PROJECTSTAGE_UNIQUEID")
	REFERENCES "DB2INST1"."PROJECTSTAGES"
		("UNIQUEID")
	ON DELETE NO ACTION
	ON UPDATE NO ACTION
	ENFORCED
	ENABLE QUERY OPTIMIZATION;

-- FK
ALTER TABLE "DB2INST1"."TEAM_USER" 
	ADD CONSTRAINT "FK_USER_TEAM" FOREIGN KEY
		("PROJECTTEAM_UNIQUEID")
	REFERENCES "DB2INST1"."PROJECTTEAM"
		("UNIQUEID")
	ON DELETE NO ACTION
	ON UPDATE NO ACTION
	ENFORCED
	ENABLE QUERY OPTIMIZATION;

-- FK
ALTER TABLE "DB2INST1"."TEAM_USER" 
	ADD CONSTRAINT "FK_TEAM_USR" FOREIGN KEY
		("MEMBERS_UNIQUEID")
	REFERENCES "DB2INST1"."USERS"
		("UNIQUEID")
	ON DELETE NO ACTION
	ON UPDATE NO ACTION
	ENFORCED
	ENABLE QUERY OPTIMIZATION;

-- FK
ALTER TABLE "DB2INST1"."MESSAGES" 
	ADD CONSTRAINT "FK_MESSAGE_USR" FOREIGN KEY
		("SENDER_UNIQUEID")
	REFERENCES "DB2INST1"."USERS"
		("UNIQUEID")
	ON DELETE NO ACTION
	ON UPDATE NO ACTION
	ENFORCED
	ENABLE QUERY OPTIMIZATION;

-- FK
ALTER TABLE "DB2INST1"."MESSAGES" 
	ADD CONSTRAINT "FK_MSG_USR2" FOREIGN KEY
		("RECIPIENT_UNIQUEID")
	REFERENCES "DB2INST1"."USERS"
		("UNIQUEID")
	ON DELETE NO ACTION
	ON UPDATE NO ACTION
	ENFORCED
	ENABLE QUERY OPTIMIZATION;

 
GRANT USE OF TABLESPACE "SYSTOOLSTMPSPACE" TO  PUBLIC   ;

COMMIT WORK;

CONNECT RESET;

TERMINATE;

;