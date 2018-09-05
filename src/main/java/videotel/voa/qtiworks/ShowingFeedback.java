package videotel.voa.qtiworks;

import uk.ac.ed.ph.jqtiplus.internal.util.DumpMode;
import uk.ac.ed.ph.jqtiplus.internal.util.ObjectDumper;
import videotel.voa.qtiworks.helpers.AssessmentItemWrapper;

// @todo
public class ShowingFeedback {

    public static void main(String[] args) {
        AssessmentItemWrapper itemWrapper = new AssessmentItemWrapper("com/videotel/samples/feedback.xml");
        itemWrapper.bindAndCommitResponses("MGH001C");
        itemWrapper.processResponseAndCloseItem();
        System.out.println(ObjectDumper.dumpObject(itemWrapper.getState(), DumpMode.DEEP));
    }
}
