package com.felbsn.odev2;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

import static android.Manifest.permission.CALL_PHONE;
import static android.Manifest.permission.READ_CONTACTS;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;

public class InfoScreen extends AppCompatActivity {



    public final int PHONE_REQUEST_NUM = 454;


    TextView mAdLabel, mSoyadLabel , mTcLabel ,mMailLabel, mTelLabel;
    ImageButton callBtn , mailBtn;
    ImageView imgView;
    Button dersBtn;


    boolean ableToCall= false;


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_screen);

        setTitle("Girilen Bilgiler");


        mAdLabel    = findViewById(R.id.ad_info_view);
        mSoyadLabel   = findViewById(R.id.soyad_info_view);
        mTcLabel = findViewById(R.id.tc_info_view);
        mMailLabel  = findViewById(R.id.mail_info_view);
        mTelLabel   = findViewById(R.id.tel_info_view);

        imgView    = findViewById(R.id.imageView);

        callBtn    = findViewById(R.id.callBtn);
        mailBtn    = findViewById(R.id.mailBtn);
        dersBtn    = findViewById(R.id.dersbtn_view);



        callBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if(isPermissionGranted( CALL_PHONE , PHONE_REQUEST_NUM )){
                    CallPhone();
                }
            }
        });

        mailBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:" + mMailLabel.getText().toString()));
                startActivity(intent);
            }
        });



        dersBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext()  , LessonListener.class);
                startActivity(intent);
            }
        });







        fillForms();
    }


    public void CallPhone()
    {

        Intent intent = new Intent(Intent.ACTION_DIAL ,Uri.parse("tel:" + mTelLabel.getText().toString()) );
        startActivity(intent);
    }


    public void fillForms()
    {
        Intent it = getIntent();


        mAdLabel.setText(   it.getStringExtra("ad") );
        mSoyadLabel.setText(   it.getStringExtra("soyad") );
        mTcLabel.setText(   it.getStringExtra("tc") );
        mMailLabel.setText(   it.getStringExtra("mail") );
        mTelLabel.setText(   it.getStringExtra("tel") );

        if(it.getStringExtra("imgUri") != null)
        {
            if(isPermissionGranted(READ_EXTERNAL_STORAGE , 44 ))
            {

                Uri imgUri = Uri.parse(it.getStringExtra("imgUri"));

                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imgUri);
                    imgView.setImageBitmap(bitmap);


                }catch (IOException e)
                {
                    e.printStackTrace();
                }
            }else
            {
                // daha once izin vermis olması lazım , uri yi set edebilmesi icin...
            }

        }else
        {
            // image not set
        }
    }


    public  boolean isPermissionGranted( String PERM , int REQ_CODE) {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(PERM)
                    == PackageManager.PERMISSION_GRANTED) {
                return true;
            } else {

                ActivityCompat.requestPermissions(this, new String[]{PERM}, REQ_CODE);
                return false;
            }
        }
        else { //permission is automatically granted on sdk<23 upon installation
            return true;
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {

            case PHONE_REQUEST_NUM: {

                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(getApplicationContext(), "izin verildi", Toast.LENGTH_SHORT).show();
                    CallPhone();
                } else {
                    Toast.makeText(getApplicationContext(), "izin alınamadı", Toast.LENGTH_SHORT).show();
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }


}
