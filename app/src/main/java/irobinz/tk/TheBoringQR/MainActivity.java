package irobinz.tk.TheBoringQR;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.ConsoleMessage;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import net.glxn.qrgen.android.QRCode;
import net.glxn.qrgen.core.scheme.VCard;

import java.io.File;

import static android.provider.ContactsContract.Contacts.CONTENT_VCARD_TYPE;

/*
* Both buttons with layout of Wrap_Content... so it grows and shrinks accordingly
* If doesnt like set "fixed" in constraints layout instead
* Can also do match_parent however i doubt it will look good
* */
public class MainActivity extends AppCompatActivity {

    static String [] sub_temp = new String[4]; // name,email,phone,address
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        SharedPreferences sharedPref = getSharedPreferences("info", Context.MODE_PRIVATE);
        String name = "Hi " + sharedPref.getString("Name", "!");
        ((TextView)findViewById(R.id.textView2)).setText(name);

        findViewById(R.id.formBtn).setOnClickListener(new View.OnClickListener() {
                                                         @Override
                                                         public void onClick(View v) {
            Button btn = (Button)v;
            btn.setText("Pressed");
            finish();
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
                scanning_qr();
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




    // Generation of QR
    private void GenerateQR() {
        ImageView imageView = findViewById(R.id.imageView2);
        VCard vCard = getVCard();
        Bitmap bitmap = QRCode.from(vCard).bitmap();
        imageView.setImageBitmap(bitmap);
    }

    private VCard getVCard() {
        SharedPreferences sharedPref = getSharedPreferences("info", Context.MODE_PRIVATE);
        VCard vcard = new VCard();
        vcard.setName(sharedPref.getString("Name", "XYZ"));
        vcard.setEmail(sharedPref.getString("Email", "abc@gmail.com"));
        vcard.setPhoneNumber(sharedPref.getString("Phone", "12345"));
        vcard.setAddress(sharedPref.getString("Address", "#3219 sd "));
        //vcard.setNote(sharedPref.getString("Note", "NOTEE"));
        return vcard;
    }




    // Recieving of QR

    void scanning_qr() {
        IntentIntegrator qrScan = new IntentIntegrator(this);
        qrScan.initiateScan();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        //retrieve scan result
        IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);

        if (scanningResult != null) {
            //we have a result
            String result_scan = scanningResult.getContents();

            if (result_scan == null)
                return;
            // splitting the vcard String into structured strings
            String [] temp = result_scan.split("\n");

            int len = temp.length;

            for(int i = 2 ,j = 0; i < len && j < 4 ; i++) { // from name to note
                int index = temp[i].indexOf(":");
                sub_temp[j++] = temp[i].substring(index + 1);
                //Log.v("check1",temp[i] + " " + sub_temp);
            }
        }
        // major bug if open up the scanner and press back button app closes
        else{
            Toast toast = Toast.makeText(getApplicationContext(),
                    "No scan data received!", Toast.LENGTH_SHORT);
            toast.show();
        }
        contact_add();
    }
/*
*   sub_temp index:
 *   0 ==> Name
 *   1 ==> phone
 *   2 ==> email
 *   3 ==> Address
*
*
*
* */
    // Add recieved QR to contacts
    void contact_add() {
        // Class ContactsContract used .. help https://developer.android.com/training/contacts-provider/modify-data.html

        Intent intent = new Intent(ContactsContract.Intents.Insert.ACTION);
        intent.setType(ContactsContract.RawContacts.CONTENT_TYPE);

        intent.putExtra(ContactsContract.Intents.Insert.NAME,sub_temp[0]);
        intent.putExtra(ContactsContract.Intents.Insert.PHONE, ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE)
                .putExtra(ContactsContract.Intents.Insert.PHONE,sub_temp[1]);
        intent.putExtra(ContactsContract.Intents.Insert.EMAIL, ContactsContract.CommonDataKinds.Email.ADDRESS)
                .putExtra(ContactsContract.Intents.Insert.EMAIL,sub_temp[2]);
        intent.putExtra(ContactsContract.Intents.Insert.POSTAL, ContactsContract.CommonDataKinds.SipAddress.TYPE_HOME)
                .putExtra(ContactsContract.Intents.Insert.POSTAL,sub_temp[3]);

        startActivity(intent);

    }
}
