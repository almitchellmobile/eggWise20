package net.almitchellmobile.eggwise20;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import net.almitchellmobile.eggwise20.util.Common;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class EggWiseMainActivity extends AppCompatActivity {

    Button buttonLinkBrinsea, buttonLinkBrinseaFacebook;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_egg_wise_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getIntent().getBooleanExtra("EXIT", false)) {
            finish();
        }

        FloatingActionButton fabEggWiseMain = findViewById(R.id.fabEggWiseMain);
        fabEggWiseMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                //        .setAction("Action", null).show();

                /*String extraText = "";
                String versionName = "";
                int versionCode = 0;

                try {
                    versionName = getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
                    versionCode = getPackageManager().getPackageInfo(getPackageName(), 0).versionCode;
                } catch (PackageManager.NameNotFoundException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

                extraText = "Version Name: " + versionName + ", Version Code: " + versionCode + ".";


                final Intent _Intent = new Intent(android.content.Intent.ACTION_SEND);
                _Intent.setType("text/html");
                _Intent.putExtra(android.content.Intent.EXTRA_EMAIL, "almitchellmobile@gmail.com");
                _Intent.putExtra(android.content.Intent.EXTRA_SUBJECT, "eggWISE Mobile - User Feedback");
                _Intent.putExtra(android.content.Intent.EXTRA_TEXT, extraText);
                startActivity(Intent.createChooser(_Intent, "Send feedback"));


*/
                startActivity(
                        new Intent(EggWiseMainActivity.this, FeedbackActivity.class));

            }



        });


        buttonLinkBrinsea = (Button)findViewById(R.id.buttonLinkBrinsea);
        buttonLinkBrinsea.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Uri uri = Uri.parse("http://www.brinsea.com/");

                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });

        buttonLinkBrinseaFacebook = (Button)findViewById(R.id.buttonLinkBrinseaFacebook);
        buttonLinkBrinseaFacebook.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Uri uri = Uri.parse("https://www.facebook.com/pages/Brinsea-ProductsInc/140621339296778/");

                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);

        switch (item.getItemId()) {
            case R.id.send_feedback:
                final Intent _Intent = new Intent(android.content.Intent.ACTION_SEND);
                _Intent.setType("text/html");
                _Intent.putExtra(android.content.Intent.EXTRA_EMAIL, "almitchellmobile@gmail.com");
                _Intent.putExtra(android.content.Intent.EXTRA_SUBJECT, "eggWISE Mobile - User Feedback");
                _Intent.putExtra(android.content.Intent.EXTRA_TEXT, Common.getExtraText(this));
                startActivity(Intent.createChooser(_Intent, "Send feedback"));
                return true;

            default:
                try {
                    Common common = new Common();
                    common.menuOptions(item, getApplicationContext(), this);
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                return true;
        }
    }

    public void sendFeedback() {
        String extraText = "";
        String versionName = "";
        int versionCode = 0;

        try {
            versionName = getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
            versionCode = getPackageManager().getPackageInfo(getPackageName(), 0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        extraText = "Version Name: " + versionName + ", Version Code: " + versionCode + ".";

        final Intent _Intent = new Intent(Intent.ACTION_SEND);
        _Intent.setType("text/html");
        _Intent.putExtra(Intent.EXTRA_EMAIL, "almitchellmobile@gmail.com");
        _Intent.putExtra(Intent.EXTRA_SUBJECT, "eggWISE Mobile - User Feedback");
        _Intent.putExtra(Intent.EXTRA_TEXT, extraText);
        startActivity(Intent.createChooser(_Intent, "Send feedback"));

    }

}
