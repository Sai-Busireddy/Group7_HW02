package com.example.group7_hw02;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Profile implements Parcelable {
    Double weight;
    String gender;

    public Profile() {
    }

    @NonNull
    @Override
    public String toString() {
        return "Profile{" +
                "weight=" + weight +
                ", gender='" + gender + '\'' +
                '}';
    }

    protected Profile(Parcel in) {
        weight = in.readDouble();
        gender = in.readString();
    }

    public static final Creator<Profile> CREATOR = new Creator<Profile>() {
        @Override
        public Profile createFromParcel(Parcel in) {
            return new Profile(in);
        }

        @Override
        public Profile[] newArray(int size) {
            return new Profile[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeDouble(this.weight);
        parcel.writeString(this.gender);
    }
}
