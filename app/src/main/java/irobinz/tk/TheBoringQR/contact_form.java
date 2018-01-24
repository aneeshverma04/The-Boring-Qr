package irobinz.tk.TheBoringQR;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.Random;

public class contact_form extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_form);
        final Button submitBtn = findViewById(R.id.submit);
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                processSubmission();
                submitBtn.setText(new Random().nextBoolean() ? "Pressed!" : "You pressed it!");
            }
        });

    }

    public void processSubmission() {
        SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("Name", ((EditText)findViewById(R.id.nameText)).getText().toString());
        editor.commit();
    }
}
