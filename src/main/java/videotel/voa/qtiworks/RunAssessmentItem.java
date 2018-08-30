package videotel.voa.qtiworks;

import uk.ac.ed.ph.jqtiplus.SimpleJqtiFacade;
import uk.ac.ed.ph.jqtiplus.internal.util.DumpMode;
import uk.ac.ed.ph.jqtiplus.internal.util.ObjectDumper;
import uk.ac.ed.ph.jqtiplus.internal.util.ObjectUtilities;
import uk.ac.ed.ph.jqtiplus.resolution.ResolvedAssessmentItem;
import uk.ac.ed.ph.jqtiplus.running.ItemProcessingInitializer;
import uk.ac.ed.ph.jqtiplus.running.ItemSessionController;
import uk.ac.ed.ph.jqtiplus.running.ItemSessionControllerSettings;
import uk.ac.ed.ph.jqtiplus.state.ItemProcessingMap;
import uk.ac.ed.ph.jqtiplus.state.ItemSessionState;
import uk.ac.ed.ph.jqtiplus.types.Identifier;
import uk.ac.ed.ph.jqtiplus.types.ResponseData;
import uk.ac.ed.ph.jqtiplus.types.StringResponseData;
import uk.ac.ed.ph.jqtiplus.xmlutils.locators.ClassPathResourceLocator;
import uk.ac.ed.ph.jqtiplus.xmlutils.locators.ResourceLocator;
import videotel.voa.qtiworks.tests.AssessmentItemWrapper;

import java.net.URI;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

// @todo refactor

/**
 * Runs an assessment item, selects correct answer and processes the result
 */
public class RunAssessmentItem {

    public static void main(final String[] args) {
        AssessmentItemWrapper itemWrapper = new AssessmentItemWrapper("com/videotel/samples/choice.xml");
        System.out.println("Rendering item:");
        itemWrapper.renderItem();
        itemWrapper.bindAndCommitResponses("ChoiceA");
        itemWrapper.processResponseAndCloseItem();
        System.out.println(ObjectDumper.dumpObject(itemWrapper.getState(), DumpMode.DEEP));
    }
}
