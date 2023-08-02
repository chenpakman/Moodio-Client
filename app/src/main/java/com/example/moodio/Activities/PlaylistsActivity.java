package com.example.moodio.Activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.moodio.PlayListAdapter;
import com.example.moodio.PlaylistInfo;
import com.example.moodio.R;
import com.example.moodio.Utils.Playlist;
import com.example.moodio.Utils.ResponseServer;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Map;

public class PlaylistsActivity extends AppCompatActivity {


    private ListView playListsListView;
    private PlayListAdapter playListAdapter;
    private ArrayList<PlaylistInfo> playlistsArray;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playlists);
        getWindow().setStatusBarColor(Color.BLACK);
        playListsListView=findViewById(R.id.listView);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        playlistsArray =new ArrayList<>();

        String playlistsJson = getIntent().getStringExtra("EXTRA_MESSAGE");
        Gson gson = new Gson();
        ResponseServer res = gson.fromJson(playlistsJson, ResponseServer.class);
        TextView title =findViewById(R.id.playlistTitle);
        title.setText("It seems you are feeling "+ res.getEmotion().toLowerCase(Locale.ROOT)+".\n So here are your playlists:\n");
        playListAdapter=new PlayListAdapter(this,R.layout.list_row,playlistsArray);
        playListsListView.setAdapter(playListAdapter);

        addPlaylists(res);
        System.out.println("On create PlaylistActivity");//ToDo:delete

    }
private void addPlaylists(ResponseServer res){
    playlistsArray.clear();
    playListAdapter.clear();
    playListAdapter.notifyDataSetChanged();

    for (Map.Entry<String, Playlist> entry: res.getPlaylistsUrls().entrySet()) {
        PlaylistInfo playlist=new PlaylistInfo(entry.getValue().getImageUrl(),entry.getKey(),entry.getValue().getPlaylistUrl());
        playlistsArray.add(playlist);
        playListsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String playlistUrl=playListAdapter.getPlaylistUrl(position);
                Intent intent = new Intent(PlaylistsActivity.this, SpotifyActivity.class);
                intent.putExtra("EXTRA_MESSAGE", playlistUrl);
                startActivity(intent);
            }
        });

    }

    playListAdapter.notifyDataSetChanged();
}







}