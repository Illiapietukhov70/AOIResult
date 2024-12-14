package model_classes;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.ArrayList;

public class Comp extends Pin{
    private final Node nodePin;
    protected ArrayList<Node> pins;

    public Comp(Node nodePin) {
        super(nodePin);
        this.nodePin = nodePin;
        this.pins = new ArrayList<>();

        NodeList startNode = this.nodePin.getChildNodes();
        for (int i = 0; i < startNode.getLength(); i++) {
            if (startNode.item(i).getNodeType() == Node.ELEMENT_NODE) {
                if (startNode.item(i).getNodeName().equals("Object")) {
                    addPin(startNode.item(i)); // Это только для потомка Comp
                }
            }
        }

    }
    protected void addPin(Node nodePin) {
        pins.add(nodePin);
    }
    public ArrayList<Node> getPins() {
        return this.pins;
    }
}
