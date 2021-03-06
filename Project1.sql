DROP TABLE REQUESTS;
DROP TABLE EMPLOYEES;
DROP TABLE REQUEST_STATUS;
DROP TABLE REQUEST_TYPE;
DROP TABLE JOB_ROLES;
DROP SEQUENCE EMPLOYEE_PK_SEQ;
DROP SEQUENCE REQUEST_PK_SEQ;

CREATE TABLE REQUEST_STATUS (
R_STATUS_ID NUMBER,
R_STATUS VARCHAR2 (10),  
CONSTRAINT R_STATUS_PK PRIMARY KEY (R_STATUS_ID)
);

CREATE TABLE REQUEST_TYPE (
R_TYPE_ID NUMBER,
R_TYPE VARCHAR2 (10),
CONSTRAINT R_TYPE_PK PRIMARY KEY (R_TYPE_ID )
);

CREATE TABLE JOB_ROLES (
JOB_ROLE_ID NUMBER,
JOB_ROLE VARCHAR2(10),
CONSTRAINT JOB_ROLE_PK PRIMARY KEY (JOB_ROLE_ID) 
);
/


--YAY!!!!
CREATE TABLE EMPLOYEES(
EMPLOYEE_ID NUMBER,
JOB_ID NUMBER,
USER_NAME VARCHAR2(50) UNIQUE NOT NULL,
PASS_WORD VARCHAR2(50),
FIRSTNAME VARCHAR2(50),
LASTNAME VARCHAR2(50),
EMAIL VARCHAR2(150) NOT NULL,
    
CONSTRAINT EMPLOYEE_PK PRIMARY KEY (EMPLOYEE_ID),
CONSTRAINT EMPLOYEE_UNv1 UNIQUE(USER_NAME, EMAIL),
CONSTRAINT JOB_ID FOREIGN KEY (JOB_ID) REFERENCES JOB_ROLES (JOB_ROLE_ID)
    );
/



CREATE TABLE REQUESTS(
REQUEST_ID INTEGER PRIMARY KEY,
AMOUNT        NUMBER,
SUBMITTED     VARCHAR2 (20),
RESEOLVED      VARCHAR2 (20),
DESCRIPTN   VARCHAR2 (250),
RECIEPT       BLOB,
R_AUTHOR        NUMBER,
R_RESOLVER      NUMBER,
R_STATUS_ID     NUMBER,
R_TYPE_ID       NUMBER
);
/

ALTER TABLE REQUESTS ADD 
CONSTRAINT EMPLOYEE_FK_AUTH FOREIGN KEY (R_AUTHOR)
REFERENCES EMPLOYEES (EMPLOYEE_ID);

ALTER TABLE REQUESTS ADD 
CONSTRAINT EMPLOYEE_FK_RESOLVER FOREIGN KEY (R_RESOLVER) 
REFERENCES EMPLOYEES (EMPLOYEE_ID);

ALTER TABLE REQUESTS ADD 
CONSTRAINT REQUEST_STATUS_FK FOREIGN KEY (R_STATUS_ID) 
REFERENCES REQUEST_STATUS (R_STATUS_ID);

ALTER TABLE REQUESTS ADD 
CONSTRAINT REQUEST_TYPE_FK FOREIGN KEY (R_TYPE_ID) 
REFERENCES REQUEST_TYPE (R_TYPE_ID);


CREATE SEQUENCE EMPLOYEE_PK_SEQ
MINVALUE 1
MAXVALUE 99999999
INCREMENT BY 1
START WITH 1;
/
CREATE SEQUENCE REQUEST_PK_SEQ
MINVALUE 1
MAXVALUE 99999999
INCREMENT BY 1
START WITH 1;
/

CREATE OR REPLACE TRIGGER EMP_PK_TRIGGER
BEFORE INSERT ON EMPLOYEES
FOR EACH ROW
BEGIN
    SELECT EMPLOYEE_PK_SEQ.NEXTVAL
    INTO :NEW.EMPLOYEE_ID
    FROM DUAL;
END;
/

CREATE OR REPLACE TRIGGER REQUEST_PK_TRIGGER
BEFORE INSERT ON REQUESTS
FOR EACH ROW
BEGIN
    SELECT REQUEST_PK_SEQ.NEXTVAL
    INTO :NEW.REQUEST_ID
    FROM DUAL;
END;
/


INSERT INTO REQUEST_TYPE VALUES (1, 'lodging');
INSERT INTO REQUEST_TYPE VALUES (2, 'travel');
INSERT INTO REQUEST_TYPE VALUES (3, 'food');
INSERT INTO REQUEST_TYPE VALUES (4, 'other');

INSERT INTO REQUEST_STATUS VALUES (1, 'pending');
INSERT INTO REQUEST_STATUS VALUES (2, 'approved');
INSERT INTO REQUEST_STATUS VALUES (3, 'denied');


INSERT INTO JOB_ROLES VALUES (1, 'manager');
INSERT INTO JOB_ROLES VALUES (2, 'employee');


COMMIT; 

INSERT INTO EMPLOYEES VALUES (0, 2, 'shawkurr', '12345', 'Shawn', 'Currey', 'shawn.m.currey@gmail.com');
INSERT INTO EMPLOYEES VALUES (0, 1, 'shaWKING', '13346', 'Shane', 'Cracts', 'shawking@gmail.com');
INSERT INTO EMPLOYEES VALUES (0, 1, 'Edwinna', '11111', 'Edgar', 'Padillo', 'pads@gmail.com');

INSERT INTO REQUESTS VALUES (0, 50, '2019-3-1', 2019, 'I had a steak dinner.', null, 1, 2, 3, 3);
INSERT INTO REQUESTS VALUES (0, 100, '2019-3-1', 2019, 'I had stayed at work', null, 1, 2, 2, 4);

SELECT * FROM EMPLOYEES JOIN JOB_ROLES ON EMPLOYEES.EMPLOYEE_ID = JOB_ROLES.JOB_ROLE_ID WHERE USER_NAME = 'shawkurr';

SELECT * FROM EMPLOYEES JOIN JOB_ROLES ON EMPLOYEES.EMPLOYEE_ID = JOB_ROLES.JOB_ROLE_ID WHERE USER_NAME = 'shaWKING';
