package teodor.flavor_chaser_android_app.classes;

public class RecipeIngredientResult {

    private String ingredientName;
    private Double milliliters;
    private Double grams;
    private Double percentageOfTotal;
    private Double cost;

    public RecipeIngredientResult(String ingredientName, Double milliliters, Double grams, Double percentageOfTotal, Double cost) {
        this.ingredientName = ingredientName;
        this.milliliters = milliliters;
        this.grams = grams;
        this.percentageOfTotal = percentageOfTotal;
        this.cost = cost;
    }

    public RecipeIngredientResult() {
    }

    public String getIngredientName() {
        return ingredientName;
    }

    public void setIngredientName(String ingredientName) {
        this.ingredientName = ingredientName;
    }

    public Double getMilliliters() {
        return milliliters;
    }

    public void setMilliliters(Double milliliters) {
        this.milliliters = milliliters;
    }

    public Double getGrams() {
        return grams;
    }

    public void setGrams(Double grams) {
        this.grams = grams;
    }

    public Double getPercentageOfTotal() {
        return percentageOfTotal;
    }

    public void setPercentageOfTotal(Double percentageOfTotal) {
        this.percentageOfTotal = percentageOfTotal;
    }

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    @Override
    public String toString() {
        return "RecipeIngredientResult{" +
                "ingredientName='" + ingredientName + '\'' +
                ", milliliters=" + milliliters +
                ", grams=" + grams +
                ", percentageOfTotal=" + percentageOfTotal +
                ", cost=" + cost +
                '}';
    }
}
