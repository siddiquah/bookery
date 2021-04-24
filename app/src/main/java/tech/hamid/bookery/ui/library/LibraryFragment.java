package tech.hamid.bookery.ui.library;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Random;

import tech.hamid.bookery.MyStory;
import tech.hamid.bookery.R;
import tech.hamid.bookery.StoryActivity;
import tech.hamid.bookery.StoryAdapter;

public class LibraryFragment extends Fragment {

    private LibraryViewModel LibraryViewModel;

    ListView storyList;
    ProgressBar storyLoader;
    DatabaseReference databaseReference;
    ArrayList<MyStory> stories;
    StoryAdapter adapter;
    ValueEventListener fdbListner;

    String[] colors = new String[]{
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
    };

    public String getRandomColor() {
        int rnd = new Random().nextInt(this.colors.length);
        return this.colors[rnd];
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        LibraryViewModel = new ViewModelProvider(this).get(LibraryViewModel.class);
        View root = inflater.inflate(R.layout.fragment_library, container, false);

        storyList = root.findViewById(R.id.storyList);
        storyLoader = root.findViewById(R.id.storyLoader);
        databaseReference = FirebaseDatabase.getInstance().getReference("stories");
        stories = new ArrayList();
        adapter = new StoryAdapter(getActivity(), stories);
        fdbListner = databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                stories.clear();
                for (DataSnapshot ds : snapshot.getChildren()) {
                    MyStory myStory = ds.getValue(MyStory.class);
                    myStory.setColor(getRandomColor());
                    stories.add(0, myStory);
                }
                storyList.setAdapter(adapter);
                storyLoader.setVisibility(ProgressBar.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("anjum", "oncancelled");

                Log.w("anjum", "loadLog:onCancelled", error.toException());
            }
        });
        storyList.setAdapter(adapter);
        storyList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), StoryActivity.class);
                MyStory s = stories.get(position);
                intent.putExtra("story", s);
                startActivity(intent);
            }
        });
        return root;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        Log.d("anjum", "oncreateoptionmenu");

        FragmentActivity activity = getActivity();
        activity.getMenuInflater().inflate(R.menu.menu, menu);
        MenuItem menuItem = menu.findItem(R.id.searchIcon);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setQueryHint("Search stories");

        ArrayList searchedStories = new ArrayList<MyStory>();
        StoryAdapter newAdapter = new StoryAdapter(getActivity(), searchedStories);

        super.onCreateOptionsMenu(menu, inflater);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            public void searchStories(String query) {
                Log.d("anjum", "searchstories");

                String q = query.toLowerCase();
                searchedStories.clear();
                for(int i=0; i<stories.size(); i++) {
                    MyStory story = stories.get(i);
                    if(story.getTitle().toLowerCase().contains(q)) {
                        searchedStories.add(stories.get(i));
                    }
                }
            }

            @Override
            public boolean onQueryTextSubmit(String query) {
                Log.d("anjum", "onquerytextsubmit");

                searchStories(query);
                storyList.setAdapter(newAdapter);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Log.d("anjum", "onquerytextchange");

                searchStories(newText);
                storyList.setAdapter(newAdapter);
                return false;
            }
        });
        menuItem.setOnActionExpandListener(new MenuItem.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                Log.d("anjum", "onmenuitemactionexpand");

                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                Log.d("anjum", "onMenuItemActionCollapse");

                searchedStories.clear();
                searchedStories.addAll(stories);
                storyList.setAdapter(newAdapter);
                return true;
            }
        });
    }

    @Override
    public void onDestroy() {
        databaseReference.removeEventListener(fdbListner);
        super.onDestroy();
    }
}