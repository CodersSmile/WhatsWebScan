package com.whatswebdots.whatswebscannerpy.gbwhats.gb_countrypicker;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.futuremind.recyclerviewfastscroll.FastScroller;
import com.whatswebdots.whatswebscannerpy.gbwhats.R;

import java.lang.reflect.Field;
import java.util.Iterator;
import java.util.List;


public class CountryCodeDialog {
    static Context context;
    static Dialog dialog;
    private static Field sCursorDrawableField;
    private static Field sCursorDrawableResourceField;
    private static Field sEditorField;

    CountryCodeDialog() {
    }

    static {
        Field field;
        Field field2;
        Field field3;
        Field field4;
        Field field5;
        Class<?> cls = null;
        boolean z = true;
        try {
            field3 = TextView.class.getDeclaredField("mCursorDrawableRes");
            try {
                field3.setAccessible(true);
                if (Build.VERSION.SDK_INT < 16) {
                    cls = TextView.class;
                    field = null;
                } else {
                    field5 = TextView.class.getDeclaredField("mEditor");
                    try {
                        field5.setAccessible(true);
                        field = field5;
                        cls = field5.getType();
                    } catch (Exception unused) {
                        field4 = null;
                        field = field5;
                        field2 = field4;
                        if (!z) {
                        }
                    }
                }
                try {
                    field2 = cls.getDeclaredField("mCursorDrawable");
                    try {
                        field2.setAccessible(true);
                        z = false;
                    } catch (Exception unused2) {
                        field4 = field2;
                        field5 = field;
                        field = field5;
                        field2 = field4;
                        if (!z) {
                        }
                    }
                } catch (Exception unused3) {
                    field5 = field;
                    field4 = null;
                    field = field5;
                    field2 = field4;
                    if (!z) {
                    }
                }
            } catch (Exception unused4) {
                field5 = null;
                field4 = field5;
                field = field5;
                field2 = field4;
                if (!z) {
                }
            }
        } catch (Exception unused5) {
            field3 = null;
            field5 = null;
            field4 = field5;
            field = field5;
            field2 = field4;
            if (!z) {
            }
        }
        if (!z) {
            sEditorField = null;
            sCursorDrawableField = null;
            sCursorDrawableResourceField = null;
//            return;
        }
        sEditorField = field;
        sCursorDrawableField = field2;
        sCursorDrawableResourceField = field3;
    }

    public static void openCountryCodeDialog(CountryCodePicker countryCodePicker) {
        openCountryCodeDialog(countryCodePicker, null);
    }

