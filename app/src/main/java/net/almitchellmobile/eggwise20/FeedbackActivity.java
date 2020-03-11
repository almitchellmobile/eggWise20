package net.almitchellmobile.eggwise20;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class FeedbackActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /*FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/


        sendFeedback();

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

        Intent intent2 = new Intent(FeedbackActivity.this,
                EggWiseMainActivity.class);
        intent2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
    }

}
