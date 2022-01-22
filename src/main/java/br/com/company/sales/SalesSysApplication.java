package br.com.company.sales;

import br.com.company.sales.entity.Client;
import br.com.company.sales.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class SalesSysApplication {
	@Bean
	CommandLineRunner init(@Autowired ClientRepository clientRepository){
		return args -> {
			System.out.println("\n[Step 1] : Creating clients... ");
			Client client1 = new Client();
			client1.setName("Tiago");

			Client client2 = new Client();
			client2.setName("Pedro");

			clientRepository.save(client1);
			clientRepository.save(client2);
			System.out.println("\n[Step 2] : Listing clients ... ");
			List<Client> clients = clientRepository.findAll();
			clients.forEach(System.out::println);

			System.out.println("\n[Step 3] : Updating clients... ");
			clients.forEach(c -> {
				c.setName(c.getName()+" Updated");
				clientRepository.save(c);
			});

			System.out.println("\n[Step 4] : Listing clients updated (Finding by name)... ");
			clients = clientRepository.findByNameLike("%Up%");
			clients.forEach(System.out::println);
			System.out.println(clientRepository.existsByNameLike("%Ti%"));
			System.out.println("\n[Step 5] : Deleting clients... ");
			for (Client c : clients) {
				clientRepository.delete(c);
			}

			clients = clientRepository.findAll();
			if(clients.isEmpty()){
				System.out.println("\nNone client :(");
			}else {
				clients.forEach(System.out::println);
			}
		};
	}
	public static void main(String[] args) {
		SpringApplication.run(SalesSysApplication.class, args);
	}

}
