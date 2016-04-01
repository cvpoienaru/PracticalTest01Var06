package ro.pub.cs.systems.eim.practicaltest01var06;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class PracticalTest01Var06SecondaryActivity extends Activity {
	private class ButtonClickListener implements View.OnClickListener {
		@Override
		public void onClick(View v) {
			Button button = (Button)v;
			if(button == null) {
				return;
			}
			
			switch(button.getId()) {
				case R.id.ok_button:
					setResult(RESULT_OK, null);
					break;
				case R.id.cancel_button:
					setResult(RESULT_CANCELED, null);
					break;
			}
			
			finish();
		}
	}
	
	private ButtonClickListener listener = new ButtonClickListener();
	private Button okButton = null;
	private Button cancelButton = null;
	private EditText firstBox = null;
	private EditText secondBox = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_practical_test01_var06_secondary);
		
		okButton = (Button)findViewById(R.id.ok_button);
		cancelButton = (Button)findViewById(R.id.cancel_button);
		firstBox = (EditText)findViewById(R.id.secondary_activity_first_box);
		secondBox = (EditText)findViewById(R.id.secondary_activity_uri_box);
		
		Intent intent = getIntent();
		if(intent != null) {
			if(intent.getExtras().containsKey("uri")) {
				firstBox.setText(intent.getExtras().getString("uri"));
			}
			if(intent.getExtras().containsKey("validation")) {
				secondBox.setText(intent.getExtras().getString("validation"));
			}
		}
		
		okButton.setOnClickListener(listener);
		cancelButton.setOnClickListener(listener);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater()
				.inflate(R.menu.practical_test01_var06_secondary, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
