package com.chris.tiantian.view;

import android.app.Activity;
import android.content.Context;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import com.google.android.material.snackbar.Snackbar;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


import com.chris.tiantian.R;
import com.chris.tiantian.util.CommonUtil;


/**
 * Created by jianjianhong on 19-5-8
 */
public class Messagebar {

    private Snackbar snackbar;
    /**
     * 信息提示类型
     */
    private static final int MESSAGE_INFO = 0;
    private static final int MESSAGE_SUCCESS = 1;
    private static final int MESSAGE_WARNING = 2;
    private static final int MESSAGE_ERROR = 3;


    /*private Messagebar(Activity context, String message, int type, final OnActionListener listener) {
        ViewGroup rootView = context.findViewById(android.R.id.content);
        View view = findCoordinatorLayout(rootView);
        if(view == null) {
            view = rootView.getRootView();
        }
        new Messagebar(view, message, type, listener);
    }*/

    private Messagebar(View view, String message, final String actionMessage, int type, final OnActionListener dismissedListener) {
        snackbar = Snackbar.make(view, message, Snackbar.LENGTH_LONG);
        View snackbarView = snackbar.getView();
        Button actionButton = snackbarView.findViewById(R.id.snackbar_action);
        Context context = CommonUtil.getApplicationContext();
        switch (type) {
            case MESSAGE_SUCCESS:
                snackbarView.setBackgroundColor(context.getResources().getColor(R.color.message_success));
                actionButton.setTextColor(context.getResources().getColor(R.color.message_success_action));
                break;
            case MESSAGE_WARNING:
                snackbarView.setBackgroundColor(CommonUtil.getApplicationContext().getResources().getColor(R.color.message_warning));
                actionButton.setTextColor(context.getResources().getColor(R.color.message_warning_action));
                break;
            case MESSAGE_ERROR:
                snackbarView.setBackgroundColor(context.getResources().getColor(R.color.message_error));
                actionButton.setTextColor(context.getResources().getColor(R.color.message_error_action));
                break;
            case MESSAGE_INFO:
                actionButton.setTextColor(context.getResources().getColor(R.color.message_default_action));
                break;
        }

        if (!TextUtils.isEmpty(actionMessage)) {
            snackbar.setAction(actionMessage, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(dismissedListener != null) {
                        dismissedListener.onAction();
                    }
                }
            });
        }

        snackbar.setCallback(new Snackbar.Callback() {
            @Override
            public void onDismissed(Snackbar snackbar, int event) {
                super.onDismissed(snackbar, event);
                if(TextUtils.isEmpty(actionMessage)) {
                    if(dismissedListener != null) {
                        dismissedListener.onAction();
                    }
                }
            }
            @Override
            public void onShown(Snackbar snackbar) {
                super.onShown(snackbar);
            }
        });
    }

    public static CoordinatorLayout findCoordinatorLayout(ViewGroup group) {

        if (group != null) {
            for (int i = 0, j = group.getChildCount(); i < j; i++) {
                View child = group.getChildAt(i);
                if (child instanceof CoordinatorLayout) {
                    return (CoordinatorLayout) child;
                } else if (child instanceof ViewGroup) {
                    CoordinatorLayout result = findCoordinatorLayout((ViewGroup) child);
                    if (result != null)
                        return result;
                }
            }
        }
        return null;
    }

    private static View getCoordinatorView(Activity context) {
        ViewGroup rootView = context.findViewById(android.R.id.content);
        View view = findCoordinatorLayout(rootView);
        if(view == null) {
            view = rootView.getChildAt(0);
        }
        return view;
    }

    public static Messagebar makeInfo(Activity context, String message, OnActionListener dismissedListener) {
        return makeInfo(context, message, "", dismissedListener);
    }

    public static Messagebar makeInfo(Activity context, String message, String actionMessage, OnActionListener dismissedListener) {
        return makeInfo(getCoordinatorView(context), message, actionMessage, dismissedListener);
    }

    public static Messagebar makeInfo(View view, String message, OnActionListener dismissedListener) {
        return makeInfo(view, message, "", dismissedListener);
    }

    public static Messagebar makeInfo(View view, String message, String actionMessage, OnActionListener dismissedListener) {
        return new Messagebar(view, message, actionMessage, MESSAGE_INFO, dismissedListener);
    }

    public static Messagebar makeSuccess(Activity context, String message, OnActionListener dismissedListener) {
        return makeSuccess(context, message, "", dismissedListener);
    }

    public static Messagebar makeSuccess(Activity context, String message, String actionMessage, OnActionListener dismissedListener) {
        return makeSuccess(getCoordinatorView(context), message, actionMessage, dismissedListener);
    }

    public static Messagebar makeSuccess(View view, String message, OnActionListener dismissedListener) {
        return makeSuccess(view, message, "", dismissedListener);
    }

    public static Messagebar makeSuccess(View view, String message, String actionMessage, OnActionListener dismissedListener) {
        return new Messagebar(view, message, actionMessage, MESSAGE_SUCCESS, dismissedListener);
    }

    public static Messagebar makeWarning(Activity context, String message, OnActionListener dismissedListener) {
        return makeWarning(context, message, "", dismissedListener);
    }

    public static Messagebar makeWarning(Activity context, String message, String actionMessage, OnActionListener dismissedListener) {
        return makeWarning(getCoordinatorView(context), message, actionMessage, dismissedListener);
    }

    public static Messagebar makeWarning(View view, String message, OnActionListener dismissedListener) {
        return makeWarning(view, message, "", dismissedListener);
    }

    public static Messagebar makeWarning(View view, String message, String actionMessage, OnActionListener dismissedListener) {
        return new Messagebar(view, message, actionMessage, MESSAGE_WARNING, dismissedListener);
    }

    public static Messagebar makeError(Activity context, String message, OnActionListener dismissedListener) {
        return makeError(context, message, "", dismissedListener);
    }

    public static Messagebar makeError(Activity context, String message, String actionMessage, OnActionListener dismissedListener) {
        return makeError(getCoordinatorView(context), message, actionMessage, dismissedListener);
    }

    public static Messagebar makeError(View view, String message, OnActionListener dismissedListener) {
        return makeError(view, message, "", dismissedListener);
    }

    public static Messagebar makeError(View view, String message, String actionMessage, OnActionListener dismissedListener) {
        return new Messagebar(view, message, actionMessage, MESSAGE_ERROR, dismissedListener);
    }

    public void show() {
        snackbar.show();
    }

    public interface OnActionListener {
        void onAction();
    }
}
