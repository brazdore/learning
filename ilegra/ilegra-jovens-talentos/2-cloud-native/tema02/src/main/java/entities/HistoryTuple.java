package entities;

import interfaces.IServices;

import java.util.Objects;

public class HistoryTuple {

    private Long serviceID;
    private Long petID;
    private IServices serviceType;
    private Long instant;

    public HistoryTuple() {
    }

    public HistoryTuple(Long serviceID, Long petID, IServices serviceType) {
        this.serviceID = serviceID;
        this.petID = petID;
        this.serviceType = serviceType;
        this.instant = System.currentTimeMillis();
    }

    public Long getServiceID() {
        return serviceID;
    }

    public Long getPetID() {
        return petID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HistoryTuple that = (HistoryTuple) o;
        return serviceID.equals(that.serviceID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(serviceID);
    }

    @Override
    public String toString() {
        return "{serviceID=" + serviceID +
                ", petID=" + petID +
                ", serviceType=" + serviceType +
                ", instant=" + instant +
                '}';
    }
}
