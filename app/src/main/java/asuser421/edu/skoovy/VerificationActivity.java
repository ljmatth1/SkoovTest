package asuser421.edu.skoovy;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by user on 19-Feb-17.
 */

public class VerificationActivity extends AppCompatActivity implements View.OnClickListener {


    private Button registerButton;
    private EditText codeEditText;

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.verification_layout);

        codeEditText = (EditText) findViewById(R.id.code_Edittext);
        registerButton = (Button) findViewById(R.id.buttonRegister);
        registerButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.buttonRegister){

        }
    }
}
