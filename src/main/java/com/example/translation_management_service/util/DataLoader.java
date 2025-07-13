package com.example.translation_management_service.util;

import com.example.translation_management_service.entity.AppUser;
import com.example.translation_management_service.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import java.util.Set;

@Component
public class DataLoader implements CommandLineRunner {
    private final UserRepository userRepo;
    private final PasswordEncoder pwEncoder;

    public DataLoader(UserRepository userRepo,
                      PasswordEncoder pwEncoder) {
        this.userRepo = userRepo;
        this.pwEncoder = pwEncoder;
    }

    @Override
    public void run(String... args) {
        if (userRepo.count() == 0) {
            AppUser admin = new AppUser();
            admin.setUsername("admin");
            admin.setPasswordHash(pwEncoder.encode("admin123"));
            admin.setRoles(Set.of("ROLE_ADMIN","ROLE_USER"));
            userRepo.save(admin);
        }
    }
}
