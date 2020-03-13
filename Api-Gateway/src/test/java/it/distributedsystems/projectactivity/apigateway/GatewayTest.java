package it.distributedsystems.projectactivity.apigateway;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
/**
 * GatewayTest
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
@DirtiesContext
public class GatewayTest {
    
    @LocalServerPort
	protected int port = 0;

	protected WebTestClient client;

	@Before
	public void setup() {
		String baseUri = "http://localhost:" + port;
		this.client = WebTestClient.bindToServer().baseUrl(baseUri).build();
    }
    
    @Test
	public void forwardWorks() {
        this.client.post().uri("/users").bodyValue(new User("andrea.sturiale@live.it",29,false))
        .exchange()
        .expectStatus()
		.isOk();
	}
    
}