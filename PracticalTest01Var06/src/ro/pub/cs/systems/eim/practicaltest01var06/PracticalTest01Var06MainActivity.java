package ro.pub.cs.systems.eim.practicaltest01var06;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;


public class PracticalTest01Var06MainActivity extends Activity {
	private class ButtonClickListener implements View.OnClickListener {
		@Override
		public void onClick(View v) {
			switch(layout.getVisibility()) {
				case View.VISIBLE:
					moreLessDetailsButton.setText(R.string.more_details);
					layout.setVisibility(View.INVISIBLE);
					break;
				case View.INVISIBLE:
					moreLessDetailsButton.setText(R.string.less_details);
					layout.setVisibility(View.VISIBLE);
					break;
			}
		}
	}
	
	private class ValidationListener implements android.text.TextWatcher {
		@Override
		public void afterTextChanged(Editable arg0) {
			// TODO Auto-generated method stub
		}

		@Override
		public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
				int arg3) {
			// TODO Auto-generated method stub
		}

		@Override
		public void onTextChanged(CharSequence arg0, int arg1, int arg2,
				int arg3) {
			String url = arg0.toString();
			if(url.startsWith("http://")) {
				validationButton.setText(R.string.pass);
				validationButton.setBackground(getResources().getDrawable(R.color.green));
				Log.d("message", "validation");
				
				String uri = uriBox.getText().toString();
				if(serviceStatus == Constants.SERVICE_STOPPED) {
					Intent intent = new Intent(getApplicationContext(), PracticalTest01Var06Service.class);
					intent.putExtra("uri", uri);
					getApplicationContext().startService(intent);
					serviceStatus = Constants.SERVICE_STARTED;
				}
			} else {
				validationButton.setText(R.string.fail);
				validationButton.setBackground(getResources().getDrawable(R.color.red));
			}
		}
	}
	
	private class MessageBroadcastReceiver extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {
			Log.d("[Message]", intent.getStringExtra("message"));			
		}
	}
	
	private class SecondaryActivityButtonClickListener implements View.OnClickListener {
		@Override
		public void onClick(View v) {
			Intent intent = new Intent(getApplicationContext(), PracticalTest01Var06SecondaryActivity.class);
			intent.putExtra("uri", uriBox.getText().toString());
			intent.putExtra("validation", validationButton.getText());
			
			startActivityForResult(intent, SECONDARY_ACTIVITY_REQUEST_CODE);
		}
	}
	
	private int serviceStatus = Constants.SERVICE_STOPPED;
	private static final int SECONDARY_ACTIVITY_REQUEST_CODE = 2016;
	private ButtonClickListener buttonListener = new ButtonClickListener();
	private ValidationListener validationListener = new ValidationListener();
	private SecondaryActivityButtonClickListener secondaryActivityListener = new SecondaryActivityButtonClickListener();
	private Button moreLessDetailsButton = null;
	private Button validationButton = null;
	private Button navigateToSecondaryActivityButton = null;
	private EditText firstBox = null;
	private EditText uriBox = null;
	private LinearLayout layout = null;
	private MessageBroadcastReceiver messageBroadcastReceiver = new MessageBroadcastReceiver();
	private IntentFilter intentFilter = new IntentFilter();
	
	@Override
	protected void onDestroy() {
		Intent intent = new Intent(this, PracticalTest01Var06Service.class);
		stopService(intent);
		super.onDestroy();
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		registerReceiver(messageBroadcastReceiver, intentFilter);
	}
	
	@Override
	protected void onPause() {
		unregisterReceiver(messageBroadcastReceiver);
		super.onPause();
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
		if(requestCode == SECONDARY_ACTIVITY_REQUEST_CODE) {
			Toast t = Toast.makeText(this, "Activity exited with code: " + resultCode, Toast.LENGTH_LONG);
			t.show();
		}
	}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practical_test01_var06_main);
        
        moreLessDetailsButton = (Button)findViewById(R.id.more_less_details_button);
        navigateToSecondaryActivityButton = (Button)findViewById(R.id.secondary_button);
        validationButton = (Button)findViewById(R.id.validation_button);
        layout = (LinearLayout)findViewById(R.id.layout);
        firstBox = (EditText)findViewById(R.id.first_box);
        uriBox = (EditText)findViewById(R.id.uri_box);
        
        moreLessDetailsButton.setOnClickListener(buttonListener);
        navigateToSecondaryActivityButton.setOnClickListener(secondaryActivityListener);
        uriBox.addTextChangedListener(validationListener);
        
        if(savedInstanceState != null) {
        	if(savedInstanceState.containsKey("firstBox")) {
				firstBox.setText(savedInstanceState.getString("firstBox"));
			}
			if(savedInstanceState.containsKey("uriBox")) {
				uriBox.setText(savedInstanceState.getString("uriBox"));
			}
			
			Toast t = Toast.makeText(this,
	        		"FirstBox: " + firstBox.getText().toString() + "; SecondBox: " + uriBox.getText().toString(),
	        		Toast.LENGTH_LONG);
			t.show();
        }
        
        for(int i = 0; i < Constants.actionType.length; ++i) {
			intentFilter.addAction(Constants.actionType[i]);
		}
    }

    @Override
	protected void onSaveInstanceState(Bundle savedInstanceState) {
		if(savedInstanceState == null) {
			return;
		}
		
		savedInstanceState.putString("firstBox", firstBox.getText().toString());
		savedInstanceState.putString("uriBox", uriBox.getText().toString());
	}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.practical_test01_var06_main, menu);
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
