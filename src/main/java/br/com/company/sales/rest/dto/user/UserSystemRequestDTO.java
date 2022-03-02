package br.com.company.sales.rest.dto.user;

import br.com.company.sales.entity.UserSystem;
import br.com.company.sales.util.Helper;
import lombok.AllArgsConstructor;
import lombok.Data;
import javax.validation.constraints.NotBlank;

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
        userSystem.setUsername(Helper.toLowerCase(getUsername()));
        userSystem.setPassword(getPassword());
        userSystem.setAdmin(isAdmin());
        return userSystem;
    }
}
