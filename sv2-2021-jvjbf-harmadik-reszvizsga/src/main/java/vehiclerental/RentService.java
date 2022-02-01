package vehiclerental;

import java.time.LocalTime;
import java.util.*;

public class RentService {
    public static final int MAX_RENTING_TIME_IN_MINUTES = 180;

    private Set<User> users = new HashSet<>();
    private Set<Rentable> rentables = new HashSet<>();
    private Map<Rentable, User> rents = new TreeMap<>();

    public void registerUser(User user) {
        if (isUserNameTaken(user)) {
            throw new UserNameIsAlreadyTakenException("Username is taken!");
        }
        users.add(user);
    }

    public void addRentable(Rentable rentable) {
        rentables.add(rentable);
    }

    public Set<User> getUsers() {
        return Collections.unmodifiableSet(users);
    }

    public Set<Rentable> getRentables() {
        return Collections.unmodifiableSet(rentables);
    }

    public void rent(User user, Rentable rentable, LocalTime time) {
        isRented(rentable);
        chekUsersBalance(user, rentable);
        rentable.rent(time);
        rents.put(rentable, user);
    }

    public Map<Rentable, User> getActualRenting() {
        return Collections.unmodifiableMap(rents);
    }

    public void closeRent(Rentable rentable, long minutes) {
        rents.get(rentable).minusBalance(rentable.calculateSumPrice(minutes));
        rents.remove(rentable);
        rentable.closeRent();
    }

    private boolean isUserNameTaken(User user) {
        return users.stream()
                .anyMatch(u -> u.getUserName().equals(user.getUserName()));
    }

    private boolean isRented(Rentable rentable) {
        if (rentable.getRentingTime() != null) {
            throw new IllegalStateException();
        }
        return false;
    }

    private void chekUsersBalance(User user, Rentable rentable) {
        if (user.getBalance() < rentable.calculateSumPrice(MAX_RENTING_TIME_IN_MINUTES)) {
            throw new IllegalStateException();
        }
    }
}
