# Library Management REST API

A Spring Boot REST backend for managing library books, students, issue records, returns and overdue items. It follows the required layered architecture:

`Controller -> Service -> Repository -> MySQL Database`

## Technology
- Java 17 or later
- Spring Boot 3.3.5, Spring Web, Spring Data JPA, Hibernate, Jakarta Validation
- MySQL 8+, Maven, Postman

## 1. Database setup
Start MySQL and run:

```sql
CREATE DATABASE library_db;
```

The same command is available in `database/create_database.sql`. Hibernate creates/updates the three tables when the app starts.

## 2. Configure database credentials
Open `src/main/resources/application.properties`. Defaults are user `root` and a blank password. Change them to match your MySQL installation, or set environment variables `DB_USERNAME`, `DB_PASSWORD`, and optionally `DB_URL`. Do not upload real passwords to GitHub.

## 3. Run
From this project folder:

```bash
mvn clean package
mvn spring-boot:run
```

The API starts at `http://localhost:8080`.

In Eclipse: File -> Import -> Existing Maven Projects -> select this folder -> Finish. Wait for Maven dependencies to finish downloading, then run `LibraryManagementApiApplication.java` as Java Application / Spring Boot App.

## API endpoints

| Method | URL | Purpose |
|---|---|---|
| POST | `/api/books` | Add book |
| GET | `/api/books` | List books |
| GET | `/api/books/{id}` | Get book |
| PUT | `/api/books/{id}` | Update book |
| DELETE | `/api/books/{id}` | Delete book (only if it has no issue history) |
| GET | `/api/books/category/{category}` | Search category |
| POST | `/api/students` | Register student |
| GET | `/api/students` | List students |
| GET | `/api/students/{id}` | Get student |
| PUT | `/api/students/{id}` | Update student |
| DELETE | `/api/students/{id}` | Delete student (only if it has no issue history) |
| POST | `/api/issues/{studentId}/{bookId}` | Issue book |
| GET | `/api/issues` | List issue records |
| GET | `/api/issues/student/{studentId}` | Student issue history |
| PUT | `/api/issues/return/{issueId}` | Return book |
| GET | `/api/issues/overdue` | List overdue active issues |

## Example JSON

### Add a book
```json
{
  "title": "Clean Code",
  "author": "Robert C. Martin",
  "isbn": "9780132350884",
  "category": "Programming",
  "totalCopies": 5
}
```

### Register a student
```json
{
  "studentName": "Rahul Sen",
  "email": "rahul.sen@example.com",
  "department": "CSE",
  "phoneNumber": "9876543210"
}
```

## Business rules implemented
- Student and book must exist before issuing.
- An issue is blocked if no copy is available.
- `availableCopies` decreases on issue and increases on return.
- A student can have at most three active issues.
- Loan period is 14 days; issue and due dates are set automatically.
- A returned issue cannot be returned again.
- Overdue means an active issue whose due date is before the current date.
- ISBN and student email are unique.
- Book/student records with issue history cannot be deleted, preserving referential integrity.
- Updating total copies cannot set the number below the quantity currently on loan.
- Issue and return operations use transactions.

## Error handling
`@RestControllerAdvice` maps not found to 404, duplicate data to 409, business-rule failures and invalid input to 400. Validation errors include field-level messages.

## Postman
Import `postman/Library_Management_API.postman_collection.json`. Select the collection and run requests in order. The collection stores created IDs in variables for subsequent calls. It also creates and deletes temporary unused records to test the DELETE endpoints. Update the sample ISBN/email before a second run if they already exist in your database. Save your response screenshots in the `screenshots` folder.

## Git workflow required by the assignment
Use your own Git identity and make commits for work you understand. Example workflow:

```bash
git init
git add .
git commit -m "Add initial Library Management API"
git checkout -b development
git checkout -b feature/book-management
```

For a real feature-branch commit, open `BookService.java` and make this small improvement in `byCategory`: change `findByCategoryIgnoreCase(category)` to `findByCategoryIgnoreCase(category.trim())`. Then run:

```bash
git add src/main/java/com/anirban/librarymanagement/service/BookService.java
git commit -m "Normalize category search input"
git checkout development
git merge feature/book-management
git remote add origin https://github.com/YOUR_USERNAME/LibraryManagementAPI.git
git push -u origin development
git push -u origin feature/book-management
```

Create the GitHub repository first and replace `YOUR_USERNAME`. Git author information should be your own. Do not claim commits authored by someone else as your own.
