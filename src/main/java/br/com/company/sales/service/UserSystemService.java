package br.com.company.sales.service;

import br.com.company.sales.entity.UserSystem;
import br.com.company.sales.repository.UserSystemRepository;
import br.com.company.sales.rest.dto.user.UserSystemResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserSystemService implements UserDetailsService {
    private UserSystemRepository userSystemRepository;

    public UserSystemService(@Autowired UserSystemRepository userSystemRepository) {
        this.userSystemRepository = userSystemRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserSystem userSystem = userSystemRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));
        String[] roles = (userSystem.isAdmin()) ? new String[]{"ADMIN", "USER"} : new String[]{"USER"};
        return User
                .builder()
                .username(userSystem.getUsername())
                .password(userSystem.getPassword())
                .roles(roles)
                .build();
    }

    public UserSystemResponseDTO save(UserSystem userSystem){
        userSystemRepository.save(userSystem);
        return userSystemToResponseDTO(userSystem);
    }

    private UserSystemResponseDTO userSystemToResponseDTO(UserSystem userSystem){
        UserSystemResponseDTO userSystemResponseDTO = new UserSystemResponseDTO();
        userSystemResponseDTO.setUsername(userSystem.getUsername());
        userSystemResponseDTO.setAdmin(userSystem.isAdmin());
        return userSystemResponseDTO;
    }

}
