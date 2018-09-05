package videotel.voa.qtiworks.helpers;

import uk.ac.ed.ph.jqtiplus.SimpleJqtiFacade;
import uk.ac.ed.ph.jqtiplus.group.NodeGroup;
import uk.ac.ed.ph.jqtiplus.group.NodeGroupList;
import uk.ac.ed.ph.jqtiplus.internal.util.DumpMode;
import uk.ac.ed.ph.jqtiplus.internal.util.ObjectDumper;
import uk.ac.ed.ph.jqtiplus.internal.util.ObjectUtilities;
import uk.ac.ed.ph.jqtiplus.node.content.basic.TextRun;
import uk.ac.ed.ph.jqtiplus.node.item.interaction.Interaction;
import uk.ac.ed.ph.jqtiplus.node.item.interaction.Prompt;
import uk.ac.ed.ph.jqtiplus.node.item.interaction.choice.SimpleChoice;
import uk.ac.ed.ph.jqtiplus.node.item.response.declaration.ResponseDeclaration;
import uk.ac.ed.ph.jqtiplus.resolution.ResolvedAssessmentItem;
import uk.ac.ed.ph.jqtiplus.running.ItemProcessingInitializer;
import uk.ac.ed.ph.jqtiplus.running.ItemSessionController;
import uk.ac.ed.ph.jqtiplus.running.ItemSessionControllerSettings;
import uk.ac.ed.ph.jqtiplus.running.TestSessionController;
import uk.ac.ed.ph.jqtiplus.state.*;
import uk.ac.ed.ph.jqtiplus.types.Identifier;
import uk.ac.ed.ph.jqtiplus.types.ResponseData;
import uk.ac.ed.ph.jqtiplus.types.StringResponseData;
import uk.ac.ed.ph.jqtiplus.xmlutils.locators.ClassPathResourceLocator;
import uk.ac.ed.ph.jqtiplus.xmlutils.locators.ResourceLocator;
import videotel.voa.qtiworks.helpers.TestHelper;
import videotel.voa.qtiworks.helpers.interactions.SimpleChoiceRenderer;

import java.net.URI;
import java.util.*;

public class AssessmentItemWrapper {
    public ItemSessionController itemSessionController;
    protected ItemSessionState itemSessionState;
    protected ItemProcessingMap itemProcessingMap;
    String filePath = null;

    public AssessmentItemWrapper(String filePath) {
        this.filePath = filePath;
        this.initItemSessionController();
    }

    public void initItemSessionController() {
        final ResourceLocator inputResourceLocator = new ClassPathResourceLocator();
        final URI inputUri = URI.create("classpath:/" + this.filePath);

        final SimpleJqtiFacade simpleJqtiFacade = new SimpleJqtiFacade();
        final ResolvedAssessmentItem resolvedAssessmentItem = simpleJqtiFacade.loadAndResolveAssessmentItem(inputResourceLocator, inputUri);

        System.out.println(resolvedAssessmentItem);
        this.itemProcessingMap = new ItemProcessingInitializer(resolvedAssessmentItem, false).initialize();
        //System.out.println("Run map is: " + ObjectDumper.dumpObject(this.itemProcessingMap, DumpMode.DEEP));
        this.itemSessionState = new ItemSessionState();

        final ItemSessionControllerSettings itemSessionControllerSettings = new ItemSessionControllerSettings();
        this.itemSessionController = simpleJqtiFacade.createItemSessionController(itemSessionControllerSettings, this.itemProcessingMap, this.itemSessionState);

        final Date timestamp1 = new Date();
        this.itemSessionController.initialize(timestamp1);
        this.itemSessionController.performTemplateProcessing(timestamp1);
        //System.out.println("State after init: " + ObjectDumper.dumpObject(this.itemSessionState, DumpMode.DEEP));

        this.itemSessionController.enterItem(new Date());



        //System.out.println("State after entry: " + ObjectDumper.dumpObject(this.itemSessionState, DumpMode.DEEP));
    }

    public void bindAndCommitResponses(String responseChoice) {
        final Map<Identifier, ResponseData> responseMap = new HashMap<Identifier, ResponseData>();
        responseMap.put(Identifier.parseString("RESPONSE"), new StringResponseData(responseChoice));
        this.itemSessionController.bindResponses(new Date(), responseMap);
        System.out.println("Unbound responses: " + this.itemSessionState.getUnboundResponseIdentifiers());
        System.out.println("Invalid responses:" + this.itemSessionState.getInvalidResponseIdentifiers());
        this.itemSessionController.commitResponses(new Date());
    }

    public void processResponseAndCloseItem() {
        this.itemSessionController.performResponseProcessing(new Date());
        this.itemSessionController.endItem(new Date());
        this.itemSessionController.exitItem(new Date());
    }

    public Object getState() {
        return this.itemSessionState;
    }

    /** @todo:
     * we need to create it for each interaction type
     * **/
    public void renderItem() {
        SimpleChoiceRenderer renderer = new SimpleChoiceRenderer();
        List<Interaction> interactions = this.itemProcessingMap.getInteractions();
        TextRun tr2= (TextRun) interactions.get(0).getNodeGroups().get(0).getChildren().get(0).getNodeGroups().get(0).getChildren().get(0);
        String question = tr2.getTextContent();
        renderer.setQuestion(question);

        List<String> choices = new ArrayList<>();
        List<SimpleChoice> children = (List<SimpleChoice>)interactions.get(0).getNodeGroups().get(1).getChildren();

        for (SimpleChoice child : children) {
            TextRun tr = (TextRun)child.getNodeGroups().get(0).getChildren().get(0);
            String choice = tr.getTextContent();
            renderer.addChoice(choice);
        }

        renderer.render();
    }

}
