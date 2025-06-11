package com.example.jsonplaceholderclone.config;

import com.example.jsonplaceholderclone.model.Address;
import com.example.jsonplaceholderclone.model.Company;
import com.example.jsonplaceholderclone.model.Geo;
import com.example.jsonplaceholderclone.model.User;
import com.example.jsonplaceholderclone.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Component
public class DataInitializer implements CommandLineRunner {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        if (userRepository.count() == 0) {
            List<User> users = List.of(
                createUser(
                    "Leanne Graham",
                    "Bret",
                    "Sincere@april.biz",
                    "password",
                    createAddress(
                        "Kulas Light",
                        "Apt. 556",
                        "Gwenborough",
                        "92998-3874",
                        createGeo("-37.3159", "81.1496")
                    ),
                    "1-770-736-8031 x56442",
                    "hildegard.org",
                    createCompany(
                        "Romaguera-Crona",
                        "Multi-layered client-server neural-net",
                        "harness real-time e-markets"
                    )
                ),
                createUser(
                    "Ervin Howell",
                    "Antonette",
                    "Shanna@melissa.tv",
                    "password",
                    createAddress(
                        "Victor Plains",
                        "Suite 879",
                        "Wisokyburgh",
                        "90566-7771",
                        createGeo("-43.9509", "-34.4618")
                    ),
                    "010-692-6593 x09125",
                    "anastasia.net",
                    createCompany(
                        "Deckow-Crist",
                        "Proactive didactic contingency",
                        "synergize scalable supply-chains"
                    )
                )
            );
            userRepository.saveAll(users);
        }
    }

    private User createUser(String name, String username, String email, String password,
                          Address address, String phone, String website, Company company) {
        User user = new User();
        user.setName(name);
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        user.setAddress(address);
        user.setPhone(phone);
        user.setWebsite(website);
        user.setCompany(company);
        return user;
    }

    private Address createAddress(String street, String suite, String city, String zipcode, Geo geo) {
        Address address = new Address();
        address.setStreet(street);
        address.setSuite(suite);
        address.setCity(city);
        address.setZipcode(zipcode);
        address.setGeo(geo);
        return address;
    }

    private Geo createGeo(String lat, String lng) {
        Geo geo = new Geo();
        geo.setLat(lat);
        geo.setLng(lng);
        return geo;
    }

    private Company createCompany(String name, String catchPhrase, String bs) {
        Company company = new Company();
        company.setName(name);
        company.setCatchPhrase(catchPhrase);
        company.setBs(bs);
        return company;
    }
} 