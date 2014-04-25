DROP PROCEDURE generate_users@
DROP TYPE first_names@
DROP TYPE last_names@



CREATE TYPE first_names AS VARCHAR(20) ARRAY[]@
CREATE TYPE last_names AS VARCHAR(20) ARRAY[]@


CREATE PROCEDURE generate_users LANGUAGE SQL
	BEGIN
	
		DECLARE malefnames first_names;
		DECLARE malelnames last_names;
		DECLARE femalefnames first_names;
		DECLARE femalelnames last_names;
		DECLARE lecturerfnames first_names;
		DECLARE lecturerlnames last_names;
		
		
		DECLARE i, j, malefnames_size, malelnames_size, femalefnames_size, femalelnames_size, lecturerfnames_size, lecturerlnames_size INTEGER;
		
		SET malefnames = ARRAY['Pawe�', 'Zygmunt', 'Rafa�','Tomasz','Robert', 'Adam', 'Lech', 'Jaros�aw'];
		SET malelnames = ARRAY['Kowalski', 'Wi�niewski', 'D�browski','Wo�niak','Wojciechowski',
		'Straub','Michalski','Krawczyk','Marciniak','Wasilewski'];
		
		
		SET femalefnames = ARRAY['Anna', 'Joanna', 'Katarzyna','Ma�gorzata','Krystyna', 'Natalia', 'Marta'];
		SET femalelnames = ARRAY['Kowalska', 'Krawczyk', 'Wo�niak','D�browska','Socha', 'Kowalska', 'Kr�l'];
		
		SET i=1;
		SET j=1;
		SET malefnames_size = CARDINALITY(malefnames);
		SET malelnames_size = CARDINALITY(malelnames);
		SET femalefnames_size = CARDINALITY(femalefnames);
		SET femalelnames_size = CARDINALITY(femalelnames);
		
		WHILE i <= malefnames_size DO
			WHILE j <= malelnames_size DO
				INSERT INTO DB2INST1.USERS VALUES (
					NEXT VALUE FOR USER_SEQ,
					0,
					malefnames[i] concat '.' concat malelnames[j] concat '@agh.edu.pl',
					malefnames[i],
					malelnames[j],
					malefnames[i] concat '.' concat malelnames[j],
					'',
					'123',
					'',
					'STUDENT'
				);
				SET j = j + 1;
			END WHILE;
			SET j = 1;
			SET i = i + 1;
		END WHILE;
		
		
		SET i=1;
		SET j=1;
		
		
		WHILE i <= femalefnames_size DO
			WHILE j <= femalelnames_size DO
				INSERT INTO DB2INST1.USERS VALUES (
					NEXT VALUE FOR USER_SEQ,
					0,
					femalefnames[i] concat '.' concat femalelnames[j] concat '@agh.edu.pl',
					femalefnames[i],
					femalelnames[j],
					femalefnames[i] concat '.' concat femalelnames[j],
					'',
					'123',
					'',
					'STUDENT'
				);
				SET j = j + 1;
			END WHILE;
			SET j = 1;
			SET i = i + 1;
		END WHILE;
		
		
		
		
		SET lecturerfnames = ARRAY['Anna', 'Tomasz', 'Karol','Krystyna', 'Marta'];
		SET lecturerlnames = ARRAY['Nowak', 'Krawczyk', 'Mazur','Stpie�','Dudek', 'Pawlak', 'Duda'];
		SET lecturerfnames_size = CARDINALITY(lecturerfnames);
		SET lecturerlnames_size = CARDINALITY(lecturerlnames);
		SET i=1;
		SET j=1;
		
		WHILE i <= lecturerfnames_size DO
			WHILE j <= lecturerlnames_size DO
				INSERT INTO DB2INST1.USERS VALUES (
					NEXT VALUE FOR USER_SEQ,
					0,
					lecturerfnames[i] concat '.' concat lecturerlnames[j] concat '@agh.edu.pl',
					lecturerfnames[i],
					lecturerlnames[j],
					lecturerfnames[i] concat '.' concat lecturerlnames[j],
					'',
					'123',
					'',
					'LECTURER'
				);
				SET j = j + 1;
			END WHILE;
			SET j = 1;
			SET i = i + 1;
		END WHILE;
	
		
		INSERT INTO DB2INST1.USERS VALUES ( NEXT VALUE FOR USER_SEQ, 0, 'admin','admin','admin','admin','admin','123','admin','ADMIN');
		
		
END@