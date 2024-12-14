import failed_classes.FailedBoard;
import failed_classes.FailedComp;
import model_classes.Board;
import model_classes.Comp;
import model_classes.Pin;
import org.w3c.dom.Node;
import parammetr_classes.Feature;
import parammetr_classes.ValueComp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class ResultReport {
    private String barcode; // DateModel Barcode
    private String testProgName;// DataModel Name
    private HashMap<String, String> timeTest;// DataModel Inspection
    public HashMap<String, String> compNameTestDict;
    public ArrayList<FailedBoard> boardFailedList;
    private int testBoardCount;

    public ResultReport(String barcode, String testProgName) {
        this.barcode = barcode;
        this.testProgName = testProgName;
        this.timeTest = new HashMap<>();
        this.boardFailedList = new ArrayList<>();
        this.compNameTestDict = new HashMap<>();
        this.testBoardCount = 0;
    }

    public String getBarcode() {
        return barcode;

    }

    public String getTestProgName() {
        return testProgName;
    }

    public HashMap<String, String> getTimeTest() {
        return timeTest;
    }

    public ArrayList<FailedBoard> getBoardFailedList() {
        return boardFailedList;
    }

    public void setTimeTest(String key, String value) {
        timeTest.put(key, value);
    }

    public void setBoardFailedList(FailedBoard board) {
        boardFailedList.add(board);
    }

    public void setCompNameTestDict(String nameComp, String partNumperComp) {
        compNameTestDict.put(nameComp, partNumperComp);
    }

    public HashMap<String, String> getCompNameTestDict() {
        return compNameTestDict;
    }

    public int getTestBoardCount() {
        return testBoardCount;
    }

    public void setTestBoardCount(int testBoardCount) {
        this.testBoardCount = testBoardCount;
    }

    public void printResultReport() {
        System.out.println("* ===========  Result Report  ========== *");
        System.out.printf("ID Test program result: %s, Program Name: %s.\n", this.getBarcode(), this.getTestProgName());
        for (Map.Entry<String, String> entry : this.getTimeTest().entrySet()) {
            System.out.println(entry.getKey() + " = " + entry.getValue());
        }
        System.out.println("Test Board Qty: " + this.getTestBoardCount());
        System.out.println("Test Component List (Part ID/ SAP Number)");
        for (Map.Entry<String, String> entry : this.getCompNameTestDict().entrySet()) {
            System.out.print(entry.getKey() + ": " + entry.getValue() + "; ");


        }
        System.out.println("");
        System.out.printf("Tested All %d components\n", this.getCompNameTestDict().size());
        System.out.println("* ==========  Failed Result ========== *");
        System.out.println("Failed Boards Qty: " + this.getBoardFailedList().size());
        System.out.println("* ==========  Name of Failed Component ========== *");
        for (Board board : this.getBoardFailedList()) {
            for (Node elem : board.getComps()) {
                FailedComp failedComp = new FailedComp(elem);
                System.out.printf("Name %s; SAP number %s\n", failedComp.getName(), failedComp.getPartNumber());
                if (failedComp.getFeatures().size() != 0) {
                    System.out.println("Failed Feature of Component: Name/ Value/ Unit/ Method");
                    for (Node nodeFeature : failedComp.getFeatures()) {
                        Feature feature = new Feature(nodeFeature);
                        if (feature.getStatus().getOverall("Is Failed")) {
                            for (Node valueNode : feature.getValues()) {
                                ValueComp valueComp = new ValueComp(valueNode);
                                System.out.println(valueComp.getName() + "/"
                                        + valueComp.getValue() + "/"
                                        + valueComp.getUnit() + "/"
                                        + valueComp.getThreshold());
                            }
                        }
                    }
                }
                if (failedComp.getPins().size() != 0) {
                    System.out.println("Failed pin Name");
                    System.out.println("* ==================================================== *");
                    for (Node nodePin : failedComp.getPins()) {
                        Pin pin = new Pin(nodePin);
                        System.out.println(pin.getName());
                        for (Node nodeFeatPin : pin.getFeatures()) {
                            Feature featurePin = new Feature(nodeFeatPin);
                            if (featurePin.getStatus().getOverall("IsFailed")) {
                                System.out.println("Name of Failed Test: " + featurePin.getName());
                                System.out.println("Failed Feature of Pin: Name/ Value/ Unit/ Method");
                                for (Node valueNodePin : featurePin.getValues()) {
                                    ValueComp valuePin = new ValueComp(valueNodePin);
                                    System.out.println(valuePin.getName() + "/"
                                            + valuePin.getValue() + "/"
                                            + valuePin.getUnit() + "/"
                                            + valuePin.getThreshold());
                                }
                            }
                            System.out.println("* ==================================================== *");
                        }
                    }

                }

            }
        }
    }
}


