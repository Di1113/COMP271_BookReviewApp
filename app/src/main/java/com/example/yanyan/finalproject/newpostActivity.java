package com.example.yanyan.finalproject;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.text.DateFormat;
import java.util.Date;

import cz.msebera.android.httpclient.util.ByteArrayBuffer;

/**
 * Created by yanyan on 4/6/18.
 */

public class newpostActivity extends AppCompatActivity {

    private Context mContext;
    private static final String TAG = newpostActivity.class.getSimpleName();
    private DatabaseHelper myDb;
    private RelativeLayout newpostLayout;
    private EditText edittags, editquote, editthoughts;
    private EditText editbook, editauthor;
    private Button submit;
    private ImageButton chooseImage, takePicture;
    private String audiooutput;
    private String bookcoverURL;
    private byte[] bookCoverBlob;
    private ImageView imageview;
    private RatingBar ratingBar;
    private ImageUtils imageUtils = new ImageUtils();

    public static final int CAMERA_REQUEST = 1888;
    public static final int GALLERY_REQUEST = 1889;

    private String title, author, tag, quote, rate, thought, imageURI, draftAll,fetch;
    private String key = "com.example.di.finalproject_test.key";
    private boolean autoSavedLastTime = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.NewPost);
        setContentView(R.layout.newpost);

        mContext = this;
        myDb = new DatabaseHelper(this);

        editbook = findViewById(R.id.title_edit);
        editbook.getText().clear();

        editauthor = findViewById(R.id.author_edit);
        editauthor.getText().clear();

        edittags = findViewById(R.id.tags_edit);
        edittags.getText().clear();

        editquote = findViewById(R.id.quote_edit);
        editquote.getText().clear();

        editthoughts = findViewById(R.id.review_edit);
        editthoughts.getText().clear();

        ratingBar = findViewById(R.id.ratebar);
        submit = findViewById(R.id.submit);
        chooseImage = findViewById(R.id.image_select_button);
        takePicture = findViewById(R.id.camera_button);
        imageview = findViewById(R.id.imageView);
        newpostLayout = findViewById(R.id.newpost);
//        fetch ="NO";


        //sharedPreference for autoSave
        SharedPreferences prefs = mContext.getSharedPreferences(
                "com.example.di.finalproject_test", Context.MODE_PRIVATE);
        Log.d(this.getClass().getName(), "draftAll: " + draftAll);

        draftAll = "false";
        String[] draft = prefs.getString(key, draftAll).split("/");

        autoSavedLastTime = Boolean.valueOf(draft[0]);

        Log.d(this.getClass().getName(), "draft length: " + draft.length);

        if (autoSavedLastTime && draft.length > 1) {
            Log.d(this.getClass().getName(), "autosave processing.....");

            switch (draft.length) {
                case 2:
                    editbook.setText(draft[1]);
                    break;

                case 3:
                    editbook.setText(draft[1]);
                    editauthor.setText(draft[2]);
                    break;

                case 4:
                    editbook.setText(draft[1]);
                    editauthor.setText(draft[2]);
                    edittags.setText(draft[3]);
                    break;

                case 5:
                    editbook.setText(draft[1]);
                    editauthor.setText(draft[2]);
                    edittags.setText(draft[3]);
                    editquote.setText(draft[4]);
                    break;


                case 6:
                    editbook.setText(draft[1]);
                    editauthor.setText(draft[2]);
                    edittags.setText(draft[3]);
                    editquote.setText(draft[4]);
                    try{
                        ratingBar.setRating(Float.valueOf(draft[5]));}
                    catch ( NumberFormatException e){
                        ratingBar.setRating(0);
                    }
                    break;


                case 7:
                    editbook.setText(draft[1]);
                    editauthor.setText(draft[2]);
                    edittags.setText(draft[3]);
                    editquote.setText(draft[4]);
                    try{
                        ratingBar.setRating(Float.valueOf(draft[5]));}
                    catch ( NumberFormatException e){
                        ratingBar.setRating(0);
                    }
                    editthoughts.setText(draft[6]);
                    break;
            }
        }


        //select image from gallery
        chooseImage.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent chooseImgIntent = new Intent( Intent.ACTION_PICK);
                chooseImgIntent.setType("image/*");
                startActivityForResult(chooseImgIntent, GALLERY_REQUEST);
            }
        });

        takePicture.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent takePictureIntent = new Intent( MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(takePictureIntent, CAMERA_REQUEST);
            }
        });


        //load the book information from the previous searchview,get as a book
        Boolean fetchbook = this.getIntent().getExtras().getBoolean("fetch");

        if (fetchbook) {
            Book book = (Book) getIntent().getSerializableExtra("book");
            editbook.setText(book.getTitle());
            editauthor.setText(book.getAuthor());
            bookcoverURL = book.getCoverUrl();
            bookCoverBlob = getImageBLOB(bookcoverURL);
//
//            newpostLayout.removeView(chooseImage);
//            newpostLayout.removeView(takePicture);

            imageview.setImageBitmap(ImageUtils.getImage(bookCoverBlob));
//            fetch="YES";
        }

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //clean out the sharedpreference draft
                SharedPreferences prefs = mContext.getSharedPreferences(
                        "com.example.di.finalproject_test", Context.MODE_PRIVATE);
                draftAll = "false,a,b,c";
                prefs.edit().putString(key, draftAll).apply();


                String rate = String.valueOf(ratingBar.getRating());
                String book = editbook.getText().toString();
                String author = editauthor.getText().toString();
                String hashtag = edittags.getText().toString();
                String quote = editquote.getText().toString();
                String thoughts = editthoughts.getText().toString();
