/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

/**
 *
 * @author admin
 */
import entity.Patient;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PatientDAO {
    private String url = "jdbc:sqlserver://localhost:1433;databaseName=mm;encrypt=true;trustServerCertificate=true;";
    private String username = "sa"; // Tài khoản SQL Server của bạn
    private String password = "123"; // Mật khẩu SQL Server của bạn

    protected Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            connection = DriverManager.getConnection(url, username, password);
        } catch (ClassNotFoundException | SQLException ex) {
            System.out.println(ex);
        }
        return connection;
    }

    // C - CREATE: Thêm bệnh nhân
    public void insertPatient(Patient patient) throws SQLException {
        String sql = "INSERT INTO Patients (username, password, fullname, gender, phone, email, dob) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, patient.getUsername());
            ps.setString(2, patient.getPassword());
            ps.setString(3, patient.getFullname()); // SQL Driver tự xử lý NVARCHAR nếu truyền chuỗi đúng cách
            ps.setString(4, patient.getGender());
            ps.setString(5, patient.getPhone());
            ps.setString(6, patient.getEmail());
            ps.setDate(7, patient.getDob());
            ps.executeUpdate();
        }
    }

    // R - READ: Lấy 1 bệnh nhân theo ID (để sửa)
    public Patient selectPatient(int id) {
        Patient patient = null;
        String sql = "SELECT * FROM Patients WHERE patient_id = ?";
        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                patient = new Patient(
                    rs.getInt("patient_id"),
                    rs.getString("username"),
                    rs.getString("password"),
                    rs.getString("fullname"),
                    rs.getString("gender"),
                    rs.getString("phone"),
                    rs.getString("email"),
                    rs.getDate("dob")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return patient;
    }

    // R - READ: Lấy tất cả danh sách bệnh nhân
    public List<Patient> selectAllPatients() {
        List<Patient> patients = new ArrayList<>();
        String sql = "SELECT * FROM Patients";
        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                patients.add(new Patient(
                    rs.getInt("patient_id"),
                    rs.getString("username"),
                    rs.getString("password"),
                    rs.getString("fullname"),
                    rs.getString("gender"),
                    rs.getString("phone"),
                    rs.getString("email"),
                    rs.getDate("dob")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return patients;
    }

    // U - UPDATE: Cập nhật thông tin bệnh nhân
    public boolean updatePatient(Patient patient) throws SQLException {
        boolean rowUpdated;
        String sql = "UPDATE Patients SET username = ?, password = ?, fullname = ?, gender = ?, phone = ?, email = ?, dob = ? WHERE patient_id = ?";
        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, patient.getUsername());
            ps.setString(2, patient.getPassword());
            ps.setString(3, patient.getFullname());
            ps.setString(4, patient.getGender());
            ps.setString(5, patient.getPhone());
            ps.setString(6, patient.getEmail());
            ps.setDate(7, patient.getDob());
            ps.setInt(8, patient.getPatientId());
            rowUpdated = ps.executeUpdate() > 0;
        }
        return rowUpdated;
    }

    // D - DELETE: Xóa bệnh nhân
    public boolean deletePatient(int id) throws SQLException {
        boolean rowDeleted;
        String sql = "DELETE FROM Patients WHERE patient_id = ?";
        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            rowDeleted = ps.executeUpdate() > 0;
        }
        return rowDeleted;
    }
}
