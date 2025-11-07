// src/main/java/ru/avangard/website/service/AdminUserDetailsService.java
package ru.avangard.website.service;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.avangard.website.entity.Admin;
import ru.avangard.website.repository.IAdminRepository;

@Service
public class AdminUserDetailsService implements UserDetailsService {

    private final IAdminRepository adminRepository;

    public AdminUserDetailsService(IAdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        Admin admin = adminRepository.findByLogin(login)
                .orElseThrow(() -> new UsernameNotFoundException("Admin not found: " + login));

        return User.builder()
                .username(admin.getLogin())
                .password(admin.getPassword())
                .roles("ADMIN")
                .build();
    }
}