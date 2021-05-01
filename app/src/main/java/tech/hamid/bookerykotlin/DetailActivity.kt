package tech.hamid.bookerykotlin

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.ScrollView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


class DetailActivity : AppCompatActivity() {

    lateinit var txt : TextView
    lateinit var scrollView : ScrollView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        var actionBar  = supportActionBar

        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
        }

        txt = findViewById(R.id.next_txt)
        scrollView = findViewById(R.id.page_scroll)

        val story = intent.getSerializableExtra("story") as MyStory?

        if (story != null) {
            txt.text = story.story
            title = story.title
        }
        else {
            Log.d("anjum", "No story to read")
        }

        var colors = intArrayOf(Color.parseColor("#ffffff"), Color.parseColor(story?.color))
        var gd = GradientDrawable(GradientDrawable.Orientation.TL_BR, colors)
        scrollView.background=gd

    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            android.R.id.home -> {
                this.finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}