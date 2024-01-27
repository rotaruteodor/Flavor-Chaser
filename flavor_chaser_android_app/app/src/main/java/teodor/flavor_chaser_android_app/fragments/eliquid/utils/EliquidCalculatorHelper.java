package teodor.flavor_chaser_android_app.fragments.eliquid.utils;

import static java.math.RoundingMode.CEILING;
import static teodor.flavor_chaser_android_app.models.enums.MainIngredientType.BASE;
import static teodor.flavor_chaser_android_app.models.enums.MainIngredientType.FLAVOR;
import static teodor.flavor_chaser_android_app.models.enums.MainIngredientType.NICOTINE;
import static teodor.flavor_chaser_android_app.models.enums.MainIngredientType.PG;
import static teodor.flavor_chaser_android_app.models.enums.MainIngredientType.VG;

import android.util.Log;

import java.math.BigDecimal;
import java.util.ArrayList;

import teodor.flavor_chaser_android_app.models.IngredientInStash;
import teodor.flavor_chaser_android_app.models.enums.MainIngredientType;

public class EliquidCalculatorHelper {

    // TODO Improve cost
    // TODO Maybe I don't want the InputsBundle design to be separated into base, nicotine
    private static final Double VG_WEIGHT_PER_ML = 1.26;
    private static final Double PG_WEIGHT_PER_ML = 1.04;
    private static final Double PURE_NICOTINE_WEIGHT_PER_ML = 1.01;
    private static final Double FLAVOR_WEIGHT_PER_ML = 1d;
    private static final Double FLAVOR_PG_PERCENTAGE = 1d;
    private static final Double FLAVOR_VG_PERCENTAGE = 0d;
    private static final String NO_DESCRIPTION_TAG = "No";

    public static ArrayList<RecipeIngredientResult> getRecipeIngredientsResults(RecipeInputsBundle recipeInputsBundle,
                                                                                ArrayList<IngredientInStash> ingredientsInStash) {
        Log.e("asdadadad", recipeInputsBundle.toString());
        Double finalAmount = recipeInputsBundle.getFinalAmount();
        RecipeIngredientResult nicotineRecipeIngredientResult = getNicotineRecipeResult(recipeInputsBundle.getNicotine(),
                finalAmount,
                getIngredientInStash(ingredientsInStash, NICOTINE, recipeInputsBundle.getNicotine().getDescription()));

        ArrayList<RecipeIngredientResult> flavorsRecipeIngredientsResults = new ArrayList<>();
        Double allFlavorsMilliliters = 0d;
        for (FlavorInputsBundle flavor : recipeInputsBundle.getFlavors()) {
            flavorsRecipeIngredientsResults.add(getFlavorRecipeResult(flavor,
                    finalAmount,
                    getIngredientInStash(ingredientsInStash, FLAVOR, flavor.getDescription())));
            allFlavorsMilliliters += finalAmount * flavor.getPercentage();
        }

        ArrayList<RecipeIngredientResult> recipeIngredientsResults = new ArrayList<>();
        if (recipeInputsBundle.getBase().getWithIndividualPgVg()) {
            recipeIngredientsResults.add(getVgRecipeResult(recipeInputsBundle.getBase().getVgPercentage(),
                    recipeInputsBundle.getNicotine().getVgPercentage(),
                    finalAmount,
                    allFlavorsMilliliters,
                    nicotineRecipeIngredientResult.getMilliliters(),
                    getIngredientInStash(ingredientsInStash, VG, recipeInputsBundle.getBase().getVgDescription())));
            recipeIngredientsResults.add(getPgRecipeResult(recipeInputsBundle.getBase().getPgPercentage(),
                    recipeInputsBundle.getNicotine().getPgPercentage(),
                    finalAmount,
                    allFlavorsMilliliters,
                    nicotineRecipeIngredientResult.getMilliliters(),
                    getIngredientInStash(ingredientsInStash, PG, recipeInputsBundle.getBase().getPgDescription())));
        } else {
            recipeIngredientsResults.add(getBaseRecipeResult(recipeInputsBundle.getBase().getVgPercentage(),
                    recipeInputsBundle.getBase().getPgPercentage(),
                    finalAmount,
                    allFlavorsMilliliters,
                    nicotineRecipeIngredientResult.getMilliliters(),
                    getIngredientInStash(ingredientsInStash, BASE, recipeInputsBundle.getBase().getBaseDescription())));
        }
        recipeIngredientsResults.add(nicotineRecipeIngredientResult);
        recipeIngredientsResults.addAll(flavorsRecipeIngredientsResults);
        return recipeIngredientsResults;
    }

