package irobinz.tk.TheBoringQR;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Random;

public class contact_form extends AppCompatActivity {

    SharedPreferences sharedPref;
    SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_form);

        sharedPref = getSharedPreferences("info", Context.MODE_PRIVATE);
        editor = sharedPref.edit();

        fillForm();

        final Button submitBtn = findViewById(R.id.submit);
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                processSubmission();
                submitBtn.setText(new Random().nextBoolean() ? "Pressed!" : "You pressed it!");
            }
        });

    }

    private void fillForm() {
        ((TextView)findViewById(R.id.nameText)).setText(sharedPref.getString("Name", ""));
    }

    private void processSubmission() {
        editor.putString("Name", ((EditText)findViewById(R.id.nameText)).getText().toString());
        editor.commit();
    }
}
