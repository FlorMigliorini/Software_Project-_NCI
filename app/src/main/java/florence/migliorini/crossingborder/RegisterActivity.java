package florence.migliorini.crossingborder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

import florence.migliorini.db.DbLogin;
import florence.migliorini.db.SQLiteMan;

public class RegisterActivity extends AppCompatActivity {

    private Button btnCreateAcc;
    private EditText etInputName, etPhoneNo, etInputPassword;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        btnCreateAcc = (Button) findViewById(R.id.btn_create_acc);
        etInputName = (EditText) findViewById(R.id.register_name);
        etPhoneNo = (EditText) findViewById(R.id.register_phone);
        etInputPassword = (EditText) findViewById(R.id.register_password);
        progressDialog = new ProgressDialog(this);

        /*btnCreateAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CreateAccount();
            }
        });*/
    }

    /*private void CreateAccount() {
        //getting inputs
        String name = etInputName.getText().toString();
        String phone = etPhoneNo.getText().toString();
        String password = etInputPassword.getText().toString();
        //checking the field is empty
        if (TextUtils.isEmpty(name)){
            Toast.makeText(this, "Please write your name", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(phone)){
            Toast.makeText(this, "Please write your phone number", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(password)){
            Toast.makeText(this, "Please write your password", Toast.LENGTH_SHORT).show();
        }
        else{
            //Display msg
            progressDialog.setTitle("Create Account");
            progressDialog.setMessage("Please wait, while we are checking the credentials");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();

            Boolean bol = ValidatephoneNo(name, phone, password);
            if(bol) {
                Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                startActivity(intent);
            }else {
                //Mensagem de erro
            }
        }
    }*/
    /*private Boolean ValidatephoneNo(String name,final String phone, final String password) {
        Boolean bol = SQLiteMan.signUp(name,phone,password);
        return bol;

    }*/
//    private void ValidatephoneNo(String name, final String phone, final String password) {
//        String urlFirebase = "https://bikecommerce-e9050-default-rtdb.firebaseio.com/"; //need to change it for the crossingborder project
//        //connect database
//        final DatabaseReference databaseReference;
//        databaseReference = FirebaseDatabase.getInstance(urlFirebase).getReference();
//
//        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot)
//            {
//                if (!(snapshot.child("Users").child(phone).exists()))
//                {
//                    HashMap<String, Object> userDataMap = new HashMap<>();
//                    userDataMap.put("name", name);
//                    userDataMap.put("phone", phone);
//                    userDataMap.put("password", password);
//
//                    databaseReference.child("Users").child(phone).updateChildren(userDataMap)
//                            .addOnCompleteListener(new OnCompleteListener<Void>() {
//                                @Override
//                                public void onComplete(@NonNull Task<Void> task) {
//                                    if (task.isSuccessful()){
//                                        Toast.makeText(RegisterActivity.this, "Account created successfully.", Toast.LENGTH_SHORT).show();
//                                        progressDialog.dismiss();
//
//                                        Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
//                                        startActivity(intent);
//                                    }
//                                    else {
//                                        progressDialog.dismiss();
//                                        Toast.makeText(RegisterActivity.this, "Error, please try again.", Toast.LENGTH_SHORT).show();
//                                    }
//                                }
//                            });
//                }
//                else
//                {
//                    Toast.makeText(RegisterActivity.this, "This " + phone + " already exist.", Toast.LENGTH_SHORT).show();
//                    progressDialog.dismiss();
//
//                    Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
//                    startActivity(intent);
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
}
