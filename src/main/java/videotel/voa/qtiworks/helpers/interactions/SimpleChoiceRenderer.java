package videotel.voa.qtiworks.helpers.interactions;

import uk.ac.ed.ph.jqtiplus.node.content.basic.TextRun;
import uk.ac.ed.ph.jqtiplus.node.item.interaction.choice.SimpleChoice;

public class SimpleChoiceRenderer extends InteractionAbstractRenderer {


    @Override
    public void setQuestion(String question) {
        this.question = question;
    }

    @Override
    public void addChoice(String choice) {
        this.choices.add(choice);
    }

    @Override
    public void render() {
        System.out.println("Question is : " + this.question);
        System.out.println("Choices: ");
        for (String choice : this.choices) {
            System.out.println("\t" + choice);
        }
    }
}
