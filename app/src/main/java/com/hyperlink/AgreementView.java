package com.hyperlink;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

/**
 * Created by zs on 18/4/25.
 * <p>
 * Description
 */

public class AgreementView extends LinearLayout {
    private Context context;
    private int cbBackground;
    private int tvSize;
    private int tvColor;
    private int marginLeft;
    private CheckBox cBox;
    private SpannableHyperlinkBuilder mSpannableHyperlinkBuilder;

    public AgreementView(Context context) {
        this(context, null);
    }

    public AgreementView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AgreementView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        Resources resources = context.getResources();
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.AgreementView, defStyleAttr, 0);
        cbBackground = a.getResourceId(R.styleable.AgreementView_cbBackground, R.drawable.bg_agreement_selector);
        tvColor = a.getResourceId(R.styleable.AgreementView_android_textColor, resources.getColor(R.color.agreement_def_tv_color));
        tvSize = a.getDimensionPixelSize(R.styleable.AgreementView_android_textSize, (int) resources.getDimension(R.dimen.agreementDefTextSize));
        marginLeft = a.getDimensionPixelSize(R.styleable.AgreementView_marginLeft, (int) resources.getDimension(R.dimen.agreementDefMarginLeft));
        a.recycle();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        init();
    }

    private void init() {
        mSpannableHyperlinkBuilder = new SpannableHyperlinkBuilder();
        setOrientation(HORIZONTAL);
        cBox = new CheckBox(context);
        cBox.setButtonDrawable(cbBackground);
        addView(cBox);
        TextView textView = new TextView(context);
        textView.setTextSize(Utils.px2sp(context, tvSize));
        textView.setTextColor(tvColor);
        //去掉点击高亮背景色
        textView.setHighlightColor(getResources().getColor(android.R.color.transparent));
        textView.setLineSpacing(10, 1);
        addView(textView);
        LinearLayout.LayoutParams tvParams = (LayoutParams) textView.getLayoutParams();
        tvParams.leftMargin = marginLeft;
        textView.setLayoutParams(tvParams);
        mSpannableHyperlinkBuilder.setTextView(textView);
    }

    public void setAgreementText(List<SpannableHyperlinkBuilder.Hyperlink> hyperlink) {
        mSpannableHyperlinkBuilder.add(hyperlink);
        mSpannableHyperlinkBuilder.builder();
    }

    public void setOnTextClick(SpannableHyperlinkBuilder.OnTextSpannableClick listener) {
        mSpannableHyperlinkBuilder.setOnTextSpannableClick(listener);
    }

    public void setOnCheckedListener(CompoundButton.OnCheckedChangeListener checkedChangeListener) {
        cBox.setOnCheckedChangeListener(checkedChangeListener);
    }

}
