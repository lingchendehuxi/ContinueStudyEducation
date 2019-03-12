package cn.incongress.continuestudyeducation.db;

/**
 * Created by Jacky on 2015/12/28.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import cn.incongress.continuestudyeducation.bean.VideoRecordBean;

/**
 * 数据库业务类，根据需要设计该类
 * @author Administrator
 *
 */
public class VideoDBService {
    private VideoDBHelper mDBHelper;

    public VideoDBService(Context context) {
        mDBHelper = new VideoDBHelper(context);
    }

    /**
     * 添加一个视频记录
     * @param videoRecord
     */
    public long addOneVideoRecord(VideoRecordBean videoRecord) {
        SQLiteDatabase db = mDBHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(VideoDBHelper.VIDEO_CWUUID, videoRecord.getCwUuid());
        cv.put( VideoDBHelper.VIDEO_USERUUID, videoRecord.getUserUuid());
        long result = db.insert(VideoDBHelper.VIDEO_TABLE_NAME, null, cv);
        db.close();
        return result;
    }

    /**
     * 查询一个视频记录是否存在
     * @param cwuuid 课件ID
     * @param userUuid 用户ID
     */
    public boolean queryIsVideoRecordExist(String cwuuid, String userUuid) {
        SQLiteDatabase db = mDBHelper.getWritableDatabase();
        Cursor cursor = db.query(VideoDBHelper.VIDEO_TABLE_NAME,null,VideoDBHelper.VIDEO_CWUUID +"=? and " + VideoDBHelper.VIDEO_USERUUID +"=?", new String[]{cwuuid, userUuid},null,null,null);
        while(cursor.moveToFirst()) {
            cursor.close();
            return true;
        }
        return false;
    }

    public int queryIsVideoFinish(String cwuuid, String userUuid) {
        SQLiteDatabase db = mDBHelper.getWritableDatabase();
        Cursor cursor = db.query(VideoDBHelper.VIDEO_TABLE_NAME,null,VideoDBHelper.VIDEO_CWUUID +"=? and " + VideoDBHelper.VIDEO_USERUUID +"=?", new String[]{cwuuid, userUuid},null,null,null);
        if(cursor.moveToFirst()) {
            return cursor.getInt(cursor.getColumnIndex(VideoDBHelper.VIDEO_ISFINISH));
        }else {
            return 0;
        }
    }

    /**
     * 删除一个VideoRecord记录 根据用户ID和videoId做双重保障
     * @param videoRecord
     * @return
     */
    public int deleteOneVideoRecord(VideoRecordBean videoRecord) {
        SQLiteDatabase db = mDBHelper.getWritableDatabase();
        int result = db.delete(VideoDBHelper.VIDEO_TABLE_NAME, VideoDBHelper.VIDEO_CWUUID + " = ? and " + VideoDBHelper.VIDEO_USERUUID + " = ?", new String[]{videoRecord.getCwUuid(),videoRecord.getUserUuid()});
        db.close();
        return result;
    }

    /**
     * 修改VideoRecord的状态
     * @param videoRecordBean,isUploadSuccess
     */
    public int updateVideoRecord(VideoRecordBean videoRecordBean) {
        SQLiteDatabase db = mDBHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();

        int result = db.update( VideoDBHelper.VIDEO_TABLE_NAME, cv, VideoDBHelper.VIDEO_CWUUID + " = ?",
                new String[]{ videoRecordBean.getCwUuid() + "" });
        db.close();
        return result;
    }

    /**
     * 获取表中所有的video记录
     * @return
     */
    public List<VideoRecordBean> getAllVideoRecords() {
        SQLiteDatabase db = mDBHelper.getWritableDatabase();
        Cursor cursor = db.query(VideoDBHelper.VIDEO_TABLE_NAME, null, null, null, null, null, null);
        List<VideoRecordBean> mVideoRecords = new ArrayList<VideoRecordBean>();

        while(cursor.moveToNext()) {
            String cwUuid = cursor.getString(cursor.getColumnIndex(VideoDBHelper.VIDEO_CWUUID));
            String userUuid = cursor.getString(cursor.getColumnIndex(VideoDBHelper.VIDEO_USERUUID));
            mVideoRecords.add(new VideoRecordBean(cwUuid, userUuid));
        }
        db.close();
        return mVideoRecords;
    }
}
