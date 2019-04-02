package com.yondev.yaumiyah.adapter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.yondev.yaumiyah.R;
import com.yondev.yaumiyah.entity.Target;
import com.yondev.yaumiyah.entity.TargetDetail;
import com.yondev.yaumiyah.sqlite.DatabaseManager;
import com.yondev.yaumiyah.sqlite.ds.TargetDataSource;
import com.yondev.yaumiyah.utils.Constant;
import com.yondev.yaumiyah.utils.Shared;

public class TargetListAdapter extends BaseAdapter {
 
    private List<Target> dtList = new ArrayList<Target>();
    private Activity context;
    private LayoutInflater inflater;
    public TargetListAdapter(Activity context) {
        this.context = context;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    
    public TargetListAdapter(Activity context, List<Target> data) {
     
        this.context = context;
        this.dtList = data;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    
    private class ViewHolder {
        TextView title;
        ImageView icon;
        ImageButton btnCeklist;
        ImageButton btnEx;
        Button btnUndo;
        RelativeLayout wrapper;
    }
 
    public int getCount() {
        return dtList.size();
    }
    
    public List<Target> getData() {
        return dtList;
    }
    
  
    public void set(List<Target> list) {
    	dtList = list;
        notifyDataSetChanged();
    }
    
    public void addOrReplace(Target data) {
    	boolean hasData = false;
    	for (int i = 0; i < dtList.size(); i++) 
    	{
    		if (dtList.get(i).getId() == data.getId())
    		{
    			dtList.set(i, data);
    			hasData = true;
    		}
    	}
    	
    	if(!hasData)
    		dtList.add(data);
    	
        notifyDataSetChanged();
    }
    
    public void remove(Target data) {
    	dtList.remove(data);
        notifyDataSetChanged();
    }
    public void removeAll() {
    	dtList = new ArrayList<Target>();
        notifyDataSetChanged();
    }
    
    public void removeByID(int id) {
    	for (int i = 0; i < dtList.size(); i++) {
			if(dtList.get(i).getId() == id)
				dtList.remove(i);
		};
        notifyDataSetChanged();
    }

    public void addMany(List<Target>  dt) {
        for (int i = 0; i < dt.size(); i++)
        {
            dtList.add(dt.get(i));
        }

        notifyDataSetChanged();
    }

    public void add(Target data) {
    	dtList.add(data);
        notifyDataSetChanged();
    }
    
    public void insert(Target data,int index) {
    	dtList.add(index, data);
        notifyDataSetChanged();
    }
 
    public Object getItem(int position) {
        return dtList.get(position);
    }
 
    public long getItemId(int position) {
        return 0;
    }
    
 
    public View getView(final int position, View convertView, ViewGroup parent) {
        View vi = convertView;
        ViewHolder holder;
        
        if (convertView == null) {
        	vi = inflater.inflate(R.layout.target_item, null);
            holder = new ViewHolder();

            holder.title = (TextView) vi.findViewById(R.id.textView7);
            holder.icon = (ImageView) vi.findViewById(R.id.imageView2);
            holder.btnCeklist = (ImageButton) vi.findViewById(R.id.imageButton2);
            holder.btnEx = (ImageButton) vi.findViewById(R.id.imageButton);
            holder.wrapper  = (RelativeLayout) vi.findViewById(R.id.listWrapper);
            holder.btnUndo = (Button) vi.findViewById(R.id.btnUndo);
            holder.title.setTypeface(Shared.appfont);
            holder.btnUndo.setTypeface(Shared.appfontBold);

            vi.setTag(holder);
        } else {
        	 holder=(ViewHolder)vi.getTag();
        }

        final Target data = (Target) getItem(position);

        if(data.getJudul().length() >= 30)
            holder.title.setText(data.getJudul().substring(0,29)+"...");
        else
            holder.title.setText(data.getJudul());

        holder.icon.setImageResource(data.getIcon());
        holder.btnCeklist.setEnabled(true);
        holder.btnEx.setEnabled(true);
        holder.btnEx.setVisibility(View.VISIBLE);
        holder.btnCeklist.setVisibility(View.VISIBLE);
        holder.wrapper.setBackgroundResource(0);
        holder.title.setTextColor(ContextCompat.getColor(context, R.color.abu_tua));
        holder.btnUndo.setVisibility(View.GONE);
        if(data.isHasAction())
        {
            holder.wrapper.setBackgroundResource(R.color.abu_muda);
            holder.title.setTextColor(ContextCompat.getColor(context, R.color.abu_sedang));
            holder.btnUndo.setVisibility(View.VISIBLE);
            if(data.getLaststatus() == Constant.STATUS_NO)
            {
                holder.btnEx.setEnabled(false);
                holder.btnCeklist.setVisibility(View.GONE);
            }
            else if(data.getLaststatus() == Constant.STATUS_OK)
            {
                holder.btnCeklist.setEnabled(false);
                holder.btnEx.setVisibility(View.GONE);
            }
        }

        holder.btnEx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db =  DatabaseManager.getInstance().openDatabase();
                TargetDataSource DS = new TargetDataSource(db);

                TargetDetail detail = DS.getDetail(data.getId(),Shared.dateformatDate.format(new Date()));
                detail.setId_target(data.getId());
                detail.setUpdated_date(new Date());
                detail.setStatus(Constant.STATUS_NO);
                detail.setTemp(false);
                detail.setNote("");

                if(detail.getId() != 0)
                    DS.updateDetail(detail,detail.getId());
                else
                    DS.insertDetail(detail);

                DatabaseManager.getInstance().closeDatabase();
                data.setHasAction(true);
                data.setLaststatus(detail.getStatus());
                notifyDataSetChanged();
            }
        });

        holder.btnCeklist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db =  DatabaseManager.getInstance().openDatabase();
                TargetDataSource DS = new TargetDataSource(db);
                TargetDetail detail = DS.getDetail(data.getId(),Shared.dateformatDate.format(new Date()));
                detail.setId_target(data.getId());
                detail.setUpdated_date(new Date());
                detail.setStatus(Constant.STATUS_OK);
                detail.setTemp(false);
                detail.setNote("");

                if(detail.getId() != 0)
                    DS.updateDetail(detail,detail.getId());
                else
                    DS.insertDetail(detail);

                DatabaseManager.getInstance().closeDatabase();
                data.setHasAction(true);
                data.setLaststatus(detail.getStatus());
                notifyDataSetChanged();
            }
        });

        holder.btnUndo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db =  DatabaseManager.getInstance().openDatabase();
                TargetDataSource DS = new TargetDataSource(db);
                TargetDetail detail = DS.getDetail(data.getId(),Shared.dateformatDate.format(new Date()));
                DS.deleteDetail(detail.getId());
                DatabaseManager.getInstance().closeDatabase();
                data.setHasAction(false);
                data.setLaststatus(0);
                notifyDataSetChanged();
            }
        });

        return vi;
    }
}