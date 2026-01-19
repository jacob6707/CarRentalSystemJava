package dev.jacob6707.carrentalsystemjavafx.model.vehicle;

import dev.jacob6707.carrentalsystemjavafx.model.Entity;
import dev.jacob6707.carrentalsystemjavafx.util.database.DatabaseColumn;

import java.math.BigDecimal;

public class VehicleDTO extends Entity {
    private String brand;
    private String model;
    @DatabaseColumn("license_plate")
    private String licensePlate;
    @DatabaseColumn("manufacture_year")
    private int year;
    private int mileage;
    @DatabaseColumn("daily_price")
    private BigDecimal dailyPrice;
    @DatabaseColumn("vehicle_type")
    private String type;

    public VehicleDTO() {}

    public VehicleDTO(String brand, String model, String licensePlate, int year, int mileage, BigDecimal dailyPrice, String type) {
        super();
        this.brand = brand;
        this.model = model;
        this.licensePlate = licensePlate;
        this.year = year;
        this.mileage = mileage;
        this.dailyPrice = dailyPrice;
        this.type = type;
    }

    public VehicleDTO(Vehicle vehicle) {
        this();
        if (vehicle instanceof Car car) {
            this.brand = car.getBrand();
            this.model = car.getModel();
            this.licensePlate = car.getLicensePlate();
            this.year = car.getYear();
            this.mileage = car.getMileage();
            this.dailyPrice = car.getDailyPrice();
            this.type = "Car";
            this.setId(car.getId());
            this.setCreatedAt(car.getCreatedAt());
            this.setUpdatedAt(car.getUpdatedAt());
        } else if (vehicle instanceof SUV suv) {
            this.brand = suv.getBrand();
            this.model = suv.getModel();
            this.licensePlate = suv.getLicensePlate();
            this.year = suv.getYear();
            this.mileage = suv.getMileage();
            this.dailyPrice = suv.getDailyPrice();
            this.type = "SUV";
            this.setId(suv.getId());
            this.setCreatedAt(suv.getCreatedAt());
            this.setUpdatedAt(suv.getUpdatedAt());

        }
    }

    public VehicleDTO(Car car) {
        this(car.getBrand(), car.getModel(), car.getLicensePlate(), car.getYear(), car.getMileage(), car.getDailyPrice(), "SUV");
        this.setId(car.getId());
        this.setCreatedAt(car.getCreatedAt());
        this.setUpdatedAt(car.getUpdatedAt());
    }
    public VehicleDTO(SUV suv) {
        this(suv.getBrand(), suv.getModel(), suv.getLicensePlate(), suv.getYear(), suv.getMileage(), suv.getDailyPrice(), "SUV");
        this.setId(suv.getId());
        this.setCreatedAt(suv.getCreatedAt());
        this.setUpdatedAt(suv.getUpdatedAt());
    }
    
    public Vehicle constructFromDTO() {
        Vehicle vehicle = switch(type) {
            case "Car" -> new Car.CarBuilder().id(this.getId()).licensePlate(licensePlate).brand(brand).model(model).year(year).mileage(mileage).dailyPrice(dailyPrice).available(true).build();
            case "SUV" -> new SUV.SUVBuilder().id(this.getId()).licensePlate(licensePlate).brand(brand).model(model).year(year).mileage(mileage).dailyPrice(dailyPrice).available(true).build();
            default -> null;
        };
        if (vehicle == null) return null;
        vehicle.setCreatedAt(this.getCreatedAt());
        vehicle.setUpdatedAt(this.getUpdatedAt());
        return vehicle;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMileage() {
        return mileage;
    }

    public void setMileage(int mileage) {
        this.mileage = mileage;
    }

    public BigDecimal getDailyPrice() {
        return dailyPrice;
    }

    public void setDailyPrice(BigDecimal dailyPrice) {
        this.dailyPrice = dailyPrice;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "VehicleDTO{" +
                "brand='" + brand + '\'' +
                ", model='" + model + '\'' +
                ", licensePlate='" + licensePlate + '\'' +
                ", year=" + year +
                ", mileage=" + mileage +
                ", dailyPrice=" + dailyPrice +
                ", type='" + type + '\'' +
                '}';
    }
}
