package com.yondev.yaumiyah.sqlite.ds;

import java.util.ArrayList;
import java.util.Date;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.yondev.yaumiyah.entity.Target;
import com.yondev.yaumiyah.entity.TargetDetail;
import com.yondev.yaumiyah.sqlite.DbSchema;
import com.yondev.yaumiyah.utils.Shared;

import org.joda.time.DateTime;
import org.joda.time.DateTimeConstants;

public class TargetDataSource {
	private SQLiteDatabase db;
	public TargetDataSource(SQLiteDatabase db)
	{
		this.db = db;
	}
	
	public long truncate()
	{
		db.delete(DbSchema.TBL_TARGET_DETAIL,null,null);
		return db.delete(DbSchema.TBL_TARGET,null,null);
	}
	
	public Target get(int code) {

		Target item = new Target();
		String selectQuery = 	" SELECT  * FROM " + DbSchema.TBL_TARGET   +
								" Where " +DbSchema.COL_TARGET_CODE + " = '"+code+"' ";
		
		Cursor c = db.rawQuery(selectQuery, null);
		if (c.moveToFirst()) {
			do {
			
				item.setId(c.getInt(c.getColumnIndex(DbSchema.COL_TARGET_CODE)));
				item.setJudul(c.getString(c.getColumnIndex(DbSchema.COL_TARGET_TITLE)));
				try { item.setWaktu(Shared.dateformat.parse(c.getString(c.getColumnIndex(DbSchema.COL_TARGET_WAKTU)))); } catch (Exception e) {}
				try { item.setCreate_date(Shared.dateformat.parse(c.getString(c.getColumnIndex(DbSchema.COL_TARGET_CREATE_DATE)))); } catch (Exception e) {}
				try { item.setLast_notif(Shared.dateformat.parse(c.getString(c.getColumnIndex(DbSchema.COL_TARGET_LAST_NOTIF)))); } catch (Exception e) {}
				item.setTipe(c.getInt(c.getColumnIndex(DbSchema.COL_TARGET_TIPE)));
				item.setPengulangan(c.getInt(c.getColumnIndex(DbSchema.COL_TARGET_PENGULANGAN)));
				item.setIs_deteted(c.getInt(c.getColumnIndex(DbSchema.COL_TARGET_IS_DELETED)) == 1 ? true : false);
				item.setNotifikasi(c.getInt(c.getColumnIndex(DbSchema.COL_TARGET_NOTIFICATION)) == 1 ? true : false);
				item.setNote(c.getString(c.getColumnIndex(DbSchema.COL_TARGET_NOTE)));
				item.setIcon(c.getInt(c.getColumnIndex(DbSchema.COL_TARGET_ICON)));
				item.setVibrasi(c.getInt(c.getColumnIndex(DbSchema.COL_TARGET_VIBRASI)) == 1 ? true : false);
				item.setSoundtime(c.getInt(c.getColumnIndex(DbSchema.COL_TARGET_SOUND_TIME)));
				item.setSoundname(c.getString(c.getColumnIndex(DbSchema.COL_TARGET_SOUND_NAME)));
				item.setSounduri(c.getString(c.getColumnIndex(DbSchema.COL_TARGET_SOUND_URI)));

				String selectQueryDetail =  " SELECT  *  FROM " + DbSchema.TBL_TARGET_DETAIL  +
											" WHERE " +DbSchema.COL_TARGET_DETAIL_CODE_TARGET + " = '"+code+"'";

				Cursor cDetail = db.rawQuery(selectQueryDetail, null);
				
				ArrayList<TargetDetail> details = new ArrayList<TargetDetail>();
				if (cDetail.moveToFirst()) {
					do {

						TargetDetail  dt = new TargetDetail();
						dt.setId(cDetail.getInt(cDetail.getColumnIndex(DbSchema.COL_TARGET_DETAIL_CODE)));
						dt.setStatus(cDetail.getInt(cDetail.getColumnIndex(DbSchema.COL_TARGET_DETAIL_STATUS)));
						try { dt.setUpdated_date(Shared.dateformat.parse(cDetail.getString(cDetail.getColumnIndex(DbSchema.COL_TARGET_DETAIL_UPDATE_DATE)))); } catch (Exception e) {}
						dt.setNote(c.getString(c.getColumnIndex(DbSchema.COL_TARGET_DETAIL_NOTE)));

						details.add(dt);
					} while (cDetail.moveToNext());
				}
				
				 item.setDetails(details);
			
			} while (c.moveToNext());
		}
		return item;
	}
	
	
	public ArrayList<Target> getAll() {
		return getAll(null,null,0,0);
	}
	
