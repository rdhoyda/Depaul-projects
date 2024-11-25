
## Project Summary

### Technology Used
- **Java**: The primary programming language used for developing the application.
- **IntelliJ IDEA**: Integrated Development Environment (IDE) for Java development.
- **Git**: Version control system to manage code versions.

### Problem Solved
The project focuses on creating a robust system for managing and manipulating prices represented as cents. It includes functionality for:
- Creating price objects from integer and string inputs.
- Performing arithmetic operations (addition, subtraction, multiplication) on prices.
- Comparing prices to determine relational properties (greater than, less than, equal to).
- Converting prices to string format and generating hash codes for price objects.

### Key Learnings
- **Object-Oriented Programming (OOP)**: Gained experience in designing classes and objects to encapsulate price data and related operations.
- **Factory Design Pattern**: Implemented the Factory Pattern to create price objects from different input formats, enhancing the flexibility and scalability of the code.
- **Java Specifics**: Enhanced understanding of Java-specific features such as method overriding, encapsulation, and use of utility classes like `DecimalFormat`.
- **Code Reusability and Maintainability**: Learned to write reusable and maintainable code by separating concerns into different classes (`Price` and `PriceFactory`).

### Project Components
- **Main.java**: The main driver class that demonstrates the creation and manipulation of price objects.
- **Price.java**: Defines the `Price` class with attributes and methods for arithmetic operations, comparisons, and formatting.
- **PriceFactory.java**: Implements the factory methods for creating `Price` objects from integers and strings.
