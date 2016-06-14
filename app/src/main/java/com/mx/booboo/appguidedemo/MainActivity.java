package com.mx.booboo.appguidedemo;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements SurfaceHolder.Callback, MediaPlayer.OnPreparedListener {

    @Bind(R.id.viewPager)
    ViewPager mViewPager;

    private View mView1;
    private View mView2;
    private View mView3;
    private ArrayList<View>mViewcontaniers;
    private SurfaceHolder mHolder1;
    private SurfaceHolder mHolder2;
    private SurfaceHolder mHolder3;

    private MediaPlayer mPlayer;

    private String Path= Environment.getExternalStorageDirectory().getPath()+"/1.mp4";
    private SurfaceView mSurfaceView1;
    private SurfaceView mSurfaceView2;
    private SurfaceView mSurfaceView3;




    /*
context.getClass().getClassLoader().getResourceAsStream("assets/"+资源名);
     */


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        initView();

        mHolder1=mSurfaceView1.getHolder();
        mHolder2=mSurfaceView2.getHolder();
        mHolder3=mSurfaceView3.getHolder();

        mHolder1.addCallback(this);
        mHolder2.addCallback(this);
        mHolder3.addCallback(this);

        mViewPager.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return mViewcontaniers.size();
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view==object;
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                container.addView(mViewcontaniers.get(position));
                return mViewcontaniers.get(position);
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                    container.removeView((View) object);

            }
        });

    }

    private void initView() {

        mViewcontaniers=new ArrayList<>();
        mView1=View.inflate(this,R.layout.guide_view1,null);
        mView2=View.inflate(this,R.layout.guide_view2,null);
        mView3=View.inflate(this,R.layout.guide_view3,null);

        mSurfaceView1= (SurfaceView) mView1.findViewById(R.id.sf1);
        mSurfaceView2= (SurfaceView) mView2.findViewById(R.id.sf2);
        mSurfaceView3= (SurfaceView) mView3.findViewById(R.id.sf3);

        mViewcontaniers.add(mView1);
        mViewcontaniers.add(mView2);
        mViewcontaniers.add(mView3);


    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        mPlayer=new MediaPlayer();
        mPlayer.setDisplay(holder);
        mPlayer.setOnPreparedListener(this);
        //设置循环播放
        mPlayer.setLooping(true);

        if(mHolder1.equals(holder)){
            Path=Environment.getExternalStorageDirectory().getPath()+"/1.mp4";
        }
        if(mHolder2.equals(holder)){
            Path=Environment.getExternalStorageDirectory().getPath()+"/2.mp4";
        }if(mHolder3.equals(holder)){
            Path=Environment.getExternalStorageDirectory().getPath()+"/3.mp4";
        }


        //设置文件的路径
        try {
            mPlayer.setDataSource(Path);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //准备播放
        try{
            mPlayer.prepareAsync();
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }

    @Override
    public void onPrepared(MediaPlayer mp) {

        mp.start();
    }
}
