package services;

import entity.Patient;
import DAO.PatientDAO;
import constants.AppConstants;
import exceptions.PatientException;
import dto.PatientRequest;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

/**
 * Service layer xử lý business logic cho Patient
 * @author admin
 */
public class PatientService {
    
    private final PatientDAO patientDAO;
    
    public PatientService() {
        this.patientDAO = new PatientDAO();
    }
    
    /**
     * Lấy danh sách tất cả bệnh nhân
     * @return List<Patient>
     * @throws PatientException nếu có lỗi
     */
    public List<Patient> getAllPatients() throws PatientException {
        try {
            return patientDAO.selectAllPatients();
        } catch (SQLException e) {
            throw new PatientException("DATABASE_ERROR", "Lỗi khi lấy danh sách bệnh nhân", e);
        }
    }
    
    /**
     * Lấy thông tin 1 bệnh nhân theo ID
     * @param patientId ID của bệnh nhân
     * @return Patient
     * @throws PatientException nếu không tìm thấy hoặc có lỗi
     */
    public Patient getPatientById(int patientId) throws PatientException {
        try {
            Patient patient = patientDAO.selectPatient(patientId);
            if (patient == null) {
                throw new PatientException("NOT_FOUND", AppConstants.ERROR_PATIENT_NOT_FOUND + patientId);
            }
            return patient;
        } catch (SQLException e) {
            throw new PatientException("DATABASE_ERROR", "Lỗi khi lấy thông tin bệnh nhân", e);
        }
    }
    
    /**
     * Thêm mới bệnh nhân (sử dụng DTO)
     * @param request PatientRequest DTO
     * @throws PatientException nếu validation fail hoặc có lỗi database
     */
    public void createPatient(PatientRequest request) throws PatientException {
        // Validate dữ liệu
        validatePatientData(request);
        
        try {
            // Tạo đối tượng Patient
            Patient newPatient = new Patient(
                request.getUsername(),
                request.getPassword(),
                request.getFullname(),
                request.getGender(),
                request.getPhone(),
                request.getEmail(),
                Date.valueOf(request.getDob())
            );
            
            // Lưu vào database
            patientDAO.insertPatient(newPatient);
            System.out.println("✓ Service: Created patient - " + request.getUsername());
            
        } catch (SQLException e) {
            throw new PatientException("DATABASE_ERROR", AppConstants.ERROR_INSERT_FAILED, e);
        }
    }
    
    /**
     * Cập nhật thông tin bệnh nhân (sử dụng DTO)
     * @param request PatientRequest DTO (phải có ID)
     * @throws PatientException nếu validation fail hoặc có lỗi database
     */
    public void updatePatient(PatientRequest request) throws PatientException {
        if (request.getId() == null) {
            throw new PatientException("INVALID_INPUT", "ID bệnh nhân không được null");
        }
        
        // Validate dữ liệu
        validatePatientData(request);
        
        try {
            // Kiểm tra bệnh nhân có tồn tại không
            Patient existingPatient = patientDAO.selectPatient(request.getId());
            if (existingPatient == null) {
                throw new PatientException("NOT_FOUND", AppConstants.ERROR_PATIENT_NOT_FOUND + request.getId());
            }
            
            // Tạo đối tượng Patient với thông tin mới
            Patient updatedPatient = new Patient(
                request.getId(),
                request.getUsername(),
                request.getPassword(),
                request.getFullname(),
                request.getGender(),
                request.getPhone(),
                request.getEmail(),
                Date.valueOf(request.getDob())
            );
            
            // Cập nhật vào database
            boolean success = patientDAO.updatePatient(updatedPatient);
            if (!success) {
                throw new PatientException("UPDATE_FAILED", AppConstants.ERROR_UPDATE_FAILED);
            }
            
            System.out.println("✓ Service: Updated patient ID - " + request.getId());
            
        } catch (SQLException e) {
            throw new PatientException("DATABASE_ERROR", AppConstants.ERROR_UPDATE_FAILED, e);
        }
    }
    
    /**
     * Xóa bệnh nhân
     * @param patientId ID của bệnh nhân cần xóa
     * @throws PatientException nếu không tìm thấy hoặc có lỗi
     */
    public void deletePatient(int patientId) throws PatientException {
        try {
            // Kiểm tra bệnh nhân có tồn tại không
            Patient existingPatient = patientDAO.selectPatient(patientId);
            if (existingPatient == null) {
                throw new PatientException("NOT_FOUND", AppConstants.ERROR_PATIENT_NOT_FOUND + patientId);
            }
            
            // Xóa khỏi database
            boolean success = patientDAO.deletePatient(patientId);
            if (!success) {
                throw new PatientException("DELETE_FAILED", AppConstants.ERROR_DELETE_FAILED);
            }
            
            System.out.println("✓ Service: Deleted patient ID - " + patientId);
            
        } catch (SQLException e) {
            throw new PatientException("DATABASE_ERROR", AppConstants.ERROR_DELETE_FAILED, e);
        }
    }
    
    /**
     * Validate dữ liệu đầu vào
     * @param request PatientRequest DTO
     * @throws PatientException nếu dữ liệu không hợp lệ
     */
    private void validatePatientData(PatientRequest request) throws PatientException {
        if (request.getUsername() == null || request.getUsername().trim().isEmpty()) {
            throw new PatientException("VALIDATION_ERROR", AppConstants.ERROR_USERNAME_EMPTY);
        }
        
        if (request.getPassword() == null || request.getPassword().trim().isEmpty()) {
            throw new PatientException("VALIDATION_ERROR", AppConstants.ERROR_PASSWORD_EMPTY);
        }
        
        if (request.getFullname() == null || request.getFullname().trim().isEmpty()) {
            throw new PatientException("VALIDATION_ERROR", AppConstants.ERROR_FULLNAME_EMPTY);
        }
        
        if (request.getDob() == null || request.getDob().trim().isEmpty()) {
            throw new PatientException("VALIDATION_ERROR", AppConstants.ERROR_DOB_EMPTY);
        }
        
        // Validate format ngày sinh
        try {
            Date.valueOf(request.getDob());
        } catch (IllegalArgumentException e) {
            throw new PatientException("VALIDATION_ERROR", AppConstants.ERROR_DOB_INVALID, e);
        }
    }
}
