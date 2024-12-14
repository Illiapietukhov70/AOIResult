package model_classes;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import parammetr_classes.Geometry;
import parammetr_classes.Status;

import java.util.ArrayList;

public class MainObj {
    protected Geometry geometry;
    protected Status status;
    protected ArrayList<Node> features;
    private  Node nodeMain;
    private String className;
    private String name;
    private String type;
    private String group;
    private String partNumber;

    public MainObj(Node nodeMain) {
        this.geometry = new Geometry();
        this.status = new Status();
        this.features = new ArrayList<>();
        this.nodeMain = nodeMain;
        NamedNodeMap attr = nodeMain.getAttributes();
        for(int j = 0; j < attr.getLength(); j++) {
            switch (attr.item(j).getNodeName()) {
                case "Class" -> this.className = attr.item(j).getNodeValue();
                case "Name" -> this.name = attr.item(j).getNodeValue();
                case "Type" -> this.type = attr.item(j).getNodeValue();
                case "Group" -> this.group = attr.item(j).getNodeValue();
                case "PartNumber" -> this.partNumber = attr.item(j).getNodeValue();
            }
        }
    }

    protected void setStatus (Node inputStatus) { // Передаем Ноде (startNode.item(i) с именем Status) для обработки Статуса
        NodeList nodeNext = inputStatus.getChildNodes(); // Получаем список детей входящего Нода
        for (int i = 0; i < nodeNext.getLength(); i++) {// Перебираем список
            if (nodeNext.item(i).getNodeType() == Node.ELEMENT_NODE) {
                switch (nodeNext.item(i).getNodeName()) {
                    case "Overall" -> setDictOverall(nodeNext.item(i));// Если имя подребенка Overall - пишем в Status словарь Overall
                    case "Inspection" -> setDictInspection(nodeNext.item(i));// то-же самое
                    case "Classification" -> setDictClassification(nodeNext.item(i));
                    case "Repair" -> setDictRepair(nodeNext.item(i));
                }

            }
        }
    }
    protected void setDictOverall (Node nodeDict) {// передаем подребенка с именем Overall для записи словаря Status.Overall
        NamedNodeMap attr = nodeDict.getAttributes();
        for (int j = 0; j < attr.getLength(); j++) {
            status.putOverall(attr.item(j).getNodeName(), Boolean.parseBoolean(attr.item(j).getNodeValue()));
        }
    }

    protected void setDictInspection (Node nodeDict) {// то-же самое
        NamedNodeMap attr = nodeDict.getAttributes();
        for (int j = 0; j < attr.getLength(); j++) {
            status.putInspection(attr.item(j).getNodeName(), Boolean.parseBoolean(attr.item(j).getNodeValue().toLowerCase()));
        }
    }
    protected void setDictClassification (Node nodeDict) {
        NamedNodeMap attr = nodeDict.getAttributes();
        for (int j = 0; j < attr.getLength(); j++) {
            status.putClassification(attr.item(j).getNodeName(), Boolean.parseBoolean(attr.item(j).getNodeValue().toLowerCase()));
        }
    }
    protected void setDictRepair (Node nodeDict) {
        NamedNodeMap attr = nodeDict.getAttributes();
        for (int j = 0; j < attr.getLength(); j++) {
            status.putRepair(attr.item(j).getNodeName(), Boolean.parseBoolean(attr.item(j).getNodeValue().toLowerCase()));
        }
    }
    public Status getStatus (){
        return this.status;
    }
    protected void setGeometry(Node inputGeometry){
        NodeList nodeNext = inputGeometry.getChildNodes(); // Получаем список детей входящего Нода
        for (int i = 0; i < nodeNext.getLength(); i++) {// Перебираем список
            if (nodeNext.item(i).getNodeType() == Node.ELEMENT_NODE) {
                switch (nodeNext.item(i).getNodeName()) {
                    case "Position" -> setPosition(nodeNext.item(i), this.geometry.getPosition());
                    case "PositionLocal" -> setPosition(nodeNext.item(i), this.geometry.getPositionLocal());
                    case "Rotation" -> setPosition(nodeNext.item(i), this.geometry.getRotation());
                    case "RotationLocal" -> setPosition(nodeNext.item(i), this.geometry.getRotationLocal());
                    case "Size" -> setPosition(nodeNext.item(i), this.geometry.getSize());
                    case "SizeLocal" -> setPosition(nodeNext.item(i), this.geometry.getSizeLocal());
                }

            }
        }

    }
    private void setPosition (Node nodeArray, int[] nameArray){
        NamedNodeMap attr = nodeArray.getAttributes();
        for (int j = 0; j < attr.getLength(); j++) {
            nameArray[j] = Integer.parseInt(attr.item(j).getNodeValue());
        }

    }
    public Geometry getGeometry() {
        return this.geometry;
    }

    protected void addFeature(Node inputFeatures) {
        NodeList nodeNext = inputFeatures.getChildNodes(); // Получаем список детей входящего Нода
        for (int i = 0; i < nodeNext.getLength(); i++) {// Перебираем список
            if (nodeNext.item(i).getNodeType() == Node.ELEMENT_NODE) {
                if(nodeNext.item(i).getNodeName().equals("Feature")) {//Если Feature - добавляем в Лист features
                    features.add(nodeNext.item(i));
                }
            }

        }
    }

    public String getClassName() {
        return className;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getGroup() {
        return group;
    }

    public String getPartNumber() {
        return partNumber;
    }

    public ArrayList<Node> getFeatures() {
        return features;
    }
}
