package com.luisbarrichello.api.ecommerce.config.seed;

import com.luisbarrichello.api.ecommerce.model.role.Role;
import com.luisbarrichello.api.ecommerce.repository.role.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class RoleSeeder implements CommandLineRunner {
    private final RoleRepository roleRepository;

    public RoleSeeder(RoleRepository repository) {
        this.roleRepository = repository;
    }


    @Override
    public void run(String... args) throws Exception {
        if (roleRepository.count() == 0) {
            Role costumerRole = new Role();
            costumerRole.setName("ROLE_CUSTOMER");

            Role adminRole = new Role();
            adminRole.setName("ROLE_ADMIN");

            Role managerRole = new Role();
            managerRole.setName("ROLE_MANAGER");

            roleRepository.saveAll(List.of(costumerRole, adminRole, managerRole));
            System.out.println("RoleSeeder: Initial functions successfully registered!");
        }
    }
}
