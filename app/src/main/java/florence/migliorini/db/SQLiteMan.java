package florence.migliorini.db;

public class SQLiteMan {
    private static DbLogin db= new DbLogin(null,null,null,1);

    public static Boolean login(String sEmail, String sPassword){
        return db.login(sEmail,sPassword);
    }
    public static Boolean signUp(String name, String phone, String password){
        return db.signUp(name,phone,password);
    }
}
