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
    private static DbHistory dbHistoric= new DbHistory(null,null,null,1);

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
    @RequiresApi(api = Build.VERSION_CODES.O)
    public static List<TravelDTO> getListFavoritesWithTransportType(String type){
        try{
            return dbFavorite.getAllFavoritesWithType(type);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public static void removeFavoriteById(Integer id){
        dbFavorite.removeFavorite(id);
    }

    public static void addFavorite(TravelDTO travel){
        dbFavorite.addFavorite(travel);
    }

    public static void addHistoric(TravelDTO travelDTO) {
        dbHistoric.addHistory(travelDTO);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static List<TravelDTO> getListHistoric() {
        try{
            return dbHistoric.getHistory();
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
