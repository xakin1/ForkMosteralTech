package es.web.rest.custom;

import java.util.List;

public class FeatureCollectionJSON {

  private String type = "FeatureCollection";
  private List<FeatureJSON> features;

  public FeatureCollectionJSON(List<FeatureJSON> features) {
    this.features = features;
  }

  public FeatureCollectionJSON() {}

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public List<FeatureJSON> getFeatures() {
    return features;
  }

  public void setFeatures(List<FeatureJSON> features) {
    this.features = features;
  }
}
