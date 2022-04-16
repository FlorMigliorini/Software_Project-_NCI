package florence.migliorini.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.LinkedList;
import java.util.List;

import florence.migliorini.model.Favorite;

public class DbHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "DBFavorite";
    private static final String TABLE_NAME = "FavoritesTable";

    private static final String KEY_ID = "id";
    private static final String KEY_LOCATION = "location";
    private static final String KEY_DESTINATION = "destination";

    private SQLiteDatabase db;

    private static final String[] COLUMNS = {KEY_ID, KEY_LOCATION, KEY_DESTINATION};

    public DbHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }
    public DbHelper (Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME
                + " ( " + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + KEY_LOCATION + " TEXT, "
                + KEY_DESTINATION + " TEXT )";
        sqLiteDatabase.execSQL(CREATE_TABLE);
        db = sqLiteDatabase;
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        String DROP_TABLE_IF_EXISTS = "DROP TABLE IF EXISTS " + TABLE_NAME;
        sqLiteDatabase.execSQL(DROP_TABLE_IF_EXISTS);
        db = sqLiteDatabase;
        this.onCreate(sqLiteDatabase);
    }

    public void addFavorite(Favorite favorite) {
        db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_LOCATION, favorite.getLocation());
        contentValues.put(KEY_DESTINATION, favorite.getDestination());
        db.insert(TABLE_NAME, null, contentValues);
        db.close();
    }

    public Favorite getFavorite(int id) {
        db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME,
                COLUMNS,
                " id=?",
                new String[]{String.valueOf(id)},
                null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        } else {
            return null;
        }
        Favorite favorite = new Favorite();
        favorite.setId(id);
        favorite.setLocation(cursor.getString(1));
        favorite.setDestination(cursor.getString(2));
        db.close();
        return favorite;
    }
    public List<Favorite> getAllFavorites() {
        List<Favorite> plants = new LinkedList<Favorite>();
        String getAllCarsStatement = "SELECT * FROM "+TABLE_NAME;
        db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(getAllCarsStatement,null);
        Favorite favorite = null;
        if(cursor.moveToFirst()) {
            do {
                favorite = new Favorite();
                favorite.setId(Integer.parseInt(cursor.getString(0)));
                favorite.setLocation(cursor.getString(1));
                favorite.setDestination(cursor.getString(2));
                plants.add(favorite);
            } while (cursor.moveToNext());
        }
        db.close();
        return plants;
    }

    public int updateFavorite(Favorite favorite) {
        int id = favorite.getId();
        db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_LOCATION, favorite.getLocation());
        contentValues.put(KEY_DESTINATION, favorite.getDestination());
        int iNumberOfRowsUpdated = db.update(TABLE_NAME,
                contentValues,
                KEY_ID+"=?",
                new String[] {String.valueOf(id)});
        db.close();
        return iNumberOfRowsUpdated;
    }

    public int deleteFavorite(Favorite favorite) {
        int id = favorite.getId();
        db = this.getWritableDatabase();
        int iNumberOfRowsDeleted = db.delete(TABLE_NAME,
                KEY_ID+"=?",
                new String[] {String.valueOf(id)});
        db.close();
        return iNumberOfRowsDeleted;
    }

    public void deleteAllFavorites() {
        String DELETE_ALL_FAVORITES_STATEMENT = "DELETE FROM "+TABLE_NAME;
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(DELETE_ALL_FAVORITES_STATEMENT);
    }
}
