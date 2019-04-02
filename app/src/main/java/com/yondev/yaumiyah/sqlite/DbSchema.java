package com.yondev.yaumiyah.sqlite;


import com.yondev.yaumiyah.R;

public interface DbSchema {
	
	String DB_NAME = "com_yondev_yaumiyah.db";
	int DB_VERSION = 1;
	
	String TBL_TARGET = "target";
	String COL_TARGET_CODE = "code";
	String COL_TARGET_TITLE = "title";
	String COL_TARGET_WAKTU = "waktu";
	String COL_TARGET_PENGULANGAN = "pengulangan";
	String COL_TARGET_TIPE = "tipe";
	String COL_TARGET_LAST_NOTIF = "last_notif";
	String COL_TARGET_CREATE_DATE = "create_date";
	String COL_TARGET_IS_DELETED = "is_deleted";
	String COL_TARGET_NOTIFICATION = "notification";
	String COL_TARGET_NOTE = "note";
	String COL_TARGET_ICON = "icon";
	String COL_TARGET_SOUND_TIME = "soundtime";
	String COL_TARGET_SOUND_NAME = "soundname";
	String COL_TARGET_SOUND_URI = "sounduri";
	String COL_TARGET_VIBRASI = "vibrasi";

	String CREATE_TBL_TARGET = "CREATE TABLE "
								+ TBL_TARGET
								+ "(" 
									+ COL_TARGET_CODE  + " INTEGER PRIMARY KEY AUTOINCREMENT,"
									+ COL_TARGET_TITLE + " TEXT,"
									+ COL_TARGET_WAKTU + " DATETIME,"
									+ COL_TARGET_PENGULANGAN + " INTEGER,"
									+ COL_TARGET_TIPE + " INTEGER,"
									+ COL_TARGET_LAST_NOTIF + " DATETIME,"
									+ COL_TARGET_CREATE_DATE + " DATETIME,"
									+ COL_TARGET_IS_DELETED + " INTEGER,"
									+ COL_TARGET_NOTIFICATION + " INTEGER,"
									+ COL_TARGET_ICON + " INTEGER,"
									+ COL_TARGET_NOTE + " TEXT,"
									+ COL_TARGET_SOUND_NAME + " TEXT,"
									+ COL_TARGET_SOUND_TIME + " INTEGER,"
									+ COL_TARGET_SOUND_URI + " TEXT,"
									+ COL_TARGET_VIBRASI + " INTEGER"
								+ ");";

	String TBL_TARGET_DETAIL = "target_detail";
	String COL_TARGET_DETAIL_CODE = "code";
	String COL_TARGET_DETAIL_CODE_TARGET = "code_target";
	String COL_TARGET_DETAIL_STATUS = "status";
	String COL_TARGET_DETAIL_UPDATE_DATE = "update_date";
	String COL_TARGET_DETAIL_NOTE = "note";
	String COL_TARGET_DETAIL_TEMP = "tmp";

	String CREATE_TBL_TARGET_DETAIL = "CREATE TABLE "
								+ TBL_TARGET_DETAIL
								+ "(" 
									+ COL_TARGET_DETAIL_CODE  + " INTEGER PRIMARY KEY AUTOINCREMENT,"
									+ COL_TARGET_DETAIL_CODE_TARGET + " INTEGER,"
									+ COL_TARGET_DETAIL_STATUS + " INTEGER,"
									+ COL_TARGET_DETAIL_UPDATE_DATE + " DATETIME,"
									+ COL_TARGET_DETAIL_TEMP + " INTEGER,"
									+ COL_TARGET_DETAIL_NOTE + " TEXT"
								+ ");";
	
	
	
	String TBL_SETTING = "settings";	
	String COL_SETTING_CODE = "code";
	String COL_SETTING_NAME = "name";
	String COL_SETTING_VALUE = "value";
			
	String CREATE_TBL_SETTING = "CREATE TABLE "
								+ TBL_SETTING 
								+ "(" 
									+ COL_SETTING_CODE  + " TEXT PRIMARY KEY," 
									+ COL_SETTING_NAME + " TEXT," 
									+ COL_SETTING_VALUE + " TEXT"
								+ ");";

/*	String INSERT_TBL_TARGET = "INSERT INTO "+ TBL_TARGET + " VALUES (1,'Subuh','2017-05-10 04:35:00',2,1,NULL,'2017-05-10 04:35:00',0,1,'',"+ R.mipmap.ic_salat_default +") "
			+ ", (2,'Dhuhur','2017-05-10 11:51:00',2,1,NULL,'2017-05-10 04:35:00',0,1,'',"+ R.mipmap.ic_salat_default +") "
			+ ", (3,'Ashar','2017-05-10 15:13:00',2,1,NULL,'2017-05-10 04:35:00',0,1,'',"+ R.mipmap.ic_salat_default +") "
			+ ", (5,'Maghrib','2017-05-10 17:47:00',2,1,NULL,'2017-05-10 04:35:00',0,1,'',"+ R.mipmap.ic_salat_default +") "
			+ ", (4,'Isya','2017-05-10 18:59:00',2,1,NULL,'2017-05-10 04:35:00',0,1,'',"+ R.mipmap.ic_salat_default +") " ;
*/
	String DROP_TBL_TARGET = "DROP TABLE IF EXISTS "+ TBL_TARGET;
	String DROP_TBL_TARGET_DETAIL = "DROP TABLE IF EXISTS "+ TBL_TARGET_DETAIL;
	String DROP_TBL_SETTING = "DROP TABLE IF EXISTS "+ TBL_SETTING;
}
