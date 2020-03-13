package net.almitchellmobile.eggwise20;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import net.almitchellmobile.eggwise20.util.Common;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.ActionMenuView;
import androidx.appcompat.widget.Toolbar;
import androidx.interpolator.view.animation.FastOutSlowInInterpolator;
import uk.co.samuelwall.materialtaptargetprompt.MaterialTapTargetPrompt;

public class EggWiseMainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    Button buttonLinkBrinsea, buttonLinkBrinseaFacebook, button_show_overflow_prompt;
    Toolbar toolbarEggWiseMain;
    MaterialTapTargetPrompt mFabPrompt;
    MaterialTapTargetPrompt.Builder tapTargetPromptBuilder;
    MaterialTapTargetPrompt tapTargetPromptBuilder2;
    public static Toolbar tb;
    public static View child;
    public static ActionMenuView actionMenuView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_egg_wise_main);
        //toolbarEggWiseMain = findViewById(R.id.toolbar_egg_wise_main);
        toolbarEggWiseMain = findViewById(R.id.toolbar_egg_wise_main);
        toolbarEggWiseMain.inflateMenu(R.menu.main);
        setSupportActionBar(toolbarEggWiseMain);

        if (getIntent().getBooleanExtra("EXIT", false)) {
            finish();
        }

        /*FloatingActionButton fabEggWiseMain = findViewById(R.id.fabEggWiseMain);
        fabEggWiseMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendFeedback();
            }
        });*/


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


        eggWiseQuickStart1();
    }

    private void eggWiseQuickStart1() {
        final MaterialTapTargetPrompt.Builder tapTargetPromptBuilder = new MaterialTapTargetPrompt.Builder(this)
                .setPrimaryText("Start here")
                .setSecondaryText("Tap on the menu button and select the Egg Batch Management option.")
                .setAnimationInterpolator(new FastOutSlowInInterpolator())
                .setMaxTextWidth(R.dimen.tap_target_menu_max_width)
                .setIcon(R.drawable.ic_more_vert);
        final Toolbar tb = this.findViewById(R.id.toolbar_egg_wise_main);
        tb.inflateMenu(R.menu.main);
        final View child = tb.getChildAt(0);
        if (child instanceof ActionMenuView)
        {
            final ActionMenuView actionMenuView = ((ActionMenuView) child);
            tapTargetPromptBuilder.setTarget(actionMenuView.getChildAt(actionMenuView.getChildCount() - 1));
        }
        else
        {
            Toast.makeText(this, R.string.overflow_unavailable, Toast.LENGTH_SHORT).show();
        }
        tapTargetPromptBuilder.show();
    }

    public void showOverflowPrompt(View view)
    {

        tapTargetPromptBuilder = new MaterialTapTargetPrompt.Builder(this)
                //.setTarget(toolbarEggWiseMain)
                .setPrimaryText("Start here")
                .setSecondaryText("Tap on the menu button and select the Egg Batch Management option.")
                .setAnimationInterpolator(new FastOutSlowInInterpolator())
                .setMaxTextWidth(R.dimen.tap_target_menu_max_width)
                .setAutoDismiss(false)
                .setAutoFinish(false)
                //.setCaptureTouchEventOutsidePrompt(true)
                .setPromptStateChangeListener((prompt, state) -> {
                    if (state == MaterialTapTargetPrompt.STATE_FOCAL_PRESSED)
                    {
                        prompt.finish();
                        mFabPrompt = null;
                    }
                    else if (state == MaterialTapTargetPrompt.STATE_NON_FOCAL_PRESSED)
                    {
                        mFabPrompt = null;
                    }
                })
                .setIcon(R.drawable.ic_more_vert);
        final Toolbar tb = this.findViewById(R.id.toolbar);
        final View child = tb.getChildAt(1);
        if (child instanceof ActionMenuView)
        {
            ActionMenuView actionMenuView = ((ActionMenuView) child);
            tapTargetPromptBuilder.setTarget(actionMenuView.getChildAt(actionMenuView.getChildCount() - 1));
            tapTargetPromptBuilder.show();
        }
        else
        {
            Toast.makeText(this, R.string.overflow_unavailable, Toast.LENGTH_SHORT).show();
        }

    }

    public void showNoAutoDismiss(View view)
    {
        final Toolbar tb = this.findViewById(R.id.toolbar_egg_wise_main);
        final View child = tb.getChildAt(1);
        final ActionMenuView actionMenuView = ((ActionMenuView) child);

        if (tapTargetPromptBuilder2 != null)
        {
            return;
        }
        tapTargetPromptBuilder2 = new MaterialTapTargetPrompt.Builder(this)
                .setPrimaryText("No Auto Dismiss")
                .setSecondaryText("This prompt will only be removed after tapping the envelop")
                .setAnimationInterpolator(new FastOutSlowInInterpolator())
                .setAutoDismiss(false)
                .setAutoFinish(false)
                .setCaptureTouchEventOutsidePrompt(true)
                .setPromptStateChangeListener((prompt, state) -> {
                    if (state == MaterialTapTargetPrompt.STATE_FOCAL_PRESSED)
                    {
                        prompt.finish();
                        mFabPrompt = null;
                    }
                    else if (state == MaterialTapTargetPrompt.STATE_NON_FOCAL_PRESSED)
                    {
                        mFabPrompt = null;
                    }
                }).setTarget(actionMenuView.getChildAt(actionMenuView.getChildCount() - 1)).show();


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

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        return false;
    }
}
