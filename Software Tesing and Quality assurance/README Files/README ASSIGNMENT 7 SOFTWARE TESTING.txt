
Project Summary for Readme
--------------------------

Project Overview
----------------
This project focused on software testing and issue generation using Jira. The primary objective was to identify and report a defect on a test website and document the findings in Jira.

Technologies Used
-----------------
- Jira: For bug tracking and issue management.
- Web Browsers: For navigating and testing the web application.
- Tomcat Server: To observe server-side error handling and logging.

Problem Solved
--------------
Issue Identified: A bug was discovered on the registration page of the test website (https://petstore.octoperf.com/actions/Catalog.action). When attempting to register a new user without providing complete account information, the server returned an HTTP Status 500 – Internal Server Error due to a data integrity violation.

Technical Details:
- Error: HTTP Status 500 – Internal Server Error
- Root Cause: java.sql.SQLIntegrityConstraintViolationException: integrity constraint violation: NOT NULL check constraint ; SYS_CT_10102 table: ACCOUNT column: EMAIL
- Stack Trace: The error was traced back to an unhandled exception during database update operations involving the AccountMapper in MyBatis.

Learning Outcomes
-----------------
1. Bug Identification and Reporting: Learned how to systematically identify, reproduce, and report software defects using a structured approach in Jira.
2. Understanding Server-Side Errors: Gained insights into common server-side issues such as integrity constraint violations and how they are handled in Java-based web applications.
3. Jira Proficiency: Enhanced skills in using Jira for creating and managing bug reports, including setting issue types, statuses, and detailed descriptions for effective tracking and resolution.

Key Steps and Learnings
-----------------------
1. Reproducing the Bug:
   - Navigated to the registration page of the test website.
   - Attempted to register with incomplete information, leading to the discovery of the bug.
2. Documentation in Jira:
   - Logged into Jira, created a new issue, and documented the bug with detailed steps to reproduce, expected vs. actual results, and technical details of the error.
   - Set appropriate labels and assigned the issue for resolution.
3. Analyzing Server Logs:
   - Reviewed server logs to understand the root cause of the error, focusing on database integrity constraints and exception handling mechanisms.

This project provided a practical experience in software testing and issue management, reinforcing the importance of thorough testing and precise documentation in the software development lifecycle.
