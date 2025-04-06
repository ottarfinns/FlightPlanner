package vinnsla.service;

import vinnsla.entities.Passenger;
import vinnsla.repository.PassengerRepository;

public class PassengerService implements PassengerServiceInterface {
    private PassengerRepository passengerRepository;

    public PassengerService(PassengerRepository passengerRepository) {
        this.passengerRepository = passengerRepository;
    }

    @Override
    public Passenger selectPassenger(String nationalId) {
        return passengerRepository.selectPassenger(nationalId);
    }

    @Override
    public void updatePassenger(Passenger passenger) {
        passengerRepository.updatePassenger(passenger);
    }

    @Override
    public void deletePassenger(Passenger passenger) {
        passengerRepository.deletePassenger(passenger);
    }
} 