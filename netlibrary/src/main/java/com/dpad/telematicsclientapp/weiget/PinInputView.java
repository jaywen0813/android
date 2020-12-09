package com.dpad.telematicsclientapp.weiget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dpad.crmclientapp.android.R;

import java.util.ArrayList;

/**
 * ================================================
 * 作    者：booob
 * 版    本：1.0
 * 创建日期：2019-06-26-0026 11:20
 * 描    述：
 * 修订历史：
 * ================================================
 */
public class PinInputView extends LinearLayout {
    private LinearLayout pointLayout;
    private TextView point1;
    private TextView point2;
    private TextView point3;
    private TextView point4;
    private ImageView cancel;
    private TextView forgetPwd;
    private TextView one;
    private TextView two;
    private TextView three;
    private TextView four;
    private TextView five;
    private TextView six;
    private TextView seven;
    private TextView eight;
    private TextView nine;
    private TextView zero;
    private TextView clean;
    private ArrayList<String> input = new ArrayList<>();

    public PinInputView(final Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setOrientation(VERTICAL);
        LayoutInflater.from(context).inflate(R.layout.pin_input_layout, this);
        pointLayout = findViewById(R.id.pointLayout);
        point1 = findViewById(R.id.point1);
        point2 = findViewById(R.id.point2);
        point3 = findViewById(R.id.point3);
        point4 = findViewById(R.id.point4);
        one = findViewById(R.id.one);
        two = findViewById(R.id.two);
        three = findViewById(R.id.three);
        four = findViewById(R.id.four);
        five = findViewById(R.id.five);
        six = findViewById(R.id.six);
        seven = findViewById(R.id.seven);
        eight = findViewById(R.id.eight);
        nine = findViewById(R.id.nine);
        zero = findViewById(R.id.zero);
        clean = findViewById(R.id.clean);
        cancel = (ImageView) findViewById(R.id.cancel);
        forgetPwd = findViewById(R.id.forget_pwd_tv);

        forgetPwd.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (input.size() > 0) {
                    ClearAll();
                }
                if (onForgetListener != null) {
                    onForgetListener.onForget();
                }
            }
        });
        cancel.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (input.size() > 0) {
                    remove(context);
                } else {
                    if (onCancelClickListener != null) {
                        onCancelClickListener.OnCancelClick();
                    }
                }
            }
        });
//        clean.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (input.size() > 0) {
//                    input.clear();
//                    point1.setText("");
//                    point2.setText("");
//                    point3.setText("");
//                    point4.setText("");
//                }
//            }
//        });
        one.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                add("1", context);
            }
        });
        two.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                add("2", context);
            }
        });
        three.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                add("3", context);
            }
        });
        four.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                add("4", context);
            }
        });
        five.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                add("5", context);
            }
        });
        six.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                add("6", context);
            }
        });
        seven.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                add("7", context);
            }
        });
        eight.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                add("8", context);
            }
        });
        nine.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                add("9", context);
            }
        });
        zero.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                add("0", context);
            }
        });
    }

    public PinInputView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public PinInputView(Context context) {
        super(context);

    }

    private void add(String pin, Context context) {
        if (input.size() == 0) {
//            Animatable animatable = ((Animatable) cancel.getDrawable());
//            animatable.start();
            point1.setText("*");
            input.add(pin);
        } else if (input.size() == 1) {
            point2.setText("*");
            input.add(pin);
        } else if (input.size() == 2) {
            point3.setText("*");
            input.add(pin);
        } else if (input.size() == 3) {
            point4.setText("*");
            input.add(pin);
        } else {
            return;
        }
        if (input.size() == 4) {
            StringBuilder stringBuilder = new StringBuilder();
            for (String str : input) {
                stringBuilder.append(str);
            }
            if (onFullListener != null) {
                onFullListener.OnFull(stringBuilder.toString());
            }
        }

    }

    private void remove(Context context) {
        if (input.size() == 4) {
            input.remove(input.size() - 1);
            point4.setText("");
        } else if (input.size() == 3) {
            input.remove(input.size() - 1);
            point3.setText("");
        } else if (input.size() == 2) {
            input.remove(input.size() - 1);
            point2.setText("");
        } else if (input.size() == 1) {
            input.remove(input.size() - 1);
            point1.setText("");
        }
    }

    OnCancelClickListener onCancelClickListener;
    OnFullListener onFullListener;
    OnForgetListener onForgetListener;

    public interface OnCancelClickListener {
        void OnCancelClick();
    }

    public interface OnFullListener {
        void OnFull(String value);
    }

    public interface OnForgetListener {
        void onForget();
    }

    public void setOnCancelClickListener(OnCancelClickListener mListener) {
        this.onCancelClickListener = mListener;
    }
    public void setOnForgetListener(OnForgetListener mOnForgetListener) {
        this.onForgetListener = mOnForgetListener;
    }

    public boolean setOnFullListener(OnFullListener mListener) {
        this.onFullListener = mListener;
        return false;
    }

    public void setNumColor(int color) {
        one.setTextColor(color);
        two.setTextColor(color);
        three.setTextColor(color);
        four.setTextColor(color);
        five.setTextColor(color);
        six.setTextColor(color);
        seven.setTextColor(color);
        eight.setTextColor(color);
        nine.setTextColor(color);
        zero.setTextColor(color);
    }

    public void setClearButtonColor(int color) {
        clean.setTextColor(color);
    }

    public void setPointColor(int color) {
        point1.setTextColor(color);
        point2.setTextColor(color);
        point3.setTextColor(color);
        point4.setTextColor(color);
    }

    public void setCancelButtonColor(int color) {
        cancel.setColorFilter(color);
    }

    public void Error() {
        Animation mShakeAnimation = AnimationUtils.loadAnimation(getContext(), R.anim.pin_error);
        pointLayout.startAnimation(mShakeAnimation);
        ClearAll();
    }

    public void ClearAll() {
        input.clear();
        point1.setText("");
        point2.setText("");
        point3.setText("");
        point4.setText("");
    }
}
