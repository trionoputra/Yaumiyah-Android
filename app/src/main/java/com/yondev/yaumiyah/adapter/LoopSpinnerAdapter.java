package com.yondev.yaumiyah.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.yondev.yaumiyah.R;
import com.yondev.yaumiyah.utils.Shared;

public class LoopSpinnerAdapter extends BaseAdapter {
	private ArrayList<String> data;
	private Context c;
	private static LayoutInflater inflater = null;
	
	public LoopSpinnerAdapter(Context c, ArrayList<String> data)
	{
		this.c = c;
		this.data = data;
		inflater = (LayoutInflater)c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return this.data.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return data.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	public void set(ArrayList<String> list) {
		data = list;
		notifyDataSetChanged();
	}

	public static class ViewHolder{
	     public TextView text;
	}

	 
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		View vi = convertView;
        ViewHolder holder;
        if(convertView==null){
            vi = inflater.inflate(R.layout.loop_spinner_item, null);
            holder=new ViewHolder();
            holder.text = (TextView)vi.findViewById(R.id.textView1);
            vi.setTag(holder);
        }
        else
            holder=(ViewHolder)vi.getTag();
      	
        holder.text.setText(data.get(position));
        return vi;
	}
	
	@Override
	public View getDropDownView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		View vi = convertView;
        ViewHolder holder;
        if(convertView==null){
            vi = inflater.inflate(R.layout.loop_spinner_item_dropdow, null);
            holder=new ViewHolder();
            holder.text = (TextView)vi.findViewById(R.id.textView1);
			holder.text.setTypeface(Shared.appfontBold);
			vi.setTag(holder);
        }
        else
            holder=(ViewHolder)vi.getTag();

		holder.text.setText(data.get(position));
        return vi;
	}
}
