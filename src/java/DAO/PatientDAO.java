/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import entity.Patient;
import config.DBContext;
import constants.AppConstants;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object cho Patient
 * Chịu trách nhiệm tương tác với database
 * @author admin
 */
public class PatientDAO {

    /**
     * CREATE: Thêm bệnh nhân mới vào database
     * @param patient Đối tượng Patient cần thêm
     * @throws SQLException nếu có lỗi database
     */
    public void insertPatient(Patient patient) throws SQLException {
        String sql = "INSERT INTO " + AppConstants.TABLE_PATIENTS + 
                    " (username, password, fullname, gender, phone, email, dob) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = DBContext.getConnection(); 
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            setPatientParameters(ps, patient, false);
            int rowsAffected = ps.executeUpdate();
            
            if (rowsAffected == 0) {
                throw new SQLException(AppConstants.ERROR_INSERT_FAILED);
            }
            System.out.println("✓ Inserted patient: " + patient.getUsername());
        }
    }

    /**
     * READ: Lấy thông tin 1 bệnh nhân theo ID
     * @param id ID của bệnh nhân
     * @return Patient hoặc null nếu không tìm thấy
     * @throws SQLException nếu có lỗi database
     */
    public Patient selectPatient(int id) throws SQLException {
        String sql = "SELECT * FROM " + AppConstants.TABLE_PATIENTS + 
                    " WHERE " + AppConstants.COL_PATIENT_ID + " = ?";
        
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                Patient patient = mapResultSetToPatient(rs);
                System.out.println("✓ Found patient: " + patient.getUsername());
                return patient;
            }
            
            System.out.println("✗ Patient not found with ID: " + id);
            return null;
        }
    }

    /**
     * READ: Lấy danh sách tất cả bệnh nhân
     * @return List<Patient> danh sách bệnh nhân
     * @throws SQLException nếu có lỗi database
     */
    public List<Patient> selectAllPatients() throws SQLException {
        List<Patient> patients = new ArrayList<>();
        String sql = "SELECT * FROM " + AppConstants.TABLE_PATIENTS + " ORDER BY " + AppConstants.COL_PATIENT_ID + " DESC";
        
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                patients.add(mapResultSetToPatient(rs));
            }
            
            System.out.println("✓ Retrieved " + patients.size() + " patients");
            return patients;
        }
    }

    /**
     * UPDATE: Cập nhật thông tin bệnh nhân
     * @param patient Đối tượng Patient với thông tin mới
     * @return true nếu cập nhật thành công
     * @throws SQLException nếu có lỗi database
     */
    public boolean updatePatient(Patient patient) throws SQLException {
        String sql = "UPDATE " + AppConstants.TABLE_PATIENTS + 
                    " SET username = ?, password = ?, fullname = ?, gender = ?, phone = ?, email = ?, dob = ? " +
                    "WHERE " + AppConstants.COL_PATIENT_ID + " = ?";
        
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            setPatientParameters(ps, patient, true);
            int rowsAffected = ps.executeUpdate();
            
            if (rowsAffected > 0) {
                System.out.println("✓ Updated patient ID: " + patient.getPatientId());
                return true;
            }
            
            System.out.println("✗ No patient updated with ID: " + patient.getPatientId());
            return false;
        }
    }

    /**
     * DELETE: Xóa bệnh nhân khỏi database
     * @param id ID của bệnh nhân cần xóa
     * @return true nếu xóa thành công
     * @throws SQLException nếu có lỗi database
     */
    public boolean deletePatient(int id) throws SQLException {
        String sql = "DELETE FROM " + AppConstants.TABLE_PATIENTS + 
                    " WHERE " + AppConstants.COL_PATIENT_ID + " = ?";
        
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, id);
            int rowsAffected = ps.executeUpdate();
            
            if (rowsAffected > 0) {
                System.out.println("✓ Deleted patient ID: " + id);
                return true;
            }
            
            System.out.println("✗ No patient deleted with ID: " + id);
            return false;
        }
    }
    
    /**
     * Helper method: Map ResultSet to Patient object
     * @param rs ResultSet từ query
     * @return Patient object
     * @throws SQLException nếu có lỗi đọc dữ liệu
     */
    private Patient mapResultSetToPatient(ResultSet rs) throws SQLException {
        return new Patient(
            rs.getInt(AppConstants.COL_PATIENT_ID),
            rs.getString(AppConstants.COL_USERNAME),
            rs.getString(AppConstants.COL_PASSWORD),
            rs.getString(AppConstants.COL_FULLNAME),
            rs.getString(AppConstants.COL_GENDER),
            rs.getString(AppConstants.COL_PHONE),
            rs.getString(AppConstants.COL_EMAIL),
            rs.getDate(AppConstants.COL_DOB)
        );
    }
    
    /**
     * Helper method: Set parameters cho PreparedStatement
     * @param ps PreparedStatement
     * @param patient Patient object
     * @param isUpdate true nếu là UPDATE (cần set ID cuối cùng)
     * @throws SQLException nếu có lỗi set parameter
     */
    private void setPatientParameters(PreparedStatement ps, Patient patient, boolean isUpdate) throws SQLException {
        ps.setString(1, patient.getUsername());
        ps.setString(2, patient.getPassword());
        ps.setString(3, patient.getFullname());
        ps.setString(4, patient.getGender());
        ps.setString(5, patient.getPhone());
        ps.setString(6, patient.getEmail());
        ps.setDate(7, patient.getDob());
        
        if (isUpdate) {
            ps.setInt(8, patient.getPatientId());
        }
    }
}
