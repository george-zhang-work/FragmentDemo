package com.example.fragmentswitchtest;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

public class FirstFragment extends BaseFragment {

    public static String TAG = FirstFragment.class.getSimpleName();

    public static FirstFragment newInstance(Bundle args) {
        return new FirstFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_content, container, false);
        ((TextView) v.findViewById(R.id.content_tv)).setText("Hello Fragment "
                + FirstFragment.class.getSimpleName());

        v.findViewById(R.id.pre_btn).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        v.findViewById(R.id.nxt_btn).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                showFragment(FirstFragment.this, SecondFragment.class,
                        SecondFragment.TAG, null, false);
            }
        });
        return v;
    }

    @Override
    protected void onBackPressed() {
        getActivity().onBackPressed();
    }
}
