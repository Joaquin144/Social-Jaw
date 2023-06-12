package com.devcommop.myapplication.ui.components.settings.privacy_policy;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.widget.TextView;

import com.devcommop.myapplication.R;

public class PrivacyPolicyActivity extends AppCompatActivity {
    private TextView privacyPolicyTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_privacy_policy);

        privacyPolicyTv = findViewById(R.id.privacy_policy_content_tv);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            //privacyPolicyTv.setText(Html.fromHtml(getString(R.string.privacy_policy_text), Html.FROM_HTML_MODE_LEGACY));
            privacyPolicyTv.setText(Html.fromHtml(getString(R.string.privacypolicy_two), Html.FROM_HTML_MODE_LEGACY));
        } else {
            //privacyPolicyTv.setText(Html.fromHtml(getString(R.string.privacy_policy_text)));
            privacyPolicyTv.setText(Html.fromHtml(getString(R.string.privacypolicy_two)));
        }
    }
}