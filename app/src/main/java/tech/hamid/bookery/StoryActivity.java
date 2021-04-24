package tech.hamid.bookery;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.view.MenuItem;
import android.widget.ScrollView;
import android.widget.TextView;

public class StoryActivity extends AppCompatActivity {

    TextView txt, titleTxt;
    ScrollView scrollView;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story);

        // calling the action bar
        ActionBar actionBar = getSupportActionBar();

        // showing the back button in action bar
        actionBar.setDisplayHomeAsUpEnabled(true);


        titleTxt = findViewById(R.id.storyTitle);
        txt = findViewById(R.id.next_txt);
        scrollView = findViewById(R.id.page_scroll);

        MyStory story = (MyStory) getIntent().getSerializableExtra("story");
//        txt.setText(story.getStory());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            txt.setText(Html.fromHtml(story.getStory(), Html.FROM_HTML_MODE_COMPACT));
        } else {
            txt.setText(Html.fromHtml(story.getStory()));
        }

        setTitle(story.getTitle());
        titleTxt.setText(story.getTitle());

        int[] colors = {Color.parseColor("#ffffff"),Color.parseColor(story.getColor())};
        GradientDrawable gd = new GradientDrawable(GradientDrawable.Orientation.TL_BR, colors);

        scrollView.setBackground(gd);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}