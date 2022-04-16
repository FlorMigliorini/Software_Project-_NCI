package florence.migliorini.db;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.text.ParseException;
import java.util.List;

import florence.migliorini.model.Favorite;
import florence.migliorini.model.TravelDTO;

public class SQLiteMan {
    private static DbLogin dbLogin= new DbLogin(null,null,null,1);
    private static DbFavorite dbFavorite= new DbFavorite(null,null,null,1);

    public static Boolean login(String sEmail, String sPassword){
        return dbLogin.login(sEmail,sPassword);
    }
    public static Boolean signUp(String name, String phone, String password){
        return dbLogin.signUp(name,phone,password);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static List<TravelDTO> getListFavorites() throws ParseException {
        return dbFavorite.getAllFavorites();
    }

    public static void removeItemList(Integer id){
        dbFavorite.removeFavorite(id);
    }

    public static void addFavorite(TravelDTO travel){
        dbFavorite.addFavorite(travel);
    }
}
