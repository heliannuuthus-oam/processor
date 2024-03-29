package io.ghcr.heliannuuthus.devtools.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties("oam.devtool.crypto")
public class CryptoProperties {}
