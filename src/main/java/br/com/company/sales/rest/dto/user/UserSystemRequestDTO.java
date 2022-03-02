package br.com.company.sales.rest.dto.user;

import br.com.company.sales.entity.UserSystem;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
public class UserSystemRequestDTO {

    @NotBlank(message = "Campo username é obrigatório")
    private String username;
    @NotBlank(message = "Campo password é obrigatório")
    private String password;
    private boolean admin;

    public UserSystem toUserSystem(){
        UserSystem userSystem = new UserSystem();
        userSystem.setUsername(getUsername().toLowerCase());
        userSystem.setPassword(getPassword());
        userSystem.setAdmin(isAdmin());
        return userSystem;
    }
}
