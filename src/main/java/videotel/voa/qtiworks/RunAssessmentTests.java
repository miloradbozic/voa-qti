package videotel.voa.qtiworks;

import uk.ac.ed.ph.jqtiplus.internal.util.ObjectUtilities;
import uk.ac.ed.ph.jqtiplus.state.ItemSessionState;
import uk.ac.ed.ph.jqtiplus.state.TestPlanNode;
import videotel.voa.qtiworks.tests.AssessmentTestWrapper;

import javax.sound.midi.SysexMessage;
import java.util.Date;

public class RunAssessmentTests {


    public static void main(String[] args) {
        runNonLinearIndividual();
        runNonLinearIndividual();
    }


    private static void runNonLinearIndividual() {

        Date testEntryTimestamp = new Date();
        Date testPartEntryTimestamp = ObjectUtilities.addToTime(testEntryTimestamp, 1000L);
        Date operationTimestamp = ObjectUtilities.addToTime(testPartEntryTimestamp, 1000L);

        // In this submission mode, candidate responses are processed immediately. Some of the questions included here have question-level feedback, and this will be shown immediately if it exists.
        AssessmentTestWrapper test = new AssessmentTestWrapper("com/videotel/samples/simple-nonlinear-individual.xml");

        test.testSessionController.enterTest(testEntryTimestamp);
        test.testSessionController.enterNextAvailableTestPart(testPartEntryTimestamp);
        /* Answer item 1 correctly */
        test.selectItem(1);
        test.handleChoiceResponse(operationTimestamp, "ChoiceA");
        /* Answer item 2 incorrectly */
        test.selectItem(2);
        test.handleChoiceResponse(operationTimestamp, "ChoiceB");

        //get score item1
        String scoreItem1 = test.getItemScore(1); //i1
        System.out.println("First item score: " + scoreItem1);

        //score item2
        String scoreItem2 = test.getItemScore(2); //i2
        System.out.println("Second item score: " + scoreItem2);

        //check if test scoring is performed
        boolean processed = test.isOutcomeProcessed();
        System.out.println("Test processed: " + processed);

        //score test
        String testScore = test.getScore();
        System.out.println("Test score: " + testScore);
    }

}
