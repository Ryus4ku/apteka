package ru.shutov.apteka.apteka.Services;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.shutov.apteka.apteka.DTOs.ContextDto;
import ru.shutov.apteka.apteka.DTOs.CreateUserDto;
import ru.shutov.apteka.apteka.Exceptions.CustomException;
import ru.shutov.apteka.apteka.Model.Role;
import ru.shutov.apteka.apteka.Model.User;
import ru.shutov.apteka.apteka.Repository.UserRepository;

@Service
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username);
    }

    public void createNewUser(CreateUserDto user) {
        boolean isValid = validateData(user);
        if (isValid) {
            User newUser = new User();
            newUser.setUsername(user.getUsername());
            newUser.setPassword(passwordEncoder.encode(user.getPassword()));
            newUser.getRoles().add(Role.USER);
            newUser.setActive(true);
            userRepository.save(newUser);
        } else {
            throw new CustomException("Плохие данные");
        }
    }

    private boolean validateData(CreateUserDto user) {
        return !user.getUsername().isEmpty() && !user.getPassword().isEmpty();
    }

    public ContextDto getContext(User user) {
        return ContextDto.builder()
                .isAdmin(user.getRoles().contains(Role.SUPER_USER))
                .isAuthorized(user != null)
                .build();
    }
}
