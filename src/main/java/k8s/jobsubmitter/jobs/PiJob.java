package k8s.jobsubmitter.jobs;

import io.fabric8.kubernetes.api.model.batch.v1.Job;
import io.fabric8.kubernetes.api.model.batch.v1.JobBuilder;
import org.springframework.stereotype.Component;

@Component("pi-job")
public class PiJob implements KubernetesJob {

    private static final String JOB_NAME = "pi-job";

    public Job createJobConfig(String arg) {

        String numDigits = arg.isEmpty() ? "100" : arg;

        String jobName = JOB_NAME + "-" + System.currentTimeMillis();

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
                .withName("pi")
                .withImage("perl")
                .withCommand("perl", "-Mbignum=bpi", "-wle", String.format("print bpi(%s)", numDigits))
                .endContainer()
                .withRestartPolicy("Never")
                .endSpec()
                .endTemplate()
                .endSpec().build();

        return job;
    }

}
