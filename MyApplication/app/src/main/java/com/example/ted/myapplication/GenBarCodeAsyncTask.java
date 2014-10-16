package com.example.ted.myapplication;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.AsyncTask;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

import de.greenrobot.event.EventBus;

public class GenBarCodeAsyncTask extends AsyncTask<String, Integer, Bitmap> {
    public static class GenBarCodeEvent {
        public GenBarCodeEvent(Bitmap bitmap) {
            this.bitmap = bitmap;
        }

        Bitmap bitmap;

    }

    private int width;
    private int height;
    private BarcodeFormat format;

    public GenBarCodeAsyncTask(int _width, int _height, BarcodeFormat _format) {
        width = _width;
        height = _height;
        format = _format;
    }

    @Override
    protected Bitmap doInBackground(String... params) {
        return generateBarCode(params[0]);
    }

    public Bitmap generateBarCode(String data) {
        Bitmap bitmap = null;
        MultiFormatWriter writer = new MultiFormatWriter();
        try {
            BitMatrix bm = writer.encode(data, format, width, height);
            bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);

            for (int i = 0; i < width; i++) {
                for (int j = 0; j < height; j++) {

                    bitmap.setPixel(i, j, bm.get(i, j) ? Color.BLACK : Color.WHITE);
                }
            }
        } catch (WriterException e) {
            e.printStackTrace();
        }

        return bitmap;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        super.onPostExecute(bitmap);
        EventBus.getDefault().post(new GenBarCodeEvent(bitmap));
    }
}