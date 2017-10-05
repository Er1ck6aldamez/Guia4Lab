package com.example.hd.guia4lab;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.FileDescriptor;
import java.io.IOException;

public class Principal extends AppCompatActivity {

    private ImageView imagView1,imageView2;
    private int PICK_PHOTO=3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);


        imagView1 = (ImageView) findViewById(R.id.img1);
        imageView2 = (ImageView) findViewById(R.id.img2);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu,menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){

            case R.id.agregar1:
                buscarIMG(1);
                Toast.makeText(this,"Agregar Imagen",Toast.LENGTH_SHORT).show();
                return true;
            case R.id.agregar2:
                buscarIMG(2);
                Toast.makeText(this,"Agregar Imagen",Toast.LENGTH_SHORT).show();
                return true;
            case R.id.eliminar:
                eliminarIMG();
                Toast.makeText(this,"Imagenes Eliminadas",Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }

    }



    private void buscarIMG(int btn){
        if(btn==1) {
            Intent intent = new
                    Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent,PICK_PHOTO);
        }else{
            Intent intent = new
                    Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, 4);
        }
    }




    private void eliminarIMG() {
        imagView1.setImageDrawable(null);
        imageView2.setImageDrawable(null);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_PHOTO && resultCode == Activity.RESULT_OK) {
            Uri selectedImage = data.getData();
            Bitmap bmp = null;
            try {
                bmp = getBitmapFromUri(selectedImage);
                imagView1.setImageBitmap(bmp);
            } catch (IOException e) {
                Toast.makeText(this,"Error Cargando imagen",Toast.LENGTH_SHORT);
                e.printStackTrace();
            }
        }
        if (requestCode == 4 && resultCode == Activity.RESULT_OK) {
            Uri selectedImage = data.getData();
            Bitmap bmp = null;
            try {
                bmp = getBitmapFromUri(selectedImage);
                imageView2.setImageBitmap(bmp);
            } catch (IOException e) {
                Toast.makeText(this,"Error Cargando imagen",Toast.LENGTH_SHORT);
                e.printStackTrace();
            }
        }

    }

    private Bitmap getBitmapFromUri(Uri uri) throws IOException {
        ParcelFileDescriptor parcelFileDescriptor =
                getContentResolver().openFileDescriptor(uri, "r");
        FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();
        Bitmap image = BitmapFactory.decodeFileDescriptor(fileDescriptor);
        parcelFileDescriptor.close();
        return image;
    }




}
