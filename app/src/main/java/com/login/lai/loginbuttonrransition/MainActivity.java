package com.login.lai.loginbuttonrransition;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.login.lai.library.LoginViewLayout;

public class MainActivity extends AppCompatActivity {

    private LoginViewLayout loginViewLayout;
    private Button mButton;
    private TextView mTextBar;

    private TextView mTextBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loginViewLayout = (LoginViewLayout) findViewById(R.id.login_view);
        mButton = (Button) findViewById(R.id.btn_start);
        mTextBtn = (TextView) findViewById(R.id.btn_text);
        mTextBar = (TextView) findViewById(R.id.bar_text);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginViewLayout.rotate();
            }
        });

        loginViewLayout.setLoginAnimationFinish(new LoginViewLayout.OnLoginAnimationListener() {
            @Override
            public void finish() {
                mButton.setVisibility(View.VISIBLE);
            }

            @Override
            public void start() {

            }
        });
    }

    public void onOpen(View view) {
        loginViewLayout.open();
    }

    public void btnChoose(View v) {
        switch (v.getId()) {
            case R.id.btn_default:
                mButton.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                mTextBtn.setText("按钮的颜色(ButtonColor): 蓝色");
                loginViewLayout.setBtnColor(R.color.colorPrimary);
                break;
            case R.id.btn_pink:
                mButton.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                mTextBtn.setText("按钮的颜色(ButtonColor): 粉红");
                loginViewLayout.setBtnColor(R.color.colorAccent);

                break;
            case R.id.btn_black:
                mButton.setBackgroundColor(getResources().getColor(R.color.black));
                mTextBtn.setText("按钮的颜色(ButtonColor): 黑色");
                loginViewLayout.setBtnColor(R.color.black);

                break;
            case R.id.btn_gray:
                mButton.setBackgroundColor(getResources().getColor(R.color.dialog_text_color));
                mTextBtn.setText("按钮的颜色(ButtonColor): 灰色");
                loginViewLayout.setBtnColor(R.color.dialog_text_color);
                break;
        }
    }

    public void barChoose(View v) {
        switch (v.getId()) {
            case R.id.bar_default:
                mTextBar.setText("按钮的颜色(ButtonColor): 蓝色");
                loginViewLayout.setProcessColor(R.color.colorPrimary);
                break;
            case R.id.bar_pink:
                mTextBar.setText("进度条的颜色(progressBar Color): 粉红");
                loginViewLayout.setProcessColor(R.color.colorAccent);

                break;
            case R.id.bar_black:
                mTextBar.setText("进度条的颜色(progressBar Color): 黑色");
                loginViewLayout.setProcessColor(R.color.black);

                break;
            case R.id.bar_gray:
                mTextBar.setText("进度条的颜色(progressBar Color): 灰色");
                loginViewLayout.setProcessColor(R.color.dialog_text_color);
                break;
        }
    }

}
