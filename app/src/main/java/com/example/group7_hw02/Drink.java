package com.example.group7_hw02;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Drink implements Parcelable {

    int alcoholPercentage;
    int drinkSize;
    String drinkAddedDate;

    public Drink(int alcoholPercentage, int drinkSize, String drinkAddedDate) {
        this.alcoholPercentage = alcoholPercentage;
        this.drinkSize = drinkSize;
        this.drinkAddedDate = drinkAddedDate;
    }

    protected Drink(Parcel in) {
        alcoholPercentage = in.readInt();
        drinkSize = in.readInt();
        drinkAddedDate = in.readString();
    }

    public static final Creator<Drink> CREATOR = new Creator<Drink>() {
        @Override
        public Drink createFromParcel(Parcel in) {
            return new Drink(in);
        }

        @Override
        public Drink[] newArray(int size) {
            return new Drink[size];
        }
    };

    @NonNull
    @Override
    public String toString() {
        return "Drink{" +
                "alcoholPercentage=" + alcoholPercentage +
                ", drinkSize=" + drinkSize +
                ", drinkAddedDate='" + drinkAddedDate + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(alcoholPercentage);
        parcel.writeInt(drinkSize);
        parcel.writeString(drinkAddedDate);
    }
}
