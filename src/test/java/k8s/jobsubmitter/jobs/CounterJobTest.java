package k8s.jobsubmitter.jobs;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.fabric8.kubernetes.api.model.batch.v1.Job;
import io.fabric8.kubernetes.client.internal.SerializationUtils;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

public class CounterJobTest {

    @Test
    public void itShouldSubmitCorrectJobSpec() throws JsonProcessingException {
        CounterJob counterJob = new CounterJob();
        Job jobConfig = counterJob.createJobConfig("20");
        String fabric8Yaml = SerializationUtils.dumpAsYaml(jobConfig);

        Assert.assertEquals(String.format(yaml, jobConfig.getMetadata().getName(), "20"), fabric8Yaml);

    }

    private static final String yaml =
            "---\n" +
                    "apiVersion: \"batch/v1\"\n" +
                    "kind: \"Job\"\n" +
                    "metadata:\n" +
                    "  name: \"%s\"\n" +
                    "spec:\n" +
                    "  backoffLimit: 4\n" +
                    "  template:\n" +
                    "    spec:\n" +
                    "      containers:\n" +
                    "      - args:\n" +
                    "        - \"echo \\\"Job started\\\"; i=1; while [ $i -le $MAX_COUNT ]; do echo $i; i=$((i+1))\\\n" +
                    "          \\ ; sleep 1;done; echo \\\"Job Done!\\\"\"\n" +
                    "        command:\n" +
                    "        - \"/bin/sh\"\n" +
                    "        - \"-c\"\n" +
                    "        env:\n" +
                    "        - name: \"MAX_COUNT\"\n" +
                    "          value: \"%s\"\n" +
                    "        image: \"busybox\"\n" +
                    "        name: \"counter\"\n" +
                    "      restartPolicy: \"Never\"\n";
}
