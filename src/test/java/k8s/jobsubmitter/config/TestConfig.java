package k8s.jobsubmitter.config;

import io.fabric8.kubernetes.client.KubernetesClient;
import io.fabric8.kubernetes.client.server.mock.KubernetesServer;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

@TestConfiguration
public class TestConfig {

    @Bean(destroyMethod = "close")
    @Primary
    public KubernetesClient testKubernetesClient() {
        KubernetesServer kubernetesServer = new KubernetesServer(true, true);
        kubernetesServer.before();// means init!
        return kubernetesServer.getClient();
    }
}
