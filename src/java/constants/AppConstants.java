package constants;

/**
 * Lưu trữ các hằng số của ứng dụng
 * @author admin
 */
public class AppConstants {
    
    // URL Patterns
    public static final String URL_NEW = "/new";
    public static final String URL_INSERT = "/insert";
    public static final String URL_EDIT = "/edit";
    public static final String URL_UPDATE = "/update";
    public static final String URL_DELETE = "/delete";
    public static final String URL_LIST = "/list";
    
    // JSP Paths
    public static final String JSP_PATIENT_LIST = "WEB-INF/patient_list.jsp";
    public static final String JSP_PATIENT_FORM = "WEB-INF/patient_form.jsp";
    
    // Request Parameters
    public static final String PARAM_ID = "id";
    public static final String PARAM_USERNAME = "username";
    public static final String PARAM_PASSWORD = "password";
    public static final String PARAM_FULLNAME = "fullname";
    public static final String PARAM_GENDER = "gender";
    public static final String PARAM_PHONE = "phone";
    public static final String PARAM_EMAIL = "email";
    public static final String PARAM_DOB = "dob";
    
    // Request Attributes
    public static final String ATTR_PATIENT = "patient";
    public static final String ATTR_PATIENT_LIST = "listPatient";
    public static final String ATTR_ERROR_MESSAGE = "errorMessage";
    public static final String ATTR_SUCCESS_MESSAGE = "successMessage";
    
    // Error Messages
    public static final String ERROR_PATIENT_NOT_FOUND = "Không tìm thấy bệnh nhân với ID: ";
    public static final String ERROR_USERNAME_EMPTY = "Username không được để trống";
    public static final String ERROR_PASSWORD_EMPTY = "Password không được để trống";
    public static final String ERROR_FULLNAME_EMPTY = "Họ tên không được để trống";
    public static final String ERROR_DOB_EMPTY = "Ngày sinh không được để trống";
    public static final String ERROR_DOB_INVALID = "Định dạng ngày sinh không hợp lệ. Vui lòng nhập theo format: yyyy-MM-dd";
    public static final String ERROR_INSERT_FAILED = "Thêm bệnh nhân thất bại";
    public static final String ERROR_UPDATE_FAILED = "Cập nhật bệnh nhân thất bại";
    public static final String ERROR_DELETE_FAILED = "Xóa bệnh nhân thất bại";
    
    // Success Messages
    public static final String SUCCESS_INSERT = "Thêm bệnh nhân thành công";
    public static final String SUCCESS_UPDATE = "Cập nhật bệnh nhân thành công";
    public static final String SUCCESS_DELETE = "Xóa bệnh nhân thành công";
    
    // Database
    public static final String TABLE_PATIENTS = "Patients";
    public static final String COL_PATIENT_ID = "patient_id";
    public static final String COL_USERNAME = "username";
    public static final String COL_PASSWORD = "password";
    public static final String COL_FULLNAME = "fullname";
    public static final String COL_GENDER = "gender";
    public static final String COL_PHONE = "phone";
    public static final String COL_EMAIL = "email";
    public static final String COL_DOB = "dob";
    
    // Private constructor để ngăn khởi tạo
    private AppConstants() {
        throw new AssertionError("Cannot instantiate constants class");
    }
}
