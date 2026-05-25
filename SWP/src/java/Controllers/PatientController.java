package Controllers;

import entity.Patient;
import services.PatientService;
import constants.AppConstants;
import exceptions.PatientException;
import dto.PatientRequest;
        
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Controller xử lý HTTP requests cho Patient
 * Chỉ chịu trách nhiệm nhận request, gọi service, và trả về response
 * @author admin
 */
public class PatientController extends HttpServlet {
    
    private PatientService patientService;

    @Override
    public void init() {
        patientService = new PatientService();
        System.out.println("✓ PatientController initialized");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        doGet(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        
        String action = request.getServletPath();
        System.out.println("→ Action: " + action);

        try {
            switch (action) {
                case AppConstants.URL_NEW:
                    showNewForm(request, response);
                    break;
                case AppConstants.URL_INSERT:
                    insertPatient(request, response);
                    break;
                case AppConstants.URL_DELETE:
                    deletePatient(request, response);
                    break;
                case AppConstants.URL_EDIT:
                    showEditForm(request, response);
                    break;
                case AppConstants.URL_UPDATE:
                    updatePatient(request, response);
                    break;
                default:
                    listPatient(request, response);
                    break;
            }
        } catch (PatientException ex) {
            handleError(request, response, ex);
        }
    }

    /**
     * Hiển thị danh sách tất cả bệnh nhân
     */
    private void listPatient(HttpServletRequest request, HttpServletResponse response) 
            throws PatientException, ServletException, IOException {
        List<Patient> listPatient = patientService.getAllPatients();
        request.setAttribute(AppConstants.ATTR_PATIENT_LIST, listPatient);
        forward(request, response, AppConstants.JSP_PATIENT_LIST);
    }

    /**
     * Hiển thị form thêm mới bệnh nhân
     */
    private void showNewForm(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        forward(request, response, AppConstants.JSP_PATIENT_FORM);
    }

    /**
     * Hiển thị form sửa thông tin bệnh nhân
     */
    private void showEditForm(HttpServletRequest request, HttpServletResponse response) 
            throws PatientException, ServletException, IOException {
        int id = Integer.parseInt(request.getParameter(AppConstants.PARAM_ID));
        Patient existingPatient = patientService.getPatientById(id);
        request.setAttribute(AppConstants.ATTR_PATIENT, existingPatient);
        forward(request, response, AppConstants.JSP_PATIENT_FORM);
    }

    /**
     * Xử lý thêm mới bệnh nhân
     */
    private void insertPatient(HttpServletRequest request, HttpServletResponse response) 
            throws PatientException, IOException {
        PatientRequest patientRequest = buildPatientRequestFromParams(request);
        patientService.createPatient(patientRequest);
        response.sendRedirect(AppConstants.URL_LIST);
    }

    /**
     * Xử lý cập nhật thông tin bệnh nhân
     */
    private void updatePatient(HttpServletRequest request, HttpServletResponse response) 
            throws PatientException, IOException {
        PatientRequest patientRequest = buildPatientRequestFromParams(request);
        patientRequest.setId(Integer.parseInt(request.getParameter(AppConstants.PARAM_ID)));
        patientService.updatePatient(patientRequest);
        response.sendRedirect(AppConstants.URL_LIST);
    }

    /**
     * Xử lý xóa bệnh nhân
     */
    private void deletePatient(HttpServletRequest request, HttpServletResponse response) 
            throws PatientException, IOException {
        int id = Integer.parseInt(request.getParameter(AppConstants.PARAM_ID));
        patientService.deletePatient(id);
        response.sendRedirect(AppConstants.URL_LIST);
    }
    
    /**
     * Helper: Build PatientRequest DTO từ request parameters
     */
    private PatientRequest buildPatientRequestFromParams(HttpServletRequest request) {
        return new PatientRequest(
            request.getParameter(AppConstants.PARAM_USERNAME),
            request.getParameter(AppConstants.PARAM_PASSWORD),
            request.getParameter(AppConstants.PARAM_FULLNAME),
            request.getParameter(AppConstants.PARAM_GENDER),
            request.getParameter(AppConstants.PARAM_PHONE),
            request.getParameter(AppConstants.PARAM_EMAIL),
            request.getParameter(AppConstants.PARAM_DOB)
        );
    }
    
    /**
     * Helper: Forward request to JSP
     */
    private void forward(HttpServletRequest request, HttpServletResponse response, String path) 
            throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher(path);
        dispatcher.forward(request, response);
    }
    
    /**
     * Helper: Xử lý lỗi và hiển thị thông báo
     */
    private void handleError(HttpServletRequest request, HttpServletResponse response, PatientException ex) 
            throws ServletException, IOException {
        System.err.println("✗ Error: " + ex.getMessage());
        ex.printStackTrace();
        
        request.setAttribute(AppConstants.ATTR_ERROR_MESSAGE, ex.getMessage());
        
        // Nếu là lỗi NOT_FOUND, redirect về list
        if ("NOT_FOUND".equals(ex.getErrorCode())) {
            response.sendRedirect(AppConstants.URL_LIST);
        } else {
            // Các lỗi khác, hiển thị lại form hoặc list
            try {
                listPatient(request, response);
            } catch (PatientException e) {
                // Nếu không lấy được list, hiển thị error page
                request.setAttribute(AppConstants.ATTR_ERROR_MESSAGE, "Lỗi nghiêm trọng: " + e.getMessage());
                forward(request, response, AppConstants.JSP_PATIENT_LIST);
            }
        }
    }
}