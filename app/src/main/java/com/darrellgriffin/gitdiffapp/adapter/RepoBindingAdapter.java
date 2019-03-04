package com.darrellgriffin.gitdiffapp.adapter;

import android.widget.TextView;

import com.darrellgriffin.gitdiffapp.view.DiffTextView;

import androidx.databinding.BindingAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class RepoBindingAdapter {


    @BindingAdapter("setText")
    public static void setText(TextView view, String text){
        view.setText(String.valueOf(text));
    }

    @BindingAdapter("setInt")
    public static void setText(TextView view, int number){
        view.setText(String.valueOf(number));
    }

    @BindingAdapter("setAdapter")
    public static void setAdapter(RecyclerView recyclerView, RepoRecyclerAdapter adapter){
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
        recyclerView.setAdapter(adapter);
    }

    @BindingAdapter("setLeftSide")
    public static void setLeftSide(DiffTextView textView, boolean isLeftSide){
        textView.setLeftSide(isLeftSide);
    }

    @BindingAdapter("setDiffText")
    public static void setDiffText(DiffTextView textView, String text){
        textView.setText(text, null);
    }

}
