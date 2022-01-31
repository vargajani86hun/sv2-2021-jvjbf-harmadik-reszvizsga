package vehiclerental;

import java.time.LocalTime;
import java.util.Objects;

public class Car implements Rentable{

    private String id;
    private LocalTime rentingTime;
    private int pricePerMinute;

    public Car(String id, int pricePerMinute) {
        this.id = id;
        this.pricePerMinute = pricePerMinute;
    }

    @Override
    public int calculateSumPrice(long minutes) {
        return (int) minutes * pricePerMinute;
    }

    @Override
    public LocalTime getRentingTime() {
        return rentingTime;
    }

    @Override
    public void rent(LocalTime time) {
        rentingTime = time;
    }

    @Override
    public void closeRent() {
        rentingTime = null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Car car = (Car) o;
        return pricePerMinute == car.pricePerMinute && Objects.equals(id, car.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, pricePerMinute);
    }
}
