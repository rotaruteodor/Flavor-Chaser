package teodor.flavor_chaser_android_app.fragments.eliquid;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.stream.Collectors;

import teodor.flavor_chaser_android_app.R;
import teodor.flavor_chaser_android_app.fragments.eliquid.utils.BaseInputsBundle;
import teodor.flavor_chaser_android_app.fragments.eliquid.utils.FlavorInputsBundle;
import teodor.flavor_chaser_android_app.fragments.eliquid.utils.NicotineInputsBundle;
import teodor.flavor_chaser_android_app.fragments.eliquid.utils.RecipeIngredientResult;
import teodor.flavor_chaser_android_app.databinding.FragmentEliquidCalculatorBinding;
import teodor.flavor_chaser_android_app.fragments.eliquid.utils.RecipeInputsBundle;
import teodor.flavor_chaser_android_app.models.LiquidCalculatorPreferences;
import teodor.flavor_chaser_android_app.models.User;
import teodor.flavor_chaser_android_app.models.enums.MainIngredientType;
import teodor.flavor_chaser_android_app.fragments.eliquid.utils.EliquidCalculatorHelper;
import teodor.flavor_chaser_android_app.models.Flavor;
import teodor.flavor_chaser_android_app.models.IngredientInStash;
import teodor.flavor_chaser_android_app.utils.GeneralInfo;
import teodor.flavor_chaser_android_app.utils.NumberInputFilterMinMax;


public class EliquidCalculatorFragment extends Fragment {

    private FragmentEliquidCalculatorBinding binding;
    private AlertDialog saveRecipePopupDialog;
    private EditText editTextInputRecipeName;
    private TextView textviewFinishSavingRecipePopup;

