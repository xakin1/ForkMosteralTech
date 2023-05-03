package es.web.rest.custom;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import es.model.util.jackson.CustomGeometrySerializer;

import java.lang.reflect.*;
import java.util.HashMap;
import java.util.Map;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.hibernate.LazyInitializationException;
import org.locationtech.jts.geom.Geometry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FeatureJSON {

  private static final Logger logger = LoggerFactory.getLogger(FeatureJSON.class);

  private Long id;

  private String type = "Feature";

  @JsonSerialize(using = CustomGeometrySerializer.class)
  private Geometry geometry;

  private Map<String, String> properties;

  public FeatureJSON(Class entity, Object object) {
    Field[] fields = entity.getDeclaredFields();
    Map<String, String> entityProperties = new HashMap();
    for (Field field : fields) {
      try {
        if (!isEntity(field)) {
          Object result = FieldUtils.readField(object, field.getName(), true);
          if (result instanceof Geometry == false) {
            entityProperties.put(field.getName(), result == null ? null : result.toString());
          }
        }
      } catch (LazyInitializationException lazyException) {
        continue;
      } catch (Exception exception) {
        logger.error(exception.getMessage(), exception);
      }
    }
    this.setProperties(entityProperties);
  }

  public FeatureJSON() {}

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public Geometry getGeometry() {
    return geometry;
  }

  public void setGeometry(Geometry geometry) {
    this.geometry = geometry;
  }

  public Map<String, String> getProperties() {
    return properties;
  }

  public void setProperties(Map<String, String> properties) {
    this.properties = properties;
  }

  private boolean isEntity(Field field) {
    return field.isAnnotationPresent(ManyToOne.class)
        || field.isAnnotationPresent(ManyToMany.class)
        || field.isAnnotationPresent(OneToMany.class)
        || field.isAnnotationPresent(OneToOne.class);
  }
}
