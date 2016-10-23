
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

--Procedure to retrieve customer info
--SELECT * FROM get_customer('fasm22');
CREATE OR REPLACE FUNCTION get_customer(pUser varchar(25)) 
RETURNS SETOF CUSTOMER AS 
$$
SELECT * FROM CUSTOMER WHERE CUSTOMER.username = pUser;
$$ LANGUAGE SQL;

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

--Procedure to delete customer
--SELECT update_customer(201505054);
CREATE OR REPLACE FUNCTION delete_customer(pID int)
RETURNS TEXT AS $$
BEGIN
	IF (SELECT EXISTS(SELECT 1 FROM CUSTOMER WHERE ID_Customer=pID)) THEN
		UPDATE CUSTOMER SET active = False WHERE ID_Customer = pID;
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

--Procedure to retrieve engineer info
--SELECT * FROM get_engineer('fasm22');
DROP FUNCTION get_engineer(character varying);
CREATE OR REPLACE FUNCTION get_engineer(pUser varchar(25)) 
RETURNS TABLE (
	ID_Engineer int,
	Name varchar(25),
	LastName1 varchar(25),
	LastName2 varchar(25),
	Phone varchar(15),
	Email varchar(50),
	Eng_Code varchar(15),
	Username varchar(25),
	Password varchar(25),
	Role varchar(25)
)AS 
$$
SELECT ENGINEER.ID_Engineer,ENGINEER.Name,ENGINEER.LastName1,ENGINEER.LastName2,
	ENGINEER.Phone,ENGINEER.Email,ENGINEER.Eng_Code,ENGINEER.Username,
	ENGINEER.Password,ROLE.Name
FROM ENGINEER JOIN ROLExENGINEER ON ROLExENGINEER.ID_Engineer = ENGINEER.ID_Engineer
JOIN ROLE ON ROLE.ID_Role = ROLExENGINEER.ID_Role WHERE ENGINEER.Username=pUser;
$$ LANGUAGE SQL;

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

--Procedure to delete engineer
--SELECT delete_engineer(201505054);
CREATE OR REPLACE FUNCTION delete_engineer(pID int)
RETURNS TEXT AS $$
BEGIN
	IF (SELECT EXISTS(SELECT 1 FROM ENGINEER WHERE ID_Engineer=pID)) THEN
		UPDATE ENGINEER SET active = False WHERE ID_Engineer = pID;
		RETURN 'SUCCESS';
	ELSE
		RAISE EXCEPTION 'UNEXISTENT ID KEY VIOLATION';
	END IF;
	EXCEPTION
		WHEN unique_violation 
		THEN  RAISE EXCEPTION 'UNIQUE KEY VIOLATION';
		--WHEN undefined_function
		--THEN RAISE EXCEPTION 'UNDEFINED FUNCTION. FUNCTION DOES NOT MATCH ARGUMENTS';
END;
$$ LANGUAGE plpgsql;


--Procedure to insert a new project
--SELECT add_project('Casa de David','Coronado',201505054,2014078784,'','')
CREATE OR REPLACE FUNCTION add_project(pName varchar(50),pLocation varchar(50),pID_Engineer int,pID_Customer int,pComments varchar(255),pDetails varchar(255))
RETURNS TEXT AS $$
BEGIN
	
	INSERT INTO PROJECT VALUES (DEFAULT,pName,pLocation,pID_Engineer,pID_Customer,pComments,pDetails,True,False);
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

--Procedure to delete project
--SELECT delete_project(1);
CREATE OR REPLACE FUNCTION delete_project(pID int)
RETURNS TEXT AS $$
BEGIN
	IF (SELECT EXISTS(SELECT 1 FROM PROJECT WHERE ID_Project=pID)) THEN
		UPDATE PROJECT SET active = False WHERE ID_Project = pID;
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

--Procedure to finish a project
--SELECT complete_project(1)
CREATE OR REPLACE FUNCTION complete_project(pIDProject int)
RETURNS TEXT AS $$
BEGIN
	
	--INSERT INTO PROJECT VALUES (DEFAULT,pName,pLocation,pID_Engineer,pID_Customer,pComments,pDetails,True);
	UPDATE PROJECT SET Completed= True WHERE PROJECT.ID_Project = pIDProject;
	RETURN 'SUCCESS';
	EXCEPTION
		WHEN unique_violation 
		THEN  RAISE EXCEPTION 'UNIQUE KEY VIOLATION';
		WHEN undefined_function
		THEN RAISE EXCEPTION 'UNDEFINED FUNCTION. FUNCTION DOES NOT MATCH ARGUMENTS';
