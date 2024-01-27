package teodor.flavor_chaser_android_app.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class User implements Parcelable {

    private Long id;
    private String firstName;
    private String lastName;
    private String emailAddress;
    private String password;
    private LocalDateTime creationDate;
    private LiquidCalculatorPreferences liquidCalculatorPreferences;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.id);
        dest.writeString(this.firstName);
        dest.writeString(this.lastName);
        dest.writeString(this.emailAddress);
        dest.writeString(this.password);
        dest.writeSerializable(this.creationDate);
        dest.writeParcelable(this.liquidCalculatorPreferences, flags);
    }

    public void readFromParcel(Parcel source) {
        this.id = (Long) source.readValue(Long.class.getClassLoader());
        this.firstName = source.readString();
        this.lastName = source.readString();
        this.emailAddress = source.readString();
        this.password = source.readString();
        this.creationDate = (LocalDateTime) source.readSerializable();
        this.liquidCalculatorPreferences = source.readParcelable(LiquidCalculatorPreferences.class.getClassLoader());
    }

    protected User(Parcel in) {
        this.id = (Long) in.readValue(Long.class.getClassLoader());
        this.firstName = in.readString();
        this.lastName = in.readString();
        this.emailAddress = in.readString();
        this.password = in.readString();
        this.creationDate = (LocalDateTime) in.readSerializable();
        this.liquidCalculatorPreferences = in.readParcelable(LiquidCalculatorPreferences.class.getClassLoader());
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
