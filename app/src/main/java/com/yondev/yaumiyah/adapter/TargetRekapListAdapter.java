package com.yondev.yaumiyah.adapter;

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

import com.yondev.yaumiyah.R;
import com.yondev.yaumiyah.entity.Target;
import com.yondev.yaumiyah.entity.TargetDetail;
import com.yondev.yaumiyah.sqlite.DatabaseManager;
import com.yondev.yaumiyah.sqlite.ds.TargetDataSource;
import com.yondev.yaumiyah.utils.Constant;
import com.yondev.yaumiyah.utils.Shared;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class TargetRekapListAdapter extends BaseAdapter {

    private List<Target> dtList = new ArrayList<Target>();
    private Activity context;
    private LayoutInflater inflater;
    public TargetRekapListAdapter(Activity context) {
        this.context = context;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public TargetRekapListAdapter(Activity context, List<Target> data) {
     
        this.context = context;
        this.dtList = data;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    
    private class ViewHolder {
        TextView title;
        ImageButton sunday;
        ImageButton monday;
        ImageButton tuesday;
        ImageButton wednesday;
        ImageButton thursday;
        ImageButton friday;
        ImageButton saturday;
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
        	vi = inflater.inflate(R.layout.rekap_1_minggu_item, null);
            holder = new ViewHolder();

            holder.title = (TextView) vi.findViewById(R.id.textView7);

            holder.sunday = (ImageButton) vi.findViewById(R.id.imgMinggu);
            holder.monday = (ImageButton) vi.findViewById(R.id.imgSenin);
            holder.tuesday = (ImageButton) vi.findViewById(R.id.imgSelasa);
            holder.wednesday  = (ImageButton) vi.findViewById(R.id.imgRabu);
            holder.thursday = (ImageButton) vi.findViewById(R.id.imgKamis);
            holder.friday = (ImageButton) vi.findViewById(R.id.imgJumat);
            holder.saturday = (ImageButton) vi.findViewById(R.id.imgSabtu);

            vi.setTag(holder);
        } else {
        	 holder=(ViewHolder)vi.getTag();
        }

        holder.sunday.setImageResource(R.mipmap.ic_kosong);
        holder.monday.setImageResource(R.mipmap.ic_kosong);
        holder.tuesday.setImageResource(R.mipmap.ic_kosong);
        holder.wednesday.setImageResource(R.mipmap.ic_kosong);
        holder.thursday.setImageResource(R.mipmap.ic_kosong);
        holder.friday.setImageResource(R.mipmap.ic_kosong);
        holder.saturday.setImageResource(R.mipmap.ic_kosong);

        final Target data = (Target) getItem(position);
        holder.title.setText(data.getJudul());

        Calendar c = Calendar.getInstance();
        for (TargetDetail dt : data.getDetails())
        {
            c.setTime(dt.getUpdated_date());
            if(dt.getStatus() == Constant.STATUS_OK)
            {
                if(c.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY)
                    holder.sunday.setImageResource(R.mipmap.ic_checklist);
                if(c.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY)
                    holder.monday.setImageResource(R.mipmap.ic_checklist);
                if(c.get(Calendar.DAY_OF_WEEK) == Calendar.TUESDAY)
                    holder.tuesday.setImageResource(R.mipmap.ic_checklist);
                if(c.get(Calendar.DAY_OF_WEEK) == Calendar.WEDNESDAY)
                    holder.wednesday.setImageResource(R.mipmap.ic_checklist);
                if(c.get(Calendar.DAY_OF_WEEK) == Calendar.THURSDAY)
                    holder.thursday.setImageResource(R.mipmap.ic_checklist);
                if(c.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY)
                    holder.friday.setImageResource(R.mipmap.ic_checklist);
                if(c.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY)
                    holder.saturday.setImageResource(R.mipmap.ic_checklist);
            }
            else if(dt.getStatus() == Constant.STATUS_NO)
            {
                if(c.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY)
                    holder.sunday.setImageResource(R.mipmap.ic_delete);
                if(c.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY)
                    holder.monday.setImageResource(R.mipmap.ic_delete);
                if(c.get(Calendar.DAY_OF_WEEK) == Calendar.TUESDAY)
                    holder.tuesday.setImageResource(R.mipmap.ic_delete);
                if(c.get(Calendar.DAY_OF_WEEK) == Calendar.WEDNESDAY)
                    holder.wednesday.setImageResource(R.mipmap.ic_delete);
                if(c.get(Calendar.DAY_OF_WEEK) == Calendar.THURSDAY)
                    holder.thursday.setImageResource(R.mipmap.ic_delete);
                if(c.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY)
                    holder.friday.setImageResource(R.mipmap.ic_delete);
                if(c.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY)
                    holder.saturday.setImageResource(R.mipmap.ic_delete);
            }
        }
        return vi;
    }
}