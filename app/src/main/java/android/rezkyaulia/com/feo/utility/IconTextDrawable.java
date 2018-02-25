package android.rezkyaulia.com.feo.utility;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.rezkyaulia.com.feo.R;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.widget.AppCompatDrawableManager;

import com.amulyakhare.textdrawable.TextDrawable;
import com.app.infideap.stylishwidget.view.Stylish;


/**
 * Created by Shiburagi on 24/01/2017.
 */
public class IconTextDrawable extends Drawable {

    private final String text;
    private final Paint paint;
    private final Bitmap bitmap;
    private final Context context;
    private final float sizeX;
    private final float sizeY;

    private final float dp24;

    public IconTextDrawable(Context context, String text, int drawable) {
        this.context = context;
        this.text = text;
        this.bitmap = getBitmapFromVectorDrawable(context, drawable);
        dp24 = DimensionConverter.getInstance().stringToDimensionPixelSize("24dp", context.getResources().getDisplayMetrics());

        this.paint = new Paint();
        paint.setTextSize(22f);
        paint.setAntiAlias(true);
        paint.setFakeBoldText(true);
        paint.setShadowLayer(6f, 0, 0, Color.BLACK);
        paint.setStyle(Paint.Style.FILL);
        paint.setTextAlign(Paint.Align.RIGHT);
        sizeX = dp24;
        sizeY = dp24;
    }

    public IconTextDrawable(Context context, String text, int drawable, float width, float height) {
        dp24 = DimensionConverter.getInstance().stringToDimensionPixelSize("24dp", context.getResources().getDisplayMetrics());

        this.context = context;
        this.text = text;
        this.bitmap = getBitmapFromVectorDrawable(context, drawable);


        this.sizeX = width;
        this.sizeY = height;
        this.paint = new Paint();
        paint.setTextSize(22f);
        paint.setAntiAlias(true);
        paint.setFakeBoldText(true);
        paint.setShadowLayer(6f, 0, 0, Color.BLACK);
        paint.setStyle(Paint.Style.FILL);
        paint.setTextAlign(Paint.Align.RIGHT);
    }

    @Override
    public void draw(Canvas canvas) {
        float sizeX = (this.sizeX - dp24) / 2f;

        float sizeY = (this.sizeY - dp24) / 2f;

        canvas.drawBitmap(bitmap, (float) Math.floor(sizeX), sizeY, paint);
        if(text==null)
            return;
        paint.setColor(Color.RED);
//        canvas.drawCircle(bitmap.getWidth()-6, bitmap.getHeight()/2-6, 12, paint);
        paint.setColor(Color.YELLOW);
//        canvas.drawText(text, bitmap.getWidth(), bitmap.getHeight()/2, paint);
        float textSize = DimensionConverter.getInstance().stringToDimension("12sp", context.getResources().getDisplayMetrics());
        int width = (int) (textSize * text.length() + textSize / 2f);
        TextDrawable drawable = TextDrawable.builder()
                .beginConfig()
                .textColor(Color.WHITE)
                .useFont(Stylish.getInstance().bold(context))
                .fontSize((int) textSize) /* sizeX in px */
                .bold()
                .width(width)
                .height((int) (textSize * 1.5f))
                .toUpperCase()
//                .withBorder(15)
                .withBorder(DimensionConverter.getInstance().stringToDimensionPixelSize("1dp", context.getResources().getDisplayMetrics()))
                .endConfig()
                .buildRoundRect(text, ContextCompat.getColor(context, R.color.colorDeepOrange_500), (int) textSize);
        canvas.drawBitmap(convertToBitmap(drawable, width, (int) (textSize * 1.5f)),
                bitmap.getWidth() - width * 2 / 3 + sizeX, sizeY - dp24 / 4, paint);
//        bitmap.draw(canvas);
    }

    @Override
    public void setAlpha(int alpha) {
        paint.setAlpha(alpha);
    }

    @Override
    public void setColorFilter(ColorFilter cf) {
        paint.setColorFilter(cf);
    }

    @Override
    public int getOpacity() {
        return PixelFormat.TRANSLUCENT;
    }

    public static Bitmap getBitmapFromVectorDrawable(Context context, int drawableId) {
        Drawable drawable = AppCompatDrawableManager.get().getDrawable(context, drawableId);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            drawable = (DrawableCompat.wrap(drawable)).mutate();
        }

        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(),
                drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);

        return bitmap;
    }

    public static Bitmap drawableToBitmap(Drawable drawable) {
        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        }

        int width = drawable.getIntrinsicWidth();
        width = width > 0 ? width : 1;
        int height = drawable.getIntrinsicHeight();
        height = height > 0 ? height : 1;

        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);

        return bitmap;
    }

    public static Bitmap convertToBitmap(Drawable drawable, int widthPixels, int heightPixels) {
        Bitmap mutableBitmap = Bitmap.createBitmap(widthPixels, heightPixels, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(mutableBitmap);
        drawable.setBounds(0, 0, widthPixels, heightPixels);
        drawable.draw(canvas);

        return mutableBitmap;
    }
}