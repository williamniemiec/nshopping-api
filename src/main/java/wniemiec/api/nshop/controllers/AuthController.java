package wniemiec.api.nshop.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import wniemiec.api.nshop.dto.EmailDTO;
import wniemiec.api.nshop.security.JWTUtil;
import wniemiec.api.nshop.security.UserSpringSecurity;
import wniemiec.api.nshop.services.AuthService;
import wniemiec.api.nshop.services.UserService;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;


/**
 * Responsible for handling authentication requests.
 */
@RestController
@RequestMapping(value="/auth")
public class AuthController {

    //-------------------------------------------------------------------------
    //		Attributes
    //-------------------------------------------------------------------------
    @Autowired
    private JWTUtil jwtUtil;

    @Autowired
    private AuthService service;


    //-------------------------------------------------------------------------
    //		Methods
    //-------------------------------------------------------------------------
    @PostMapping(value="/refresh_token")
    public ResponseEntity<Void> refreshToken(HttpServletResponse response) {
        UserSpringSecurity user = UserService.authenticatedUser();
        String token = jwtUtil.generateToken(user.getUsername());

        response.addHeader("Authorization", "Bearer " + token);
        response.addHeader("access-control-expose-headers", "Authorization");

        return ResponseEntity.noContent().build();
    }

    @PostMapping(value="/forgot")
    public ResponseEntity<Void> forgot(@Valid @RequestBody EmailDTO emailDTO) {
        service.sendNewPassword(emailDTO.getEmail());

        return ResponseEntity.noContent().build();
    }
}
