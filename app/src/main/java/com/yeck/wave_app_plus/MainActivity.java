package com.yeck.wave_app_plus;

import android.app.Activity;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class MainActivity extends Activity {
    private SurfaceView mySurfaceView;
    private DrawingThread mDrawingThread;
    SurfaceHolder surfaceHolder;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mySurfaceView = (SurfaceView) findViewById(R.id.surfaceViewl);
        surfaceHolder = mySurfaceView.getHolder();
        surfaceHolder.addCallback(new SurfaceHolder.Callback() {

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
                // TODO Auto-generated method stub
            }

            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                // TODO Auto-generated method stub
                mDrawingThread = new DrawingThread();
                mDrawingThread.mSurface = surfaceHolder;
                mDrawingThread.start();
            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                // TODO Auto-generated method stub
                mDrawingThread.mQuit = true;
            }
        });
    }

    static final class MovingPoint {
        float x, y, dx, dy;

        void init(int width, int height, float minStep) {
            x = (float) ((width - 1) * Math.random());
            y = (float) ((height - 1) * Math.random());
            dx = (float) (Math.random() * minStep * 2) + 1;
            dy = (float) (Math.random() * minStep * 2) + 1;
        }

        float adjDelta(float cur, float minStep, float maxStep) {
            cur += (Math.random() * minStep) - (minStep / 2);
            if (cur < 0 && cur > -minStep) cur = -minStep;
            if (cur >= 0 && cur < minStep) cur = minStep;
            if (cur > maxStep) cur = maxStep;
            if (cur < -maxStep) cur = -maxStep;
            return cur;
        }

        void step(int width, int height, float minStep, float maxStep) {
            x += dx;
            if (x <= 0 || x >= (width - 1)) {
                if (x <= 0) x = 0;
                else if (x >= (width - 1)) x = width - 1;
                dx = adjDelta(-dx, minStep, maxStep);
            }
            y += dy;
            if (y <= 0 || y >= (height - 1)) {
                if (y <= 0) y = 0;
                else if (y >= (height - 1)) y = height - 1;
                dy = adjDelta(-dy, minStep, maxStep);
            }
        }
    }

    class DrawingThread extends Thread {
        // These are protected by the Thread's lock
        SurfaceHolder mSurface;
        boolean mRunning;
        boolean mActive;
        boolean mQuit;
        // Internal state
        int mLineWidth;
        float mMinStep;
        float mMaxStep;

        //我的内容
        float Funx1 = 500, Funy1 = 500, Funx2 = 500, Funy2 = 500;
        float[] mPts = new float[100000];
        boolean flag = false;
        int t = 0;

        //

        boolean mInitialized = false;
        final MovingPoint mPoint1 = new MovingPoint();
        final MovingPoint mPoint2 = new MovingPoint();

        static final int NUM_OLD = 100;
        int mNum0ld = 0;
        final float[] m0ld = new float[NUM_OLD * 4];
        final int[] m01dColor = new int[NUM_OLD];
        int mBrightLine = 0;

        // X is red, Y is blue
        final MovingPoint mColor = new MovingPoint();

        final Paint mBackground = new Paint();
        final Paint mForeground = new Paint();

        int makeGreen(int index) {
            int dist = Math.abs(mBrightLine - index);
            if (dist > 10) return 0;
            return (255 - (dist * (255 / 10))) << 8;
        }

        @Override
        public void run() {
            mPts[0] = 1000;
            mPts[1] = 1000;
            mLineWidth = (int) (getResources().getDisplayMetrics().density * 1.5);
            if (mLineWidth < 1) mLineWidth = 1;
            mMinStep = mLineWidth * 2;
            mMaxStep = mMinStep * 3;

            mBackground.setColor(Color.WHITE);
            mForeground.setColor(Color.GREEN);
            mForeground.setAntiAlias(true);
            mForeground.setStrokeWidth(10);

            int length = 0;
            int i = 0;
            int delay;

            while (true) {
                if (mQuit) {
                    return;
                }
                // Lock the canvas for drawing
                Canvas canvas = mSurface.lockCanvas();
                if (canvas == null) {
                    Log.i("WindowSurface", "Failure locking canvas");
                    continue;
                }
                canvas.drawPoint(1000, 1000, mForeground);
                // Update graphics
//                if (!mInitialized) {
//                    mInitialized = true;
//                    mPoint1.init(canvas.getWidth(), canvas.getHeight(), mMinStep);
//                    mPoint2.init(canvas.getWidth(), canvas.getHeight(), mMinStep);
//                    mColor.init(127, 127, 1);
//                } else {
//                    mPoint1.step(canvas.getWidth(), canvas.getHeight(), mMinStep, mMaxStep);
//                    mPoint2.step(canvas.getWidth(), canvas.getHeight(), mMinStep, mMaxStep);
//                    mColor.step(127, 127, 1, 3);
//                }
//                mBrightLine += 2;
//                if (mBrightLine > (NUM_OLD * 2)) {
//                    mBrightLine = -2;
//                }

                // Clear background
                //canvas.drawColor(mBackground.getColor());
//                 Draw old lines
//                for (int i = mNum0ld - 1; i >= 0; i--) {
//                    mForeground.setColor(m01dColor[i] | makeGreen(i));
//                    mForeground.setAlpha(((NUM_OLD - i) * 255) / NUM_OLD);
//                    int p = i * 4;
//                    canvas.drawLine(m0ld[p], m0ld[p + 1], m0ld[p + 2], m0ld[p + 3], mForeground);
//                }
                // Draw new line
//                int red = (int) mColor.x + 128;
//                if (red > 255) red = 255;
//                int blue = (int) mColor.y + 128;
//                if (blue > 255) blue = 255;
//                int color = 0xff000000 | (red << 16) | blue;
//                mForeground.setColor(color | makeGreen(-2));
//                canvas.drawLine(mPoint1.x, mPoint1.y, mPoint2.x, mPoint2.y, mForeground);


//                canvas.drawLine(0 , 1000, 500, 1000, mForeground);


//                if (i > 100) {
//                    double a = Math.toRadians(i);
//                    mPts[i] = 1000;
//                    mPts[i + 1] = (float) Math.sin(a);
//                    if (i == 101 || i == 102) {
//                        i += 2;
//                        continue;
//                    }
////                    canvas.drawLines(mPts, mForeground);
////                    mForeground.setColor(Color.BLACK);
////                    canvas.drawLine(0, 0, mPts[i], mPts[i+1], mForeground);
//                    for (int t = 0; t < i - 2; t += 2) {
//                        canvas.drawLine(mPts[t], mPts[t + 1], mPts[t + 2], mPts[t + 3], mForeground);
//                    }
//                    i += 2;
////                    Funx2 = (float) (Funx1 + 0.6);
////                    a = Math.toRadians(Funx2);
////                    Funy2 = 200 * (float) Math.sin(a);
////                    canvas.drawLine(Funx1 - 500, Funy1 + 500, Funx2 - 500, Funy2 + 500, mForeground);
////                    canvas.drawPoint(Funx1 - 500, Funy1 + 500, mForeground);
////                    canvas.drawPoint(Funx2 - 500, Funy2 + 500, mForeground);
////                    Funx1 += 1.2;
//                } else {
                mPts[i] = 1000;
                mPts[i + 1] = 1000;
                if (flag == true) {
                    if (t < 25) {
                        mPts[i + 1] = (float) (1000 - t * 2.5);
                    } else if (t >= 25 && t < 50) {
                        mPts[i + 1] = (float) (937.5 + t * 2.5 - 25 * 2.5);
                    } else if (t >= 100 && t < 120) {
                        mPts[i + 1] = 1000 + t * 2 - 200;
                    } else if (t >= 120 && t < 160) {
                        mPts[i + 1] = 1040 - t * 12 + 120 * 12;
                    } else if (t >= 160 && t < 210) {
                        mPts[i + 1] = 560 + t * 12 - 160 * 12;
                    } else if (t >= 210 && t < 223) {
                        mPts[i + 1] = 1160 - t * 12 + 210 * 12;
                    } else if (t >= 310 && t <= 400) {
                        mPts[i + 1] = 1004 - t * 2 + 310 * 2;
                    } else if (t >= 400 && t <= 460) {
                        mPts[i + 1] = 824 + t * 3 - 400 * 3;
                    } else if (t >= 460 && t < 500) {
                        mPts[i + 1] = (float) (1000 - t * 1.8 + 460 * 1.8);
                    } else if (t >= 500 && t <= 540) {
                        mPts[i + 1] = (float) (928 + t * 1.8 - 500 * 1.8);
                    } else if (t > 540){
                        flag = false;
                        t = 0;
                    }
                    t++;
                }

                if (i % 500 == 0 && flag == false) {
                    flag = true;
                    t = 0;
                }
                canvas.drawColor(Color.BLACK);
                mForeground.setColor(Color.GREEN);
                for (int t = 0; t < i - 2; t += 2) {
                    canvas.drawLine(mPts[t], mPts[t + 1], mPts[t + 2], mPts[t + 3], mForeground);
                }
//                    canvas.drawLines(mPts, mForeground);
//                    mForeground.setColor(Color.BLACK);
//                    canvas.drawLine(0, 0, 1000, 1000, mForeground);

                i += 2;
                length += 2;
//                }
                for (int j = 0; j < i; j += 2) {
                    mPts[j] -= 2;
                }

                if (mPts[i] < 0) {
                    for (int k = 0; k <= i + 1; k++) {
                        mPts[k] = mPts[k + 2];
                    }
                    i -= 2;
                }
//                if (length >= 1200){
//                    int k;
//                    for (k = 0; k < length; k++){
//                        mPts[k] = mPts[k+600];
//                    }
//                    length = 600;
//                    i = k;
//                }

                // Add in the new line
//                if (mNum0ld > 1) {
//                    System.arraycopy(m0ld, 0, m0ld, 4, (mNum0ld - 1) * 4);
//                    System.arraycopy(m01dColor, 0, m01dColor, 1, mNum0ld - 1);
//                }
//                if (mNum0ld < NUM_OLD) mNum0ld++;
//                m0ld[0] = mPoint1.x;
//                m0ld[1] = mPoint1.y;
//                m0ld[2] = mPoint2.x;
//                m0ld[3] = mPoint2.y;
//                m01dColor[0] = color;
                // All done
                mSurface.unlockCanvasAndPost(canvas);
            }
        }
    }
}