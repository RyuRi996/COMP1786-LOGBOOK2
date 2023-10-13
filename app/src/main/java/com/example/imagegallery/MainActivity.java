package com.example.imagegallery;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import java.lang.reflect.Field;
import java.util.ArrayList;
import pl.droidsonroids.gif.GifImageView;

public class MainActivity extends AppCompatActivity {
    int i = -1;
    TextView title, imageId;
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
        imageId = findViewById(R.id.imageId);
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
            imageId.setVisibility(View.VISIBLE);
            title.setVisibility(View.VISIBLE);
            imageId.setText("Image ID: "+ i);
            title.setText(titleName);
        });
        previous.setOnClickListener(view -> {
            if (i==0 || i<0){
                i = imageList.size()-1;
            } else {
                i--;
            }
            imageView.setImageResource(imageList.get(i));
            imageView.setTag(imageList.get(i));
            String titleName = getResources().getResourceName(imageList.get(i)).substring(34);
            imageId.setVisibility(View.VISIBLE);
            title.setVisibility(View.VISIBLE);
            imageId.setText("Image ID: "+ i);
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
                if (fileName.endsWith("jpg")||
                    fileName.endsWith("png")||
                    fileName.endsWith("gif")){
                    imageList.add(drawableFiles[i].getInt(null));
                }
            } catch (Exception e){
                throw new RuntimeException(e);
            }
        }
        return imageList;
    }
}