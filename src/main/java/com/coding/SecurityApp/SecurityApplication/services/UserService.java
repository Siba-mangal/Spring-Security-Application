package com.coding.SecurityApp.SecurityApplication.services;

import com.coding.SecurityApp.SecurityApplication.dto.LoginDto;
import com.coding.SecurityApp.SecurityApplication.dto.SignUpDto;
import com.coding.SecurityApp.SecurityApplication.dto.UserDto;
import com.coding.SecurityApp.SecurityApplication.entities.User;
import com.coding.SecurityApp.SecurityApplication.exceptions.ResourceNotFoundException;
import com.coding.SecurityApp.SecurityApplication.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username)
                .orElseThrow(() -> new BadCredentialsException("User not found"));
    }

    public User getUserById(Long userId){
        return userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    public UserDto signUp(SignUpDto signUpDto){
        Optional<User> user = userRepository.findByEmail(signUpDto.getEmail());
        if(user.isPresent()){
            throw new BadCredentialsException("User already exists");
        }

        User toBeCreateUser = modelMapper.map(signUpDto, User.class);
        toBeCreateUser.setPassword(passwordEncoder.encode(signUpDto.getPassword()));
        User saveUser = userRepository.save(toBeCreateUser);
        return modelMapper.map(saveUser, UserDto.class);

    }


}
