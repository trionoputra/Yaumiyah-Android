package com.yondev.yaumiyah.fragment;

import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Spinner;

import com.yondev.yaumiyah.AddTargetActivity;
import com.yondev.yaumiyah.R;
import com.yondev.yaumiyah.adapter.RekapFilterSpinnerAdapter;
import com.yondev.yaumiyah.adapter.TargetRekapListAdapter;
import com.yondev.yaumiyah.entity.Target;
import com.yondev.yaumiyah.sqlite.DatabaseManager;
import com.yondev.yaumiyah.sqlite.ds.TargetDataSource;
import com.yondev.yaumiyah.widget.LoadingDialog;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by ThinkPad on 5/6/2017.
 */

public class RecapFragment extends Fragment{
    private View viewroot;
    private RekapFilterSpinnerAdapter spinneradapter;
    private ListView lv;
    private Spinner spinnerFilter;

    private TargetRekapListAdapter adapter;
    private LoadingDialog loading;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        viewroot = inflater.inflate(R.layout.view_recap, container, false);

        spinneradapter = new RekapFilterSpinnerAdapter(getActivity(),getSpinnerData());
        lv = (ListView)viewroot.findViewById(R.id.listView1);
        spinnerFilter = (Spinner)viewroot.findViewById(R.id.spinner);

        spinnerFilter.setAdapter(spinneradapter);

        adapter = new TargetRekapListAdapter(getActivity());
        lv.setAdapter(adapter);

        loading = new LoadingDialog(getActivity());

        GetAsync get = new GetAsync(1);
        get.execute();

        spinnerFilter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                int type = position + 1;
                if(position == 4)
                    type = 0;

                GetAsync get = new GetAsync(type);
                get.execute();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        return viewroot;
    }

    public class GetAsync extends AsyncTask<String, String, ArrayList<Target>>
    {
        private int type = 0;
        public GetAsync(int type){
            this.type = type;
        }
        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            if(loading != null)
                loading.show();
            else
            {
                loading = new LoadingDialog(getActivity());
                loading.show();
            }
        }

        @Override
        protected ArrayList<Target> doInBackground(String... params) {
            // TODO Auto-generated method stub
            ArrayList<Target> result = new ArrayList<Target>();
                SQLiteDatabase db =  DatabaseManager.getInstance().openDatabase();
                TargetDataSource DS = new TargetDataSource(db);
                result = DS.getRekap(type,0,0);
                DatabaseManager.getInstance().closeDatabase();

            return result;
        }

        @Override
        protected void onPostExecute(ArrayList<Target> result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);
            loading.dismiss();
            adapter.set(result);

        }
    }

    private ArrayList<String> getSpinnerData()
    {
        ArrayList<String> data = new ArrayList<>();
        try
        {
            data.add("Rekap Minggu ini");
            data.add("Rekap Minggu kemarin");
            data.add("Rekap Bulan ini");
            data.add("Rekap Bulan kemarin");
            data.add("Rekap dari hari pertama");
        }
        catch (Exception ex){}

        return data;
    }
}
