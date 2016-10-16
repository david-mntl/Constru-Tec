
--Procedure to insert a new customer
--SELECT add_customer(201505054,'Ricardo','Flores','Obando','88855692','ricardo@gmail.com','richard','123');
CREATE OR REPLACE FUNCTION add_customer(ID int, name varchar(25),last1 varchar(25),last2 varchar(25),phone varchar(15),mail varchar(50),username varchar(25), pass varchar(25))
RETURNS TEXT AS $$
BEGIN
	INSERT INTO CUSTOMER VALUES (ID,name,last1,last2,phone,mail,username,pass,True);
	RETURN 'SUCCESS';
	EXCEPTION
		--WHEN unique_violation 
		--THEN RETURN 'UNIQUE KEY VIOLATION';
		WHEN unique_violation 
		THEN  RAISE EXCEPTION 'UNIQUE KEY VIOLATION';
		WHEN undefined_function
		THEN RAISE EXCEPTION 'UNDEFINED FUNCTION. FUNCTION DOES NOT MATCH ARGUMENTS';
END;
$$ LANGUAGE plpgsql;

--Procedure to update customer info
--SELECT update_customer(201505054,'Jose','Flores','Obando','88855692','ricardo@gmail.com','richard','123');
CREATE OR REPLACE FUNCTION update_customer(pID int, pName_eng varchar(25),pLast1 varchar(25),pLast2 varchar(25),pPhone varchar(15),pMail varchar(50),pUsername varchar(25), pPass varchar(25))
RETURNS TEXT AS $$
BEGIN
	IF (SELECT EXISTS(SELECT 1 FROM CUSTOMER WHERE ID_Customer=pID)) THEN
		UPDATE CUSTOMER SET ID_Customer = pID, 
					Name = pName_eng,
					LastName1 = pLast1,
					LastName2 = pLast2, 
					Phone = pPhone, 
					email = pMail, 
					username = pUsername, 
					password = pPass , 
					active = True
		WHERE ID_Customer = pID;
		RETURN 'SUCCESS';
	ELSE
		RAISE EXCEPTION 'UNEXISTENT ID KEY VIOLATION';
	END IF;
	EXCEPTION
		WHEN unique_violation 
		THEN  RAISE EXCEPTION 'UNIQUE KEY VIOLATION';
		WHEN undefined_function
		THEN RAISE EXCEPTION 'UNDEFINED FUNCTION. FUNCTION DOES NOT MATCH ARGUMENTS';
END;
$$ LANGUAGE plpgsql;

--Procedure to insert a new engineer
--SELECT add_engineer(201505054,'El inge','Vargas','Campos,','25587878','vargas@gmail.com','ABC159','campos','123','Ingeniero');
CREATE OR REPLACE FUNCTION add_engineer(ID int, name varchar(25),last1 varchar(25),last2 varchar(25),phone varchar(15),mail varchar(50),code varchar(15),username varchar(25), pass varchar(25),pRole varchar(25))
RETURNS TEXT AS $$
BEGIN
	
	INSERT INTO ENGINEER VALUES (ID,name,last1,last2,phone,mail,code,username,pass,True);
	INSERT INTO ROLExENGINEER VALUES(DEFAULT,ID,(SELECT ID_Role FROM ROLE WHERE ROLE.Name = pRole));
	RETURN 'SUCCESS';
	EXCEPTION
		WHEN unique_violation 
		THEN  RAISE EXCEPTION 'UNIQUE KEY VIOLATION';
		WHEN undefined_function
		THEN RAISE EXCEPTION 'UNDEFINED FUNCTION. FUNCTION DOES NOT MATCH ARGUMENTS';
END;
$$ LANGUAGE plpgsql;

