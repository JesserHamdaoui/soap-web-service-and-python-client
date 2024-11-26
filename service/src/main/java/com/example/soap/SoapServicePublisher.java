package com.example.soap;

import javax.xml.ws.Endpoint;

public class SoapServicePublisher {
    public static void main(String[] args) {
        // Publish the SOAP service at the specified endpoint
        Endpoint.publish("http://localhost:8080/soapService", new SoapServiceImpl());
        System.out.println("SOAP Service is running at http://localhost:8080/soapService");
    }
}
