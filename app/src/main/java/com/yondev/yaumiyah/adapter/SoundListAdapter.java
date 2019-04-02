package com.yondev.yaumiyah.adapter;

import android.app.Activity;
import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.yondev.yaumiyah.R;
import com.yondev.yaumiyah.entity.Sound;
import java.util.ArrayList;
import java.util.List;

public class SoundListAdapter extends BaseAdapter {

    private List<Sound> dtList = new ArrayList<Sound>();
    private Activity context;
    private LayoutInflater inflater;

    public SoundListAdapter(Activity context) {
        this.context = context;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public SoundListAdapter(Activity context, List<Sound> data) {
     
        this.context = context;
        this.dtList = data;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    
    private class ViewHolder {
        TextView title;
        Button btnPilih;
    }
 
    public int getCount() {
        return dtList.size();
    }
    
    public List<Sound> getData() {
        return dtList;
    }
    
  
    public void set(List<Sound> list) {
    	dtList = list;
        notifyDataSetChanged();
    }
    

    public void remove(Sound data) {
    	dtList.remove(data);
        notifyDataSetChanged();
    }
    public void removeAll() {
    	dtList = new ArrayList<Sound>();
        notifyDataSetChanged();
    }


    public void add(Sound data) {
    	dtList.add(data);
        notifyDataSetChanged();
    }
    
    public void insert(Sound data,int index) {
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
        	vi = inflater.inflate(R.layout.sound_item, null);
            holder = new ViewHolder();

            holder.title = (TextView) vi.findViewById(R.id.textView);
            holder.btnPilih = (Button) vi.findViewById(R.id.button);

            vi.setTag(holder);
        } else {
        	 holder=(ViewHolder)vi.getTag();
        }

        final Sound data = (Sound) getItem(position);
        if(data.getName().length() >= 30)
            holder.title.setText(data.getName().substring(0,29)+"...");
        else
            holder.title.setText(data.getName());

        holder.btnPilih.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listener != null)
                    listener.onSelect(data);
            }
        });

        return vi;
    }

    private selectListener listener;
    public void setChangeListener(selectListener listener)
    {
        this.listener = listener;
    }

    public interface selectListener {
        public void onSelect(Sound data);
    }

}