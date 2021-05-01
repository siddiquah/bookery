package tech.hamid.bookerykotlin.ui.library

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ListView
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.database.*
import tech.hamid.bookerykotlin.*
import tech.hamid.bookerykotlin.R
import java.util.*

class LibraryFragment : Fragment() {

    private lateinit var homeViewModel: LibraryViewModel
    lateinit var listView: ListView;
    lateinit var databaseReference: DatabaseReference;
    lateinit var stories: ArrayList<MyStory>;
    lateinit var adapter: StoryAdapter
    lateinit var storyLoader : ProgressBar


    var colors: Array<String> = arrayOf(
        "#FFCDD2",
        "#F8BBD0",
        "#E1BEE7",
        "#D1C4E9",
        "#C5CAE9",
        "#BBDEFB",
        "#B3E5FC",
        "#B2EBF2",
        "#B2DFDB",
        "#C8E6C9",
        "#DCEDC8",
        "#F0F4C3",
        "#FFF9C4",
        "#FFECB3",
        "#FFE0B2",
        "#FFCCBC",
        "#D7CCC8",
        "#B0BEC5"
    )


    private fun getRandomColor(): String {
        val rnd = Random().nextInt(colors.size)
        return colors[rnd]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
                ViewModelProvider(this).get(LibraryViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_library, container, false)
        homeViewModel.text.observe(viewLifecycleOwner, Observer {
        })


        listView = root.findViewById(R.id.listView)
        storyLoader = root.findViewById(R.id.storyLoader)
        stories = java.util.ArrayList()
        adapter = activity?.let { StoryAdapter(it, stories) }!!
        databaseReference = FirebaseDatabase.getInstance().getReference("stories")

        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                Log.d("anjum", "ondatachange")
                stories.clear()
                for (ds in snapshot.children) {
                    Log.d("anjum", ds.toString())

                    var myStory = ds.getValue(MyStory::class.java)
                    if (myStory != null) {
                        myStory.color = getRandomColor()
                        stories.add(0, myStory)
                    }
                }
                storyLoader.visibility = ProgressBar.GONE

                listView.adapter = adapter
                listView.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
                    gotoStory(stories[position])
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })


        return root
    }
    fun gotoStory(story: MyStory) {
        val intent = Intent(activity, DetailActivity::class.java)
        intent.putExtra("story", story)
        startActivity(intent)
    }
}