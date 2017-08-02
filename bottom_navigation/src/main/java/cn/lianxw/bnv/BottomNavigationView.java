package cn.lianxw.bnv;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * 底部导航
 * Created by Lian on 2017/8/2.
 */

public class BottomNavigationView extends LinearLayout implements View.OnClickListener {

    private int selectedIndex = -1;
    private OnTabChangeListener onTabChangeListener;

    public BottomNavigationView(Context context) {
        super(context);
        init();
    }


    public BottomNavigationView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public BottomNavigationView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    /**
     * 初始化
     */
    private void init() {
        setOrientation(HORIZONTAL);
        setVerticalGravity(Gravity.CENTER_VERTICAL);
        int padding = DensityUtils.dp2px(6);
        setBackgroundColor(Color.WHITE);
        setPadding(0, padding, 0, padding);
    }

    /**
     * 添加项目
     * @param text 文本
     * @param icon 图片
     */
    public void addItem(String text, @DrawableRes int icon) {
        ItemView itemView = createItem(getContext());
        itemView.tvTitle.setText(text);
        itemView.vIcon.setBackgroundResource(icon);
        addView(itemView);
        itemView.index = getChildCount() - 1;
        itemView.setOnClickListener(this);

        requestLayout();
    }

    /**
     * 清除所有项目
     */
    public void clear() {
        selectedIndex = -1;
        removeAllViews();
    }

    /**
     * 选中指定位置的项目
     * @param index 位置
     */
    public void selectIndex(int index) {
        if (index >= 0 && index < getChildCount()) {
            if (index == selectedIndex) {
                return;
            }
            if (onTabChangeListener != null
                    && selectedIndex != -1
                    && !onTabChangeListener.beforeChange(selectedIndex, index)) {
                return;
            }
            if (selectedIndex >= 0 && selectedIndex < getChildCount()) {
                getChildAt(selectedIndex).setSelected(false);
            }
            selectedIndex = index;
            getChildAt(index).setSelected(true);
            if (onTabChangeListener != null) {
                onTabChangeListener.afterChange(selectedIndex);
            }
        }
    }

    private static ItemView createItem(Context context) {
        ItemView itemView = new ItemView(context);

        LayoutParams params = new LayoutParams(0, LayoutParams.WRAP_CONTENT);
        params.weight = 1;
        itemView.setLayoutParams(params);
        return itemView;
    }

    @Override
    public void onClick(View v) {
        int index = ((ItemView) v).index;
        selectIndex(index);
    }

    /**
     * 设置Tab切换监听器
     * @param listener  监听器
     */
    public void setOnTabChangeListener(OnTabChangeListener listener) {
        this.onTabChangeListener = listener;
    }

    private static class ItemView extends RelativeLayout {
        int index;
        View vIcon;
        TextView tvTitle;

        public ItemView(Context context) {
            super(context);
            init();
        }

        public ItemView(Context context, AttributeSet attrs) {
            super(context, attrs);
            init();
        }

        public ItemView(Context context, AttributeSet attrs, int defStyleAttr) {
            super(context, attrs, defStyleAttr);
            init();
        }

        private void init() {
            int size = DensityUtils.dp2px(20);
            vIcon = new View(getContext());
            LayoutParams params = new LayoutParams(size, size);
            params.addRule(RelativeLayout.CENTER_HORIZONTAL);
            vIcon.setLayoutParams(params);
            vIcon.setId(ViewUtils.generateViewId());
            addView(vIcon);

            tvTitle = new TextView(getContext());
            params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
            params.addRule(RelativeLayout.CENTER_HORIZONTAL);
            params.addRule(RelativeLayout.BELOW, vIcon.getId());
            tvTitle.setLayoutParams(params);
            tvTitle.setId(ViewUtils.generateViewId());
            tvTitle.setTextColor(getResources().getColorStateList(R.color.slt_bnv_text));
            tvTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
            addView(tvTitle);

        }
    }

    public interface OnTabChangeListener {
        /**
         * 在Tab切换之前调用
         *
         * @param current 当前位置
         * @param target  目标位置
         * @return 返回true 切换Tab <br/>返回false 不切换Tab
         */
        boolean beforeChange(int current, int target);

        /**
         * Tab切换完成之后调用
         *
         * @param position
         */
        void afterChange(int position);
    }
}
