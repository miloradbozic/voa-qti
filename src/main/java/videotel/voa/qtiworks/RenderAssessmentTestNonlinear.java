package videotel.voa.qtiworks;


import videotel.voa.qtiworks.helpers.AssessmentItemWrapper;
import videotel.voa.qtiworks.helpers.AssessmentTestWrapper;

public class RenderAssessmentTestNonlinear {

    public static void main(String[] args) {
        // In this submission mode, candidate responses are processed immediately. Some of the questions included here have question-level feedback, and this will be shown immediately if it exists.
        AssessmentTestWrapper test = new AssessmentTestWrapper("com/videotel/samples/simple-nonlinear-individual.xml");

        System.out.println("Entering the test");

        System.out.println("Rendering the first question");
        test.getItemByIdentifier(1).renderItem();

        System.out.println("Rendering the second question");
        test.getItemByIdentifier(2).renderItem();

        System.out.println("Rendering the third question");
        test.getItemByIdentifier(3).renderItem();
    }
}
