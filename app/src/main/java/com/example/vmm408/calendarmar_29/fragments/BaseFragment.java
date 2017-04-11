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
    private View view;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        unbinder = ButterKnife.bind(this, view);
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        unbinder.unbind();
        super.onDestroyView();
    }

    public void initNewFragment(AppCompatActivity appCompatActivity, View view, String tag) {
        this.appCompatActivity = appCompatActivity;
        this.view = view;
        appCompatActivity.getSupportFragmentManager().beginTransaction()
                .replace(view.getId(), this, tag).commit();
    }

    public void initNewFragment(Fragment fragment, String tag) {
        appCompatActivity.getSupportFragmentManager().beginTransaction()
                .replace(view.getId(), fragment, tag).commit();
    }
}
