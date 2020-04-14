package org.astroman.base.maven.demo.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import org.astroman.base.maven.demo.service.SampleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class SampleControllerTest {

  private static final int SAMPLE_ANSWER = 31;

  @InjectMocks
  private SampleController sampleController;

  @Mock
  private SampleService sampleService;

  @BeforeEach
  public void init() {
    MockitoAnnotations.initMocks(this);
    when(sampleService.getAnswer()).thenReturn(SAMPLE_ANSWER);
  }

  @Test
  public void testGetAnswer() {
    assertThat(sampleController.getAnswer()).isEqualTo(SAMPLE_ANSWER);
  }

  @Test
  public void testHealthCheck() {
    assertThat(sampleController.healthCheck()).isNotBlank();
  }
}