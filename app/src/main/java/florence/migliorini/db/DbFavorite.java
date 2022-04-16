package florence.migliorini.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import florence.migliorini.model.TravelDTO;

public class DbFavorite extends SQLiteOpenHelper {
    private SQLiteDatabase db;
    public DbFavorite(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        this.db= this.getReadableDatabase();
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_TABLE="CREATE TABLE TB_FAVORITE (" +
                "ID_FAVORITE INTEGER PRIMARY KEY AUTOINCREMENT," +
                "DS_LOCATION VARCHAR(100)," +
                "CD_TRANSPORT INTEGER," +
                "DS_DISTINY VARCHAR(100)," +
                "DT_TIME VARCHAR(100))";
        sqLiteDatabase.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public List<TravelDTO> getAllFavorites() throws ParseException {
        List<TravelDTO> list = new ArrayList<>();
        Cursor cursor = this.db.rawQuery("SELECT * FROM TB_FAVORITE",null);
        TravelDTO travel = null;
        if(cursor.moveToFirst()) {
            do {
                travel = new TravelDTO(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(3),
                        null,
                        cursor.getInt(2),
                        null,
                        null
                );
                list.add(travel);
            } while (cursor.moveToNext());
        }
        return list;
    }

    public void addFavorite(TravelDTO travel){
        ContentValues values = new ContentValues();
        values.put("DS_LOCATION",travel.getLocation());
        values.put("CD_TRANSPORT",travel.getCdTransport());
        values.put("DS_DISTINY",travel.getDestiny());
        values.put("DT_TIME",travel.getDtInitial().toString());
        this.db.insert("TB_FAVORITE",null,values);
    }

    public void removeFavorite(Integer id){
        db.execSQL("DELETE FROM TB_FAVORITE WHERE ID_FAVORITE = "+id);
    }

    public void closeDb(){
        db.close();
    }
}
