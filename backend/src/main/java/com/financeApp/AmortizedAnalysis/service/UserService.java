package com.financeApp.AmortizedAnalysis.service;

import com.financeApp.AmortizedAnalysis.model.Users;
import com.financeApp.AmortizedAnalysis.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepo repo;

    @Autowired
    private JWTService jwtService;

    @Autowired
    private AuthenticationManager authManager;

    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    public Users signup(Users user) throws Exception {
        String userEmail = user.getEmail();
        String userUsername = user.getUsername();
        Users u1 = repo.findByEmail(userEmail);
        Users u2 = repo.findByUsername(userUsername);
        if (u1 != null) {
            System.out.println("Fail");
            throw new Exception("Email is already in use.");
        } else if (u2 != null) {
            System.out.println("Fail again.");
            throw new Exception("Username is already in use.");
        } else {
            System.out.println("Success");
            user.setPassword(encoder.encode(user.getPassword()));
            return repo.save(user);
        }
    }

    public String verify(Users user) {
        Authentication auth = authManager
                .authenticate(new UsernamePasswordAuthenticationToken(
                        user.getUsername(), user.getPassword()));

        if (auth.isAuthenticated()) {
            return jwtService.generateToken(user.getUsername());
        } else {
            return "Fail";
        }
    }
}
