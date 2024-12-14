package failed_classes;

import model_classes.Board;
import org.w3c.dom.Node;

import java.util.ArrayList;

public class FailedBoard extends Board {
    public FailedBoard(Node nodeMain) {
        super(nodeMain);

    }
    protected void addComp(Node nodeComp) {
        FailedComp failedComp = new FailedComp(nodeComp);
        if(failedComp.getStatus().getOverall("IsFailed")){
            comps.add(nodeComp);
        }
    }
}
