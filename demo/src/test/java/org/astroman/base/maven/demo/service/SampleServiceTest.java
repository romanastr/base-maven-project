package org.astroman.base.maven.demo.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.astroman.base.maven.demo.service.SampleService.ANSWER;

import org.junit.jupiter.api.Test;

class SampleServiceTest {

  private SampleService sampleService = new SampleService();

  @Test
  public void testGetAnswer() {
    assertThat(sampleService.getAnswer()).isEqualTo(ANSWER);
  }
}