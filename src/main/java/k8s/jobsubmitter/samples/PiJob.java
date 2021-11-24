package k8s.jobsubmitter.samples;

import io.fabric8.kubernetes.api.model.batch.v1.Job;
import io.fabric8.kubernetes.api.model.batch.v1.JobBuilder;
import io.fabric8.kubernetes.client.DefaultKubernetesClient;
import io.fabric8.kubernetes.client.KubernetesClient;

public class PiJob {

    public static void main(String[] args) {

        String namespace = "default";

        Job job = new JobBuilder()
                .withApiVersion("batch/v1")
                .withNewMetadata()
                .withName("pi")
                .endMetadata()
                .withNewSpec()
                .withBackoffLimit(4)
                .withNewTemplate()
                .withNewSpec()
                .addNewContainer()
                .withName("pi")
                .withImage("perl")
                .withCommand("perl", "-Mbignum=bpi", "-wle", "print bpi(2000)")
                .endContainer()
                .withRestartPolicy("Never")
                .endSpec()
                .endTemplate()
                .endSpec().build();


        KubernetesClient kubernetesClient = new DefaultKubernetesClient();
        kubernetesClient.batch().v1().jobs().inNamespace(namespace).createOrReplace(job);
        kubernetesClient.close();

    }
}
