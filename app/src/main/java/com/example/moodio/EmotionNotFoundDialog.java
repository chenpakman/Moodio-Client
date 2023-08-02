package com.example.moodio;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.moodio.Activities.AnalyzeEmotionActivity;

import java.util.ArrayList;
import java.util.List;

public class EmotionNotFoundDialog extends Dialog implements View.OnClickListener {

    private AnalyzeEmotionActivity activity;

    private Dialog dialog;
    private TextView tellText, goBackText, doneText;

    //private ChooseEmotionsDialog emotionsDialog;

    String[] emotions = {"Happy", "Sad", "Angry", "Exited", "Nervous", "Fear"};

    ArrayAdapter<String> adapter;

    List<String> chosenEmotionsByUser;

    ListView emotionsListView;

    public EmotionNotFoundDialog(@NonNull AnalyzeEmotionActivity activity) {
        super(activity);
        this.activity = activity;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cancel_text:
                this.dismiss();
                break;
            case R.id.okay_text:
                this.showEmotionsDialog();
                break;
            case R.id.done_text:
                this.startPlaylistsActivity();
                break;
        }
    }

    private void startPlaylistsActivity() {
        //todo
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.playlists_dialog_layout);
        getWindow().getAttributes().windowAnimations = R.style.animation;
        setCancelable(false);
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        this.tellText = findViewById(R.id.okay_text);
        this.goBackText = findViewById(R.id.cancel_text);
        this.tellText.setOnClickListener(this);
        this.goBackText.setOnClickListener(this);
    }

    public void showEmotionsDialog() {
        setContentView(R.layout.emotions_dialog_layout);
        this.doneText = findViewById(R.id.done_text);
        this.doneText.setOnClickListener(this);
        this.emotionsListView = findViewById(R.id.moods_listview);
        this.emotionsListView.setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE);
        this.chosenEmotionsByUser = new ArrayList<>();

        adapter = new ArrayAdapter<>
                (this.activity,
                        android.R.layout.select_dialog_multichoice,
                        this.emotions
                );

        emotionsListView.setOnItemClickListener((parent, view, position, id) -> {
            if(!chosenEmotionsByUser.contains(adapter.getItem(position))) {
                chosenEmotionsByUser.add(adapter.getItem(position));
            }
        });
        this.emotionsListView.setAdapter(adapter);
        doneText = findViewById(R.id.done_text);

        doneText.setOnClickListener(v -> {

            String emotions = "";
            for (String emotion:chosenEmotionsByUser) {
                emotions += " " + emotion;
            }
            emotions.trim();
            dismiss();
            this.activity.requestPlayLists(emotions);

        });

        this.show();
    }


}