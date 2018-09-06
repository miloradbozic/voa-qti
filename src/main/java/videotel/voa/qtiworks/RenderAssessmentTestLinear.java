package videotel.voa.qtiworks;


import uk.ac.ed.ph.jqtiplus.internal.util.ObjectUtilities;
import uk.ac.ed.ph.jqtiplus.state.TestPlanNode;
import uk.ac.ed.ph.jqtiplus.state.TestPlanNodeKey;
import videotel.voa.qtiworks.helpers.AssessmentTestWrapper;

import java.util.Date;

public class RenderAssessmentTestLinear {

    public static void main(String[] args) {
        Date testEntryTimestamp = new Date();
        Date testPartEntryTimestamp = ObjectUtilities.addToTime(testEntryTimestamp, 1000L);
        Date operationTimestamp = ObjectUtilities.addToTime(testPartEntryTimestamp, 1000L);

        AssessmentTestWrapper test = new AssessmentTestWrapper("com/videotel/samples/simple-linear-individual.xml");

        System.out.println("Entering the test");
        test.enterTest();

        System.out.println("Rendering the first item:");
        test.getCurrentItem().renderItem();
        System.out.println("-------------------");

        System.out.println("\nMoving to the next item...");
        test.testSessionController.advanceItemLinear(operationTimestamp);

        System.out.println("\r\nRendering the next item:");
        test.getCurrentItem().renderItem();
        System.out.println("-------------------");

        System.out.println("\nMoving to the next item...");
        test.testSessionController.advanceItemLinear(operationTimestamp);

        System.out.println("\r\nRendering the next item:");
        test.getCurrentItem().renderItem();

    }
}
