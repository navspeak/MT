package com.two.signalling.restaurant.nav;

public class Request {
    private final Customer customer;
    private final REQUEST_TYPE requestType;
    private volatile boolean handled = false;

    public Request(Customer customer, REQUEST_TYPE requestType) {
        this.customer = customer;
        this.requestType = requestType;
    }

    public boolean getHandled() {
        return handled;
    }

    public void setHandled(boolean handled) {
        this.handled = handled;
    }

    public Customer getCustomer() {
        return customer;
    }
    public REQUEST_TYPE getRequestType() {
        return requestType;
    }
}
