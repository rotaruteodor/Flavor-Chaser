package teodor.flavor_chaser_android_app.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

import teodor.flavor_chaser_android_app.R;
import teodor.flavor_chaser_android_app.classes.RecipeIngredientResult;


public class EliquidCalculatorFragment extends Fragment {

    private TextView textviewCalculatorFragBaseTag;
    private EditText edittextCalculatorBaseVG;
    private EditText edittextCalculatorBasePG;
    private CheckBox checkboxIndividualPgVg;
    private EditText edittextCalculatorNicotineVG;
    private EditText edittextCalculatorNicotinePG;
    private EditText edittextCalculatorNicotineMg;
    private ImageButton imgButtonAddRecipeFlavor;
    private LinearLayout linearLayoutCalculatorFragFlavors;
    private EditText editTextCalculatorFinalAmount;
    private EditText editTextCalculatorDesiredNicotineStrength;
    private Button buttonCalculateRecipeResults;
    private TableLayout tableLayoutRecipeResults;

    private final Double vgWeightPerMl = 1.26;
    private final Double pgWeightPerMl = 1.04;
    private final Double pureNicotineWeightPerMl = 1.01;
    private final Double flavorWeightPerMl = 1d;
    private final Double flavorPgPercentage = 1d;
    private final Double flavorVgPercentage = 0d;
    private Double totalFinalAmount;

    //todo treat cases when parsing to double fails
    //todo treat cases when it's impossible to make certain eliquid based on ingredients' stats
    //todo treat case when percentages are inputted higher than 100%
    //todo max VG option
    //todo replace cost with DB values
    //todo flavors suggestions from DB
    //todo delete flavor
    //todo treat case when no flavor name was inputted
    //todo style

