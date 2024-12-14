package parammetr_classes;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.ArrayList;

public class Feature {
    private final Node nodeMain;
    private String name; // Имя теста-проверки "mainClass.Pin.Meniscus" для пина или "Reference" для корпуса чипа или резистора
    private String methodName; // Имя метода - чем мы проверяем, точнее описание инструмета проверки
    private ArrayList<Node> values; // Список с результатами тестов (экземпляры класса Value)
    private final Status status; // Окончательный результат проверки (сравнение Тест - Эталон) всех тестов = экземпляр класса mainClass.Status

    public Feature(Node nodeMain) {
        this.nodeMain = nodeMain;
        this.status = new Status();
        this.values = new ArrayList<>();

        NodeList startNode = this.nodeMain.getChildNodes();
        //System.out.println("Object complete " + startNode.getLength());

        for (int i = 0; i < startNode.getLength(); i++) {
            if (startNode.item(i).getNodeType() == Node.ELEMENT_NODE) {
                switch (startNode.item(i).getNodeName()) {
                    case "Status" -> setStatus(startNode.item(i));// Записываем Статус
                    case "Values" -> addValues(startNode.item(i));// Формируем list fidMarksArray или list boards, в зависимости от имени Ноде
                }
            }

        }
        NamedNodeMap attr = this.nodeMain.getAttributes(); // Получаем его атрибуты
        for (int j = 0; j < attr.getLength(); j++) {
            switch (attr.item(j).getNodeName()) {
                case "Name" -> this.name = attr.item(j).getNodeValue();
                case "MethodName" -> this.methodName= attr.item(j).getNodeValue();
            }
        }
    }




    public String getName() {
        return name;
    }

    public String getMethodName() {
        return methodName;
    }

    public ArrayList<Node> getValues() {
        return values;
    }

    public Status getStatus() {
        return status;
    }

    protected void setStatus (Node inputStatus) { // Передаем Ноде (startNode.item(i) с именем Status) для обработки Статуса
        NodeList nodeNext = inputStatus.getChildNodes(); // Получаем список детей входящего Нода
        for (int i = 0; i < nodeNext.getLength(); i++) {// Перебираем список
            if (nodeNext.item(i).getNodeType() == Node.ELEMENT_NODE) {
                switch (nodeNext.item(i).getNodeName()) {
                    case "Overall" -> setDictOverall(nodeNext.item(i));// Если имя под.ребенка Overall - пишем в Status словарь Overall
                    case "Inspection" -> setDictInspection(nodeNext.item(i));// то-же самое
                    case "Classification" -> setDictClassification(nodeNext.item(i));
                    case "Repair" -> setDictRepair(nodeNext.item(i));
                }

            }
        }
    }
    protected void setDictOverall (Node nodeDict) {// передаем под.ребенка с именем Overall для записи словаря Status.Overall
        NamedNodeMap attr = nodeDict.getAttributes();
        for (int j = 0; j < attr.getLength(); j++) {
            status.putOverall(attr.item(j).getNodeName(), Boolean.parseBoolean(attr.item(j).getNodeValue()));
        }
    }

    protected void setDictInspection (Node nodeDict) {// то-же самое
        NamedNodeMap attr = nodeDict.getAttributes();
        for (int j = 0; j < attr.getLength(); j++) {
            status.putInspection(attr.item(j).getNodeName(), Boolean.parseBoolean(attr.item(j).getNodeValue()));
        }
    }
    protected void setDictClassification (Node nodeDict) {
        NamedNodeMap attr = nodeDict.getAttributes();
        for (int j = 0; j < attr.getLength(); j++) {
            status.putClassification(attr.item(j).getNodeName(), Boolean.parseBoolean(attr.item(j).getNodeValue()));
        }
    }
    protected void setDictRepair (Node nodeDict) {
        NamedNodeMap attr = nodeDict.getAttributes();
        for (int j = 0; j < attr.getLength(); j++) {
            status.putRepair(attr.item(j).getNodeName(), Boolean.parseBoolean(attr.item(j).getNodeValue()));
        }
    }
    private void addValues(Node nodeValue) {
        NodeList startNode = nodeValue.getChildNodes();
        for (int i = 0; i < startNode.getLength(); i++) {
            if (startNode.item(i).getNodeType() == Node.ELEMENT_NODE) {
                if(startNode.item(i).getNodeName().equals("Value")) {
                    values.add(startNode.item(i));
                }

            }
        }


    }



}
