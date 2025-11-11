package com.vb.place_me.Usuario.security;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.vb.place_me.Usuario.dto.LoginCreateDTO;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class AuthenticationController {
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @PostMapping("/authenticate")
    public String authenticate(@RequestBody LoginCreateDTO dto) {

        var authenticationToken = new UsernamePasswordAuthenticationToken(dto.email(), dto.senha());

        Authentication authenticated = authenticationManager.authenticate(authenticationToken);

        return jwtService.generateToken(authenticated);
    }
}
