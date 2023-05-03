package es.model.util;

import org.apache.commons.beanutils.ConversionException;
import org.apache.commons.beanutils.converters.AbstractConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EnumConverter extends AbstractConverter {

  private static final Logger LOGGER = LoggerFactory.getLogger(EnumConverter.class);

  @Override
  protected String convertToString(final Object pValue) throws Throwable {
    return ((Enum) pValue).name();
  }

  @Override
  protected Object convertToType(final Class pType, final Object pValue) throws Throwable {

    final Class<? extends Enum> type = pType;
    try {
      return Enum.valueOf(type, pValue.toString());
    } catch (final IllegalArgumentException e) {
      throw new ConversionException(
          "Error converting"
              + " from "
              + pValue.getClass().getName()
              + " to "
              + type.getName()
              + ". Received value: "
              + pValue);
    }
  }

  @Override
  protected Class getDefaultType() {
    return null;
  }
}
