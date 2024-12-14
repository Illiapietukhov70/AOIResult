package model_classes;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.HashMap;

public class DataModel {
    private final Node modelNote;
    private String name;
    private String barcode;
    private HashMap<String, String> inspectionArray;
    private HashMap<String, Boolean> classificationArray;
    protected Panel panel;



    public DataModel(Node modelNote) {
        this.modelNote = modelNote;
        this.inspectionArray = new HashMap<>();
        this.classificationArray = new HashMap<>();

        NamedNodeMap attr = modelNote.getAttributes();
        for(int j = 0; j < attr.getLength(); j++) {
            switch (attr.item(j).getNodeName()) {
                case "Name" -> this.name = attr.item(j).getNodeValue();
                case "Barcode" -> this.barcode = attr.item(j).getNodeValue();
            }
        }

        NodeList startNode = this.modelNote.getChildNodes();

        for (int i = 0; i < startNode.getLength(); i++) {
            if (startNode.item(i).getNodeType() == Node.ELEMENT_NODE) {
                switch (startNode.item(i).getNodeName()) {
                    case "Inspection" -> setInspection(startNode.item(i));// Записываем Геометрию
                    case "Classification" -> setClassification(startNode.item(i));// Записываем Статус
                    case "Object" -> this.panel = new Panel(startNode.item(i));
                    //Формируем Панель для дальнейшей работы
                }
            }
        }

    }
    private void setInspection(Node startNode) {
        NamedNodeMap attr = startNode.getAttributes();
        for(int j = 0; j < attr.getLength(); j++) {
            inspectionArray.put(attr.item(j).getNodeName(), attr.item(j).getNodeValue());
        }
    }
    private  void setClassification(Node startNode){
        NamedNodeMap attr = startNode.getAttributes();
        for(int j = 0; j < attr.getLength(); j++) {
            classificationArray.put(attr.item(j).getNodeName(), Boolean.parseBoolean(attr.item(j).getNodeValue()));

        }
    }

    public String getName() {
        return this.name;
    }

    public String getBarcode() {
        return this.barcode;
    }

    public String getInspectionArray(String key) { return inspectionArray.get(key); }

    public boolean getClassificationArray(String key) { return classificationArray.get(key);}

    public Panel getPanel() {
        return this.panel;
    }
}
