# Employee REST API (Scala)

This project implements a RESTful API for managing employee data using Scala and a MySQL database.

## Features

- **Add Employee**: Allows adding a new employee to the database.
- **Get Employee**: Retrieves a specific employee by company and ID.
- **Update Employee**: Updates employee details by company and ID.
- **Delete Employee**: Deletes an employee by ID.
- **Get Employees by Company**: Retrieves all employees for a specific company.

## Routes

The API endpoints are defined as follows:

- **Add Employee**: Adds a new employee.
  - `POST /api/employee/add`

- **Get Employee**: Retrieves a specific employee by company and ID.
  - `GET /api/employee/:company/:id`

- **Update Employee**: Updates employee details by company and ID.
  - `PUT /api/employee/update/:company/:id`

- **Delete Employee**: Deletes an employee by ID.
  - `DELETE /api/employee/delete/:id`

- **Get Employees by Company**: Retrieves all employees for a specific company.
  - `GET /api/employee/:company`



## Database Setup

1. Create a database named `employeedbscala` if it doesn't already exist.
2. Switch to the `employeedbscala` database.
3. Drop the `Employee` table if it exists.
4. Create the `Employee` table with the following schema:

```sql
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

5. Switch back to the employeedbscala database.
6. Verify the table by running the following query:

SELECT * FROM Employee;
