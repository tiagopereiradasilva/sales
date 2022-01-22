package br.com.company.sales;

import br.com.company.sales.entity.Client;
import br.com.company.sales.entity.Order;
import br.com.company.sales.repository.ClientRepository;
import br.com.company.sales.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@SpringBootApplication
public class SalesSysApplication {
	@Bean
	CommandLineRunner init(@Autowired ClientRepository clientRepository,
						   @Autowired OrderRepository orderRepository){
		return args -> {
			System.out.println("\n[Step 1] : Creating clients... ");
			Client client1 = new Client();
			client1.setName("Tiago");
			clientRepository.save(client1);

			System.out.println("\n[Step 2] : Creating orders... ");
			Order order = new Order();
			order.setClient(client1);
			order.setDateOrder(LocalDate.now());
			order.setTotal(BigDecimal.valueOf(100));
			orderRepository.save(order);

			System.out.println("\n[Step 3] : Listing orders by client (Seeking from entity Client: unusual form of get)...");
			//Unusual form of get ... (Only test for learn)
			System.out.println(clientRepository.findOrdersFetch(client1.getId()).getOrders().toString());

			System.out.println("\n[Step 4] : Listing orders by client (Usual method)... ");
			orderRepository.findByClient(client1).forEach(System.out::println);

		};
	}
	public static void main(String[] args) {
		SpringApplication.run(SalesSysApplication.class, args);
	}

}
