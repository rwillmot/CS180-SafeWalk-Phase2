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
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;

/**
 * This fragment is the "page" where the user inputs information about the
 * request, he/she wishes to send.
 *
 * @author YL
 */
public class ClientFragment extends Fragment implements OnClickListener,  OnItemSelectedListener{

	/**
	 * Activity which have to receive callbacks.
	 */
	private SubmitCallbackListener activity;
	
	private EditText name;	
	private RadioGroup radioGroup;	
	private String protocol;	
	private Button submit;
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
	    
        return view;
	}
	
	public String getName() {
		System.out.println(this.name);
		return this.name != null ? name.getText().toString() : "Muhammad Lee";
		
	}
	
	// Listener methods for spinners
	public void onItemSelected(AdapterView<?> parent, View view, 
            int pos, long id) {
        if (id == R.id.from_spinner) {
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
