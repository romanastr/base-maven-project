package org.astroman.base.maven.demo.service;

import org.springframework.stereotype.Component;

@Component
public class SampleService {

  public static final int ANSWER = 42;

  public int getAnswer() {
    return ANSWER;
  }

}
