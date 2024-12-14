package parammetr_classes;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

public class ValueComp {
    private final Node nodeValue;
    private String name;
    private String value;
    private String unit;
    private String threshold;

    public ValueComp(Node nodeValue) {
        this.nodeValue = nodeValue;
        this.threshold = "";

        NamedNodeMap attr = nodeValue.getAttributes(); // Получаем его атрибуты
        for (int j = 0; j < attr.getLength(); j++) {
            switch (attr.item(j).getNodeName()) {
                case "Name" -> this.name = attr.item(j).getNodeValue();
                case "Value" -> this.value = attr.item(j).getNodeValue();
                case "Unit" -> this.unit = attr.item(j).getNodeValue();
                case "Threshold" -> threshold = attr.item(j).getNodeValue();
            }
        }

    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }

    public String getUnit() {
        return unit;
    }

    public String getThreshold() {
        return threshold;
    }
}
