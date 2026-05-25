# 📋 Cấu Trúc Dự Án - Patient Management System

## 🏗️ Kiến Trúc Tổng Quan

```
src/java/
├── Controllers/          → HTTP Request/Response handling
├── services/            → Business Logic Layer
├── DAO/                 → Data Access Layer
├── entity/              → Domain Models
├── dto/                 → Data Transfer Objects
├── config/              → Configuration (Database, etc.)
├── constants/           → Application Constants
└── exceptions/          → Custom Exceptions
```

---

## 📦 Chi Tiết Các Package

### 1. **Controllers** (Presentation Layer)
**Trách nhiệm:**
- Nhận HTTP requests từ client
- Gọi Service layer để xử lý logic
- Trả về HTTP responses (JSP views hoặc redirects)
- **KHÔNG** chứa business logic

**Files:**
- `PatientController.java` - Xử lý CRUD operations cho Patient

**Nguyên tắc:**
- Thin controller - chỉ routing và delegation
- Sử dụng constants thay vì hardcode strings
- Exception handling tập trung

---

### 2. **services** (Business Logic Layer)
**Trách nhiệm:**
- Xử lý toàn bộ business logic
- Validation dữ liệu đầu vào
- Orchestrate các DAO calls
- Transform giữa DTO và Entity

**Files:**
- `PatientService.java` - Business logic cho Patient operations

**Nguyên tắc:**
- Validate trước khi gọi DAO
- Throw custom exceptions (PatientException)
- Logging các operations quan trọng
- Sử dụng DTO để giảm coupling

---

### 3. **DAO** (Data Access Layer)
**Trách nhiệm:**
- Tương tác trực tiếp với database
- CRUD operations
- Query execution
- **KHÔNG** chứa business logic

**Files:**
- `PatientDAO.java` - Database operations cho Patient table

**Nguyên tắc:**
- Try-with-resources cho auto-close connections
- PreparedStatement để tránh SQL injection
- Sử dụng DBContext để lấy connection
- Helper methods để giảm code duplication

---

### 4. **entity** (Domain Models)
**Trách nhiệm:**
- Đại diện cho database tables
- Plain Old Java Objects (POJOs)
- Getters/Setters và toString()

**Files:**
- `Patient.java` - Entity cho Patients table

**Nguyên tắc:**
- Map 1-1 với database schema
- Immutable khi có thể
- Override toString() để debug

---

### 5. **dto** (Data Transfer Objects)
**Trách nhiệm:**
- Transfer data giữa layers
- Giảm số lượng parameters trong methods
- Decouple presentation từ domain model

**Files:**
- `PatientRequest.java` - DTO cho Patient create/update requests

**Lợi ích:**
- Giảm method parameters từ 7+ xuống 1
- Dễ dàng thêm/bớt fields
- Type-safe hơn Map<String, Object>

---

### 6. **config** (Configuration)
**Trách nhiệm:**
- Database connection management
- Application configuration
- Singleton patterns

**Files:**
- `DBContext.java` - Centralized database connection

**Nguyên tắc:**
- Load JDBC driver một lần duy nhất (static block)
- Provide connection factory method
- Logging connection status

---

### 7. **constants** (Application Constants)
**Trách nhiệm:**
- Lưu trữ tất cả constants
- Tránh magic strings/numbers
- Single source of truth

**Files:**
- `AppConstants.java` - All application constants

**Categories:**
- URL patterns
- JSP paths
- Request parameters/attributes
- Error/Success messages
- Database column names

---

### 8. **exceptions** (Custom Exceptions)
**Trách nhiệm:**
- Custom exception types
- Error codes
- Meaningful error messages

**Files:**
- `PatientException.java` - Custom exception cho Patient operations

**Lợi ích:**
- Phân biệt business errors vs system errors
- Error codes để i18n
- Better error handling

---

## 🔄 Data Flow

```
Client Request
    ↓
Controller (PatientController)
    ↓
Service (PatientService) ← Validation & Business Logic
    ↓
DAO (PatientDAO) ← Database Operations
    ↓
Database (SQL Server)
```

---

## ✅ Best Practices Được Áp Dụng

### 1. **Separation of Concerns**
- Mỗi layer có trách nhiệm riêng biệt
- Không mix business logic với database code

### 2. **DRY (Don't Repeat Yourself)**
- Constants thay vì hardcode
- Helper methods cho code reuse
- DTO để giảm duplication

### 3. **SOLID Principles**
- Single Responsibility: Mỗi class một nhiệm vụ
- Dependency Inversion: Depend on abstractions

### 4. **Clean Code**
- Meaningful names
- Small methods
- Comments khi cần thiết
- Consistent formatting

### 5. **Error Handling**
- Try-with-resources
- Custom exceptions
- Centralized error handling
- Logging

### 6. **Security**
- PreparedStatement (SQL injection prevention)
- Input validation
- Encoding UTF-8

---

## 🚀 Cách Sử Dụng

### Thêm mới Patient:
```java
PatientRequest request = new PatientRequest(
    "john_doe", "password123", "John Doe",
    "Male", "0123456789", "john@email.com", "1990-01-01"
);
patientService.createPatient(request);
```

### Cập nhật Patient:
```java
PatientRequest request = new PatientRequest(
    1, "john_doe", "newpass", "John Doe Updated",
    "Male", "0987654321", "john@email.com", "1990-01-01"
);
patientService.updatePatient(request);
```

### Lấy danh sách:
```java
List<Patient> patients = patientService.getAllPatients();
```

---

## 📊 Database Schema

```sql
CREATE TABLE Patients (
    patient_id INT PRIMARY KEY IDENTITY(1,1),
    username NVARCHAR(50) NOT NULL,
    password NVARCHAR(100) NOT NULL,
    fullname NVARCHAR(100) NOT NULL,
    gender NVARCHAR(10),
    phone NVARCHAR(20),
    email NVARCHAR(100),
    dob DATE NOT NULL
);
```

---

## 🔧 Configuration

### Database Connection (DBContext.java):
- **URL:** `jdbc:sqlserver://localhost:1433;databaseName=mm`
- **Username:** `sa`
- **Password:** `123`
- **Driver:** `com.microsoft.sqlserver.jdbc.SQLServerDriver`

### URL Mappings (web.xml):
- `/list` - Danh sách bệnh nhân
- `/new` - Form thêm mới
- `/insert` - Xử lý thêm mới
- `/edit?id=X` - Form sửa
- `/update` - Xử lý cập nhật
- `/delete?id=X` - Xóa bệnh nhân

---

## 📝 Logging

Tất cả operations đều có logging:
- ✓ Success operations (green checkmark)
- ✗ Failed operations (red X)
- → Action indicators (arrow)

Example:
```
✓ SQL Server JDBC Driver loaded successfully
✓ Database connection established
→ Action: /list
✓ Retrieved 5 patients
✓ Database connection closed
```

---

## 🎯 Next Steps / Improvements

1. **Security:**
   - Hash passwords (BCrypt)
   - Add authentication/authorization
   - CSRF protection

2. **Features:**
   - Pagination cho danh sách
   - Search/Filter functionality
   - Export to Excel/PDF

3. **Code Quality:**
   - Unit tests (JUnit)
   - Integration tests
   - Code coverage

4. **Performance:**
   - Connection pooling (HikariCP)
   - Caching (Redis)
   - Query optimization

5. **UI/UX:**
   - Bootstrap/Tailwind CSS
   - AJAX for better UX
   - Client-side validation

---

**Author:** admin  
**Last Updated:** 2026-05-25  
**Version:** 2.0 (Refactored with Service Layer + DTO)
