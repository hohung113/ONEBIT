package com.example.onebitmoblie.common;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;

import com.example.onebitmoblie.R;

public class PopupHelper {
    public static void shopPopup(Context context, String message, int textColor, int backgroundColor) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View layout = inflater.inflate(R.layout.custom_toast, null);

        CardView cardView = layout.findViewById(R.id.toast_card);
        cardView.setBackgroundColor(backgroundColor);

        TextView text = layout.findViewById(R.id.toast_text);
        text.setText(message);
        text.setTextColor(textColor);

        Toast toast = new Toast(context);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout);
        toast.show();
    }
}
