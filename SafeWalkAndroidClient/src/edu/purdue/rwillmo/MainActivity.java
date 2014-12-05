package edu.purdue.rwillmo;

import edu.purdue.rwillmo.R;
import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements SubmitCallbackListener,
		StartOverCallbackListener {

	/**
	 * The ClientFragment used by the activity.
	 */
	private ClientFragment clientFragment;

	/**
	 * The ServerFragment used by the activity.
	 */
	private ServerFragment serverFragment;

	/**
	 * UI component of the ActionBar used for navigation.
	 */
	private Button left;
	private Button right;
	private TextView title;

	private String protocol;

	/**
	 * Called once the activity is created.
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_layout);

		this.clientFragment = ClientFragment.newInstance(this);
		this.serverFragment = ServerFragment.newInstance();

		FragmentTransaction ft = getFragmentManager().beginTransaction();
		ft.add(R.id.fl_main, this.clientFragment);
		ft.commit();
	}

	/**
	 * Creates the ActionBar: - Inflates the layout - Extracts the components
	 */
	@SuppressLint("InflateParams")
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		final ViewGroup actionBarLayout = (ViewGroup) getLayoutInflater()
				.inflate(R.layout.action_bar, null);

		// Set up the ActionBar
		final ActionBar actionBar = getActionBar();
		actionBar.setDisplayShowHomeEnabled(false);
		actionBar.setDisplayShowTitleEnabled(false);
		actionBar.setDisplayShowCustomEnabled(true);
		actionBar.setCustomView(actionBarLayout);

		// Extract the UI component.
		this.title = (TextView) actionBarLayout.findViewById(R.id.tv_title);
		this.left = (Button) actionBarLayout.findViewById(R.id.bu_left);
		this.right = (Button) actionBarLayout.findViewById(R.id.bu_right);
		this.right.setVisibility(View.INVISIBLE);

		return true;
	}

	/**
	 * Callback function called when the user click on the right button of the
	 * ActionBar.
	 * 
	 * @param v
	 */
	public void onRightClick(View v) {
		FragmentTransaction ft = getFragmentManager().beginTransaction();

		this.title.setText(this.getResources().getString(R.string.client));
		this.left.setVisibility(View.VISIBLE);
		this.right.setVisibility(View.INVISIBLE);
		ft.replace(R.id.fl_main, this.clientFragment);
		ft.commit();
	}

	/**
	 * Callback function called when the user click on the left button of the
	 * ActionBar.
	 * 
	 * @param v
	 */
	public void onLeftClick(View v) {
		FragmentTransaction ft = getFragmentManager().beginTransaction();

		this.title.setText(this.getResources().getString(R.string.server));
		this.left.setVisibility(View.INVISIBLE);
		this.right.setVisibility(View.VISIBLE);
		ft.replace(R.id.fl_main, this.serverFragment);
		ft.commit();

	}

	/**
	 * Callback function called when the user click on the submit button.
	 */
	@Override
	public void onSubmit() {
		// TODO: Get client info via client fragment
		String name = this.clientFragment.getName();
		String toLocation = this.clientFragment.getToLocation().substring(0,
				this.clientFragment.getToLocation().indexOf(" "));
		String fromLocation = this.clientFragment.getFromLocation().substring(
				0, this.clientFragment.getFromLocation().indexOf(" "));
		// Server info
		String host = this.serverFragment.getHost(getResources().getString(
				R.string.default_host));
		int port = this.serverFragment.getPort(Integer.parseInt(getResources()
				.getString(R.string.default_port)));

		if (name.contains(",")
				|| name.equals("")
				|| host.equals("")
				|| host.contains(" ")
				|| port < 1
				|| port > 65535
				|| (toLocation.substring(0, 1).equals("*") && !protocol
						.equals("2")) || toLocation.equals(fromLocation)) {
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			if (name.contains(","))
				builder.setMessage("ERROR: Your name cannot contain a comma.");
			else if (name.equals(""))
				builder.setMessage("ERROR: You must input a name.");
			else if (host.equals(""))
				builder.setMessage("ERROR: Host field empty.");
			else if (host.contains(" "))
				builder.setMessage("ERROR: Host field cannot contain a space.");
			else if (port < 1 || port > 65535)
				builder.setMessage("ERROR: Port number must be between 1 and 65535.");
			else if (toLocation.substring(0, 1).equals("*")
					&& !protocol.equals("2"))
				builder.setMessage("ERROR: Must be a volunteer to request * as TO location.");
			else if (toLocation.equals(fromLocation))
				builder.setMessage("ERROR: FROM and TO locations cannot be the same.");
			builder.setTitle("INPUT ERROR");
			builder.setIcon(android.R.drawable.ic_dialog_alert);
			builder.setPositiveButton("Accept",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int id) {

							dialog.cancel();
						}
					});
			AlertDialog alert = builder.create();
			alert.setCanceledOnTouchOutside(false);
			alert.show();
		} else {
			Toast.makeText(
					getApplication(),
					"[ " + name + "," + fromLocation + "," + toLocation + ","
							+ protocol + " ]", Toast.LENGTH_LONG).show();

			// TODO: Need to get command from client fragment
			String command = this.getResources().getString(
					R.string.default_command);

			FragmentTransaction ft = getFragmentManager().beginTransaction();

			this.title.setText(getResources().getString(R.string.match));
			this.left.setVisibility(View.INVISIBLE);
			this.right.setVisibility(View.INVISIBLE);

			// TODO: You may want additional parameters here if you tailor
			// the match fragment
			MatchFragment frag = MatchFragment.newInstance(this, host, port,
					command, name);

			ft.replace(R.id.fl_main, frag);
			ft.commit();
		}
	}

	/**
	 * Callback function call from MatchFragment when the user want to create a
	 * new request.
	 */
	@Override
	public void onStartOver() {
		onRightClick(null);
	}

	// Listener method for radio button click
	public void onRadioButtonClicked(View radioButton) {
		// get selected radio button from radioGroup
		int selectedId = radioButton.getId();
		if (selectedId == R.id.radio_neutral) {
			protocol = "0";
		} else if (selectedId == R.id.radio_requester) {
			protocol = "1";
		} else if (selectedId == R.id.radio_volunteer) {
			protocol = "2";
		}
	}

}