END;
$$ LANGUAGE plpgsql;

--Procedure to insert a new project stage
--SELECT add_project_stage(1,'Cimientos','1/2/16','6/6/17','Cimientos de alta calidad','');
CREATE OR REPLACE FUNCTION add_project_stage(pIDProject int,pStageName varchar(50),pStartDate varchar(50),pEndDate varchar(50),pDetails varchar(255),pComments varchar(255))
RETURNS TEXT AS $$
BEGIN
	
	INSERT INTO PROJECT_STAGE VALUES (DEFAULT,
					(SELECT ID_Stage_name FROM STAGE_NAME WHERE STAGE_NAME.Name = pStageName),
					pIDProject,
					pStartDate::date,
					pEndDate::date,
					pDetails,
					False,
					pComments);
	RETURN 'SUCCESS';
	EXCEPTION
		WHEN invalid_datetime_format
		THEN RAISE EXCEPTION 'INVALID DATETIME FORMAT. PLEASE CHECK INPUT';
		WHEN unique_violation 
		THEN  RAISE EXCEPTION 'UNIQUE KEY VIOLATION';
		WHEN undefined_function
		THEN RAISE EXCEPTION 'UNDEFINED FUNCTION. FUNCTION DOES NOT MATCH ARGUMENTS';
END;
$$ LANGUAGE plpgsql;

--Procedure to finish a stage
--SELECT complete_stage(1)
CREATE OR REPLACE FUNCTION complete_stage(pIDStage int)
RETURNS TEXT AS $$
BEGIN
	
	--INSERT INTO PROJECT VALUES (DEFAULT,pName,pLocation,pID_Engineer,pID_Customer,pComments,pDetails,True);
	UPDATE PROJECT_STAGE SET Completed= True WHERE PROJECT_STAGE.ID_Stage = pIDStage;
	RETURN 'SUCCESS';
	EXCEPTION
		WHEN unique_violation 
		THEN  RAISE EXCEPTION 'UNIQUE KEY VIOLATION';
		WHEN undefined_function
		THEN RAISE EXCEPTION 'UNDEFINED FUNCTION. FUNCTION DOES NOT MATCH ARGUMENTS';
END;
$$ LANGUAGE plpgsql;

--Procedure to insert a new role
--SELECT add_new_role('Conserje');
CREATE OR REPLACE FUNCTION add_new_role(pRole varchar(50))
RETURNS TEXT AS $$
BEGIN
	INSERT INTO ROLE VALUES (DEFAULT,pRole);
	RETURN 'SUCCESS';
	EXCEPTION
		WHEN unique_violation 
		THEN  RAISE EXCEPTION 'UNIQUE KEY VIOLATION';
		WHEN undefined_function
		THEN RAISE EXCEPTION 'UNDEFINED FUNCTION. FUNCTION DOES NOT MATCH ARGUMENTS';
END;
$$ LANGUAGE plpgsql;

--Procedure to get roles
--SELECT get_roles();
DROP FUNCTION IF EXISTS get_roles();
CREATE OR REPLACE FUNCTION get_roles() 
RETURNS TABLE (
	Role varchar(50)
)AS 
$$
SELECT Name FROM ROLE;
$$ LANGUAGE SQL;

--Procedure to insert a stage_name
--SELECT add_new_stage_name('Conserje');
CREATE OR REPLACE FUNCTION add_new_stage_name(pStageName varchar(50))
RETURNS TEXT AS $$
BEGIN
	INSERT INTO STAGE_NAME VALUES (DEFAULT,pStageName);
	RETURN 'SUCCESS';
	EXCEPTION
		WHEN unique_violation 
		THEN  RAISE EXCEPTION 'UNIQUE KEY VIOLATION';
		WHEN undefined_function
		THEN RAISE EXCEPTION 'UNDEFINED FUNCTION. FUNCTION DOES NOT MATCH ARGUMENTS';
END;
$$ LANGUAGE plpgsql;

--Procedure to assign a role to an engineer
--SELECT add_role_to_engineer(304980022,'Ingeniero');
CREATE OR REPLACE FUNCTION add_role_to_engineer(pID int, pRoleName varchar(50))
RETURNS TEXT AS $$
BEGIN
	INSERT INTO ROLExENGINEER VALUES(DEFAULT,pID,(SELECT ID_Role FROM ROLE WHERE ROLE.Name = pRoleName));
	RETURN 'SUCCESS';
	EXCEPTION
		WHEN unique_violation 
		THEN  RAISE EXCEPTION 'UNIQUE KEY VIOLATION';
		WHEN undefined_function
		THEN RAISE EXCEPTION 'UNDEFINED FUNCTION. FUNCTION DOES NOT MATCH ARGUMENTS';
