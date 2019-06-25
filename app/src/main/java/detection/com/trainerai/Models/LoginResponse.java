package detection.com.trainerai.Models;

public class LoginResponse {
    String jwt;
    String email, password;

    public LoginResponse(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getJwt() {
        return jwt;
    }
}
