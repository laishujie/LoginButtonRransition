# LoginButtonRransition
  loginButton styles 登录过渡式按钮
#layout
     <com.login.lai.library.LoginViewLayout
        android:id="@+id/login_view"
        android:layout_width="match_parent"
        android:layout_height="match_parent">
          <Button
              android:id="@+id/login_bt"
              android:layout_width="150dip"
              android:layout_height="35dip"
              android:layout_gravity="bottom|center_horizontal"
              android:layout_marginBottom="55dp"
              android:gravity="center"
              android:textColor="@android:color/white"
              android:textSize="12sp" />
    </com.login.lai.library.LoginViewLayout>
    
#Method
      private LoginViewLayout loginViewLayout;
      private Button mButton;
  
      // (设置登录按钮的监听器)Set a listener to be invoked when the button should be login
      loginViewLayout.setLoginAnimationFinish(new LoginViewLayout.OnLoginAnimationListener() {
            @Override
            public void start() {
            }
            @Override
            public void finish() {
            }
        });
      //设置按钮颜色(set button Color)
      public void setBtnColor(int res);
      //设置进度条颜色（set progress bar Color）
      public void setProcessColor(int res) 
      //旋转加载(loading)
      public void rotate();
      //加载好后打开方法(Spread)
      public void open();
     
      
            
        
