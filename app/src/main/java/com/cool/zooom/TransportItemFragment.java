package com.cool.zooom;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link TransportItemFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link TransportItemFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TransportItemFragment extends Fragment {

    private String transportType_val;
    private String cost_val;
    private String time_departure_val;
    private String time_arrival_val;

    private OnFragmentInteractionListener mListener;

    /**
     * Create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment TransportItemFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TransportItemFragment newInstance(String transportType, String cost, String time_departure,
                                                    String time_arrival) {
        TransportItemFragment fragment = new TransportItemFragment();
        Bundle args = new Bundle();
        args.putString("transportType", transportType);
        args.putString("cost", cost);
        args.putString("time_departure", time_departure);
        args.putString("time_arrival", time_arrival);
        fragment.setArguments(args);
        return fragment;
    }

    public TransportItemFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            transportType_val = getArguments().getString("transportType");
            cost_val = getArguments().getString("cost");
            time_departure_val = getArguments().getString("time_departure");
            time_arrival_val = getArguments().getString("time_arrival");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_transport_item, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

}
