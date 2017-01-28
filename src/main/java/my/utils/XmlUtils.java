package my.utils;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.InputStream;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;

public class XmlUtils {

    public static String getNamespace(String xml) throws Exception {
        Document xmlDocument = createDocument(xml);
        return getNamespace(xmlDocument.getDocumentElement());
    }

    public static String getNamespace(Node node) {
        return node.getNamespaceURI();
    }

    public static Document createDocument(String xml, boolean shouldBeNamespaceAware) throws Exception {
        try {
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            documentBuilderFactory.setNamespaceAware(shouldBeNamespaceAware);
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();

            try (InputStream inputStream = StringUtils.toInputStream(xml)) {
                return documentBuilder.parse(inputStream);
            }
        } catch (Exception e) {
            throw new Exception(String.format("Failed to create document from xml string. %s", e.getMessage()), e);
        }
    }

    public static Document createDocument(String xml) throws Exception {
        return createDocument(xml, true);
    }

    public static Document copyDocument(Document xmlDocument, boolean shouldBeNamespaceAware) throws Exception {
        try {
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            documentBuilderFactory.setNamespaceAware(shouldBeNamespaceAware);
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();

            Document documentCopy = documentBuilder.newDocument();
            Node rootNodeCopy = documentCopy.importNode(xmlDocument.getDocumentElement(), true);
            documentCopy.appendChild(rootNodeCopy);
            return documentCopy;

        } catch (Exception e) {
            throw new Exception(String.format("Failed to copy document. %s", e.getMessage()), e);
        }
    }

    public static Document copyDocument(Document xmlDocument) throws Exception {
        return copyDocument(xmlDocument, true);
    }

    public static Node getNode(String xml, String element) throws Exception {
        Document xmlDocument = createDocument(xml);
        return getNode(xmlDocument, element);
    }

    public static Node getNode(String xml, String namespace, String element) throws Exception {
        Document xmlDocument = createDocument(xml);
        return getNode(xmlDocument, namespace, element);
    }

    public static Node getNode(Document xmlDocument, String element) throws Exception {
        return getNode(xmlDocument, "*", element);
    }

    public static Node getNode(Document xmlDocument, String namespace, String element) throws Exception {
        NodeList nodes = xmlDocument.getDocumentElement().getElementsByTagNameNS(namespace, element);

        return nodes.getLength() == 0 ? null : nodes.item(0);
    }

    public static NodeList getNodes(Document xmlDocument, String element) throws Exception {
        return getNodes(xmlDocument, "*", element);
    }

    public static NodeList getNodes(Document xmlDocument, String namespace, String element) throws Exception {
        return xmlDocument.getDocumentElement().getElementsByTagNameNS(namespace, element);
    }

    public static String getSingleElementValue(Document xmlDocument, String element) throws Exception {
        Node node = getNode(xmlDocument, element);
        return node.getTextContent();
    }

    public static String getElementXml(String xml, String namespace, String element) throws Exception {
        Document xmlDocument = createDocument(xml);
        Node node = getNode(xmlDocument, namespace, element);
        return toXml(node);
    }

    public static String getElementXml(String xml, String element) throws Exception {
        return getElementXml(xml, "*", element);
    }

    public static String toXml(Node node) throws Exception {
        try {
            StringWriter stringWriter = new StringWriter();

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.METHOD, "xml");
            transformer.setOutputProperty(OutputKeys.ENCODING, StandardCharsets.UTF_8.name());
            transformer.transform(new DOMSource(node), new StreamResult(stringWriter));

            return stringWriter.toString();
        } catch (Exception e) {
            throw new Exception(String.format("Failed to create string from xml document. %s", e.getMessage()), e);
        }
    }

    public static Element getFirstChildElement(Node node) {
        NodeList children = node.getChildNodes();
        for (int index = 0; index < children.getLength(); index++) {
            Node child = children.item(index);
            if (child instanceof Element) {
                return (Element) child;
            }
        }

        return null;
    }
}