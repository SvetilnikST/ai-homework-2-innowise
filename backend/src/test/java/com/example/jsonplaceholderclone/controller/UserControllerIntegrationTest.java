package com.example.jsonplaceholderclone.controller;

import com.example.jsonplaceholderclone.model.Address;
import com.example.jsonplaceholderclone.model.Company;
import com.example.jsonplaceholderclone.model.Geo;
import com.example.jsonplaceholderclone.model.User;
import com.example.jsonplaceholderclone.repository.UserRepository;
import com.example.jsonplaceholderclone.config.TestSecurityConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@Transactional
@Import(TestSecurityConfig.class)
@TestPropertySource(properties = {
    "spring.main.allow-bean-definition-overriding=true",
    "spring.security.enabled=false"
})
class UserControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ObjectMapper objectMapper;

    private User user;

    @BeforeEach
    void setUp() {
        userRepository.deleteAll();

        // Create test user
        user = new User();
        user.setName("John Doe");
        user.setUsername("johndoe");
        user.setEmail("john@example.com");
        user.setPassword("password123");

        Address address = new Address();
        address.setStreet("123 Main St");
        address.setSuite("Apt 4B");
        address.setCity("New York");
        address.setZipcode("10001");

        Geo geo = new Geo();
        geo.setLat("40.7128");
        geo.setLng("-74.0060");
        address.setGeo(geo);
        user.setAddress(address);

        Company company = new Company();
        company.setName("Acme Inc");
        company.setCatchPhrase("Making things better");
        company.setBs("Enterprise solutions");
        user.setCompany(company);

        user = userRepository.save(user);
    }

    @Test
    void getAllUsers() throws Exception {
        mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].username", is("johndoe")));
    }

    @Test
    void getUserById() throws Exception {
        mockMvc.perform(get("/users/" + user.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username", is("johndoe")));
    }

    @Test
    void createUser() throws Exception {
        String json = """
        {
            "name": "New User",
            "username": "newuser",
            "email": "new@example.com",
            "password": "password",
            "address": {
                "street": "New St",
                "suite": "Apt 2",
                "city": "New City",
                "zipcode": "54321",
                "geo": {"lat": "1", "lng": "1"}
            },
            "phone": "987-654-3210",
            "website": "new.com",
            "company": {
                "name": "NewCo",
                "catchPhrase": "New everything",
                "bs": "new-bs"
            }
        }
        """;
        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username", is("newuser")));
    }

    @Test
    void updateUser() throws Exception {
        String json = """
        {
            "name": "Updated User",
            "username": "updateduser",
            "email": "updated@example.com",
            "password": "password",
            "address": {
                "street": "Updated St",
                "suite": "Apt 3",
                "city": "Updated City",
                "zipcode": "67890",
                "geo": {"lat": "2", "lng": "2"}
            },
            "phone": "555-555-5555",
            "website": "updated.com",
            "company": {
                "name": "UpdatedCo",
                "catchPhrase": "Update everything",
                "bs": "updated-bs"
            }
        }
        """;
        mockMvc.perform(put("/users/" + user.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username", is("updateduser")));
    }

    @Test
    void deleteUser() throws Exception {
        mockMvc.perform(delete("/users/" + user.getId()))
                .andExpect(status().isNoContent());
        assertFalse(userRepository.findById(user.getId()).isPresent());
    }

    @Test
    void shouldCreateUser() throws Exception {
        // Create test data
        User newUser = new User();
        newUser.setName("Jane Doe");
        newUser.setUsername("janedoe");
        newUser.setEmail("jane@example.com");
        newUser.setPassword("password123");

        Address address = new Address();
        address.setStreet("456 Oak St");
        address.setSuite("Apt 5C");
        address.setCity("Los Angeles");
        address.setZipcode("90001");

        Geo geo = new Geo();
        geo.setLat("34.0522");
        geo.setLng("-118.2437");
        address.setGeo(geo);
        newUser.setAddress(address);

        Company company = new Company();
        company.setName("Tech Corp");
        company.setCatchPhrase("Innovation at its best");
        company.setBs("cutting-edge solutions");
        newUser.setCompany(company);

        // Convert to JSON
        String userJson = objectMapper.writeValueAsString(newUser);

        // Perform the request
        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username", is("janedoe")));
    }
} 