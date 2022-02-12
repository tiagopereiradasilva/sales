package br.com.company.sales.repository;

import br.com.company.sales.entity.UserSystem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserSystemRepository extends JpaRepository<UserSystem, Integer> {
    Optional<UserSystem> findByUsername(String username);
}
