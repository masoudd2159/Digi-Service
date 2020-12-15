package ir.ac.sku.service.digiservice.util;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.annotation.DrawableRes;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.net.URI;
import java.net.URISyntaxException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import ir.ac.sku.service.digiservice.R;
import ir.ac.sku.service.digiservice.activity.ActivityWebView;

import static ir.ac.sku.service.digiservice.config.MyLog.DIGI_SERVICE;

public class Tools {

    static final String HEXES = "0123456789abcdef";

    public static void displayImageOriginal(Context ctx, ImageView img, @DrawableRes int drawable) {
        try {
            Glide
                    .with(ctx)
                    .load(drawable)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(img);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void displayImageOriginal(Context ctx, ImageView img, String url) {
        try {
            Glide
                    .with(ctx)
                    .load(url)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(img);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void displayImageOriginal(Context ctx, ImageView img, Bitmap bitmap) {
        try {
            Glide
                    .with(ctx)
                    .load(bitmap)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .into(img);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void toggleArrow(boolean show, View view) {
        toggleArrow(show, view, true);
    }

    public static void toggleArrow(boolean show, View view, boolean delay) {
        if (show) {
            view.animate().setDuration(delay ? 200 : 0).rotation(180);
        } else {
            view.animate().setDuration(delay ? 200 : 0).rotation(0);
        }
    }

    public static void changeOverflowMenuIconColor(Toolbar toolbar, @ColorInt int color) {
        try {
            Drawable drawable = toolbar.getOverflowIcon();
            drawable.mutate();
            drawable.setColorFilter(color, PorterDuff.Mode.SRC_ATOP);
        } catch (Exception e) {
        }
    }

    public static void setSystemBarColor(Activity act) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = act.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(act.getResources().getColor(R.color.colorPrimaryDark));
        }
    }

    public static void setSystemBarColor(Activity act, @ColorRes int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = act.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(act.getResources().getColor(color));
        }
    }

    public static void setSystemBarColorInt(Activity act, @ColorInt int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = act.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(color);
        }
    }

    public static void setSystemBarColorDialog(Context act, Dialog dialog, @ColorRes int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = dialog.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(act.getResources().getColor(color));
        }
    }

    public static void setSystemBarLight(Activity act) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            View view = act.findViewById(android.R.id.content);
            int flags = view.getSystemUiVisibility();
            flags |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            view.setSystemUiVisibility(flags);
        }
    }

    public static void setSystemBarLightDialog(Dialog dialog) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            View view = dialog.findViewById(android.R.id.content);
            int flags = view.getSystemUiVisibility();
            flags |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            view.setSystemUiVisibility(flags);
        }
    }

    public static void clearSystemBarLight(Activity act) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Window window = act.getWindow();
            window.setStatusBarColor(ContextCompat.getColor(act, R.color.colorPrimaryDark));
        }
    }

    public static String getHostName(String url) {
        try {
            URI uri = new URI(url);
            String new_url = uri.getHost();
            if (!new_url.startsWith("www.")) new_url = "www." + new_url;
            return new_url;
        } catch (URISyntaxException e) {
            if (e.getMessage() != null) {
                Log.e("ERROR", e.getMessage());
            }
            return url;
        }
    }

    public static void directLinkToBrowser(Activity activity, String url) {
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            activity.startActivity(intent);
        } catch (Exception e) {
            Toast.makeText(activity, "Ops, Cannot open url", Toast.LENGTH_LONG).show();
        }
    }

    public static void changeMenuIconColor(Menu menu, @ColorInt int color) {
        for (int i = 0; i < menu.size(); i++) {
            Drawable drawable = menu.getItem(i).getIcon();
            if (drawable == null) continue;
            drawable.mutate();
            drawable.setColorFilter(color, PorterDuff.Mode.SRC_ATOP);
        }
    }

    public static void openWebViewActivity(Context context, String url) {
        if (ManagerHelper.checkInternetServices(context)) {
            Intent intent = new Intent(context, ActivityWebView.class);
            intent.putExtra("key.EXTRA_OBJC", url);
            context.startActivity(intent);
        }
    }

    public static String encryptString(String input) {
        Log.i(DIGI_SERVICE + Tools.class.getSimpleName(), input);
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            byte[] messageDigest = md.digest(input.getBytes());
            String textHash = getHex(messageDigest);
            Log.i(DIGI_SERVICE + Tools.class.getSimpleName(), textHash);
            return textHash.toLowerCase();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String getHex(byte[] raw) {
        if (raw == null) {
            return null;
        }
        final StringBuilder hex = new StringBuilder(2 * raw.length);
        for (final byte b : raw) {
            hex.append(HEXES.charAt((b & 0xF0) >> 4))
                    .append(HEXES.charAt((b & 0x0F)));
        }
        return hex.toString();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static String encryptBase64String(String password, String passwordSalt) {

        Log.i(DIGI_SERVICE + Tools.class.getSimpleName(), password);
        Log.i(DIGI_SERVICE + Tools.class.getSimpleName(), passwordSalt);

        try {
            byte[] bytes = password.getBytes();
            byte[] newBytes = new byte[bytes.length * 2];

            for (int i = 0; i < newBytes.length; i++) {
                if (i % 2 == 0)
                    newBytes[i] = bytes[i / 2];
                else newBytes[i] = 0;
            }

            byte[] src = Base64.getDecoder().decode(passwordSalt);
            byte[] dst = new byte[src.length + newBytes.length];
            System.arraycopy(src, 0, dst, 0, src.length);
            System.arraycopy(newBytes, 0, dst, src.length, newBytes.length);
            MessageDigest algorithm = MessageDigest.getInstance("SHA-1");
            byte[] inArray = algorithm.digest(dst);
            String hashedPassword = Base64.getEncoder().encodeToString(inArray);
            Log.i(DIGI_SERVICE + Tools.class.getSimpleName(), hashedPassword);
            return hashedPassword.toLowerCase();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }
}
