package es.component.quartz_jobs;

import es.model.domain.user_management.UMUser;
import es.model.repository.user_management.UMUserRepository;
import es.model.service.UMUserService;
import es.model.service.exceptions.account.AccountException;
import es.security.AuthoritiesConstants;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import javax.inject.Inject;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

public class InitDBJob implements Job {

  @Inject private UMUserRepository userRepository;

  @Inject private UMUserService userService;

  @Value("${initDB.defaultAdminUser}")
  private String adminUser;

  @Value("${initDB.defaultAdminPassword}")
  private String adminPassword;

  @Value("${initDB.defaultAdminEmail}")
  private String adminEmail;

  private final Logger log = LoggerFactory.getLogger(InitDBJob.class);

  @Override
  public void execute(JobExecutionContext context) throws JobExecutionException {
    try {
      if (userRepository.count() == 0) {
        log.debug("Creating user \"admin\" in the database");
        UMUser u;
        // Admin
        u =
            userService.createUser(
                adminUser, adminPassword, "Administrador", null, adminEmail, "en");
        Set<String> authorities = new HashSet<>(Arrays.asList(AuthoritiesConstants.ADMIN));
        userService.updateUser(
            u.getId(),
            u.getLogin(),
            u.getFirstName(),
            u.getLastName(),
            u.getEmail(),
            u.getLangKey(),
            authorities);
        log.debug("User created successfully");
      }
    } catch (AccountException e) {
      log.error(e.getMessage());
    }
  }
}
