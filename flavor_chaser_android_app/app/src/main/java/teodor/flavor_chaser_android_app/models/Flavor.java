package teodor.flavor_chaser_android_app.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

import lombok.Data;

@Data
public class Flavor implements Parcelable {

    private Long id;
    private String name;
    private String description;
    private Company company;

    public Flavor(String name, String description, Company company, FlavorCategory category, FlavorWarning warning, List<Rating> ratings) {
        this.name = name;
        this.description = description;
        this.company = company;
        this.category = category;
        this.warning = warning;
        this.ratings = ratings;
    }

    private FlavorCategory category;
    private FlavorWarning warning;
    private List<Rating> ratings;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.id);
        dest.writeString(this.name);
        dest.writeString(this.description);
        dest.writeParcelable(this.company, flags);
        dest.writeParcelable(this.category, flags);
        dest.writeParcelable(this.warning, flags);
        dest.writeTypedList(this.ratings);
    }

    public void readFromParcel(Parcel source) {
        this.id = (Long) source.readValue(Long.class.getClassLoader());
        this.name = source.readString();
        this.description = source.readString();
        this.company = source.readParcelable(Company.class.getClassLoader());
        this.category = source.readParcelable(FlavorCategory.class.getClassLoader());
        this.warning = source.readParcelable(FlavorWarning.class.getClassLoader());
        this.ratings = source.createTypedArrayList(Rating.CREATOR);
    }

    public Flavor() {
    }

    public Flavor(Long id) {
        this.id = id;
    }

    protected Flavor(Parcel in) {
        this.id = (Long) in.readValue(Long.class.getClassLoader());
        this.name = in.readString();
        this.description = in.readString();
        this.company = in.readParcelable(Company.class.getClassLoader());
        this.category = in.readParcelable(FlavorCategory.class.getClassLoader());
        this.warning = in.readParcelable(FlavorWarning.class.getClassLoader());
        this.ratings = in.createTypedArrayList(Rating.CREATOR);
    }

    public static final Parcelable.Creator<Flavor> CREATOR = new Parcelable.Creator<Flavor>() {
        @Override
        public Flavor createFromParcel(Parcel source) {
            return new Flavor(source);
        }

        @Override
        public Flavor[] newArray(int size) {
            return new Flavor[size];
        }
    };

}
