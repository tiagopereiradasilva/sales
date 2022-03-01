package br.com.company.sales.service;

import br.com.company.sales.entity.UserSystem;
import br.com.company.sales.exception.SalesException;
import br.com.company.sales.repository.UserSystemRepository;
import br.com.company.sales.rest.dto.CredentialsRequestDTO;
import br.com.company.sales.rest.dto.TokenResponseDTO;
import br.com.company.sales.rest.dto.user.UserSystemResponseDTO;
import br.com.company.sales.security.jwt.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserSystemService implements UserDetailsService {
    private UserSystemRepository userSystemRepository;
    private PasswordEncoder passwordEncoder;
    private JwtService jwtService;

    public UserSystemService(@Autowired UserSystemRepository userSystemRepository,
                             @Autowired PasswordEncoder passwordEncoder,
                             @Autowired JwtService jwtService) {
        this.userSystemRepository = userSystemRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserSystem userSystem = getUserSystemByUserName(username);
        System.out.println("AQUI ==> "+userSystem.getUsername());
        String[] roles = (userSystem.isAdmin()) ? new String[]{"ADMIN", "USER"} : new String[]{"USER"};
        return User
                .builder()
                .username(userSystem.getUsername())
                .password(userSystem.getPassword())
                .roles(roles)
                .build();
    }

    public UserSystem save(UserSystem userSystem){

        return userSystemRepository.save(userSystem);
    }

    public TokenResponseDTO auth(CredentialsRequestDTO credentials){
        //buscando detalhes do usuário.
        UserDetails userDetails = loadUserByUsername(credentials.getUsername());
        //verificar se a senha informada está correta.
        boolean passwordIsValid = passwordEncoder.matches(credentials.getPassword(), userDetails.getPassword());
        if(passwordIsValid){
            return getToken(credentials);
        }
        throw new SalesException("Credenciais Inválidas");
    }

    private TokenResponseDTO getToken(CredentialsRequestDTO credentials){
        UserSystem userSystem = getUserSystemByUserName(credentials.getUsername());
        var token = jwtService.createToken(userSystem);
        return new TokenResponseDTO(
                userSystem.getId(),
                token,
                jwtService.getExpireIn(token),
                "Bearer"

        );

    }

    private UserSystem getUserSystemByUserName(String username){
        return userSystemRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));
    }
    private UserSystemResponseDTO userSystemToResponseDTO(UserSystem userSystem){
        UserSystemResponseDTO userSystemResponseDTO = new UserSystemResponseDTO();
        userSystemResponseDTO.setUsername(userSystem.getUsername());
        userSystemResponseDTO.setAdmin(userSystem.isAdmin());
        return userSystemResponseDTO;
    }

}
