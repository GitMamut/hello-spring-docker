package mintfrost.cloud;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

public class SensorRestRequest implements Callable<Map<String, Object>> {
    final private String sensorEndpoint;

    SensorRestRequest(String sensorEndpoint) {
        this.sensorEndpoint = sensorEndpoint;
    }

    @Override
    public Map<String, Object> call() {
        RestTemplate rest = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        headers.add("Accept", "*/*");


        HttpEntity<String> requestEntity = new HttpEntity<>("", headers);
        ResponseEntity<String> responseEntity = rest.exchange("http://192.168.1.27:8080/" + sensorEndpoint, HttpMethod.GET, requestEntity, String.class);
        String responseEntityBody = responseEntity.getBody();

        ObjectMapper objectMapper = new ObjectMapper();

        List<Map<String, Object>> stringObjectList = null;
        try {
            stringObjectList = objectMapper.readValue(responseEntityBody, new TypeReference<List<Map<String, Object>>>() {
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

        return CollectionUtils.isEmpty(stringObjectList) ? Collections.emptyMap() : stringObjectList.get(0);
    }
}
