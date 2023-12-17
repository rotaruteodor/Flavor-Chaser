package teodor.flavor_chaser_android_app.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import teodor.flavor_chaser_android_app.enums.MainIngredientType;


@Data
public class IngredientInStash implements Parcelable {

    private Long id;
    private MainIngredientType type;
    private Flavor flavor;
    private Double currentQuantityInMl;
    private LocalDate purchaseDate;
    private BigDecimal price;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.id);
        dest.writeInt(this.type == null ? -1 : this.type.ordinal());
        dest.writeParcelable(this.flavor, flags);
        dest.writeValue(this.currentQuantityInMl);
        dest.writeSerializable(this.purchaseDate);
        dest.writeSerializable(this.price);
    }

    public void readFromParcel(Parcel source) {
        this.id = (Long) source.readValue(Long.class.getClassLoader());
        int tmpType = source.readInt();
        this.type = tmpType == -1 ? null : MainIngredientType.values()[tmpType];
        this.flavor = source.readParcelable(Flavor.class.getClassLoader());
        this.currentQuantityInMl = (Double) source.readValue(Double.class.getClassLoader());
        this.purchaseDate = (LocalDate) source.readSerializable();
        this.price = (BigDecimal) source.readSerializable();
    }

    public IngredientInStash() {
    }

    protected IngredientInStash(Parcel in) {
        this.id = (Long) in.readValue(Long.class.getClassLoader());
        int tmpType = in.readInt();
        this.type = tmpType == -1 ? null : MainIngredientType.values()[tmpType];
        this.flavor = in.readParcelable(Flavor.class.getClassLoader());
        this.currentQuantityInMl = (Double) in.readValue(Double.class.getClassLoader());
        this.purchaseDate = (LocalDate) in.readSerializable();
        this.price = (BigDecimal) in.readSerializable();
    }

    public static final Parcelable.Creator<IngredientInStash> CREATOR = new Parcelable.Creator<IngredientInStash>() {
        @Override
        public IngredientInStash createFromParcel(Parcel source) {
            return new IngredientInStash(source);
        }

        @Override
        public IngredientInStash[] newArray(int size) {
            return new IngredientInStash[size];
        }
    };
}
