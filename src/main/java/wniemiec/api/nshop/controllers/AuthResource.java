package wniemiec.api.nshop.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import wniemiec.api.nshop.dto.EmailDTO;
import wniemiec.api.nshop.security.JWTUtil;
import wniemiec.api.nshop.security.UserSpringSecurity;
import wniemiec.api.nshop.services.AuthService;
import wniemiec.api.nshop.services.UserService;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequestMapping(value="/auth")
public class AuthResource {

    @Autowired
    private JWTUtil jwtUtil;

    @Autowired
    private AuthService service;

    @RequestMapping(value="/refresh_token", method=RequestMethod.POST)
    public ResponseEntity<Void> refreshToken(HttpServletResponse response) {
        UserSpringSecurity user = UserService.authenticatedUser();
        String token = jwtUtil.generateToken(user.getUsername());

        response.addHeader("Authorization", "Bearer " + token);
        response.addHeader("access-control-expose-headers", "Authorization");

        return ResponseEntity.noContent().build();
    }

    @RequestMapping(value="/forgot", method=RequestMethod.POST)
    public ResponseEntity<Void> forgot(@Valid @RequestBody EmailDTO emailDTO) {
        service.sendNewPassword(emailDTO.getEmail());

        return ResponseEntity.noContent().build();
    }
}
