package com.example.kamil.qrcodereader;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import com.google.zxing.Result;
import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class MainActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler {

  private ZXingScannerView mScannerView;
  private Button btnScanQR;
  private String result;
  private AlertDialog alert1;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    btnScanQR = (Button) findViewById(R.id.btn_Scan_QR);
    btnScanQR.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        QrScanner();
      }
    });

  }

  public void QrScanner(View view) {

    mScannerView = new ZXingScannerView(this);   // Programmatically initialize the scanner view
    setContentView(mScannerView);
    mScannerView.setResultHandler(this); // Register ourselves as a handler for scan results.
    mScannerView.startCamera();         // Start camera

  }

  public void QrScanner() {

    mScannerView = new ZXingScannerView(this);   // Programmatically initialize the scanner view
    setContentView(mScannerView);
    mScannerView.setResultHandler(this); // Register ourselves as a handler for scan results.
    mScannerView.startCamera();         // Start camera

  }

  @Override
  public void onPause() {
    super.onPause();
    //mScannerView.stopCamera();           // Stop camera on pause
  }

  @Override
  public void handleResult(Result rawResult) {
    // Do something with the result here

    Log.e("handler", rawResult.getText()); // Prints scan results
    Log.e("handler", rawResult.getBarcodeFormat().toString()); // Prints the scan format (qrcode)

    // show the scanner result into dialog box.
    AlertDialog.Builder builder = new AlertDialog.Builder(this);
    builder.setTitle("Scan Result");
    String newResult = rawResult.getText();
    //if ((result == null) || result != null && !newResult.equals(result)) {

    result = newResult;
    builder.setMessage(rawResult.getText());
    builder.setPositiveButton("OK",
        new DialogInterface.OnClickListener() {
          @Override
          public void onClick(DialogInterface dialog, int witch) {
            dialog.dismiss();
            alert1 = null;
            //recreate();
          }
        });
    if (alert1 == null) {
      alert1 = builder.create();
      alert1.show();
    }
    //}

    // If you would like to resume scanning, call this method below:
    mScannerView.resumeCameraPreview(this);

  }
}