END;
$$ LANGUAGE plpgsql;


DROP FUNCTION IF EXISTS login(varchar(25),varchar(25));
CREATE OR REPLACE FUNCTION login(pUser varchar(25), pPass varchar(25))
RETURNS TABLE(
	ID_C_E int,
	Name varchar(25),
	LastName1 varchar(25),
	LastName2 varchar(25),
	Phone varchar(15),
	Email varchar(50),
	Username varchar(25),
	Password varchar(25),
	Active boolean,
	Eng_Code varchar(15)
)AS $$
BEGIN
	IF (SELECT EXISTS(SELECT 1 FROM CUSTOMER WHERE CUSTOMER.Username = pUser)) THEN
		IF( (SELECT CUSTOMER.Password FROM CUSTOMER WHERE CUSTOMER.Username = pUser) = pPass) THEN
			RETURN QUERY
			SELECT CUSTOMER.ID_Customer,CUSTOMER.Name,CUSTOMER.LastName1,CUSTOMER.LastName2,
				CUSTOMER.Phone,CUSTOMER.Email,CUSTOMER.Username,CUSTOMER.Password,
				CUSTOMER.Active,varchar(15)'0'
			FROM CUSTOMER WHERE CUSTOMER.Username = pUser;
		ELSE
			RAISE EXCEPTION 'CUSTOMER EXISTS BUT PASSWORD IS INCORRECT';
		END IF;
	ELSIF (SELECT EXISTS(SELECT 1 FROM ENGINEER WHERE ENGINEER.Username = pUser)) THEN
		IF( (SELECT ENGINEER.Password FROM ENGINEER WHERE ENGINEER.Username = pUser) = pPass) THEN
			RETURN QUERY
			SELECT ENGINEER.ID_Engineer,ENGINEER.Name,ENGINEER.LastName1,ENGINEER.LastName2,
				ENGINEER.Phone,ENGINEER.Email,ENGINEER.Username,ENGINEER.Password,
				ENGINEER.Active,(SELECT ROLE.Name FROM ENGINEER JOIN ROLExENGINEER ON ENGINEER.ID_Engineer = ROLExENGINEER.ID_Engineer JOIN ROLE ON ROLE.ID_Role = ROLExENGINEER.ID_Role
				WHERE ENGINEER.Username = pUser)
			FROM ENGINEER WHERE ENGINEER.Username = pUser;
		ELSE
			RAISE EXCEPTION 'ENGINEER EXISTS BUT PASSWORD IS INCORRECT';
		END IF;
	END IF;

END;
$$ LANGUAGE plpgsql;



--Procedure to get all the products from an specific stage
--SELECT get_products_from_stage(202);
DROP FUNCTION IF EXISTS get_products_from_stage(int);
CREATE OR REPLACE FUNCTION get_products_from_stage(pID int)
RETURNS TABLE(
	Name varchar(50),
	Quantity int,
	Price int,
	Purchased boolean
)AS $$
BEGIN
	RETURN QUERY
	SELECT PRODUCT.Name,PRODUCTxSTAGE.Quantity,PRODUCTxSTAGE.Price,PRODUCTxSTAGE.Purchased
	FROM PRODUCT JOIN PRODUCTxSTAGE ON PRODUCTxSTAGE.ID_Product = PRODUCT.ID_Product JOIN PROJECT_STAGE ON PROJECT_STAGE.ID_Stage = PRODUCTxSTAGE.ID_STAGE
	WHERE PROJECT_STAGE.ID_Stage = pID;
END;
$$ LANGUAGE plpgsql;

--Procedure to get all the projects from a customer or a engineer
--SELECT get_stages_from_project(100);
DROP FUNCTION IF EXISTS get_projects_from_generic(int,int);
CREATE OR REPLACE FUNCTION get_projects_from_generic(pMode int,pID int)
RETURNS TABLE(
	ID_Project int,
	Project_Name varchar(50)
	
)AS $$
BEGIN
	IF (pMode = 0) THEN
		RETURN QUERY
		SELECT PROJECT.ID_Project,PROJECT.Name 
		FROM PROJECT WHERE PROJECT.ID_Customer = pID;
	ELSIF (pMode = 1) THEN
		RETURN QUERY
		SELECT PROJECT.ID_Project,PROJECT.Name 
		FROM PROJECT WHERE PROJECT.ID_Engineer = pID;
	END IF;
	
END;
$$ LANGUAGE plpgsql;

