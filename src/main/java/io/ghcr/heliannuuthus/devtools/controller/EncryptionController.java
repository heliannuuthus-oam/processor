package io.ghcr.heliannuuthus.devtools.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.stream.Stream;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/crypto")
@Tag(name = "encrypt/decrypt api")
public class EncryptionController {

  @GetMapping("/encryption")
  public Flux<String> algorithm() {
    return Flux.fromStream(Stream.of("AES", "RSA-OAEP", "ECIES"));
  }
}
