package com.example.onebitmoblie.Helper;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Base64;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

public class ConvertImageBase64 {
    public static String convertImageToBase64(Context context, Uri imageUri) {
        try {
            InputStream inputStream = context.getContentResolver().openInputStream(imageUri);
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
            inputStream.close();

            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
            byte[] imageBytes = byteArrayOutputStream.toByteArray();

            return Base64.encodeToString(imageBytes, Base64.DEFAULT);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
