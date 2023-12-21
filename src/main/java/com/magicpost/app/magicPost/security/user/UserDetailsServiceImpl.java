package com.magicpost.app.magicPost.security.user;

import com.magicpost.app.magicPost.exception.ResourceNotFoundException;
import com.magicpost.app.magicPost.user.UserRepository;
import com.magicpost.app.magicPost.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;
    /*
        * Username of security user is email of user in app
     */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User"));
        return new UserDetailsImpl(user);
    }
}
