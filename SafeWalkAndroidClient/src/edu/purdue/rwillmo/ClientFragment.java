package edu.purdue.rwillmo;

import edu.purdue.rwillmo.R;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;

/**
 * This fragment is the "page" where the user inputs information about the
 * request, he/she wishes to send.
 *
 * @author YL
 */
public class ClientFragment extends Fragment implements OnClickListener {

	/**
	 * Activity which have to receive callbacks.
	 */
	private SubmitCallbackListener activity;
	
	private EditText name;
	
	private RadioGroup radioGroup;
	
	private String protocol;
	
	private Button submit;

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
        
		// Adds radio listener
		radioGroup = (RadioGroup) view.findViewById(R.id.radioGroup1);
		view.findViewById(R.id.bu_submit).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// get selected radio button from radioGroup
				int selectedId = radioGroup.getCheckedRadioButtonId();
				if (selectedId == R.id.radio_neutral) {
					protocol = "0";
				} else if (selectedId == R.id.radio_requester) {
					protocol = "1";
				} else if (selectedId == R.id.radio_volunteer) {
					protocol = "2";
				}
			}
		});
		
		// TODO: import your Views from the layout here. See example in
		// ServerFragment.
		this.name = (EditText) view.findViewById(R.id.tv_name);
	    
        return view;
	}
	
	public String getName() {
		System.out.println(this.name);
		return this.name != null ? name.getText().toString() : "Muhammad Lee";
		
	}
	
	/**
	 * Callback function for the OnClickListener interface.
	 */
	@Override
	public void onClick(View v) {
		this.activity.onSubmit();
	}
}
