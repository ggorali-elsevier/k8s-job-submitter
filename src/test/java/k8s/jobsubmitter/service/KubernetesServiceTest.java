package k8s.jobsubmitter.service;

import io.fabric8.kubernetes.api.model.batch.v1.Job;
import io.fabric8.kubernetes.client.KubernetesClient;
import k8s.jobsubmitter.config.TestConfig;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@SpringBootTest
@Import(TestConfig.class)
public class KubernetesServiceTest {

    @Autowired
    private KubernetesService kubernetesService;

    @Autowired
    private KubernetesClient kubernetesClient;

    @Test
    public void itShouldSubmitCorrectJob() {
        String instance = kubernetesService.submitJob("counter-job", "10");
        Job job = kubernetesClient.batch().v1().jobs().inNamespace("default").withName(instance).get();
        Assert.assertNotNull(job);
        Assert.assertEquals(instance, job.getMetadata().getName());
    }

    @Test
    public void itShouldSubmitCorrectJob2() {
        String instance = kubernetesService.submitJob("pi-job", "10");
        Job job = kubernetesClient.batch().v1().jobs().inNamespace("default").withName(instance).get();
        Assert.assertNotNull(job);
        Assert.assertEquals(instance, job.getMetadata().getName());
    }

    // More tests ...
}
