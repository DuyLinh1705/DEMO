package config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Quản lý kết nối database tập trung
 * Singleton pattern để tái sử dụng cấu hình
 * @author admin
 */
public class DBContext {
    
    private static final String URL = "jdbc:sqlserver://localhost:1433;databaseName=mm;encrypt=true;trustServerCertificate=true;";
    private static final String USERNAME = "sa";
    private static final String PASSWORD = "123";
    private static final String DRIVER = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
    
    // Load driver một lần duy nhất khi class được load
    static {
        try {
            Class.forName(DRIVER);
            System.out.println("✓ SQL Server JDBC Driver loaded successfully");
        } catch (ClassNotFoundException e) {
            System.err.println("✗ SQL Server JDBC Driver not found!");
            e.printStackTrace();
            throw new RuntimeException("Failed to load JDBC driver", e);
        }
    }
    
    /**
     * Tạo connection mới đến database
     * @return Connection
     * @throws SQLException nếu không thể kết nối
     */
    public static Connection getConnection() throws SQLException {
        try {
            Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            System.out.println("✓ Database connection established");
            return conn;
        } catch (SQLException e) {
            System.err.println("✗ Failed to connect to database: " + e.getMessage());
            throw e;
        }
    }
    
    /**
     * Đóng connection an toàn
     * @param conn Connection cần đóng
     */
    public static void closeConnection(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
                System.out.println("✓ Database connection closed");
            } catch (SQLException e) {
                System.err.println("✗ Error closing connection: " + e.getMessage());
            }
        }
    }
}
