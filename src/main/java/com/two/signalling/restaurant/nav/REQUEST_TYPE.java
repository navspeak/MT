package com.two.signalling.restaurant.nav;

public enum REQUEST_TYPE {
    SEATING(" is requesting to be seated.", " has been seated.", " is seating a customer."),
    ORDER(" is requesting to order.", " has ordered.", " is taking order of a customer."),
    SERVE(" is waiting to be served the meal.", "'s order has arrived.", " is serving food to a customer."),
    BILL(" is requesting the bill.", " has paid the bill.", " is getting the bill for a customer.");

    private String preRequestMessage;
    private String postRequestMessage;
    private String waiterMessage;

    REQUEST_TYPE(String preRequestMessage, String postRequestMessage, String waiterMessage) {
        this.preRequestMessage = preRequestMessage;
        this.postRequestMessage = postRequestMessage;
        this.waiterMessage = waiterMessage;
    }

    public String getPreRequestMessage() {
        return preRequestMessage;
    }

    public String getPostRequestMessage() {
        return postRequestMessage;
    }

    public String getWaiterMessage() {
        return waiterMessage;
    }
}
