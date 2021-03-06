package br.com.company.sales.controller;

import br.com.company.sales.entity.UserSystem;
import br.com.company.sales.rest.dto.CredentialsRequestDTO;
import br.com.company.sales.rest.dto.ResponseTemplate;
import br.com.company.sales.rest.dto.TokenResponseDTO;
import br.com.company.sales.rest.dto.user.UserSystemRequestDTO;
import br.com.company.sales.rest.dto.user.UserSystemResponseDTO;
import br.com.company.sales.service.UserSystemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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
    public ResponseEntity<ResponseTemplate> save(@RequestBody @Valid UserSystemRequestDTO userSystemDto){
//        String password = passwordEncoder.encode(userSystemDto.getPassword());
//        userSystemDto.setPassword(password);
        return ResponseEntity.status(HttpStatus.CREATED).body(userSystemService.save(userSystemDto));
    }

    @PostMapping("/auth")
    public ResponseEntity<ResponseTemplate> auth(@RequestBody CredentialsRequestDTO credentials){
        return ResponseEntity.status(HttpStatus.OK).body(userSystemService.auth(credentials));
    }

}
