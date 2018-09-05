package videotel.voa.qtiworks;


import uk.ac.ed.ph.jqtiplus.internal.util.ObjectUtilities;
import uk.ac.ed.ph.jqtiplus.state.TestPlanNode;
import videotel.voa.qtiworks.helpers.AssessmentTestWrapper;

import java.util.Date;

public class RenderAssessmentTestLinear {

    public static void main(String[] args) {
        Date testEntryTimestamp = new Date();
        Date testPartEntryTimestamp = ObjectUtilities.addToTime(testEntryTimestamp, 1000L);
        Date operationTimestamp = ObjectUtilities.addToTime(testPartEntryTimestamp, 1000L);

        AssessmentTestWrapper test = new AssessmentTestWrapper("com/videotel/samples/simple-linear-individual.xml");

        System.out.println("Entering the test");
        test.testSessionController.enterTest(testEntryTimestamp);
        test.testSessionController.enterNextAvailableTestPart(testPartEntryTimestamp);
        TestPlanNode currentTestPlanNode = test.testSessionController.advanceItemLinear(operationTimestamp);

        String path = currentTestPlanNode.getItemSystemId().toString();
        System.out.println(path);

        currentTestPlanNode = test.testSessionController.advanceItemLinear(operationTimestamp);
        path = currentTestPlanNode.getItemSystemId().toString();
        System.out.println(path);

//        System.out.println("Rendering the first question");
//        test.getItemByIdentifier(1).renderItem();
//
//        System.out.println("Rendering the second question");
//        test.getItemByIdentifier(2).renderItem();
//
//        System.out.println("Rendering the third question");
//        test.getItemByIdentifier(3).renderItem();
    }
}
