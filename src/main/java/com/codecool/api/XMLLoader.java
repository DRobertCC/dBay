package com.codecool.api;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.List;

public class XMLLoader extends XMLParser {


    public XMLLoader() {
    }

    public List<User> getUsers(String path) {
        Document userDoc = loadXMLDocument(path); // userDoc-ban lesz letárolva a teljes XML
        Element users = (Element) userDoc.getElementsByTagName("users").item(0);
        NodeList nList = users.getElementsByTagName("user");
        List<User> result = new ArrayList<>();

        for (int temp = 0; temp < nList.getLength(); temp++) {
            Node nNode = nList.item(temp);

            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element) nNode;

                String username = (eElement.getAttribute("username"));
                String password = (eElement.getElementsByTagName("password").item(0).getTextContent());
                String fullName = (eElement.getElementsByTagName("fullname").item(0).getTextContent());
                String email = (eElement.getElementsByTagName("email").item(0).getTextContent());
                String country = (eElement.getElementsByTagName("country").item(0).getTextContent());
                result.add(new User(username, password, fullName, email, country));
            }
        }
        return result;
    }

    public List<Car> getCars(String path) {
        Document userDoc = loadXMLDocument(path); // userDoc-ban lesz letárolva a teljes XML
        Element users = (Element) userDoc.getElementsByTagName("cars").item(0);
        NodeList nList = users.getElementsByTagName("car");
        List<Car> result = new ArrayList<>();

        for (int temp = 0; temp < nList.getLength(); temp++) {
            Node nNode = nList.item(temp);

            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element) nNode;

                int id = Integer.parseInt(eElement.getAttribute("id"));
                String name = (eElement.getElementsByTagName("name").item(0).getTextContent());
                float price = Float.parseFloat(eElement.getElementsByTagName("price").item(0).getTextContent());
                float enginesize = Float.parseFloat(eElement.getElementsByTagName("enginesize").item(0).getTextContent());
                int numberofdoors = Integer.parseInt(eElement.getElementsByTagName("numberofdoors").item(0).getTextContent());
                TypeOfCarBody typefcarbody = TypeOfCarBody.valueOf(eElement.getElementsByTagName("typefcarbody").item(0).getTextContent());
                boolean ismanual = Boolean.parseBoolean(eElement.getElementsByTagName("ismanual").item(0).getTextContent());
                result.add(new Car(id, name, price, enginesize, numberofdoors, typefcarbody, ismanual));
            }
        }
        return result;
    }

}