//                String Bookcover = bookcoverURL;
                String record = audiooutput;


                //get the date and time when submitted
                Date todayDate = new Date();
                DateFormat dateFormat = DateFormat.getDateTimeInstance();
                String datetime = dateFormat.format(todayDate);


                //Toast.makeText(newpostActivity.this, datetime, Toast.LENGTH_LONG).show();


//                Post post = new Post(book, author,hashtag, quote, rate, thoughts, record ,Bookcover,datetime,fetch);
//                Post post = new Post(book, author,hashtag, quote, rate, thoughts, record, Bookcover,datetime);


                //boolean checksub = thoughts.equals("")&&record.equals("");

                if (rate.isEmpty()||book.isEmpty()||author.isEmpty()||thoughts.isEmpty()){
                    Toast.makeText(newpostActivity.this, "Please complete the required information", Toast.LENGTH_LONG).show();
                }
                else{
                    //String book, String author, String tags, String quote, String rate, String thoughts,String record,String datetime
//                    if (myDb.insertData(book, author,hashtag, quote, rate, thoughts, record ,Bookcover,datetime,fetch)){
                    if (myDb.insertData(book, author,hashtag, quote, rate, thoughts, bookCoverBlob ,datetime)){
                        Toast.makeText(newpostActivity.this, "New Post Submitted!", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(newpostActivity.this,postlistActivity.class);
                        startActivity(intent);

                        //clean out the sharedpreference draft
                        editbook.setText("");
                        editauthor.setText("");
                        edittags.setText("");
                        editquote.setText("");
                        editthoughts.setText("");

                    }

                }


            }


        });

        myDb.close();
    }

    //for URL to convert to BLOB
    protected byte[] getImageBLOB(String url){
        try {
            URL imageUrl = new URL(url);
            URLConnection ucon = imageUrl.openConnection();

            InputStream is = ucon.getInputStream();
            BufferedInputStream bis = new BufferedInputStream(is);

            ByteArrayBuffer baf = new ByteArrayBuffer(500);
            int current = 0;
            while ((current = bis.read()) != -1) {
                baf.append((byte) current);
            }

            return baf.toByteArray();
        } catch (Exception e) {
            Log.d("ImageManager", "Error: " + e.toString());
        }
        return null;
    }

    //For Camera and gallery get pictures intent
    protected void onActivityResult (int requestCode, int resultCode, Intent data){
        // if the request code is camera request
        if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK){
            Bitmap photo = (Bitmap)data.getExtras().get("data");
            imageview.setImageBitmap(photo);

            try {
                bookCoverBlob = ImageUtils.getImageBytes(photo);
            }
            catch (NullPointerException e){
                Toast.makeText(this, "Take or choose a picture please.", Toast.LENGTH_LONG).show();
            }
        }
        else if (requestCode == GALLERY_REQUEST && resultCode == RESULT_OK){
            try {
                final Uri imageUri = data.getData();
                final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                imageview.setImageBitmap(selectedImage);
                try {
                    bookCoverBlob = ImageUtils.getBytes(imageStream);
                }
                catch(IOException e){
                    Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG).show();
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG).show();
            }

        }else {
            Toast.makeText(this, "You haven't picked or taken an Image",Toast.LENGTH_LONG).show();
        }

    }


    protected void autoSave(){
        if(this.title != null || this.quote != null || this.thought != null){

            SharedPreferences prefs = mContext.getSharedPreferences(
                    "com.example.di.finalproject_test", Context.MODE_PRIVATE);

            String titleDraft, authorDraft, tagDraft, quoteDraft, rateDraft, thoughtDraft, imgURIDraft;
            // title, author, tag, quote, rate, thought, imageURI

            titleDraft = this.title;
            authorDraft = this.author;
            tagDraft = this.tag;
            quoteDraft = this.quote;
            rateDraft = this.rate;
            thoughtDraft = this.thought;

            draftAll = "true/" + titleDraft + "/" + authorDraft + "/" + tagDraft + "/"
                    + quoteDraft + "/" + rateDraft + "/"  + thoughtDraft;

            prefs.edit().putString(key, draftAll).apply();

            Log.d(this.getClass().getName(), "Draft saved(success/title/quote/thought):" + prefs.getString(key, draftAll));

        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == android.view.KeyEvent.KEYCODE_BACK)) {
            Log.d(this.getClass().getName(), "back button pressed" + "\n" + "title entered:"
                    + editbook.getText().toString() + "; quote: " + editquote.getText().toString()
                    + "; thought: " + editthoughts.getText().toString());

            if(! (editbook.getText().toString().matches("")
                    && editauthor.getText().toString().matches("")
                    && edittags.getText().toString().matches("")
                    && editquote.getText().toString().matches("")
                    && ratingBar.getRating() == 0
                    && editthoughts.getText().toString().matches(""))) {

                title = editbook.getText().toString();
                author = editauthor.getText().toString();
                tag = edittags.getText().toString();
                quote = editquote.getText().toString();
                rate = String.valueOf(ratingBar.getRating());
                thought = editthoughts.getText().toString();

                Log.d(this.getClass().getName(), "Draft to be saved: \n title: " + title
                        + "\n author: " + author + "\n tags: " + tag + "\n quote: " + quote
                        + "\n rate: " + rate + " \n thought: " + thought);

                autoSave();
            }
        }
        return super.onKeyDown(keyCode, event);
    }

}
