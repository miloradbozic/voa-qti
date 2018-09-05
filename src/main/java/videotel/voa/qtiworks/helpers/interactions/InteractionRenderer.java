package videotel.voa.qtiworks.helpers.interactions;

public interface InteractionRenderer {
    void setQuestion(String question);
    void addChoice(String choice);
    void render();
}