	public ArrayList<Target> getAll(ArrayList<String> filter,String orderby,int limit,int offset) {
		 
		ArrayList<Target> items = new ArrayList<Target>();
		
		String selectQuery = " SELECT  *  FROM " + DbSchema.TBL_TARGET ;
		String _orderBy =  " ORDER BY " + DbSchema.COL_TARGET_CREATE_DATE + " DESC ";
		if(orderby != null)
			_orderBy =  " ORDER BY " + orderby;

		String _where = " WHERE " +DbSchema.COL_TARGET_IS_DELETED + " = 0 ";

		String _limitOffset =  " ";
		if(limit != 0 || offset != 0)
		{
			_limitOffset = " LIMIT " + offset + "," + limit;
		}

		if(filter != null)
		{
			for (String fi : filter)
			{
				_where += " AND ("+fi+") ";
			}
		}

		selectQuery += _where;
		selectQuery += _orderBy;
		selectQuery += _limitOffset;

		Cursor c = db.rawQuery(selectQuery, null);
		if (c.moveToFirst()) {
			do {

				Target item = new Target();
				item.setId(c.getInt(c.getColumnIndex(DbSchema.COL_TARGET_CODE)));
				item.setJudul(c.getString(c.getColumnIndex(DbSchema.COL_TARGET_TITLE)));
				try { item.setWaktu(Shared.dateformat.parse(c.getString(c.getColumnIndex(DbSchema.COL_TARGET_WAKTU)))); } catch (Exception e) {}
				try { item.setCreate_date(Shared.dateformat.parse(c.getString(c.getColumnIndex(DbSchema.COL_TARGET_CREATE_DATE)))); } catch (Exception e) {}
				try { item.setLast_notif(Shared.dateformat.parse(c.getString(c.getColumnIndex(DbSchema.COL_TARGET_LAST_NOTIF)))); } catch (Exception e) {}
				item.setTipe(c.getInt(c.getColumnIndex(DbSchema.COL_TARGET_TIPE)));
				item.setPengulangan(c.getInt(c.getColumnIndex(DbSchema.COL_TARGET_PENGULANGAN)));
				item.setIs_deteted(c.getInt(c.getColumnIndex(DbSchema.COL_TARGET_IS_DELETED)) == 1 ? true : false);
				item.setNotifikasi(c.getInt(c.getColumnIndex(DbSchema.COL_TARGET_NOTIFICATION)) == 1 ? true : false);
				item.setNote(c.getString(c.getColumnIndex(DbSchema.COL_TARGET_NOTE)));
				item.setIcon(c.getInt(c.getColumnIndex(DbSchema.COL_TARGET_ICON)));
				item.setVibrasi(c.getInt(c.getColumnIndex(DbSchema.COL_TARGET_VIBRASI)) == 1 ? true : false);
				item.setSoundtime(c.getInt(c.getColumnIndex(DbSchema.COL_TARGET_SOUND_TIME)));
				item.setSoundname(c.getString(c.getColumnIndex(DbSchema.COL_TARGET_SOUND_NAME)));
				item.setSounduri(c.getString(c.getColumnIndex(DbSchema.COL_TARGET_SOUND_URI)));
				item.setHasAction(false);

				String selectQueryDetail =  " SELECT  *  FROM " + DbSchema.TBL_TARGET_DETAIL  +
						" WHERE " +DbSchema.COL_TARGET_DETAIL_CODE_TARGET + " = '"+item.getId()+"'";

				Cursor cDetail = db.rawQuery(selectQueryDetail, null);

				ArrayList<TargetDetail> details = new ArrayList<TargetDetail>();
				if (cDetail.moveToFirst()) {
					do {

						TargetDetail  dt = new TargetDetail();
						dt.setId(cDetail.getInt(cDetail.getColumnIndex(DbSchema.COL_TARGET_DETAIL_CODE)));
						dt.setStatus(cDetail.getInt(cDetail.getColumnIndex(DbSchema.COL_TARGET_DETAIL_STATUS)));
						try {

							dt.setUpdated_date(Shared.dateformat.parse(cDetail.getString(cDetail.getColumnIndex(DbSchema.COL_TARGET_DETAIL_UPDATE_DATE))));
							if(Shared.dateformatAdd.format(new Date()).equals(Shared.dateformatAdd.format(dt.getUpdated_date())))
							{
								item.setHasAction(true);
								item.setLaststatus(dt.getStatus());
							}

						} catch (Exception e) {}
						dt.setNote(cDetail.getString(cDetail.getColumnIndex(DbSchema.COL_TARGET_DETAIL_NOTE)));



						details.add(dt);
					} while (cDetail.moveToNext());
				}

				 item.setDetails(details);

				
				items.add(item);
			} while (c.moveToNext());
		}
	
		return items;
	}
	
