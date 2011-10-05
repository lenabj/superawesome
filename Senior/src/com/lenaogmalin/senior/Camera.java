package com.lenaogmalin.senior;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore.Images;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class Camera extends Activity {
	private static final int CAMERA_PIC_REQUEST = 1337;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.camera);
		
		Button take;
		Button send;
		
		take = (Button) findViewById(R.id.bTakePic);
		send = (Button) findViewById(R.id.bSendPic);

		take.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				Intent cameraIntent = new Intent(
						android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
				startActivityForResult(cameraIntent, CAMERA_PIC_REQUEST);
			}
		});
		
		send.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				//send mail, get from the one under
						
			}
		});

	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == CAMERA_PIC_REQUEST) {
			
			Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
			ImageView image = (ImageView) findViewById(R.id.imageV);
			image.setImageBitmap(thumbnail);

			String url = Images.Media.insertImage(getContentResolver(),
					thumbnail, "temp.png", null);

			Intent sendIntent = new Intent(Intent.ACTION_SEND);
			sendIntent.putExtra(Intent.EXTRA_EMAIL,
					new String[] { "adresse" });
			sendIntent.putExtra(Intent.EXTRA_SUBJECT, "Subject");
			sendIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse(url));

			sendIntent.setType("image/png");
			startActivity(Intent.createChooser(sendIntent, "Email:"));

		}
	}
}
