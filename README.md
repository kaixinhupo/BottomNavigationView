# BottomNavigationView

### 导入
#### Step 1. 添加JitPack到项目的build.gradle

```groovy
    allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
```

#### Step 2.添加依赖

```groovy
	dependencies {
	        compile 'com.github.kaixinhupo:BottomNavigationView:1.0'
	}
```
### 使用

1.布局文件

```xml
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical">
    <FrameLayout
        android:id="@+id/fl_fragment_container"
        android:layout_width="match_parent"
        android:layout_height="0dp"
        android:layout_weight="1"/>

    <cn.lianxw.bnv.BottomNavigationView
        android:id="@+id/vnv_main"
        android:layout_width="match_parent"
        android:layout_height="wrap_content" />

</LinearLayout>
```

2.在Activity中设置

```java
public class IndexActivity extends Activity implements BottomNavigationView.OnTabChangeListener {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }
    
    private void initView() {
        bnvMain = (BottomNavigationView) findViewById(R.id.bnv_main);
        bnvMain.addItem("首页", R.drawable.slt_main_home);
        bnvMain.addItem("发现", R.drawable.slt_main_discover);
        bnvMain.addItem("我", R.drawable.slt_main_me);
    
        bnvMain.setOnTabChangeListener(this);
        bnvMain.selectIndex(2);
    }
    
    @Override
    public boolean beforeChange(int current, int target) {
        //如果没有登录，不能跳转到个人中心
        if (target == 2 && !accountUtil.isLogin()) {
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
            //返回false表示不能跳转
            return false;
        }
        //返回true允许跳转
        return true;
    }

    @Override
    public void afterChange(int position) {
        //选中位置发生变化，切换Fragment
        FragmentManager fm = this.getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        switch (position) {
            case 0:
                transaction.replace(R.id.ll_fragment_container, indexFragment);
                break;
            case 1:
                transaction.replace(R.id.ll_fragment_container, discoverFragment);
                break;
            case 2:
                transaction.replace(R.id.ll_fragment_container, meFragment);
                break;
        }
        transaction.commit();
    }
}
```

### 3.个性化
如果要修改BottomNavigationView文字的颜色,请在你项目中的colors.xml定义两个颜色：**bnv_text_normal**，**bnv_text_selected**，并设置成你期望的色值就可以了。

```xml
    <!--选中时文字的颜色-->
    <color name="bnv_text_selected" >#E54539</color>
    <!--未选中时文字的颜色-->
    <color name="bnv_text_normal" >#4A4A4A</color>
```

