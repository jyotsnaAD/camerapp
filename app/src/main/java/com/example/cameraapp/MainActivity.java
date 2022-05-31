package com.example.cameraapp;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private final int Camera_Req_Code = 100;
    ImageView imgCamera;
    Button btnCamera;
    Button btnShare;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imgCamera = findViewById(R.id.imgCamera);
        /*imgGallery = findViewById(R.id.imgGallery);*/
//        Button btnYouTube= findViewById(R.id.btnYouTube);
         btnShare = findViewById(R.id.btnShare);
        Button btnCamera = findViewById(R.id.btnCamera);

        btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(iCamera, Camera_Req_Code);

            }
        });

        btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"click on share",Toast.LENGTH_SHORT).show();
                BitmapDrawable drawable=(BitmapDrawable)imgCamera.getDrawable();
                Bitmap bitmap=drawable.getBitmap();
                String bitmapPath=MediaStore.Images.Media.insertImage(getContentResolver(),bitmap,"title","null");
                Uri uri=Uri.parse(bitmapPath);
                Intent intent=new Intent(Intent.ACTION_SEND);
                intent.setType("image/png");
                intent.putExtra(Intent.EXTRA_STREAM,uri);
                intent.putExtra(Intent.EXTRA_STREAM,"http://playstore.google.com");
                startActivity(Intent.createChooser(intent,"Share"));


            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {

            if (requestCode == Camera_Req_Code) {
                //camera
                Bitmap img = (Bitmap) data.getExtras().get("data");
                imgCamera.setImageBitmap(img);
//            }  else {
//                final boolean b = requestCode == Gallery_Req_Code;
//            }
//            //gallery
//            imgGallery.setImageURI(data.getData());

            }
        }
    }
}
