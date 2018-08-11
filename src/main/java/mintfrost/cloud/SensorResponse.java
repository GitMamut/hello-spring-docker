package mintfrost.cloud;

import java.util.Map;

public class SensorResponse {
    private final String sensorName;
    private final Map<String, Object> responseMap;

    public SensorResponse(String sensorName, Map<String, Object> responseMap) {
        this.sensorName = sensorName;
        this.responseMap = responseMap;
    }

    public String getSensorName() {
        return sensorName;
    }

    public Map<String, Object> getResponseMap() {
        return responseMap;
    }
}