--Procedure to get all the stages from a project
--SELECT get_stages_from_project(100);
DROP FUNCTION IF EXISTS get_stages_from_project(int);
CREATE OR REPLACE FUNCTION get_stages_from_project(pID_Project int)
RETURNS TABLE(
	ID_Project_Stage int,
	Stage_Name varchar(50),
	Completed boolean
	
)AS $$
BEGIN
	RETURN QUERY
	SELECT PROJECT_STAGE.ID_Stage,STAGE_NAME.Name,PROJECT_STAGE.Completed 
	FROM PROJECT_STAGE JOIN STAGE_NAME ON PROJECT_STAGE.ID_Stage_Name = STAGE_NAME.ID_Stage_Name
	WHERE PROJECT_STAGE.ID_Project = pID_Project;
END;
$$ LANGUAGE plpgsql;

--Procedure to get all the information from a stage
--SELECT get_stages_from_project(100);
DROP FUNCTION IF EXISTS get_info_from_stage(int);
CREATE OR REPLACE FUNCTION get_info_from_stage(pID_Stage int)
RETURNS SETOF PROJECT_STAGE AS $$
BEGIN
	RETURN QUERY
	SELECT * FROM PROJECT_STAGE WHERE ID_Stage = pID_Stage;
END;
$$ LANGUAGE plpgsql;

--Procedure to get all the projects that a stage will begin in the next 2 weeks
--SELECT get_projects_next_weeks();
DROP FUNCTION IF EXISTS get_projects_next_weeks();
CREATE OR REPLACE FUNCTION get_projects_next_weeks()
RETURNS TABLE(
	ID_Project int,
	Name varchar(50),
	Location varchar(50),
	Engineer text,
	Completed boolean,
	Comments varchar(255),
	Details varchar(255),
	NextStage varchar(50),
	Start_Date date
) AS $$
BEGIN
	RETURN QUERY
	SELECT PROJECT.ID_Project,PROJECT.Name,PROJECT.Location,
		(ENGINEER.Name || ' ' || ENGINEER.LastName1 || ' ' || ENGINEER.LastName2),
		PROJECT.Completed,PROJECT.Comments,PROJECT.Details,STAGE_NAME.Name,
		PROJECT_STAGE.Start_Date FROM PROJECT JOIN PROJECT_STAGE
		ON PROJECT_STAGE.ID_Project = PROJECT.ID_Project JOIN ENGINEER ON
		PROJECT.ID_Engineer=ENGINEER.ID_Engineer JOIN STAGE_NAME ON 
		STAGE_NAME.ID_Stage_Name = PROJECT_STAGE.ID_Stage_Name 
		WHERE (PROJECT_STAGE.Start_Date - current_date <= 15 AND PROJECT_STAGE.Start_Date - current_date > 0);
END;
$$ LANGUAGE plpgsql;

--Procedure to get all products information from a project
--SELECT get_products_info_from_project();
DROP FUNCTION IF EXISTS get_products_info_from_project(int);
CREATE OR REPLACE FUNCTION get_products_info_from_project(pID_Project int)
RETURNS TABLE(
	ID_Stage int,
	Stage_Name varchar(50),
	ID_Product int,
	Product_name varchar(50),
	Price int,
	Quantity int
) AS $$
BEGIN
	RETURN QUERY
	SELECT PROJECT_STAGE.ID_Stage,STAGE_NAME.Name, PRODUCT.ID_Product,
		PRODUCT.Name, PRODUCT.Price, PRODUCTxSTAGE.Quantity
		FROM PROJECT JOIN PROJECT_STAGE
		ON PROJECT_STAGE.ID_Project = PROJECT.ID_Project JOIN STAGE_NAME ON 
		STAGE_NAME.ID_Stage_Name = PROJECT_STAGE.ID_Stage_Name JOIN PRODUCTxSTAGE
		ON PRODUCTxSTAGE.ID_Stage = PROJECT_STAGE.ID_STAGE JOIN PRODUCT ON PRODUCT.ID_Product = 
		PRODUCTxSTAGE.ID_Product WHERE (PROJECT.ID_Project = pID_Project);
END;
$$ LANGUAGE plpgsql;


