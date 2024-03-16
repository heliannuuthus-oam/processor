package io.ghcr.heliannuuthus.devtools.config;

import java.security.Security;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SecuredConfiguration {
  static {
    Security.addProvider(new BouncyCastleProvider());
  }
}
