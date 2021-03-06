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

public class SensorRestRequest implements Callable<SensorResponse> {
    private static final RestTemplate REST_TEMPLATE = new RestTemplate();

    private final String sensorUrl;
    private final String sensorName;

    public SensorRestRequest(String sensorUrl, String sensorName) {
        this.sensorUrl = sensorUrl;
        this.sensorName = sensorName;
    }

    @Override
    public SensorResponse call() {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, "application/json");
        headers.add(HttpHeaders.ACCEPT, "*/*");


        HttpEntity<String> requestEntity = new HttpEntity<>("", headers);
        System.out.println("RQ: " + sensorUrl + sensorName);
        ResponseEntity<String> responseEntity = REST_TEMPLATE.exchange(sensorUrl + sensorName, HttpMethod.GET, requestEntity, String.class);
        String responseEntityBody = responseEntity.getBody();

        ObjectMapper objectMapper = new ObjectMapper();

        List<Map<String, Object>> stringObjectList = null;
        try {
            stringObjectList = objectMapper.readValue(responseEntityBody, new TypeReference<List<Map<String, Object>>>() {
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

        Map<String, Object> responseMap = CollectionUtils.isEmpty(stringObjectList) ? Collections.emptyMap() : stringObjectList.get(0);
        return new SensorResponse(sensorName, responseMap);
    }
}
