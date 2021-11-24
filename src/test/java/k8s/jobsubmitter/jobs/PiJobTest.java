package k8s.jobsubmitter.jobs;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.fabric8.kubernetes.api.model.batch.v1.Job;
import io.fabric8.kubernetes.client.internal.SerializationUtils;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

public class PiJobTest {

    @Test
    public void itShouldSubmitCorrectJobSpec() throws JsonProcessingException {
        PiJob piJob = new PiJob();
        Job jobConfig = piJob.createJobConfig("20000");
        String fabric8Yaml = SerializationUtils.dumpAsYaml(jobConfig);
        System.out.println(fabric8Yaml);

        Assert.assertEquals(String.format(yaml, jobConfig.getMetadata().getName(), "20000"), fabric8Yaml);

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
                        "      - command:\n" +
                        "        - \"perl\"\n" +
                        "        - \"-Mbignum=bpi\"\n" +
                        "        - \"-wle\"\n" +
                        "        - \"print bpi(%s)\"\n" +
                        "        image: \"perl\"\n" +
                        "        name: \"pi\"\n" +
                        "      restartPolicy: \"Never\"\n";
    }