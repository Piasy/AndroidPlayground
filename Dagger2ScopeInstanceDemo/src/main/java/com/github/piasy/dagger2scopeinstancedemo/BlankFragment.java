package com.github.piasy.dagger2scopeinstancedemo;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import javax.inject.Inject;
import timber.log.Timber;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link BlankFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link BlankFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BlankFragment extends Fragment {

    @Inject
    DemoNewDependency mDemoNewDependency;
    @Inject
    DemoInjectDependency mDemoInjectDependency;
    @Inject
    DemoDirectInjectDependency mDemoDirectInjectDependency;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Timber.d("BlankFragment before inject, mDemoNewDependency: " + mDemoNewDependency);
        Timber.d("BlankFragment before inject, mDemoInjectDependency: " + mDemoInjectDependency);
        Timber.d("BlankFragment before inject, mDemoDirectInjectDependency: " + mDemoDirectInjectDependency);

        DemoComponent component = ((MainActivity) getActivity()).getComponent();
        Timber.d("BlankFragment inject, component: " + component);
        component.inject(this);

        Timber.d("BlankFragment after inject, mDemoNewDependency: " + mDemoNewDependency);
        Timber.d("BlankFragment after inject, mDemoInjectDependency: " + mDemoInjectDependency);
        Timber.d("BlankFragment after inject, mDemoDirectInjectDependency: " + mDemoDirectInjectDependency);
    }
}
