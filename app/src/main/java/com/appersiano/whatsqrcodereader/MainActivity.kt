package com.appersiano.whatsqrcodereader

import android.graphics.Color
import android.view.LayoutInflater
import android.widget.Button
import android.widget.Toast
import com.appersiano.whatsqrcode.BarcodeCaptureActivity
import com.google.android.gms.vision.barcode.Barcode

class MainActivity : BarcodeCaptureActivity() {

    override fun init() {
        autoFocus = true
        setAutoLight(true)
        scanLineColor(Color.RED)
        setTargetColor(Color.YELLOW)
        setHeaderText("This is an example")

        addBottomView()
    }

    private fun addBottomView() {
        val inflater = LayoutInflater.from(baseContext)
        val view = inflater.inflate(R.layout.sample_inflate, null)
        var button = view.findViewById<Button>(R.id.btnLight)

        button.setOnClickListener({ _ ->
            setAutoLight(false)

            if (isFlashActive) {
                deactivedFlash()
            } else {
                activateFlash()
            }
        })
        setBottomView(view)
    }

    override fun onBarcodeDetected(barcode: Barcode?) {
        runOnUiThread {
            Toast.makeText(this, barcode!!.displayValue, Toast.LENGTH_SHORT).show();
        }
    }
}