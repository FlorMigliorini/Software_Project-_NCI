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
        String CREATE_TABLE="CREATE TABLE USER (USER_NAME VARCHAR(100)," +
                "USER_PHONE VARCHAR(100)," +
                "USER_PASSWORD VARCHAR(100))";
        sqLiteDatabase.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS USER");
        onCreate(sqLiteDatabase);
    }

    public Boolean login(String phone, String password){
        Cursor cursor = this.getReadableDatabase().query("USER",new String[]{"USER_NAME","USER_PHONE","USER_PASSWORD"},
                "USER_PHONE = ? AND USER_PASSWORD = ?",new String[]{phone,password},null,null,null,null);
        cursor.moveToFirst();
        Boolean bol = ((cursor.getCount()>0));
        this.getReadableDatabase().close();
        return bol;
    }

    public Boolean signUp(String name, String phone, String password){
        ContentValues values = new ContentValues();
        values.put("USER_NAME",name);
        values.put("USER_PHONE",phone);
        values.put("USER_PASSWORD",password);
        this.getReadableDatabase().insert("USER",null,values);
        //this.db.close();
        Boolean bol = login(phone,password);
        this.getReadableDatabase().close();
        return bol;
    }

    public void closeDb(){
        this.getReadableDatabase().close();
    }
    public void beginTransaction(){this.getReadableDatabase().beginTransaction();}
}
