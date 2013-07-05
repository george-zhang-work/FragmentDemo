package com.example.fragmentswitchtest;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.widget.Toast;

public abstract class BaseFragment extends Fragment {

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (!(activity instanceof HomeActivity)) {
            Toast.makeText(activity,
                    "The context should be" + HomeActivity.class.getName(),
                    Toast.LENGTH_LONG).show();
        }
    }

    protected void showFragment(Fragment curFragment, Class<?> fragmentClass,
            final String tag, Bundle ags, boolean flag) {
        ((HomeActivity) getActivity()).showFragment(curFragment, fragmentClass, tag, ags,
                flag);
    }

    /**
     * Back to the front fragment. The default is to invoke the {
     * {@link Activity#onBackPressed()}.Take care of popping the fragment back
     * stack or finishing the activity as appropriate.
     */
    protected void onBackPressed() {
        getActivity().onBackPressed();
    }
}
