package irobinz.tk.TheBoringQR;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class Reciever extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reciever);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        //contact_add();
        scanning_qr();
    }
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
            String scanFormat = scanningResult.getFormatName();
            TextView text_temp = findViewById(R.id.textView3);
            text_temp.setText(result_scan);
        }
        else{
            Toast toast = Toast.makeText(getApplicationContext(),
                    "No scan data received!", Toast.LENGTH_SHORT);
            toast.show();
        }
    }


    void contact_add() {
        // Class ContactsContract used .. help https://developer.android.com/training/contacts-provider/modify-data.html

        Intent intent = new Intent(ContactsContract.Intents.Insert.ACTION);
        intent.setType(ContactsContract.RawContacts.CONTENT_TYPE);

        intent.putExtra(ContactsContract.Intents.Insert.NAME,"Aneesh");
        intent.putExtra(ContactsContract.Intents.Insert.POSTAL, ContactsContract.CommonDataKinds.SipAddress.TYPE_HOME)
                .putExtra(ContactsContract.Intents.Insert.POSTAL,"BattisUnnis");
        intent.putExtra(ContactsContract.Intents.Insert.PHONE, ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE)
                .putExtra(ContactsContract.Intents.Insert.PHONE,"9872340544");

        startActivity(intent);

    }

}
