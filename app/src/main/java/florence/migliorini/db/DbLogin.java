package florence.migliorini.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DbLogin extends SQLiteOpenHelper {
    private SQLiteDatabase db;
    public DbLogin(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        this.db= this.getReadableDatabase();
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_TABLE="CREATE TABLE USER (USER_NAME VARCHAR(100)," +
                "USER_PHONE VARCHAR(100)," +
                "USER_PASSWORD VARCHAR(100))";
        sqLiteDatabase.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public Boolean login(String phone, String password){
        Cursor cursor = this.db.query("USER",new String[]{"USER_NAME","USER_PHONE","USER_PASSWORD"},
                "USER_PHONE = ? AND USER_PASSWORD = ?",new String[]{phone,password},null,null,null,null);
        cursor.moveToFirst();
        System.out.println(cursor.getCount());
        return (cursor.getCount()>0);
    }

    public Boolean signUp(String name, String phone, String password){
        ContentValues values = new ContentValues();
        values.put("USER_NAME",name);
        values.put("USER_PHONE",phone);
        values.put("USER_PASSWORD",password);
        this.db.insert("USER",null,values);
        //this.db.close();
        return login(phone,password);
    }

    public void closeDb(){
        db.close();
    }

}
