package es.component.quartz_jobs;

import es.model.service.UMUserService;
import javax.inject.Inject;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class RemoveOldPersistentTokensJob implements Job {

  @Inject private UMUserService umUserService;

  @Override
  public void execute(JobExecutionContext context) throws JobExecutionException {
    umUserService.removeOldPersistentTokens();
  }
}
