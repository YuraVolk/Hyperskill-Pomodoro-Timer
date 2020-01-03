package org.hyperskill.pomodoro;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.media.RingtoneManager;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.text.format.DateUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;

import java.util.concurrent.TimeUnit;

public class TimerView extends View {
    private NotificationCompat.Builder notificationBuilder;
    private NotificationManagerCompat notificationManagerCompat;
    private int uniqueID = 0;

    private static final int ARC_START_ANGLE = 270;
    private PomodoroTimer timer;
    private int n = 5;


    private static final float THICKNESS_SCALE = 1.9f;
    private String text = "";

    private Bitmap mBitmap;
    private Canvas mCanvas;

    private RectF mCircleOuterBounds;
    private RectF mCircleInnerBounds;

    private Paint mCirclePaint;
    private Paint mEraserPaint;
    private Paint textPaint;

    private float mCircleSweepAngle = 360.0f;

    private static ValueAnimator mTimerAnimator;

    public void setActivty(Activity activity) {
        timer = new PomodoroTimer(activity);
        notificationBuilder = new NotificationCompat.Builder(activity, "hyperskill_pomodoro");
        notificationBuilder.setContentTitle("You need a rest!!!");
        notificationBuilder.setContentText("It's time to stop");
        notificationBuilder.setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM));
        notificationBuilder.setPriority(NotificationCompat.PRIORITY_MAX); //Test purposes only

        notificationManagerCompat = NotificationManagerCompat.from(activity);
    }

    public TimerView(Context context) {
        this(context, null);
    }

    public TimerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TimerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        int circleColor;

        circleColor = R.color.colorGreen;

        if (attrs != null) {
            TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.TimerView);
            if (ta != null) {
                ta.recycle();
            }
        }



        mCirclePaint = new Paint();
        mCirclePaint.setAntiAlias(true);
        mCirclePaint.setColor(Color.GREEN);

        textPaint = new Paint();
        textPaint.setAntiAlias(true);
        textPaint.setColor(Color.DKGRAY);
        textPaint.setTextSize(80);
        textPaint.setTextAlign(Paint.Align.CENTER);

        mEraserPaint = new Paint();
        mEraserPaint.setAntiAlias(true);
        mEraserPaint.setColor(Color.TRANSPARENT);
        mEraserPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));

        this.text = DateUtils.formatElapsedTime(3);
    }

    @SuppressWarnings("SuspiciousNameCombination")
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        if (w != oldw || h != oldh) {
            mBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
            mBitmap.eraseColor(Color.TRANSPARENT);
            mCanvas = new Canvas(mBitmap);
        }

        super.onSizeChanged(w, h, oldw, oldh);
        updateBounds();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        mCanvas.drawColor(0, PorterDuff.Mode.CLEAR);

        int x = canvas.getWidth() / 2;
        int y = (int) ((canvas.getHeight() / 2) - ((textPaint.descent() + textPaint.ascent()) / 2));

        if (mCircleSweepAngle > 0f) {
            mCanvas.drawArc(mCircleOuterBounds, ARC_START_ANGLE, mCircleSweepAngle, true, mCirclePaint);
            mCanvas.drawOval(mCircleInnerBounds, mEraserPaint);
        }

        mCanvas.drawText(text, x, y, textPaint);

        canvas.drawBitmap(mBitmap, 0, 0, null);
    }

    public void setTime(String time) {
        this.text = time;
    }

    public int getColor() {
        return mCirclePaint.getColor();
    }

    public String getText() {
        return text;
    }

    public void start(int secs, boolean isStart) {
        stop(secs, false);

        if (isStart) {
            n = 5;
        }

        timer.reset(secs);
        timer.countdown(secs, 1000);
        mTimerAnimator = ValueAnimator.ofFloat(1f, 0f);
        mTimerAnimator.setDuration(TimeUnit.SECONDS.toMillis(secs));
        mTimerAnimator.setInterpolator(new LinearInterpolator());
        mTimerAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                drawProgress((float) animation.getAnimatedValue());
            }
        });

        mTimerAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation){
                Log.d("tessssss", "FFFFFFFFFFFFFFF");
                continueAnimation(secs);
            }
        });

        mTimerAnimator.start();
    }

    public void setColor(int color) {
        mCirclePaint = new Paint();
        mCirclePaint.setAntiAlias(true);
        mCirclePaint.setColor(color);
    }

    public boolean isRunning() {
        return mTimerAnimator != null && mTimerAnimator.isRunning();
    }


    private void continueAnimation(int secs) {
        if (n == 0) {
            n = 4;
            stop(secs, true);
            setColor(Color.YELLOW);
            return;
        }

        stop(secs, false);
        if (n % 2 == 0) {
            uniqueID++;
            notificationManagerCompat.notify(uniqueID, notificationBuilder.build());
            setColor(Color.GREEN);
            start(MainActivity.breakSeconds, false);
        } else {
            start(MainActivity.workSeconds, false);
            setColor(Color.RED);
        }
    }

    public void stop(int secs, boolean fullStop) {
     //   setColor(Color.YELLOW);
        timer.reset(secs);

        if (fullStop) {
            n = 0;

            if (mTimerAnimator != null && mTimerAnimator.isRunning()) {
                mTimerAnimator.cancel();
                this.clearAnimation();
                mTimerAnimator = null;
                setColor(Color.YELLOW);
                drawProgress(1f);
            }

            return;
        }

        if (mTimerAnimator != null && mTimerAnimator.isRunning()) {
            mTimerAnimator.cancel();
            this.clearAnimation();
            mTimerAnimator = null;

            Log.d("timer-view-stop", "yay");
            drawProgress(1f);

            n--;
        }
    }



    private void drawProgress(float progress) {
        mCircleSweepAngle = 360 * progress;

        invalidate();
    }

    private void updateBounds() {
        final float thickness = getWidth() * THICKNESS_SCALE;

        mCircleOuterBounds = new RectF(0, 0, getWidth(), getHeight());
        mCircleInnerBounds = new RectF(
                mCircleOuterBounds.left + thickness,
                mCircleOuterBounds.top + thickness,
                mCircleOuterBounds.right - thickness,
                mCircleOuterBounds.bottom - thickness);

        invalidate();
    }
}