--Procedure to update engineer info
--SELECT update_engineer(201505054,'El inge','Vargas','Campos,','25587878','vargas@gmail.com','ABC159','campos','123');
CREATE OR REPLACE FUNCTION update_engineer(pID int, pName_eng varchar(25),pLast1 varchar(25),pLast2 varchar(25),pPhone varchar(15),pMail varchar(50),pCode varchar(15),pUsername varchar(25), pPass varchar(25))
RETURNS TEXT AS $$
BEGIN
	IF (SELECT EXISTS(SELECT 1 FROM ENGINEER WHERE ID_Engineer=pID)) THEN
		UPDATE ENGINEER SET ID_Engineer = pID, 
					Name = pName_eng,
					LastName1 = pLast1,
					LastName2 = pLast2, 
					Phone = pPhone, 
					email = pMail,
					eng_code= pCode, 
					username = pUsername, 
					password = pPass , 
					active = True
		WHERE ID_Engineer = pID;
		RETURN 'SUCCESS';
	ELSE
		RAISE EXCEPTION 'UNEXISTENT ID KEY VIOLATION';
	END IF;
	
	EXCEPTION
		WHEN unique_violation 
		THEN  RAISE EXCEPTION 'UNIQUE KEY VIOLATION';
		WHEN undefined_function
		THEN RAISE EXCEPTION 'UNDEFINED FUNCTION. FUNCTION DOES NOT MATCH ARGUMENTS';
END;
$$ LANGUAGE plpgsql;


--Procedure to insert a new project
--SELECT add_project('Casa de David','Coronado',201505054,2014078784,'','')
CREATE OR REPLACE FUNCTION add_project(pName varchar(50),pLocation varchar(50),pID_Engineer int,pID_Customer int,pComments varchar(255),pDetails varchar(255))
RETURNS TEXT AS $$
BEGIN
	
	INSERT INTO PROJECT VALUES (DEFAULT,pName,pLocation,pID_Engineer,pID_Customer,pComments,pDetails,True);
	RETURN 'SUCCESS';
	EXCEPTION
		WHEN unique_violation 
		THEN  RAISE EXCEPTION 'UNIQUE KEY VIOLATION';
		WHEN undefined_function
		THEN RAISE EXCEPTION 'UNDEFINED FUNCTION. FUNCTION DOES NOT MATCH ARGUMENTS';
END;
$$ LANGUAGE plpgsql;

--Procedure to insert add comments to a project
--SELECT add_comment_to_project(1,"Esto es un comentario de prueba")
CREATE OR REPLACE FUNCTION add_comment_to_project(pIDProject int, pComments varchar(255))
RETURNS TEXT AS $$
BEGIN
	
	--INSERT INTO PROJECT VALUES (DEFAULT,pName,pLocation,pID_Engineer,pID_Customer,pComments,pDetails,True);
	UPDATE PROJECT SET Comments= ((SELECT PROJECT.Comments FROM PROJECT WHERE PROJECT.ID_Project = pIDProject) || pComments )WHERE PROJECT.ID_Project = pIDProject;
	RETURN 'SUCCESS';
	EXCEPTION
		WHEN unique_violation 
		THEN  RAISE EXCEPTION 'UNIQUE KEY VIOLATION';
		WHEN undefined_function
		THEN RAISE EXCEPTION 'UNDEFINED FUNCTION. FUNCTION DOES NOT MATCH ARGUMENTS';
END;
$$ LANGUAGE plpgsql;

--Procedure to delete comments from a project
--SELECT delete_comment_from_project(1)
CREATE OR REPLACE FUNCTION delete_comment_from_project(pIDProject int)
RETURNS TEXT AS $$
BEGIN
	
	--INSERT INTO PROJECT VALUES (DEFAULT,pName,pLocation,pID_Engineer,pID_Customer,pComments,pDetails,True);
	UPDATE PROJECT SET Comments= '' WHERE PROJECT.ID_Project = pIDProject;
	RETURN 'SUCCESS';
	EXCEPTION
		WHEN unique_violation 
		THEN  RAISE EXCEPTION 'UNIQUE KEY VIOLATION';
		WHEN undefined_function
		THEN RAISE EXCEPTION 'UNDEFINED FUNCTION. FUNCTION DOES NOT MATCH ARGUMENTS';
END;
$$ LANGUAGE plpgsql;


