package id.hisyam.travblog;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {

    private EditText mNameField;
    private EditText mEmailField;
    private EditText mPasswordField;
    private EditText mRePasswordField;

    private Button mRegisterButton;

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    private ProgressDialog mProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Users");


        mProgress = new ProgressDialog(this);

        mEmailField = (EditText) findViewById(R.id.emailField);
        mPasswordField = (EditText) findViewById(R.id.passwordField);
        mRePasswordField = (EditText) findViewById(R.id.repasswordField);
        mRegisterButton = (Button) findViewById(R.id.registerBtn);

        mRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startRegister();
            }
        });
    }

    private void startRegister() {
        String email = mEmailField.getText().toString().trim();
        String password = mPasswordField.getText().toString().trim();
        String repassword = mRePasswordField.getText().toString().trim();

        if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password) && !TextUtils.isEmpty(repassword)) {

            mProgress.setMessage("Signing up ...");
            mProgress.show();

            if (!password.equals(repassword)) {
                mProgress.dismiss();
                Toast.makeText(RegisterActivity.this, "Password Tidak Match", Toast.LENGTH_LONG).show();
                return;
            }

            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        //String user_id = mAuth.getCurrentUser().getUid();
                        //DatabaseReference current_user_db = mDatabase.child(user_id);

                        //current_user_db.child("name").setValue(name);
                        //current_user_db.child("image").setValue("default");

                        mProgress.dismiss();

                        Intent mainIntent = new Intent(RegisterActivity.this, MainActivity.class);
                        mainIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(mainIntent);
                    }
                }
            });
        }
    }
}
