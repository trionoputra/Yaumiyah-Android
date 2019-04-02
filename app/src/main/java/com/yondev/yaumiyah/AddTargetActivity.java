package com.yondev.yaumiyah;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.media.RingtoneManager;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.yondev.yaumiyah.adapter.LoopSpinnerAdapter;
import com.yondev.yaumiyah.adapter.NotifSpinnerAdapter;
import com.yondev.yaumiyah.entity.Target;
import com.yondev.yaumiyah.sqlite.DatabaseHelper;
import com.yondev.yaumiyah.sqlite.DatabaseManager;
import com.yondev.yaumiyah.sqlite.ds.TargetDataSource;
import com.yondev.yaumiyah.utils.Shared;
import com.yondev.yaumiyah.widget.ConfirmDialog;
import com.yondev.yaumiyah.widget.FlowLayout;
import com.yondev.yaumiyah.widget.LoadingDialog;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class AddTargetActivity extends AppCompatActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {
    private boolean isEditMode = false;

    private EditText txtDate;
    private EditText txtTime;
    private EditText txtJudul;
    private TextView txtSound;
    private FlowLayout flow;

    private DatePickerDialog datePickerDialog;
    private TimePickerDialog timePickerDialog;
    private Spinner spinnerLoop;
    private Spinner spinnerWaktu;

    private String selectedDate;
    private String selectedTime;

    private Target dtTarget;

    private LoopSpinnerAdapter adapter;
    private NotifSpinnerAdapter waktuAdapter;
    private LoadingDialog loading;
    private Switch notif;
    private Switch vibrasi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_target);
        DatabaseManager.initializeInstance(new DatabaseHelper(AddTargetActivity.this));

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle(R.string.buat_target_baru);

        txtJudul = (EditText)findViewById(R.id.editText) ;
        txtDate = (EditText) findViewById(R.id.editText2);
        txtTime = (EditText) findViewById(R.id.editText3);
        txtSound = (TextView) findViewById(R.id.textView12);
        notif = (Switch)findViewById(R.id.switch1);
        vibrasi = (Switch)findViewById(R.id.switch2);

        txtSound.setTag(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));

        flow = (FlowLayout) findViewById(R.id.lambangWrapper);

        txtDate.setOnClickListener(this);
        txtTime.setOnClickListener(this);

        notif.setOnCheckedChangeListener(this);

        findViewById(R.id.btnCancel).setOnClickListener(this);
        findViewById(R.id.btnBuat).setOnClickListener(this);
        findViewById(R.id.suaraWrapper).setOnClickListener(this);

        spinnerLoop = (Spinner)findViewById(R.id.spinner);
        spinnerWaktu = (Spinner)findViewById(R.id.spinner2);

        adapter = new LoopSpinnerAdapter(this,getSpinnerData());
        waktuAdapter = new NotifSpinnerAdapter(this,getWaktuSpinnerData());


        selectedDate = Shared.dateformatAdd.format(new Date());
        selectedTime = Shared.dateformatTime.format(new Date());

        Calendar c = Calendar.getInstance();
        datePickerDialog = new DatePickerDialog(this, dateListener, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
        timePickerDialog = new TimePickerDialog(this,timeListener,c.get(Calendar.HOUR_OF_DAY),c.get(Calendar.MINUTE),true);


        ((TextView)findViewById(R.id.textView)).setTypeface(Shared.appfontBold);
        ((TextView)findViewById(R.id.textView5)).setTypeface(Shared.appfontBold);
        ((TextView)findViewById(R.id.textView6)).setTypeface(Shared.appfontBold);
        ((Button)findViewById(R.id.btnBuat)).setTypeface(Shared.appfontBold);
        ((Button)findViewById(R.id.btnCancel)).setTypeface(Shared.appfontBold);


        spinnerLoop.setAdapter(adapter);
        spinnerWaktu.setAdapter(waktuAdapter);

        loading = new LoadingDialog(this);

        Bundle extras = getIntent().getExtras();
        if(extras != null)
        {
            isEditMode = true;

            SQLiteDatabase db =  DatabaseManager.getInstance().openDatabase();
            TargetDataSource DS = new TargetDataSource(db);
            dtTarget = DS.get(extras.getInt("code"));
            DatabaseManager.getInstance().closeDatabase();

            initEditView();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    private Menu menu;
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        this.menu = menu;
        getMenuInflater().inflate(R.menu.add, menu);

        if(isEditMode)
        {
            toogleMenu(isEditMode && dtTarget.getTipe() == 2);
        }
        else
            toogleMenu(isEditMode);

        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                finish();
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                break;
            case R.id.action_delete:
               delete();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void delete()
    {
        final ConfirmDialog dialog = new ConfirmDialog(this,getString(R.string.yakin),"","");
        dialog.setConfirmListener(new ConfirmDialog.ConfirmListener() {
            @Override
            public void onOK() {
                SQLiteDatabase db =  DatabaseManager.getInstance().openDatabase();
                TargetDataSource DS = new TargetDataSource(db);

                boolean realdelete = false;
                if(dtTarget.getDetails() != null)
                {
                    if(dtTarget.getDetails().size() == 0)
                    {
                        realdelete = true;
                    }
                }

                if(realdelete)
                    DS.delete(dtTarget.getId());
                else
                {
                    dtTarget.setIs_deteted(true);
                    DS.update(dtTarget,dtTarget.getId());
                }

                DatabaseManager.getInstance().closeDatabase();
                dialog.dismiss();
                setResult(RESULT_OK);
                finish();
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
            @Override
            public void onCancel() {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private void save()
    {

        if(txtJudul.getText().toString().equals(""))
        {
            Toast.makeText(this,R.string.isi_judul,Toast.LENGTH_SHORT).show();
        }
        else if(txtDate.getText().toString().equals(""))
        {
            Toast.makeText(this,R.string.isi_tanggal,Toast.LENGTH_SHORT).show();
        }
        else if(txtTime.getText().toString().equals(""))
        {
            Toast.makeText(this,R.string.isi_jam,Toast.LENGTH_SHORT).show();
        }
        else
        {
            Target target = new Target();
            if(isEditMode)
            {
                target = dtTarget;
                target.setLast_notif(null);
            }

            target.setJudul(txtJudul.getText().toString());
            try {target.setWaktu(Shared.datetimeformat.parse(txtDate.getText().toString()+ " "+ txtTime.getText().toString()));} catch (ParseException e) {e.printStackTrace();}
            target.setPengulangan(spinnerLoop.getSelectedItemPosition() + 1);
            target.setCreate_date(new Date());
            target.setIs_deteted(false);
            target.setNotifikasi(notif.isChecked());
            target.setIcon(flow.clickedId);
            target.setNote("");
            target.setVibrasi(vibrasi.isChecked());
            target.setSoundname(txtSound.getText().toString());
            target.setSounduri(txtSound.getTag().toString());
            target.setSoundtime(spinnerLoop.getSelectedItemPosition() + 1);
            SaveAsync save = new SaveAsync(target);
            save.execute();
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch(buttonView.getId())
        {
            case R.id.switch1:
                if(isChecked)
                    findViewById(R.id.notifDetailWrapper).setVisibility(View.VISIBLE);
                else
                    findViewById(R.id.notifDetailWrapper).setVisibility(View.GONE);

                break;
        }
    }

    public class SaveAsync extends AsyncTask<String, String, String>
    {
        private Target data;
        public SaveAsync(Target data)
        {
            this.data = data;
        }
        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            if(loading != null)
                loading.show();
            else
            {
                loading = new LoadingDialog(AddTargetActivity.this);
                loading.show();
            }
        }

        @Override
        protected String doInBackground(String... params) {
            // TODO Auto-generated method stub

            String result = "0";
            try {

                SQLiteDatabase db =  DatabaseManager.getInstance().openDatabase();
                TargetDataSource DS = new TargetDataSource(db);
                if(!isEditMode)
                    DS.insert(this.data);
                else
                    DS.update(this.data,this.data.getId());

                DatabaseManager.getInstance().closeDatabase();

                result = "1";

                Thread.sleep(1000);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);
            loading.dismiss();

            setResult(RESULT_OK);
            finish();
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

        }
    }

    private void toogleMenu(boolean show)
    {
        this.menu.findItem(R.id.action_delete).setVisible(show);
    }

    private DatePickerDialog.OnDateSetListener dateListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            // TODO Auto-generated method stub
            Calendar calendar = Calendar.getInstance();
            calendar.set(year, monthOfYear, dayOfMonth);
            datePickerDialog.updateDate(year, monthOfYear, dayOfMonth);
            selectedDate = Shared.dateformatAdd.format(calendar.getTime());
            txtDate.setText(selectedDate);
        }
    };

    private TimePickerDialog.OnTimeSetListener timeListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            Calendar calendar = Calendar.getInstance();
            calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH),hourOfDay,minute);
            timePickerDialog.updateTime(hourOfDay,minute);

            selectedTime =  Shared.dateformatTime.format(calendar.getTime());
            txtTime.setText(selectedTime);
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.btnBuat:
                save();
                break;
            case R.id.btnCancel:
                finish();
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                break;
            case R.id.waktuWrapper:
                if(isEditMode)
                    if(dtTarget.getTipe() == 1)
                        Toast.makeText(AddTargetActivity.this,R.string.note_wajib,Toast.LENGTH_SHORT).show();

                break;
            case R.id.TanggalWrapper:
                if(isEditMode)
                    if(dtTarget.getTipe() == 1)
                        Toast.makeText(AddTargetActivity.this,R.string.note_wajib,Toast.LENGTH_SHORT).show();

                break;
            case R.id.judulWrapper:
                if(isEditMode)
                    if(dtTarget.getTipe() == 1)
                        Toast.makeText(AddTargetActivity.this,R.string.note_wajib,Toast.LENGTH_SHORT).show();
                break;
            case R.id.editText2:
                datePickerDialog.show();
                break;
            case R.id.editText3:
                timePickerDialog.show();
                break;
            case R.id.suaraWrapper:
                Intent intent = new Intent(AddTargetActivity.this,SoundListActivity.class);
                startActivityForResult(intent,10001);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                break;
        }
    }

    private ArrayList<String> getSpinnerData()
    {
        ArrayList<String> data = new ArrayList<>();
        try
        {
            data.add(getString(R.string.hanya_sekali));
            data.add(getString(R.string.setiap_hari));
            data.add(getString(R.string.seminggu_sekali));
            data.add(getString(R.string.sebulan_sekali));
            data.add(getString(R.string.setahun_sekali));
        }
        catch (Exception ex){}
        return data;
    }

    private ArrayList<String> getWaktuSpinnerData()
    {
        ArrayList<String> data = new ArrayList<>();
        try
        {
            data.add(getString(R.string.tepat_waktu));
            data.add(getString(R.string.lima_menit));
            data.add(getString(R.string.sepuluh_menit));
            data.add(getString(R.string.limabelas_menit));
        }
        catch (Exception ex){}

        return data;
    }

    private void initEditView()
    {

        txtDate.setText(Shared.dateformatAdd.format(dtTarget.getWaktu()));
        txtTime.setText(Shared.dateformatTime.format(dtTarget.getWaktu()));
        txtJudul.setText(dtTarget.getJudul());

        selectedDate = Shared.dateformatAdd.format(dtTarget.getWaktu());
        selectedTime = Shared.dateformatTime.format(dtTarget.getWaktu());

        Calendar c = Calendar.getInstance();
        c.setTime(dtTarget.getWaktu());
        datePickerDialog = new DatePickerDialog(this, dateListener, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
        timePickerDialog = new TimePickerDialog(this,timeListener,c.get(Calendar.HOUR_OF_DAY),c.get(Calendar.MINUTE),true);

        adapter.set(getSpinnerData());
        spinnerLoop.setSelection(dtTarget.getPengulangan() - 1);
        notif.setChecked(dtTarget.isNotifikasi());
        vibrasi.setChecked(dtTarget.isVibrasi());
        txtSound.setText(dtTarget.getSoundname());
        txtSound.setTag(dtTarget.getSounduri());

        spinnerWaktu.setSelection(dtTarget.getSoundtime() - 1);
        findViewById(R.id.notifDetailWrapper).setVisibility(dtTarget.isNotifikasi() ? View.VISIBLE : View.GONE);

        flow.post(new Runnable() {
            @Override
            public void run() {
                for(int i= 0;i < flow.getChildCount();i++)
                {
                    RelativeLayout wrapper  = (RelativeLayout)flow.getChildAt(i);
                    ImageView img = (ImageView)wrapper.getChildAt(0);
                    if((Integer)wrapper.getTag() == dtTarget.getIcon())
                    {
                        flow.clickedId = dtTarget.getIcon();
                        flow.clickedIds = wrapper.getId();
                        img.setEnabled(false);
                        wrapper.setBackgroundResource(R.drawable.border_layout_green);
                    }
                    else
                    {
                        wrapper.setBackgroundResource(R.drawable.border_layout);
                        img.setEnabled(true);
                    }

                }
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (loading != null) {
            loading.dismiss();
            loading = null;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        if (loading != null) {
            loading.dismiss();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK)
        {
            if(requestCode == 10001)
            {
                if(data != null)
                {
                    Bundle extras =  data.getExtras();
                    if(extras != null)
                    {
                        txtSound.setText(extras.getString("NAME"));
                        txtSound.setTag(extras.getString("URI"));
                    }

                }
            }
        }
    }
}