    private ArrayList<Flavor> allFlavorsInDatabase;
    private ArrayList<IngredientInStash> ingredientsInStash;
    private User user;
    private RecipeInputsBundle recipeInputsBundle;

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
        allFlavorsInDatabase = getArguments().getParcelableArrayList(GeneralInfo.PASS_FLAVORS_MAINACTIVITY_TO_ELIQCALCFRAGMENT);
        ingredientsInStash = getArguments().getParcelableArrayList(GeneralInfo.PASS_INGREDIENTS_IN_STASH_MAINACTIVITY_TO_ELIQCALCFRAGMENT);
        user = getArguments().getParcelable(GeneralInfo.PASS_USER_FROM_MAIN_ACTIVITY_TO_ELIQCALCFRAGMENT);
        initializeRecipeInputsBundle();
    }

    private void initializeRecipeInputsBundle() {
        LiquidCalculatorPreferences preferences = user.getLiquidCalculatorPreferences();
        BaseInputsBundle baseInputsBundle = new BaseInputsBundle(preferences.getBaseVgPercentage(),
                preferences.getBasePgPercentage(),
                preferences.getBaseDescription(),
                preferences.isWithIndividualPgVg(),
                preferences.getVgDescription(),
                preferences.getPgDescription(),
                BigDecimal.ZERO);
        NicotineInputsBundle nicotineInputsBundle = new NicotineInputsBundle(preferences.getNicshotVgPercentage(),
                preferences.getNicshotPgPercentage(),
                preferences.getNicshotStrengthInMg(),
                preferences.getNicshotDescription(),
                preferences.getFinalNicotineStrength(),
                BigDecimal.ZERO);

        recipeInputsBundle = new RecipeInputsBundle(baseInputsBundle, nicotineInputsBundle, new ArrayList<>(), preferences.getTotalFinalAmount());
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
        saveRecipePopupDialog.getWindow()
                .setBackgroundDrawable(ContextCompat.getDrawable(getContext(), R.drawable.round_corners_30));
        configureAddRecipeFlavorButton();
        configureCheckboxIndividualVgPg();
        configureEditTextBaseVG();
        configureEditTextBasePG();
        configureDescriptionSpinner(binding.spinnerCalculatorFragBaseDescription, MainIngredientType.BASE);
        configureDescriptionSpinner(binding.spinnerCalculatorFragVgDescription, MainIngredientType.VG);
        configureDescriptionSpinner(binding.spinnerCalculatorFragPgDescription, MainIngredientType.PG);
        configureDescriptionSpinner(binding.spinnerCalculatorFragNicotineDescription, MainIngredientType.NICOTINE);
        configureEditTextNicotineVG();
        configureEditTextNicotinePG();
        configureEditTextNicotineMG();
        configureEditTextNicotineStrength();
        configureEditTextFinalAmount();
        configureButtonSaveRecipe();
        configureTvFinishSavingRecipePopup();
        addFlavorRowInInputsTable();
        displayRecipeResultsInTable();
    }

    private void configureDescriptionSpinner(Spinner spinner, MainIngredientType ingredientType) {
        spinner.post(() -> spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selection = parent.getItemAtPosition(position).toString();
                switch (ingredientType) {
                    case BASE:
                        recipeInputsBundle.getBase().setBaseDescription(selection);
                        break;
                    case VG:
                        recipeInputsBundle.getBase().setVgDescription(selection);
                        break;
                    case PG:
                        recipeInputsBundle.getBase().setPgDescription(selection);
                        break;
                    case NICOTINE:
                        recipeInputsBundle.getNicotine().setDescription(selection);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        }));

        ArrayList<String> descriptions = (ArrayList<String>) ingredientsInStash.stream()
                .filter(i -> i.getType() == ingredientType)
                .map(IngredientInStash::getDescription)
                .collect(Collectors.toList()); // TODO Improve?
        descriptions.add(0, "No");
        String[] descriptionsArray = descriptions.toArray(new String[descriptions.size()]);
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<>(getContext(),
                R.layout.description_spinner_item,
                descriptionsArray);
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerArrayAdapter);
    }

    private void configureTvFinishSavingRecipePopup() {
        textviewFinishSavingRecipePopup.setOnClickListener(v -> {
            if (editTextInputRecipeName.getText().length() > 0) {
                saveRecipePopupDialog.dismiss();
                editTextInputRecipeName.setText("");
            } else {
                setEditTextError(editTextInputRecipeName, "Field can't be empty");
            }
        });
    }

    private void configureButtonSaveRecipe() {
        binding.buttonSaveRecipe.setOnClickListener(v -> {
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
        });
    }

    private void configureAddRecipeFlavorButton() {
        binding.imgButtonAddRecipeFlavor.setOnClickListener(v1 -> {
            addFlavorRowInInputsTable();
            displayRecipeResultsInTable();
        });
    }

    private void configureEditTextNicotineMG() {
        binding.edittextCalculatorNicotineMg.setText(new DecimalFormat("##.#")
                .format(recipeInputsBundle.getNicotine().getStrengthInMg()));

        binding.edittextCalculatorNicotineMg.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try {
                    recipeInputsBundle.getNicotine().setStrengthInMg(Double.valueOf(String.valueOf(s)));
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
        binding.editTextCalculatorDesiredNicotineStrength.setText(new DecimalFormat("##.##")
                .format(recipeInputsBundle.getNicotine().getFinalStrengthInMg()));

        binding.editTextCalculatorDesiredNicotineStrength.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try {
                    recipeInputsBundle.getNicotine().setFinalStrengthInMg(Double.valueOf(s.toString()));
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

    private void configureEditTextFinalAmount() {
        binding.editTextCalculatorFinalAmount.setText(new DecimalFormat("##.#")
                .format(recipeInputsBundle.getFinalAmount()));
        binding.editTextCalculatorFinalAmount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try {
                    recipeInputsBundle.setFinalAmount(Double.valueOf(s.toString()));
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
            handleEventsOfIndividualVgPgCheckbox(isChecked);
        });

        if (recipeInputsBundle.getBase().getWithIndividualPgVg()) {
            binding.checkboxIndividualPgVg.setChecked(true);
            handleEventsOfIndividualVgPgCheckbox(true);
        }

    }

    private void handleEventsOfIndividualVgPgCheckbox(boolean isChecked) {
        if (isChecked) {
            handleCheckEventOfIndividualVgPgCheckbox();
        } else {
            handleUncheckEventOfIndividualVgPgCheckbox();
        }
    }

    private void handleUncheckEventOfIndividualVgPgCheckbox() {
        binding.textviewCalculatorFragBaseTag.setText("Base");
        binding.spinnerCalculatorFragBaseDescription.setVisibility(View.VISIBLE);
        binding.textviewCalculatorFragBaseDescriptionTag.setVisibility(View.VISIBLE);
        binding.spinnerCalculatorFragVgDescription.setVisibility(View.GONE);
        binding.textviewCalculatorFragVgDescriptionTag.setVisibility(View.GONE);
        binding.spinnerCalculatorFragPgDescription.setVisibility(View.GONE);
        binding.textviewCalculatorFragPgDescriptionTag.setVisibility(View.GONE);
        recipeInputsBundle.getBase().setWithIndividualPgVg(false);
        displayRecipeResultsInTable();
    }

    private void handleCheckEventOfIndividualVgPgCheckbox() {
        binding.textviewCalculatorFragBaseTag.setText("Desired final ratio");
        binding.spinnerCalculatorFragBaseDescription.setVisibility(View.GONE);
        binding.textviewCalculatorFragBaseDescriptionTag.setVisibility(View.GONE);
        binding.spinnerCalculatorFragVgDescription.setVisibility(View.VISIBLE);
        binding.textviewCalculatorFragVgDescriptionTag.setVisibility(View.VISIBLE);
        binding.spinnerCalculatorFragPgDescription.setVisibility(View.VISIBLE);
        binding.textviewCalculatorFragPgDescriptionTag.setVisibility(View.VISIBLE);
        recipeInputsBundle.getBase().setWithIndividualPgVg(true);
        displayRecipeResultsInTable();
    }

    private void configureEditTextBaseVG() {
        binding.edittextCalculatorBaseVG.setText(String.format("%.0f", recipeInputsBundle.getBase().getVgPercentage() * 100));
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
                        recipeInputsBundle.getBase().setVgPercentage(currentInput / 100);
                        recipeInputsBundle.getBase().setPgPercentage(100 - (currentInput / 100));
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
        binding.edittextCalculatorBasePG.setText(String.format("%.0f", recipeInputsBundle.getBase().getPgPercentage() * 100));
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
                        recipeInputsBundle.getBase().setPgPercentage(currentInput / 100);
                        recipeInputsBundle.getBase().setVgPercentage(100 - (currentInput / 100));
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

    private void configureEditTextNicotineVG() {
        binding.edittextCalculatorNicotineVG.setText(String.format("%.0f", recipeInputsBundle.getNicotine().getVgPercentage() * 100));
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
                        recipeInputsBundle.getNicotine().setVgPercentage(currentInput / 100);
                        recipeInputsBundle.getNicotine().setPgPercentage(100 - (currentInput / 100));
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

    private void configureEditTextNicotinePG() {
        binding.edittextCalculatorNicotinePG.setText(String.format("%.0f", recipeInputsBundle.getNicotine().getPgPercentage() * 100));
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
                        recipeInputsBundle.getNicotine().setPgPercentage(currentInput / 100); // TODO VG PG % as Integer?
                        recipeInputsBundle.getNicotine().setVgPercentage(100 - (currentInput / 100));
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
                Double.parseDouble(((EditText) flavorRowView.findViewById(R.id.editTextRecipeFlavorPercentage)).getText().toString());
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
        return EliquidCalculatorHelper.getRecipeIngredientsResults(recipeInputsBundle, ingredientsInStash);
    }

    private LinkedHashMap<String, Double> getFlavorsPercentagesMap() {
        LinkedHashMap<String, Double> flavorsPercentagesMap = new LinkedHashMap<>();
        for (int i = 0; i < binding.linearLayoutCalculatorFragFlavors.getChildCount(); i++) {
            View flavorRowView = binding.linearLayoutCalculatorFragFlavors.getChildAt(i);
            String flavorName = ((AutoCompleteTextView) flavorRowView.findViewById(R.id.autoCompleteTextViewFlavorName)).getText().toString();
            Double flavorPercentage = Double.parseDouble(((EditText) flavorRowView.findViewById(R.id.editTextRecipeFlavorPercentage)).getText().toString()) / 100;
            if (flavorName.matches("")) {
                flavorName = "Flavor " + (i + 1) + " (" + flavorPercentage * 100 + "%)";
            }
            flavorsPercentagesMap.put(flavorName, flavorPercentage);
        }
        return flavorsPercentagesMap;
    }

    private void addFlavorRowInInputsTable() {
        LayoutInflater vi = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View flavorRowInInputsView = vi.inflate(R.layout.flavor_recipe_component_layout,
                binding.linearLayoutCalculatorFragFlavors,
                false);

        recipeInputsBundle.getFlavors().add(0, new FlavorInputsBundle("", 0d, BigDecimal.ZERO));
        EditText editTextRecipeFlavorPercentage = flavorRowInInputsView.findViewById(R.id.editTextRecipeFlavorPercentage);
        ImageView imageViewDeleteRecipeFlavor = flavorRowInInputsView.findViewById(R.id.imageViewDeleteRecipeFlavor);
        AutoCompleteTextView autoCompleteTextViewFlavorName = flavorRowInInputsView.findViewById(R.id.autoCompleteTextViewFlavorName);

        configureEditTextRecipeFlavorPercentage(flavorRowInInputsView, editTextRecipeFlavorPercentage);
        configureImageViewDeleteRecipeFlavor(flavorRowInInputsView, imageViewDeleteRecipeFlavor);
        configureAutoCompleteTextViewFlavorName(flavorRowInInputsView, autoCompleteTextViewFlavorName);

        binding.linearLayoutCalculatorFragFlavors.addView(flavorRowInInputsView,
                0,
                new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT));
    }

    private void configureAutoCompleteTextViewFlavorName(View flavorRowInInputsView, AutoCompleteTextView autoCompleteTextViewFlavorName) {
        String[] flavorNames = allFlavorsInDatabase.stream().map(Flavor::getName).toArray(String[]::new); // TODO super slow?
        ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter<>(getContext(), android.R.layout.select_dialog_item, flavorNames);
        autoCompleteTextViewFlavorName.setThreshold(3);
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

                FlavorInputsBundle flavorWithNewName = recipeInputsBundle.getFlavors().get(currentFlavorIndexInInputs);
                flavorWithNewName.setDescription(String.valueOf(s));
                recipeInputsBundle.getFlavors().set(currentFlavorIndexInInputs, flavorWithNewName);
                Log.e("FLAVOR-CHASER-BUNDLE-NAME", recipeInputsBundle.getFlavors().toString());
                // TODO Also update price
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void configureImageViewDeleteRecipeFlavor(View flavorRowInInputsView, ImageView imageViewDeleteRecipeFlavor) {
        imageViewDeleteRecipeFlavor.setOnClickListener(v1 -> {
            int currentFlavorIndexInInputs = binding.linearLayoutCalculatorFragFlavors.indexOfChild(flavorRowInInputsView);
            recipeInputsBundle.getFlavors().remove(currentFlavorIndexInInputs);
            Log.e("FLAVOR-CHASER-BUNDLE-DELETE", recipeInputsBundle.getFlavors().toString());
            binding.linearLayoutCalculatorFragFlavors.removeView(flavorRowInInputsView);
            displayRecipeResultsInTable();
        });
    }

    private void configureEditTextRecipeFlavorPercentage(View flavorRowInInputsView, EditText editTextRecipeFlavorPercentage) {
        editTextRecipeFlavorPercentage.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try {
                    int currentFlavorIndexInInputs = binding.linearLayoutCalculatorFragFlavors.indexOfChild(flavorRowInInputsView);
                    Double newPercentage = Double.parseDouble(String.valueOf(s)) / 100;
                    FlavorInputsBundle flavorWithNewPercentage = recipeInputsBundle.getFlavors().get(currentFlavorIndexInInputs);
                    flavorWithNewPercentage.setPercentage(newPercentage);
                    recipeInputsBundle.getFlavors().set(currentFlavorIndexInInputs, flavorWithNewPercentage);
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

    private void setEditTextError(EditText editText, String errorMessage) {
        editText.setError(errorMessage);
        editText.requestFocus();
    }
}







