package tech.hamid.bookery;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class StoryAdapter  extends ArrayAdapter<MyStory> {
    public StoryAdapter(Context context, ArrayList<MyStory> stories) {
        super(context, 0, stories);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MyStory story = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item, parent, false);
        }

        LinearLayout wrap = (LinearLayout) convertView.findViewById(R.id.item_wrap);
        TextView title = (TextView) convertView.findViewById(R.id.item_txt);
        TextView snipet = (TextView) convertView.findViewById(R.id.snipet);

        int[] colors = {Color.parseColor("#ffffff"), Color.parseColor(story.getColor())};
        GradientDrawable gd = new GradientDrawable(GradientDrawable.Orientation.TL_BR, colors);
        gd.setCornerRadius(16.0f);

        wrap.setBackground(gd);

        title.setText(story.getTitle());
        snipet.setText(story.getStory());
        return convertView;
    }
}