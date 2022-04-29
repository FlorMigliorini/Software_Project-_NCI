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
    private DbLogin dbLogin;
    private DbFavorite dbFavorite;
    private DbHistory dbHistoric;

    /*public static Boolean login(String sEmail){
        Boolean bool =dbLogin.login(sEmail);
        return bool;
    }
    public static Boolean signUp(String name, String phone, String password){
        Boolean bool =dbLogin.signUp(name,phone,password);
        return bool;
    }*/
    public void setUserConnected(String email){
        dbLogin.setUserConnected(email);
    }
    public void logoutUser(){
        dbLogin.logoutDb(getUserConnected());
    }
    public String getUserConnected(){
        return dbLogin.getUserConnected();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public List<TravelDTO> getListFavorites() throws ParseException {
        List<TravelDTO> list = dbFavorite.getAllFavorites(getUserConnected());
        return list;
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public List<TravelDTO> getListFavoritesWithTransportType(String type){
        try{
            List<TravelDTO> list = dbFavorite.getAllFavoritesWithType(type,getUserConnected());
            return list;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public void removeFavoriteById(Integer id){
        dbFavorite.removeFavorite(id,getUserConnected());
    }

    public void addFavorite(TravelDTO travel){
        dbFavorite.addFavorite(travel,getUserConnected());
    }

    public void addHistoric(TravelDTO travelDTO) {
        dbHistoric.addHistory(travelDTO,getUserConnected());
    }
    public void removeHistoricById(Integer id){
        dbHistoric.removeHistory(id,getUserConnected());
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public List<TravelDTO> getListHistoric() {
        try{
            List<TravelDTO> list = dbHistoric.getHistory(getUserConnected());
            return list;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
