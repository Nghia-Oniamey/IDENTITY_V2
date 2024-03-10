package fplhn.udpm.identity.infrastructure.config.excelconfig.jobconfig;

import fplhn.udpm.identity.infrastructure.config.uploadfileconfig.service.FileUploadService;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

@Component
@Slf4j
@NoArgsConstructor
public class NhanVienJobLauncher {

    private final AtomicBoolean enabled = new AtomicBoolean(false);

    @Autowired
    @Qualifier("excelFileToDatabaseJob")
    private Job job;

    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    @Qualifier("excelService")
    private FileUploadService fileUploadService;

    @Setter
    private String fullPathFileName;


    @Scheduled(cron = "${cron-job.time}")
    void launchExcelToDatabaseJob() {
        if (enabled.get() && fullPathFileName != null) {
            try {
                log.info("Launching excel to database job");
                JobExecution jobExecution = jobLauncher.run(job, newExecution());
                ExitStatus exitStatus = jobExecution.getExitStatus();
                log.info("Exit status: {}", exitStatus);
                if (exitStatus.getExitCode().equals(ExitStatus.COMPLETED.getExitCode())) {
                    fileUploadService.delete(fullPathFileName);
                }
                if (exitStatus.getExitCode().equals(ExitStatus.FAILED.getExitCode())) {
                    log.error("Error launching excel to database job");
                }
            } catch (Exception e) {
                log.error("Error launching excel to database job", e);
            } finally {
                fullPathFileName = null;
                disable();
            }
        }
    }

    private JobParameters newExecution() {
        Map<String, JobParameter<?>> parameters = new HashMap<>();
        parameters.put("time", new JobParameter<>(new Date(), Date.class));
        parameters.put("fullPathFileName", new JobParameter<>(fullPathFileName, String.class));
        return new JobParameters(parameters);
    }

    public void enable() {
        enabled.set(true);
    }

    public void disable() {
        enabled.set(false);
        log.info("Import file successfully");
    }

}
