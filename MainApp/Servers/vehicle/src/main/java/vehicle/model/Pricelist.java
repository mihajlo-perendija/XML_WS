package vehicle.model;

import java.util.ArrayList;

public class Pricelist {

    private Long id;
    private Long ownerId;
    private String name;
    private long pricePerDay;
    private long pricePerKm;
    private long cdw;
    private String description;
    private ArrayList<Vehicle> vehicles;

    public Pricelist(Long id,
                     Long ownerId,
                     String name,
                     long pricePerDay,
                     long pricePerKm,
                     long cdw,
                     String description) {
        this.id = id;
        this.ownerId = ownerId;
        this.name = name;
        this.pricePerDay = pricePerDay;
        this.pricePerKm = pricePerKm;
        this.cdw = cdw;
        this.description = description;
    }

    public Pricelist(Long id,
                     Long ownerId,
                     String name,
                     long pricePerDay,
                     long pricePerKm,
                     long cdw,
                     String description,
                     ArrayList<Vehicle> vehicles) {
        this.id = id;
        this.ownerId = ownerId;
        this.name = name;
        this.pricePerDay = pricePerDay;
        this.pricePerKm = pricePerKm;
        this.cdw = cdw;
        this.description = description;
        this.vehicles = vehicles;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getPricePerDay() {
        return pricePerDay;
    }

    public void setPricePerDay(long pricePerDay) {
        this.pricePerDay = pricePerDay;
    }

    public long getPricePerKm() {
        return pricePerKm;
    }

    public void setPricePerKm(long pricePerKm) {
        this.pricePerKm = pricePerKm;
    }

    public long getCdw() {
        return cdw;
    }

    public void setCdw(long cdw) {
        this.cdw = cdw;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ArrayList<Vehicle> getVehicles() {
        return vehicles;
    }

    public void setVehicles(ArrayList<Vehicle> vehicles) {
        this.vehicles = vehicles;
    }
}
