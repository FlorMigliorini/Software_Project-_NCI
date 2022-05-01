package florence.migliorini.crossingborder;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Locale;

import florence.migliorini.db.SQLiteMan;

public class RegisterActivity extends AppCompatActivity {

    private Button btnCreateAcc;
    private EditText etEmail,etConfirmEmail,etPassword,etConfirmPassword;
    private ProgressDialog progressDialog;
    private TextView alertMail,alertPassword;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        etEmail = findViewById(R.id.email);
        etConfirmEmail = findViewById(R.id.confirmMail);
        etPassword = findViewById(R.id.password);
        etConfirmEmail = findViewById(R.id.confirmPassword);
        btnCreateAcc = (Button) findViewById(R.id.btn_create_acc);
        alertMail = findViewById(R.id.alertEmailRegister);
        alertPassword = findViewById(R.id.alertPasswordRegister);
        progressDialog = new ProgressDialog(this);
        mAuth = FirebaseAuth.getInstance();
    }
    //Ação createAccount
    public void createAccountAction(View view){
        alertMail.setText("");
        alertPassword.setText("");
        if(validateMail()){
            if(validatePassword()){
                registerWithFirebaseAuth(etEmail.getText().toString(),
                        etPassword.getText().toString());
            }
        }
    }
    //Registra uma nova conta com firebase auth
    public void registerWithFirebaseAuth(String email, String password){
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            SQLiteMan.getInstance(getApplicationContext(),"database")
                                    .setUserConnected(etEmail.getText().toString());
                            startHome();
                        } else {
                            Toast.makeText(RegisterActivity.this, "Falha ao cadastrar usuario.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
    //Validação de email
    public Boolean validateMail(){
        String email = etEmail.getText().toString().trim().toLowerCase(Locale.ROOT);
        String confirmEmail=etConfirmEmail.getText().toString().trim().toLowerCase(Locale.ROOT);
        if(!email.equals(confirmEmail)){
            alertMail.setText("Os e-mails digitados não correspondem");
            return false;
        }
        if(!etEmail.getText().toString().matches("(\\w|[_.])+@(\\w)([.](\\w))+")
                ||!etConfirmEmail.getText().toString().matches("(\\w|[_.])+@(\\w)([.](\\w))+")){
            alertMail.setText("O formato do email deve conter um domínio e não permite caracteres especiais diferentes de .,_");
            return false;
        }
        return true;
    }
    //Validação de senha
    public Boolean validatePassword(){
        String email = etPassword.getText().toString().trim().toLowerCase(Locale.ROOT);
        String confirmEmail=etConfirmPassword.getText().toString().trim().toLowerCase(Locale.ROOT);
        if(!email.equals(confirmEmail)){
            alertPassword.setText("As senhas digitadas não correspondem");
            return false;
        }
        if(!etEmail.getText().toString().matches("(\\w|[_.])+@(\\w)([.](\\w))+")
                ||!etConfirmEmail.getText().toString().matches("(\\w|[_.])+@(\\w)([.](\\w))+")){
            alertPassword.setText("A sua senha deve conter pelo menos uma letra maiuscula e números");
            return false;
        }
        return true;
    }
    //Inicia uma intent para a tela home
    private void startHome() {
        Intent intent = new Intent(RegisterActivity.this, HomeActivity.class);
        startActivity(intent);
        finish();
    }
}
