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

        addTopView()
        addBottomView()
    }


    /**
     * Add header on the top of QR Code scanner
     */
    private fun addTopView() {
        val inflater = LayoutInflater.from(baseContext)
        val view = inflater.inflate(R.layout.header_inflate, null)

        setTopView(view)
    }

    /**
     * Add footer on the bottom of QR Code scanner
     */
    private fun addBottomView() {
        val inflater = LayoutInflater.from(baseContext)
        val view = inflater.inflate(R.layout.footer_inflate, null)
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

    /**
     * When a Barcode is detected this func is triggered
     */
    override fun onBarcodeDetected(barcode: Barcode?) {
        runOnUiThread {
            Toast.makeText(this, barcode!!.displayValue, Toast.LENGTH_SHORT).show()
        }
    }
}