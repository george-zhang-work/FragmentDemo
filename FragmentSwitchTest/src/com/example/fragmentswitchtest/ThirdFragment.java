package com.example.fragmentswitchtest;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class ThirdFragment extends BaseFragment {
    public static String TAG = ThirdFragment.class.getSimpleName();

    public static ThirdFragment newInstance(Bundle args) {
        return new ThirdFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_content, container, false);
        ((TextView) v.findViewById(R.id.content_tv)).setText("Hello Fragment "
                + ThirdFragment.class.getSimpleName());

        v.findViewById(R.id.pre_btn).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        v.findViewById(R.id.nxt_btn).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                showFragment(ThirdFragment.this, FourthFragment.class,
                        FourthFragment.TAG, null, false);
            }
        });
        return v;
    }

    @Override
    protected void onBackPressed() {
        showFragment(ThirdFragment.this, SecondFragment.class, SecondFragment.TAG, null,
                true);
    }
}
