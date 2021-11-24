package k8s.jobsubmitter.api;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JobRequest {
    private String jobName;
    private String arg;
}
