package com.example.csit451sapien.jigsaw;

import android.app.DialogFragment;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;



/**
 * Created by Brian on 4/17/2016.
 */
public class Celebrate extends DialogFragment implements View.OnClickListener {

    ImageView imageView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_celebrate, null);

        imageView = (ImageView)view.findViewById(R.id.celebrateImg);
        imageView.setBackgroundResource(R.drawable.victoryanimation);

        AnimationDrawable anim = (AnimationDrawable) imageView.getBackground();
        anim.start();

        view.findViewById(R.id.homeBtn).setOnClickListener(this);
        view.findViewById(R.id.statBtn).setOnClickListener(this);

        setCancelable(false);
        return view;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.homeBtn:
                startActivity(new Intent(getActivity(), JigsawHomePage.class));
                break;
            case R.id.statBtn:
                startActivity(new Intent(getActivity(), Statistics.class));
                break;
            default:
                // do nothing
        }
    }
}
