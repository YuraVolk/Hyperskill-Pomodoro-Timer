package org.hyperskill.pomodoro;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class TimerView extends View {
    private int timerColor;
    private int labelColor;
    private String timerText;
    private Paint timerPaint;

    public TimerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray attributes = context.getTheme().obtainStyledAttributes(
                attrs, R.styleable.timerView, 0,  0);
    }
}
