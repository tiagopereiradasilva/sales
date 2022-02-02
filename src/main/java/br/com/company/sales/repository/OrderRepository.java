package br.com.company.sales.repository;

import br.com.company.sales.entity.Client;
import br.com.company.sales.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {
    List<Order> findByClient(Client client);

    @Query("select o from Order o left join fetch o.itemOrders where o.id = :id")
    Optional<Order> findByIdFetchItemOrders(@Param("id") Integer id);
}
