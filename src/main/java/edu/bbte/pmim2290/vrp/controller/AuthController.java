package edu.bbte.pmim2290.vrp.controller;

import edu.bbte.pmim2290.vrp.dto.InUserDTO;
import edu.bbte.pmim2290.vrp.exception.DatabaseException;
import edu.bbte.pmim2290.vrp.exception.EntityNotFoundException;
import edu.bbte.pmim2290.vrp.model.User;
import edu.bbte.pmim2290.vrp.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(final AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/signup")
    public ResponseEntity<User> signup(@RequestBody final InUserDTO userDTO)
            throws EntityNotFoundException, DatabaseException {
        final User user = authService.registerUser(userDTO);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestParam final String username,
                                        @RequestParam final String password) {
        final String token = authService.login(username, password);
        return ResponseEntity.ok(token);
    }

    // JWT-based logout can just be handled client-side by removing token
}