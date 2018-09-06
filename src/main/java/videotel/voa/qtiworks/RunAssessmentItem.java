package videotel.voa.qtiworks;

import uk.ac.ed.ph.jqtiplus.internal.util.DumpMode;
import uk.ac.ed.ph.jqtiplus.internal.util.ObjectDumper;
import videotel.voa.qtiworks.helpers.AssessmentItemWrapper;

// @todo refactor

/**
 * Runs an assessment item, selects correct answer and processes the result
 */
public class RunAssessmentItem {

    public static void main(final String[] args) {
        AssessmentItemWrapper itemWrapper = new AssessmentItemWrapper("classpath:/com/videotel/samples/choice.xml");
        itemWrapper.bindAndCommitResponses("ChoiceA");
        itemWrapper.processResponseAndCloseItem();
        System.out.println(ObjectDumper.dumpObject(itemWrapper.getState(), DumpMode.DEEP));
    }
}