	public long insert(Target item)
	{
		ContentValues values = new ContentValues();
		//values.put(DbSchema.COL_TARGET_CODE, item.getId());
		values.put(DbSchema.COL_TARGET_TITLE, item.getJudul());
		values.put(DbSchema.COL_TARGET_WAKTU,  Shared.dateformat.format(item.getWaktu()));
		values.put(DbSchema.COL_TARGET_PENGULANGAN , item.getPengulangan());
		values.put(DbSchema.COL_TARGET_TIPE, item.getTipe());
		values.put(DbSchema.COL_TARGET_LAST_NOTIF, item.getLast_notif() != null ? Shared.dateformat.format(item.getLast_notif()) : null);
		values.put(DbSchema.COL_TARGET_CREATE_DATE,  Shared.dateformat.format(item.getCreate_date()));
		values.put(DbSchema.COL_TARGET_IS_DELETED,  item.is_deteted() ? 1 : 0);
		values.put(DbSchema.COL_TARGET_NOTIFICATION, item.isNotifikasi() ? 1 : 0);
		values.put(DbSchema.COL_TARGET_ICON, item.getIcon());
		values.put(DbSchema.COL_TARGET_SOUND_NAME, item.getSoundname());
		values.put(DbSchema.COL_TARGET_SOUND_URI, item.getSounduri());
		values.put(DbSchema.COL_TARGET_SOUND_TIME, item.getSoundtime());
		values.put(DbSchema.COL_TARGET_VIBRASI, item.isVibrasi() ? 1 : 0);


		return db.insert(DbSchema.TBL_TARGET, null, values);
	}

	public int delete(int code)
	{
		db.delete(DbSchema.TBL_TARGET_DETAIL, DbSchema.COL_TARGET_DETAIL_CODE_TARGET + "= '" + code + "'", null);
		db.delete(DbSchema.TBL_TARGET, DbSchema.COL_TARGET_CODE + "= '" + code + "'", null);
		return 1;
	}

	public int deleteDetail(int code)
	{
		return db.delete(DbSchema.TBL_TARGET_DETAIL, DbSchema.COL_TARGET_DETAIL_CODE + "= '" + code + "'", null);
	}

	public long update(Target item,int lastCode)
	{
		ContentValues values = new ContentValues();

		values.put(DbSchema.COL_TARGET_TITLE, item.getJudul());
		values.put(DbSchema.COL_TARGET_WAKTU,  Shared.dateformat.format(item.getWaktu()));
		values.put(DbSchema.COL_TARGET_PENGULANGAN , item.getPengulangan());
		values.put(DbSchema.COL_TARGET_TIPE, item.getTipe());
		values.put(DbSchema.COL_TARGET_LAST_NOTIF, item.getLast_notif() != null ? Shared.dateformat.format(item.getLast_notif()) : null);
		values.put(DbSchema.COL_TARGET_CREATE_DATE,item.getCreate_date() != null ?   Shared.dateformat.format(item.getCreate_date()) : null);
		values.put(DbSchema.COL_TARGET_IS_DELETED,  item.is_deteted() ? 1 : 0);
		values.put(DbSchema.COL_TARGET_NOTIFICATION, item.isNotifikasi() ? 1 : 0);
		values.put(DbSchema.COL_TARGET_ICON, item.getIcon());

		values.put(DbSchema.COL_TARGET_SOUND_NAME, item.getSoundname());
		values.put(DbSchema.COL_TARGET_SOUND_URI, item.getSounduri());
		values.put(DbSchema.COL_TARGET_SOUND_TIME, item.getSoundtime());
		values.put(DbSchema.COL_TARGET_VIBRASI, item.isVibrasi() ? 1 : 0);

		return db.update(DbSchema.TBL_TARGET, values, DbSchema.COL_TARGET_CODE+"= '"+lastCode+"' ", null);
	}

