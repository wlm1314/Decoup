package com.autozi.cropimg;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Create by on wsy on 2018/8/23 15:35
 */
public class PaintView extends View {
    private String TAG = "PaintView";
    private Paint mPaint, mPaint_rec;
    private Bitmap mBgBitmap;//画布
    private Canvas mCanvas;
    private float startX, startY, endX, endY;

    private ArrayList<Float> mList_X = new ArrayList<>();
    private ArrayList<Float> mList_Y = new ArrayList<>();

    public PaintView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
//        init();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawBitmap(mBgBitmap, 0, 0, null);
    }

    public void init(int width, int height, int resId) {
        mPaint = new Paint();
        mPaint.setColor(Color.RED);
        mPaint.setStrokeWidth(6);
        mPaint_rec = new Paint();
        mPaint_rec.setColor(Color.RED);
        mPaint_rec.setAlpha(0);
        mBgBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        mCanvas = new Canvas(mBgBitmap);
        mCanvas.drawColor(Color.BLACK);
        Bitmap dstBitmap = zoomImg(BitmapFactory.decodeResource(getResources(), resId).copy(Bitmap.Config.ARGB_8888, true), width, height);
        mCanvas.drawBitmap(dstBitmap, (width - dstBitmap.getWidth()) / 2, 0, null);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mList_X.clear();
                mList_Y.clear();
                startX = event.getX();
                startY = event.getY();
                mList_X.add(startX);
                mList_Y.add(startY);
                break;
            case MotionEvent.ACTION_MOVE:
                float stopX = event.getX();
                float stopY = event.getY();
                Log.e(TAG, "onTouchEvent-ACTION_MOVE\nstartX is " + startX +
                        " startY is " + startY + " stopX is " + stopX + " stopY is " + stopY);
                mCanvas.drawLine(startX, startY, stopX, stopY, mPaint);
                startX = event.getX();
                startY = event.getY();
                mList_X.add(startX);
                mList_Y.add(startY);
                invalidate();//call onDraw()
                break;
            case MotionEvent.ACTION_UP:
                RectF rectF = new RectF(Collections.min(mList_X), Collections.min(mList_Y), Collections.max(mList_X), Collections.max(mList_Y));
                mCanvas.drawRect(rectF, mPaint_rec);
                invalidate();//call onDraw()
                saveBitmap(rectF);
                break;
        }
        return true;
    }

    private void saveBitmap(RectF rect) {
        try {
            Log.i("point", "====保存的尺寸==" + rect.left + "/" + rect.top + "  ///" + rect.right + "/" + rect.bottom);
            Bitmap bmp = Bitmap.createBitmap((int) rect.width(), (int) rect.height(), Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bmp);
            canvas.translate(-(rect.left), -(rect.top));
            canvas.drawColor(Color.WHITE);
            canvas.drawBitmap(mBgBitmap, 0, 0, null);
            canvas.save(Canvas.ALL_SAVE_FLAG);
            canvas.restore();
            File file = new File("/sdcard//test.png");
            if (!file.exists()) {
                file.createNewFile();
            }
            FileOutputStream fos = new FileOutputStream(file.getPath());
            bmp.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 缩放图片
    private Bitmap zoomImg(Bitmap bm, int newWidth, int newHeight) {
        // 获得图片的宽高
        int width = bm.getWidth();
        int height = bm.getHeight();
        // 计算缩放比例
        float scaleWidth;
        float scaleHeight;
        if (newWidth < width)
            scaleWidth = ((float) newWidth) / width;
        else
            scaleWidth = ((float) width) / newWidth;
        if (newHeight < height)
            scaleHeight = ((float) newHeight) / height;
        else
            scaleHeight = ((float) height) / newHeight;

        // 取得想要缩放的matrix参数
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleWidth);
        // 得到新的图片
        Bitmap newbm = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, true);
        return newbm;
    }
}
