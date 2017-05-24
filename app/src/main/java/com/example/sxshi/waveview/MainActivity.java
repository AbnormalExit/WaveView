package com.example.sxshi.waveview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.sxshi.waveview.view.BeizierView;
import com.example.sxshi.waveview.view.DrawPanel;
import com.example.sxshi.waveview.view.PathTest;
import com.example.sxshi.waveview.view.WaveView;

public class MainActivity extends AppCompatActivity {
    private Button mBtnReset;
    private Button mBtnStart;
    private DrawPanel drawPanel;
    private WaveView waveView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        waveView = (WaveView) findViewById(R.id.waveView);
        mBtnReset= (Button) findViewById(R.id.btn_reset);
        mBtnStart= (Button) findViewById(R.id.btn_start);
        mBtnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                waveView.startAnim();
            }
        });
        mBtnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                waveView.reset();
            }
        });
//        setContentView(new PathTest(this));
//        setContentView(new BeizierView(this));
/*        mBtnReset= (Button) findViewById(R.id.btn_reset);
        drawPanel= (DrawPanel) findViewById(R.id.drawpanel);
        mBtnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawPanel.reset();
            }
        });*/
    }
}
