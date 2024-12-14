import failed_classes.FailedBoard;
import failed_classes.FailedComp;
import model_classes.*;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import parammetr_classes.Feature;
import parammetr_classes.ValueComp;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.ArrayList;

public class MainTest {
    public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException {


        DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();

        //Создается объект Document — он является представлением всей информации внутри XML
        Document document = builder.parse("300216_b_p3_AOI_101908_03092024.xml");

        //Создается узел root путем получения первого дочернего узла. Это будет тег <VvExtDataExportXml>
        Node root = document.getFirstChild();

        NodeList rootChild = root.getChildNodes();
        Node firstNode = null;
        for (int i = 0; i < rootChild.getLength(); i++) {
            if (rootChild.item(i).getNodeType() == Node.ELEMENT_NODE) {
                firstNode = rootChild.item(i);//формирую Node "DataModel"
//                System.out.println(firstNode.getNodeName());
            }

        }
        DataModel dataModel = new DataModel(firstNode);
//        System.out.println(dataModel.getPanel().checkAllFidMarks());

        /* Делаем проверку, что все Марки сыграли (Тест распознал плату)
        * Проверяем Panel.Status.Overall.IsFailed - были ли ошибки
        * Если checkAllFidMars = False, и IsFailed = True -> генерим ResultReport
        */

        if(dataModel.getPanel().checkAllFidMarks() == false
                && dataModel.getPanel().getStatus().getOverall("IsFailed") == true) {
            ResultReport resultReport = new ResultReport(dataModel.getName(), dataModel.getBarcode());

            /* Заполняем Словарь timeTest с временными параметрами Теста, для этого
             *  Визываем DataModel.getInspection и копируем нужные атрибуты
             */

            String inStr = "InspectionStart",
                    inEnd = "InspectionEnd",
                    anStr = "AnalysisStart",
                    anEnd = "AnalysisEnd",
                    timeCycle = "CycleTime";
            resultReport.setTimeTest(inStr, dataModel.getInspectionArray(inStr));
            resultReport.setTimeTest(inEnd, dataModel.getInspectionArray(inEnd));
            resultReport.setTimeTest(anStr, dataModel.getInspectionArray(anStr));
            resultReport.setTimeTest(anEnd, dataModel.getInspectionArray(anEnd));
            resultReport.setTimeTest(timeCycle, dataModel.getInspectionArray(timeCycle));

            /* Генерим Board[0], то есть первую плату из Списка
            (т. к. все Компоненты одинаковы) и создаем Славарь compName:compPartNumber
            для всех Компонентов, которые были проверенны в данном Тесте
            Так же заполняем Список Failed Board, путем вызова массива DataModel.getPanel.getBoards
            и контроля Статуса каждой Платы -> board.getStatus("IsFailed"), если True ->
            Пишем failedBoard в Список ResultReport.boardFailedList
            */

            Board boardCompList = new Board(dataModel.getPanel().getBoards().get(0));
            for (Node comp : boardCompList.getComps()) {
                Comp compListElem = new Comp(comp);
                resultReport.setCompNameTestDict(compListElem.getName(), compListElem.getPartNumber());
                resultReport.setTestBoardCount(dataModel.getPanel().getBoards().size());
            }

            for (Node boardTest : dataModel.getPanel().getBoards()) {
                Board board = new Board(boardTest);
                if (board.getStatus().getOverall("IsFailed")) {
                    FailedBoard failedBoard = new FailedBoard(boardTest);
                    resultReport.setBoardFailedList(failedBoard);
                }
            }
            // Результат Теста -
            resultReport.printResultReport();

        }


    }
}
