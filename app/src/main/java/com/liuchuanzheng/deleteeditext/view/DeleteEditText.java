package com.liuchuanzheng.deleteeditext.view;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatEditText;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;

import com.liuchuanzheng.deleteeditext.R;


/**
 * Created by 刘传政 on 2018/5/3 0003.
 * QQ:1052374416
 * 电话:18501231486
 * 作用:
 * 注意事项:
 */

public class DeleteEditText extends AppCompatEditText {
    private int delete_src_id = R.drawable.vector_delete_black;
    private boolean delete = false;

    public DeleteEditText(Context context) {
        this(context, null);
        init(context,null,0);
    }

    public DeleteEditText(Context context, AttributeSet attrs) {
        super(context,attrs);
        //this(context, attrs, -1);
        init(context, attrs, 0);
    }

    public DeleteEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        if (attrs != null) {
            //获取自定义属性
            TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.DeleteEditText);
            //获取自定义属性的某个字段的具体值
            delete_src_id = ta.getResourceId(R.styleable.DeleteEditText_delete_src, delete_src_id);
            //回收
            ta.recycle();
        }
        initDeleteIcon();
        initListener();

    }

    private void initListener() {
        setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    setDeleteIconVisible(getText().length() > 0);
                } else {
                    setDeleteIconVisible(false);
                }
            }
        });
        addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                setDeleteIconVisible(getText().length() > 0);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void initDeleteIcon() {
        Drawable deleteDrawable = getCompoundDrawables()[2];
        if (deleteDrawable == null) {
            deleteDrawable = ContextCompat.getDrawable(getContext(), delete_src_id);
        }
        //Drawable的setBounds方法有四个参数，setBounds(int left, int top, int right, int bottom),这个四参数指的是drawable将在被绘制在canvas的哪个矩形区域内。
        deleteDrawable.setBounds(0, 0, (int) dp2px(20), (int) dp2px(20));
        Drawable right = delete ? deleteDrawable : null;
        //设置右侧图片
        setCompoundDrawables(getCompoundDrawables()[0], getCompoundDrawables()[1], right, getCompoundDrawables()[3]);
    }

    private float dp2px(float dpValue) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpValue, Resources.getSystem().getDisplayMetrics());
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP) {
            if (getCompoundDrawables()[2] != null) {
                boolean touchable = event.getX() > (getWidth() - getTotalPaddingRight())
                        && (event.getX() < ((getWidth() - getPaddingRight())));
                if (touchable) {
                    this.setText("");
                }
            }
        }
        return super.onTouchEvent(event);
    }

    private void setDeleteIconVisible(boolean visible) {
        delete = visible;
        initDeleteIcon();
    }
}
