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
    protected static DbFavorite INSTANCE;

    public static DbFavorite getInstance(Context context, String name, @Nullable SQLiteDatabase.CursorFactory factory, int version){
        if(INSTANCE==null){
            INSTANCE =new DbFavorite(context,name,factory,version);
            return INSTANCE;
        }else{
            return INSTANCE;
        }
    }
    protected DbFavorite(Context context,String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_TABLE="CREATE TABLE TB_FAVORITE (" +
                "ID_FAVORITE INTEGER PRIMARY KEY AUTOINCREMENT," +
                "DS_LOCATION VARCHAR(100)," +
                "CD_TRANSPORT INTEGER," +
                "DS_DISTINY VARCHAR(100)," +
                "DT_TIME VARCHAR(100)," +
                "NUM_VALUE INTEGER," +
                "DT_DURATION VARCHAR(100)," +
                "DS_TITLE_TICKET VARCHAR(100)," +
                "DT_HOUR_DEPARTURE VARCHAR(100)," +
                "DT_HOUR_TRAVEL VARCHAR(100)," +
                "NUM_PASSENGERS INTEGER)";
        sqLiteDatabase.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS TB_FAVORITE");
        onCreate(sqLiteDatabase);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public List<TravelDTO> getAllFavorites() throws ParseException {
        List<TravelDTO> list = new ArrayList<>();
        Cursor cursor = this.getReadableDatabase().rawQuery("SELECT * FROM TB_FAVORITE",null);
        TravelDTO travel = null;
        if(cursor.moveToFirst()) {
            do {
                travel = new TravelDTO(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(3),
                        null,
                        cursor.getInt(2),
                        cursor.getString(4),
                        cursor.getInt(5),
                        cursor.getString(6),
                        cursor.getString(7),
                        cursor.getString(8),
                        cursor.getString(9),
                        cursor.getInt(10)
                );
                list.add(travel);
            } while (cursor.moveToNext());
        }
        this.getReadableDatabase().close();
        return list;
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public List<TravelDTO> getAllFavoritesWithType(String type) throws ParseException {
        List<TravelDTO> list = new ArrayList<>();
        Cursor cursor = this.getReadableDatabase().query("TB_FAVORITE",
                new String[]{"ID_FAVORITE","DS_LOCATION","CD_TRANSPORT","DS_DISTINY","DT_TIME",
                "NUM_VALUE","DT_DURATION","DS_TITLE_TICKET","DT_HOUR_DEPARTURE","DT_HOUR_TRAVEL","NUM_PASSENGERS"},
                "CD_TRANSPORT = ?",new String[]{type},null,null,null,null);
        TravelDTO travel = null;
        if(cursor.moveToFirst()) {
            do {
                travel = new TravelDTO(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(3),
                        null,
                        cursor.getInt(2),
                        cursor.getString(4),
                        cursor.getInt(5),
                        cursor.getString(6),
                        cursor.getString(7),
                        cursor.getString(8),
                        cursor.getString(9),
                        cursor.getInt(10)
                );
                list.add(travel);
            } while (cursor.moveToNext());
        }
        this.getReadableDatabase().close();
        return list;
    }
    public void addFavorite(TravelDTO travel){
        ContentValues values = new ContentValues();
        values.put("DS_LOCATION",travel.getLocation());
        values.put("CD_TRANSPORT",travel.getCdTransport());
        values.put("DS_DISTINY",travel.getDestiny());
        values.put("DT_TIME",travel.getDtInitial().toString());
        values.put("NUM_VALUE",travel.getValue().toString());
        values.put("DT_DURATION",travel.getDtDuration().toString());
        values.put("DS_TITLE_TICKET",travel.getDsTitleTicket().toString());
        values.put("DT_HOUR_DEPARTURE",travel.getDtHourDeparture().toString());
        values.put("DT_HOUR_TRAVEL",travel.getDtHourTravel().toString());
        values.put("NUM_PASSENGERS",travel.getNumPassengers().toString());
        this.getReadableDatabase().insert("TB_FAVORITE",null,values);
        this.getReadableDatabase().close();
    }

    public void removeFavorite(Integer id){
        this.getReadableDatabase().execSQL("DELETE FROM TB_FAVORITE WHERE ID_FAVORITE = "+id);
        this.getReadableDatabase().close();
    }

    public void closeDb(){
        this.getReadableDatabase().close();
    }
    public void beginTransaction(){this.getReadableDatabase().beginTransaction();}
    public void endTransaction(){this.getReadableDatabase().endTransaction();}
    public void successfulTransaction(){
        this.getReadableDatabase().setTransactionSuccessful();
    }
}
