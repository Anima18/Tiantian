package com.chris.tiantian.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;

import com.chris.tiantian.R;
import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import q.rorbin.badgeview.Badge;
import q.rorbin.badgeview.QBadgeView;

/**
 * Created by jianjianhong on 19-4-18
 */
public class BottomNavigationViewHelper {
    @SuppressLint("RestrictedApi")
    public static void disableShiftMode(BottomNavigationView navigationView) {

        BottomNavigationMenuView menuView = (BottomNavigationMenuView) navigationView.getChildAt(0);
       // menuView.setLabelVisibilityMode(1);
        for (int i = 0; i < menuView.getChildCount(); i++) {
            BottomNavigationItemView item = (BottomNavigationItemView) menuView.getChildAt(i);
            //去除shift效果
            // support-v27版本则是 item.setShiftingMode(false)
            item.setShifting(false);
            item.setChecked(item.getItemData().isChecked());
        }
    }

    /**
     * BottomNavigationView显示角标
     *
     * @param viewIndex tab索引
     */
    public static Badge getBottomNavigationBadge(Context context, BottomNavigationView bottomNavigationView, int viewIndex) {
        // 具体child的查找和view的嵌套结构请在源码中查看
        // 从bottomNavigationView中获得BottomNavigationMenuView
        BottomNavigationMenuView menuView = (BottomNavigationMenuView) bottomNavigationView.getChildAt(0);
        // 从BottomNavigationMenuView中获得childview, BottomNavigationItemView
        if (viewIndex < menuView.getChildCount()) {
            // 获得viewIndex对应子tab
            View view = menuView.getChildAt(viewIndex);
            // 从子tab中获得其中显示图片的ImageView
            View icon = view.findViewById(R.id.icon);
            // 获得图标的宽度
            int iconWidth = icon.getWidth();
            // 获得tab的宽度/2
            int tabWidth = view.getWidth() / 2;
            // 计算badge要距离右边的距离
            int spaceWidth = tabWidth - iconWidth - 10;

            // 显示badegeview
            return new QBadgeView(context).setShowShadow(false).bindTarget(view).setGravityOffset(spaceWidth, 5, false).setBadgeTextSize(8, true);
        }else {
            return null;
        }
    }

    public static void showRedDotBadge(Context context, BottomNavigationView bottomNavigationView, int viewIndex) {
        // 从bottomNavigationView中获得BottomNavigationMenuView
        BottomNavigationMenuView menuView = (BottomNavigationMenuView) bottomNavigationView.getChildAt(0);
        // 从BottomNavigationMenuView中获得childview, BottomNavigationItemView
        if (viewIndex < menuView.getChildCount()) {
            // 获得viewIndex对应子tab
            View view = menuView.getChildAt(viewIndex);
            // 从子tab中获得其中显示图片的ImageView
            View icon = view.findViewById(R.id.icon);
            // 计算badge要距离右边的距离
            int spaceWidth = view.getWidth() / 2 - icon.getWidth()/ 2;
            // 显示badegeview
            new QBadgeView(context).bindTarget(view).setGravityOffset(spaceWidth, 16, false).setBadgePadding(4, true).setBadgeTextSize(4, true).setBadgeText("");
        }
    }
}
