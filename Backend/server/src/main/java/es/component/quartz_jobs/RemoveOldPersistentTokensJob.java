package es.component.quartz_jobs;

import javax.inject.Inject;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import es.model.service.UMUserService;

public class RemoveOldPersistentTokensJob implements Job {

  @Inject private UMUserService umUserService;

  @Override
  public void execute(JobExecutionContext context) throws JobExecutionException {
    umUserService.removeOldPersistentTokens();
  }
}
