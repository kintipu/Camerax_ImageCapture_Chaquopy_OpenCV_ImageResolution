package com.example.Camerax_ImageCapture_Chaquopy_OpenCV_ImageResolution;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.util.Size;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.ImageCaptureException;
import androidx.camera.core.ImageProxy;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.PreviewView;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.LifecycleOwner;


import com.chaquo.python.PyObject;
import com.chaquo.python.Python;
import com.chaquo.python.android.AndroidPlatform;
import com.google.common.util.concurrent.ListenableFuture;

import java.io.ByteArrayOutputStream;
import java.nio.ByteBuffer;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;

public class CameraActivity extends AppCompatActivity {
    private PreviewView previewView;
    private ImageCapture imageCapture;
    private ListenableFuture<ProcessCameraProvider> cameraProviderFuture;
    private TextView textView;
    private Button button;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        previewView = findViewById(R.id.previewView);
        cameraProviderFuture = ProcessCameraProvider.getInstance(this);
        textView = findViewById(R.id.length);
        button = findViewById(R.id.button);
        
        if(!Python.isStarted())
              Python.start(new AndroidPlatform(this ));


        cameraProviderFuture.addListener(new Runnable() {
            @Override
            public void run() {
                try {
                    ProcessCameraProvider cameraProvider = cameraProviderFuture.get();
                    bindImageAnalysis(cameraProvider);
                } catch (ExecutionException | InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, ContextCompat.getMainExecutor(this));
    }


    private void bindImageAnalysis(@NonNull ProcessCameraProvider cameraProvider) {

        Preview preview = new Preview.Builder().build();
        CameraSelector cameraSelector = new CameraSelector.Builder()
                .requireLensFacing(CameraSelector.LENS_FACING_BACK).build();
        preview.setSurfaceProvider(previewView.createSurfaceProvider());

        Executor cameraexecutor = ContextCompat.getMainExecutor(this);
        ImageCapture imageCapture = new ImageCapture.Builder().setTargetResolution(new Size(1200, 1600)).build();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageCapture.takePicture(cameraexecutor, new ImageCapture.OnImageCapturedCallback() {
                    @Override
                    public void onCaptureSuccess(@NonNull ImageProxy image){
                        ByteBuffer byteBuffer = image.getPlanes()[0].getBuffer();

                        byte[] bytes = new byte[byteBuffer.capacity()];
                        byteBuffer.get(bytes);

                        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                        byte[] imageBytes = baos.toByteArray();

//                        String length = String.valueOf((imgString.length()));
//                        textView.setText(length);

                        PyObject obj = pythonn(imageBytes);

                        textView.setText(obj.toString());

                    }

                    @Override
                    public void onError(@NonNull ImageCaptureException error) {
                        error.printStackTrace();
                    }
                });
            }

        });


        cameraProvider.bindToLifecycle((LifecycleOwner)this, cameraSelector, imageCapture,
                preview);
    }

    private PyObject pythonn(byte[] imageBytes) {

        Python py = Python.getInstance();

        final PyObject pyobj = py.getModule("shape");
        PyObject obj = pyobj.callAttr("main",imageBytes);
        return obj;
    }
}
