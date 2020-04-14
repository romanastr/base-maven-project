package org.astroman.base.maven.demo.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.astroman.base.maven.demo.controller.SampleController.ANSWER_PATH;
import static org.astroman.base.maven.demo.controller.SampleController.HEALTH_CHECK_PATH;
import static org.astroman.base.maven.demo.controller.SampleController.HEALTH_CHECK_TEXT;
import static org.astroman.base.maven.demo.service.SampleService.ANSWER;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SampleControllerIntegrationTest {

  private static final String LOCALHOST = "http://localhost:";

  @LocalServerPort
  private int port;

  @Autowired
  private TestRestTemplate testRestTemplate;

  @Test
  public void testGetHealthCheck() throws Exception {
    assertThat(testRestTemplate.getForObject(LOCALHOST + port + HEALTH_CHECK_PATH, String.class))
        .contains(HEALTH_CHECK_TEXT);
  }

  @Test
  public void testGetAnswer() {
    assertThat(testRestTemplate.getForObject(LOCALHOST + port + ANSWER_PATH, Integer.class))
        .isEqualTo(ANSWER);
  }
}