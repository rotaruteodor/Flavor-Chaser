package teodor.flavor_chaser_android_app.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import teodor.flavor_chaser_android_app.R;


public class EliquidCalculatorFragment extends Fragment {


    private ImageButton imgButtonAddRecipeFlavor;
    private LinearLayout linearLayoutCalculatorFragFlavors;

    public EliquidCalculatorFragment() {}

    public static EliquidCalculatorFragment newInstance() {
        EliquidCalculatorFragment fragment = new EliquidCalculatorFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initializeNonGraphicalComponents();
    }

    private void initializeNonGraphicalComponents() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_eliquid_calculator, container, false);
        initializeGraphicalComponents(v);
        return v;
    }

    private void initializeGraphicalComponents(View v) {
        imgButtonAddRecipeFlavor = v.findViewById(R.id.imgButtonAddRecipeFlavor);
        linearLayoutCalculatorFragFlavors = v.findViewById(R.id.linearLayoutCalculatorFragFlavors);
        configureGraphicalComponents(v);
    }

    private void configureGraphicalComponents(View v) {
        imgButtonAddRecipeFlavor.setOnClickListener(v1 -> {

            addFlavorRow(v);
        });
    }

    private void addFlavorRow(View v) {
        LayoutInflater vi = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = vi.inflate(R.layout.flavor_recipe_component_layout, null);
        ViewGroup insertPoint = (ViewGroup) v.findViewById(R.id.linearLayoutCalculatorFragFlavors);
        insertPoint.addView(view,
                0,
                new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT));
    }
}







