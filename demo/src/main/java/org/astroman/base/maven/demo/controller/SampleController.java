package org.astroman.base.maven.demo.controller;

import lombok.AllArgsConstructor;
import org.astroman.base.maven.demo.service.SampleService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class SampleController {

  public static final String HEALTH_CHECK_TEXT = "OK";
  public static final String HEALTH_CHECK_PATH = "/";
  public static final String ANSWER_PATH = "/answer";

  private SampleService sampleService;

  @GetMapping(HEALTH_CHECK_PATH)
  public String healthCheck() {
    return HEALTH_CHECK_TEXT;
  }

  @GetMapping(ANSWER_PATH)
  public int getAnswer() {
    return sampleService.getAnswer();
  }

}
