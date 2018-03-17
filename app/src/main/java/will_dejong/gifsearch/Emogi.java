package will_dejong.gifsearch;

import java.util.ArrayList;

/**
 * Created by William on 3/7/2018.
 */

public class Emogi {
    String fullUrl;
    ArrayList<String> tags;

    public Emogi(String url, ArrayList<String> tags) {
        this.fullUrl = url;
        this.tags = tags;
    }

    public String getThumbUrl() {
        return fullUrl;
    }

    public ArrayList<String> getTags() {
        return tags;
    }
    
}
