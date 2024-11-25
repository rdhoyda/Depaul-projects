# Project Summary

## Overview

This project involved developing a series of PL/SQL procedures and scripts to perform various tasks related to employee data management in a database. It demonstrates proficiency with PL/SQL, including the use of cursors, control structures, and SQL operations to handle employee records and generate reports.

## Technologies Used

- **Oracle PL/SQL**: Utilized for writing and executing scripts to handle data manipulation and retrieval.
- **SQL**: Used for querying and updating database tables.
- **DBMS_OUTPUT**: Employed to display output from PL/SQL procedures for debugging and reporting.

## Key Tasks and Solutions

1. **Generating Employee Reports**:
   - **Task**: Create formatted reports of employees with salaries below a certain threshold.
   - **Solution**: Implemented cursors and `DBMS_OUTPUT` to print employee details in a tabular format. Used both explicit and implicit cursors to achieve the desired output.

2. **Calculating Bonuses**:
   - **Task**: Compute and display bonuses for employees based on their salary and commission rates.
   - **Solution**: Developed PL/SQL blocks using cursors and conditional logic (`IF` statements and `CASE` statements) to determine bonus amounts based on salary and commission brackets. Implemented nested conditional logic to handle different ranges of salaries.

3. **Exploring Table Metadata**:
   - **Task**: Retrieve and display metadata for specified tables, including column names, data types, and lengths.
   - **Solution**: Used parameterized cursors to dynamically fetch and display metadata for different tables. Demonstrated the use of cursors with parameters to loop through results and present them in a formatted output.

4. **Updating Records with Cursors**:
   - **Task**: Update employee salaries based on a specific condition and display old and new salaries.
   - **Solution**: Employed cursors for row-level operations (`FOR UPDATE` clause) to update records within a loop. Displayed changes using `DBMS_OUTPUT` and ensured that updates were rolled back for demonstration purposes.

5. **Using Cursor Variables**:
   - **Task**: Utilize cursor variables to fetch and display employee data based on different criteria.
   - **Solution**: Created and used cursor variables (`REF CURSOR`) to dynamically handle different queries. Demonstrated fetching and displaying results for various conditions using cursor variables to streamline data handling.

## Learning Outcomes

- **Mastery of PL/SQL**: Gained expertise in writing and managing PL/SQL code, including the use of cursors, conditional logic, and output formatting.
- **SQL Proficiency**: Enhanced skills in SQL for querying and updating database tables.
- **Debugging and Output Management**: Learned to effectively use `DBMS_OUTPUT` for debugging and presenting results in a user-friendly format.
- **Dynamic Query Handling**: Acquired experience with dynamic SQL and cursor variables to handle different data retrieval scenarios efficiently.

This project illustrates advanced capabilities in PL/SQL and SQL, showcasing problem-solving skills in data management and reporting.
