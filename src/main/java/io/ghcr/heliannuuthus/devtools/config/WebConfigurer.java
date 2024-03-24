package io.ghcr.heliannuuthus.devtools.config;

import io.ghcr.heliannuuthus.devtools.constants.CodecFormat;
import io.ghcr.heliannuuthus.devtools.constants.KeyAlgorithm;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.reactive.config.WebFluxConfigurer;

@Configuration
public class WebConfigurer implements WebFluxConfigurer {

  interface keyAlgorithmConverter extends Converter<String, KeyAlgorithm> {}

  interface CodecFormatConverter extends Converter<String, CodecFormat> {}

  @Override
  public void addFormatters(FormatterRegistry registry) {
    registry.addConverter(
        (keyAlgorithmConverter) source -> KeyAlgorithm.valueOf(source.toUpperCase()));
    registry.addConverter(
        (CodecFormatConverter) source -> CodecFormat.valueOf(source.toUpperCase()));
  }
}
