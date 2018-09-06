package videotel.voa.qtiworks;


import videotel.voa.qtiworks.helpers.AssessmentItemWrapper;

public class RenderAssessmentItem {

    public static void main(String[] args) {
        AssessmentItemWrapper itemWrapper = new AssessmentItemWrapper("classpath:/com/videotel/samples/choice.xml");
        System.out.println("Rendering item:");
        itemWrapper.renderItem();
    }
}