    public EliquidCalculatorFragment() {
    }

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
        View v = inflater.inflate(R.layout.fragment_eliquid_calculator, container, false);
        initializeGraphicalComponents(v);
        return v;
    }

    private void initializeGraphicalComponents(View v) {
        textviewCalculatorFragBaseTag = v.findViewById(R.id.textviewCalculatorFragBaseTag);
        edittextCalculatorBaseVG = v.findViewById(R.id.edittextCalculatorBaseVG);
        edittextCalculatorBasePG = v.findViewById(R.id.edittextCalculatorBasePG);
        checkboxIndividualPgVg = v.findViewById(R.id.checkboxIndividualPgVg);
        edittextCalculatorNicotineVG = v.findViewById(R.id.edittextCalculatorNicotineVG);
        edittextCalculatorNicotinePG = v.findViewById(R.id.edittextCalculatorNicotinePG);
        edittextCalculatorNicotineMg = v.findViewById(R.id.edittextCalculatorNicotineMg);
        imgButtonAddRecipeFlavor = v.findViewById(R.id.imgButtonAddRecipeFlavor);
        linearLayoutCalculatorFragFlavors = v.findViewById(R.id.linearLayoutCalculatorFragFlavors);
        editTextCalculatorFinalAmount = v.findViewById(R.id.editTextCalculatorFinalAmount);
        editTextCalculatorDesiredNicotineStrength = v.findViewById(R.id.editTextCalculatorDesiredNicotineStrength);
        buttonCalculateRecipeResults = v.findViewById(R.id.buttonCalculateRecipeResults);
        tableLayoutRecipeResults = v.findViewById(R.id.tableLayoutRecipeResults);

        configureGraphicalComponents(v);
    }

    private void configureGraphicalComponents(View v) {
        addFlavorRow(v);
        totalFinalAmount = Double.parseDouble(editTextCalculatorFinalAmount.getText().toString());
        imgButtonAddRecipeFlavor.setOnClickListener(v1 -> addFlavorRow(v));
        buttonCalculateRecipeResults.setOnClickListener(v12 -> displayRecipeResultsInTable());

        configureCheckboxIndividualVgPg();
        configureEditTextBasePG();
        configureEditTextBaseVG();
        configureEditTextNicotineVG();
        configureEditTextNicotinePG();
        configureEditTextFinalAmount();
        //textviewCalculatorFragBaseTag.setOnClickListener(v13 -> clearRecipeResultsTable());
    }

    private void configureEditTextFinalAmount() {
        editTextCalculatorFinalAmount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                totalFinalAmount = Double.valueOf(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void configureCheckboxIndividualVgPg() {
        checkboxIndividualPgVg.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                textviewCalculatorFragBaseTag.setText("Desired final ratio");
            } else {
                textviewCalculatorFragBaseTag.setText("Base");
            }
        });
    }

    private void configureEditTextNicotinePG() {
        edittextCalculatorNicotinePG.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (edittextCalculatorNicotinePG.hasFocus()) {
                    Double currentInput = Double.valueOf(String.valueOf(s));
                    edittextCalculatorNicotineVG.setText(String.format("%.0f", 100 - currentInput));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void configureEditTextNicotineVG() {
        edittextCalculatorNicotineVG.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (edittextCalculatorNicotineVG.hasFocus()) {
                    Double currentInput = Double.valueOf(String.valueOf(s));
                    edittextCalculatorNicotinePG.setText(String.format("%.0f", 100 - currentInput));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void configureEditTextBaseVG() {
        edittextCalculatorBaseVG.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (edittextCalculatorBaseVG.hasFocus()) {
                    Double currentInput = Double.valueOf(String.valueOf(s));
                    edittextCalculatorBasePG.setText(String.format("%.0f", 100 - currentInput));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void configureEditTextBasePG() {
        edittextCalculatorBasePG.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (edittextCalculatorBasePG.hasFocus()) {
                    Double currentInput = Double.valueOf(String.valueOf(s));
                    edittextCalculatorBaseVG.setText(String.format("%.0f", 100 - currentInput));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void displayRecipeResultsInTable() {
        clearRecipeResultsTable();
        ArrayList<RecipeIngredientResult> recipeIngredientsResults = getRecipeIngredientsResults();
        for (RecipeIngredientResult recipeIngredientsResult : recipeIngredientsResults) {
            tableLayoutRecipeResults.addView(getRecipeResultTableRow(recipeIngredientsResult));
        }
    }

    private void clearRecipeResultsTable() {
        for (int i = tableLayoutRecipeResults.getChildCount(); i > 0; i--) {
            tableLayoutRecipeResults.removeView(tableLayoutRecipeResults.getChildAt(i));
        }
    }

    @SuppressLint("DefaultLocale")
    private TableRow getRecipeResultTableRow(RecipeIngredientResult recipeIngredientResult) {
        TableRow tableRow = new TableRow(getContext());

        TextView tvIngredientName = (TextView) getLayoutInflater()
                .inflate(R.layout.textview_recipe_results_component, null);
        tvIngredientName.setText(recipeIngredientResult.getIngredientName());
        TextView tvMilliliters = (TextView) getLayoutInflater()
                .inflate(R.layout.textview_recipe_results_component, null);
        tvMilliliters.setText(String.format("%.2f", recipeIngredientResult.getMilliliters()));
        TextView tvGrams = (TextView) getLayoutInflater()
                .inflate(R.layout.textview_recipe_results_component, null);
        tvGrams.setText(String.format("%.2f", recipeIngredientResult.getGrams()));
        TextView tvPercentageOfTotal = (TextView) getLayoutInflater()
                .inflate(R.layout.textview_recipe_results_component, null);
        tvPercentageOfTotal.setText(String.format("%.0f", recipeIngredientResult.getPercentageOfTotal()));
        TextView tvCost = (TextView) getLayoutInflater()
                .inflate(R.layout.textview_recipe_results_component, null);
        tvCost.setText(String.format("%.1f", recipeIngredientResult.getCost()));

        tableRow.addView(tvIngredientName);
        tableRow.addView(tvMilliliters);
        tableRow.addView(tvGrams);
        tableRow.addView(tvPercentageOfTotal);
        tableRow.addView(tvCost);

        return tableRow;
    }

    private ArrayList<RecipeIngredientResult> getRecipeIngredientsResults() {
        ArrayList<RecipeIngredientResult> recipeIngredientsResults = new ArrayList<>();
        if (checkboxIndividualPgVg.isChecked()) {
            recipeIngredientsResults.add(getVgRecipeResult());
            recipeIngredientsResults.add(getPgRecipeResult());
        } else {
            recipeIngredientsResults.add(getBaseRecipeResult());
        }

        recipeIngredientsResults.add(getNicotineRecipeResult());
        for (int i = 0; i < linearLayoutCalculatorFragFlavors.getChildCount(); i++) {
            View flavorRowView = linearLayoutCalculatorFragFlavors.getChildAt(i);
            String flavorName = ((AutoCompleteTextView) flavorRowView.findViewById(R.id.autoCompleteTextViewFlavorName))
                    .getText().toString();
            Double flavorPercentage = Double.parseDouble(
                    ((EditText) flavorRowView.findViewById(R.id.editTextRecipeFlavorPercentage))
                            .getText().toString()) / 100;
            recipeIngredientsResults.add(getFlavorRecipeResult(flavorName, flavorPercentage));
        }

        return recipeIngredientsResults;
    }

    private RecipeIngredientResult getPgRecipeResult() {
        Double milliliters = getPgRecipeResultInMilliliters();
        Double grams = milliliters * pgWeightPerMl;
        Double percentageOfTotal = (milliliters / totalFinalAmount) * 100;
        Double cost = (double) new Random().nextInt(10);

        return new RecipeIngredientResult("PG",
                milliliters,
                grams,
                percentageOfTotal,
                cost);
    }

    private RecipeIngredientResult getVgRecipeResult() {
        Double milliliters = getVgRecipeResultInMilliliters();
        Double grams = milliliters * vgWeightPerMl;
        Double percentageOfTotal = (milliliters / totalFinalAmount) * 100;
        Double cost = (double) new Random().nextInt(10);

        return new RecipeIngredientResult("VG",
                milliliters,
                grams,
                percentageOfTotal,
                cost);
    }

    private Double getPgRecipeResultInMilliliters() {
        Double desiredFinalPgPercentage = Double.parseDouble(edittextCalculatorBasePG.getText().toString()) / 100;
        Double nicshotPgPercentage = Double.parseDouble(edittextCalculatorNicotinePG.getText().toString()) / 100;
        return (totalFinalAmount * desiredFinalPgPercentage)
                - (flavorPgPercentage * getAllFlavorsRecipeResultsInMilliliters() +
                nicshotPgPercentage * getNicotineRecipeResultInMilliliters());
    }

    private Double getVgRecipeResultInMilliliters() {
        Double desiredFinalVgPercentage = Double.parseDouble(edittextCalculatorBaseVG.getText().toString()) / 100;
        Double nicshotVgPercentage = Double.parseDouble(edittextCalculatorNicotineVG.getText().toString()) / 100;
        return (totalFinalAmount * desiredFinalVgPercentage)
                - (flavorVgPercentage * getAllFlavorsRecipeResultsInMilliliters() +
                nicshotVgPercentage * getNicotineRecipeResultInMilliliters());
    }

    private RecipeIngredientResult getBaseRecipeResult() {

        Double milliliters = getBaseRecipeResultInMilliliters();
        Double baseVgPercentage = Double.parseDouble(edittextCalculatorBaseVG.getText().toString()) / 100;
        Double basePgPercentage = Double.parseDouble(edittextCalculatorBaseVG.getText().toString()) / 100;
        Double grams = milliliters * (baseVgPercentage * vgWeightPerMl + basePgPercentage * pgWeightPerMl);
        Double percentageOfTotal = (milliliters / totalFinalAmount) * 100;
        Double cost = (double) new Random().nextInt(10);

        return new RecipeIngredientResult("Base",
                milliliters,
                grams,
                percentageOfTotal,
                cost);
    }

    private RecipeIngredientResult getNicotineRecipeResult() {

        Double milliliters = getNicotineRecipeResultInMilliliters();
        Double nicshotStrengthInMg = Double.valueOf(edittextCalculatorNicotineMg.getText().toString());
        Double nicshotVgPercentage = Double.parseDouble(edittextCalculatorNicotineVG.getText().toString()) / 100;
        Double nicshotPgPercentage = Double.parseDouble(edittextCalculatorNicotinePG.getText().toString()) / 100;
        Double nicshotStrengthPercentage = nicshotStrengthInMg / 1000;
        Double grams = (milliliters * nicshotStrengthPercentage * pureNicotineWeightPerMl) +
                (milliliters * (1 - nicshotStrengthPercentage)) *
                        (nicshotVgPercentage * vgWeightPerMl + nicshotPgPercentage * pgWeightPerMl);

        Double percentageOfTotal = (milliliters / totalFinalAmount) * 100;
        Double cost = (double) new Random().nextInt(10);

        return new RecipeIngredientResult("Nicotine",
                milliliters,
                grams,
                percentageOfTotal,
                cost);

    }

    private RecipeIngredientResult getFlavorRecipeResult(String flavorName, Double flavorPercentage) {

        Double milliliters = getFlavorRecipeResultInMilliliters(flavorPercentage);
        Double grams = milliliters * flavorWeightPerMl;
        Double percentageOfTotal = (milliliters / totalFinalAmount) * 100;
        Double cost = (double) new Random().nextInt(10);
        RecipeIngredientResult flavorRecipeResult = new RecipeIngredientResult(flavorName, milliliters, grams, percentageOfTotal, cost);
        return flavorRecipeResult;
    }

    private Double getBaseRecipeResultInMilliliters() {
        return totalFinalAmount - (getNicotineRecipeResultInMilliliters() + getAllFlavorsRecipeResultsInMilliliters());
    }

    private Double getNicotineRecipeResultInMilliliters() {
        Double finalNicotineStrength = Double.valueOf(editTextCalculatorDesiredNicotineStrength.getText().toString());
        Double nicshotStrengthInMg = Double.valueOf(edittextCalculatorNicotineMg.getText().toString());
        return (totalFinalAmount * finalNicotineStrength) / nicshotStrengthInMg;
    }

    private Double getFlavorRecipeResultInMilliliters(Double flavorPercentage) {
        return totalFinalAmount * flavorPercentage;
    }

    private Double getAllFlavorsRecipeResultsInMilliliters() {
        Double milliliters = 0d;
        for (int i = 0; i < linearLayoutCalculatorFragFlavors.getChildCount(); i++) {
            View flavorRowView = linearLayoutCalculatorFragFlavors.getChildAt(i);
            Double flavorPercentage = Double.parseDouble(
                    ((EditText) flavorRowView.findViewById(R.id.editTextRecipeFlavorPercentage))
                            .getText().toString()) / 100;
            milliliters += getFlavorRecipeResultInMilliliters(flavorPercentage);
        }

        return milliliters;
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







