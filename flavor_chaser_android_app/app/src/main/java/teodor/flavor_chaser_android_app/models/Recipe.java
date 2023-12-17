package teodor.flavor_chaser_android_app.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class Recipe implements Parcelable {
    private Long id;
    private String name;
    private String description;
    private List<RecipeFlavor> recipeFlavors;
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
        dest.writeTypedList(this.recipeFlavors);
        dest.writeList(this.ratings);
    }

    public void readFromParcel(Parcel source) {
        this.id = (Long) source.readValue(Long.class.getClassLoader());
        this.name = source.readString();
        this.description = source.readString();
        this.recipeFlavors = source.createTypedArrayList(RecipeFlavor.CREATOR);
        this.ratings = new ArrayList<Rating>();
        source.readList(this.ratings, Rating.class.getClassLoader());
    }

    public Recipe() {
    }

    protected Recipe(Parcel in) {
        this.id = (Long) in.readValue(Long.class.getClassLoader());
        this.name = in.readString();
        this.description = in.readString();
        this.recipeFlavors = in.createTypedArrayList(RecipeFlavor.CREATOR);
        this.ratings = new ArrayList<Rating>();
        in.readList(this.ratings, Rating.class.getClassLoader());
    }

    public static final Parcelable.Creator<Recipe> CREATOR = new Parcelable.Creator<Recipe>() {
        @Override
        public Recipe createFromParcel(Parcel source) {
            return new Recipe(source);
        }

        @Override
        public Recipe[] newArray(int size) {
            return new Recipe[size];
        }
    };
}