	public long insertDetail(TargetDetail item)
	{

		ContentValues valuesDetails = new ContentValues();
		valuesDetails.put(DbSchema.COL_TARGET_DETAIL_STATUS, item.getStatus());
		valuesDetails.put(DbSchema.COL_TARGET_DETAIL_UPDATE_DATE,  Shared.dateformat.format(item.getUpdated_date()));
		valuesDetails.put(DbSchema.COL_TARGET_DETAIL_NOTE, item.getNote());
		valuesDetails.put(DbSchema.COL_TARGET_DETAIL_TEMP,  item.isTemp() ? 1 : 0);
		valuesDetails.put(DbSchema.COL_TARGET_DETAIL_CODE_TARGET, item.getId_target());
		return db.insert(DbSchema.TBL_TARGET_DETAIL, null, valuesDetails);
	}

	public long updateDetail(TargetDetail item,int lastCode)
	{
		ContentValues valuesDetails = new ContentValues();
		valuesDetails.put(DbSchema.COL_TARGET_DETAIL_STATUS, item.getStatus());
		valuesDetails.put(DbSchema.COL_TARGET_DETAIL_CODE_TARGET, item.getId_target());
		valuesDetails.put(DbSchema.COL_TARGET_DETAIL_UPDATE_DATE,  Shared.dateformat.format(item.getUpdated_date()));
		valuesDetails.put(DbSchema.COL_TARGET_DETAIL_NOTE, item.getNote());
		valuesDetails.put(DbSchema.COL_TARGET_DETAIL_TEMP,  item.isTemp() ? 1 : 0);


		return db.update(DbSchema.TBL_TARGET_DETAIL, valuesDetails, DbSchema.COL_TARGET_CODE+"= '"+lastCode+"' ", null);
	}

	public TargetDetail getDetail(int code,String date)
	{

		TargetDetail item = new TargetDetail();
		String selectQuery = 	" SELECT  * FROM " + DbSchema.TBL_TARGET_DETAIL   +
				" Where " +DbSchema.COL_TARGET_DETAIL_CODE_TARGET + " = '"+code+"' and strftime('%d/%m/%Y',"+DbSchema.COL_TARGET_DETAIL_UPDATE_DATE+") = strftime('%d/%m/%Y','"+date+"')";
		Cursor cDetail = db.rawQuery(selectQuery, null);
		if (cDetail.moveToFirst()) {
			do {

				TargetDetail  dt = new TargetDetail();
				dt.setId(cDetail.getInt(cDetail.getColumnIndex(DbSchema.COL_TARGET_DETAIL_CODE)));
				dt.setStatus(cDetail.getInt(cDetail.getColumnIndex(DbSchema.COL_TARGET_DETAIL_STATUS)));
				try { dt.setUpdated_date(Shared.dateformat.parse(cDetail.getString(cDetail.getColumnIndex(DbSchema.COL_TARGET_DETAIL_UPDATE_DATE)))); } catch (Exception e) {}
				dt.setNote(cDetail.getString(cDetail.getColumnIndex(DbSchema.COL_TARGET_DETAIL_NOTE)));
				dt.setTemp(cDetail.getInt(cDetail.getColumnIndex(DbSchema.COL_TARGET_DETAIL_TEMP) )== 1 ? true : false);
				dt.setId_target(cDetail.getInt(cDetail.getColumnIndex(DbSchema.COL_TARGET_DETAIL_CODE_TARGET)));
				item = dt;
			} while (cDetail.moveToNext());
		}

		return item;
	}

