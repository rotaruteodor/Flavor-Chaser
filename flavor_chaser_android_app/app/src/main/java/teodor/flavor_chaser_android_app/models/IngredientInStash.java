package teodor.flavor_chaser_android_app.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import teodor.flavor_chaser_android_app.models.enums.MainIngredientType;


@Data
@AllArgsConstructor
public class IngredientInStash implements Parcelable {

    private Long id;
    private MainIngredientType type;
    private String description;
    private Flavor flavor;
    private Double currentQuantityInMl;
    private Double usedQuantityInMl;
    private Double priceQuantityInMl;
    private LocalDate purchaseDate;
    private BigDecimal price;
    private User user;


    public IngredientInStash(MainIngredientType type,
                             String description,
                             Double currentQuantityInMl,
                             Double priceQuantityInMl,
                             LocalDate purchaseDate,
                             BigDecimal price,
                             User user) {
        this.type = type;
        this.description = description;
        this.currentQuantityInMl = currentQuantityInMl;
        this.priceQuantityInMl = priceQuantityInMl;
        this.purchaseDate = purchaseDate;
        this.price = price;
        this.user = user;
    }

    public IngredientInStash(MainIngredientType type,
                             String description,
                             Flavor flavor,
                             Double currentQuantityInMl,
                             Double priceQuantityInMl,
                             LocalDate purchaseDate,
                             BigDecimal price,
                             User user) {
        this.type = type;
        this.description = description;
        this.flavor = flavor;
        this.currentQuantityInMl = currentQuantityInMl;
        this.priceQuantityInMl = priceQuantityInMl;
        this.purchaseDate = purchaseDate;
        this.price = price;
        this.user = user;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.id);
        dest.writeInt(this.type == null ? -1 : this.type.ordinal());
        dest.writeString(this.description);
        dest.writeParcelable(this.flavor, flags);
        dest.writeValue(this.currentQuantityInMl);
        dest.writeValue(this.usedQuantityInMl);
        dest.writeValue(this.priceQuantityInMl);
        dest.writeSerializable(this.purchaseDate);
        dest.writeSerializable(this.price);
        dest.writeParcelable(this.user, flags);
    }

    public void readFromParcel(Parcel source) {
        this.id = (Long) source.readValue(Long.class.getClassLoader());
        int tmpType = source.readInt();
        this.type = tmpType == -1 ? null : MainIngredientType.values()[tmpType];
        this.description = source.readString();
        this.flavor = source.readParcelable(Flavor.class.getClassLoader());
        this.currentQuantityInMl = (Double) source.readValue(Double.class.getClassLoader());
        this.usedQuantityInMl = (Double) source.readValue(Double.class.getClassLoader());
        this.priceQuantityInMl = (Double) source.readValue(Double.class.getClassLoader());
        this.purchaseDate = (LocalDate) source.readSerializable();
        this.price = (BigDecimal) source.readSerializable();
        this.user = source.readParcelable(User.class.getClassLoader());
    }

    protected IngredientInStash(Parcel in) {
        this.id = (Long) in.readValue(Long.class.getClassLoader());
        int tmpType = in.readInt();
        this.type = tmpType == -1 ? null : MainIngredientType.values()[tmpType];
        this.description = in.readString();
        this.flavor = in.readParcelable(Flavor.class.getClassLoader());
        this.currentQuantityInMl = (Double) in.readValue(Double.class.getClassLoader());
        this.usedQuantityInMl = (Double) in.readValue(Double.class.getClassLoader());
        this.priceQuantityInMl = (Double) in.readValue(Double.class.getClassLoader());
        this.purchaseDate = (LocalDate) in.readSerializable();
        this.price = (BigDecimal) in.readSerializable();
        this.user = in.readParcelable(User.class.getClassLoader());
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
