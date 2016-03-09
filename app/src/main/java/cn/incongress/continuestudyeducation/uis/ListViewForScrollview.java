package cn.incongress.continuestudyeducation.uis;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * Created by Jacky on 2015/12/31.
 */
public class ListViewForScrollview extends ListView {
    public ListViewForScrollview(Context context) {
        super(context);
    }

    public ListViewForScrollview(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ListViewForScrollview(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }

}
