package hcmute.nhom1.chatapp.UI;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

import hcmute.nhom1.chatapp.R;

public class PhoneActivity extends AppCompatActivity {

    ImageView imgBack;
    Button btnCountinue;
    EditText edtPhone;
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone);
        imgBack = findViewById(R.id.imageView187);
        btnCountinue = findViewById(R.id.buttonCountinue);
        edtPhone =findViewById(R.id.editTextInputPhone);
        mAuth =FirebaseAuth.getInstance();


        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btnCountinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(edtPhone.getText().toString().equals("")){
                    Toast.makeText(PhoneActivity.this, "Enter Phone", Toast.LENGTH_SHORT).show();
                }
                else{
                    VertifyPhone("+84"+edtPhone.getText().toString());
//                    Intent intent = new Intent(PhoneActivity.this,EnterOTPActivity.class);
//                    intent.putExtra("numberphone",edtPhone.getText().toString());
//                    startActivity(intent);
                }

            }
        });
    }

    private void VertifyPhone(String phone) {
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber(phone)       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(this)                 // Activity (for callback binding)
                        .setCallbacks(new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                            @Override
                            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                                //signInWithPhoneAuthCredential(phoneAuthCredential);
                            }

                            @Override
                            public void onVerificationFailed(@NonNull FirebaseException e) {
                                Toast.makeText(PhoneActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
                                Log.d("ERROR",e.toString());
                            }

                            @Override
                            public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                super.onCodeSent(s, forceResendingToken);
                                Intent intent =new Intent(PhoneActivity.this,EnterOTPActivity.class);
                                intent.putExtra("numberphone",edtPhone.getText().toString());
                                intent.putExtra("otp",s);
                                startActivity(intent);

                            }
                        })          // OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);

    }
}