package dto;

/**
 * Data Transfer Object cho Patient request
 * Giúp giảm số lượng tham số truyền vào method
 * @author admin
 */
public class PatientRequest {
    private Integer id;
    private String username;
    private String password;
    private String fullname;
    private String gender;
    private String phone;
    private String email;
    private String dob;
    
    // Constructor
    public PatientRequest() {}
    
    public PatientRequest(String username, String password, String fullname, 
                         String gender, String phone, String email, String dob) {
        this.username = username;
        this.password = password;
        this.fullname = fullname;
        this.gender = gender;
        this.phone = phone;
        this.email = email;
        this.dob = dob;
    }
    
    public PatientRequest(Integer id, String username, String password, String fullname,
                         String gender, String phone, String email, String dob) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.fullname = fullname;
        this.gender = gender;
        this.phone = phone;
        this.email = email;
        this.dob = dob;
    }
    
    // Getters and Setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    
    public String getFullname() { return fullname; }
    public void setFullname(String fullname) { this.fullname = fullname; }
    
    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }
    
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public String getDob() { return dob; }
    public void setDob(String dob) { this.dob = dob; }
    
    @Override
    public String toString() {
        return "PatientRequest{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", fullname='" + fullname + '\'' +
                ", gender='" + gender + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", dob='" + dob + '\'' +
                '}';
    }
}
