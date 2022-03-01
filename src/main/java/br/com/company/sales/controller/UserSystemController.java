package br.com.company.sales.controller;

import br.com.company.sales.entity.UserSystem;
import br.com.company.sales.rest.dto.CredentialsRequestDTO;
import br.com.company.sales.rest.dto.TokenResponseDTO;
import br.com.company.sales.rest.dto.user.UserSystemResponseDTO;
import br.com.company.sales.service.UserSystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/usuario")
public class UserSystemController {
    UserSystemService userSystemService;
    PasswordEncoder passwordEncoder;
    public UserSystemController(@Autowired UserSystemService userSystemService,
                                @Autowired PasswordEncoder passwordEncoder) {
        this.userSystemService = userSystemService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping
    public ResponseEntity<UserSystem> save(@RequestBody UserSystem userSystem){
        String password = passwordEncoder.encode(userSystem.getPassword());
        userSystem.setPassword(password);
        return ResponseEntity.status(HttpStatus.CREATED).body(userSystemService.save(userSystem));
    }

    @PostMapping("/auth")
    public ResponseEntity<TokenResponseDTO> auth(@RequestBody CredentialsRequestDTO credentials){
        return ResponseEntity.status(HttpStatus.OK).body(userSystemService.auth(credentials));
    }

}
