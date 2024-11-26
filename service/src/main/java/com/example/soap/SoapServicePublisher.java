package com.example.soap;

import org.apache.cxf.Bus;
import org.apache.cxf.BusFactory;
import org.apache.cxf.jaxws.EndpointImpl;

public class SoapServicePublisher {
    public static void main(String[] args) {
        // Initialize the CXF Bus
        Bus bus = BusFactory.newInstance().createBus();
        BusFactory.setDefaultBus(bus);

        // Publish the service
        EndpointImpl endpoint = new EndpointImpl(bus, new SoapServiceImpl());
        endpoint.publish("http://localhost:8080/soapService");

        System.out.println("SOAP Service is running at http://localhost:8080/soapService");
    }
}
