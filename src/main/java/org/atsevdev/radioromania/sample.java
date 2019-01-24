package org.atsevdev.radioromania;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.v4.media.MediaDescriptionCompat;
import android.support.v4.media.MediaMetadataCompat;

//java file to use bitmap in notification


public final class sample {


public final int bitmap;


    public sample(int bitmap) {
        this.bitmap = bitmap;
    }

    public static final sample[] SAMPLES = new sample[]{
            new sample(R.mipmap.app_radio_romania_round)

    };

    public static MediaDescriptionCompat getMediaDescription(Context context, sample sample){
        Bundle extras = new Bundle();
        Bitmap bitmap = getBitmap(context, sample.bitmap);
        extras.putParcelable(MediaMetadataCompat.METADATA_KEY_DISPLAY_ICON, bitmap);
        return new MediaDescriptionCompat.Builder()
                .setTitle("Radio Romania")
                .setIconBitmap(bitmap)
                .setExtras(extras)
                .build();
    }

    public static Bitmap getBitmap(Context context, @DrawableRes int bitmapResource) {
        return ((BitmapDrawable) context.getResources().getDrawable(bitmapResource)).getBitmap();
    }


}