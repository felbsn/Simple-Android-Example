package com.felbsn.odev2;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;

public class InputActivity extends AppCompatActivity {


    public final int OPEN_IMG_CODE = 245;

    public  final int READ_EXTERNAL_REQUEST = 44;


    EditText mTcView ,mAdView, mSoyadView , mMailView, mTelView;
    ImageButton mImgBtn;
    Button mTemizle , mKaydet , mEkleBtn;

    Uri imageUri;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input);

        setTitle("Bilgi Girişi");

        mTcView    = findViewById(R.id.tc_view);
        mAdView   = findViewById(R.id.ad_view);
        mSoyadView = findViewById(R.id.soyad_view);
        mMailView  = findViewById(R.id.mail_view);
        mTelView   = findViewById(R.id.tel_view);

        mImgBtn    = findViewById(R.id.imgBtn_view);

        mTemizle    = findViewById(R.id.sTemizle_view);
        mKaydet    = findViewById(R.id.sKaydet_view);

        mEkleBtn    = findViewById(R.id.ppEkleView);




        mImgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(isPermissionGranted(Manifest.permission.READ_EXTERNAL_STORAGE , READ_EXTERNAL_REQUEST) ) {

                    Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                    intent.addCategory(Intent.CATEGORY_OPENABLE);
                    intent.setType("image/*");
                    startActivityForResult(intent, OPEN_IMG_CODE);
                }
            }
        });

        mEkleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(isPermissionGranted(Manifest.permission.READ_EXTERNAL_STORAGE , READ_EXTERNAL_REQUEST) ) {

                    Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                    intent.addCategory(Intent.CATEGORY_OPENABLE);
                    intent.setType("image/*");
                    startActivityForResult(intent, OPEN_IMG_CODE);
                }
            }
        });




        mTemizle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mAdView.getText().clear();
                mTcView.getText().clear();
                mSoyadView.getText().clear();
                mMailView.getText().clear();
                mTelView.getText().clear();
            }
        });


        mKaydet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent nextPhaze = new Intent(getApplicationContext() , InfoScreen.class);
                nextPhaze.putExtra("ad" ,mAdView.getText().toString());
                nextPhaze.putExtra("tc" ,mTcView.getText().toString());
                nextPhaze.putExtra("soyad" ,mSoyadView.getText().toString());
                nextPhaze.putExtra("mail" ,mMailView.getText().toString());
                nextPhaze.putExtra("tel" ,mTelView.getText().toString());

                if(imageUri != null)
                nextPhaze.putExtra("imgUri" ,imageUri.toString());







                startActivity(nextPhaze);
            }
        });


        mMailView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == EditorInfo.IME_ACTION_DONE || id == EditorInfo.IME_NULL) {



                    if(!android.util.Patterns.EMAIL_ADDRESS.matcher( textView.getText().toString()).matches())
                    {
                        textView.setError("girilen email geçersiz!");
                    }


                    return true;
                }
                textView.setError(null);
                return false;
            }
        });

        mTelView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {



                if(!Patterns.PHONE.matcher( mTelView.getText().toString()).matches())
                {

                    textView.setError("girilen numara geçersiz!");
                }else
                {
                    textView.setError(null);
                }


                return false;
            }
        });

        mTcView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == EditorInfo.IME_ACTION_DONE || id == EditorInfo.IME_NULL) {

                    if(textView.getText().toString().length() != 11)
                    {
                        textView.setError("girilen tc kimlik no bilgisi geçersiz!");
                        return false;
                    }
                    else
                    return true;
                }

              //  textView.setError(null);

                if(textView.getText().toString().length() != 11)
                {
                    textView.setError("girilen tc kimlik no bilgisi geçersiz!");
                }else
                {
                    textView.setError(null);
                }



                return false;
            }
        });


    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent resultData) {
        if (requestCode == OPEN_IMG_CODE && resultCode == RESULT_OK) {
            if (resultData != null) {

                imageUri = resultData.getData();


                Uri uri = resultData.getData();

                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);


                    mImgBtn.setImageBitmap(bitmap);
                    Toast.makeText(getApplicationContext(), "Resim Eklendi " +imageUri.toString() , Toast.LENGTH_SHORT).show();
                } catch (IOException e) {

                    e.printStackTrace();
                }

            }
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





}
