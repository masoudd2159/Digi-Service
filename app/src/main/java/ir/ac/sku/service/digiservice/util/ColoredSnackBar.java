package ir.ac.sku.service.digiservice.util;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

public class ColoredSnackBar {

    private static final int BLUE = 0xFF51C9FF;
    private static final int ORANGE = 0xFFFF8D35;
    private static final int GREEN = 0xFF00CE2C;
    private static final int RED = 0xFFFF0000;
    private static final int COLOR_PRIMARY = 0xFFCE2A2A;
    private static final int COLOR_PRIMARY_DARK = 0xFFBD2626;
    private static final int COLOR_ACCENT = 0xFFD99F2C;
    private static final int COLOR_ACCENT_DARK = 0xFFE6A82F;

    private static View getSnackBarLayout(Snackbar snackbar) {
        if (snackbar != null)
            return snackbar.getView();
        return null;
    }

    @SuppressLint("WrongConstant")
    private static Snackbar colorSnackBar(Snackbar snackbar, int color) {
        View snackBarView = getSnackBarLayout(snackbar);
        TextView snackBarText = snackBarView.findViewById(com.google.android.material.R.id.snackbar_text);
        snackBarView.setBackgroundColor(color);
        snackBarText.setTextAlignment(Gravity.CENTER_HORIZONTAL);
        snackBarText.setTextColor(Color.WHITE);
        snackBarText.setTextSize(12);
        return snackbar;
    }

    public static Snackbar info(Snackbar snackbar) {
        return colorSnackBar(snackbar, BLUE);
    }

    public static Snackbar warning(Snackbar snackbar) {
        return colorSnackBar(snackbar, ORANGE);
    }

    public static Snackbar success(Snackbar snackbar) {
        return colorSnackBar(snackbar, GREEN);
    }

    public static Snackbar error(Snackbar snackbar) {
        return colorSnackBar(snackbar, RED);
    }

}
