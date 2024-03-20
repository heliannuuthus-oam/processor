package io.ghcr.heliannuuthus.devtools;

import com.ctrip.framework.apollo.spring.annotation.EnableApolloConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.r2dbc.R2dbcAutoConfiguration;

@EnableApolloConfig
@SpringBootApplication(exclude = {R2dbcAutoConfiguration.class})
public class RuntimeApplication {

  public static void main(String[] args) {
    // https://docs.spring.io/spring-boot/docs/2.1.5.RELEASE/reference/html/using-boot-devtools.html#using-boot-devtools-restart-disable
    System.setProperty("spring.devtools.restart.enabled", "false");
    SpringApplication.run(RuntimeApplication.class, args);
  }
}
