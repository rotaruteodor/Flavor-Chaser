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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

import teodor.flavor_chaser_android_app.R;
import teodor.flavor_chaser_android_app.databinding.FragmentEliquidCalculatorBinding;
import teodor.flavor_chaser_android_app.utils.NumberInputFilterMinMax;
import teodor.flavor_chaser_android_app.classes.RecipeIngredientResult;


public class EliquidCalculatorFragment extends Fragment {

    private FragmentEliquidCalculatorBinding binding;
    private AlertDialog saveRecipePopupDialog;
    private EditText editTextInputRecipeName;
    private TextView textviewFinishSavingRecipePopup;
    private static final Double VG_WEIGHT_PER_ML = 1.26;
    private static final Double PG_WEIGHT_PER_ML = 1.04;
    private static final Double PURE_NICOTINE_WEIGHT_PER_ML = 1.01;
    private static final Double FLAVOR_WEIGHT_PER_ML = 1d;
    private static final Double FLAVOR_PG_PERCENTAGE = 1d;
    private static final Double FLAVOR_VG_PERCENTAGE = 0d;
    private Double totalFinalAmount;
    private String[] flavorNames = new String[0]; //todo

    //todo replace cost with DB values
    //todo style?
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

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentEliquidCalculatorBinding.inflate(inflater, container, false);
        View v = binding.getRoot();
        initializeGraphicalComponents(v);
        return v;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }

    private void initializeGraphicalComponents(View v) {
        initializeSaveRecipePopup();
        configureGraphicalComponents(v);
    }

    private void initializeSaveRecipePopup() {
        View saveRecipePopupView = getActivity().getLayoutInflater()
                .inflate(R.layout.save_recipe_popup, null);
        saveRecipePopupDialog = new AlertDialog.Builder(getActivity(),
                AlertDialog.THEME_DEVICE_DEFAULT_DARK)
                .setView(saveRecipePopupView)
                .create();
        editTextInputRecipeName = saveRecipePopupView.findViewById(R.id.editTextInputRecipeName);
        textviewFinishSavingRecipePopup = saveRecipePopupView.findViewById(R.id.tvFinishSavingRecipePopup);
    }

    private void configureGraphicalComponents(View v) {
        totalFinalAmount = Double.parseDouble(binding.editTextCalculatorFinalAmount.getText().toString());
        saveRecipePopupDialog.getWindow()
                .setBackgroundDrawable(ContextCompat.getDrawable(getContext(), R.drawable.round_corners_30));
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
        binding.buttonSaveRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveRecipePopupDialog.show();
                saveRecipePopupDialog.getWindow().setLayout(WindowManager.LayoutParams.WRAP_CONTENT,
                        WindowManager.LayoutParams.WRAP_CONTENT);
                //todo
//                String recipeName = editTextInputRecipeName.getText().toString();
//                List<RecipeFlavor> recipeFlavors = new ArrayList<>();
//
//                Recipe recipe = new Recipe(
//                        ,
//                        "",
//                        );
//                RecipeApi recipeApi = RetrofitService.getRetrofit().create(RecipeApi.class);
//                recipeApi.add()
            }
        });
    }

    private void configureAddRecipeFlavorButton(View v) {
        binding.imgButtonAddRecipeFlavor.setOnClickListener(v1 -> {
            addFlavorRowInTable(v);
            displayRecipeResultsInTable();
        });
    }

    private void configureEditTextNicotineMG() {
        binding.edittextCalculatorNicotineMg.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try {
                    Double.valueOf(String.valueOf(s));
                    displayRecipeResultsInTable();
                } catch (Exception e) {
                    binding.textviewCalculateRecipeResultsError.setVisibility(View.VISIBLE);
                    setEditTextError(binding.edittextCalculatorNicotineMg, "Invalid input");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void configureEditTextNicotineStrength() {
        binding.editTextCalculatorDesiredNicotineStrength.addTextChangedListener(new TextWatcher() {
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
                    binding.textviewCalculateRecipeResultsError.setVisibility(View.VISIBLE);
                    setEditTextError(binding.editTextCalculatorDesiredNicotineStrength, "Field can't be empty");
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
        binding.editTextCalculatorFinalAmount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try {
                    totalFinalAmount = Double.valueOf(s.toString());
                    displayRecipeResultsInTable();
                } catch (Exception e) {
                    binding.textviewCalculateRecipeResultsError.setVisibility(View.VISIBLE);
                    setEditTextError(binding.editTextCalculatorFinalAmount, "Field can't be empty");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void configureCheckboxIndividualVgPg() {
        binding.checkboxIndividualPgVg.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                binding.textviewCalculatorFragBaseTag.setText("Desired final ratio");
                displayRecipeResultsInTable();
            } else {
                binding.textviewCalculatorFragBaseTag.setText("Base");
                displayRecipeResultsInTable();
            }
        });
    }

    private void configureEditTextNicotinePG() {
        binding.edittextCalculatorNicotinePG.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (binding.edittextCalculatorNicotinePG.hasFocus()) {
                    try {
                        Double currentInput = Double.valueOf(String.valueOf(s));
                        binding.edittextCalculatorNicotineVG.setText(String.format("%.0f", 100 - currentInput));
                        displayRecipeResultsInTable();
                    } catch (Exception e) {
                        binding.textviewCalculateRecipeResultsError.setVisibility(View.VISIBLE);
                        setEditTextError(binding.edittextCalculatorNicotinePG, "Invalid input");
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        binding.edittextCalculatorNicotinePG.setFilters(new InputFilter[]{new NumberInputFilterMinMax(0, 100)});
    }

    private void configureEditTextNicotineVG() {
        binding.edittextCalculatorNicotineVG.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (binding.edittextCalculatorNicotineVG.hasFocus()) {
                    try {
                        Double currentInput = Double.valueOf(String.valueOf(s));
                        binding.edittextCalculatorNicotinePG.setText(String.format("%.0f", 100 - currentInput));
                        displayRecipeResultsInTable();
                    } catch (Exception e) {
                        binding.textviewCalculateRecipeResultsError.setVisibility(View.VISIBLE);
                        setEditTextError(binding.edittextCalculatorNicotineVG, "Invalid input");
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        binding.edittextCalculatorNicotineVG.setFilters(new InputFilter[]{new NumberInputFilterMinMax(0, 100)});
    }

    private void configureEditTextBaseVG() {
        binding.edittextCalculatorBaseVG.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (binding.edittextCalculatorBaseVG.hasFocus()) {
                    try {
                        Double currentInput = Double.valueOf(String.valueOf(s));
                        binding.edittextCalculatorBasePG.setText(String.format("%.0f", 100 - currentInput));
                        displayRecipeResultsInTable();
                    } catch (Exception e) {
                        binding.textviewCalculateRecipeResultsError.setVisibility(View.VISIBLE);
                        setEditTextError(binding.edittextCalculatorBaseVG, "Invalid input");
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        binding.edittextCalculatorBaseVG.setFilters(new InputFilter[]{new NumberInputFilterMinMax(0, 100)});
    }

    private void configureEditTextBasePG() {
        binding.edittextCalculatorBasePG.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (binding.edittextCalculatorBasePG.hasFocus()) {
                    try {
                        Double currentInput = Double.valueOf(String.valueOf(s));
                        binding.edittextCalculatorBaseVG.setText(String.format("%.0f", 100 - currentInput));
                        displayRecipeResultsInTable();
                    } catch (Exception e) {
                        binding.textviewCalculateRecipeResultsError.setVisibility(View.VISIBLE);
                        setEditTextError(binding.edittextCalculatorBasePG, "Invalid input");
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        binding.edittextCalculatorBasePG.setFilters(new InputFilter[]{new NumberInputFilterMinMax(0, 100)});
    }

    private void displayRecipeResultsInTable() {
        if (areInputsValid()) {
            binding.textviewCalculateRecipeResultsError.setVisibility(View.GONE);
            clearRecipeResultsTable();
            ArrayList<RecipeIngredientResult> recipeIngredientsResults = getRecipeIngredientsResults();
            for (RecipeIngredientResult recipeIngredientsResult : recipeIngredientsResults) {
                binding.tableLayoutRecipeResults.addView(getRecipeResultTableRow(recipeIngredientsResult));
                if (recipeIngredientsResult.getMilliliters() < 0) {
                    binding.textviewCalculateRecipeResultsError.setVisibility(View.VISIBLE);
                }
            }
        } else {
            binding.textviewCalculateRecipeResultsError.setVisibility(View.VISIBLE);
        }
    }

    private boolean areInputsValid() {
        try {
            Double.parseDouble(binding.edittextCalculatorBaseVG.getText().toString());
            Double.parseDouble(binding.edittextCalculatorBasePG.getText().toString());
            Double.parseDouble(binding.edittextCalculatorNicotineVG.getText().toString());
            Double.parseDouble(binding.edittextCalculatorNicotinePG.getText().toString());
            Double.parseDouble(binding.edittextCalculatorNicotineMg.getText().toString());
            Double.parseDouble(binding.editTextCalculatorFinalAmount.getText().toString());
            Double.parseDouble(binding.editTextCalculatorDesiredNicotineStrength.getText().toString());
            for (int i = 0; i < binding.linearLayoutCalculatorFragFlavors.getChildCount(); i++) {
                View flavorRowView = binding.linearLayoutCalculatorFragFlavors.getChildAt(i);
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
        for (int i = binding.tableLayoutRecipeResults.getChildCount(); i > 0; i--) {
            binding.tableLayoutRecipeResults.removeView(binding.tableLayoutRecipeResults.getChildAt(i));
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
        if (binding.checkboxIndividualPgVg.isChecked()) {
            recipeIngredientsResults.add(getVgRecipeResult());
            recipeIngredientsResults.add(getPgRecipeResult());
        } else {
            recipeIngredientsResults.add(getBaseRecipeResult());
        }

        recipeIngredientsResults.add(getNicotineRecipeResult());
        for (int i = 0; i < binding.linearLayoutCalculatorFragFlavors.getChildCount(); i++) {
            View flavorRowView = binding.linearLayoutCalculatorFragFlavors.getChildAt(i);
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
        Double grams = milliliters * PG_WEIGHT_PER_ML;
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
        Double grams = milliliters * VG_WEIGHT_PER_ML;
        Double percentageOfTotal = (milliliters / totalFinalAmount) * 100;
        Double cost = (double) new Random().nextInt(10);

        return new RecipeIngredientResult("VG",
                milliliters,
                grams,
                percentageOfTotal,
                cost);
    }

    private Double getPgRecipeResultInMilliliters() {
        Double desiredFinalPgPercentage = Double.parseDouble(binding.edittextCalculatorBasePG.getText().toString()) / 100;
        Double nicshotPgPercentage = Double.parseDouble(binding.edittextCalculatorNicotinePG.getText().toString()) / 100;
        return (totalFinalAmount * desiredFinalPgPercentage) -
                (FLAVOR_PG_PERCENTAGE * getAllFlavorsRecipeResultsInMilliliters() +
                nicshotPgPercentage * getNicotineRecipeResultInMilliliters());
    }

    private Double getVgRecipeResultInMilliliters() {
        Double desiredFinalVgPercentage = Double.parseDouble(binding.edittextCalculatorBaseVG.getText().toString()) / 100;
        Double nicshotVgPercentage = Double.parseDouble(binding.edittextCalculatorNicotineVG.getText().toString()) / 100;
        return (totalFinalAmount * desiredFinalVgPercentage) -
                (FLAVOR_VG_PERCENTAGE * getAllFlavorsRecipeResultsInMilliliters() +
                nicshotVgPercentage * getNicotineRecipeResultInMilliliters());
    }

    private RecipeIngredientResult getBaseRecipeResult() {

        Double milliliters = getBaseRecipeResultInMilliliters();
        Double baseVgPercentage = Double.parseDouble(binding.edittextCalculatorBaseVG.getText().toString()) / 100;
        Double basePgPercentage = Double.parseDouble(binding.edittextCalculatorBaseVG.getText().toString()) / 100;
        Double grams = milliliters * (baseVgPercentage * VG_WEIGHT_PER_ML + basePgPercentage * PG_WEIGHT_PER_ML);
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
        Double nicshotStrengthInMg = Double.valueOf(binding.edittextCalculatorNicotineMg.getText().toString());
        Double nicshotVgPercentage = Double.parseDouble(binding.edittextCalculatorNicotineVG.getText().toString()) / 100;
        Double nicshotPgPercentage = Double.parseDouble(binding.edittextCalculatorNicotinePG.getText().toString()) / 100;
        Double nicshotStrengthPercentage = nicshotStrengthInMg / 1000;
        Double grams = (milliliters * nicshotStrengthPercentage * PURE_NICOTINE_WEIGHT_PER_ML) +
                (milliliters * (1 - nicshotStrengthPercentage)) *
                        (nicshotVgPercentage * VG_WEIGHT_PER_ML + nicshotPgPercentage * PG_WEIGHT_PER_ML);

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
        Double grams = milliliters * FLAVOR_WEIGHT_PER_ML;
        Double percentageOfTotal = (milliliters / totalFinalAmount) * 100;
        Double cost = (double) new Random().nextInt(10);
        RecipeIngredientResult flavorRecipeResult = new RecipeIngredientResult(flavorName, milliliters, grams, percentageOfTotal, cost);
        return flavorRecipeResult;
    }

    private Double getBaseRecipeResultInMilliliters() {
        return totalFinalAmount - (getNicotineRecipeResultInMilliliters() + getAllFlavorsRecipeResultsInMilliliters());
    }

    private Double getNicotineRecipeResultInMilliliters() {
        Double finalNicotineStrength = Double.valueOf(binding.editTextCalculatorDesiredNicotineStrength.getText().toString());
        Double nicshotStrengthInMg = Double.valueOf(binding.edittextCalculatorNicotineMg.getText().toString());
        return (totalFinalAmount * finalNicotineStrength) / nicshotStrengthInMg;
    }

    private Double getFlavorRecipeResultInMilliliters(Double flavorPercentage) {
        return totalFinalAmount * flavorPercentage;
    }

    private Double getAllFlavorsRecipeResultsInMilliliters() {
        Double milliliters = 0d;
        for (int i = 0; i < binding.linearLayoutCalculatorFragFlavors.getChildCount(); i++) {
            View flavorRowView = binding.linearLayoutCalculatorFragFlavors.getChildAt(i);
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
                binding.linearLayoutCalculatorFragFlavors,
                false);

        EditText editTextRecipeFlavorPercentage = flavorRowInInputsView.findViewById(R.id.editTextRecipeFlavorPercentage);
        ImageView imageViewDeleteRecipeFlavor = flavorRowInInputsView.findViewById(R.id.imageViewDeleteRecipeFlavor);
        AutoCompleteTextView autoCompleteTextViewFlavorName = flavorRowInInputsView.findViewById(R.id.autoCompleteTextViewFlavorName);

        configureEditTextRecipeFlavorPercentage(editTextRecipeFlavorPercentage);
        configureImageViewDeleteRecipeFlavor(flavorRowInInputsView, imageViewDeleteRecipeFlavor);
        configureAutoCompleteTextViewFlavorName(flavorRowInInputsView, autoCompleteTextViewFlavorName);

        binding.linearLayoutCalculatorFragFlavors.addView(flavorRowInInputsView,
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
                int currentFlavorIndexInInputs = binding.linearLayoutCalculatorFragFlavors.indexOfChild(flavorRowInInputsView);

                TableRow tableRowOfCurrentFlavor = (TableRow) binding.tableLayoutRecipeResults.getChildAt(
                        binding.tableLayoutRecipeResults.getChildCount() -
                                binding.linearLayoutCalculatorFragFlavors.getChildCount() +
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
            binding.linearLayoutCalculatorFragFlavors.removeView(flavorRowInInputsView);
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
                    binding.textviewCalculateRecipeResultsError.setVisibility(View.VISIBLE);
                    setEditTextError(editTextRecipeFlavorPercentage, "Invalid input");
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
}







