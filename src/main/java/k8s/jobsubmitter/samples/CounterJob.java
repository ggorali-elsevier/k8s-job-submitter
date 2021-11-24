package k8s.jobsubmitter.samples;

import io.fabric8.kubernetes.api.model.EnvVarBuilder;
import io.fabric8.kubernetes.api.model.PodList;
import io.fabric8.kubernetes.api.model.batch.v1.Job;
import io.fabric8.kubernetes.api.model.batch.v1.JobBuilder;
import io.fabric8.kubernetes.client.DefaultKubernetesClient;
import io.fabric8.kubernetes.client.KubernetesClient;

import java.util.concurrent.TimeUnit;

public class CounterJob {

    public static void main(String[] args) {

        String namespace = "default";
        String jobName = "counter-fabric8";

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
                .withEnv(new EnvVarBuilder().withName("MAX_COUNT").withValue("5").build())
                .withCommand("/bin/sh", "-c")
                .withArgs("echo \"Job started\"; i=1; while [ $i -le $MAX_COUNT ]; do echo $i; i=$((i+1)) ; sleep 1;done; echo \"Job Done!\"")
                .endContainer()
                .withRestartPolicy("Never")
                .endSpec()
                .endTemplate()
                .endSpec().build();


        KubernetesClient kubernetesClient = new DefaultKubernetesClient();
        kubernetesClient.batch().v1().jobs().inNamespace(namespace).createOrReplace(job);

        PodList podList = kubernetesClient.pods().inNamespace(namespace).withLabel("job-name", job.getMetadata().getName()).list();

        kubernetesClient.pods().inNamespace(namespace).withName(podList.getItems().get(0).getMetadata().getName())
                .waitUntilCondition(pod -> pod.getStatus().getPhase().equals("Succeeded"), 1, TimeUnit.MINUTES);

        String joblog = kubernetesClient.batch().v1().jobs().inNamespace(namespace).withName(jobName).getLog();
        System.out.println(joblog);
        kubernetesClient.close();

    }
}
