package videotel.voa.qtiworks;

import uk.ac.ed.ph.jqtiplus.JqtiExtensionManager;
import uk.ac.ed.ph.jqtiplus.internal.util.ObjectDumper;
import uk.ac.ed.ph.jqtiplus.node.item.AssessmentItem;
import uk.ac.ed.ph.jqtiplus.node.outcome.declaration.OutcomeDeclaration;
import uk.ac.ed.ph.jqtiplus.node.shared.FieldValue;
import uk.ac.ed.ph.jqtiplus.node.shared.declaration.DefaultValue;
import uk.ac.ed.ph.jqtiplus.node.test.*;
import uk.ac.ed.ph.jqtiplus.reading.QtiObjectReader;
import uk.ac.ed.ph.jqtiplus.reading.QtiXmlReader;
import uk.ac.ed.ph.jqtiplus.resolution.AssessmentObjectResolver;
import uk.ac.ed.ph.jqtiplus.resolution.ResolvedAssessmentItem;
import uk.ac.ed.ph.jqtiplus.serialization.QtiSerializer;
import uk.ac.ed.ph.jqtiplus.types.Identifier;
import uk.ac.ed.ph.jqtiplus.validation.AssessmentObjectValidator;
import uk.ac.ed.ph.jqtiplus.validation.ItemValidationResult;
import uk.ac.ed.ph.jqtiplus.value.BaseType;
import uk.ac.ed.ph.jqtiplus.value.Cardinality;
import uk.ac.ed.ph.jqtiplus.value.FloatValue;
import uk.ac.ed.ph.jqtiplus.xmlutils.locators.NullResourceLocator;

import java.net.URI;

public class CreateNewAssessment {
    public static void main(final String[] args) throws Exception {
        /* Create empty Assessment and add necessary properties to make it valid */
        final AssessmentTest assessmentTest = new AssessmentTest();
        assessmentTest.setIdentifier("MYTest");
        assessmentTest.setTitle("Videotel Test Example");

        /* Declare a SCORE outcome variable */
        final OutcomeDeclaration score = new OutcomeDeclaration(assessmentTest);
        score.setIdentifier(Identifier.assumedLegal("OP_DONE"));
        score.setCardinality(Cardinality.SINGLE);
        score.setBaseType(BaseType.FLOAT);
        final DefaultValue defaultValue = new DefaultValue(score);
        defaultValue.getFieldValues().add(new FieldValue(defaultValue, new FloatValue(0.0)));
        score.setDefaultValue(defaultValue);
        assessmentTest.getOutcomeDeclarations().add(score);


        /* Declare test part variable */
        final TestPart testPart = new TestPart(assessmentTest);
        testPart.setIdentifier(Identifier.assumedLegal("p"));
        testPart.setNavigationMode(NavigationMode.NONLINEAR);
        testPart.setSubmissionMode(SubmissionMode.INDIVIDUAL);
        final AssessmentSection assessmentSection = new AssessmentSection(testPart);
        assessmentSection.setVisible(true);
        assessmentSection.setIdentifier(Identifier.assumedLegal("s"));
        assessmentSection.setIdentifier(Identifier.assumedLegal("Section"));
        final AssessmentItemRef itemRef = new AssessmentItemRef(assessmentSection);
        itemRef.setHref(URI.create("choice.xml"));
        itemRef.setIdentifier(Identifier.assumedLegal("i1"));
        assessmentSection.getSectionParts().add(itemRef);
        testPart.getAssessmentSections().add(assessmentSection);
        assessmentTest.getTestParts().add(testPart);

        /* Validate */
        // @todo
        //System.out.println("Validation result:");
        //ObjectDumper.dumpObjectToStdout(validationResult);

        /* Finally serialize the assessmentItem to XML and print it out */
        final JqtiExtensionManager jqtiExtensionManager = new JqtiExtensionManager();
        final QtiSerializer qtiSerializer = new QtiSerializer(jqtiExtensionManager);
        System.out.println("Serialized XML:");
        System.out.println(qtiSerializer.serializeJqtiObject(assessmentTest));
    }

}
