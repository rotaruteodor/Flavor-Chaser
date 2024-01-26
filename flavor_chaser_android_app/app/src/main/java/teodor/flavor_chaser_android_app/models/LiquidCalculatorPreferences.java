package teodor.flavor_chaser_android_app.models;

import android.os.Parcel;
import android.os.Parcelable;

import lombok.Data;

@Data
public class LiquidCalculatorPreferences implements Parcelable {
    private Long id;
    private Double baseVgPercentage;
    private Double basePgPercentage;
    private String baseDescription;
    private boolean withIndividualPgVg;
    private String pgDescription;
    private String vgDescription;
    private Double nicshotVgPercentage;
    private Double nicshotPgPercentage;
    private Double finalNicotineStrength;
    private Double nicshotStrengthInMg;
    private String nicshotDescription;
    private Double totalFinalAmount;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.id);
        dest.writeValue(this.baseVgPercentage);
        dest.writeValue(this.basePgPercentage);
        dest.writeString(this.baseDescription);
        dest.writeByte(this.withIndividualPgVg ? (byte) 1 : (byte) 0);
        dest.writeString(this.pgDescription);
        dest.writeString(this.vgDescription);
        dest.writeValue(this.nicshotVgPercentage);
        dest.writeValue(this.nicshotPgPercentage);
        dest.writeValue(this.finalNicotineStrength);
        dest.writeValue(this.nicshotStrengthInMg);
        dest.writeString(this.nicshotDescription);
        dest.writeValue(this.totalFinalAmount);
    }

    public void readFromParcel(Parcel source) {
        this.id = (Long) source.readValue(Long.class.getClassLoader());
        this.baseVgPercentage = (Double) source.readValue(Double.class.getClassLoader());
        this.basePgPercentage = (Double) source.readValue(Double.class.getClassLoader());
        this.baseDescription = source.readString();
        this.withIndividualPgVg = source.readByte() != 0;
        this.pgDescription = source.readString();
        this.vgDescription = source.readString();
        this.nicshotVgPercentage = (Double) source.readValue(Double.class.getClassLoader());
        this.nicshotPgPercentage = (Double) source.readValue(Double.class.getClassLoader());
        this.finalNicotineStrength = (Double) source.readValue(Double.class.getClassLoader());
        this.nicshotStrengthInMg = (Double) source.readValue(Double.class.getClassLoader());
        this.nicshotDescription = source.readString();
        this.totalFinalAmount = (Double) source.readValue(Double.class.getClassLoader());
    }

    protected LiquidCalculatorPreferences(Parcel in) {
        this.id = (Long) in.readValue(Long.class.getClassLoader());
        this.baseVgPercentage = (Double) in.readValue(Double.class.getClassLoader());
        this.basePgPercentage = (Double) in.readValue(Double.class.getClassLoader());
        this.baseDescription = in.readString();
        this.withIndividualPgVg = in.readByte() != 0;
        this.pgDescription = in.readString();
        this.vgDescription = in.readString();
        this.nicshotVgPercentage = (Double) in.readValue(Double.class.getClassLoader());
        this.nicshotPgPercentage = (Double) in.readValue(Double.class.getClassLoader());
        this.finalNicotineStrength = (Double) in.readValue(Double.class.getClassLoader());
        this.nicshotStrengthInMg = (Double) in.readValue(Double.class.getClassLoader());
        this.nicshotDescription = in.readString();
        this.totalFinalAmount = (Double) in.readValue(Double.class.getClassLoader());
    }

    public static final Parcelable.Creator<LiquidCalculatorPreferences> CREATOR = new Parcelable.Creator<LiquidCalculatorPreferences>() {
        @Override
        public LiquidCalculatorPreferences createFromParcel(Parcel source) {
            return new LiquidCalculatorPreferences(source);
        }

        @Override
        public LiquidCalculatorPreferences[] newArray(int size) {
            return new LiquidCalculatorPreferences[size];
        }
    };
}
