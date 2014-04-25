DROP PROCEDURE generate_mails@


CREATE PROCEDURE generate_mails LANGUAGE SQL
BEGIN
	DECLARE i INTEGER;
	DECLARE currentUid INTEGER;
	DECLARE no_more_rows CONDITION FOR SQLSTATE '02000';
	DECLARE exitcode INTEGER DEFAULT 0;
	DECLARE csr CURSOR FOR select uniqueid as uid FROM users;
	DECLARE CONTINUE HANDLER FOR no_more_rows SET exitcode = 1;
	
	SET i=1;

	OPEN csr;
   	REPEAT
     FETCH FROM csr INTO currentUid;
     	INSERT INTO messages VALUES (NEXT VALUE FOR MESSAGE_SEQ, 'Testowa wiadomosc', current timestamp ,
     			'NEW',0,0, 'Testowa wiadomosc' || CAST(i AS CHAR(20)), 163911550, currentUid);
     	SET i=i+1;
     UNTIL exitcode = 1
   	END REPEAT;
   	CLOSE csr;

		
END@
