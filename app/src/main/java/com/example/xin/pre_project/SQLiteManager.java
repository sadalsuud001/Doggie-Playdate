package com.example.xin.pre_project;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.view.Gravity;
import android.widget.Toast;

import java.util.ArrayList;

public class SQLiteManager extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "DoggiePlaydate";
    private static final int DATABASE_VERSION = 1;
    private static int playdateCount = 0;
    private Context myContext;
    private String userName;

    public SQLiteManager(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.myContext = context;
        this.userName = "andrew";
    }

    public SQLiteManager (Context context, String userName) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.myContext = context;
        this.userName = userName;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE IF NOT EXISTS Playdates(" +
                "_id integer primary key autoincrement," +
                "date text, " +
                "latitude real, " +
                "longitude real)";

        db.execSQL(sql);

        // create dogs table
        String sql1 = "CREATE TABLE IF NOT EXISTS " + userName + "_Dogs(" +
                "name text primary key, " +
                "breed text, " +
                "gender integer, " +
                "size integer, " +
                "year integer, " +
                "month integer, " +
                "day integer, " +
                "path text)";
        db.execSQL(sql1);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int v1, int v2) {
        String sql = "DROP TABLE IF EXISTS Playdates;";
        db.execSQL(sql);
        sql = "DROP TABLE IF EXISTS " + userName + "_Dogs;";
        db.execSQL(sql);
        onCreate(db);
    }

    public void addPlaydate(Playdate pd, Context con) {


        SQLiteDatabase db = getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put("date", pd.dateToDBString());
        cv.put("latitude", pd.meetingPlace.getLatitude());
        cv.put("longitude", pd.meetingPlace.getLongitude());
        int lastIndex = (int) db.insert("Playdates", null, cv);
/*
        // get index of last row to correctly create PDx table holding users
        String query = "SELECT max(_id) from Playdates";
        Cursor c = db.rawQuery(query, null);
        c.moveToFirst();
        int index = c.getInt(c.getColumnIndexOrThrow("max(_id)"));
  */
        Toast t = Toast.makeText(con, "index of last row in playdates: " + lastIndex, Toast.LENGTH_SHORT);
        t.setGravity(Gravity.CENTER,0,0);
        t.show();

        // create table PDx to hold list of attendees
        String sql = "CREATE TABLE IF NOT EXISTS PD" + lastIndex + "(" +
                "pdId integer, " +
                "user text, " +
                "FOREIGN KEY (pdId) REFERENCES Playdates(_id), " +
                "PRIMARY KEY (pdId, user));";
        db.execSQL(sql);

        for(int x = 0; x < pd.attendees.size(); x++) {
            ContentValues cv1 = new ContentValues();
            cv1.put("pdId", lastIndex);
            cv1.put("user", pd.attendees.get(x));
            int success = (int) db.insert("PD" + lastIndex, null, cv1);
        }

    }

    public boolean deletePlaydate(int pdId) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete("Playdates", "_id="+pdId, null);
        return true;
    }

    public boolean deletePlaydateTable(int pdId) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS PD" + pdId);
        return true;
    }

    public Cursor getAllPlaydates() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor c;
        try {
            c = db.rawQuery("SELECT * FROM Playdates ORDER BY date ASC", null);
            return c;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public Cursor getPlaydatesAfter(String todaysDateDBString) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor c;
        try {
            c = db.rawQuery("SELECT * FROM Playdates WHERE date>=" + todaysDateDBString + " ORDER BY date(date) ASC", null);
            return c;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Cursor getSinglePlaydate(int pdId) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor c;
        try {
            c = db.rawQuery("SELECT * FROM PD" + pdId, null);
            return c;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public ArrayList<Dog> getAllDogs(String username) {
        ArrayList<Dog> dogs = new ArrayList<>();

        SQLiteDatabase db = getReadableDatabase();
        Cursor c;
        try {
            c = db.rawQuery("SELECT * from " + username + "_Dogs", null);
        } catch (Exception e) {
            c = null;
            e.printStackTrace();
        }
        if(c != null) {
            if (c.moveToFirst()) {
                do {
                    String name, breed, path;
                    int gender, size, year, month, day;
                    Dog d;

                    name = c.getString(c.getColumnIndexOrThrow("name"));
                    breed = c.getString(c.getColumnIndexOrThrow("name"));
                    gender = c.getInt(c.getColumnIndexOrThrow("gender"));
                    size = c.getInt(c.getColumnIndexOrThrow("size"));
                    year = c.getInt(c.getColumnIndexOrThrow("year"));
                    month = c.getInt(c.getColumnIndexOrThrow("month"));
                    day = c.getInt(c.getColumnIndexOrThrow("day"));
                    path = c.getString(c.getColumnIndexOrThrow("path"));

                    d = new Dog(name, breed, gender, size, year, month, day, path);
                    dogs.add(d);
                } while (c.moveToNext());
                c.close();
                return dogs;
            }
        }
        return dogs;
    }

    public long addDog(String userName, Dog newDog) {
        ContentValues cv1 = new ContentValues();
        cv1.put("name", newDog.name);
        cv1.put("breed", newDog.breed);
        cv1.put("gender", newDog.gender);
        cv1.put("size", newDog.size);
        cv1.put("year", newDog.bdayYear);
        cv1.put("month", newDog.bdayMonth);
        cv1.put("day", newDog.bdayDay);
        cv1.put("path", newDog.profilePicPath);
        int val;

        SQLiteDatabase db = getWritableDatabase();
        try {
            String sql = "CREATE TABLE IF NOT EXISTS " + userName + "_Dogs(" +
                    "name text primary key, " +
                    "breed text, " +
                    "gender integer, " +
                    "size integer, " +
                    "year integer, " +
                    "month integer, " +
                    "day integer, " +
                    "path text)";
            db.execSQL(sql);
            db.beginTransaction();
            val = (int)db.insert(userName + "_Dogs", null, cv1);
            db.setTransactionSuccessful();
        }
        finally {
            db.endTransaction();
        }
        return val;
    }

    public void removeDog(String userName, String dogName) {
        SQLiteDatabase db = getWritableDatabase();
        switch(db.delete(userName + "_Dogs", "name=\"" + dogName + "\"", null)) {
            case 0: Toast t = Toast.makeText(myContext, "Error: dog not found", Toast.LENGTH_SHORT);
                t.setGravity(Gravity.CENTER, 0 ,0);
                t.show();
                break;
            default: Toast t1 = Toast.makeText(myContext, dogName + " removed from Your Dogs", Toast.LENGTH_SHORT);
                t1.setGravity(Gravity.CENTER, 0 ,0);
                t1.show();
                break;
        }
    }
}
