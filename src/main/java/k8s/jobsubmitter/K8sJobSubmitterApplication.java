package k8s.jobsubmitter;

import k8s.jobsubmitter.service.KubernetesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class K8sJobSubmitterApplication {

    public static void main(String[] args) {
        SpringApplication.run(K8sJobSubmitterApplication.class, args);
    }

    @Autowired
    private KubernetesService kubernetesService;

//	@Override
//	public void run(String... args) throws Exception {
//		kubernetesService.submitJob("jobName");
////		System.exit(0);
//	}
}
