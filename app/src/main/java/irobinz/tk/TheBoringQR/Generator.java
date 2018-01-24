package irobinz.tk.TheBoringQR;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import net.glxn.qrgen.android.QRCode;
import net.glxn.qrgen.core.scheme.VCard;

public class Generator extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generator);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        init(); // calling of generation of qr

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

            }
        });
    }
    private void init() {
        ImageView imageView = findViewById(R.id.imageView1);
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
