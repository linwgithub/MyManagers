/**
 *
 */
package com.linw.mymanager.common.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class BaseDBHelper extends SQLiteOpenHelper {
	protected static final String LOG_TAG = BaseDBHelper.class.getSimpleName();

	protected SQLiteDatabase writableDB = null;
	protected SQLiteDatabase readableDB = null;

	public BaseDBHelper(Context context, String name, CursorFactory factory,
						int version) {
		super(context, name, factory, version);
	}

	@Override
	public SQLiteDatabase getReadableDatabase() {
		if (readableDB == null || readableDB.isOpen() == false) {
			readableDB = super.getReadableDatabase();
		}
		return readableDB;
	}

	@Override
	public SQLiteDatabase getWritableDatabase() {
		if (writableDB == null || writableDB.isOpen() == false) {
			writableDB = super.getWritableDatabase();
		}
		return writableDB;
	}

	public void closeDB() {
		if (readableDB != null && readableDB.isOpen() == true) {
			readableDB.close();
			readableDB = null;
		}

		if (writableDB != null && writableDB.isOpen() == true) {
			writableDB.close();
			writableDB = null;
		}
	}

	@Override
	public void onCreate(SQLiteDatabase db) { }

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) { }

	protected boolean isTableExist(SQLiteDatabase db, String tableName) {
		boolean bResult = false;
		if (tableName == null || tableName.length() == 0) {
			return false;
		}

		try {
			Cursor cursor = null;
			String sql = "select count(1) as c from sqlite_master where type='table' " +
					"and name='" + tableName.trim() + "'";
			cursor = db.rawQuery(sql, null);
			if (cursor.moveToNext()) {
				int count = cursor.getInt(0);
				if (count > 0) {
					bResult = true;
				}
			}
			cursor.close();
		} catch(Exception e) {
			Log.e("DBHelper", e.getMessage());
		}

		return bResult;
	}
}
