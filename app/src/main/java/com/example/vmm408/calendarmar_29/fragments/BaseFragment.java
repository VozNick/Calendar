package com.example.vmm408.calendarmar_29.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public class BaseFragment extends Fragment {
    private Unbinder unbinder;
    private AppCompatActivity appCompatActivity;
    private View viewContainer;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        unbinder = ButterKnife.bind(this, view);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    public void initNewFragment(AppCompatActivity appCompatActivity, View viewContainer, String tag) {
        this.appCompatActivity = appCompatActivity;
        this.viewContainer = viewContainer;
        appCompatActivity.getSupportFragmentManager().beginTransaction()
                .replace(viewContainer.getId(), this, tag).commit();
    }

    public void initNewFragment(Fragment fragment, String tag) {
        appCompatActivity.getSupportFragmentManager().beginTransaction()
                .replace(viewContainer.getId(), fragment, tag).commit();
    }
}
