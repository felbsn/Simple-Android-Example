package com.felbsn.odev2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class LessonListener extends AppCompatActivity {


    RecyclerView recyclerView;
    LessonAdaptor adaptor;
    private List<Lesson> lessons = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lesson_listener);

        setTitle("Ders Seçim");

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        adaptor = new LessonAdaptor(lessons , getApplicationContext());
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adaptor);

        initLessons();
    }

    public  void initLessons()
    {

        for(int i = 0 ; i < 30 ; i ++)
        {
            Lesson l = new Lesson();

            l.name = "Ders " + i ;
            l.info = " Ders " + i + " Hakkında bir takım bilgiler...";
            lessons.add(l);
        }

        adaptor.notifyDataSetChanged();
    }
}
