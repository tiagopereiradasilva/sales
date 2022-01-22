package br.com.company.sales.repository;
import br.com.company.sales.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClientRepository extends JpaRepository<Client, Integer> {

    List<Client> findByNameLike(String name);

    /*Examples using Query Methods.*/
    List<Client> findByNameOrId(String name, Integer id);
    boolean existsByNameLike(String name);
}
