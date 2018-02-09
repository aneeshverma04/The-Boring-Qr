package irobinz.tk.TheBoringQR;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
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
                submitBtn.setText("Updating..");
                int colorFrom = getResources().getColor(R.color.colorPrimaryDark);
                int colorTo = getResources().getColor(R.color.colorPrimary);
                ValueAnimator colorAnimation = ValueAnimator.ofObject(new ArgbEvaluator(), colorFrom, colorTo);
                colorAnimation.setDuration(250); // milliseconds
                colorAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

                    @Override
                    public void onAnimationUpdate(ValueAnimator animator) {
                        submitBtn.setBackgroundColor((int) animator.getAnimatedValue());
                    }

                });
                colorAnimation.start();
            }
        });
    }

    private void fillForm() {
        ((TextView)findViewById(R.id.nameText))
                .setText(sharedPref.getString("Name", ""));
        ((TextView)findViewById(R.id.email1Text))
                .setText(sharedPref.getString("Email", ""));
        ((TextView)findViewById(R.id.phone1Text))
                .setText(sharedPref.getString("Phone", ""));
        ((TextView)findViewById(R.id.address))
                .setText(sharedPref.getString("Address", ""));
        ((TextView)findViewById(R.id.noteText))
                .setText(sharedPref.getString("Note", ""));
    }

    private void processSubmission() {
        editor.putString("Name",
                ((EditText)findViewById(R.id.nameText)).getText().toString());
        editor.putString("Email",
                ((EditText)findViewById(R.id.email1Text)).getText().toString());
        editor.putString("Phone",
                ((EditText)findViewById(R.id.phone1Text)).getText().toString());
        editor.putString("Address",
                ((EditText)findViewById(R.id.address)).getText().toString());
        editor.putString("Note",
                ((EditText)findViewById(R.id.noteText)).getText().toString());
        editor.commit();
    }
}
