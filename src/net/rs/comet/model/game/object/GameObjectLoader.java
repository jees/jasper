package net.rs.comet.model.game.object;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import net.rs.comet.model.game.Position;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class GameObjectLoader {
	
	/**
	 * Defines the API to obtain DOM Document instances from an XML document.
	 */
	private static DocumentBuilder documentBuilder;
	
	/**
	 * Defines a factory API that enables applications to obtain a parser that
	 * produces DOM object trees from XML documents.
	 */
	private static final DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();

	/**
	 * Loads the specified objects from an external XML type file.
	 * 
	 * @throws Exception The exception thrown if an error occurs.
	 */
	public static void loadObjects() throws Exception {
		documentBuilder = documentBuilderFactory.newDocumentBuilder();
		Document document = documentBuilder.parse(new File("./data/load/Object-Spawns.xml"));
		document.getDocumentElement().normalize();
		NodeList objects = (NodeList) document.getElementsByTagName("OBJECT");
		for (int i = 0; i < objects.getLength(); i++) {
			Element loadedObjects = (Element) objects.item(i);
			/*
			 * The index of the object.
			 */
			int objectIdentity = Integer.parseInt(loadedObjects.getAttribute("ID"));
			/*
			 * The positioning and classification element.
			 */
			Element attributes = (Element) loadedObjects.getElementsByTagName("ATTRIBUTES").item(0);
			/*
			 * The X position of the object.
			 */
			int positionX = Integer.parseInt(attributes.getAttribute("X"));
			/*
			 * The Y position of the object.
			 */
			int positionY = Integer.parseInt(attributes.getAttribute("Y"));
			/*
			 * The numerical rotation of the object.
			 */
			int rotation = Integer.parseInt(attributes.getAttribute("ROTATION"));
			/*
			 * The numerical type of the object.
			 */
			int type = Integer.parseInt(attributes.getAttribute("TYPE"));
			/*
			 * Allocates the new object and adds it to the global list.
			 */
			GameObject object = new GameObject(objectIdentity, type, rotation, new Position(positionX, positionY, 0));
			GameObjectHandler.getSingleton().getObjects().add(object);
		}
	}
}