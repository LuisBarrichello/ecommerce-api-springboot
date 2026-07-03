package com.luisbarrichello.api.ecommerce;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
@SpringBootTest(properties = {
		"JWT_SECRET=9a4f43c936a5d4e225d10d6778e62c15401241d346d554f72158864aee9a4f43",
		"MELHOR_ENVIO_TOKEN=fake-token",
		"security.jwt.secret-key=9a4f43c936a5d4e225d10d6778e62c15401241d346d554f72158864aee9a4f43",
		"security.jwt.expiration-time=3600000"
})
class EcommerceApplicationTests {

	@Container
	@ServiceConnection
	static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16-alpine");

	@Test
	void contextLoads() {
	}
}