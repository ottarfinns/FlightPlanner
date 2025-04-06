package vinnsla.repository;

import vinnsla.entities.Passenger;

public interface PassengerRepository {
    Passenger selectPassenger(String nationalId);
    void updatePassenger(Passenger passenger);
    void deletePassenger(Passenger passenger);
} 