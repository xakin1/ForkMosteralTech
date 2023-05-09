package es.config;

import es.model.util.EnumAwareConvertUtilsBean;
import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.beanutils.ConvertUtilsBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConvertersConfiguration {

  /**
   * Adds support to Enum conversion in ConvertUtils.
   *
   * @return
   */
  @Bean(name = "convertUtils")
  public ConvertUtilsBean convertUtils() {
    BeanUtilsBean.setInstance(new BeanUtilsBean(new EnumAwareConvertUtilsBean()));
    return BeanUtilsBean.getInstance().getConvertUtils();
  }

  /**
   * Adds support to Enum conversion in ConvertUtils. <br>
   * If conversion fails a exception is thrown
   *
   * @return
   */
  @Bean(name = "convertUtilsWithExceptions")
  public ConvertUtilsBean convertUtilsWithExceptions() {
    BeanUtilsBean.setInstance(new BeanUtilsBean(new EnumAwareConvertUtilsBean()));
    BeanUtilsBean.getInstance().getConvertUtils().register(true, true, 0);
    return BeanUtilsBean.getInstance().getConvertUtils();
  }
}
