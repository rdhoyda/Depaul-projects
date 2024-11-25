
# Summary of Tasks and Project

## Overview
This project involves developing and testing various PL/SQL procedures, functions, and declarations for an employee management system. The tasks include calculating bonuses based on employee details, retrieving job IDs using overloaded functions, and comparing employee names using PL/SQL records. The primary goal is to showcase proficiency in PL/SQL and SQL concepts while handling real-world scenarios related to employee data management.

## Technologies Used
- **PL/SQL (Procedural Language/Structured Query Language):** A procedural extension for SQL used for writing code to execute complex operations on an Oracle database.
- **SQL (Structured Query Language):** Used for querying and managing data in the Oracle database.
- **Oracle Database:** The relational database management system (RDBMS) where employee data is stored and managed.

## Problems Solved
1. **Calculating Employee Bonuses:**
   - **Procedures:**
     - `Emply_Bonus`: A procedure to calculate bonuses based on salary and years of service.
     - `Emly_Bonus`: A modified version of the `Emply_Bonus` procedure.
   - **Functions:**
     - `FN_Bonus`: A function to calculate and return the bonus for a given employee.
     - `FN_Bonus (with record type)`: A function returning a record type containing employee details and the calculated bonus.
   - **Tasks:**
     - Calculating and displaying bonuses for employees in department 60.
     - Summing up the total bonus for the department.

2. **Retrieving Job IDs:**
   - **Overloaded Functions:**
     - `Emp_Job (emp_id number)`: Retrieves job ID based on employee ID.
     - `Emp_Job (emp_email varchar2)`: Retrieves job ID based on employee email.
   - **Tasks:**
     - Demonstrating the usage of overloaded functions to retrieve job IDs using either employee ID or email.

3. **Comparing Employee Names:**
   - **Record Type:**
     - `emp_name`: A record type to store employee first and last names.
   - **Tasks:**
     - Comparing the names of two employees to check if they are the same.

## Learning Outcomes
- **Proficiency in PL/SQL:**
  - Gained hands-on experience in writing and executing PL/SQL procedures and functions.
  - Learned to handle exceptions, use cursors, and work with record types in PL/SQL.
- **Understanding of SQL:**
  - Improved skills in writing complex SQL queries for data retrieval and manipulation.
  - Learned to use SQL functions like `TO_DATE`, `FLOOR`, and `MONTHS_BETWEEN`.
- **Practical Application:**
  - Applied theoretical knowledge to solve practical problems related to employee data management.
  - Understood the importance of modular programming by using procedures and functions to simplify complex operations.

## Summary for Resume
- **Project Name:** Employee Management System Using PL/SQL
- **Technologies Used:** PL/SQL, SQL, Oracle Database
- **Description:**
  - Developed PL/SQL procedures and functions to calculate employee bonuses based on salary and years of service.
  - Created overloaded functions to retrieve job IDs using either employee ID or email.
  - Implemented record types to compare employee names and manage employee data efficiently.
- **Key Achievements:**
  - Successfully calculated and displayed bonuses for employees in a specific department.
  - Demonstrated proficiency in handling PL/SQL exceptions, cursors, and record types.
  - Applied modular programming principles to create reusable and efficient code for data management tasks.
