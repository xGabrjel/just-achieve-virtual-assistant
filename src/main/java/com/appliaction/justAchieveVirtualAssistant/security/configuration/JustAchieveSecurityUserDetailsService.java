package com.appliaction.justAchieveVirtualAssistant.security.configuration;

import com.appliaction.justAchieveVirtualAssistant.security.user.UserJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JustAchieveSecurityUserDetailsService implements UserDetailsService {

    private final UserJpaRepository userJpaRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userJpaRepository.findByUsername(username)
                .map(JustAchieveSecurityUserDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}
