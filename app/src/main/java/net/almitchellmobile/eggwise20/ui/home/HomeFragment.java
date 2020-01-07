package net.almitchellmobile.eggwise20.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import net.almitchellmobile.eggwise20.R;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    View root;
    TextView textView;
    ImageView image_eggwise_image;
    Button buttonLinkBrinsea;
    ImageView brinsea_image;
    Button buttonByLineALMmitchell;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
       /* homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        final TextView textView = root.findViewById(R.id.text_home);
        homeViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });*/

        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        root = inflater.inflate(R.layout.fragment_home, container, false);
        //textView = root.findViewById(R.id.text_home);
        image_eggwise_image = root.findViewById(R.id.eggwise_image);
        buttonLinkBrinsea = root.findViewById(R.id.buttonLinkBrinsea);
        brinsea_image = root.findViewById(R.id.brinsea_image);
        buttonByLineALMmitchell = root.findViewById(R.id.buttonByLineALMmitchell);

        return root;
    }
}