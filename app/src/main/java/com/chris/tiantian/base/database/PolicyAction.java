package com.chris.tiantian.base.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.chris.tiantian.entity.Policy;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jianjianhong on 19-10-11
 */
public class PolicyAction {
    public static final String TABLE_NAME = "PolicyAction";

    /**
     * 所有的字段
     * */
    private static final String[] AVAILABLE_PROJECTION = new String[]{
            PolicyColumns.id,
            PolicyColumns.name,
            PolicyColumns.developerId,
            PolicyColumns.fileName,
            PolicyColumns.kind,
            PolicyColumns.market,
            PolicyColumns.timeLevel,
            PolicyColumns.direction,
            PolicyColumns.accuracy,
            PolicyColumns.profit,
            PolicyColumns.verifiedLevel,
            PolicyColumns.price,
            PolicyColumns.publishDate,
            PolicyColumns.refineTimes,
            PolicyColumns.subscribeNumber,
            PolicyColumns.weight,
            PolicyColumns.file,
    };

    /**
     * 创建表的语句
     * */
    public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "("
            + PolicyColumns._ID + " INTEGER PRIMARY KEY,"
            + PolicyColumns.id + " INTEGER,"
            + PolicyColumns.name + " TEXT,"
            + PolicyColumns.developerId + " TEXT,"
            + PolicyColumns.fileName + " TEXT,"
            + PolicyColumns.kind + " TEXT,"
            + PolicyColumns.market + " TEXT,"
            + PolicyColumns.timeLevel + " TEXT,"
            + PolicyColumns.direction + " TEXT,"
            + PolicyColumns.accuracy + " INTEGER,"
            + PolicyColumns.profit + " INTEGER,"
            + PolicyColumns.verifiedLevel + " INTEGER,"
            + PolicyColumns.price + " INTEGER,"
            + PolicyColumns.publishDate + " TEXT,"
            + PolicyColumns.refineTimes + " INTEGER,"
            + PolicyColumns.subscribeNumber + " INTEGER,"
            + PolicyColumns.weight + " INTEGER,"
            + PolicyColumns.file + " TEXT"
            + ")";

    /**
     * 删除表的语句
     * */
    public static final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;


    public static List<Policy> query(SQLiteOpenHelper helper) {
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, AVAILABLE_PROJECTION, null, null, null, null, PolicyColumns.publishDate + " desc", null);
        List<Policy> locations = new ArrayList<>();
        while (cursor.moveToNext())
        {
            locations.add(getPolicyFromCursor(cursor));
        }
        cursor.close();
        return locations;
    }

    public static boolean findById(SQLiteOpenHelper helper, String id) {
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, AVAILABLE_PROJECTION, "id=?", new String[] {id}, null, null, null, null);
        return cursor.getCount() > 0;
    }

    public static void updateOrInsert(SQLiteOpenHelper helper, Policy policy) {
        if(findById(helper, policy.getId()+"")) {
            update(helper, policy);
        }else {
            insert(helper, policy);
        }
    }

    public static void insert(SQLiteOpenHelper helper, Policy student) {
        SQLiteDatabase db = helper.getWritableDatabase();
        db.insert(TABLE_NAME, null, toContentValues(student));
    }

    public static void update(SQLiteOpenHelper helper, Policy policy) {
        SQLiteDatabase db = helper.getWritableDatabase();
        db.update(TABLE_NAME, toContentValues(policy), "id=?", new String[] {policy.getId()+""});
    }

    private static Policy getPolicyFromCursor(Cursor cursor) {
        Policy Policy = new Policy();
        Policy.id = cursor.getInt(cursor.getColumnIndex(PolicyColumns.id));
        Policy.name = cursor.getString(cursor.getColumnIndex(PolicyColumns.name));
        Policy.developerId = cursor.getString(cursor.getColumnIndex(PolicyColumns.developerId));
        Policy.fileName = cursor.getString(cursor.getColumnIndex(PolicyColumns.fileName));
        Policy.kind = cursor.getString(cursor.getColumnIndex(PolicyColumns.kind));
        Policy.market = cursor.getString(cursor.getColumnIndex(PolicyColumns.market));
        Policy.timeLevel = cursor.getString(cursor.getColumnIndex(PolicyColumns.timeLevel));
        Policy.direction = cursor.getString(cursor.getColumnIndex(PolicyColumns.direction));
        Policy.accuracy = cursor.getInt(cursor.getColumnIndex(PolicyColumns.accuracy));
        Policy.profit = cursor.getInt(cursor.getColumnIndex(PolicyColumns.profit));
        Policy.verifiedLevel = cursor.getInt(cursor.getColumnIndex(PolicyColumns.verifiedLevel));
        Policy.price = cursor.getInt(cursor.getColumnIndex(PolicyColumns.price));
        Policy.publishDate = cursor.getString(cursor.getColumnIndex(PolicyColumns.publishDate));
        Policy.refineTimes = cursor.getInt(cursor.getColumnIndex(PolicyColumns.refineTimes));
        Policy.subscribeNumber = cursor.getInt(cursor.getColumnIndex(PolicyColumns.subscribeNumber));
        Policy.weight = cursor.getInt(cursor.getColumnIndex(PolicyColumns.weight));
        Policy.file = cursor.getString(cursor.getColumnIndex(PolicyColumns.fileName));
        return Policy;
    }

    public static void clear(SQLiteOpenHelper helper) {
        SQLiteDatabase db = helper.getWritableDatabase();
        int count = db.delete(TABLE_NAME, null, null);
        Log.i("PolicyAction", "delete count:"+count);
    }

    private static ContentValues toContentValues(Policy provider) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(PolicyColumns.id, provider.id);
        contentValues.put(PolicyColumns.name, provider.name);
        contentValues.put(PolicyColumns.developerId, provider.developerId);
        contentValues.put(PolicyColumns.fileName, provider.fileName);
        contentValues.put(PolicyColumns.kind, provider.kind);
        contentValues.put(PolicyColumns.market, provider.market);
        contentValues.put(PolicyColumns.timeLevel, provider.timeLevel);
        contentValues.put(PolicyColumns.direction, provider.direction);
        contentValues.put(PolicyColumns.accuracy, provider.accuracy);
        contentValues.put(PolicyColumns.profit, provider.profit);
        contentValues.put(PolicyColumns.verifiedLevel, provider.verifiedLevel);
        contentValues.put(PolicyColumns.price, provider.price);
        contentValues.put(PolicyColumns.publishDate, provider.publishDate);
        contentValues.put(PolicyColumns.refineTimes, provider.refineTimes);
        contentValues.put(PolicyColumns.subscribeNumber, provider.subscribeNumber);
        contentValues.put(PolicyColumns.weight, provider.weight);
        contentValues.put(PolicyColumns.file, provider.file);


        return contentValues;
    }
}
