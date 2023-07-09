package teodor.flavor_chaser_android_app.fragments;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import teodor.flavor_chaser_android_app.R;
import teodor.flavor_chaser_android_app.retrofit.entities_apis.FlavorApi;
import teodor.flavor_chaser_android_app.retrofit.RetrofitService;
import teodor.flavor_chaser_android_app.utils.NumberInputFilterMinMax;
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
    private Button buttonSaveRecipe;
    private TextView textviewCalculateRecipeResultsError;
    private TableLayout tableLayoutRecipeResults;
    private EditText editTextInputRecipeName;
    private TextView textviewFinishSavingRecipePopup;
    private AlertDialog saveRecipePopupDialog;

    private final Double vgWeightPerMl = 1.26;
    private final Double pgWeightPerMl = 1.04;
    private final Double pureNicotineWeightPerMl = 1.01;
    private final Double flavorWeightPerMl = 1d;
    private final Double flavorPgPercentage = 1d;
    private final Double flavorVgPercentage = 0d;
    private Double totalFinalAmount;
    private String[] flavorNames = new String[0];

    //todo resolve async call for flavorNames
    //todo replace cost with DB values
    //todo flavors suggestions from DB
    //todo style
    //todo save recipe
    //todo save preferences of stats
    //todo various calculators (for shortfill, etc)

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
        FlavorApi flavorApi = RetrofitService.getRetrofit().create(FlavorApi.class);
        flavorApi.getAllFlavorNames().enqueue(new Callback<List<String>>() {
            @Override
            public void onResponse(Call<List<String>> call, Response<List<String>> response) {
                if (response.body() != null) {
                    flavorNames = response.body().toArray(new String[0]);
                    Toast.makeText(getContext(), Arrays.toString(flavorNames), Toast.LENGTH_LONG).show();

                } else {
                    onFailure(call, new Throwable());
                }
            }

            @Override
            public void onFailure(Call<List<String>> call, Throwable t) {
                Toast.makeText(getContext(),
                        "ERROR! Could not load flavors from database!",
                        Toast.LENGTH_LONG).show();
            }
        });

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
        buttonSaveRecipe = v.findViewById(R.id.buttonSaveRecipe);
        textviewCalculateRecipeResultsError = v.findViewById(R.id.textviewCalculateRecipeResultsError);
        tableLayoutRecipeResults = v.findViewById(R.id.tableLayoutRecipeResults);

        View saveRecipePopupView = getActivity().getLayoutInflater()
                .inflate(R.layout.save_recipe_popup, null);
        saveRecipePopupDialog = new AlertDialog.Builder(getActivity(),
                AlertDialog.THEME_DEVICE_DEFAULT_DARK)
                .setView(saveRecipePopupView)
                .create();
        editTextInputRecipeName = saveRecipePopupView.findViewById(R.id.editTextInputRecipeName);
        textviewFinishSavingRecipePopup = saveRecipePopupView.findViewById(R.id.tvFinishSavingRecipePopup);

        configureGraphicalComponents(v);
    }

    private void configureGraphicalComponents(View v) {
        totalFinalAmount = Double.parseDouble(editTextCalculatorFinalAmount.getText().toString());
        saveRecipePopupDialog.getWindow()
                .setBackgroundDrawable(ContextCompat.getDrawable(getContext(),
                        R.drawable.round_corners_30));

        configureAddRecipeFlavorButton(v);
        configureCheckboxIndividualVgPg();
        configureEditTextBasePG();
        configureEditTextBaseVG();
        configureEditTextNicotineVG();
        configureEditTextNicotinePG();
        configureEditTextNicotineMG();
        configureEditTextFinalAmount();
        configureEditTextNicotineStrength();
        configureButtonSaveRecipe();
        configureTvFinishSavingRecipePopup();

        addFlavorRowInTable(v);
        displayRecipeResultsInTable();
    }

    private void configureTvFinishSavingRecipePopup() {
        textviewFinishSavingRecipePopup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editTextInputRecipeName.getText().length() > 0) {
                    //todo save recipe to DB
                    saveRecipePopupDialog.dismiss();
                    editTextInputRecipeName.setText("");
                } else {
                    setEditTextError(editTextInputRecipeName, "Field can't be empty");
                }

            }
        });
    }


    private void configureButtonSaveRecipe() {
        buttonSaveRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveRecipePopupDialog.show();
                saveRecipePopupDialog.getWindow().setLayout(WindowManager.LayoutParams.WRAP_CONTENT,
                        WindowManager.LayoutParams.WRAP_CONTENT);
                //todo
//                String recipeName = editTextInputRecipeName.getText().toString();
//                List<RecipeFlavorDto> recipeFlavorDtos = new ArrayList<>();
//
//                RecipeDto recipe = new RecipeDto(
//                        ,
//                        "",
//                        );
//                RecipeApi recipeApi = RetrofitService.getRetrofit().create(RecipeApi.class);
//                recipeApi.add()
            }
        });
    }

    private void configureAddRecipeFlavorButton(View v) {
        imgButtonAddRecipeFlavor.setOnClickListener(v1 -> {
            addFlavorRowInTable(v);
            displayRecipeResultsInTable();
        });
    }

    private void configureEditTextNicotineMG() {
        edittextCalculatorNicotineMg.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try {
                    Double.valueOf(String.valueOf(s));
                    displayRecipeResultsInTable();
                } catch (Exception e) {
                    textviewCalculateRecipeResultsError.setVisibility(View.VISIBLE);
                    setEditTextError(edittextCalculatorNicotineMg, "Invalid input");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void configureEditTextNicotineStrength() {
        editTextCalculatorDesiredNicotineStrength.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try {
                    Double.valueOf(s.toString());
                    //todo maybe treat case when desired nic strength is greater than nicshot strength?
                    displayRecipeResultsInTable();
                } catch (Exception e) {
                    textviewCalculateRecipeResultsError.setVisibility(View.VISIBLE);
                    setEditTextError(editTextCalculatorDesiredNicotineStrength, "Field can't be empty");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void setEditTextError(EditText editText, String errorMessage) {
        editText.setError(errorMessage);
        editText.requestFocus();
    }

    private void configureEditTextFinalAmount() {
        editTextCalculatorFinalAmount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try {
                    totalFinalAmount = Double.valueOf(s.toString());
                    displayRecipeResultsInTable();
                } catch (Exception e) {
                    textviewCalculateRecipeResultsError.setVisibility(View.VISIBLE);
                    setEditTextError(editTextCalculatorFinalAmount, "Field can't be empty");
                }
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
                displayRecipeResultsInTable();
            } else {
                textviewCalculatorFragBaseTag.setText("Base");
                displayRecipeResultsInTable();
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
                    try {
                        Double currentInput = Double.valueOf(String.valueOf(s));
                        edittextCalculatorNicotineVG.setText(String.format("%.0f", 100 - currentInput));
                        displayRecipeResultsInTable();
                    } catch (Exception e) {
                        textviewCalculateRecipeResultsError.setVisibility(View.VISIBLE);
                        setEditTextError(edittextCalculatorNicotinePG, "Invalid input");
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        edittextCalculatorNicotinePG.setFilters(new InputFilter[]{new NumberInputFilterMinMax(0, 100)});
    }

    private void configureEditTextNicotineVG() {
        edittextCalculatorNicotineVG.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (edittextCalculatorNicotineVG.hasFocus()) {
                    try {
                        Double currentInput = Double.valueOf(String.valueOf(s));
                        edittextCalculatorNicotinePG.setText(String.format("%.0f", 100 - currentInput));
                        displayRecipeResultsInTable();
                    } catch (Exception e) {
                        textviewCalculateRecipeResultsError.setVisibility(View.VISIBLE);
                        setEditTextError(edittextCalculatorNicotineVG, "Invalid input");
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        edittextCalculatorNicotineVG.setFilters(new InputFilter[]{new NumberInputFilterMinMax(0, 100)});
    }

    private void configureEditTextBaseVG() {
        edittextCalculatorBaseVG.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (edittextCalculatorBaseVG.hasFocus()) {
                    try {
                        Double currentInput = Double.valueOf(String.valueOf(s));
                        edittextCalculatorBasePG.setText(String.format("%.0f", 100 - currentInput));
                        displayRecipeResultsInTable();
                    } catch (Exception e) {
                        textviewCalculateRecipeResultsError.setVisibility(View.VISIBLE);
                        setEditTextError(edittextCalculatorBaseVG, "Invalid input");
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        edittextCalculatorBaseVG.setFilters(new InputFilter[]{new NumberInputFilterMinMax(0, 100)});
    }

    private void configureEditTextBasePG() {
        edittextCalculatorBasePG.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (edittextCalculatorBasePG.hasFocus()) {
                    try {
                        Double currentInput = Double.valueOf(String.valueOf(s));
                        edittextCalculatorBaseVG.setText(String.format("%.0f", 100 - currentInput));
                        displayRecipeResultsInTable();
                    } catch (Exception e) {
                        textviewCalculateRecipeResultsError.setVisibility(View.VISIBLE);
                        setEditTextError(edittextCalculatorBasePG, "Invalid input");
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        edittextCalculatorBasePG.setFilters(new InputFilter[]{new NumberInputFilterMinMax(0, 100)});
    }

    private void displayRecipeResultsInTable() {
        if (areInputsValid()) {
            textviewCalculateRecipeResultsError.setVisibility(View.GONE);
            clearRecipeResultsTable();
            ArrayList<RecipeIngredientResult> recipeIngredientsResults = getRecipeIngredientsResults();
            for (RecipeIngredientResult recipeIngredientsResult : recipeIngredientsResults) {
                tableLayoutRecipeResults.addView(getRecipeResultTableRow(recipeIngredientsResult));
                if (recipeIngredientsResult.getMilliliters() < 0) {
                    textviewCalculateRecipeResultsError.setVisibility(View.VISIBLE);
                }
            }
        } else {
            textviewCalculateRecipeResultsError.setVisibility(View.VISIBLE);
        }
    }

    private boolean areInputsValid() {
        try {
            Double.parseDouble(edittextCalculatorBaseVG.getText().toString());
            Double.parseDouble(edittextCalculatorBasePG.getText().toString());
            Double.parseDouble(edittextCalculatorNicotineVG.getText().toString());
            Double.parseDouble(edittextCalculatorNicotinePG.getText().toString());
            Double.parseDouble(edittextCalculatorNicotineMg.getText().toString());
            Double.parseDouble(editTextCalculatorFinalAmount.getText().toString());
            Double.parseDouble(editTextCalculatorDesiredNicotineStrength.getText().toString());
            for (int i = 0; i < linearLayoutCalculatorFragFlavors.getChildCount(); i++) {
                View flavorRowView = linearLayoutCalculatorFragFlavors.getChildAt(i);
                Double.parseDouble(
                        ((EditText) flavorRowView.findViewById(R.id.editTextRecipeFlavorPercentage))
                                .getText().toString());
            }
            return true;
        } catch (Exception e) {
            return false;
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

            if (flavorName.matches("")) {
                flavorName = new StringBuilder().append("Flavor ")
                        .append(i + 1)
                        .append(" (")
                        .append(flavorPercentage * 100)
                        .append("%)")
                        .toString();
            }
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

    private void addFlavorRowInTable(View v) {
        LayoutInflater vi = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View flavorRowInInputsView = vi.inflate(R.layout.flavor_recipe_component_layout,
                linearLayoutCalculatorFragFlavors,
                false);

        EditText editTextRecipeFlavorPercentage = flavorRowInInputsView.findViewById(R.id.editTextRecipeFlavorPercentage);
        ImageView imageViewDeleteRecipeFlavor = flavorRowInInputsView.findViewById(R.id.imageViewDeleteRecipeFlavor);
        AutoCompleteTextView autoCompleteTextViewFlavorName = flavorRowInInputsView.findViewById(R.id.autoCompleteTextViewFlavorName);

        configureEditTextRecipeFlavorPercentage(editTextRecipeFlavorPercentage);
        configureImageViewDeleteRecipeFlavor(flavorRowInInputsView, imageViewDeleteRecipeFlavor);
        configureAutoCompleteTextViewFlavorName(flavorRowInInputsView, autoCompleteTextViewFlavorName);

        linearLayoutCalculatorFragFlavors.addView(flavorRowInInputsView,
                0,
                new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT));

    }

    private void configureAutoCompleteTextViewFlavorName(View flavorRowInInputsView, AutoCompleteTextView autoCompleteTextViewFlavorName) {
        ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter<>
                (getContext(), android.R.layout.select_dialog_item, flavorNames);

        autoCompleteTextViewFlavorName.setThreshold(2);
        autoCompleteTextViewFlavorName.setAdapter(stringArrayAdapter);

        autoCompleteTextViewFlavorName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                int currentFlavorIndexInInputs = linearLayoutCalculatorFragFlavors.indexOfChild(flavorRowInInputsView);

                TableRow tableRowOfCurrentFlavor = (TableRow) tableLayoutRecipeResults.getChildAt(
                        tableLayoutRecipeResults.getChildCount() -
                                linearLayoutCalculatorFragFlavors.getChildCount() +
                                currentFlavorIndexInInputs);
                ((TextView) tableRowOfCurrentFlavor.getChildAt(0)).setText(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void configureImageViewDeleteRecipeFlavor(View flavorRowInInputsView, ImageView imageViewDeleteRecipeFlavor) {
        imageViewDeleteRecipeFlavor.setOnClickListener(v1 -> {
            linearLayoutCalculatorFragFlavors.removeView(flavorRowInInputsView);
            displayRecipeResultsInTable();
        });
    }

    private void configureEditTextRecipeFlavorPercentage(EditText editTextRecipeFlavorPercentage) {
        editTextRecipeFlavorPercentage.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try {
                    Double.valueOf(String.valueOf(s));
                    displayRecipeResultsInTable();
                } catch (Exception e) {
                    textviewCalculateRecipeResultsError.setVisibility(View.VISIBLE);
                    setEditTextError(editTextRecipeFlavorPercentage, "Invalid input");
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
}







