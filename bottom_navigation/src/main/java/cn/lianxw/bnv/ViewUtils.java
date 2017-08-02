package cn.lianxw.bnv;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 视图工具
 * Created by Lian on 2017/8/2.
 */

public class ViewUtils {
    private static final AtomicInteger sNextGeneratedId = new AtomicInteger(1);

    /**
     * 生成视图ID
     * @return
     */
    public static int generateViewId() {
        for (;;) {
            final int result = sNextGeneratedId.get();
            // aapt-generated IDs have the high byte nonzero; clamp to the range under that.
            int newValue = result + 1;
            if (newValue > 0x00FFFFFF) newValue = 1; // Roll over to 1, not 0.
            if (sNextGeneratedId.compareAndSet(result, newValue)) {
                return result;
            }
        }
    }
}
