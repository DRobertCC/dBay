package com.codecool.api;

import com.codecool.cmd.IO;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.transform.TransformerException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.FileNotFoundException;
import java.util.List;

abstract class XMLWriter extends XMLParser {

    static void updateCarsInXML(List<Car> cars, String xmlPath, int lastSavedItemId) {

        Document doc = loadXMLDocument(xmlPath);

//        Element root = doc.getDocumentElement(); // Creating a new "root" element. The above example just uses the current root node from the existing xml and then just appends a new Car element and re-writes the file.
        NodeList carsNodeList = doc.getElementsByTagName("Cars");
        Node carsNode = carsNodeList.item(0);
        Element carsElement = (Element) carsNode;

        boolean areThereNewItems = false;
        for (Car car : cars) {
            if (car.getId() > lastSavedItemId) {
                areThereNewItems = true;

                Element newCar = doc.createElement("Car");
                newCar.setAttribute( "id", Integer.toString( car.getId() ) );
                newCar.appendChild(newCar);

                Element name = doc.createElement("name");
                name.appendChild(doc.createTextNode( car.getName() ));
                newCar.appendChild(name);

                Element price = doc.createElement("price");
                price.appendChild( doc.createTextNode( Double.toString( car.getPrice() ) ) );
                newCar.appendChild(price);

                Element yearOfManufacture = doc.createElement("yearofmanufacture");
                yearOfManufacture.appendChild( doc.createTextNode( Integer.toString( car.getYearOfManufacture()) ) );
                newCar.appendChild(yearOfManufacture);

                Element numberOfDoors = doc.createElement("numberofdoors");
                numberOfDoors.appendChild( doc.createTextNode( Integer.toString( car.getNumberOfDoors()) ) );
                newCar.appendChild(numberOfDoors);

                Element typeOfCarBody = doc.createElement("typefcarbody");
                typeOfCarBody.appendChild( doc.createTextNode( car.getTypeOfCarBody().toString() ) );
                newCar.appendChild(typeOfCarBody);

                Element engineSize = doc.createElement("enginesize");
                engineSize.appendChild( doc.createTextNode( Double.toString( car.getEngineSize()) ) );
                newCar.appendChild(engineSize);

                Element isManual = doc.createElement("ismanual");
                isManual.appendChild( doc.createTextNode( car.getName() ) );
                newCar.appendChild(isManual);

                Element listedBy = doc.createElement("listedby");
                listedBy.appendChild( doc.createTextNode( car.getListedBy() ) );
                newCar.appendChild(listedBy);

                carsElement.appendChild(newCar); // Adding the new "root" element.
            }
        }

        if (areThereNewItems) {
            try {
                writeXMLDocument(doc, xmlPath);
                IO.printMessage("\n Successfully saved data to " + xmlPath);
            } catch (TransformerException | FileNotFoundException e) {
                e.printStackTrace();
                System.exit(1);
            }
        }
    }

    static void updateMotorCyclesInXML(List<MotorCycle> cars, String s, int lastSavedItemId) {
        IO.printMessage("Not implemented yet.");
    }

    public static void updateUserListInXML(List<User> users, String s, int lastSavedUserId) {
        IO.printMessage("Not implemented yet.");
    }

    static void updateCurrentItemIdInXML(String xmlPath, int nextItemId) {

        Document doc = loadXMLDocument(xmlPath);

        XPath xPath = XPathFactory.newInstance().newXPath();
        Node currentItemIdNode = null;
        try {
            currentItemIdNode = (Node) xPath.compile("/dbay/ids/currentitemid").evaluate(doc, XPathConstants.NODE);
        } catch (XPathExpressionException e) {
            e.printStackTrace();
        }
        assert currentItemIdNode != null;
        currentItemIdNode.setTextContent(Integer.toString(nextItemId));

        try {
            writeXMLDocument(doc, xmlPath);
            IO.printMessage("\nSuccessfully saved data to " + xmlPath);
        } catch (TransformerException | FileNotFoundException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
}

