package com.felbsn.odev2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class Description extends AppCompatActivity {

    TextView ld , ln;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description);

        setTitle("Ders Hakkında");



        Intent intent = getIntent();

        ld = findViewById(R.id.lessonDesc_s);
        ln = findViewById(R.id.lessonName_s);



        ln.setText(  intent.getStringExtra("name"));
        ld.setText( intent.getStringExtra("desc"));



        findViewById(R.id.backBtn_s).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


    }





}
