package cn.incongress.continuestudyeducation.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Jacky on 2015/12/28.
 */
public class VideoDBHelper extends SQLiteOpenHelper {
    private static final int VERSION = 1;
    public static final String DBName = "cme";
    public static final String VIDEO_TABLE_NAME = "videos";

    public static final String VIDEO_CWUUID = "cwuuid";
    public static final String VIDEO_USERUUID = "userUuId";
    public static final String VIDEO_ISFINISH = "isFinish";
    public static final String VIDEO_ISUPLOADSUCCESS = "isUploadSuccess";

    private String CREATE_TABLE = "create table if not exists " + VIDEO_TABLE_NAME + "(id integer primary key, "+ VIDEO_CWUUID + " text, " + VIDEO_USERUUID + " text, "+
            VIDEO_ISFINISH + " integer, " + VIDEO_ISUPLOADSUCCESS +" integer)";

    public VideoDBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, VERSION);
    }

    /**
     * 对外创建该类的时候就用这个方法，比较方便，因为数据库的版本号以及CursorFactory一般也不会改变。
     * @param context
     */
    public VideoDBHelper(Context context) {
        super(context, DBName, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL( CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + VIDEO_TABLE_NAME);
        onCreate(db);
    }
}
