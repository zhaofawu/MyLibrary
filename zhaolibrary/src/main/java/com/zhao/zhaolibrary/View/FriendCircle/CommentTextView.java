package com.zhao.zhaolibrary.View.FriendCircle;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
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
import android.text.style.StyleSpan;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import com.zhao.zhaolibrary.R;


public class CommentTextView extends AppCompatTextView {
    private TextView instance;
    private Context mContext;
    private StyleSpan boldSpan;
    //回复者
    private ClickableSpan reviewSpan;
    //被回复者
    private ClickableSpan replySpan;

    public CommentTextView(Context context) {
        super(context);
        mContext = context;
        initView();
    }

    public CommentTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        initView();
    }

    public CommentTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        initView();
    }

    //点击名字的监听
    private OnCommentNameClickListener commentNameClickListener;
    public void setCommentNameListener(OnCommentNameClickListener commentNameClickListener) {
        this.commentNameClickListener = commentNameClickListener;
    }

    private void initView() {
        instance = this;
        setHighlightColor(getResources().getColor(R.color.transparent));
        setMovementMethod(LinkMovementMethod.getInstance());
        boldSpan = new StyleSpan(Typeface.BOLD);
        reviewSpan = new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                CommentBean model1 = (CommentBean) widget.getTag();
                commentNameClickListener.onNameClick(widget,model1.getUser());
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setTypeface(Typeface.DEFAULT_BOLD);
                ds.setUnderlineText(false);
                ds.setColor(getResources().getColor(R.color.blue_666e8a));
            }
        };
        replySpan = new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                CommentBean model1 = (CommentBean) widget.getTag();
                commentNameClickListener.onNameClick(widget,model1.getTo_user());
            }

            @Override
            public void updateDrawState(@NonNull TextPaint ds) {
                super.updateDrawState(ds);
                ds.setTypeface(Typeface.DEFAULT_BOLD);
                ds.setUnderlineText(false);
                ds.setColor(getResources().getColor(R.color.blue_666e8a));
            }
        };
    }

    //长按监听和整条文本监听
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
                if(listener != null && !isMove){
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
//                return result;
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
            } else if (action == MotionEvent.ACTION_UP || action == MotionEvent.ACTION_CANCEL) {
                if (mStart >= 0 && mEnd >= mStart) {
                    buffer.setSpan(new BackgroundColorSpan(Color.TRANSPARENT), mStart, mEnd,
                            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    mStart = -1;
                    mEnd = -1;
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
            return result;
        }
    }

    /**
     * 设置回复的可点击文本
     *
     * @param model
     * @return
     */
    public void setReply(CommentBean model) {
        setText("");
        setTag(model);
        String reviewName = model.getUser().getName()==null?"":model.getUser().getName();
        SpannableStringBuilder stylesBuilder;
        String str;
        if (!TextUtils.isEmpty(model.getTo_user().getUid())) {
            String replyName = model.getTo_user().getName();
            str = reviewName + "回复" + replyName + "：" + model.getText();
            if(TextUtils.isEmpty(replyName)){
                str = reviewName + "回复" + replyName + "：" +"null";
            }
            int reviewStart = str.indexOf(reviewName);
            int replyStart = str.indexOf(replyName);
            stylesBuilder = new SpannableStringBuilder(str);
            stylesBuilder.setSpan(reviewSpan, reviewStart, reviewStart + reviewName.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            stylesBuilder.setSpan(replySpan, replyStart, replyStart + replyName.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        } else {
            str = reviewName + "：" + model.getText();
            int reviewStart = str.indexOf(reviewName);
            stylesBuilder = new SpannableStringBuilder(str);
            stylesBuilder.setSpan(reviewSpan, reviewStart, reviewStart + reviewName.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        append(stylesBuilder);
    }

}