	public ArrayList<Target> getRekap(int tipe,int limit,int offset)
	{

		ArrayList<Target> target = new ArrayList<Target>();

		String _where = "";
		if(tipe == 1)
		{
			DateTime thisweek = DateTime.now();
			DateTime mondayThisweek = thisweek.withDayOfWeek(DateTimeConstants.MONDAY);
			_where = " WHERE update_date between  strftime('%Y-%m-%d 00:00:00','"+ Shared.dateformat.format(mondayThisweek.toDate()) +"') and strftime('%Y-%m-%d 23:59:59','"+Shared.dateformat.format(new Date())+"')   ";
		}
		else if(tipe == 2)
		{

			DateTime lastweek = DateTime.now();
			DateTime last = lastweek.minusWeeks(1);
			DateTime mondayLastweek = last.withDayOfWeek(DateTimeConstants.MONDAY);
			DateTime sundayLastweek = last.withDayOfWeek(DateTimeConstants.SUNDAY);

			_where = " WHERE update_date between  strftime('%Y-%m-%d 00:00:00','"+ Shared.dateformat.format(mondayLastweek.toDate()) +"') and strftime('%Y-%m-%d 23:59:59','"+Shared.dateformat.format(sundayLastweek.toDate())+"')   ";
		}
		else if(tipe == 3)
		{
			DateTime thismont = DateTime.now();
			_where = " WHERE update_date between  strftime('%Y-%m-01 00:00:00','"+ Shared.dateformat.format(thismont.toDate()) +"') and strftime('%Y-%m-%d 23:59:59','"+Shared.dateformat.format(thismont.toDate())+"')   ";

		}
		else if(tipe == 4)
		{
			DateTime lastmont = DateTime.now();
			DateTime lastm = lastmont.minusMonths(1);
			DateTime lastsss = lastm.dayOfMonth().withMaximumValue();
			_where = " WHERE update_date between  strftime('%Y-%m-01 00:00:00','"+ Shared.dateformat.format(lastm.toDate()) +"') and strftime('%Y-%m-%d 23:59:59','"+Shared.dateformat.format(lastsss.toDate())+"')   ";
		}

		String _orderBy =  " ORDER BY t." + DbSchema.COL_TARGET_TITLE + " ASC ";

		String _limitOffset =  " ";
		if(limit != 0 || offset != 0)
		{
			_limitOffset = " LIMIT " + offset + "," + limit;
		}

		String selectQuery = 	" SELECT  d.*,t."+DbSchema.COL_TARGET_TITLE+" FROM " + DbSchema.TBL_TARGET_DETAIL
								+ " d LEFT JOIN " + DbSchema.TBL_TARGET + " t ON t." + DbSchema.COL_TARGET_CODE + " = d." + DbSchema.COL_TARGET_DETAIL_CODE_TARGET;

		selectQuery += _where;
		selectQuery += _orderBy;
		selectQuery += _limitOffset;

		Cursor cDetail = db.rawQuery(selectQuery, null);

		ArrayList<TargetDetail> item = new ArrayList<TargetDetail>();
		if (cDetail.moveToFirst()) {
			do {

				TargetDetail  dt = new TargetDetail();
				dt.setId(cDetail.getInt(cDetail.getColumnIndex(DbSchema.COL_TARGET_DETAIL_CODE)));
				dt.setNama_target(cDetail.getString(cDetail.getColumnIndex(DbSchema.COL_TARGET_TITLE)));
				dt.setStatus(cDetail.getInt(cDetail.getColumnIndex(DbSchema.COL_TARGET_DETAIL_STATUS)));
				try { dt.setUpdated_date(Shared.dateformat.parse(cDetail.getString(cDetail.getColumnIndex(DbSchema.COL_TARGET_DETAIL_UPDATE_DATE)))); } catch (Exception e) {}
				dt.setNote(cDetail.getString(cDetail.getColumnIndex(DbSchema.COL_TARGET_DETAIL_NOTE)));
				dt.setTemp(cDetail.getInt(cDetail.getColumnIndex(DbSchema.COL_TARGET_DETAIL_TEMP) )== 1 ? true : false);
				dt.setId_target(cDetail.getInt(cDetail.getColumnIndex(DbSchema.COL_TARGET_DETAIL_CODE_TARGET)));

				item.add(dt);

				Target t = new Target();
				t.setJudul(dt.getNama_target());
				t.setId(dt.getId_target());

				if(target.indexOf(t) == -1)
					target.add(t);

			} while (cDetail.moveToNext());
		}

		for (Target dt : target)
		{
			ArrayList<TargetDetail> d = new ArrayList<TargetDetail>();
			for(TargetDetail det : item)
			{
				if(det.getId_target() == dt.getId())
					d.add(det);
			}
			dt.setDetails(d);
		}

		return target;
	}
}