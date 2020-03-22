    package com.kelsiraman.peminum;

    import android.content.Intent;
    import android.os.Bundle;
    import android.view.View;
    import android.widget.TextView;

    import androidx.appcompat.app.AppCompatActivity;

    public class SignUp extends AppCompatActivity {

    TextView signIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        signIn = findViewById(R.id.moveToSignIn);

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //moveToSignIn();
                onBackPressed();
            }
        });


    }

    public void moveToSignIn(){
        Intent signIn = new Intent(getBaseContext(),loginActivity.class);
        startActivity(signIn);
    }
}
