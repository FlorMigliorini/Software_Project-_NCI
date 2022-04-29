package florence.migliorini.db;

import android.content.Context;
import android.os.Build;

import androidx.annotation.RequiresApi;

import java.text.ParseException;
import java.util.List;

import florence.migliorini.model.Favorite;
import florence.migliorini.model.TravelDTO;

public class SQLiteMan {
    protected static SQLiteMan INSTANCE;
    public static SQLiteMan getInstance(Context context,String databaseTag){
        if(INSTANCE==null){
            INSTANCE = new SQLiteMan(context,databaseTag);
            return INSTANCE;
        }else{
            return INSTANCE;
        }
    }
    protected SQLiteMan(Context context, String databaseTag){
        dbLogin = DbLogin.getInstance(context,databaseTag+"1",null,1);
        dbFavorite = DbFavorite.getInstance(context,databaseTag+"2",null,1);
        dbHistoric = DbHistory.getInstance(context,databaseTag+"3",null,1);
    }
    private static DbLogin dbLogin;
    private static DbFavorite dbFavorite;
    private static DbHistory dbHistoric;

    public static Boolean login(String sEmail, String sPassword){
        Boolean bool =dbLogin.login(sEmail,sPassword);
        return bool;
    }
    public static Boolean signUp(String name, String phone, String password){
        Boolean bool =dbLogin.signUp(name,phone,password);
        return bool;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public List<TravelDTO> getListFavorites() throws ParseException {
        List<TravelDTO> list = dbFavorite.getAllFavorites();
        return list;
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public List<TravelDTO> getListFavoritesWithTransportType(String type){
        try{
            List<TravelDTO> list = dbFavorite.getAllFavoritesWithType(type);
            return list;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public void removeFavoriteById(Integer id){
        dbFavorite.removeFavorite(id);
    }

    public void addFavorite(TravelDTO travel){
        dbFavorite.addFavorite(travel);
    }

    public static void addHistoric(TravelDTO travelDTO) {
        dbHistoric.addHistory(travelDTO);
    }
    public void removeHistoricById(Integer id){
        dbHistoric.removeHistory(id);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static List<TravelDTO> getListHistoric() {
        try{
            List<TravelDTO> list = dbHistoric.getHistory();
            return list;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
