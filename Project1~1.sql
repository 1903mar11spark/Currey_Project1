--Yay for creating the Project1 database!!!!

CREATE TABLE EMPLOYEES(
    USER_ID INTEGER PRIMARY KEY,
    FIRSTNAME VARCHAR2(100),
    LASTNAME VARCHAR2(100),
    USERNAME VARCHAR2(100) NOT NULL,
    PASS_WORD VARCHAR2(100) NOT NULL
);
/

CREATE TABLE MANAGERS(
    USER_ID INTEGER PRIMARY KEY,
    FIRSTNAME VARCHAR2(100),
    LASTNAME VARCHAR2(100),
    USERNAME VARCHAR2(100) NOT NULL,
    PASS_WORD VARCHAR2(100) NOT NULL
);
/