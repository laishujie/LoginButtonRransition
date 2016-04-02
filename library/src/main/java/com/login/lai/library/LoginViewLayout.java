package com.login.lai.library;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.RectF;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.OvershootInterpolator;
import android.widget.FrameLayout;

/**
 * 登录过渡按钮
 */
public class LoginViewLayout extends FrameLayout {
    private Paint mProcessPaint;
    private Paint mPaint;

    //控件四个点坐标
    private PointF topLeft;
    private PointF bottomLeft;
    private PointF topRight;
    private PointF bottomtRight;

    //控件左边看控制点和右边控制点
    private PointF mL;
    private PointF mR;

    private PointF mT;
    private PointF mB;

    private float start1;
    private float start2;
    private float start3;
    private float start4;

    private PointF mS1;
    private PointF mS2;
    private PointF mS3;
    private PointF mS4;

    private int painAlpha;
    private float startAngle;

    private float sumWidth;
    private float sumHeight;
    private ValueAnimator finishAnimator;

    private boolean isStartRunLoading = false;

    private RectF oval;

    private int backgroundColor;
    private int btnColor;
    private int processColor;
    private LoginViewLayout loginViewLayout;

    public LoginViewLayout(Context context) {
        this(context, null);
    }

    public LoginViewLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoginViewLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        backgroundColor = Color.WHITE;
        btnColor = getResources().getColor(R.color.colorPrimary);
        processColor = Color.WHITE;
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.FILL);//充满
        mPaint.setColor(btnColor);
        mPaint.setAntiAlias(true);// 设置画笔的锯齿效果

        painAlpha = 255;
        setWillNotDraw(false);
        this.mProcessPaint = new Paint();
        this.mProcessPaint.setAntiAlias(true); //消除锯齿
        mProcessPaint.setColor(processColor);
        this.mProcessPaint.setStyle(Paint.Style.STROKE); //绘制空心圆
        mProcessPaint.setStrokeWidth((float) 1.0);
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        loginViewLayout = this;
        loginViewLayout.setVisibility(View.VISIBLE);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        if (widthMode != MeasureSpec.EXACTLY || heightMode != MeasureSpec.EXACTLY) {
            throw new IllegalArgumentException(
                    "LoginViewLayout 必须固定设置宽或高为 match_parent .LoginViewLayout must be measured with MeasureSpec.EXACTLY. or set your layout match_parent");
        } else {
        }

        float right = Float.valueOf(String.valueOf(textView.getRight()));
        float left = Float.valueOf(String.valueOf(textView.getLeft()));
        float top = Float.valueOf(String.valueOf(textView.getTop()));
        float bottom = Float.valueOf(String.valueOf(textView.getBottom()));

        float measuredHeight = Float.valueOf(String.valueOf(textView.getMeasuredHeight()));

        WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        sumWidth = Float.valueOf(String.valueOf(wm.getDefaultDisplay().getWidth()));
        sumHeight = Float.valueOf(String.valueOf(wm.getDefaultDisplay().getHeight()));

        //standard = measuredWidth / 4;
        //先把控件四个坐标点拿出来
        topLeft = new PointF(left, top);
        bottomLeft = new PointF(left, bottom);
        topRight = new PointF(right, top);
        bottomtRight = new PointF(right, bottom);
        //控制点L
        float cy = (bottom + topLeft.y) / 2;
        float cx = left - (measuredHeight / 2);
        mL = new PointF(cx, cy);
        //控制点R
        float cy2 = (bottom + topLeft.y) / 2;
        float cx2 = right + (measuredHeight / 2);
        mR = new PointF(cx2, cy2);

        //控制T
        float cx3 = (topRight.x + topLeft.x) / 2;
        float cy3 = top;
        mT = new PointF(cx3, cy3);

        //控制点bottom
        float cx4 = (right + left) / 2;
        float cy4 = bottom;

        mB = new PointF(cx4, cy4);

        //控制点bottom
        float cx5 = (mL.x + mT.x) / 2;
        float cy5 = bottom;
        mS1 = new PointF(cx5, cy5);

        //控制点bottom
        float cx6 = (mT.x + mR.x) / 2;
        float cy6 = bottom;
        mS2 = new PointF(cx6, cy6);


        //控制点bottom
        float cx7 = (mS1.x + mB.x) / 2;
        float cy7 = bottom;
        mS3 = new PointF(cx7, cy7);

        //控制点bottom
        float cx8 = (mB.x + mS2.x) / 2;
        float cy8 = bottom;
        mS4 = new PointF(cx8, cy8);

        start1 = topLeft.x;
        start2 = mS3.x;
        start3 = topRight.x;
        start4 = mS4.x;
        startAngle = 255f;
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.save();
        canvas.drawColor(backgroundColor);
        Path path = new Path();

        path.moveTo(topLeft.x, topLeft.y);
        path.quadTo(mL.x, mL.y, bottomLeft.x, bottomLeft.y);
        path.quadTo(mB.x, mB.y, bottomtRight.x, bottomtRight.y);
        path.quadTo(mR.x, mR.y, topRight.x, topRight.y);
        path.quadTo(mT.x, mT.y, topLeft.x, topLeft.y);

        canvas.drawPath(path, mPaint);


    /*    canvas.drawCircle(topLeft.x, topLeft.y, 5, mPaint);
        canvas.drawCircle(topRight.x, topRight.y, 5, mPaint);
        canvas.drawCircle(bottomLeft.x, bottomLeft.y, 5, mPaint);
        canvas.drawCircle(bottomtRight.x, bottomtRight.y, 5, mPaint);*/
        canvas.drawCircle(mL.x, mL.y, 5, mPaint);
        canvas.drawCircle(mR.x, mR.y, 5, mPaint);

        // canvas.drawCircle(mS3.x, mS3.y, 5, mPaint);
        //canvas.drawCircle(mS4.x, mS4.y, 5, mPaint);

