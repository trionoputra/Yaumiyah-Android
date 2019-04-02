package com.yondev.yaumiyah;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.yondev.yaumiyah.service.SchedulerService;
import com.yondev.yaumiyah.utils.Shared;

public class LandingPageActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing_page);
        Shared.initialize(getBaseContext());

        TextView t2 = (TextView)findViewById(R.id.textView2);

        Button btnNex = (Button)findViewById(R.id.button1);
        btnNex.setOnClickListener(this);

        t2.setTypeface(Shared.appfontThin);

        Intent serviceIntent = new Intent(getBaseContext(), SchedulerService.class);
        startService(serviceIntent);

    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(LandingPageActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }
}
