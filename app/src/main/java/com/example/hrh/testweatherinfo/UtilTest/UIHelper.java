package com.example.hrh.testweatherinfo.UtilTest;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.example.hrh.testweatherinfo.data.SimpleBackPage;

/**
 * Created by hrh on 2015/10/6.
 */
public class UIHelper {

    public static void showSimpleBack(Context context, SimpleBackPage page) {
//        Intent intent = new Intent(context, SimpleBackActivity.class);
//        intent.putExtra(SimpleBackActivity.BUNDLE_KEY_PAGE, page.getValue());
//        context.startActivity(intent);
        Intent intent = new Intent(context, page.getClz());
        context.startActivity(intent);
    }

    public static void showSimpleBack(Context context, SimpleBackPage page,
                                      Bundle args) {
        Intent intent = new Intent(context, page.getClz());
//        intent.putExtra(SimpleBackActivity.BUNDLE_KEY_ARGS, args);
//        intent.putExtra(SimpleBackActivity.BUNDLE_KEY_PAGE, page.getValue());
        context.startActivity(intent);
    }

    /**
     * ¡¤¡é?¨ªApp ¨°¨¬3¡ê¡À¨¤¨¤¡ê¡À¡§??
     * @param context
     */
    public static void sendAppCrashReport(final Context context) {
        DialogHelp.getConfirmDialog(context, "3¨¬D¨°¡¤¡é¨¦¨²¨°¨¬3¡ê", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // ¨ª?3?
                System.exit(-1);
            }
        }).show();
    }
}