    private static RecipeIngredientResult getVgRecipeResult(Double desiredFinalVgPercentage,
                                                            Double nicshotVgPercentage,
                                                            Double totalFinalAmount,
                                                            Double allFlavorsMilliliters,
                                                            Double nicotineMililiters,
                                                            IngredientInStash ingredientInStash) {
        Double milliliters = (totalFinalAmount * desiredFinalVgPercentage) -
                (FLAVOR_VG_PERCENTAGE * allFlavorsMilliliters +
                        nicshotVgPercentage * nicotineMililiters);
        Double grams = milliliters * VG_WEIGHT_PER_ML;
        Double percentageOfTotal = (milliliters / totalFinalAmount) * 100;
        BigDecimal cost = ingredientInStash == null ? BigDecimal.ZERO : ingredientInStash.getPrice()
                .divide(BigDecimal.valueOf(ingredientInStash.getPriceQuantityInMl()), CEILING);
        return new RecipeIngredientResult("VG", milliliters, grams, percentageOfTotal, cost);
    }

    private static RecipeIngredientResult getPgRecipeResult(Double desiredFinalPgPercentage,
                                                            Double nicshotPgPercentage,
                                                            Double totalFinalAmount,
                                                            Double allFlavorsMilliliters,
                                                            Double nicotineMililiters,
                                                            IngredientInStash ingredientInStash) {
        Double milliliters = (totalFinalAmount * desiredFinalPgPercentage) -
                (FLAVOR_PG_PERCENTAGE * allFlavorsMilliliters +
                        nicshotPgPercentage * nicotineMililiters);
        Double grams = milliliters * PG_WEIGHT_PER_ML;
        Double percentageOfTotal = (milliliters / totalFinalAmount) * 100;
        BigDecimal cost = ingredientInStash == null ? BigDecimal.ZERO : ingredientInStash.getPrice()
                .divide(BigDecimal.valueOf(ingredientInStash.getPriceQuantityInMl()), CEILING);
        return new RecipeIngredientResult("PG", milliliters, grams, percentageOfTotal, cost);
    }

    private static RecipeIngredientResult getBaseRecipeResult(Double baseVgPercentage,
                                                              Double basePgPercentage,
                                                              Double totalFinalAmount,
                                                              Double allFlavorsMilliliters,
                                                              Double nicotineMililiters,
                                                              IngredientInStash ingredientInStash) {
        Double milliliters = totalFinalAmount - (nicotineMililiters + allFlavorsMilliliters);
        Double grams = milliliters * (baseVgPercentage * VG_WEIGHT_PER_ML + basePgPercentage * PG_WEIGHT_PER_ML);
        Double percentageOfTotal = (milliliters / totalFinalAmount) * 100;
        BigDecimal cost = ingredientInStash == null ? BigDecimal.ZERO : ingredientInStash.getPrice()
                .divide(BigDecimal.valueOf(ingredientInStash.getPriceQuantityInMl()), CEILING);
        return new RecipeIngredientResult("Base", milliliters, grams, percentageOfTotal, cost);
    }

    private static RecipeIngredientResult getNicotineRecipeResult(NicotineInputsBundle nicStats,
                                                                  Double finalAmount,
                                                                  IngredientInStash ingredientInStash) {
        Double milliliters = (finalAmount * nicStats.getFinalStrengthInMg()) / nicStats.getStrengthInMg();
        Double nicshotStrengthPercentage = nicStats.getStrengthInMg() / 1000;
        Double grams = (milliliters * nicshotStrengthPercentage * PURE_NICOTINE_WEIGHT_PER_ML) +
                (milliliters * (1 - nicshotStrengthPercentage)) *
                        (nicStats.getVgPercentage() * VG_WEIGHT_PER_ML + nicStats.getPgPercentage() * PG_WEIGHT_PER_ML);
        Double percentageOfTotal = (milliliters / finalAmount) * 100;
        BigDecimal cost = ingredientInStash == null ? BigDecimal.ZERO : ingredientInStash.getPrice()
                .divide(BigDecimal.valueOf(ingredientInStash.getPriceQuantityInMl()), CEILING);
        return new RecipeIngredientResult("Nicotine", milliliters, grams, percentageOfTotal, cost);
    }

    private static RecipeIngredientResult getFlavorRecipeResult(FlavorInputsBundle flavorStats,
                                                                Double totalFinalAmount,
                                                                IngredientInStash ingredientInStash) {
        Double milliliters = totalFinalAmount * flavorStats.getPercentage();
        Double grams = milliliters * FLAVOR_WEIGHT_PER_ML;
        Double percentageOfTotal = (milliliters / totalFinalAmount) * 100;
        BigDecimal cost = ingredientInStash == null ? BigDecimal.ZERO : ingredientInStash.getPrice()
                .divide(BigDecimal.valueOf(ingredientInStash.getPriceQuantityInMl()), CEILING);
        return new RecipeIngredientResult(flavorStats.getDescription(), milliliters, grams, percentageOfTotal, cost);
    }

    private static IngredientInStash getIngredientInStash(ArrayList<IngredientInStash> ingredientsInStash,
                                                          MainIngredientType ingredientType,
                                                          String description) {
        // TODO Improve
        return (description.equals(NO_DESCRIPTION_TAG)) ? null : ingredientsInStash.stream()
                .filter(i -> i.getType() == ingredientType && i.getDescription().equals(description))
                .findAny()
                .orElse(null);
    }
}
