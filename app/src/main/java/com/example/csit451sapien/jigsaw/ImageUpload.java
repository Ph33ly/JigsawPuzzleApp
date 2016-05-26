package com.example.csit451sapien.jigsaw;

/**
 * made my mauricio
 * developed by brian
 */

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.provider.MediaStore;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.FileOutputStream;
import java.io.IOException;


public class ImageUpload extends JigsawHomePage implements View.OnClickListener{

    private final int PICK_IMAGE_REQUEST = 1;
    private ImageView imageView;
    private Bitmap bitmap;
    private Uri filePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_upload);

        imageView = (ImageView) findViewById(R.id.imageView);

        // set listeners on btns
        findViewById(R.id.buttonChoose).setOnClickListener(this);
        findViewById(R.id.buttonPlay).setOnClickListener(this);
    }

    // opens file selector for img
    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    // get the selected img bitmap and display it
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {

            filePath = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                bitmap = Bitmap.createScaledBitmap(bitmap, ((bitmap.getWidth()<=400)?bitmap.getWidth():400),
                        ((bitmap.getHeight()<=400)?bitmap.getHeight():400), true);

                checkOrientation(filePath);

                imageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // checks the current orientation of img, and rotates accordingly
    private void checkOrientation(Uri uri) {
        try {
            ExifInterface exif = new ExifInterface(uri.getPath());
            int rotation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
            int rotationInDegrees = exifToDegrees(rotation);

            Matrix matrix = new Matrix();
            if (rotation != 0f) {
                matrix.preRotate(rotationInDegrees);
            }

            bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // returns int value of orientation degree
    private int exifToDegrees(int orientation) {
        if (orientation == ExifInterface.ORIENTATION_ROTATE_90) {
            return 90;
        }
        else if (orientation == ExifInterface.ORIENTATION_ROTATE_180) {
            return 180;
        }
        else if (orientation == ExifInterface.ORIENTATION_ROTATE_270) {
            return 270;
        }
        return 0;
    }

    // action for playing a puzzle game with img
    private void playWithImage() {

        // Check if user selected an image
        if (imageView.getDrawable() == null) {
            Toast.makeText(getApplicationContext(), "Please Select an Image First", Toast.LENGTH_SHORT).show();
        }
        else {
            // If so send them to the difficulty setting and save the img

            Toast.makeText(getApplicationContext(), "Starting puzzle!", Toast.LENGTH_SHORT).show();
            saveImage();
            Intent intent = new Intent(ImageUpload.this, PuzzleHandler.class);
            intent.putExtra("imageName", "");
            startActivity(intent);
        }
    }

    // save img bitmap to file
    private void saveImage() {

        try {
            FileOutputStream fOut = openFileOutput(CHOSEN_IMAGE, MODE_PRIVATE);
            bitmap.compress(Bitmap.CompressFormat.PNG, 50, fOut);
            fOut.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    // handles click events
    @Override
    public void onClick(View v) {
        int id = v.getId();

        switch (id) {
            case R.id.buttonChoose:
                 showFileChooser();
                break;
            case R.id.buttonPlay:
                playWithImage();
                break;
            default:
                // do nothing
        }
    }
}
