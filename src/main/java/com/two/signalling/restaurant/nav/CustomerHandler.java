package com.two.signalling.restaurant.nav;

public final class CustomerHandler {

    static void handle(Customer customer, REQUEST_TYPE requestType) {
        Request request = new Request(customer, requestType);
        System.out.println(customer.getName() + requestType.getPreRequestMessage());
        placeRequest(request);
        waitForRequest(request);
        System.out.println(customer.getName() + requestType.getPostRequestMessage());
    }

    // wait for Request using request monitor
    // {@link com.two.signalling.restaurant.nav.Waiter#run run}
    private static void waitForRequest(Request request) {
        synchronized (request) {
            while(!request.getHandled()){
                try {
                    request.wait();
                } catch (InterruptedException ex) {
                }
            }
        }
    }

    private static void placeRequest(Request request) {
        BarSimulator.requests.offer(request);
/*        synchronized (BarSimulator.requests) {
            BarSimulator.requests.offer(request);
            BarSimulator.requests.notify();
        }*/
    }
}
