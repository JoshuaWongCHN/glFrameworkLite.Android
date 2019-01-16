package com.joshua.samples;

import android.opengl.GLSurfaceView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public abstract class BaseGLActivity extends AppCompatActivity implements GLSurfaceView.Renderer {
    private GLSurfaceView mSurface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_gl);
        mSurface = findViewById(R.id.surface_view);
        mSurface.setEGLContextClientVersion(3);
        mSurface.setRenderer(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mSurface != null) {
            mSurface.onResume();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mSurface != null) {
            mSurface.onPause();
        }
    }
}
