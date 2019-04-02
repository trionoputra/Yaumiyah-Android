package com.yondev.yaumiyah;

import android.content.Intent;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ScrollView;

import com.yondev.yaumiyah.adapter.SoundListAdapter;
import com.yondev.yaumiyah.entity.Sound;

import java.util.ArrayList;
import java.util.List;

public class SoundListActivity extends AppCompatActivity implements View.OnClickListener {
    private ListView lv;
    private SoundListAdapter adapter;
    private Ringtone ring;
    private Uri selectedUri;
    private String selectedname;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sound_list);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle(R.string.suara_notifikasi);

        lv = (ListView)findViewById(R.id.listView1);
        adapter = new SoundListAdapter(this,getSoundList());
        lv.setAdapter(adapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Sound data = (Sound)adapter.getItem(position);
                selectedUri = Uri.parse(data.getUri());
                selectedname = data.getName();
                try
                {
                    playSound(data.getUri());
                }
                catch (Exception ex){}
            }
        });

        final ScrollView scrollView = (ScrollView)findViewById(R.id.scrollView);
        lv.post(new Runnable() {
            @Override
            public void run() {
                scrollView.fullScroll(ScrollView.FOCUS_UP);
            }
        });

        findViewById(R.id.listAdzanMekkah).setOnClickListener(this);
        findViewById(R.id.listAdzanMadinah).setOnClickListener(this);
        findViewById(R.id.listAdzanMadinahFajr).setOnClickListener(this);
        findViewById(R.id.listBismillah).setOnClickListener(this);

        findViewById(R.id.btnAdzanMekkah).setOnClickListener(this);
        findViewById(R.id.btnAdzanMadinah).setOnClickListener(this);
        findViewById(R.id.btnAdzanMadinahFajr).setOnClickListener(this);
        findViewById(R.id.btnBismillah).setOnClickListener(this);

        selectedUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        selectedname = getString(R.string.default_notifikasi);

        adapter.setChangeListener(new SoundListAdapter.selectListener() {
            @Override
            public void onSelect(Sound data) {
                selectedUri = Uri.parse(data.getUri());
                selectedname = data.getName();
                Intent intent = getIntent();
                intent.putExtra("URI",selectedUri.toString());
                intent.putExtra("NAME",selectedname);
                setResult(RESULT_OK,intent);
                finish();
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        });
    }

    @Override
    public void onBackPressed() {
        if(ring != null)
        {
            if(ring.isPlaying())
                ring.stop();
        }
        super.onBackPressed();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private List<Sound> getSoundList()
    {
        List<Sound> data  = new ArrayList<>();
        RingtoneManager manager = new RingtoneManager(this);
        manager.setType(RingtoneManager.TYPE_NOTIFICATION);
        Cursor cursor = manager.getCursor();

        while (cursor.moveToNext()) {

            Sound s = new Sound();
            s.setId(cursor.getString(RingtoneManager.ID_COLUMN_INDEX));
            s.setName(cursor.getString(RingtoneManager.TITLE_COLUMN_INDEX));
            s.setUri(cursor.getString(RingtoneManager.URI_COLUMN_INDEX) + "/" +  cursor.getString(RingtoneManager.ID_COLUMN_INDEX));
            data.add(s);
        }

        return  data;
    }

    @Override
    public void onClick(View v) {
        Uri path = null;
         switch (v.getId())
         {
             case R.id.listAdzanMekkah:
                 path = Uri.parse(v.getTag().toString());
                 break;
             case R.id.listAdzanMadinah:
                 path = Uri.parse(v.getTag().toString());
                 break;
             case R.id.listAdzanMadinahFajr:
                 path = Uri.parse(v.getTag().toString());
                 break;
             case R.id.listBismillah:
                 path = Uri.parse(v.getTag().toString());
                 break;
             case R.id.btnAdzanMekkah:
                 selectedUri = Uri.parse(v.getTag().toString());
                 selectedname = v.getTag().toString();
                 break;
             case R.id.btnAdzanMadinah:
                 selectedUri = Uri.parse(v.getTag().toString());
                 selectedname = v.getTag().toString();
                 break;
             case R.id.btnAdzanMadinahFajr:
                 selectedUri = Uri.parse(v.getTag().toString());
                 selectedname = v.getTag().toString();
                 break;
             case R.id.btnBismillah:
                 selectedUri = Uri.parse(v.getTag().toString());
                 selectedname = v.getTag().toString();
                 break;
         }

         if(path != null)
             playSound("android.resource://com.yondev.yaumiyah/raw/"+path);
        else
         {
             if(ring != null)
             {
                 if(ring.isPlaying())
                     ring.stop();
             }

             Intent intent = getIntent();
             intent.putExtra("URI","android.resource://com.yondev.yaumiyah/raw/"+selectedUri.toString());
             intent.putExtra("NAME",selectedname);
             setResult(RESULT_OK,intent);
             finish();
             overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
         }
    }
    private void playSound(String path)
    {
        try
        {
            if(ring != null)
            {
                if(ring.isPlaying())
                    ring.stop();
            }

            ring =  RingtoneManager.getRingtone(SoundListActivity.this, Uri.parse(path));
            ring.play();
        }
        catch (Exception e)
        {

        }
    }
}
