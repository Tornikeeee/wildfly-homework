package com.tadamia.servlet;

import com.tadamia.Main;
import com.tadamia.Triangle;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import java.io.*;
import java.lang.reflect.Array;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Hashtable;

@WebServlet(name = "getMaxTriangleServlet", urlPatterns = "/get-max-triangle")
public class getMaxTriangleServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp){


        InputStream in = this.getClass().getClassLoader().getResourceAsStream("triangles.xml");
        assert in != null;
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
        if (bufferedReader != null)
            Main.lgg.info("triangles.xml has loaded");

        try {

            XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();
            XMLEventReader reader = xmlInputFactory.createXMLEventReader(bufferedReader);

            resp.setContentType("text/xml");
            resp.setCharacterEncoding("UTF-8");

            ArrayList<Triangle> triangles = new ArrayList<>();
            Triangle triangle = null;

            while (reader.hasNext()){
                XMLEvent nextEvent = reader.nextEvent();

                if (nextEvent.isStartElement()){
                    StartElement startElement = nextEvent.asStartElement();

                    switch (startElement.getName().getLocalPart()){
                        case "triangle":
                            triangle = new Triangle();
                            break;
                        case "a":
                            nextEvent = reader.nextEvent();
                            assert triangle != null;
                            triangle.setA(Double.parseDouble(nextEvent.asCharacters().getData()));
                            break;
                        case "b":
                            nextEvent = reader.nextEvent();
                            assert triangle != null;
                            triangle.setB(Double.parseDouble(nextEvent.asCharacters().getData()));
                            break;
                        case "c":
                            nextEvent = reader.nextEvent();
                            assert triangle != null;
                            triangle.setC(Double.parseDouble(nextEvent.asCharacters().getData()));
                            break;
                    }
                }

                if (nextEvent.isEndElement()) {
                    EndElement endElement = nextEvent.asEndElement();
                    if (endElement.getName().getLocalPart().equals("triangle")) {
                        triangles.add(triangle);
                    }
                }
            }

            triangles.sort(Comparator.reverseOrder());

            JAXBContext context = JAXBContext.newInstance(Triangle.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.marshal(triangles.get(0), resp.getOutputStream());
        } catch (XMLStreamException | IOException | JAXBException e) {
            throw new RuntimeException(e);
        }
    }
}
