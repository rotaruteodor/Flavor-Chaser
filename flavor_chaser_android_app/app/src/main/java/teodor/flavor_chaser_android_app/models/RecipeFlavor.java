package teodor.flavor_chaser_android_app.models;

import android.os.Parcel;
import android.os.Parcelable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class RecipeFlavor implements Parcelable {
    private Long id;
    private Flavor flavor;
    private Double percentage;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.id);
        dest.writeParcelable(this.flavor, flags);
        dest.writeDouble(this.percentage);
    }

    public void readFromParcel(Parcel source) {
        this.id = source.readLong();
        this.flavor = source.readParcelable(Flavor.class.getClassLoader());
        this.percentage = source.readDouble();
    }

    public RecipeFlavor() {
    }

    protected RecipeFlavor(Parcel in) {
        this.id = in.readLong();
        this.flavor = in.readParcelable(Flavor.class.getClassLoader());
        this.percentage = in.readDouble();
    }

    public static final Parcelable.Creator<RecipeFlavor> CREATOR = new Parcelable.Creator<RecipeFlavor>() {
        @Override
        public RecipeFlavor createFromParcel(Parcel source) {
            return new RecipeFlavor(source);
        }

        @Override
        public RecipeFlavor[] newArray(int size) {
            return new RecipeFlavor[size];
        }
    };
}
