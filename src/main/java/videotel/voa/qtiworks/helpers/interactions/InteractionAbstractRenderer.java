package videotel.voa.qtiworks.helpers.interactions;

import java.util.ArrayList;
import java.util.List;

abstract public class InteractionAbstractRenderer implements InteractionRenderer{
    protected String question;
    protected List<String> choices = new ArrayList<>();
}
