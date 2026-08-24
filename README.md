# Java Swing User Authentication System

A Java Swing desktop application providing secure user registration and authentication integrated with a PostgreSQL database. Passwords are encrypted using BCrypt before storage.

---

## Features

* **User Registration**: Input validation for name, email format, date format, and password matching.
* **Secure Password Hashing**: Passwords are hashed using BCrypt (`jBCrypt`) with dynamic salting.
* **Database Integration**: PostgreSQL backend using JDBC and `PreparedStatement` to prevent SQL injection.
* **Environment Security**: Externalized credentials via `config.properties` to ensure sensitive database passwords are never committed to version control.
* **NetBeans Swing GUI**: Form layout designed with NetBeans GUI Builder using AbsoluteLayout.

---

## Project Structure

├── src/
│   ├── demo/
│   │   ├── DatabaseConnection.java  # JDBC Connection manager loading properties
│   │   ├── UserDAO.java             # Database operations (Register & Authenticate)
│   │   ├── Login_page.java          # Main GUI Login Frame
│   │   └── Registration_page.java   # Main GUI Registration Frame
│   └── pictures/                    # Assets and UI background images
├── .gitignore                       # Excludes config.properties and build files
├── config.properties.example        # Configuration template for developers
── build.xml                        # Ant build script

---

## Database Setup

* **Open pgAdmin or your PostgreSQL command terminal.
* **Create a database (e.g., user_db).
* **Run the following SQL script to create the users table:

SQL
CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    email VARCHAR(100) NOT NULL,
    gender VARCHAR(10),
    date_of_birth VARCHAR(10),
    username VARCHAR(50) UNIQUE NOT NULL,
    password_hash VARCHAR(255) NOT NULL
);

---

## Prerequisites & Dependencies

* **Java Development Kit (JDK): 8 or higher
* **PostgreSQL JDBC Driver: postgresql-42.x.x.jar
* **jBCrypt Library: jbcrypt-0.4.jar
* **NetBeans IDE (Recommended for editing .form GUI files)

---

## How to Run & Configure
* **Clone the repository:

Bash
git clone [https://github.com/SebastijanPecenko/Login-App.git](https://github.com/SebastijanPecenko/Login-App.git)
* **Set up the Configuration File:
* **Duplicate the config.properties.example file.
* **Rename the duplicated file to config.properties.
* **Update the file with your local database connection details:

Properties
db.url=jdbc:postgresql://localhost:5432/user_db
db.user=postgres
db.password=your_actual_postgres_password

---

## Build and Run:

* **Open the project in NetBeans IDE.
* **Add the required .jar libraries (PostgreSQL JDBC and jBCrypt) to the project's Libraries folder.
* **Clean and Build the project (Shift + F11).
* **Run Login_page.java.