package com.scma.tests;

import com.sfl.scma.App;
import com.sfl.scma.domain.Table;
import com.sfl.scma.domain.User;
import com.sfl.scma.enums.Role;
import com.sfl.scma.security.jwt.domain.LoginPayload;
import com.sfl.scma.service.UserService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = App.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SecurityTest {

    @LocalServerPort
    private int localPort;

    private long managerId;
    private String managerJwtToken;

    private long waiterId;
    private String waiterJwtToken;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private UserService userService;

    @Before
    public void setup() {
        managerId = createUser("manager", "manager", Role.ROLE_MANAGER);
        managerJwtToken = login("manager", "manager");

        waiterId = createUser("waiter", "waiter", Role.ROLE_WAITER);
        waiterJwtToken = login("waiter", "waiter");
    }

    @Test
    public void testWaiterMustNotHaveAccessToCreateUser() {
        User user = new User();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", waiterJwtToken);

        HttpEntity<User> request = new HttpEntity<>(user, headers);

        String url = String.format("http://localhost:%d/user", localPort);
        ResponseEntity<User> response = testRestTemplate.postForEntity(url, request, User.class);
        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
    }

    @Test
    public void testWaiterMustNotHaveAccessCreateTable() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", waiterJwtToken);

        HttpEntity<Table> request = new HttpEntity<>(headers);

        String url = String.format("http://localhost:%d/tables", localPort);
        ResponseEntity<Table> response = testRestTemplate.postForEntity(url, request, Table.class);
        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
    }

    @Test
    public void testManagerMustHaveAccessCreateTable() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", managerJwtToken);

        HttpEntity<Table> request = new HttpEntity<>(headers);

        String url = String.format("http://localhost:%d/tables", localPort);
        ResponseEntity<Table> response = testRestTemplate.postForEntity(url, request, Table.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testWaiterMustNotHaveAccessAssignTable() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", waiterJwtToken);

        HttpEntity<Table> request = new HttpEntity<>(headers);

        String url = String.format("http://localhost:%d/tables/1/waiter/%d", localPort, waiterId);
        ResponseEntity<Void> response = testRestTemplate.exchange(url, HttpMethod.PUT, request, Void.class);
        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
    }

    @Test
    public void testWaiterMustNotHaveAccessGetAllTables() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", waiterJwtToken);

        HttpEntity<Table> request = new HttpEntity<>(headers);

        String url = String.format("http://localhost:%d/tables", localPort);
        ResponseEntity<String> response = testRestTemplate.exchange(url, HttpMethod.GET, request, String.class);
        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
    }

    @Test
    public void testManagerMustHaveAccessGetAllTables() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", managerJwtToken);

        HttpEntity<Table> request = new HttpEntity<>(headers);

        String url = String.format("http://localhost:%d/tables", localPort);
        ResponseEntity<String> response = testRestTemplate.exchange(url, HttpMethod.GET, request, String.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    /**
     * Login with the given username and password
     *
     * @return JWT token
     */
    private String login(String username, String password) {
        LoginPayload loginPayload = new LoginPayload(username, password);

        HttpEntity<LoginPayload> request = new HttpEntity<>(loginPayload);

        String url = String.format("http://localhost:%d/login", localPort);
        ResponseEntity<Void> response = testRestTemplate.postForEntity(url, request, Void.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        List<String> authHeader = response.getHeaders().get("Authorization");
        assertNotNull(authHeader);
        assertEquals(1, authHeader.size());

        return authHeader.get(0);
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
        userService.deleteUser(managerId);
        userService.deleteUser(waiterId);
    }

}
