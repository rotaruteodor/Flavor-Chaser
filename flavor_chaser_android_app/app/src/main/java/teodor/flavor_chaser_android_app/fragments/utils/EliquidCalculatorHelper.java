package teodor.flavor_chaser_android_app.fragments.utils;

import static teodor.flavor_chaser_android_app.models.enums.MainIngredientType.FLAVOR;
import static teodor.flavor_chaser_android_app.models.enums.MainIngredientType.VG;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;

import teodor.flavor_chaser_android_app.models.IngredientInStash;

public class EliquidCalculatorHelper {

    // TODO Improve cost
    public static final Double VG_WEIGHT_PER_ML = 1.26;
    public static final Double PG_WEIGHT_PER_ML = 1.04;
    public static final Double PURE_NICOTINE_WEIGHT_PER_ML = 1.01;
    public static final Double FLAVOR_WEIGHT_PER_ML = 1d;
    public static final Double FLAVOR_PG_PERCENTAGE = 1d;
    public static final Double FLAVOR_VG_PERCENTAGE = 0d;

    public static ArrayList<RecipeIngredientResult> getRecipeIngredientsResults(Double baseVgPercentage,
                                                                                Double basePgPercentage,
                                                                                boolean withIndividualPgVg,
                                                                                Double nicshotVgPercentage,
                                                                                Double nicshotPgPercentage,
                                                                                Double finalNicotineStrength,
                                                                                Double nicshotStrengthInMg,
                                                                                LinkedHashMap<String, Double> flavorsPercentagesMap,
                                                                                Double totalFinalAmount,
                                                                                ArrayList<IngredientInStash> ingredientsInStash) {

        RecipeIngredientResult nicotineRecipeIngredientResult = getNicotineRecipeResult(nicshotVgPercentage,
                nicshotPgPercentage,
                totalFinalAmount,
                finalNicotineStrength,
                nicshotStrengthInMg,
                ingredientsInStash);

        ArrayList<RecipeIngredientResult> flavorsRecipeIngredientsResults = new ArrayList<>();
        Double allFlavorsMilliliters = 0d;
        for (Map.Entry<String, Double> entry : flavorsPercentagesMap.entrySet()) {
            flavorsRecipeIngredientsResults.add(getFlavorRecipeResult(entry.getKey(),
                    entry.getValue(),
                    totalFinalAmount,
                    ingredientsInStash));
            allFlavorsMilliliters += totalFinalAmount * entry.getValue();
        }

        ArrayList<RecipeIngredientResult> recipeIngredientsResults = new ArrayList<>();
        if (withIndividualPgVg) {
            recipeIngredientsResults.add(getVgRecipeResult(baseVgPercentage,
                    nicshotVgPercentage,
                    totalFinalAmount,
                    allFlavorsMilliliters,
                    nicotineRecipeIngredientResult.getMilliliters(),
                    ingredientsInStash));
            recipeIngredientsResults.add(getPgRecipeResult(basePgPercentage,
                    nicshotPgPercentage,
                    totalFinalAmount,
                    allFlavorsMilliliters,
                    nicotineRecipeIngredientResult.getMilliliters(),
                    ingredientsInStash));
        } else {
            recipeIngredientsResults.add(getBaseRecipeResult(baseVgPercentage,
                    basePgPercentage,
                    totalFinalAmount,
                    allFlavorsMilliliters,
                    nicotineRecipeIngredientResult.getMilliliters(),
                    ingredientsInStash));
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
                                                            ArrayList<IngredientInStash> ingredientsInStash) {
        Double milliliters = (totalFinalAmount * desiredFinalVgPercentage) -
                (FLAVOR_VG_PERCENTAGE * allFlavorsMilliliters +
                        nicshotVgPercentage * nicotineMililiters);
        Double grams = milliliters * VG_WEIGHT_PER_ML;
        Double percentageOfTotal = (milliliters / totalFinalAmount) * 100;
        Double cost = 10.0; // TODO
        return new RecipeIngredientResult("VG", milliliters, grams, percentageOfTotal, cost);
    }

    private static RecipeIngredientResult getPgRecipeResult(Double desiredFinalPgPercentage,
                                                            Double nicshotPgPercentage,
                                                            Double totalFinalAmount,
                                                            Double allFlavorsMilliliters,
                                                            Double nicotineMililiters,
                                                            ArrayList<IngredientInStash> ingredientsInStash) {
        Double milliliters = (totalFinalAmount * desiredFinalPgPercentage) -
                (FLAVOR_PG_PERCENTAGE * allFlavorsMilliliters +
                        nicshotPgPercentage * nicotineMililiters);
        Double grams = milliliters * PG_WEIGHT_PER_ML;
        Double percentageOfTotal = (milliliters / totalFinalAmount) * 100;
        Double cost = (double) new Random().nextInt(10); // TODO
        return new RecipeIngredientResult("PG", milliliters, grams, percentageOfTotal, cost);
    }

    private static RecipeIngredientResult getBaseRecipeResult(Double baseVgPercentage,
                                                              Double basePgPercentage,
                                                              Double totalFinalAmount,
                                                              Double allFlavorsMilliliters,
                                                              Double nicotineMililiters,
                                                              ArrayList<IngredientInStash> ingredientsInStash) {
        Double milliliters = totalFinalAmount - (nicotineMililiters + allFlavorsMilliliters);
        Double grams = milliliters * (baseVgPercentage * VG_WEIGHT_PER_ML + basePgPercentage * PG_WEIGHT_PER_ML);
        Double percentageOfTotal = (milliliters / totalFinalAmount) * 100;
        Double cost = (double) new Random().nextInt(10); // TODO
        return new RecipeIngredientResult("Base", milliliters, grams, percentageOfTotal, cost);
    }

    private static RecipeIngredientResult getNicotineRecipeResult(Double nicshotVgPercentage,
                                                                  Double nicshotPgPercentage,
                                                                  Double totalFinalAmount,
                                                                  Double finalNicotineStrength,
                                                                  Double nicshotStrengthInMg,
                                                                  ArrayList<IngredientInStash> ingredientsInStash) {
        Double milliliters = (totalFinalAmount * finalNicotineStrength) / nicshotStrengthInMg;
        Double nicshotStrengthPercentage = nicshotStrengthInMg / 1000;
        Double grams = (milliliters * nicshotStrengthPercentage * PURE_NICOTINE_WEIGHT_PER_ML) +
                (milliliters * (1 - nicshotStrengthPercentage)) *
                        (nicshotVgPercentage * VG_WEIGHT_PER_ML + nicshotPgPercentage * PG_WEIGHT_PER_ML);
        Double percentageOfTotal = (milliliters / totalFinalAmount) * 100;
        Double cost = (double) new Random().nextInt(10); // TODO
        return new RecipeIngredientResult("Nicotine", milliliters, grams, percentageOfTotal, cost);
    }

    private static RecipeIngredientResult getFlavorRecipeResult(String flavorName,
                                                                Double flavorPercentage,
                                                                Double totalFinalAmount,
                                                                ArrayList<IngredientInStash> ingredientsInStash) {
        Double milliliters = totalFinalAmount * flavorPercentage;
        Double grams = milliliters * FLAVOR_WEIGHT_PER_ML;
        Double percentageOfTotal = (milliliters / totalFinalAmount) * 100;
        Double cost = (double) new Random().nextInt(10); // TODO
        return new RecipeIngredientResult(flavorName, milliliters, grams, percentageOfTotal, cost);
    }

}
