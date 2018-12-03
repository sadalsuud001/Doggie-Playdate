package com.example.xin.pre_project;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.Gravity;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class ImageManager {

    private Context context;

    public ImageManager(Context context) {
        this.context = context;
    }

    // overloaded for User Profile pic
    public Bitmap loadFromStorage(String path) {
        try {
            ContextWrapper cw = new ContextWrapper(HomeActivity.gContext);
            // path to /data/user/0/com.example.xin.pre_project/app_imageDir
            File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
            File f=new File(directory, "profile.jpg");
            Bitmap b = BitmapFactory.decodeStream(new FileInputStream(f));
            return b;
        }
        catch (FileNotFoundException e)
        {
            Toast t = Toast.makeText(context, "Error - file not found", Toast.LENGTH_LONG);
            t.setGravity(Gravity.CENTER,0,0);
            t.show();
            e.printStackTrace();
            return null;
        }
    }

    // overloaded for User Profile pic
    public String saveToInternalStorage(Bitmap bitmapImage){
        ContextWrapper cw = new ContextWrapper(HomeActivity.gContext);
        // path to /data/user/0/com.example.xin.pre_project/app_imageDir
        File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
        // Create imageDir
        File mypath = new File(directory, "profile.jpg");
        String picPath = mypath.getParentFile().getPath();

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(mypath);
            // Use the compress method on the BitMap object to write image to the OutputStream
            bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fos);
            Toast.makeText(context, "Profile Pic saved", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return directory.getAbsolutePath();
    }

    // overloaded for Dog profile pics
    public Bitmap loadFromStorage(String path, String dogName) {
        String fileName = "a_" + dogName + ".jpg";
        try {
            File f=new File(path, fileName);
            Bitmap b = BitmapFactory.decodeStream(new FileInputStream(f));

            return b;
        } catch (FileNotFoundException e) {
            Toast t = Toast.makeText(context, "Error - file not found", Toast.LENGTH_LONG);
            t.setGravity(Gravity.CENTER, 0, 0);
            t.show();
            e.printStackTrace();
            return null;
        }

    }

    // overloaded for Dog profile pic
    public String saveToInternalStorage(Bitmap bitmapImage, String dogName){
        ContextWrapper cw = new ContextWrapper(HomeActivity.gContext);
        File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);

        String filename = "a_" + dogName + ".jpg";
        File mypath=new File(directory,filename);

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(mypath);
            // Use the compress method on the BitMap object to write image to the OutputStream
            bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fos);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Log.d("PICPATH-IM", directory.getAbsolutePath());
        return directory.getAbsolutePath();
    }
}
