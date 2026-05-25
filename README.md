<<<<<<< HEAD
Dự án demo tokuda
thành viên:
đức chim bé 
...
=======
# 🏥 Patient Management System

## 📖 Giới Thiệu

Hệ thống quản lý bệnh nhân (Patient Management System) được xây dựng với Java Servlet, JSP, và SQL Server. Dự án áp dụng kiến trúc phân lớp (Layered Architecture) với các best practices về Clean Code và Design Patterns.

---

## 🏗️ Kiến Trúc

```
┌─────────────────────────────────────────────────┐
│              Presentation Layer                  │
│         (JSP Views + Controller)                 │
└────────────────┬────────────────────────────────┘
                 │
┌────────────────▼────────────────────────────────┐
│              Business Logic Layer                │
│              (Service Layer)                     │
└────────────────┬────────────────────────────────┘
                 │
┌────────────────▼────────────────────────────────┐
│              Data Access Layer                   │
│              (DAO Layer)                         │
└────────────────┬────────────────────────────────┘
                 │
┌────────────────▼────────────────────────────────┐
│              Database Layer                      │
│              (SQL Server)                        │
└─────────────────────────────────────────────────┘
```

---

## 📁 Cấu Trúc Thư Mục

```
SWP/
├── src/java/
│   ├── Controllers/          # HTTP Request Handlers
│   │   └── PatientController.java
│   ├── services/            # Business Logic
│   │   └── PatientService.java
│   ├── DAO/                 # Database Access
│   │   └── PatientDAO.java
│   ├── entity/              # Domain Models
│   │   └── Patient.java
│   ├── dto/                 # Data Transfer Objects
│   │   └── PatientRequest.java
│   ├── config/              # Configuration
│   │   └── DBContext.java
│   ├── constants/           # Application Constants
│   │   └── AppConstants.java
│   └── exceptions/          # Custom Exceptions
│       └── PatientException.java
│
├── web/
│   ├── WEB-INF/
│   │   ├── patient_list.jsp    # Danh sách bệnh nhân
│   │   ├── patient_form.jsp    # Form thêm/sửa
│   │   └── web.xml             # Servlet configuration
│   └── index.html
│
├── lib/                     # Dependencies
│   ├── sqljdbc42.jar
│   ├── jakarta.servlet.jsp.jstl-*.jar
│   └── ...
│
└── Documentation/
    ├── README.md                    # This file
    ├── PROJECT_STRUCTURE.md         # Detailed structure
    ├── IMPROVEMENTS_SUMMARY.md      # What changed
    └── CHECKLIST.md                 # Build checklist
```

---

## ✨ Tính Năng

### CRUD Operations:
- ✅ **Create** - Thêm bệnh nhân mới
- ✅ **Read** - Xem danh sách và chi tiết bệnh nhân
- ✅ **Update** - Cập nhật thông tin bệnh nhân
- ✅ **Delete** - Xóa bệnh nhân

### Technical Features:
- ✅ Layered Architecture (Controller → Service → DAO)
- ✅ DTO Pattern để giảm coupling
- ✅ Custom Exception Handling
- ✅ Centralized Configuration
- ✅ Constants Management
- ✅ Comprehensive Logging
- ✅ SQL Injection Prevention (PreparedStatement)
- ✅ UTF-8 Encoding Support (Tiếng Việt)

---

## 🛠️ Công Nghệ Sử Dụng

- **Backend:** Java Servlet 6.0, JSP
- **Database:** Microsoft SQL Server
- **Server:** Apache Tomcat / GlassFish
- **Build Tool:** NetBeans (Ant)
- **Libraries:**
  - Jakarta Servlet API
  - JSTL 2.0
  - SQL Server JDBC Driver

---

## 📋 Yêu Cầu Hệ Thống

- **JDK:** 11 or higher
- **Server:** Tomcat 10+ / GlassFish 7+
- **Database:** SQL Server 2019+
- **IDE:** NetBeans 12+ (recommended)

---

## 🚀 Cài Đặt & Chạy

### 1. Database Setup

```sql
-- Tạo database
CREATE DATABASE mm;
GO

USE mm;
GO

-- Tạo bảng Patients
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
GO

-- Insert sample data
INSERT INTO Patients (username, password, fullname, gender, phone, email, dob)
VALUES 
    ('john_doe', 'pass123', N'Nguyễn Văn A', N'Nam', '0123456789', 'john@email.com', '1990-01-01'),
    ('jane_smith', 'pass456', N'Trần Thị B', N'Nữ', '0987654321', 'jane@email.com', '1995-05-15');
GO
```

### 2. Configure Database Connection

Mở file `src/java/config/DBContext.java` và cập nhật:

