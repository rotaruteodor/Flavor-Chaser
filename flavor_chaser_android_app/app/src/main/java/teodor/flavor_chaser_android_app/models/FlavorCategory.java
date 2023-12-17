package teodor.flavor_chaser_android_app.models;

import android.os.Parcel;
import android.os.Parcelable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
public class FlavorCategory implements Parcelable {

    private Long id;
    private String name;
//    private List<FlavorDto> flavors;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.id);
        dest.writeString(this.name);
    }

    public void readFromParcel(Parcel source) {
        this.id = (Long) source.readValue(Long.class.getClassLoader());
        this.name = source.readString();
    }

    public FlavorCategory() {
    }

    protected FlavorCategory(Parcel in) {
        this.id = (Long) in.readValue(Long.class.getClassLoader());
        this.name = in.readString();
    }

    public static final Parcelable.Creator<FlavorCategory> CREATOR = new Parcelable.Creator<FlavorCategory>() {
        @Override
        public FlavorCategory createFromParcel(Parcel source) {
            return new FlavorCategory(source);
        }

        @Override
        public FlavorCategory[] newArray(int size) {
            return new FlavorCategory[size];
        }
    };
}
