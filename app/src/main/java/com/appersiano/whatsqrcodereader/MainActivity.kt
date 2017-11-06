package com.appersiano.whatsqrcodereader

import android.graphics.Color
import android.widget.Toast
import com.appersiano.whatsqrcode.BarcodeCaptureActivity
import com.google.android.gms.vision.barcode.Barcode

class MainActivity : BarcodeCaptureActivity() {

    override fun init() {
        autoFocus = true
        autoLight = false
        scanLineColor(Color.RED)
        setTargetColor(Color.YELLOW)
        setHeaderText("This is an example");
    }

    override fun onBarcodeDetected(barcode: Barcode?) {
        runOnUiThread {
            Toast.makeText(this, barcode!!.displayValue, Toast.LENGTH_SHORT).show();
        }

    }
}