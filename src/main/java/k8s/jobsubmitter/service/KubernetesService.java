package k8s.jobsubmitter.service;

import io.fabric8.kubernetes.api.model.batch.v1.Job;
import io.fabric8.kubernetes.api.model.batch.v1.JobStatus;
import io.fabric8.kubernetes.client.KubernetesClient;
import k8s.jobsubmitter.jobs.KubernetesJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@Slf4j
public class KubernetesService {

    private static final String NAMESPACE = "default";

    @Autowired
    private KubernetesClient kubernetesClient;

    @Autowired
    private Map<String, KubernetesJob> jobRegistry;

    public String submitJob(String jobName, String theArgument) {

        if (jobRegistry.get(jobName) == null) {
            return String.format("No job with name [%s] found", jobName);
        }

        Job job = jobRegistry.get(jobName).createJobConfig(theArgument);
        kubernetesClient.batch().v1().jobs().inNamespace(NAMESPACE).createOrReplace(job);

        String jobInstance = job.getMetadata().getName();
        log.info("Job [{}] created", jobInstance);
        return jobInstance;

    }

    public JobStatus getJobStatusByName(String jobInstance) {
        Job job = kubernetesClient.batch().v1().jobs().inNamespace(NAMESPACE).withName(jobInstance).get();
        return job.getStatus();
    }

    public String getJobLogByName(String jobInstance) {
        return kubernetesClient.batch().v1().jobs().inNamespace(NAMESPACE).withName(jobInstance).getLog();
    }
}
