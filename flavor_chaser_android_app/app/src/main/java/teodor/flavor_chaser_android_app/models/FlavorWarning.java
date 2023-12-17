package teodor.flavor_chaser_android_app.models;

import android.os.Parcel;
import android.os.Parcelable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class FlavorWarning implements Parcelable {

    private Long id;
    private String description;
//    private List<FlavorDto> flavors;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.id);
        dest.writeString(this.description);
    }

    public void readFromParcel(Parcel source) {
        this.id = (Long) source.readValue(Long.class.getClassLoader());
        this.description = source.readString();
    }

    public FlavorWarning() {
    }

    protected FlavorWarning(Parcel in) {
        this.id = (Long) in.readValue(Long.class.getClassLoader());
        this.description = in.readString();
    }

    public static final Parcelable.Creator<FlavorWarning> CREATOR = new Parcelable.Creator<FlavorWarning>() {
        @Override
        public FlavorWarning createFromParcel(Parcel source) {
            return new FlavorWarning(source);
        }

        @Override
        public FlavorWarning[] newArray(int size) {
            return new FlavorWarning[size];
        }
    };
}
