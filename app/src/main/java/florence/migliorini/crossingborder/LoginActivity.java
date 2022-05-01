package florence.migliorini.crossingborder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.identity.SignInCredential;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.zaag;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import florence.migliorini.db.DbLogin;
import florence.migliorini.db.SQLiteMan;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private EditText etEmail,etPasword;
   //private static final String TAG = "EmailPassword";
    private GoogleSignInClient mGoogleSignInClient;
    private static final int REQ_ONE_TAP = 2;  // Can be any integer unique to the Activity.
    private boolean showOneTapUI = true;
    private static final String TAG = "SignInActivity";
    private static final int RC_SIGN_IN = 9001;
    private Button bLogin;
    GoogleApiClient mGoogleApiClient;
    private TextView statusTextView,errorMsgTextView;
    Button btn_SignOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        bLogin = findViewById(R.id.btn_Login);
        Button bSignup = findViewById(R.id.btn_signup);
        Button bInfo = findViewById(R.id.button4);
        etEmail = findViewById(R.id.editTextTextEmailAddress2);
        etPasword = findViewById(R.id.editTextTextPassword2);
        statusTextView = findViewById(R.id.textView);
        errorMsgTextView = findViewById(R.id.alertTextLogin);
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        configureGoogleSignInstance();

        bSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        mAuth = FirebaseAuth.getInstance();

        configLoginWithFirebaseAuth();
        bInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if (user != null) {
                    // Name, email address, and profile photo Url
                    String name = user.getDisplayName();
                    String email = user.getEmail();
                    Uri photoUrl = user.getPhotoUrl();

                    // Check if user's email is verified
                    boolean emailVerified = user.isEmailVerified();

                    // The user's ID, unique to the Firebase project. Do NOT use this value to
                    // authenticate with your backend server, if you have one. Use
                    // FirebaseUser.getIdToken() instead.
                    String uid = user.getUid();
                    Toast.makeText(getApplicationContext(), "User Details: "+user.toString(), Toast.LENGTH_SHORT).show();
                    startHome();
                }
            }
        });

        findViewById(R.id.sign_in_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn();
            }
        });
    }
    //Inicializa uma instancia de GoogleSignInClient
    /**
    * Ao clicar no botão de login com google, é iniciada uma INTENT
    * criada previamente pelo google e obtida no método signIn()
    * **/
    private void configureGoogleSignInstance() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
               //.requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
    }
    //Inicia a intent do google.
    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }
    //Inicia uma intent para a pagina inicial.
    private void startHome() {
        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
        startActivity(intent);
        finish();
    }
    //Configura o botão de login com firebase auth
    private void configLoginWithFirebaseAuth(){
        final LoginActivity activity= this;
        bLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                errorMsgTextView.setText("");
                mAuth.signInWithEmailAndPassword(etEmail.getText().toString(), etPasword.getText().toString())
                        .addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    SQLiteMan.getInstance(getApplicationContext(),"database")
                                            .setUserConnected(etEmail.getText().toString());
                                    startHome();
                                } else {
                                    errorMsgTextView.setText("Confira o email e a senha e tente novamente");
                                }
                            }
                        });
            }
        });
    }
}