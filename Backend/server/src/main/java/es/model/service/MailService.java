package es.model.service;

import es.model.domain.user_management.UMUser;
import java.util.Locale;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

@Service
public class MailService {

  private static final Logger log = LoggerFactory.getLogger(MailService.class);

  private Locale locale = LocaleContextHolder.getLocale();

  @Inject private Environment env;

  @Inject private JavaMailSenderImpl javaMailSender;

  @Inject private MessageSource messageSource;

  @Inject private SpringTemplateEngine templateEngine;

  /** System default email address that sends the e-mails. */
  private String from;

  @PostConstruct
  public void init() {
    this.from = env.getProperty("spring.mail.from");
  }

  @Async
  private void sendMail(
      String to, String subject, String content, boolean isMultipart, boolean isHtml) {
    log.debug(
        "Send mail[multipart '{}' and html '{}'] to '{}' with subject '{}' and content={}",
        isMultipart,
        isHtml,
        to,
        subject,
        content);
    // Prepare message using a Spring helper
    MimeMessage mimeMessage = javaMailSender.createMimeMessage();
    try {
      MimeMessageHelper message = new MimeMessageHelper(mimeMessage, isMultipart, "UTF-8");
      message.setTo(to);
      message.setFrom(from);
      message.setSubject(subject);
      message.setText(content, isHtml);
      javaMailSender.send(mimeMessage);
      log.debug("Sent e-mail to UMUser '{}'", to);
    } catch (Exception e) {
      log.warn("E-mail could not be sent to user '{}', exception is: {}", to, e.getMessage());
    }
  }

  /**
   * Email with account details and sign-in link
   *
   * @param user User registered
   * @param baseUrl Application base URL
   */
  @Async
  @Retryable(
      maxAttempts = 5,
      backoff = @Backoff(delay = 3000, maxDelay = 10000, multiplier = 3),
      value = Exception.class)
  public void sendAccountInformationEmail(UMUser user, String baseUrl) {
    log.debug("Sending account information e-mail to '{}'", user.getEmail());
    Locale locale = Locale.forLanguageTag(user.getLangKey());
    Context context = new Context(locale);
    context.setVariable("user", user);
    context.setVariable("baseUrl", baseUrl);
    context.setVariable("appName", "monsteral-tech");
    String content = templateEngine.process("accountInformationEmail", context);
    log.debug(content);
    String subject =
        messageSource.getMessage(
            "mail.account.information.title", new Object[] {"monsteral-tech"}, locale);
    sendMail(user.getEmail(), subject, content, false, true);
  }

  @Async
  @Retryable(
      maxAttempts = 5,
      backoff = @Backoff(delay = 3000, maxDelay = 10000, multiplier = 3),
      value = Exception.class)
  public void sendPasswordResetMail(UMUser user, String baseUrl) {
    log.debug("Sending password reset e-mail to '{}'", user.getEmail());
    Locale locale = Locale.forLanguageTag(user.getLangKey());
    Context context = new Context(locale);
    context.setVariable("user", user);
    context.setVariable("baseUrl", baseUrl);
    String content = templateEngine.process("accountPasswordResetEmail", context);
    String subject = "Account password reset";
    sendMail(user.getEmail(), subject, content, false, true);
  }
}
