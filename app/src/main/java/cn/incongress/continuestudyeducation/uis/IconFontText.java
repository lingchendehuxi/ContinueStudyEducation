package cn.incongress.continuestudyeducation.uis;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;
import android.graphics.Typeface;

/**
 * Created by Jacky on 2015/12/18.
 */
public class IconFontText extends TextView {
    private Context mContext;
    private static final String mIconFontPath = "fonts/fontawesome-webfont.ttf";

    public IconFontText(Context context) {
        this(context, null);

    }

    public IconFontText(Context context, AttributeSet attrs) {
        this(context,attrs,0);
    }

    public IconFontText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context,attrs,defStyleAttr);
        this.mContext = context;
        Typeface typeFace = Typeface.createFromAsset(mContext.getAssets(), mIconFontPath);
        this.setTypeface(typeFace);
    }
}
