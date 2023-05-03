package es.web.rest;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.model.domain._entityDomain;

@RestController
@RequestMapping(EnumResource.ENUM_RESOURCE_URL)
public class EnumResource {
  public static final String ENUM_RESOURCE_URL = "/api/enum";

  /**
   * @param name Name of the enum to be retrieved
   * @return Enum values
   */
  @GetMapping("/{name}")
  public ResponseEntity<Object> getEnum(@PathVariable String name) throws ClassNotFoundException {

    Class<?> clazz = Class.forName(_entityDomain.class.getPackage().getName() + "." + name);
    Object values = clazz.getEnumConstants();

    HttpHeaders headers = new HttpHeaders();
    return new ResponseEntity<Object>(values, headers, HttpStatus.OK);
  }
}
