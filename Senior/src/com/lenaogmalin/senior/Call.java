package com.lenaogmalin.senior;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.MediaStore.Images.Media;
import android.view.View;
import android.widget.Button;



public class Call extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.call);
		
		Button takeIt;
		Button sendIt;
		
		
		takeIt = (Button) findViewById(R.id.button1);
		sendIt = (Button) findViewById(R.id.button2);

		
		takeIt.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				takePhoto();
			}
		});
		
		
		sendIt.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				
			}
		});
	}
		private static final int TAKE_PHOTO_CODE = 1;

		private void takePhoto(){
		  final Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		  intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(getTempFile(this)) );
		  startActivityForResult(intent, TAKE_PHOTO_CODE);
		}

		private File getTempFile(Context context){
		  //it will return /sdcard/image.tmp
		  final File path = new File( Environment.getExternalStorageDirectory(), context.getPackageName() );
		  if(!path.exists()){
		    path.mkdir();
		  }
		  return new File(path, "image.png");
		}

		@Override
		protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		  if (resultCode == RESULT_OK) {
		    switch(requestCode){
		      case TAKE_PHOTO_CODE:
		        final File file = getTempFile(this);
		        try {
		          Bitmap captureBmp = Media.getBitmap(getContentResolver(), Uri.fromFile(file) );
		          // do whatever you want with the bitmap (Resize, Rename, Add To Gallery, etc)
		        } catch (FileNotFoundException e) {
		          e.printStackTrace();
		        } catch (IOException e) {
		          e.printStackTrace();
		        }
		      break;
		    }
		  }
		
	
		}

		
}
