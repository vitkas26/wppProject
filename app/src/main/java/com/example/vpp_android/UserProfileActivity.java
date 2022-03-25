package com.example.vpp_android;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import com.mikhaellopez.circularimageview.CircularImageView;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class UserProfileActivity extends AppCompatActivity {

    //We declare the variables used:
    TextView textView_firstName, textView_lastName, textView_email;
    EditText editText_firstName, editText_lastName, editText_email;
    CircularImageView circular_imageView;
    TextView textView_button;

    private final int Pick_image = 1;

    boolean st = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        //We communicate with our id:
        textView_firstName = findViewById(R.id.textView_firstName);
        textView_lastName = findViewById(R.id.textView_lastName);
        textView_email = findViewById(R.id.textView_email);
        textView_button = findViewById(R.id.textView_button);

        circular_imageView = findViewById(R.id.circular_imageView);

        editText_firstName = findViewById(R.id.editText_firstName);
        editText_lastName = findViewById(R.id.editText_lastName);
        editText_email = findViewById(R.id.editText_email);

        String text_name;
        //Associating with our TextView
        TextView PickImage = findViewById(R.id.imageClick);

//        SharedPreferences sp = getApplicationContext().getSharedPreferences("Account", Context.MODE_PRIVATE);
//        String name = sp.getString("user_name", "");

        //Set up an OnClickListener click handler for it:
        PickImage.setOnClickListener(v -> {
            //We call the standard gallery to select an image using Intent.ACTION_PICK:
            Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
            //The type of received objects is image:
            photoPickerIntent.setType("image/*");
            //We start the transition with the expectation of the return result in the form of information about the image:
            startActivityForResult(photoPickerIntent, Pick_image);
        });


        textView_button.setOnClickListener(v -> {
            if (st){
                textView_firstName.setText(editText_firstName.getText());
                editText_firstName.setVisibility(View.GONE);
                textView_firstName.setVisibility(View.VISIBLE);

                textView_lastName.setText(editText_lastName.getText());
                editText_lastName.setVisibility(View.GONE);
                textView_lastName.setVisibility(View.VISIBLE);

                textView_email.setText(editText_email.getText());
                editText_email.setVisibility(View.GONE);
                textView_email.setVisibility(View.VISIBLE);
                st = false;
            }else {
                editText_firstName.setText(textView_firstName.getText());
                textView_firstName.setVisibility(View.GONE);
                editText_firstName.setVisibility(View.VISIBLE);

                editText_lastName.setText(textView_lastName.getText());
                textView_lastName.setVisibility(View.GONE);
                editText_lastName.setVisibility(View.VISIBLE);

                editText_email.setText(textView_email.getText());
                textView_email.setVisibility(View.GONE);
                editText_email.setVisibility(View.VISIBLE);
                st = true;
            }
        });
    }

    //Processing the selection result in the gallery:
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent){
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);

        switch (requestCode){
            case Pick_image:
                if (resultCode == RESULT_OK){
                    try {
                        //Get the URI of the image, convert it to a Bitmap
                        //object and display in the ImageView element of our interface:
                        final Uri imageUri = imageReturnedIntent.getData();
                        final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                        final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                        circular_imageView.setImageBitmap(selectedImage);
                    }catch (FileNotFoundException e){
                        e.printStackTrace();
                    }
                }
        }
    }
}