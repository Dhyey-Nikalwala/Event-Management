package com.example.eventmanagement;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class SignUpActivity extends AppCompatActivity {
    private EditText emailEt,passwordEt,passwordEt2;
    private Button SignUpButton;
    private TextView SignInTv;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);
        firebaseAuth=FirebaseAuth.getInstance();
        emailEt=findViewById(R.id.email);
        passwordEt=findViewById(R.id.password);
        passwordEt2=findViewById(R.id.password2);
        SignUpButton=findViewById(R.id.register);
        progressDialog=new ProgressDialog(this);
        SignInTv=findViewById(R.id.signInTv);
        SignUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Register();
            }
        });
        SignInTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(SignUpActivity.this, MainActivity.class);
                startActivity(intent);
                finish();

            }
        });
    }
    private void Register(){
        String email=emailEt.getText().toString();
        String password=passwordEt.getText().toString();
        String password2=passwordEt2.getText().toString();
        if(TextUtils.isEmpty(email)){
            emailEt.setError("Enter Your Email");
            return;
        }
        else if(TextUtils.isEmpty(password)){
            passwordEt.setError("Enter Your password");
            return;
        }
        else if(TextUtils.isEmpty(password2)){
            passwordEt2.setError("Confirm Your password");
            return;
        }
        else if(!password.equals(password2)){
            passwordEt2.setError("Enter Password Does Not Match");
            return;
        }
        else if(password.length()<4){
            passwordEt.setError("Length Should be greater than 4");
            return;
        }
        else if(!isValidEmail(email)){
            passwordEt2.setError("Entered Password Does Not Match");
            return;
        }
        progressDialog.setMessage("Please Wait ...");
        progressDialog.show();
        progressDialog.setCanceledOnTouchOutside(false);
        firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(SignUpActivity.this, "Successfully Registered",Toast.LENGTH_LONG).show();
                    Intent intent=new Intent(SignUpActivity.this, DashboardActivity.class);
                    startActivity(intent);
                    finish();
                }
                else{
                    Toast.makeText(SignUpActivity.this, "Sign Up Failed ", Toast.LENGTH_LONG).show();

                }
                progressDialog.dismiss();
            }
        });
    }
    private boolean isValidEmail(CharSequence target){
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }
}
