package videotel.voa.qtiworks.tests;

import uk.ac.ed.ph.jqtiplus.internal.util.ObjectUtilities;
import uk.ac.ed.ph.jqtiplus.node.test.AssessmentItemRef;
import uk.ac.ed.ph.jqtiplus.node.test.AssessmentSection;
import uk.ac.ed.ph.jqtiplus.node.test.TestPart;
import uk.ac.ed.ph.jqtiplus.running.TestSessionController;
import uk.ac.ed.ph.jqtiplus.state.*;
import uk.ac.ed.ph.jqtiplus.types.Identifier;
import uk.ac.ed.ph.jqtiplus.types.ResponseData;
import uk.ac.ed.ph.jqtiplus.types.StringResponseData;
import uk.ac.ed.ph.jqtiplus.value.BooleanValue;
import videotel.voa.qtiworks.helpers.TestHelper;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/*
@missing functions:
    assertChoiceItemResponseProcessingRun(item1SessionState);
    assertChoiceItemResponseProcessingNotRun(item2SessionState);
    assertOutcomeProcessingRun();
 */
public class AssessmentTestWrapper {
    public final Identifier CHOICE_ITEM_RESPONSE = Identifier.assumedLegal("RESPONSE");
    public final Identifier CHOICE_ITEM_SCORE = Identifier.assumedLegal("SCORE");
    public final Identifier TEST_SCORE = Identifier.assumedLegal("TEST_SCORE");
    public static final Identifier TEST_OP_DONE = Identifier.assumedLegal("OP_DONE");

    public TestSessionController testSessionController;
    protected TestSessionState testSessionState;
    protected TestPlan testPlan;
    protected TestProcessingMap testProcessingMap;
    protected Long testPartEntryDelta = 1000L;
    protected Map<String, TestPlanNode> testPlanNodesByIdentifierStringMap;
    String filePath = null;
    protected String getTestFilePath() {
        return this.filePath;
    }

    public AssessmentTestWrapper(String filePath) {
        this.filePath = filePath;
        this.initTestSessionController();
    }

    public void initTestSessionController() {
        Date testEntryTimestamp = new Date();
        this.testSessionController = TestHelper.loadUnitTestAssessmentTestForControl(this.getTestFilePath(), true);
        this.testSessionController.initialize(testEntryTimestamp);
        this.testSessionState = this.testSessionController.getTestSessionState();
        this.testPlan = this.testSessionState.getTestPlan();
        this.testProcessingMap = testSessionController.getTestProcessingMap();

        // @todo: we want to be able to access nodes by identifiers (i1, i2 etc)
//        testPlanNodesByIdentifierStringMap = new HashMap<String, TestPlanNode>();
//        for (final String testNodeIdentifierString : testNodes()) {
//            final TestPlanNode testPlanNode = TestHelper.getSingleTestPlanNode(testPlan, testNodeIdentifierString);
//            testPlanNodesByIdentifierStringMap.put(testNodeIdentifierString, testPlanNode);
//        }
    }

    public void handleChoiceResponse(final Date timestamp, final String choiceIdentifier) {
        final Map<Identifier, ResponseData> responseMap = new HashMap<Identifier, ResponseData>();
        responseMap.put(CHOICE_ITEM_RESPONSE, new StringResponseData(choiceIdentifier));
        this.testSessionController.handleResponsesToCurrentItem(timestamp, responseMap);
    }

    //@todo refactor - we want to be able to get item by identifier, not like this (testSessionController.selectItemNonlinear(operationTimestamp, getTestNodeKey("i1")))
    public void selectItem(int identifier) {
        int id = identifier - 1;
        final TestPlanNode itemRefNode = testPlan.getTestPartNodes().get(0).searchDescendants(TestPlanNode.TestNodeType.ASSESSMENT_ITEM_REF).get(id);
//        AssessmentSection s = new AssessmentSection(null);
//        AssessmentItemRef itre = new AssessmentItemRef(s);

        this.testSessionController.selectItemNonlinear(new Date(), itemRefNode.getKey());
        TestPart tp = this.testSessionController.getCurrentTestPart();
    }

    // @todo: we want Double type instead of String probably
    public String getItemScore(int identifier) {
        //@todo: we want map of identifiers instead of accessing like this
        int id = identifier - 1;
        //this.testSessionController.selectItemNonlinear(new Date(), itemRefNode.getKey());
        ItemSessionState itemSessionState = this.getItemSessionState(id);
        return itemSessionState.getOutcomeValue(CHOICE_ITEM_SCORE).toString();
    }

    public boolean isOutcomeProcessed() {
        return testSessionState.getOutcomeValue(TEST_OP_DONE).equals(BooleanValue.TRUE);
    }

    // @todo return Double type
    public String getScore() {
        return testSessionState.getOutcomeValue(TEST_SCORE).toString();
    }

    /*** functions important for rendering ***/
    public void getItem(int identifier) {
        int id = identifier - 1;
        TestPlanNode itemRefNode = this.getItemRefNode(id);
        AssessmentItemRef assessmentItemRef = (AssessmentItemRef) this.testProcessingMap.resolveAbstractPart(itemRefNode);
        System.out.println(assessmentItemRef.getHref());
    }

    /** Private methods ***/
    private TestPlanNode getItemRefNode(int id) {
        return testPlan.getTestPartNodes().get(0).searchDescendants(TestPlanNode.TestNodeType.ASSESSMENT_ITEM_REF).get(id);
    }

    private ItemSessionState getItemSessionState(int id) {
        TestPlanNode itemRefNode = this.getItemRefNode(id);
        return this.testSessionState.getItemSessionStates().get(itemRefNode.getKey());
    }
}
