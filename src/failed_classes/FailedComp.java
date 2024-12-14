package failed_classes;

import model_classes.Comp;
import model_classes.Pin;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import parammetr_classes.Feature;

public class FailedComp extends Comp {
    public FailedComp(Node nodePin) {
        super(nodePin);
    }
    protected void addPin(Node nodePin) {
        Pin pin = new Pin(nodePin);
        if(pin.getStatus().getOverall("IsFailed")){
            pins.add(nodePin);
        }
    }
    protected void addFeature(Node inputFeatures) {
        NodeList nodeNext = inputFeatures.getChildNodes(); // Получаем список детей входящего Нода
        for (int i = 0; i < nodeNext.getLength(); i++) {// Перебираем список
            if (nodeNext.item(i).getNodeType() == Node.ELEMENT_NODE) {
                if(nodeNext.item(i).getNodeName().equals("Feature")) {//Если Feature - добавляем в Лист features
                    Feature feature = new Feature(nodeNext.item(i));
                    if(feature.getStatus().getOverall("IsFailed")) {//Если Feature.Status.Overall = "IsFailed"
                        features.add(nodeNext.item(i));
                    }
                }
            }

        }
    }
}
