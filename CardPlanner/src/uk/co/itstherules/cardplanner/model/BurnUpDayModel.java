package uk.co.itstherules.cardplanner.model;

import java.util.Date;

public final class BurnUpDayModel {

    private double addTotal;
    private double subtractTotal;
    private Date date;

    public BurnUpDayModel(Date date) {
        this.date = date;
        this.addTotal = 0.0;
        this.subtractTotal = 0.0;
    }

    public void add(double total) {
        addTotal += total;
    }

    public void subtract(double total) {
        subtractTotal -= total;
    }

    public Date getDate() {
        return date;
    }

    public double getAddTotal() {
        return addTotal;
    }

    public double getSubtractTotal() {
        return subtractTotal;
    }
}
