package com.haijun.opentool.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.text.Html;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


//自定义AlertDialog选择框
public class ChooseAlertDialogUtil {
    private static final String TAG = ChooseAlertDialogUtil.class.getSimpleName();
    Context context;
    private float mScreenWithPercent = 0.85f;
    private int mGravity = Gravity.CENTER; //显示位置，默认中央居中

    public ChooseAlertDialogUtil(Context context) {
        this.context = context;
    }


    public interface OnConfirmClickListener{
        void onConfirmClick(int chooseType);
    }
    public interface OnCancelClickListener{
        void onCancelClick();
    }

    OnConfirmClickListener onConfirmClickListener;
    OnCancelClickListener onCancelClickListener;

    public void setOnConfirmClickListener(OnConfirmClickListener onConfirmClickListener) {
        this.onConfirmClickListener = onConfirmClickListener;
    }

    public void setOnCancelClickListener(OnCancelClickListener onCancelClickListener) {
        this.onCancelClickListener = onCancelClickListener;
    }

    //弹出原生弹出提示框
    public static void showTipDialog(Context context, String message, String confirm, String cancel, DialogInterface.OnClickListener onConfirmClickListener){
        showTipDialog(context,"提示",message,confirm,cancel,onConfirmClickListener);
    }

    //弹出原生弹出提示框
    public static android.app.AlertDialog showTipDialog(Context context, String title, String message, String confirm, String cancel, DialogInterface.OnClickListener onConfirmClickListener){
        if (context!=null){
            android.app.AlertDialog alertDialog = new android.app.AlertDialog.Builder(context)
                    .setTitle(title)
                    .setMessage(message)
                    .setPositiveButton(confirm,onConfirmClickListener)
                    .setNegativeButton(cancel, null)
                    .create();
            alertDialog.setCancelable(false);
            alertDialog.setCanceledOnTouchOutside(false);
            alertDialog.show();
            return alertDialog;
        }
        return null;
    }

    //弹出原生弹出提示框
    public static  android.app.AlertDialog showTipDialog(Context context, String title, String message, String confirm,
                                            String cancel, DialogInterface.OnClickListener onConfirmClickListener,
                                            DialogInterface.OnClickListener onCancelClickListener){
        if (context!=null){
            android.app.AlertDialog alertDialog = new android.app.AlertDialog.Builder(context)
                    .setTitle(title)
                    .setMessage(message)
                    .setPositiveButton(confirm,onConfirmClickListener)
                    .setNegativeButton(cancel, onCancelClickListener)
                    .create();
            alertDialog.setCancelable(false);
            alertDialog.setCanceledOnTouchOutside(false);
            alertDialog.show();
            return alertDialog;
        }
        return null;
    }

    public static void showTipDialog(Context context, String message,String confirm, DialogInterface.OnClickListener onConfirmClickListener){
        if (context!=null){
            android.app.AlertDialog alertDialog = new android.app.AlertDialog.Builder(context)
                    .setTitle("提示")
                    .setMessage(message)
                    .setPositiveButton(confirm,onConfirmClickListener)
                    .create();
            alertDialog.setCanceledOnTouchOutside(false);
            alertDialog.show();
        }
    }

    public static void showTipDialog(Context context, String message,String confirm){
        if (context!=null){

            android.app.AlertDialog alertDialog = new android.app.AlertDialog.Builder(context)
                    .setTitle("提示")
                    .setMessage(message)
                    .setPositiveButton(confirm,null)
                    .create();
            alertDialog.setCanceledOnTouchOutside(false);
            alertDialog.show();
        }
    }



}
