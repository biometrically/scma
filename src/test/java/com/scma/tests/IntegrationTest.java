package com.scma.tests;

import com.sfl.scma.App;
import com.sfl.scma.domain.*;
import com.sfl.scma.enums.OrderStatus;
import com.sfl.scma.enums.Role;
import com.sfl.scma.exception.ExistingOpenOrderException;
import com.sfl.scma.service.OrderService;
import com.sfl.scma.service.ProductService;
import com.sfl.scma.service.TableService;
import com.sfl.scma.service.UserService;
import javafx.scene.layout.BorderRepeat;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = App.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class IntegrationTest {

    private long managerId;
    private long waiterId;

    @Autowired
    private UserService userService;

    @Autowired
    private TableService tableService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private ProductService productService;

    @Before
    public void setup() {
        managerId = createUser("manager", "manager", Role.ROLE_MANAGER);
        waiterId = createUser("waiter", "waiter", Role.ROLE_WAITER);
    }

    @Test
    public void testAddOrder() {
        Table table = tableService.createTable();
        Product product = productService.createProduct(new Product("Coca Cola", 350));

        tableService.assignWaiter(table.getId(), waiterId);

        Order order = orderService.createOrder(waiterId,
                new OrderRequest(
                        table.getId(),
                        Collections.singletonList(
                                new ProductInOrder(UUID.randomUUID(), product.getId(), 2)
                        )
                )
        );

        assertNotNull(order.getId());
    }

    @Test
    public void testCloseOrder() {
        Table table = tableService.createTable();
        Product product = productService.createProduct(new Product("Coca Cola", 350));

        tableService.assignWaiter(table.getId(), waiterId);

        Order order = orderService.createOrder(waiterId,
                new OrderRequest(
                        table.getId(),
                        Collections.singletonList(
                                new ProductInOrder(UUID.randomUUID(), product.getId(), 2)
                        )
                )
        );

        assertNotNull(order.getId());

        orderService.editOrderStatus(waiterId, order.getId(), OrderStatus.CLOSED);

        order = orderService.createOrder(waiterId,
                new OrderRequest(
                        table.getId(),
                        Collections.singletonList(
                                new ProductInOrder(UUID.randomUUID(), product.getId(), 2)
                        )
                )
        );

        assertNotNull(order.getId());
    }

    @Test(expected = ExistingOpenOrderException.class)
    public void testOrderFailWhenOpenOrderExists() {
        Table table = tableService.createTable();
        Product product = productService.createProduct(new Product("Coca Cola", 350));

        tableService.assignWaiter(table.getId(), waiterId);

        for (int i = 0; i < 2; i++) {
            orderService.createOrder(waiterId,
                    new OrderRequest(
                            table.getId(),
                            Collections.singletonList(
                                    new ProductInOrder(UUID.randomUUID(), product.getId(), 2)
                            )
                    )
            );
        }
    }

    @Test
    public void testAssignWaiter() {
        List<Table> assignedTables;
        Table table = tableService.createTable();

        assignedTables = tableService.getAllTablesAssignedToWaiter(waiterId);
        assertEquals(0, assignedTables.size());

        tableService.assignWaiter(table.getId(), waiterId);
        assignedTables = tableService.getAllTablesAssignedToWaiter(waiterId);
        assertEquals(1, assignedTables.size());
    }

    private long createUser(String username, String password, Role role) {
        User user = new User(
                "user",
                "user",
                username + "@gmail.com",
                username,
                password,
                role
        );

        User createdUser = userService.createUser(user);
        return createdUser.getId();
    }

    @After
    public void tearDown() {
        userService.deleteUser(waiterId);
        userService.deleteUser(managerId);
    }

}