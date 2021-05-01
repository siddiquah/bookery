package tech.hamid.bookerykotlin

import android.content.Context
import android.graphics.Color
import android.graphics.Color.parseColor
import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import android.widget.TextView


class StoryAdapter(private var ctx: Context, private var stories: ArrayList<MyStory>): ArrayAdapter<MyStory>(ctx, 0, stories) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var story = getItem(position)
        var cView = convertView

        var wrap : LinearLayout
        var title : TextView
        var snipet : TextView
        var colors : IntArray
        var gd: GradientDrawable

        if(cView == null) {
            cView = LayoutInflater.from(context).inflate(R.layout.item, parent, false)

            if (cView == null) {
                throw Error("cView is null")
            }
        }

        wrap = cView.findViewById(R.id.item_wrap)
        title = cView.findViewById(R.id.item_txt)
        snipet = cView.findViewById(R.id.snipet)

        if(story != null) {
            colors = intArrayOf(Color.parseColor("#ffffff"), Color.parseColor(story.color))
            gd = GradientDrawable(GradientDrawable.Orientation.TL_BR, colors)
            gd.cornerRadius = 16.0f
            wrap.background = gd

            title.text = story.title
            snipet.text = story.story
        }
        return cView
    }

}



