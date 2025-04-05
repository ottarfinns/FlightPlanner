package vinnsla.service;

import vinnsla.entities.Passenger;

public interface PassengerServiceInterface {
    Passenger selectPassenger(String nationalId);
    void updatePassenger(Passenger passenger);
    void deletePassenger(Passenger passenger);
} 