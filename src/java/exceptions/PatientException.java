package exceptions;

/**
 * Custom exception cho Patient operations
 * Giúp xử lý lỗi rõ ràng hơn
 * @author admin
 */
public class PatientException extends Exception {
    
    private String errorCode;
    
    public PatientException(String message) {
        super(message);
    }
    
    public PatientException(String message, Throwable cause) {
        super(message, cause);
    }
    
    public PatientException(String errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }
    
    public PatientException(String errorCode, String message, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
    }
    
    public String getErrorCode() {
        return errorCode;
    }
}
