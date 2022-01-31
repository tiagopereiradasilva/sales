package br.com.company.sales.service;

import br.com.company.sales.entity.Client;
import br.com.company.sales.entity.ItemOrder;
import br.com.company.sales.entity.Order;
import br.com.company.sales.entity.Product;
import br.com.company.sales.exception.SalesException;
import br.com.company.sales.repository.ClientRepository;
import br.com.company.sales.repository.ItemOrderRepository;
import br.com.company.sales.repository.OrderRepository;
import br.com.company.sales.repository.ProductRepository;
import br.com.company.sales.rest.dto.ItemOrderDTO;
import br.com.company.sales.rest.dto.OrderDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {
    private ProductRepository productRepository;
    private ClientRepository clientRepository;
    private OrderRepository orderRepository;
    private ItemOrderRepository itemOrderRepository;

    public OrderService(@Autowired ProductRepository productRepository,
                        @Autowired ClientRepository clientRepository,
                        @Autowired OrderRepository orderRepository,
                        @Autowired ItemOrderRepository itemOrderRepository) {
        this.productRepository = productRepository;
        this.clientRepository = clientRepository;
        this.orderRepository = orderRepository;
        this.itemOrderRepository = itemOrderRepository;
    }

    @Transactional
    public Order save(OrderDTO orderDTO){

        //Getting client by id
        Integer idClient = orderDTO.getClient();
        Client client = clientRepository.findById(idClient)
                .orElseThrow(() -> new SalesException("Cliente não encontrado!"));

        //Creating order by dto params
        Order order = new Order();
        order.setDateOrder(LocalDate.now());
        order.setTotal(orderDTO.getTotal());
        order.setClient(client);

        //Saving order
        orderRepository.save(order);

        //Getting list of ItemsOrderDTO converted in a list of ItemOrder entity.
        List<ItemOrder> itemsOrder = converterToItemsOrder(order, orderDTO.getItemsOrder());

        //Saving items in database
        itemOrderRepository.saveAll(itemsOrder);

        //Setting list Items in order entity.
        order.setItemOrders(itemsOrder);

        return order;
    }

    private List<ItemOrder> converterToItemsOrder(Order order, List<ItemOrderDTO> items){
        if(items.isEmpty()){
            throw new SalesException("Pedidos sem itens!");
        }

        return items
                .stream()
                .map(itemOrderDTO -> {
                    // Getting product by id
                    Integer idProduct = itemOrderDTO.getProduct();
                    Product product = productRepository.findById(idProduct)
                            .orElseThrow(() -> new SalesException("Produto não encontrado com id ="+idProduct));

                    // Converting to entity ItemOrder
                    ItemOrder itemOrder = new ItemOrder();
                    itemOrder.setProduct(product);
                    itemOrder.setOrder(order); //Adding items in order
                    itemOrder.setAmount(itemOrderDTO.getAmount());
                    return itemOrder;
                }).collect(Collectors.toList());

    }
}
