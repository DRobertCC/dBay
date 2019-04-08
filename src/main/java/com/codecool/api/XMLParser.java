package com.codecool.api;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

abstract class XMLParser {

    static Document loadXMLDocument(String xmlPath) {

        try {
            File inputFile = new File(xmlPath);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile); // a doc-ban lesz a teljes xml.

            doc.getDocumentElement().normalize(); // a tagok közötti (>...<) text-típusú whitespaceket távolítja el.
            return doc;
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
        return null;
    }

    static void writeXMLDocument(Document doc,  String xmlPath) throws TransformerException, FileNotFoundException {
        TransformerFactory tfFactory = TransformerFactory.newInstance();
        Transformer tf = tfFactory.newTransformer();
        tf.setOutputProperty(OutputKeys.INDENT, "yes"); // Indentator, if "yes": excess blank lines are inserted into the code :(
        doc.getDocumentElement().normalize();

        tf.transform(new DOMSource(doc), new StreamResult(
                new FileOutputStream(xmlPath)));
    }

}

