package model_classes;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import parammetr_classes.Geometry;
import parammetr_classes.Status;

import java.util.ArrayList;

public class Panel extends MainObj{
    protected Geometry geometry;
    protected Status status;
    private final Node nodeMain;
    protected ArrayList<Node> fidMarks;
    protected ArrayList<Node> boards;

    public Panel(Node nodeMain) {
        super(nodeMain);
        this.nodeMain = nodeMain;
        this.fidMarks = new ArrayList<>();
        this.boards = new ArrayList<>();

        NodeList startNode = this.nodeMain.getChildNodes();

        for (int i = 0; i < startNode.getLength(); i++) {
            if (startNode.item(i).getNodeType() == Node.ELEMENT_NODE) {
                switch (startNode.item(i).getNodeName()) {
                    case "Geometry" -> setGeometry(startNode.item(i));// Записываем Геометрию
                    case "Status" -> setStatus(startNode.item(i));// Записываем Статус
                    case "Object" -> switchObject(startNode.item(i)); // Формируем list с Feature
                }
            }
        }


    }
    private void switchObject(Node inputObject) {
        NamedNodeMap attr = inputObject.getAttributes();// Получаем его атрибуты
        for (int j = 0; j < attr.getLength(); j++) {
            if (attr.item(j).getNodeName().equals("Group") && attr.item(j).getNodeValue().equals("Fiducial")) {
                fidMarks.add(inputObject);// Если имя Ноде Т1/Т2/Т3 - кладем в list fidMarksArray
            }
            if (attr.item(j).getNodeName().equals("Class") && attr.item(j).getNodeValue().equals("Board")) {
                boards.add(inputObject); // Если mainClass.Board -  кладем в list boards
            }
        }
    }

    public ArrayList<Node> getFidMarks() {
        return fidMarks;
    }

    public ArrayList<Node> getBoards() {
        return boards;
    }
    public boolean checkAllFidMarks() {
        boolean flagResult = false;
        for(Node fidMark: fidMarks){
            FidMark fid = new FidMark(fidMark);
            flagResult = flagResult || fid.getStatus().getOverall("IsFailed");
        }
        return flagResult;
    }

}
