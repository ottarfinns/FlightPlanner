package vinnsla.controller;

import vinnsla.entities.Passenger;
import vinnsla.service.PassengerServiceInterface;

public class PassengerController {
    private PassengerServiceInterface passengerService;

    public PassengerController(PassengerServiceInterface passengerService) {
        this.passengerService = passengerService;
    }

    public Passenger selectPassenger(String nationalId) {
        return passengerService.selectPassenger(nationalId);
    }

    public void updatePassenger(Passenger passenger) {
        passengerService.updatePassenger(passenger);
    }

    public void deletePassenger(Passenger passenger) {
        passengerService.deletePassenger(passenger);
    }
} 