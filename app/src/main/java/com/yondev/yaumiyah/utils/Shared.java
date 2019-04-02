package com.yondev.yaumiyah.utils;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.graphics.Typeface;
import android.view.Display;
import android.view.WindowManager;
import java.text.SimpleDateFormat;
import java.util.Locale;

public final class Shared
{
	private static ContextWrapper instance;
	private static SharedPreferences pref;
	public static Typeface appfont;
	public static Typeface appfontBold;
	public static Typeface appfontLight;
	public static Typeface appfontThin;


	public static SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	public static SimpleDateFormat dateformatAdd = new SimpleDateFormat("dd/MM/yyyy");
	public static SimpleDateFormat datetimeformat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
	public static SimpleDateFormat dateformatTime = new SimpleDateFormat("HH:mm");
	public static SimpleDateFormat dateformatDate = new SimpleDateFormat("yyyy-MM-dd");

	public static SimpleDateFormat dateformatMonth = new SimpleDateFormat("dd MMMM",new java.util.Locale("id"));
	public static SimpleDateFormat dateformatDt = new SimpleDateFormat("dd",new java.util.Locale("id"));
	public static SimpleDateFormat dateformatDay = new SimpleDateFormat("EEEE",new java.util.Locale("id"));

	public static void initialize(Context base)
	{
		instance = new ContextWrapper(base);
		pref = instance.getSharedPreferences("com.yondev.yaumiyah", Context.MODE_PRIVATE);
		appfont = Typeface.createFromAsset(instance.getAssets(),"fonts/Roboto-Regular.ttf");
		appfontBold = Typeface.createFromAsset(instance.getAssets(),"fonts/Roboto-Bold.ttf");
		appfontLight = Typeface.createFromAsset(instance.getAssets(),"fonts/Roboto-Light.ttf");
		appfontThin = Typeface.createFromAsset(instance.getAssets(),"fonts/Roboto-Thin.ttf");
	}
	
	public static void write(String key, String value)
	{
		SharedPreferences.Editor editor = pref.edit();
		editor.putString(key, value);
		editor.commit();
	}
	
	public static String read(String key)
	{
		return Shared.read(key, null);
	}
	
	public static String read(String key, String defValue)
	{
		return pref.getString(key, defValue);
	}
	
	public static void clear()
	{
		SharedPreferences.Editor editor = pref.edit();
		editor.clear();
		editor.commit();
	}
	
	public static void clear(String key)
	{
		SharedPreferences.Editor editor = pref.edit();
		editor.remove(key);
		editor.commit();
	}
	
	public static Context getContext()
	{
		return instance.getBaseContext();
	}
	
	public static int DipToInt(int value)
	{
		return (int)(instance.getResources().getDisplayMetrics().density * value);
	}
	
	public static int getDisplayHeight()
	{
		
		 WindowManager wm = (WindowManager) instance.getSystemService(Context.WINDOW_SERVICE);
		 Display display = wm.getDefaultDisplay();
		 final int version = android.os.Build.VERSION.SDK_INT;
		 
		 if (version >= 13)
		 {
		     Point size = new Point();
		     display.getSize(size);
		    return size.y;
		 }
		 else
		 {
		     
		     return  display.getHeight();
		 }
	}
	
	public static int getDisplayWidth()
	{
		 WindowManager wm = (WindowManager) instance.getSystemService(Context.WINDOW_SERVICE);
		 Display display = wm.getDefaultDisplay();
		 final int version = android.os.Build.VERSION.SDK_INT;
		 
		 if (version >= 13)
		 {
		     Point size = new Point();
		     display.getSize(size);
		     return size.x;
		 }
		 else
		 {
		     
		     return  display.getWidth();
		 }
	}
}

