package edu.purdue.rwillmo;

import edu.purdue.rwillmo.R;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

/**
 * This fragment is the "page" where the user inputs information about the
 * request, he/she wishes to send.
 *
 * @author YL
 */
public class ClientFragment extends Fragment implements OnClickListener,
		OnItemSelectedListener {

	/**
	 * Activity which have to receive callbacks.
	 */
	private SubmitCallbackListener activity;

	private EditText name;
	private String protocol;
	private String toLocation;
	private String fromLocation;

	/**
	 * Creates a ProfileFragment
	 * 
	 * @param activity
	 *            activity to notify once the user click on the submit Button.
	 * 
	 *            ** DO NOT CREATE A CONSTRUCTOR FOR MatchFragment **
	 * 
	 * @return the fragment initialized.
	 */
	// ** DO NOT CREATE A CONSTRUCTOR FOR ProfileFragment **
	public static ClientFragment newInstance(SubmitCallbackListener activity) {
		ClientFragment f = new ClientFragment();

		f.activity = activity;
		return f;
	}

	/**
	 * Called when the fragment will be displayed.
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if (container == null) {
			return null;
		}

		View view = inflater.inflate(R.layout.client_fragment_layout,
				container, false);

		/**
		 * Register this fragment to be the OnClickListener for the submit
		 * Button.
		 */
		view.findViewById(R.id.bu_submit).setOnClickListener(this);

		// TODO: import your Views from the layout here. See example in
		// ServerFragment.
		this.name = (EditText) view.findViewById(R.id.tv_name);

		// From spinner
		Spinner fromSpinner = (Spinner) view.findViewById(R.id.from_spinner);
		// Create an ArrayAdapter using the string array and a default spinner
		// layout
		ArrayAdapter<CharSequence> fromAdapter = ArrayAdapter
				.createFromResource(view.getContext(), R.array.from_locations,
						android.R.layout.simple_spinner_item);
		// Specify the layout to use when the list of choices appears
		fromAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// Apply the adapter to the spinner
		fromSpinner.setAdapter(fromAdapter);
		// Add a listener to the spinner
		fromSpinner.setOnItemSelectedListener(this);

		// To spinner
		Spinner toSpinner = (Spinner) view.findViewById(R.id.to_spinner);
		// Create an ArrayAdapter using the string array and a default spinner
		// layout
		ArrayAdapter<CharSequence> toAdapter = ArrayAdapter.createFromResource(
				view.getContext(), R.array.to_locations,
				android.R.layout.simple_spinner_item);
		// Specify the layout to use when the list of choices appears
		toAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// Apply the adapter to the spinner
		toSpinner.setAdapter(toAdapter);
		// Add a listener to the spinner
		toSpinner.setOnItemSelectedListener(this);

		return view;
	}

	public String getName() {
		System.out.println(this.name);
		return this.name.getText().toString();

	}

	public String getToLocation() {
		return this.toLocation;
	}

	public String getFromLocation() {
		return this.fromLocation;
	}

	// Listener methods for spinners
	public void onItemSelected(AdapterView<?> parent, View view, int pos,
			long id) {
		if (parent.getId() == R.id.from_spinner) {
			fromLocation = parent.getItemAtPosition(pos).toString();
		} else {
			toLocation = parent.getItemAtPosition(pos).toString();
		}
	}

	public void onNothingSelected(AdapterView<?> parent) {
		// Nothing Happens
	}

	/**
	 * Callback function for the OnClickListener interface.
	 */
	@Override
	public void onClick(View v) {
		this.activity.onSubmit();
	}
}
