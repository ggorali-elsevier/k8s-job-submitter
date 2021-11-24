package k8s.jobsubmitter.jobs;

import io.fabric8.kubernetes.api.model.EnvVarBuilder;
import io.fabric8.kubernetes.api.model.batch.v1.Job;
import io.fabric8.kubernetes.api.model.batch.v1.JobBuilder;
import org.springframework.stereotype.Component;

@Component("counter-job")
public class CounterJob implements KubernetesJob {

    private static final String JOB_NAME = "counter-job";

    @Override
    public Job createJobConfig(String arg) {

        String jobName = JOB_NAME + "-" + System.currentTimeMillis();
        String maxCount = arg.isEmpty() ? "10" : arg;

        Job job = new JobBuilder()
                .withApiVersion("batch/v1")
                .withNewMetadata()
                .withName(jobName)
                .endMetadata()
                .withNewSpec()
                .withBackoffLimit(4)
                .withNewTemplate()
                .withNewSpec()
                .addNewContainer()
                .withName("counter")
                .withImage("busybox")
                .withEnv(new EnvVarBuilder().withName("MAX_COUNT").withValue(maxCount).build())
                .withCommand("/bin/sh", "-c")
                .withArgs("echo \"Job started\"; i=1; while [ $i -le $MAX_COUNT ]; do echo $i; i=$((i+1)) ; sleep 1;done; echo \"Job Done!\"")
                .endContainer()
                .withRestartPolicy("Never")
                .endSpec()
                .endTemplate()
                .endSpec().build();

        return job;
    }
}
