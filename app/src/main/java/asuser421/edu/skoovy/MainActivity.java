package asuser421.edu.skoovy;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.nexmo.sdk.NexmoClient;
import com.nexmo.sdk.core.client.ClientBuilderException;
import com.nexmo.sdk.verify.client.VerifyClient;
import com.nexmo.sdk.verify.event.UserObject;
import com.nexmo.sdk.verify.event.VerifyClientListener;

import java.io.IOException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "verify";
    private Button buttonRegister, verifyButton , sendVerifyButton;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private TextView textViewSignin, verifyTextView;

    private ProgressDialog progressDialog;
    private NexmoClient nexmoClient;
    private VerifyClient verifyClient;
    private EditText editTextPhone, codeEditText, countryCodeEditText;

    //private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // firebaseAuth = FirebaseAuth.getInstance();

        progressDialog = new ProgressDialog(this);

        buttonRegister = (Button) findViewById(R.id.buttonRegister);

        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        editTextPhone = (EditText) findViewById(R.id.phoneEditText);
        countryCodeEditText = (EditText) findViewById(R.id.countrycodeEditText);
        codeEditText = (EditText) findViewById(R.id.editTextCode);
        verifyTextView = (TextView) findViewById(R.id.verificatiin_textview);
        verifyButton = (Button) findViewById(R.id.buttonVerify);
        sendVerifyButton = (Button) findViewById(R.id.sendVerifButton);


        textViewSignin = (TextView) findViewById(R.id.textViewSignin);

        buttonRegister.setOnClickListener(this);
        textViewSignin.setOnClickListener(this);
        verifyButton.setOnClickListener(this);
        sendVerifyButton.setOnClickListener(this);

        Context context = getApplicationContext();
        try {
            nexmoClient = new NexmoClient.NexmoClientBuilder()
                    .context(context)
                    .applicationId(Constants.NEXMO_ID) //your App key
                    .sharedSecretKey(Constants.SHARED_SECRET) //your App secret
                    .build();
        } catch (ClientBuilderException e) {
            e.printStackTrace();
        }

        verifyClient = new VerifyClient(nexmoClient);


        verifyClient.addVerifyListener(new VerifyClientListener() {
            @Override
            public void onVerifyInProgress(final VerifyClient verifyClient, UserObject user) {
                Log.d(TAG, "onVerifyInProgress for number: " + user.getPhoneNumber());
                progressDialog.setMessage("Verifying User...");
                progressDialog.show();

            }

            @Override
            public void onUserVerified(final VerifyClient verifyClient, UserObject user) {
                Log.d(TAG, "onUserVerified for number: " + user.getPhoneNumber());
                Toast.makeText(MainActivity.this, "User verified", Toast.LENGTH_SHORT).show();

                if (progressDialog != null && progressDialog.isShowing())
                    progressDialog.dismiss();

               /* String email = editTextEmail.getText().toString().trim();
                String password = editTextPassword.getText().toString().trim();
                String phoneNumber = editTextPhone.getText().toString().trim();

                Intent intent = new Intent(MainActivity.this, VerificationActivity.class);
                intent.putExtra("email", email);
                intent.putExtra("password", password);
                intent.putExtra("phone", phoneNumber);
                startActivity(intent);*/


                Toast.makeText(MainActivity.this, "User verified successfully!!", Toast.LENGTH_SHORT).show();
                verifyTextView.setText(R.string.user_verified);
            }

            @Override
            public void onError(final VerifyClient verifyClient, final com.nexmo.sdk.verify.event.VerifyError errorCode, UserObject user) {
                Log.d(TAG, "onError: " + errorCode + " for number: " + user.getPhoneNumber());
            }

            @Override
            public void onException(final IOException exception) {
                exception.printStackTrace();
            }
        });
    }


    private void registerUser() {
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        String phoneNumber = editTextPhone.getText().toString().trim();
        String countryCode = countryCodeEditText.getText().toString().trim();

        if (TextUtils.isEmpty(email)) {
            //Email is Empty
            Toast.makeText(this, "Please Enter Email", Toast.LENGTH_SHORT).show();
            //Stopping the function execution further
            return;
        }

        if (TextUtils.isEmpty(phoneNumber)) {
            //Phone number is Empty
            Toast.makeText(this, "Please Enter Phone Number", Toast.LENGTH_SHORT).show();
            //Stopping the function exectuion further
            return;
        }

        if (TextUtils.isEmpty(countryCode)) {
            //Country code is Empty
            Toast.makeText(this, "Please Enter country code", Toast.LENGTH_SHORT).show();
            //Stopping the function exectuion further
            return;
        }

        if (TextUtils.isEmpty(password)) {
            //Password is Empty
            Toast.makeText(this, "Please Enter Password", Toast.LENGTH_SHORT).show();
            //Stopping the function exectuion further
            return;
        }
        //if validations are ok
        //we will first show a progressBar




      /*  firebaseAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            //user is successfully registered and logged in
                            //we will start teh profile activity here
                            //right now lets dispaly a toas only
                            Toast.makeText(MainActivity.this, "Registered Successfully", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(MainActivity.this, "Could Not Register! Please Try Again", Toast.LENGTH_SHORT).show();
                        }
                    }
                });*/
    }

    @Override
    public void onClick(View view) {
        if (view == buttonRegister) {
            if (verifyTextView.getText().toString().equalsIgnoreCase(getString(R.string.user_not_verified)))
                Toast.makeText(MainActivity.this, "Please verify user data first.", Toast.LENGTH_SHORT).show();
            else
                registerUser();
        }

        if (view == textViewSignin) {
            //will open activity
        }

        if(view == verifyButton){


            String code  = codeEditText.getText().toString();
            if (TextUtils.isEmpty(code)) {
                //Code is Empty
                Toast.makeText(this, "Please Enter received code", Toast.LENGTH_SHORT).show();
                //Stopping the function execution further
                return;
            }else {
                verifyClient.checkPinCode(code.trim());
            }

        }

        if (view == sendVerifyButton){
            String phoneNumber = editTextPhone.getText().toString();
            String countryCode = countryCodeEditText.getText().toString();

            if (TextUtils.isEmpty(phoneNumber)) {
                //Phone number is Empty
                Toast.makeText(this, "Please Enter Phone Number", Toast.LENGTH_SHORT).show();
                //Stopping the function exectuion further
                return;
            }else if (TextUtils.isEmpty(countryCode)) {
                //Country code is Empty
                Toast.makeText(this, "Please Enter Country code", Toast.LENGTH_SHORT).show();
                //Stopping the function exectuion further
                return;
            }else{
                verifyClient.getVerifiedUser(countryCode, phoneNumber.trim());
                Toast.makeText(MainActivity.this, "4 Digit code is being send", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
