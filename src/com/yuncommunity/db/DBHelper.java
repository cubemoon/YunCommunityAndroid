package com.yuncommunity.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.yuncommunity.conf.Constant;

/**
 * 数据库帮助类
 * 
 * @author oldfeel
 * 
 *         Create on: 2014年7月23日
 */
public class DBHelper extends OrmLiteSqliteOpenHelper {
	public DBHelper(Context context) {
		super(context, Constant.DB_NAME, null, Constant.DB_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase sqliteDatabase,
			ConnectionSource connectionSource) {
	}

	@Override
	public void onUpgrade(SQLiteDatabase sqliteDatabase,
			ConnectionSource connectionSource, int oldVer, int newVer) {
	}

}
