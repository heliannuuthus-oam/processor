package io.ghcr.heliannuuthus.devtools.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@RequestMapping("/keys")
public class KeysController {

  @GetMapping("/asymmetric")
  public Mono<String> asymmetricKey() {
    return Mono.just("");
  }

  @GetMapping("/symmetric")
  public Mono<String> symmetricKey() {
    return Mono.just("");
  }
}
