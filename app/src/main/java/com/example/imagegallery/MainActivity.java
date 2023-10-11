package com.example.imagegallery;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.content.res.ResourcesCompat;

import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.TransitionDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.util.SparseIntArray;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import pl.droidsonroids.gif.GifImageView;

public class MainActivity extends AppCompatActivity {
    int i = -1;
    TextView title;
    GifImageView imageView;
    Button next, previous;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        buttonImage();
    }

    private void buttonImage() {

        ArrayList<Integer> imageList = getImageDrawable();
        title = findViewById(R.id.titleImage);
        imageView = findViewById(R.id.imageShow);
        next = findViewById(R.id.btnNext);
        previous = findViewById(R.id.btnPrevious);

        next.setOnClickListener(view -> {
            if (i==imageList.size()-1){
                i=0;
            } else {
                i++;
            }
            imageView.setImageResource(imageList.get(i));
            String titleName = getResources().getResourceName(imageList.get(i)).substring(34);
            title.setVisibility(View.VISIBLE);
            title.setText(titleName);
        });
        previous.setOnClickListener(view -> {
            if (i==0 || i<0){
                i = imageList.size()-1;
            } else {
                i--;
            }
            imageView.setImageResource(imageList.get(i));
            String titleName = getResources().getResourceName(imageList.get(i)).substring(34);
            title.setVisibility(View.VISIBLE);
            title.setText(titleName);
        });
    }
    @NonNull
    private ArrayList<Integer> getImageDrawable() {
        Field[] drawableFiles = R.drawable.class.getFields();
        ArrayList<Integer> imageList = new ArrayList<>();

        TypedValue value = new TypedValue();
        for(int i =0; i<drawableFiles.length;i++){
            try {
                getResources().getValue(drawableFiles[i].getInt(null), value, true);
                String fileName = value.string.toString();
                if (fileName.endsWith("jpg")|| fileName.endsWith("png") || fileName.endsWith("gif")){
                    imageList.add(drawableFiles[i].getInt(null));
                }
            } catch (Exception e){
                throw new RuntimeException(e);
            }
        }
        return imageList;
    }
}