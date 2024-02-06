package me.jiniworld.demo.models.values;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@ConfigurationProperties(prefix = "demo.token")
@Component
public class TokenConfig {
  private String typ;
  private String alg;
  private String apiKey;
  private String secretKey;
}
