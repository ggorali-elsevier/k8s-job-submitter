package k8s.jobsubmitter.jobs;

import io.fabric8.kubernetes.api.model.batch.v1.Job;

public interface KubernetesJob {

    Job createJobConfig(String arg);

}
