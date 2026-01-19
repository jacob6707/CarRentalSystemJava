package dev.jacob6707.carrentalsystemjavafx.model.rental;

import dev.jacob6707.carrentalsystemjavafx.model.Entity;
import dev.jacob6707.carrentalsystemjavafx.repository.DatabaseCustomersRepository;
import dev.jacob6707.carrentalsystemjavafx.repository.DatabaseVehiclesRepository;
import dev.jacob6707.carrentalsystemjavafx.util.database.DatabaseColumn;

import java.time.LocalDateTime;
import java.util.UUID;

public class RentalDTO extends Entity {
    @DatabaseColumn("vehicle_id")
    private UUID vehicleId;
    @DatabaseColumn("customer_id")
    private UUID customerId;
    @DatabaseColumn("start_date")
    private LocalDateTime startDate;
    @DatabaseColumn("end_date")
    private LocalDateTime endDate;

    public RentalDTO() {}

    public RentalDTO(UUID vehicleId, UUID customerId, LocalDateTime startDate, LocalDateTime endDate) {
        this.vehicleId = vehicleId;
        this.customerId = customerId;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public RentalDTO(Rental rental) {
        this(rental.getVehicle().getId(), rental.getCustomer().getId(), rental.getStartDate(), rental.getEndDate());
        this.setId(rental.getId());
        this.setCreatedAt(rental.getCreatedAt());
        this.setUpdatedAt(rental.getUpdatedAt());
    }

    public Rental constructFromDTO() {
        DatabaseVehiclesRepository vehiclesRepository = new DatabaseVehiclesRepository();
        DatabaseCustomersRepository customersRepository = new DatabaseCustomersRepository();
        Rental rental = new Rental();
        rental.setId(this.getId());
        rental.setCreatedAt(this.getCreatedAt());
        rental.setUpdatedAt(this.getUpdatedAt());
        rental.setCustomer(customersRepository.findById(this.getCustomerId()).orElseThrow());
        rental.setVehicle(vehiclesRepository.findById(this.getVehicleId()).orElseThrow().constructFromDTO());
        rental.setStartDate(this.getStartDate());
        rental.setEndDate(this.getEndDate());
        return rental;
    }

    public UUID getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(UUID vehicleId) {
        this.vehicleId = vehicleId;
    }

    public UUID getCustomerId() {
        return customerId;
    }

    public void setCustomerId(UUID customerId) {
        this.customerId = customerId;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    @Override
    public String toString() {
        return "RentalDTO{" +
                "vehicleId=" + vehicleId +
                ", customerId=" + customerId +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                '}';
    }
}
