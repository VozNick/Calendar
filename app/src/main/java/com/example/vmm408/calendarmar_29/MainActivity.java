package com.example.vmm408.calendarmar_29;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.RelativeLayout;

import com.example.vmm408.calendarmar_29.fragments.CalendarViewFragment;
import com.example.vmm408.calendarmar_29.service.NotificationService;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.activity_main_container)
    RelativeLayout activityMainContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        new CalendarViewFragment().initNewFragment(this, activityMainContainer, "calendarFrag");
        startService(new Intent(this, NotificationService.class));
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().findFragmentByTag("listFrag") != null) {
            new CalendarViewFragment().initNewFragment(this, activityMainContainer, "calendarFrag");
        } else {
            super.onBackPressed();
        }
    }
}
