package com.financeApp.AmortizedAnalysis.service;

import com.financeApp.AmortizedAnalysis.model.UserPrincipal;
import com.financeApp.AmortizedAnalysis.model.Users;
import com.financeApp.AmortizedAnalysis.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepo repo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users user = repo.findByUsername(username);
        if (user == null) {
            System.out.println("User not found");
            throw new UsernameNotFoundException("User not found");
        }
        return new UserPrincipal(user);
    }

    public UserDetails loadUserByEmail(String email) throws Exception {
        Users user = repo.findByEmail(email);
        if (user == null) {
            System.out.println("User not found");
            throw new Exception("User not found");
        }
        return new UserPrincipal(user);
    }
}
