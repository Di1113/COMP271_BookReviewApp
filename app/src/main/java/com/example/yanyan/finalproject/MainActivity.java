package com.example.yanyan.finalproject;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private Context mContext;
    private Activity mActivity;
    private static final int REQUEST_CODE=123;
    Button start;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContext = this;
        mActivity =MainActivity.this;
        start = findViewById(R.id.start);
        checkPermission();

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent postlistIntent = new Intent(mContext, postlistActivity.class);
                startActivity(postlistIntent);

            }


        });


    }



    //check the permission

    protected void checkPermission(){
        if ( ContextCompat.checkSelfPermission(mActivity, Manifest.permission.CAMERA)
                + ContextCompat.checkSelfPermission(mActivity,Manifest.permission.READ_EXTERNAL_STORAGE)
                + ContextCompat.checkSelfPermission(mActivity,Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED){

            //show an alert dialog/ popup window request explanations
            if (ActivityCompat.shouldShowRequestPermissionRationale(mActivity, Manifest.permission.CAMERA)
                    ||ActivityCompat.shouldShowRequestPermissionRationale(mActivity,Manifest.permission.READ_EXTERNAL_STORAGE)
                    ||ActivityCompat.shouldShowRequestPermissionRationale(mActivity,Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    ) {
                android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(mActivity);
                builder.setMessage("Auido and External storage permission are required for this APP");
                builder.setTitle("Please grant these permission");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        ActivityCompat.requestPermissions(mActivity, new String[]{
                                        Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE,
                                        Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                REQUEST_CODE);

                    }

                });

                builder.setNegativeButton("Cancel",null);
                android.app.AlertDialog dialog = builder.create();
                dialog.show();

            }
            else{
                ActivityCompat.requestPermissions(mActivity, new String[]{
                        Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE},REQUEST_CODE);
            }

        }
        else{
            Toast.makeText(mContext,"Welcome!",Toast.LENGTH_LONG).show();
        }
        //if the permission is not granted,
        // you need to do something "explanation"
        //directly request for required permission of your app

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permission, int[] grantResults){
        if (requestCode == REQUEST_CODE){
            if (grantResults.length > 0 && (grantResults[0]+grantResults[1]+grantResults[2] == PackageManager.PERMISSION_GRANTED)){
//                Toast.makeText(mContext,"Permission already granted",Toast.LENGTH_LONG).show();
            }
            else{
                Toast.makeText(mContext,"Permission denied",Toast.LENGTH_LONG).show();
            }
        }
    }

}
