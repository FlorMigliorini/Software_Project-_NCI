package florence.migliorini.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import florence.migliorini.model.Favorite;
import florence.migliorini.model.TravelDTO;

public class DbHistory extends SQLiteOpenHelper {
    protected static DbHistory INSTANCE;

    public static DbHistory getInstance(Context context,String name, @Nullable SQLiteDatabase.CursorFactory factory, int version){
        if(INSTANCE==null){
            INSTANCE =new DbHistory(context,name,factory,version);
            return INSTANCE;
        }else{
            return INSTANCE;
        }
    }

    protected DbHistory(Context context, String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_TABLE="CREATE TABLE TB_HISTORY (" +
                "ID_HISTORY INTEGER PRIMARY KEY AUTOINCREMENT," +
                "DS_LOCATION VARCHAR(100)," +
                "CD_TRANSPORT INTEGER," +
                "DS_DISTINY VARCHAR(100)," +
                "DT_TIME VARCHAR(100))";
        sqLiteDatabase.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS TB_HISTORY");
        onCreate(sqLiteDatabase);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public List<TravelDTO> getHistory(){
        List<TravelDTO> list = new ArrayList<>();
        Cursor cursor = this.getReadableDatabase().rawQuery("SELECT * FROM TB_HISTORY",null);
        TravelDTO travel = null;
        if(cursor.moveToFirst()) {
            do {
                travel = new TravelDTO(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(3),
                        LocalDate.parse(cursor.getString(4)),
                        cursor.getInt(2),
                        null,
                        null,
                        null,null,null,null,null
                );
                list.add(travel);
            } while (cursor.moveToNext());
        }
        this.getReadableDatabase().close();
        return list;
    }

    public void addHistory(TravelDTO travel){
        ContentValues values = new ContentValues();
        values.put("DS_LOCATION",travel.getLocation());
        values.put("CD_TRANSPORT",travel.getCdTransport());
        values.put("DS_DISTINY",travel.getDestiny());
        values.put("DT_TIME",travel.getDtInitial().toString());
        this.getReadableDatabase().insert("TB_HISTORY",null,values);
        this.getReadableDatabase().close();
    }

    public void removeHistory(Integer id){
        this.getReadableDatabase().execSQL("DELETE FROM TB_HISTORY WHERE ID_HISTORY = "+id);
    }

    public void closeDb(){
        this.getReadableDatabase().close();
    }
    public void beginTransaction(){this.getReadableDatabase().beginTransaction();}
}
