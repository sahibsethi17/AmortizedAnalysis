package com.financeApp.AmortizedAnalysis.service;

import com.financeApp.AmortizedAnalysis.dto.user.UpdateUserRequest;
import com.financeApp.AmortizedAnalysis.model.UserPrincipal;
import com.financeApp.AmortizedAnalysis.model.Users;
import com.financeApp.AmortizedAnalysis.repo.UserRepo;
import com.financeApp.AmortizedAnalysis.utils.AuthResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Optional;

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

    public String deleteUser(Long id) throws Exception {
        Users user = repo.findById(id)
                .orElseThrow(() -> new Exception("User not found"));

        String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        if (!user.getUsername().equals(currentUsername)) {
            throw new AccessDeniedException("You can only delete your own account.");
        }

        repo.delete(user);
        return "Deleted Successfully.";
    }

//    public Users updateUser(Users user) throws Exception {
//        return getUsers(user);
//    }

    @Transactional
    public Users updateUser(Long id, UpdateUserRequest req) throws Exception {
        String caller = SecurityContextHolder.getContext().getAuthentication().getName();

        System.out.println("Caller: " + caller);

        Users existing = repo.findById(id)
                .orElseThrow(() -> new Exception("User not found"));

        System.out.println(existing);

        if (!caller.equals(existing.getUsername()) && !hasRole("ADMIN")) {
            throw new AccessDeniedException("You can only update your own account.");
        }

        System.out.println("Here");

        // username (optional; see JWT note below)
        if (req.username() != null) {
            String newUsername = req.username().trim();
            if (!newUsername.equals(existing.getUsername())
                    && repo.existsByUsernameAndIdNot(newUsername, id)) {
                throw new Exception("Username already in use.");
            }
            existing.setUsername(newUsername);
        }

        System.out.println("Here2");

        // email
        if (req.email() != null) {
            String newEmail = req.email().trim();
            if (!newEmail.equalsIgnoreCase(existing.getEmail())
                    && repo.existsByEmailAndIdNot(newEmail, id)) {
                throw new Exception("Email already in use.");
            }
            existing.setEmail(newEmail);
        }

        System.out.println("Here3");

        // password
        if (req.password() != null && !req.password().isBlank()) {
            existing.setPassword(encoder.encode(req.password()));
        }

        System.out.println(existing);

        if (req.firstName() != null)      existing.setFirstName(req.firstName());
        if (req.lastName() != null)       existing.setLastName(req.lastName());
        if (req.phoneNumber() != null)    existing.setPhoneNumber(req.phoneNumber());
        if (req.currency() != null)       existing.setCurrency(req.currency());
        if (req.gender() != null)         existing.setGender(req.gender());
        if (req.emailPreference() != null)existing.setEmailPreference(req.emailPreference());
        if (req.monthlyBudget() != null)  existing.setMonthlyBudget(req.monthlyBudget());
        if (req.yearlyBudget() != null)   existing.setYearlyBudget(req.yearlyBudget());

        System.out.println(existing);

        // If your DTO uses LocalDate for DOB:
        if (req.dateOfBirth() != null) {
            // convert LocalDate -> java.util.Date (if your entity still uses Date)
            var dob = java.util.Date.from(
                    req.dateOfBirth().atStartOfDay(java.time.ZoneId.systemDefault()).toInstant()
            );
            existing.setDateOfBirth(dob);
        }

        // IMPORTANT: do NOT set role or creationDate from request.
        return repo.save(existing);
    }

    private boolean hasRole(String role) {
        return SecurityContextHolder.getContext().getAuthentication()
                .getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_" + role));
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
