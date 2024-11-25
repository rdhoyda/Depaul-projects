# Project Summary

## Description

This project involved developing and executing a series of PL/SQL queries and procedures within an Oracle Database environment. The objective was to interact with the database to manage and query constraint information, department details, employee counts, and more.

## Technologies Used

- **Oracle PL/SQL**: For writing and executing procedural code within the Oracle database.
- **SQL**: For querying and manipulating database tables.

## Tasks Completed

1. **Constraint Information Query**:
   - Developed a PL/SQL block to retrieve and display constraints associated with a specific table (`LOCATIONS`).
   - Used cursors and record types to process and format constraint details, including Primary Key, Unique Key, Referential Integrity, and Check Constraints.

2. **Department Information Retrieval**:
   - Created a PL/SQL block to fetch and display department details based on a specific department ID.
   - Joined `departments` and `locations` tables to combine department names with their respective street address and city.

3. **Dynamic Department Listing**:
   - Wrote a PL/SQL block that used a collection type (nested table) to store and display department information for departments with managers.
   - Implemented dynamic record fetching and display formatting to list departments and their locations.

4. **Employee Count Procedure**:
   - Implemented a PL/SQL procedure to count employees in a specified department.
   - Developed an anonymous block to call this procedure and handle output formatting based on employee count.

5. **Department Employee Counts**:
   - Created a PL/SQL block to iterate over departments with managers, using a cursor to fetch department IDs.
   - Used the previously defined procedure to count and display the number of employees in each department.

## Learning Outcomes

- **Understanding of PL/SQL**: Enhanced knowledge in creating and managing PL/SQL blocks, procedures, and cursors.
- **Data Handling**: Gained experience in handling and processing data within Oracle databases, including complex joins and data formatting.
- **Collection Types**: Familiarized with PL/SQL collection types such as nested tables for storing and processing multiple records.
- **Dynamic SQL Execution**: Improved skills in writing dynamic SQL and handling output formatting based on conditional logic.

## Problem-Solving

- **Complex Data Retrieval**: Solved the challenge of retrieving and formatting complex constraint and department data using cursors and dynamic SQL.
- **Output Formatting**: Addressed issues related to output formatting and conditional messaging based on data retrieved from the database.
