package teodor.flavor_chaser_android_app.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class User implements Parcelable {

    private Long id;
    private String firstName;
    private String lastName;
    private String emailAddress;
    private String password;
    private LocalDateTime creationDate;
    private List<IngredientInStash> ingredientsInStash;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.id);
        dest.writeString(this.firstName);
        dest.writeString(this.lastName);
        dest.writeString(this.emailAddress);
        dest.writeString(this.password);
        dest.writeSerializable(this.creationDate);
        dest.writeTypedList(this.ingredientsInStash);
    }

    public void readFromParcel(Parcel source) {
        this.id = source.readLong();
        this.firstName = source.readString();
        this.lastName = source.readString();
        this.emailAddress = source.readString();
        this.password = source.readString();
        this.creationDate = (LocalDateTime) source.readSerializable();
        this.ingredientsInStash = new ArrayList<>();
        source.readTypedList(this.ingredientsInStash, IngredientInStash.CREATOR);
    }

    public User() {
    }

    protected User(Parcel in) {
        this.id = in.readLong();
        this.firstName = in.readString();
        this.lastName = in.readString();
        this.emailAddress = in.readString();
        this.password = in.readString();
        this.creationDate = (LocalDateTime) in.readSerializable();
        this.ingredientsInStash = new ArrayList<>();
        in.readTypedList(this.ingredientsInStash, IngredientInStash.CREATOR);
    }

    public static final Parcelable.Creator<User> CREATOR = new Parcelable.Creator<User>() {
        @Override
        public User createFromParcel(Parcel source) {
            return new User(source);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };
}
