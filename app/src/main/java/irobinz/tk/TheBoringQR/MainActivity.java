package irobinz.tk.TheBoringQR;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import net.glxn.qrgen.android.QRCode;
import net.glxn.qrgen.core.scheme.VCard;

/*
* Both buttons with layout of Wrap_Content... so it grows and shrinks accordingly
* If doesnt like set "fixed" in constraints layout instead
* Can also do match_parent however i doubt it will look good
* */
public class MainActivity extends AppCompatActivity {
        //implements NavigationView.OnNavigationItemSelectedListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        SharedPreferences sharedPref = getSharedPreferences("info", Context.MODE_PRIVATE);
        String name = "Hi " + sharedPref.getString("Name", "!");
        ((TextView)findViewById(R.id.textView2)).setText(name);

        /*
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this); */


        findViewById(R.id.formBtn).setOnClickListener(new View.OnClickListener() {
                                                         @Override
                                                         public void onClick(View v) {
            Button btn = (Button)v;
            btn.setText("Pressed");
            startActivity(new Intent(MainActivity.this,contact_form.class));
                }
                    }
            );


        /*
        * Button2 : Import Contacts
        * */
        findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button btn = (Button)v;
                btn.setText("I am blue Iice");
                //Reciever.recieve();
                startActivity(new Intent(MainActivity.this,Reciever.class));
            }
        });

        /*
        * On text click .. will delete it later.. just for fun
        * */
        findViewById(R.id.textView2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView tv = (TextView)v;
                tv.setText("Whos your daddy");
            }
        });

        GenerateQR();
    }

    @Override
    public void onBackPressed() {
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void GenerateQR() {
        ImageView imageView = findViewById(R.id.imageView2);
        VCard vCard = getVCard();
        Bitmap bitmap = QRCode.from(vCard).bitmap();
        imageView.setImageBitmap(bitmap);

    }
    private VCard getVCard() {
        VCard vcard = new VCard();
        vcard.setName("Robin");
        vcard.setAddress("Sector 9");
        vcard.setPhoneNumber("9815540544");
        return vcard;
    }
}
