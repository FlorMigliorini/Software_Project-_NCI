package florence.migliorini.crossingborder;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.transition.Explode;
import android.transition.Fade;
import android.transition.Slide;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.auth.FirebaseAuth;

import florence.migliorini.db.SQLiteMan;

public class MenuActivity extends AppCompatActivity {
    private Button buttonClose,buttonFav,buttonLoc,buttonHistory,buttonHome,bLogout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().requestFeature(Window.FEATURE_ACTIVITY_TRANSITIONS);
        getWindow().setExitTransition(new Slide());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        buttonClose = findViewById(R.id.buttonHideMenu);
        buttonFav = findViewById(R.id.btnFavTrips);
        buttonLoc = findViewById(R.id.btnRoutes);
        buttonHistory = findViewById(R.id.btnHistory);
        buttonHome = findViewById(R.id.btnGetTicket);
        bLogout = findViewById(R.id.btnLogout);
        configureButtons();
    }
    //Configura os botões e suas ações
    public void configureButtons(){
        buttonClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenuActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
            }
        });
        buttonFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenuActivity.this, FavoriteActivity.class);
                animTransition(intent);
                finish();
            }
        });
        buttonLoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenuActivity.this, RouteActivity.class);
                startActivity(intent);
                finish();
            }
        });
        buttonHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenuActivity.this, HistoryActivity.class);
                animTransition(intent);
                finish();
            }
        });
        buttonHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenuActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
            }
        });

        bLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                SQLiteMan.getInstance(getApplicationContext(),"database").logoutUser();
                startActivity(intent);
            }
        });
    }
    //Inicia uma intent para a tela home
    public void closeMenu(View view){
        Intent intent = new Intent(MenuActivity.this, HomeActivity.class);
        startActivity(intent);
        finish();
    }
    public void animTransition(Intent in){
        startActivity(in, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
    }
}