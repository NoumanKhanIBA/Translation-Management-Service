package com.example.translation_management_service.security;



import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import com.example.translation_management_service.entity.AppUser;
import com.example.translation_management_service.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.*;

import java.util.Optional;
import java.util.Set;

@ExtendWith(MockitoExtension.class)
class CustomUserDetailsServiceTest {

    @Mock
    UserRepository userRepo;
    @InjectMocks CustomUserDetailsService uds;

    @Test
    void loadUserByUsername_whenFound_returnsUserDetails() {
        AppUser u = new AppUser();
        u.setUsername("bob");
        u.setPasswordHash("pw");
        u.setRoles(Set.of("ROLE_USER"));
        given(userRepo.findByUsername("bob")).willReturn(Optional.of(u));

        UserDetails ud = uds.loadUserByUsername("bob");

        assertThat(ud.getUsername()).isEqualTo("bob");
        assertThat(ud.getAuthorities()).extracting("authority")
                .containsExactly("ROLE_USER");
    }

    @Test
    void loadUserByUsername_whenNotFound_throws() {
        given(userRepo.findByUsername("alice")).willReturn(Optional.empty());
        assertThatThrownBy(() -> uds.loadUserByUsername("alice"))
                .isInstanceOf(UsernameNotFoundException.class)
                .hasMessageContaining("alice");
    }
}
