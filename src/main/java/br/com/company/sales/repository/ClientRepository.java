package br.com.company.sales.repository;

import br.com.company.sales.entity.Client;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class ClientRepository {
    //private static final String INSERT = "INSERT INTO TB_CLIENT (name) VALUES (?)";
    private static final String SELECT_ALL = "SELECT * FROM CLIENT";
    private static final String SELECT_BY_NAME = "SELECT * FROM CLIENT WHERE name LIKE ?";
    private static final String UPDATE = "UPDATE CLIENT SET NAME = ? WHERE ID = ?";
    private static final String DELETE = "DELETE FROM CLIENT WHERE ID = ?";


    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private EntityManager entityManager;

    @Transactional
    public Client save(@NotNull Client client){
        //jdbcTemplate.update(INSERT, new Object[]{client.getName()});
        entityManager.persist(client);
        return client;
    }

    public Client update(Client client){
        jdbcTemplate.update(UPDATE, new Object[]{client.getName(), client.getId()});
        return client;
    }

    public void delete(Client client){
        delete(client.getId());
    }

    public void delete(int id){
        jdbcTemplate.update(DELETE, new Object[]{id});
    }

    public List<Client> findByName(String name){
        return jdbcTemplate.query(SELECT_BY_NAME,
                new Object[]{"%"+name+"%"},
                getClientRowMapper());
    }

    public List<Client> all(){
        return jdbcTemplate.query(SELECT_ALL, getClientRowMapper());
    }

    @NotNull
    private RowMapper<Client> getClientRowMapper() {
        return new RowMapper<Client>() {
            @Override
            public Client mapRow(ResultSet rs, int rowNum) throws SQLException {
                Client client = new Client();
                client.setId(rs.getInt("id"));
                client.setName(rs.getString("name"));
                return client;
            }
        };
    }
}
