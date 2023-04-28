# employee_restapi_scala

CREATE DATABASE IF NOT EXISTS employeedbscala;
USE employeedbscala ;
drop table if exists Employee;
CREATE TABLE Employee (
  empId INT PRIMARY KEY AUTO_INCREMENT,
  empCompany VARCHAR(255) NOT NULL,
  empName VARCHAR(255) NOT NULL,
  empSalary DECIMAL(10, 2) NOT NULL,
  empAge INT NOT NULL,
  empCity VARCHAR(255) NOT NULL,
  empEmail VARCHAR(255) NOT NULL,
  empMobile VARCHAR(255) NOT NULL,
  empTeam VARCHAR(255) NOT NULL
);
USE employeedbscala ;
select * from Employee;
