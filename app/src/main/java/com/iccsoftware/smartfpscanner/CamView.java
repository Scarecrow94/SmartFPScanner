package com.iccsoftware.smartfpscanner;

import android.app.AlertDialog;
import android.content.Context;
import android.hardware.Camera;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.FileOutputStream;

/**
 * Created by Phitv on 9/22/2016.
 */

public class CamView extends org.opencv.android.JavaCameraView implements Camera.PictureCallback {
    private static final String TAG = "AutoSelfie::camView";
    private String mPictureFileName;
    public CamView(Context context, AttributeSet attrs) {
        super(context, attrs);

    }

    @Override
    public void onPictureTaken(byte[] data, Camera camera) {
        Log.i(TAG, "Saving a bitmap to file");
        // The camera preview was automatically stopped. Start it    /
        //  again.
        mCamera.startPreview();    mCamera.setPreviewCallback(this);
        // Write the image in a file (in jpeg format)
        try {
            FileOutputStream fos = new FileOutputStream(mPictureFileName);
            fos.write(data);
            fos.close();
        } catch (java.io.IOException e) {
            Log.e("PictureDemo", "Exception in photoCallback", e);
        }

    }
    public void takePicture(final String fileName) {
        Log.i(TAG, "Taking picture");
        this.mPictureFileName = fileName;
        // Postview and jpeg are sent in the same buffers if the
        // queue is not empty when performing a capture.
        // Clear up buffers to avoid mCamera.takePicture to be stuck
        // because of a memory issue
        mCamera.setPreviewCallback(null);
        // PictureCallback is implemented by the current class
        mCamera.takePicture(null, null, this);  }

}