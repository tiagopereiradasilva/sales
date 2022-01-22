package br.com.company.sales.repository;
import br.com.company.sales.entity.Client;
import br.com.company.sales.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClientRepository extends JpaRepository<Client, Integer> {

    List<Client> findByNameLike(String name);

    /*Examples using Query Methods.*/
    List<Client> findByNameOrId(String name, Integer id);
    boolean existsByNameLike(String name);

    /*Example using @Query*/
    @Query("select c from Client c left join fetch c.orders where c.id = :id")
    Client findOrdersFetch(@Param("id") Integer id);
}
