package com.hyperlink;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements SpannableHyperlinkBuilder.OnTextSpannableClick {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AgreementView viewById = findViewById(R.id.agreementView);
        //设置事件监听
        viewById.setOnTextClick(this);
        //绑定数据
        viewById.setAgreementText(getHyperlinks());


    }

    @NonNull
    private List<SpannableHyperlinkBuilder.Hyperlink> getHyperlinks() {
        List<SpannableHyperlinkBuilder.Hyperlink> list = new ArrayList<>();
        list.add(new SpannableHyperlinkBuilder.Hyperlink("阅读并同意"));
        list.add(new SpannableHyperlinkBuilder.Hyperlink("《服务协议书一》", "www.google.com", R.color.colorAccent));
        list.add(new SpannableHyperlinkBuilder.Hyperlink("和", R.color.colorPrimary));
        list.add(new SpannableHyperlinkBuilder.Hyperlink("《安融告知书及征信授权书》", "www.google.com"));
        return list;
    }

    @Override
    public void onSpannableClick(View widget, SpannableHyperlinkBuilder.Hyperlink hyperlink) {
        Toast.makeText(this, "text = " + hyperlink.getHlText() + "\n" + hyperlink.getHlUrl(), Toast.LENGTH_SHORT).show();
    }
}
