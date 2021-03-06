package mintfrost.cloud;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;
import java.util.Map;

@RestController
public class SensorController {

    @Value("${sensorUrl}")
    private String sensorUrl;

    @RequestMapping("/sensors/{sensorEndpoint}")
    @ResponseBody
    public Map<String, Map<String, Object>> sensor(@PathVariable(value = "sensorEndpoint") String sensorEndpoint) {
        Map<String, Map<String, Object>> returnMap = new LinkedHashMap<>();
        returnMap.put(sensorEndpoint, new SensorRestRequest(sensorUrl, sensorEndpoint).call().getResponseMap());
        return returnMap;
    }
}