--Procedure to get all the projects that will use an especific material in the next 15 days
--SELECT get_projects_next_weeks();
DROP FUNCTION IF EXISTS get_projects_by_material(varchar(50));
CREATE OR REPLACE FUNCTION get_projects_by_material(pMaterial varchar(50))
RETURNS TABLE(
	ID_Project int,
	Name varchar(50),
	Location varchar(50),
	Engineer text,
	Completed boolean,
	Comments varchar(255),
	Details varchar(255),
	NextStage varchar(50),
	Start_Date date,
	Material_Name varchar(50),
	Quantity int
	
) AS $$
BEGIN
	RETURN QUERY
	SELECT PROJECT.ID_Project,PROJECT.Name,PROJECT.Location,
		(ENGINEER.Name || ' ' || ENGINEER.LastName1 || ' ' || ENGINEER.LastName2),
		PROJECT.Completed,PROJECT.Comments,PROJECT.Details,STAGE_NAME.Name,
		PROJECT_STAGE.Start_Date, PRODUCT.Name, PRODUCTxSTAGE.Quantity

		FROM PROJECT JOIN PROJECT_STAGE
		ON PROJECT_STAGE.ID_Project = PROJECT.ID_Project JOIN ENGINEER ON
		PROJECT.ID_Engineer=ENGINEER.ID_Engineer JOIN STAGE_NAME ON 
		STAGE_NAME.ID_Stage_Name = PROJECT_STAGE.ID_Stage_Name JOIN PRODUCTxSTAGE
		ON PRODUCTxSTAGE.ID_Stage = PROJECT_STAGE.ID_STAGE JOIN PRODUCT ON PRODUCT.ID_Product = 
		PRODUCTxSTAGE.ID_Product WHERE (PROJECT_STAGE.Start_Date - current_date <= 15 AND 
			PROJECT_STAGE.Start_Date - current_date > 0 AND
			PRODUCT.Name = pMaterial
			);
END;
$$ LANGUAGE plpgsql;



/******************* TRIGGERS ********************************/
--Trigger to prevent the engineer deletion if projects depend on him
DROP TRIGGER IF EXISTS eng_stamp ON ENGINEER;
DROP FUNCTION IF EXISTS eng_trig();
CREATE OR REPLACE FUNCTION eng_trig() RETURNS TRIGGER AS $eng_trig$
BEGIN
	IF(OLD.Active != NEW.Active) THEN
		IF (SELECT EXISTS(SELECT 1 FROM PROJECT WHERE ID_Engineer = OLD.ID_Engineer)) THEN
			RAISE EXCEPTION 'CANT DELETE ENGINEER IF PROJETS DEPEND ON HIM';
			RETURN NULL;
		ELSE
			RETURN NEW;
		END IF;
	ELSIF(OLD.Active = False) THEN
		RAISE EXCEPTION 'ENGINEER WAS ALREADY INACTIVE';
		RETURN NULL;
	ELSE
		RETURN NEW;
	END IF;
END;
$eng_trig$ LANGUAGE plpgsql;


CREATE TRIGGER eng_stamp BEFORE UPDATE ON ENGINEER
	FOR EACH ROW EXECUTE PROCEDURE eng_trig();

--Trigger to prevent incorrect input of dates (initial and end date)
DROP TRIGGER IF EXISTS date_stamp ON PROJECT_STAGE CASCADE;
DROP FUNCTION IF EXISTS date_trig() CASCADE;
CREATE OR REPLACE FUNCTION date_trig() RETURNS TRIGGER AS $date_trig$
BEGIN
	IF(NEW.End_Date < NEW.Start_Date) THEN
		RAISE EXCEPTION 'END DATE IS BEFORE START DATE. PLEASE CHECK INPUT';
		RETURN NULL;
	ELSIF(NEW.End_Date = NEW.Start_Date) THEN
		RAISE EXCEPTION 'END DATE AND START DATE ARE EQUAL';
		RETURN NULL;
	ELSE
		RETURN NEW;
	END IF;
END;
$date_trig$ LANGUAGE plpgsql;


CREATE TRIGGER date_stamp BEFORE INSERT ON PROJECT_STAGE
	FOR EACH ROW EXECUTE PROCEDURE date_trig();

--Trigger to prevent updating or deleting stages
DROP TRIGGER IF EXISTS prevent_st_name_func ON STAGE_NAME CASCADE;
DROP FUNCTION IF EXISTS prevent_st_name_func() CASCADE;
CREATE OR REPLACE FUNCTION prevent_st_name_func() RETURNS TRIGGER AS $stname$
BEGIN
	IF(OLD.ID_Stage_Name <= 18) THEN
		RAISE EXCEPTION 'CANT DELETE PREDEFINED STAGE NAME';
		RETURN NULL;
	ELSE
		RETURN NEW;
	END IF;
END;
$stname$ LANGUAGE plpgsql;


CREATE TRIGGER prevent_del_stage_name BEFORE DELETE ON STAGE_NAME
	FOR EACH ROW EXECUTE PROCEDURE prevent_st_name_func();