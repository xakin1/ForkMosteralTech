package es.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "security-properties", ignoreUnknownFields = false)
public class SecurityProperties {

  private final Security security = new Security();

  public Security getSecurity() {
    return security;
  }

  public static class Security {
    private String jwtSecretKey;
    private Long jwtValidity;
    private Long shortJwtValidity;

    public String getJwtSecretKey() {
      return jwtSecretKey;
    }

    public void setJwtSecretKey(String jwtSecretKey) {
      this.jwtSecretKey = jwtSecretKey;
    }

    public Long getJwtValidity() {
      return jwtValidity;
    }

    public void setJwtValidity(Long jwtValidity) {
      this.jwtValidity = jwtValidity;
    }

    public Long getShortJwtValidity() {
      return shortJwtValidity;
    }

    public void setShortJwtValidity(Long shortJwtValidity) {
      this.shortJwtValidity = shortJwtValidity;
    }
  }
}
