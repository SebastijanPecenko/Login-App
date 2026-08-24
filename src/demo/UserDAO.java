package demo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import org.mindrot.jbcrypt.BCrypt;

public class UserDAO {
    public static boolean registerUser(String firstName, String lastName, String email, 
                                      String gender, String dob, String username, char[] rawPassword) {
        String sql = "INSERT INTO users (first_name, last_name, email, gender, date_of_birth, username, password_hash) "
                   + "VALUES (?, ?, ?, ?, ?, ?, ?)";
        String hashedPassword = BCrypt.hashpw(new String(rawPassword), BCrypt.gensalt(10));
        Arrays.fill(rawPassword, '0');
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, firstName);
            pstmt.setString(2, lastName);
            pstmt.setString(3, email);
            pstmt.setString(4, gender);
            pstmt.setString(5, dob);
            pstmt.setString(6, username);
            pstmt.setString(7, hashedPassword);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("SQL Registration Error:");
            e.printStackTrace();
            return false;
        }
    }

    public static boolean authenticateUser(String username, char[] rawPassword) {
        String sql = "SELECT password_hash FROM users WHERE username = ?";
        // ALWAYS connects directly to PostgreSQL for live authentication
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    String storedHash = rs.getString("password_hash");
                    boolean match = BCrypt.checkpw(new String(rawPassword), storedHash);
                    Arrays.fill(rawPassword, '0');
                    return match;
                }
            }
        } catch (SQLException e) {
            System.err.println("SQL Authentication Error:");
            e.printStackTrace();
        }
       
        Arrays.fill(rawPassword, '0');
        return false; // Returns false if user was deleted in pgAdmin!
    }
}