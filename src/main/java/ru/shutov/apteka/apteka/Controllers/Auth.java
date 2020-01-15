package ru.shutov.apteka.apteka.Controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ru.shutov.apteka.apteka.DTOs.ContextDto;
import ru.shutov.apteka.apteka.DTOs.CreateUserDto;
import ru.shutov.apteka.apteka.Model.User;
import ru.shutov.apteka.apteka.Services.UserService;

@RestController
@RequestMapping("/")
public class Auth {
    private final UserService userService;

    public Auth(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("register")
    public ResponseEntity registration(@RequestBody CreateUserDto user) {
        userService.createNewUser(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("getContext")
    public ResponseEntity<ContextDto> context(@AuthenticationPrincipal User user) {
        return new ResponseEntity<>(userService.getContext(user), HttpStatus.OK);
    }
}
