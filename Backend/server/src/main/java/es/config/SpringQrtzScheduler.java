package es.config;

import es.component.quartz_jobs.*;
import java.util.Map;
import javax.sql.DataSource;
import org.quartz.Trigger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.quartz.QuartzProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.*;

@Configuration
@EnableAutoConfiguration
public class SpringQrtzScheduler {

  @Autowired private QuartzProperties quartzProperties;
  @Autowired private ApplicationContext applicationContext;
  private final String DAILY_AT_TWELVE = "0 0 0 ? * * *";

  @Bean
  public SpringBeanJobFactory springBeanJobFactory() {
    AutoWiringSpringBeanJobFactory jobFactory = new AutoWiringSpringBeanJobFactory();

    jobFactory.setApplicationContext(applicationContext);
    return jobFactory;
  }

  @Bean
  public SchedulerFactoryBean scheduler(DataSource quartzDataSource) {

    SchedulerFactoryBean schedulerFactory = new SchedulerFactoryBean();

    schedulerFactory.setJobFactory(springBeanJobFactory());
    schedulerFactory.setSchedulerName("qrtz_scheduler");
    schedulerFactory.setQuartzProperties(asProperties(quartzProperties.getProperties()));

    // All jobs to schedule
    Trigger[] triggers = {
      removeOldPersistentTokensTrigger().getObject(), initDBJobTrigger().getObject(),
    };
    schedulerFactory.setTriggers(triggers);
    schedulerFactory.setDataSource(quartzDataSource);

    return schedulerFactory;
  }

  /*
   * Jobs & triggers
   */
  @Bean
  public JobDetailFactoryBean removeOldPersistentTokensJobDetail() {
    JobDetailFactoryBean jobDetailFactory = new JobDetailFactoryBean();
    jobDetailFactory.setJobClass(RemoveOldPersistentTokensJob.class);
    jobDetailFactory.setDurability(true);
    jobDetailFactory.setGroup("user-management");
    return jobDetailFactory;
  }

  @Bean
  public CronTriggerFactoryBean removeOldPersistentTokensTrigger() {
    CronTriggerFactoryBean trigger = new CronTriggerFactoryBean();
    trigger.setJobDetail(removeOldPersistentTokensJobDetail().getObject());
    trigger.setGroup("user-management");
    trigger.setCronExpression(DAILY_AT_TWELVE);
    return trigger;
  }

  @Bean
  public JobDetailFactoryBean initDBJobDetail() {
    JobDetailFactoryBean jobDetailFactory = new JobDetailFactoryBean();
    jobDetailFactory.setJobClass(InitDBJob.class);
    jobDetailFactory.setDurability(true);
    jobDetailFactory.setGroup("user-management");
    return jobDetailFactory;
  }

  @Bean
  public SimpleTriggerFactoryBean initDBJobTrigger() {
    SimpleTriggerFactoryBean trigger = new SimpleTriggerFactoryBean();
    trigger.setJobDetail(initDBJobDetail().getObject());
    trigger.setGroup("user-management");
    trigger.setRepeatCount(0);
    return trigger;
  }

  /*
   * Util methods
   */

  private java.util.Properties asProperties(Map source) {
    java.util.Properties properties = new java.util.Properties();
    properties.putAll(source);
    return properties;
  }
}
