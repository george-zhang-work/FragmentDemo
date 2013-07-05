package com.example.fragmentswitchtest;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class SecondFragment extends BaseFragment {

    public static String TAG = SecondFragment.class.getSimpleName();

    public static SecondFragment newInstance(Bundle args) {
        return new SecondFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_content, container, false);
        ((TextView) v.findViewById(R.id.content_tv)).setText("Hello Fragment "
                + SecondFragment.class.getSimpleName());
        v.findViewById(R.id.pre_btn).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        v.findViewById(R.id.nxt_btn).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                showFragment(SecondFragment.this, ThirdFragment.class, ThirdFragment.TAG,
                        null, false);
            }
        });
        return v;
    }

    @Override
    protected void onBackPressed() {
        showFragment(SecondFragment.this, FirstFragment.class, FirstFragment.TAG, null,
                true);
    }
}
