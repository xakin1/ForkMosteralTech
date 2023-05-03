package es.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "properties", ignoreUnknownFields = false)
public class Properties {
  private String clientHost;

  public String getClientHost() {
    return clientHost;
  }

  public void setClientHost(String clientHost) {
    this.clientHost = clientHost;
  }

  private String environment;

  public String getEnvironment() {
    return environment;
  }

  public void setEnvironment(String environment) {
    this.environment = environment;
  }

  private final Gis gis = new Gis();

  public Gis getGis() {
    return gis;
  }

  public static class Gis {
    @Value("${properties.gis.defaultSrid}")
    private int defaultSrid;

    public int getDefaultSrid() {
      return defaultSrid;
    }

    public void setDefaultSrid(int defaultSrid) {
      this.defaultSrid = defaultSrid;
    }
  }
}
