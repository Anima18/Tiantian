package com.chris.tiantian.base.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.chris.tiantian.entity.PolicySignal;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jianjianhong on 19-10-11
 */
public class PolicySignalAction {
    public static final String TABLE_NAME = "PolicySignalAction";

    /**
     * 所有的字段
     * */
    private static final String[] AVAILABLE_PROJECTION = new String[]{
            PolicySignalColumns.policyId,
            PolicySignalColumns.policyName,
            PolicySignalColumns.direction,
            PolicySignalColumns.description,
            PolicySignalColumns.currPrice,
            PolicySignalColumns.stopPrice,
            PolicySignalColumns.time,
            PolicySignalColumns.policyKind,
            PolicySignalColumns.policyMarket,
            PolicySignalColumns.policyTimeLevel,
    };

    /**
     * 创建表的语句
     * */
    public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "("
            + PolicySignalColumns._ID + " INTEGER PRIMARY KEY,"
            + PolicySignalColumns.policyId + " INTEGER,"
            + PolicySignalColumns.policyName + " TEXT,"
            + PolicySignalColumns.direction + " TEXT,"
            + PolicySignalColumns.description + " TEXT,"
            + PolicySignalColumns.currPrice + " REAL,"
            + PolicySignalColumns.stopPrice + " REAL,"
            + PolicySignalColumns.time + " TEXT,"
            + PolicySignalColumns.policyKind + " TEXT,"
            + PolicySignalColumns.policyMarket + " TEXT,"
            + PolicySignalColumns.policyTimeLevel + " TEXT"
            + ")";

    /**
     * 删除表的语句
     * */
    public static final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;


    public static List<PolicySignal> query(SQLiteOpenHelper helper) {
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, AVAILABLE_PROJECTION, null, null, null, null, null, null);
        List<PolicySignal> locations = new ArrayList<>();
        while (cursor.moveToNext())
        {
            locations.add(getPolicySignalFromCursor(cursor));
        }
        cursor.close();
        return locations;
    }

    public static void insert(SQLiteOpenHelper helper, PolicySignal student) {
        SQLiteDatabase db = helper.getWritableDatabase();
        db.insert(TABLE_NAME, null, toContentValues(student));
    }


    private static PolicySignal getPolicySignalFromCursor(Cursor cursor) {
        PolicySignal policySignal = new PolicySignal();
        policySignal.policyId = cursor.getInt(cursor.getColumnIndex(PolicySignalColumns.policyId));
        policySignal.policyName = cursor.getString(cursor.getColumnIndex(PolicySignalColumns.policyName));
        policySignal.direction = cursor.getString(cursor.getColumnIndex(PolicySignalColumns.direction));
        policySignal.description = cursor.getString(cursor.getColumnIndex(PolicySignalColumns.description));
        policySignal.currPrice = cursor.getDouble(cursor.getColumnIndex(PolicySignalColumns.currPrice));
        policySignal.stopPrice = cursor.getDouble(cursor.getColumnIndex(PolicySignalColumns.stopPrice));
        policySignal.time = cursor.getString(cursor.getColumnIndex(PolicySignalColumns.time));
        policySignal.policyKind = cursor.getString(cursor.getColumnIndex(PolicySignalColumns.policyKind));
        policySignal.policyMarket = cursor.getString(cursor.getColumnIndex(PolicySignalColumns.policyMarket));
        policySignal.policyTimeLevel = cursor.getString(cursor.getColumnIndex(PolicySignalColumns.policyTimeLevel));
        return policySignal;
    }

    public static void clear(SQLiteOpenHelper helper) {
        SQLiteDatabase db = helper.getWritableDatabase();
        int count = db.delete(TABLE_NAME, null, null);
        Log.i("PolicySignalAction", "delete count:"+count);
    }

    private static ContentValues toContentValues(PolicySignal provider) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(PolicySignalColumns.policyId, provider.policyId);
        contentValues.put(PolicySignalColumns.policyName, provider.policyName);
        contentValues.put(PolicySignalColumns.direction, provider.direction);
        contentValues.put(PolicySignalColumns.description, provider.description);
        contentValues.put(PolicySignalColumns.currPrice, provider.currPrice);
        contentValues.put(PolicySignalColumns.stopPrice, provider.stopPrice);
        contentValues.put(PolicySignalColumns.time, provider.time);
        contentValues.put(PolicySignalColumns.policyKind, provider.policyKind);
        contentValues.put(PolicySignalColumns.policyMarket, provider.policyMarket);
        contentValues.put(PolicySignalColumns.policyTimeLevel, provider.policyTimeLevel);

        return contentValues;
    }
}
