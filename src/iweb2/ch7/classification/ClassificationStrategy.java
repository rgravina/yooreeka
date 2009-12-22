package iweb2.ch7.classification;

import iweb2.ch7.core.NewsStory;
import iweb2.ch7.core.NewsStoryGroup;

public interface ClassificationStrategy {
    public void assignTopicToCluster(NewsStoryGroup cluster);
    public void assignTopicToStory(NewsStory newsStory);
}
