package com.android.gallery3d.app;

import java.io.IOException;

import nl.hj200.stereoscopic.ParserException;
import nl.hj200.stereoscopic.Tools3D;
import nl.hj200.stereoscopic.Tools3D.Mode3D;
import nl.hj200.stereoscopic.Tools3D.Orientation;
import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.android.gallery3d.R;

public class View3D extends Activity {

	String interlacable;
	@Override
	public void onBackPressed() {
		try {
			Tools3D.set3DMode(Mode3D.OFF);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finish();
	}
	@Override
	protected void onStop(){
		super.onStop();
		try {
			Tools3D.set3DMode(Mode3D.OFF);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Override
	protected void onPause(){
		super.onPause();
		try {
			Tools3D.set3DMode(Mode3D.OFF);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Override
	protected void onDestroy(){
		super.onDestroy();
		try {
			Tools3D.set3DMode(Mode3D.OFF);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Override
	protected void onResume(){
		super.onResume();
		try {
			Tools3D.set3DMode(Mode3D.LANDSCAPE);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
    @Override
    public boolean onOptionsItemSelected(MenuItem item) 
    {
        switch (item.getItemId()) 
        {
        case android.R.id.home: 
        	finish();
            break;

        default:
            return super.onOptionsItemSelected(item);
        }
        return true;
    }
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_ACTION_BAR_OVERLAY);
		//requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
       getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
                                WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.view3d);
		getActionBar().setDisplayShowTitleEnabled(true);
		getActionBar().setDisplayHomeAsUpEnabled(true);
    	final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
            	getActionBar().hide();
            }
        }, 4000);
		final ImageView picture = (ImageView) findViewById(R.id.interlaced);
		Bundle extras = getIntent().getExtras();
		if(extras != null){
			interlacable = getIntent().getExtras().getString("interlaced");
			String title =  getIntent().getExtras().getString("name");
			setTitle(title);
		}else{
			finish();
		}
    	Tools3D tools = new Tools3D(Orientation.LANDSCAPE);
    	try {
			Tools3D.set3DMode(Mode3D.LANDSCAPE);
        	Bitmap interlaced = tools.interlace(interlacable);
        	picture.setImageBitmap(interlaced);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (OutOfMemoryError e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	picture.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(getActionBar().isShowing()){
					getActionBar().hide();
				}else{
					getActionBar().show();
			    	final Handler handler = new Handler();
			        handler.postDelayed(new Runnable() {
			            @Override
			            public void run() {
			            	getActionBar().hide();
			            }
			        }, 4000);
				}
			}
		});
	}

}
