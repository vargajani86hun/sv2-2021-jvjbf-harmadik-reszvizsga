package vehiclerental;

import java.time.LocalTime;

public interface Rentable extends Comparable<Rentable>{
    int calculateSumPrice(long minutes);

    LocalTime getRentingTime();

    void rent(LocalTime time);

    void closeRent();

    default int compareTo(Rentable rentable) {
        return this.getRentingTime().compareTo(rentable.getRentingTime());
    }
}
