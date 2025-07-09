package com.financeApp.AmortizedAnalysis.service;

import com.financeApp.AmortizedAnalysis.model.UserPrincipal;
import com.financeApp.AmortizedAnalysis.model.Users;
import com.financeApp.AmortizedAnalysis.model.UpdateUserRequest;
import com.financeApp.AmortizedAnalysis.repo.UserRepo;
import com.financeApp.AmortizedAnalysis.utils.AuthResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.nio.file.AccessDeniedException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

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
        return getUsers(user);
    }

    public AuthResponse login(Users user) {
        Authentication auth = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        user.getUsername(),
                        user.getPassword()
                )
        );

        if (auth.isAuthenticated()) {
            UserPrincipal principal = (UserPrincipal) auth.getPrincipal();
            Users actualUser = principal.getUser();

            String token = jwtService.generateToken(actualUser.getUsername(), actualUser.getEmail());
            System.out.println("TOKEN: " + token);
            Long userId = actualUser.getId();

            return new AuthResponse(token, userId);
        } else {
            throw new RuntimeException("Authentication failed");
        }
    }

    public String deleteUser(Users user) throws Exception {
        repo.delete(user);
        return "Deleted Successfully.";
    }

//    public Users updateUser(Users user) throws Exception {
//        return getUsers(user);
//    }

    public Users updateUser(Long id, UpdateUserRequest update) throws Exception {
        String jwtUsername = SecurityContextHolder.getContext().getAuthentication().getName();

        Users existing = repo.findById(id)
                .orElseThrow(() -> new Exception("User not found"));

        if (!existing.getUsername().equals(jwtUsername)) {
            throw new AccessDeniedException("You can only update your own account.");
        }

        if (update.getEmail() != null && !update.getEmail().equals(existing.getEmail())) {
            if (repo.findByEmail(update.getEmail()) != null) {
                throw new Exception("Email already in use.");
            }
            existing.setEmail(update.getEmail());
        }

        if (update.getUsername() != null && !update.getUsername().equals(existing.getUsername())) {
            if (repo.findByUsername(update.getUsername()) != null) {
                throw new Exception("Username already in use.");
            }
            existing.setUsername(update.getUsername());
        }

        if (update.getPassword() != null) {
            existing.setPassword(encoder.encode(update.getPassword()));
        }

        if (update.getFirstName() != null) existing.setFirstName(update.getFirstName());
        if (update.getLastName() != null) existing.setLastName(update.getLastName());
        if (update.getPhoneNumber() != null) existing.setPhoneNumber(update.getPhoneNumber());
        if (update.getCurrency() != null) existing.setCurrency(update.getCurrency());
        if (update.getDateOfBirth() != null) existing.setDateOfBirth(update.getDateOfBirth());
        if (update.getGender() != null) existing.setGender(update.getGender());
        if (update.getRole() != null) existing.setRole(update.getRole());
        if (update.getEmailPreference() != null) existing.setEmailPreference(update.getEmailPreference());
        if (update.getCreationDate() != null) existing.setCreationDate(update.getCreationDate());
        if (update.getMonthlyBudget() != null) existing.setMonthlyBudget(update.getMonthlyBudget());
        if (update.getYearlyBudget() != null) existing.setYearlyBudget(update.getYearlyBudget());

        return repo.save(existing);
    }

    private Users getUsers(Users user) throws Exception {
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

    public Optional<Users> getUserById(Long userId) {
        return repo.findById(userId);
    }

    public ArrayList<Users> getAllUsers() {
        return new ArrayList<>(repo.findAll());
    }
}
