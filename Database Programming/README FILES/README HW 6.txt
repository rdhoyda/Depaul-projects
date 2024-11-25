
# Project Summary

## Overview

This project demonstrates various SQL and PL/SQL techniques for managing and logging database changes in an Oracle environment. It includes creating and using triggers, packages, and handling database operations to maintain data integrity and provide audit logs.

## Technologies Used

- **SQL**: For creating tables, inserting and deleting records, and querying data.
- **PL/SQL**: For creating triggers and packages to handle complex business logic and automate auditing processes.
- **Oracle Database**: The relational database management system used for storing and managing the data.

## Tasks and Solutions

### Task 1: Employee Deletion Logging

**Problem**: Need to log deletions of employee records to maintain an audit trail.

**Solution**: 
- Created a table `Emp_log` to store log details.
- Implemented a trigger `Emp_Delete` that inserts a log entry into `Emp_log` after any deletion in the `Employees` table.
- Verified the trigger by deleting employees with a salary less than 2300 and checking the log entries.

**Learning**: Gained an understanding of after-delete triggers and logging mechanisms.

### Task 2: Detailed Employee Deletion Logging

**Problem**: Need to capture detailed information about deleted employee records.

**Solution**:
- Created a table `Empl_Del_log` to store detailed log entries, including employee ID, name, salary, and manager ID.
- Implemented a row-level trigger `Empl_Del_Row` that captures old values before deletion and inserts them into `Empl_Del_log`.
- Verified the trigger by deleting employees with a specific manager ID and checking the log entries.

**Learning**: Learned to use row-level triggers to capture detailed information before deletion.

### Task 3: Department Change Logging

**Problem**: Need to log changes (insertions, updates, deletions) to the `departments` table, especially focusing on changes to the manager ID.

**Solution**:
- Created a table `Dept_log` to store log entries for department changes.
- Implemented a trigger `Dept_Change` that logs insertions, updates, and deletions in the `departments` table.
- Verified the trigger by performing various operations (insert, update, delete) on the `departments` table and checking the log entries.

**Learning**: Enhanced skills in handling different types of triggers (before insert, before update, before delete) and logging changes in detail.

### Task 4: Department Information Package

**Problem**: Need a reusable and organized way to fetch department information based on department ID or name.

**Solution**:
- Created a package `Dept_Address` with a custom type `DEPT_INFO` to hold department details.
- Implemented two overloaded functions `Dept_Addr` to fetch department information based on either department ID or name.
- Verified the package by retrieving and displaying department information using PL/SQL anonymous blocks.

**Learning**: Gained experience in creating and using PL/SQL packages, custom types, and overloaded functions for organized code and reusable logic.

## Key Learnings

- **Trigger Implementation**: Developed skills in creating and managing triggers to automate auditing processes.
- **Row-Level and Statement-Level Triggers**: Learned the differences and use-cases for both types of triggers.
- **PL/SQL Packages**: Gained proficiency in creating packages to encapsulate related procedures and functions for better organization and reuse.
- **Error Handling in PL/SQL**: Improved error handling techniques in PL/SQL to ensure robust and maintainable code.

## Conclusion

This project provided hands-on experience with advanced SQL and PL/SQL techniques, enhancing the ability to manage and audit database changes effectively. The skills learned from implementing triggers, packages, and error handling are valuable for any database management and development role.