    public static void openCountryCodeDialog(final CountryCodePicker countryCodePicker, String str) {
        boolean z;
        context = countryCodePicker.getContext();
        dialog = new Dialog(context);
        countryCodePicker.refreshCustomMasterList();
        countryCodePicker.refreshPreferredCountries();
        List<CCPCountry> customMasterCountryList = CCPCountry.getCustomMasterCountryList(context, countryCodePicker);
        dialog.requestWindowFeature(1);
        dialog.getWindow().setContentView(R.layout.gb_layout_picker_dialog);
        if (!countryCodePicker.isSearchAllowed() || !countryCodePicker.isDialogKeyboardAutoPopup()) {
            dialog.getWindow().setSoftInputMode(2);
        } else {
            dialog.getWindow().setSoftInputMode(4);
        }
        RecyclerView recyclerView = (RecyclerView) dialog.findViewById(R.id.recycler_countryDialog);
        TextView textView = (TextView) dialog.findViewById(R.id.textView_title);
        RelativeLayout relativeLayout = (RelativeLayout) dialog.findViewById(R.id.rl_query_holder);
        ImageView imageView = (ImageView) dialog.findViewById(R.id.img_clear_query);
        EditText editText = (EditText) dialog.findViewById(R.id.editText_search);
        TextView textView2 = (TextView) dialog.findViewById(R.id.textView_noresult);
        RelativeLayout relativeLayout2 = (RelativeLayout) dialog.findViewById(R.id.rl_holder);
        ImageView imageView2 = (ImageView) dialog.findViewById(R.id.img_dismiss);
        try {
            if (countryCodePicker.getDialogTypeFace() != null) {
                if (countryCodePicker.getDialogTypeFaceStyle() != -99) {
                    textView2.setTypeface(countryCodePicker.getDialogTypeFace(), countryCodePicker.getDialogTypeFaceStyle());
                    editText.setTypeface(countryCodePicker.getDialogTypeFace(), countryCodePicker.getDialogTypeFaceStyle());
                    textView.setTypeface(countryCodePicker.getDialogTypeFace(), countryCodePicker.getDialogTypeFaceStyle());
                } else {
                    textView2.setTypeface(countryCodePicker.getDialogTypeFace());
                    editText.setTypeface(countryCodePicker.getDialogTypeFace());
                    textView.setTypeface(countryCodePicker.getDialogTypeFace());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (countryCodePicker.getDialogBackgroundColor() != 0) {
            relativeLayout2.setBackgroundColor(countryCodePicker.getDialogBackgroundColor());
        }
        if (countryCodePicker.isShowCloseIcon()) {
            imageView2.setVisibility(View.VISIBLE);
            imageView2.setOnClickListener(new View.OnClickListener() {
                /* class com.whatsweb.whatswebscanner.gbwhats.countrypicker.CountryCodeDialog.AnonymousClass1 */

                public void onClick(View view) {
                    CountryCodeDialog.dialog.dismiss();
                }
            });
        } else {
            imageView2.setVisibility(View.GONE);
        }
        if (!countryCodePicker.getCcpDialogShowTitle()) {
            textView.setVisibility(View.GONE);
        }
        if (countryCodePicker.getDialogTextColor() != 0) {
            int dialogTextColor = countryCodePicker.getDialogTextColor();
            imageView.setColorFilter(dialogTextColor);
            imageView2.setColorFilter(dialogTextColor);
            textView.setTextColor(dialogTextColor);
            textView2.setTextColor(dialogTextColor);
            editText.setTextColor(dialogTextColor);
            editText.setHintTextColor(Color.argb(100, Color.red(dialogTextColor), Color.green(dialogTextColor), Color.blue(dialogTextColor)));
        }
        if (countryCodePicker.getDialogSearchEditTextTintColor() != 0 && Build.VERSION.SDK_INT >= 21) {
            editText.setBackgroundTintList(ColorStateList.valueOf(countryCodePicker.getDialogSearchEditTextTintColor()));
            setCursorColor(editText, countryCodePicker.getDialogSearchEditTextTintColor());
        }
        textView.setText(countryCodePicker.getDialogTitle());
        editText.setHint(countryCodePicker.getSearchHintText());
        textView2.setText(countryCodePicker.getNoResultACK());
        if (!countryCodePicker.isSearchAllowed()) {
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) recyclerView.getLayoutParams();
            layoutParams.height = -2;
            recyclerView.setLayoutParams(layoutParams);
        }
        CountryCodeAdapter countryCodeAdapter = new CountryCodeAdapter(context, customMasterCountryList, countryCodePicker, relativeLayout, editText, textView2, dialog, imageView);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(countryCodeAdapter);
        FastScroller fastScroller = (FastScroller) dialog.findViewById(R.id.fastscroll);
        fastScroller.setRecyclerView(recyclerView);
        if (countryCodePicker.isShowFastScroller()) {
            if (countryCodePicker.getFastScrollerBubbleColor() != 0) {
                fastScroller.setBubbleColor(countryCodePicker.getFastScrollerBubbleColor());
            }
            if (countryCodePicker.getFastScrollerHandleColor() != 0) {
                fastScroller.setHandleColor(countryCodePicker.getFastScrollerHandleColor());
            }
            if (countryCodePicker.getFastScrollerBubbleTextAppearance() != 0) {
                try {
                    fastScroller.setBubbleTextAppearance(countryCodePicker.getFastScrollerBubbleTextAppearance());
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        } else {
            fastScroller.setVisibility(View.GONE);
        }
        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            /* class com.whatsweb.whatswebscanner.gbwhats.countrypicker.CountryCodeDialog.AnonymousClass2 */

            public void onDismiss(DialogInterface dialogInterface) {
                CountryCodeDialog.hideKeyboard(CountryCodeDialog.context);
                if (countryCodePicker.getDialogEventsListener() != null) {
                    countryCodePicker.getDialogEventsListener().onCcpDialogDismiss(dialogInterface);
                }
            }
        });
        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            /* class com.whatsweb.whatswebscanner.gbwhats.countrypicker.CountryCodeDialog.AnonymousClass3 */

            public void onCancel(DialogInterface dialogInterface) {
                CountryCodeDialog.hideKeyboard(CountryCodeDialog.context);
                if (countryCodePicker.getDialogEventsListener() != null) {
                    countryCodePicker.getDialogEventsListener().onCcpDialogCancel(dialogInterface);
                }
            }
        });
        if (str != null) {
            if (countryCodePicker.preferredCountries != null) {
                Iterator<CCPCountry> it = countryCodePicker.preferredCountries.iterator();
                while (true) {
                    if (it.hasNext()) {
                        if (it.next().nameCode.equalsIgnoreCase(str)) {
                            z = true;
                            break;
                        }
                    } else {
                        break;
                    }
                }
            }
            z = false;
            if (!z) {
                int size = (countryCodePicker.preferredCountries == null || countryCodePicker.preferredCountries.size() <= 0) ? 0 : countryCodePicker.preferredCountries.size() + 1;
                int i = 0;
                while (true) {
                    if (i >= customMasterCountryList.size()) {
                        break;
                    } else if (customMasterCountryList.get(i).nameCode.equalsIgnoreCase(str)) {
                        recyclerView.scrollToPosition(i + size);
                        break;
                    } else {
                        i++;
                    }
                }
            }
        }
        dialog.show();
        if (countryCodePicker.getDialogEventsListener() != null) {
            countryCodePicker.getDialogEventsListener().onCcpDialogOpen(dialog);
        }
    }

    /* access modifiers changed from: private */
    public static void hideKeyboard(Context context2) {
        if (context2 instanceof Activity) {
            Activity activity = (Activity) context2;
            InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService("input_method");
            View currentFocus = activity.getCurrentFocus();
            if (currentFocus == null) {
                currentFocus = new View(activity);
            }
            inputMethodManager.hideSoftInputFromWindow(currentFocus.getWindowToken(), 0);
        }
    }

    static void setCursorColor(EditText editText, int i) {
        Field field = sCursorDrawableField;
        if (field != null) {
            try {
                Drawable drawable = getDrawable(editText.getContext(), sCursorDrawableResourceField.getInt(editText));
                drawable.setColorFilter(i, PorterDuff.Mode.SRC_IN);
                field.set(Build.VERSION.SDK_INT < 16 ? editText : sEditorField.get(editText), new Drawable[]{drawable, drawable});
            } catch (Exception unused) {
            }
        }
    }

    static void clear() {
        Dialog dialog2 = dialog;
        if (dialog2 != null) {
            dialog2.dismiss();
        }
        dialog = null;
        context = null;
    }

    private static Drawable getDrawable(Context context2, int i) {
        if (Build.VERSION.SDK_INT < 21) {
            return context2.getResources().getDrawable(i);
        }
        return context2.getDrawable(i);
    }
}
