package server.Controller;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import server.ObjectGson.ForgotPasswordRequest;
import server.ObjectGson.SignupRequest;
import server.model.Account;
import server.model.LoginRequest;
import server.services.AuthenticationService;
import util.EmailService;
import org.mindrot.jbcrypt.BCrypt;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Random;

public class AuthenticationController {

    private final AuthenticationService authService;

    public AuthenticationController(AuthenticationService authService) {
        this.authService = authService;
    }

    public void handleRegister(HttpExchange exchange) throws IOException {
        InputStream requestBody = exchange.getRequestBody();
        String body = new String(requestBody.readAllBytes(), StandardCharsets.UTF_8);
        Gson gson = new Gson();
        SignupRequest signupRequest = gson.fromJson(body, SignupRequest.class);

        String responseText = processSignup(signupRequest);
        sendResponse(exchange, 200, responseText);
    }

    private String processSignup(SignupRequest signupRequest) {
        if (authService.userExists(signupRequest.getUsername())) {
            return "User already exists";
        }

        Account newAccount = new Account();
        newAccount.setUsername(signupRequest.getUsername());
        newAccount.setPassword(encryptPassword(signupRequest.getPassword()));
        newAccount.setEmail(signupRequest.getEmail());
        String otp = generateOTP();
        newAccount.setOtpCode(otp);

        boolean saved = authService.saveAccount(newAccount);
        if (saved) {
            boolean emailSent = EmailService.sendEmail(signupRequest.getEmail(), "Verify your account", "Your OTP is: " + otp);
            if (emailSent) {
                return "OTP sent to your email.";
            } else {
                return "Failed to send OTP email.";
            }
        } else {
            return "Signup failed due to server error.";
        }
    }

    public void handleLogin(HttpExchange exchange) throws IOException {
        InputStream requestBody = exchange.getRequestBody();
        String body = new String(requestBody.readAllBytes(), StandardCharsets.UTF_8);
        Gson gson = new Gson();
        LoginRequest loginRequest = gson.fromJson(body, LoginRequest.class);

        Account account = authService.getAccountByUsername(loginRequest.getUsername());
        if (account != null && BCrypt.checkpw(loginRequest.getPassword(), account.getPassword())) {
            sendResponse(exchange, 200, "Login successful!");
        } else {
            sendResponse(exchange, 401, "Invalid username or password.");
        }
    }

    public void handleLogout(HttpExchange exchange) throws IOException {
        // Gửi phản hồi đăng xuất đơn giản
        sendResponse(exchange, 200, "Logout successful!");
    }

    public void handleForgotPassword(HttpExchange exchange) throws IOException {
        InputStream requestBody = exchange.getRequestBody();
        String body = new String(requestBody.readAllBytes(), StandardCharsets.UTF_8);
        Gson gson = new Gson();
        ForgotPasswordRequest forgotPasswordRequest = gson.fromJson(body, ForgotPasswordRequest.class);

        String responseText = processForgotPassword(forgotPasswordRequest);
        sendResponse(exchange, 200, responseText);
    }

    private String processForgotPassword(ForgotPasswordRequest forgotPasswordRequest) {
        Account account = authService.getAccountByUsername(forgotPasswordRequest.getUsername());
        if (account == null) {
            return "User not found.";
        }

        String otp = generateOTP();
        account.setOtpCode(otp);

        try {
            boolean updated = authService.updateAccount(account);
            if (updated) {
                boolean emailSent = EmailService.sendEmail(account.getEmail(), "Reset Password", "Your OTP for resetting password is: " + otp);
                if (emailSent) {
                    return "OTP sent to your email.";
                } else {
                    return "Failed to send OTP email.";
                }
            } else {
                return "Failed to update account.";
            }
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }

    private void sendResponse(HttpExchange exchange, int statusCode, String response) throws IOException {
        exchange.getResponseHeaders().set("Content-Type", "text/plain");
        exchange.sendResponseHeaders(statusCode, response.getBytes(StandardCharsets.UTF_8).length);
        exchange.getResponseBody().write(response.getBytes(StandardCharsets.UTF_8));
        exchange.getResponseBody().close();
    }

    private String generateOTP() {
        Random random = new Random();
        return String.valueOf(random.nextInt(900000) + 100000);
    }

    private String encryptPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }
}
