package com.mikhail.SpringSecurityJWT.restController;

import com.mikhail.SpringSecurityJWT.config.jwt.JwtProvider;
import com.mikhail.SpringSecurityJWT.entity.UserEntity;
import com.mikhail.SpringSecurityJWT.request.AuthRequest;
import com.mikhail.SpringSecurityJWT.request.RegistrationRequest;
import com.mikhail.SpringSecurityJWT.response.AuthResponse;
import com.mikhail.SpringSecurityJWT.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Optional;

@RestController
public class AuthController {

    @Autowired
    UserService userService;

    @Autowired
    private JwtProvider jwtProvider;

    @PostMapping("/register")
    public String registerUser(@RequestBody @Valid RegistrationRequest registrationRequest) {
        UserEntity userEntity = new UserEntity();
        userEntity.setPassword(registrationRequest.getPassword());
        userEntity.setLogin(registrationRequest.getLogin());
        userService.saveUser(userEntity);
        return "OK";
    }

    @PostMapping("/auth")
    public AuthResponse auth(@RequestBody @Valid AuthRequest request) {
        String token;
        Optional<UserEntity> userEntity = userService.findByLoginAndPassword(request.getLogin(), request.getPassword());
        if (userEntity.isPresent()) {
            token = jwtProvider.generateToken(userEntity.get().getLogin());
        } else {
            token = "Smt went wrong...";
        }
        return new AuthResponse(token);
    }

}
