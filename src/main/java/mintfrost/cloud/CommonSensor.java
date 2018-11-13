package mintfrost.cloud;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class CommonSensor {
    private final String name;
    private final Date date;
    private final List<CommonSensorValue> values;


    public CommonSensor() {
        name = "indoor";
        date = new Date();
        values = Arrays.asList(new CommonSensorValue("temperature", "100.0"), new CommonSensorValue("humidity", "99.0"));
    }

    public String getName() {
        return name;
    }

    public Date getDate() {
        return date;
    }

    public List<CommonSensorValue> getValues() {
        return values;
    }

    public static class CommonSensorValue {
        private final String name;
        private final String value;

        public CommonSensorValue(String name, String value) {
            this.name = name;
            this.value = value;
        }

        public String getName() {
            return name;
        }

        public String getValue() {
            return value;
        }
    }
}
