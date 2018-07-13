package com.wipro.wipro.Utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.view.View;

import com.wipro.wipro.R;

/**
 * This class stands for displaying Alerts for Errors.
 */
public class AlertManager {

    /**
     * This method will show Error
     *
     * @param context     : Context for Alert
     * @param header      : Header text
     * @param description : Description text
     */
    public static AlertDialog showError(final Context context, String header, String description) {
        return showError(context, header, description, null);
    }

    /**
     * This method will show Error
     *
     * @param context        : Context for Alert
     * @param header         : Header text
     * @param description    : Description text
     * @param clickListener: OK button click listener
     */
    public static AlertDialog showError(final Context context, String header, String description, final View.OnClickListener clickListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(false);
        final AlertDialog alert = builder.create();
        DialogHelper viewHelper = new DialogHelper(context, header, description, context.getString(R.string.ok), view -> {
            alert.dismiss();
            if (clickListener != null) {
                clickListener.onClick(view);
            }
        });
        alert.setView(viewHelper.getDialogView(), 0, 0, 0, 0);
        if ((context instanceof Activity) && !((Activity) context).isFinishing() && !((Activity) context).isDestroyed()) {
            alert.show();
        }
        return alert;
    }
}