/*
        canvas.drawCircle(mT.x, mT.y, 5, mPaint);
        canvas.drawCircle(mB.x, mB.y, 5, mPaint);
        canvas.drawCircle(mS1.x, mS1.y, 5, mPaint);
        canvas.drawCircle(mS2.x, mS2.y, 5, mPaint);
        //canvas.drawCircle(mS3.x, mS3.y, 5, mPaint);
        canvas.drawCircle(mS4.x, mS4.y, 5, mPaint);*/
        // } else {
        //RectF对象
        if (isStartRunLoading) {
            if (oval == null) {
                oval = new RectF();
            }
            oval.top = textView.getTop();  //顶部 y                                //上边
            oval.left = mS3.x; //底部 x                      //左边
            oval.right = mS4.x;   //右上角 x                          //右边
            oval.bottom = mS4.y;    //右下角y                            //下边
            /**
             * oval：圆弧所在的椭圆对象。区域
             startAngle：圆弧的起始角度。
             sweepAngle：圆弧的角度。
             useCenter：是否显示半径连线，true表示显示圆弧与圆心的半径连线，false表示不显示。
             mProcessPaint：绘制时所使用的画笔。
             */
            canvas.drawArc(oval, startAngle, 90, false, mProcessPaint);    //绘制圆弧
            // canvas.drawRect(oval.left, oval.top, oval.right, oval.bottom, mProcessPaint);
        }
        canvas.restore();
    }

    private View textView;
    private ValueAnimator roteAnimator;

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        System.out.println("加载结束");
        if (this.getChildCount() == 1) {
            textView = this.getChildAt(0);
            textView.setVisibility(View.VISIBLE);
            textView.setBackgroundColor(btnColor);
        } else {
            throw new IllegalStateException("只能有一个控件TextView或者Button:（Can only be a control such as  a button or TextView ）");
        }
    }

    private WhileHandler handler = new WhileHandler();

    class WhileHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 0) {
                roteAnimator.start();
            }
        }
    }

    //设置按钮颜色
    public void setBtnColor(int res) {
        btnColor = getResources().getColor(res);
        mPaint.setColor(btnColor);
        invalidate();
    }

    //设置进度条颜色
    public void setProcessColor(int res) {
        processColor = getResources().getColor(res);
        mProcessPaint.setColor(processColor);
    }

    public void rotate() {
        textView.setVisibility(View.GONE);
        if (OnLoginAnimationListener != null) {
            OnLoginAnimationListener.start();
        }
        ValueAnimator leftAnimator = ValueAnimator.ofFloat(start1, start2);
        leftAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float flag = Float.valueOf(valueAnimator.getAnimatedValue().toString());
                float v = flag - topLeft.x;
                topLeft.x = flag;
                mL.x += v;
                mPaint.setAlpha(painAlpha--);
                bottomLeft.x = flag;
            }
        });
        leftAnimator.setDuration(1000);
        leftAnimator.start();
        roteAnimator = ValueAnimator.ofFloat(startAngle, 1000);
        roteAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                startAngle = Float.valueOf(valueAnimator.getAnimatedValue().toString());
                invalidate();
            }
        });
        roteAnimator.setDuration(1000);
        roteAnimator.setInterpolator(new OvershootInterpolator(4));
        roteAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                if (isStartRunLoading)
                    handler.sendEmptyMessage(0);

            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
        ValueAnimator leftTopAnimator = ValueAnimator.ofFloat(start3, start4);
        leftTopAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float flag = Float.valueOf(valueAnimator.getAnimatedValue().toString());
                float v = flag - topRight.x;
                topRight.x = flag;
                mR.x += v;
                bottomtRight.x = flag;
                invalidate();
            }
        });
        leftTopAnimator.setDuration(1000);
        leftTopAnimator.start();
        leftTopAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                isStartRunLoading = true;//加载旋转
                handler.sendEmptyMessage(0);
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
    }


    private void setBackground(int res) {

    }

    //打开
    public void open() {
        isStartRunLoading = false;
        mPaint.setMaskFilter(new BlurMaskFilter(5, BlurMaskFilter.Blur.SOLID));//毛刷效果

        ValueAnimator ah = ValueAnimator.ofInt(255, 0);
        ah.setDuration(800);
        ah.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                mPaint.setAlpha(Integer.valueOf(valueAnimator.getAnimatedValue().toString()));
            }
        });
        ah.start();
        ah.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {

            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });

        finishAnimator = ValueAnimator.ofFloat(0, sumHeight);
        finishAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float flag = Float.valueOf(valueAnimator.getAnimatedValue().toString());
                if (topLeft.y <= 0) {
                } else {
                    topLeft.x -= flag;
                    topLeft.y -= flag;
                    bottomtRight.x += flag;
                    bottomtRight.y += flag;
                    mT.y -= flag + 5;
                    mB.y += flag;
                    mL.x -= flag;
                    mR.x += flag;
                    bottomLeft.x -= flag;
                    bottomLeft.y += flag;
                    topRight.x += flag;
                    topRight.y -= flag;
                    if (topLeft.x <= 0) {
                        topLeft.x = 0;
                    }
                    if (bottomtRight.x >= sumWidth) {
                        bottomtRight.x = sumWidth;
                    }
                    if (topRight.x >= sumWidth) {
                        topRight.x = sumWidth;
                    }
                    if (bottomLeft.x < 0) {
                        bottomLeft.x = 0;
                    }
                    invalidate();
                }
            }
        });
        finishAnimator.setDuration(1000);
        finishAnimator.start();
        finishAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
            }

            @Override
            public void onAnimationEnd(Animator animator) {
                if (OnLoginAnimationListener != null) {
                    OnLoginAnimationListener.finish();
                }
                loginViewLayout.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationCancel(Animator animator) {
            }

            @Override
            public void onAnimationRepeat(Animator animator) {
            }
        });

    }

    private OnLoginAnimationListener OnLoginAnimationListener;

    public void setLoginAnimationFinish(OnLoginAnimationListener OnLoginAnimationListener) {
        this.OnLoginAnimationListener = OnLoginAnimationListener;
    }

    public interface OnLoginAnimationListener {
        void finish();

        void start();
    }
}
