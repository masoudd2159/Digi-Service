package ir.ac.sku.service.digiservice.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import ir.ac.sku.service.digiservice.R;

public class CustomToastExit {
    private View view;
    private Toast toast;
    private Context context;

    @SuppressLint("InflateParams")
    private CustomToastExit(Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.context = context;
        this.toast = new Toast(context);
        if (inflater != null) {
            this.view = inflater.inflate(R.layout.toast_exit, null);
        }
        toast.setView(view);
    }

    private void setText(String text) {
        if (view != null)
            ((TextView) view.findViewById(R.id.toastExit_Message)).setText(text);
    }

    private Toast getToast() {
        return toast;
    }

    private View getView() {
        return view;
    }

    private static View getToastView(Toast toast) {
        return (toast == null) ? null : toast.getView();
    }

    public static Toast exit(Context context, String text, int duration) {
        CustomToastExit toastExit = new CustomToastExit(context);
        toastExit.setText(text);
        toastExit.getToast().setDuration(duration);
        return toastExit.getToast();
    }
}
