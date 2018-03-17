package will_dejong.gifsearch;

import android.content.Context;
import android.net.Uri;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;

public class MainActivity extends AppCompatActivity {
    private RecyclerView rv;
    private EditText searchText;
    private RecyclerView.Adapter mAdapter;
    private Uri[] mDataSet;

    ArrayList<Emogi> emogis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rv = findViewById(R.id.rv);
        searchText = findViewById(R.id.searchText);
        rv.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        Handler jsonThread = new Handler();
        jsonThread.post((new Runnable() {
            @Override
            public void run() {
                parseJson();
            }}));
    }

    public void search(View v){
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
        String text = searchText.getEditableText().toString().trim();
        Log.d("text", text);
        mAdapter = new GifListAdapter(match(text), getApplicationContext());
        rv.setAdapter(mAdapter);
        if(text == null || text.equals("")) {
            Toast.makeText(this, "Enter some text, silly!", Toast.LENGTH_SHORT).show();
        }
    }

    public ArrayList<Emogi> match(String text){
        ArrayList<Emogi> matches = new ArrayList<>();
        for(Emogi e: emogis){
            if(e.getTags().contains(text))
                matches.add(e);
        }
        Log.d("matches", matches.size()+"");
        return matches;
    }

    public void parseJson(){
        String jsonString = null;
        try {
            InputStream is = getAssets().open("contents.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            jsonString = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        emogis = getEmogiList(jsonString);
    }

    public static ArrayList<Emogi> getEmogiList(String jsonString){

        ArrayList<Emogi> emogiList = new ArrayList<>();

        try {
            JSONObject contentsJSON = new JSONObject(jsonString);
            contentsJSON = contentsJSON.getJSONObject("contents");
            Iterator keys = contentsJSON.keys();

            while(keys.hasNext()) {
                JSONObject contentIdJSONObject = contentsJSON.getJSONObject(keys.next()+"");
                emogiList.add(
                        new Emogi(
                                getUrl(contentIdJSONObject.getJSONArray("assets")),
                                getTags(contentIdJSONObject.getJSONArray("tags"))
                        )
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return emogiList;
    }

    public static String getUrl(JSONArray input){
        try {
            String thumbUrl = input.getString(1).split("\"")[7];
            thumbUrl = thumbUrl.replace("\\","");
            return thumbUrl;
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return "";
    }

    public static ArrayList<String> getTags(JSONArray input){
        ArrayList<String> tags = new ArrayList<>();
        try {
            for (int i = 0; i < input.length(); i++) {
                tags.add(input.getString(i));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return tags;
    }
}
