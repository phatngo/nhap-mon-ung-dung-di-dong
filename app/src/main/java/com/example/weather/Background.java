package com.example.weather;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;

public class Background {

    public Background getAfter_noon() {
        return after_noon;
    }

    public void setAfter_noon(Background after_noon) {
        this.after_noon = after_noon;
    }

    public Background getCloud() {
        return cloud;
    }

    public void setCloud(Background cloud) {
        this.cloud = cloud;
    }

    public Background getClouds() {
        return clouds;
    }

    public void setClouds(Background clouds) {
        this.clouds = clouds;
    }

    public Background getNight() {
        return night;
    }

    public void setNight(Background night) {
        this.night = night;
    }

    public Background getRain() {
        return rain;
    }

    public void setRain(Background rain) {
        this.rain = rain;
    }

    public Background getStorm() {
        return storm;
    }

    public void setStorm(Background storm) {
        ;
        this.storm = storm;
    }

    private Background after_noon;
    private Background cloud;
    private Background clouds;
    private Background night;
    private Background rain;
    private Background storm;


    //convert ảnh qua Byte[]
    public byte[] ConverttoArrayByte(ImageView img)
    {
        BitmapDrawable bitmapDrawable = (BitmapDrawable) img.getDrawable();
        Bitmap bitmap=bitmapDrawable.getBitmap();

        ByteArrayOutputStream stream=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        return stream.toByteArray();
    }
}
