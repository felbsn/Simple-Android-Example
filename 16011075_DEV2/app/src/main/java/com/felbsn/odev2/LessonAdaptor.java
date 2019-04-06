package com.felbsn.odev2;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

public class LessonAdaptor extends RecyclerView.Adapter<LessonAdaptor.MyViewHolder> {

    private List<Lesson> lessonList;
    Context ctx;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public Button hbutton;
        public Button hdelbutton;

        public Lesson lesson;

        public MyViewHolder(View view ) {
            super(view);
            hbutton =   view.findViewById(R.id.lessonName);
            hdelbutton = view.findViewById(R.id.delLesson);

        }
    }


    public LessonAdaptor(List<Lesson> moviesList , Context context) {
        this.lessonList = moviesList;
        ctx = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_lesson, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        Lesson l = lessonList.get(position);
        holder.hbutton.setText(l.name);
        holder.lesson = l;

        holder.hbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(ctx, Description.class);
                intent.putExtra("name" , holder.lesson.name );
                intent.putExtra("desc" , holder.lesson.info);

                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                ctx.startActivity(intent);
            }
        });

        holder.hdelbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                lessonList.remove(holder.lesson );
                notifyDataSetChanged();
            }
        });


    }

    @Override
    public int getItemCount() {
        return lessonList.size();
    }
}