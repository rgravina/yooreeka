package iweb2.ch3.content.digg;

import java.util.ArrayList;
import java.util.List;

import de.thesuntoucher.jigg.data.Container;

public class DiggCategory extends Container {
    private static final List<DiggCategory> allCategories = new ArrayList<DiggCategory>();
    
    public static final DiggCategory TECHNOLOGY = new DiggCategory("Technology", "technology");
    public static final DiggCategory WORLD_AND_BUSINESS = new DiggCategory("World&Business", "world_business");
    public static final DiggCategory SPORTS = new DiggCategory("Sports", "sports");
    public static final DiggCategory SCIENCE = new DiggCategory("Science", "science");
    public static final DiggCategory GAMING = new DiggCategory("Gaming", "gaming");
    public static final DiggCategory ENTERTAINMENT = new DiggCategory("Entertainment", "entertainment");
    public static final DiggCategory VIDEOS = new DiggCategory("Videos", "videos");
    
    private DiggCategory(String name, String shortName) {
        super(name, shortName);
        allCategories.add(this);
    }

    // Note that default Container.toString() implementation in jigg library
    // won't work with digg api call. 
    @Override
	public String toString() {
        return getShortName();
    }
    
    public static List<DiggCategory> getAllCategories() {
        return DiggCategory.allCategories;   
    }
    
    public static DiggCategory valueOf(String name) {
        DiggCategory match = null;
        for(DiggCategory c : allCategories) {
            if( c.getName().equalsIgnoreCase(name)) {
                match = c;
                break;
            }
        }
        return match;
    }
    
}
