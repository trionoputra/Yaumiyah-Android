package com.yondev.yaumiyah.fragment;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.yondev.yaumiyah.AddTargetActivity;
import com.yondev.yaumiyah.MainActivity;
import com.yondev.yaumiyah.R;
import com.yondev.yaumiyah.adapter.TargetListAdapter;
import com.yondev.yaumiyah.entity.Target;
import com.yondev.yaumiyah.sqlite.DatabaseManager;
import com.yondev.yaumiyah.sqlite.ds.TargetDataSource;
import com.yondev.yaumiyah.utils.Shared;

import java.util.ArrayList;

/**
 * Created by ThinkPad on 5/6/2017.
 */

public class TodayFragment extends Fragment implements View.OnClickListener {
    private View viewroot;
    private TargetListAdapter adapter;
    private LinearLayout addWrapper;
    private ListView lv;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        viewroot = inflater.inflate(R.layout.view_today, container, false);

        lv = (ListView)viewroot.findViewById(R.id.listView1);
        lv.setOnItemClickListener(lvOnClick);

        adapter = new TargetListAdapter(getActivity());

        lv.setAdapter(adapter);

        addWrapper = (LinearLayout)viewroot.findViewById(R.id.addWrapper);

        viewroot.findViewById(R.id.addButton).setOnClickListener(this);

        TextView t9 = (TextView)viewroot.findViewById(R.id.textView9);
        TextView t10 = (TextView)viewroot.findViewById(R.id.textView10);

        t9.setTypeface(Shared.appfontThin);
        t10.setTypeface(Shared.appfontBold);

        populateData();

        return viewroot;
    }

    private void populateData()
    {
        SQLiteDatabase db =  DatabaseManager.getInstance().openDatabase();
        TargetDataSource DS = new TargetDataSource(db);
        adapter.set(DS.getAll());
        DatabaseManager.getInstance().closeDatabase();

        if(adapter.getCount() == 0)
        {
            addWrapper.setVisibility(View.VISIBLE);
            lv.setVisibility(View.GONE);
        }
        else
        {
            addWrapper.setVisibility(View.GONE);
            lv.setVisibility(View.VISIBLE);
        }


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(getActivity() != null)
        {
            if(resultCode == getActivity().RESULT_OK)
            {
                if(requestCode == MainActivity.ADD_TARGET)
                {
                    populateData();
                }
            }
        }
    }

    private AdapterView.OnItemClickListener lvOnClick = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            if(getActivity() != null)
            {
                Target data = (Target)adapter.getItem(position);
                if(!data.isHasAction())
                {
                    Intent  intent = new Intent(getActivity(),AddTargetActivity.class);
                    intent.putExtra("code",data.getId());
                    startActivityForResult(intent,MainActivity.ADD_TARGET);
                    getActivity().overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                }
            }
        }
    };

    @Override
    public void onClick(View v) {
        Intent  intent = new Intent(getActivity(),AddTargetActivity.class);
        startActivityForResult(intent,MainActivity.ADD_TARGET);
        getActivity().overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }
}