```java
private static final String URL = "jdbc:sqlserver://localhost:1433;databaseName=mm;...";
private static final String USERNAME = "sa";
private static final String PASSWORD = "your_password";
```

### 3. Build Project

```bash
# Trong NetBeans:
1. Right-click project → Clean and Build
2. Hoặc nhấn Shift + F11
```

### 4. Deploy & Run

```bash
# Trong NetBeans:
1. Right-click project → Run
2. Hoặc nhấn F6
```

### 5. Access Application

```
http://localhost:8080/SWP/list
```

---

## 🔗 URL Endpoints

| URL | Method | Description |
|-----|--------|-------------|
| `/list` | GET | Hiển thị danh sách bệnh nhân |
| `/new` | GET | Form thêm bệnh nhân mới |
| `/insert` | POST | Xử lý thêm bệnh nhân |
| `/edit?id=X` | GET | Form sửa bệnh nhân |
| `/update` | POST | Xử lý cập nhật bệnh nhân |
| `/delete?id=X` | GET | Xóa bệnh nhân |

---

## 📊 Database Schema

```sql
Patients
├── patient_id (INT, PK, IDENTITY)
├── username (NVARCHAR(50), NOT NULL)
├── password (NVARCHAR(100), NOT NULL)
├── fullname (NVARCHAR(100), NOT NULL)
├── gender (NVARCHAR(10))
├── phone (NVARCHAR(20))
├── email (NVARCHAR(100))
└── dob (DATE, NOT NULL)
```

---

## 🎯 Design Patterns

1. **Layered Architecture** - Separation of concerns
2. **DTO Pattern** - Data transfer between layers
3. **Singleton Pattern** - DBContext (static methods)
4. **Factory Pattern** - Connection factory
5. **Template Method** - Helper methods in DAO
6. **MVC Pattern** - Model-View-Controller

---

## 📝 Code Examples

### Thêm Bệnh Nhân:

```java
// Controller
PatientRequest request = new PatientRequest(
    "username", "password", "fullname",
    "gender", "phone", "email", "1990-01-01"
);
patientService.createPatient(request);
```

### Lấy Danh Sách:

```java
// Service
List<Patient> patients = patientService.getAllPatients();
```

### Cập Nhật:

```java
// Controller
PatientRequest request = buildPatientRequestFromParams(request);
request.setId(patientId);
patientService.updatePatient(request);
```

---

## 🐛 Troubleshooting

### Lỗi Connection:
```
✗ Failed to connect to database
```
**Giải pháp:** Kiểm tra SQL Server đang chạy, database `mm` tồn tại, credentials đúng.

### Lỗi 404:
```
HTTP Status 404 – Not Found
```
**Giải pháp:** Kiểm tra URL mapping trong `web.xml`, restart server.

### Lỗi Encoding:
```
Tiếng Việt hiển thị ???
```
**Giải pháp:** Đã xử lý UTF-8 trong code, kiểm tra database collation.

---

## 📚 Documentation

- **[PROJECT_STRUCTURE.md](PROJECT_STRUCTURE.md)** - Chi tiết cấu trúc dự án
- **[IMPROVEMENTS_SUMMARY.md](IMPROVEMENTS_SUMMARY.md)** - Tóm tắt các cải tiến
- **[CHECKLIST.md](CHECKLIST.md)** - Checklist build & deploy

---

## 🎓 Học Được Gì?

1. ✅ Layered Architecture
2. ✅ Separation of Concerns
3. ✅ Clean Code Principles
4. ✅ Design Patterns
5. ✅ Exception Handling
6. ✅ Database Best Practices
7. ✅ DTO Pattern
8. ✅ Constants Management

---

## 🚀 Next Steps

### Phase 1 (Current): ✅ COMPLETED
- [x] Basic CRUD operations
- [x] Clean architecture
- [x] Service layer
- [x] DTO pattern
- [x] Exception handling

### Phase 2 (Recommended):
- [ ] Add pagination
- [ ] Add search/filter
- [ ] Improve UI (Bootstrap)
- [ ] Client-side validation
- [ ] Password hashing

### Phase 3 (Advanced):
- [ ] Authentication/Authorization
- [ ] Unit tests (JUnit)
- [ ] REST API
- [ ] Spring Boot migration
- [ ] Frontend framework (React/Vue)

---

## 👨‍💻 Author

**Admin**  
- NetBeans Project
- Java Servlet + JSP
- SQL Server

---

## 📄 License

Educational Project - Free to use and modify

---

## 🙏 Acknowledgments

- Clean Code by Robert C. Martin
- Design Patterns: Elements of Reusable Object-Oriented Software
- Java EE Best Practices

---

**Version:** 2.0 (Clean Architecture)  
**Last Updated:** 2026-05-25  
**Status:** ✅ Production Ready
>>>>>>> eca0f0c (choajdwi)
