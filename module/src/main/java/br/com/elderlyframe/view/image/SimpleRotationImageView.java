package br.com.elderlyframe.view.image;

import android.content.Context;
import android.graphics.Rect;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;

import static android.view.MotionEvent.ACTION_CANCEL;

public class SimpleRotationImageView extends androidx.appcompat.widget.AppCompatImageView {

    private float angle;
    private float lastAngle;
    private float centerX;
    private float centerY;
    private float fX;
    private float fY;
    private float newfX;
    private float newfY;

    public SimpleRotationImageView(Context context) {
        super(context);
    }

    public SimpleRotationImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public SimpleRotationImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev){

        int maskedAction = ev.getActionMasked();

        switch (maskedAction) {

            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_POINTER_DOWN: {
                fX = ev.getRawX();
                fY = ev.getRawY();
                break;
            }
            case MotionEvent.ACTION_MOVE: {
                newfX = ev.getRawX();
                newfY = ev.getRawY();
                Rect rectCavalo = new Rect();
                getGlobalVisibleRect(rectCavalo);
                centerX = rectCavalo.exactCenterX();
                centerY = rectCavalo.exactCenterY();
                angle = angleBetweenLines (centerX, centerY, fX, fY, centerX, centerY, newfX, newfY);
                setRotation(-(angle+lastAngle));
                break;
            }
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_POINTER_UP:
            case ACTION_CANCEL: {
                lastAngle += angle;
                break;
            }
        }

        return true;
    }

    private float angleBetweenLines (float fX, float fY, float sX, float sY, float nfX, float nfY, float nsX, float nsY) {
        float angle1 = (float) Math.atan2( (fY - sY), (fX - sX) );
        float angle2 = (float) Math.atan2( (nfY - nsY), (nfX - nsX) );

        float angle = ((float)Math.toDegrees(angle1 - angle2)) % 360;
        if (angle < -180.f) angle += 360.0f;
        if (angle > 180.f) angle -= 360.0f;
        return angle;
    }
}
