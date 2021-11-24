package k8s.jobsubmitter.config;

import io.fabric8.kubernetes.client.DefaultKubernetesClient;
import io.fabric8.kubernetes.client.KubernetesClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {

    @Bean(destroyMethod = "close")
    public KubernetesClient getClient() {
        return new DefaultKubernetesClient();
    }

}
