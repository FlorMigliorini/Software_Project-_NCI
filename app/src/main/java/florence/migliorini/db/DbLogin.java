package florence.migliorini.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DbLogin extends SQLiteOpenHelper {
    protected static DbLogin INSTANCE;

    public static DbLogin getInstance(Context context,String name, @Nullable SQLiteDatabase.CursorFactory factory, int version){
        if(INSTANCE==null){
            INSTANCE =new DbLogin(context,name,factory,version);
            return INSTANCE;
        }else{
            return INSTANCE;
        }
    }

    protected DbLogin(Context context,String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_TABLE="CREATE TABLE USER (EMAIL VARCHAR(100))";
        sqLiteDatabase.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS USER");
        onCreate(sqLiteDatabase);
    }

    public Boolean login(String email){
        Cursor cursor = this.getReadableDatabase().query("USER",new String[]{"EMAIL"},
                "EMAIL = ?",new String[]{email},null,null,null,null);
        cursor.moveToFirst();
        Boolean bol = ((cursor.getCount()>0));
        this.getReadableDatabase().close();
        return bol;
    }

    public String getUserConnected(){
        Cursor cursor = this.getReadableDatabase().rawQuery("SELECT * FROM USER",null);
        cursor.moveToFirst();
        String email = cursor.getString(0);
        this.getReadableDatabase().close();
        return email;
    }
    public void setUserConnected(String email){
        ContentValues values = new ContentValues();
        values.put("EMAIL",email);
        this.getReadableDatabase().insert("USER",null,values);
        this.getReadableDatabase().close();
    }
    public void logoutDb(String email){
        this.getReadableDatabase().delete("USER","EMAIL = ?",new String[]{email});
    }
    /*public Boolean signUp(String name, String phone, String password){
        ContentValues values = new ContentValues();
        values.put("USER_NAME",name);
        values.put("USER_PHONE",phone);
        values.put("USER_PASSWORD",password);
        this.getReadableDatabase().insert("USER",null,values);
        //this.db.close();
        Boolean bol = login(phone,password);
        this.getReadableDatabase().close();
        return bol;
    }*/

    public void closeDb(){
        this.getReadableDatabase().close();
    }
    public void beginTransaction(){this.getReadableDatabase().beginTransaction();}
}
