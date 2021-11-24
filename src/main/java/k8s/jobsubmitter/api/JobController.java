package k8s.jobsubmitter.api;

import io.fabric8.kubernetes.api.model.batch.v1.JobStatus;
import k8s.jobsubmitter.service.KubernetesService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/jobs")
@Slf4j
public class JobController {

    @Autowired
    private KubernetesService kubernetesService;

    @PostMapping
    public ResponseEntity<JobResponse> submitJobWithName(@RequestBody JobRequest jobRequest) {
        log.info("Received submit job request for job [{}]", jobRequest.getJobName());
        String jobResult = kubernetesService.submitJob(jobRequest.getJobName(), jobRequest.getArg());
        return ResponseEntity.ok(new JobResponse(jobResult));
    }

    @GetMapping("/{jobInstance}/status")
    public ResponseEntity<JobStatus> getJobStatus(@PathVariable("jobInstance") String jobInstance) {
        log.info("Received job status request for job instance [{}]", jobInstance);
        return ResponseEntity.ok(kubernetesService.getJobStatusByName(jobInstance));
    }

    @GetMapping("/{jobInstance}/log")
    public ResponseEntity<String> getLogForJob(@PathVariable("jobInstance") String jobInstance) {
        log.info("Received logs request for job instance [{}]", jobInstance);
        return ResponseEntity.ok(kubernetesService.getJobLogByName(jobInstance));
    }
}
