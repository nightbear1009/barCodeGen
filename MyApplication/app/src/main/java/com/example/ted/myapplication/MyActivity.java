package com.example.ted.myapplication;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.zxing.BarcodeFormat;

import de.greenrobot.event.EventBus;


public class MyActivity extends Activity {

    private BarcodeFormat type;
    private String input;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Ted","type "+type);
               new GenBarCodeAsyncTask((int)converdptopx(400),(int)converdptopx(400),type).execute(input);
            }
        });

        ((RadioGroup)findViewById(R.id.radiogroup)).setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch(checkedId){
                    case R.id.UPCA:
                        type = BarcodeFormat.UPC_A;
                        input = "012345678905";
                        break;
//                    case R.id.UPCE://fail
//                        type = BarcodeFormat.UPC_E;
//                        input = "0123456789012";
//                        break;
                    case R.id.EAN8:
                        type = BarcodeFormat.EAN_8;
                        input = "65833254";
                        break;
                    case R.id.EAN13:
                        type = BarcodeFormat.EAN_13;
                        input = "7501054530107";
                        break;
                    case R.id.Code39:
                        type = BarcodeFormat.CODE_39;
                        input = "39123439";
                        break;
//                    case R.id.Code93://fail
//                        type = BarcodeFormat.CODE_93;
//                        input = "1234567890";
//                        break;
                    case R.id.Code128:
                        type = BarcodeFormat.CODE_128;
                        input = "4200006200";
                        break;
                    case R.id.ITF:
                        type = BarcodeFormat.ITF;
                        input = "05012345678900";
                        break;
                    case R.id.Codabar:
                        type = BarcodeFormat.CODABAR;
                        input = "A40156B";
                        break;
//                    case R.id.RSS14://fail
//                        type = BarcodeFormat.RSS_14;
//                        input = "0120012345678909";
//                        break;
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        EventBus.getDefault().unregister(this);
    }

    private float converdptopx(int dp){
        Resources r = getResources();
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics());
    }

    public void onEventMainThread(GenBarCodeAsyncTask.GenBarCodeEvent event){
        TextView textview = (TextView)findViewById(R.id.textview);
        textview.setText(input);
        ImageView imageview = (ImageView)findViewById(R.id.imageview);
        imageview.setBackground(null);
        imageview.setBackground(new BitmapDrawable(event.bitmap));
    }


}
