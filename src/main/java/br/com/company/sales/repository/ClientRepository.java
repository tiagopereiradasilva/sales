package br.com.company.sales.repository;

import br.com.company.sales.entity.Client;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class ClientRepository {
    //private static final String INSERT = "INSERT INTO TB_CLIENT (name) VALUES (?)";
    //private static final String SELECT_ALL = "SELECT * FROM CLIENT";
    //private static final String SELECT_BY_NAME = "SELECT * FROM CLIENT WHERE name LIKE ?";
    //private static final String UPDATE = "UPDATE CLIENT SET NAME = ? WHERE ID = ?";
    //private static final String DELETE = "DELETE FROM CLIENT WHERE ID = ?";

    @Autowired
    private EntityManager entityManager;

    @Transactional
    public Client save(@NotNull Client client){
        entityManager.persist(client);
        return client;
    }

    @Transactional
    public Client update(Client client){
        entityManager.merge(client);
        return client;
    }

    @Transactional
    public void delete(Client client){
        if(!entityManager.contains(client)){
            client = entityManager.merge(client);
        }
        entityManager.remove(client);
    }

    @Transactional
    public void delete(int id){
        Client client = entityManager.find(Client.class, id);
        delete(client);
    }

    @Transactional(readOnly = true)
    public List<Client> findByName(String name){
        String jpql = "select c from Client c where c.name like :name";
        TypedQuery<Client> query = entityManager.createQuery(jpql, Client.class);
        query.setParameter("name", "%"+name+"%");
        return query.getResultList();
    }

    @Transactional(readOnly = true)
    public List<Client> all(){
        return entityManager.createQuery("from Client", Client.class).getResultList();
    }

}
