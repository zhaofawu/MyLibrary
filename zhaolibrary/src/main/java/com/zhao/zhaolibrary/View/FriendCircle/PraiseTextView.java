package com.zhao.zhaolibrary.View.FriendCircle;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.AppCompatTextView;
import android.text.Layout;
import android.text.Selection;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.BackgroundColorSpan;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.ImageSpan;
import android.text.style.StyleSpan;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import com.zhao.zhaolibrary.R;

import java.util.List;

/**
 * 另一种，每项可点击
 */
public class PraiseTextView extends AppCompatTextView {
    private PraiseTextView instance;
    private Context mContext;
    private StyleSpan boldSpan;
    private ForegroundColorSpan colorSpan;
    private OnPraiseNameClickListener onPraiseNameClickListener;

    public void setOnPraiseNameClickListener(OnPraiseNameClickListener onPraiseNameClickListener) {
        this.onPraiseNameClickListener = onPraiseNameClickListener;
    }

    public PraiseTextView(Context context) {
        super(context);
        mContext = context;
        initView();
    }

    public PraiseTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        initView();
    }

    public PraiseTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        initView();
    }

    private void initView() {
        instance = this;
        setMovementMethod(LinkMovementMethod.getInstance());
        setHighlightColor(getResources().getColor(R.color.transparent));
        boldSpan = new StyleSpan(Typeface.BOLD);
        colorSpan = new ForegroundColorSpan(getResources().getColor(R.color.grey_bbbbbb));
    }

    private TextBlankClickListener listener;

    public void setListener(TextBlankClickListener listener) {
        this.listener = listener;
    }

    private int mStart = -1;

    private int mEnd = -1;

    private android.os.Handler handler = new android.os.Handler();
    //计数
    private int leftTime;
    private Runnable countDownRunnable = new Runnable() {
        public void run() {
            leftTime--;
            if (leftTime == -1) {
                // 触发长按事件
                if (listener != null) {
                    listener.onLongClick(instance);
                }
            } else {
                //100毫秒执行一次 超过500毫秒就说明触发长按
                handler.postDelayed(this, 100);
            }
        }
    };
    private boolean isMove = false;
    private float lastX;
    private float lastY;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        boolean result = super.onTouchEvent(event);

        int action = event.getAction();

        int x = (int) event.getX();
        int y = (int) event.getY();
        if (action == MotionEvent.ACTION_DOWN) {
            lastX = event.getX();
            lastY = event.getY();
            isMove = false;
        } else if (action == MotionEvent.ACTION_MOVE) {
            float distanceX = Math.abs(lastX - event.getX());
            float distanceY = Math.abs(lastY - event.getY());
            if (distanceX > 1.5f || distanceY > 1.5f) {
                isMove = true;
            }
        }

        x -= getTotalPaddingLeft();
        y -= getTotalPaddingTop();

        x += getScrollX();
        y += getScrollY();

        Layout layout = getLayout();
        int line = layout.getLineForVertical(y);
        int off = layout.getOffsetForHorizontal(line, x);
        CharSequence text = getText();
        if (TextUtils.isEmpty(text) || !(text instanceof Spannable)) {
            return result;
        }
        Spannable buffer = (Spannable) text;
        ClickableSpan[] link = buffer.getSpans(off, off, ClickableSpan.class);
        if (link.length != 0) {
            if (action == MotionEvent.ACTION_DOWN) {
                mStart = buffer.getSpanStart(link[0]);
                mEnd = buffer.getSpanEnd(link[0]);
                if (mStart >= 0 && mEnd >= mStart) {
                    buffer.setSpan(new BackgroundColorSpan(Color.GRAY), mStart, mEnd,
                            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                }
            } else if (action == MotionEvent.ACTION_UP || action == MotionEvent.ACTION_CANCEL) {;
                if (mStart >= 0 && mEnd >= mStart) {
                    buffer.setSpan(new BackgroundColorSpan(Color.TRANSPARENT), mStart, mEnd,
                            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    mStart = -1;
                    mEnd = -1;
                }
            } else if (action == MotionEvent.ACTION_MOVE) {
                if (isMove) {
                    if (mStart >= 0 && mEnd >= mStart) {
                        buffer.setSpan(new BackgroundColorSpan(Color.TRANSPARENT), mStart, mEnd,
                                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                        mStart = -1;
                        mEnd = -1;
                    }
                }
            }
            return true;
        } else {
            if (mStart >= 0 && mEnd >= mStart) {
                buffer.setSpan(new BackgroundColorSpan(Color.TRANSPARENT), mStart, mEnd,
                        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                mStart = -1;
                mEnd = -1;
            }
            if (action == MotionEvent.ACTION_DOWN) {
                setBackgroundColor(Color.GRAY);
                //开始计数
                leftTime = 5;
                handler.post(countDownRunnable);
                return true;
            } else if (action == MotionEvent.ACTION_UP || action == MotionEvent.ACTION_CANCEL) {
                setBackgroundColor(Color.TRANSPARENT);
                //如果没有调用长按 调用点击整个的监听
                if (leftTime > -1) {
                    leftTime = 10;
                    handler.removeCallbacks(countDownRunnable);//移除统计
                    if (listener != null && !isMove) {
                        listener.onBlankClick(this);
                    }
                }
            } else if (action == MotionEvent.ACTION_MOVE) {
                if (isMove) {
                    setBackgroundColor(Color.TRANSPARENT);
                }
            }
            Selection.removeSelection(buffer);
            return false;
        }
    }


    /**
     * 设置点赞的名字
     *
     * @param praises
     * @return
     */
    public void setPraises(List<UserInfo> praises,int icon) {
        setText("");
        if(praises.size()!=0){
            SpannableStringBuilder mBuilder = new SpannableStringBuilder ("我");
            mBuilder.setSpan (new iconImage(getResources ().getDrawable (icon)), 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            append(mBuilder);
        }
        int length = praises.size();
        for (int i = 0; i < length; i++) {
            final UserInfo info=praises.get(i);
            String praise;
            if (i == length - 1) {
                praise = praises.get(i).getName();
            } else {
                praise = praises.get(i).getName() + "，";
            }
            if(TextUtils.isEmpty(praise)){
                praise="游客";
            }
            SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(praise);
            spannableStringBuilder.setSpan(new ClickableSpan() {
                @Override
                public void updateDrawState(TextPaint ds) {
                    ds.setColor(getResources().getColor(R.color.praise)); // 设置超链接颜色
                    ds.setFakeBoldText(true);
                    ds.setUnderlineText(false); // 超链接下划线
                }

                @Override
                public void onClick(View widget) {
                    onNameClick(widget,info);
                }
            }, 0, praise.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            append(spannableStringBuilder);
        }
    }
    public void onNameClick(View widget, UserInfo user) {
        if (onPraiseNameClickListener != null) {
            onPraiseNameClickListener.onNameClick(widget, user);
        }
    }
    /**
     * 图片span
     */
    public class iconImage extends ImageSpan {
        private Drawable mDrawable;

        public iconImage (Drawable d) {
            super (d);
            mDrawable = d;
        }

        @Override
        public Drawable getDrawable () {
            mDrawable.setBounds (new Rect(0,0,48,48));
            return mDrawable;
        }
    }

}
