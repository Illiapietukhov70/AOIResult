package model_classes;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import parammetr_classes.Geometry;
import parammetr_classes.Status;

import java.util.ArrayList;

public class Pin extends MainObj{
    protected Geometry geometry;
    protected Status status;
    protected ArrayList<Node> features;
    private final Node nodeMain;
    private String className;
    private String name;
    private String type;
    private String group;
    private String partNumber;

    public Pin(Node nodeMain) {
        super(nodeMain);
        this.nodeMain = nodeMain;

        NodeList startNode = this.nodeMain.getChildNodes();
        for (int i = 0; i < startNode.getLength(); i++) {
            if (startNode.item(i).getNodeType() == Node.ELEMENT_NODE) {
                switch (startNode.item(i).getNodeName()) {
                    case "Geometry" -> setGeometry(startNode.item(i));// Записываем Геометрию
                    case "Status" -> setStatus(startNode.item(i));// Записываем Статус
                    case "Features" -> addFeature(startNode.item(i)); // Формируем list с Feature
                }
            }
        }
    }
}
