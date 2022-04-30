package florence.migliorini.crossingborder;


import androidx.appcompat.app.AppCompatActivity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

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


}
