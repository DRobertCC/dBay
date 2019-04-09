package com.codecool.api;

import com.codecool.api.enums.TypeOfCarBody;
import com.codecool.api.enums.TypeOfMotorCycle;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.List;

abstract class XMLLoader extends XMLParser {

    static List<User> getUsers(String path) {
        Document userDoc = loadXMLDocument(path); // userDoc-ban lesz letárolva a teljes XML
        Element users = (Element) userDoc.getElementsByTagName("users").item(0);
        NodeList nList = users.getElementsByTagName("user");
        List<User> result = new ArrayList<>();

        for (int temp = 0; temp < nList.getLength(); temp++) {
            Node nNode = nList.item(temp);

            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element) nNode;

                String userName = (eElement.getAttribute("username"));
                String password = (eElement.getElementsByTagName("password").item(0).getTextContent());
                String fullName = (eElement.getElementsByTagName("fullname").item(0).getTextContent());
                String email = (eElement.getElementsByTagName("email").item(0).getTextContent());
                String country = (eElement.getElementsByTagName("country").item(0).getTextContent());
                result.add(new User(userName, password, fullName, email, country));
            }
        }
        return result;
    }

    static List<Car> getCars(String path) {
        Document carDoc = loadXMLDocument(path); // carDoc-ban lesz letárolva a teljes XML
        Element cars = (Element) carDoc.getElementsByTagName("cars").item(0);
        NodeList nList = cars.getElementsByTagName("car");
        List<Car> result = new ArrayList<>();

        for (int temp = 0; temp < nList.getLength(); temp++) {
            Node nNode = nList.item(temp);

            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element) nNode;

                int id = Integer.parseInt(eElement.getAttribute("id"));
                String name = (eElement.getElementsByTagName("name").item(0).getTextContent());
                int yearOfManufacture = Integer.parseInt(eElement.getElementsByTagName("yearofmanufacture").item(0).getTextContent());
                double price = Double.parseDouble(eElement.getElementsByTagName("price").item(0).getTextContent());
                double engineSize = Double.parseDouble(eElement.getElementsByTagName("enginesize").item(0).getTextContent());
                int numberOfDoors = Integer.parseInt(eElement.getElementsByTagName("numberofdoors").item(0).getTextContent());
                TypeOfCarBody typeOfCarBody = TypeOfCarBody.valueOf(eElement.getElementsByTagName("typefcarbody").item(0).getTextContent());
                boolean isManual = Boolean.parseBoolean(eElement.getElementsByTagName("ismanual").item(0).getTextContent());
                String listedBy = (eElement.getElementsByTagName("listedby").item(0).getTextContent());

                result.add(new Car(id, name, yearOfManufacture, price, engineSize, numberOfDoors, typeOfCarBody, isManual, listedBy));
            }
        }
        return result;
    }

    static List<MotorCycle> getMotorCycles(String path) {
        Document motorcycleDoc = loadXMLDocument(path);
        Element motorcycles = (Element) motorcycleDoc.getElementsByTagName("motorcycles").item(0);
        NodeList nList = motorcycles.getElementsByTagName("motorcycle");
        List<MotorCycle> result = new ArrayList<>();

        for (int temp = 0; temp < nList.getLength(); temp++) {
            Node nNode = nList.item(temp);

            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element) nNode;

                int id = Integer.parseInt(eElement.getAttribute("id"));
                String name = (eElement.getElementsByTagName("name").item(0).getTextContent());
                int yearOfManufacture = Integer.parseInt(eElement.getElementsByTagName("yearofmanufacture").item(0).getTextContent());
                double price = Double.parseDouble(eElement.getElementsByTagName("price").item(0).getTextContent());
                double engineSize = Double.parseDouble(eElement.getElementsByTagName("enginesize").item(0).getTextContent());
                TypeOfMotorCycle typeOfMotorCycle = TypeOfMotorCycle.valueOf(eElement.getElementsByTagName("typeofmotorcycle").item(0).getTextContent());
                String listedBy = (eElement.getElementsByTagName("listedby").item(0).getTextContent());

                result.add(new MotorCycle(id, name, yearOfManufacture, price, engineSize, typeOfMotorCycle, listedBy));
            }
        }
        return result;
    }

    static int getnextItemId(String path) {
        Document currentItemIdDoc = loadXMLDocument(path);
        Element dbays = (Element) currentItemIdDoc.getElementsByTagName("dbay").item(0);
        NodeList nList = dbays.getElementsByTagName("ids");

        Node nNode = nList.item(0);
        int currentItemId = -1;

        if (nNode.getNodeType() == Node.ELEMENT_NODE) {
            Element eElement = (Element) nNode;
            currentItemId = Integer.parseInt(eElement.getElementsByTagName("currentitemid").item(0).getTextContent());
        }
        return currentItemId;
    }

}