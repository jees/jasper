package net.rs.comet.model.game.entity.mob;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import net.rs.comet.model.game.Game;
import net.rs.comet.model.game.Position;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class MobSpawnLoader {
	
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
	 * Loads the specified mobs from an external XML type file.
	 * 
	 * @throws Exception The exception thrown if an error occurs.
	 */
	public static void loadSpawns() throws Exception {
		documentBuilder = documentBuilderFactory.newDocumentBuilder();
		Document document = documentBuilder.parse(new File("./data/load/Npc-Spawns.xml"));
		document.getDocumentElement().normalize();
		NodeList objects = (NodeList) document.getElementsByTagName("NPC");
		for (int i = 0; i < objects.getLength(); i++) {
			Element npcs = (Element) objects.item(i);
			/*
			 * The index of the non-player character.
			 */
			int npcIdentity = Integer.parseInt(npcs.getAttribute("ID"));
			/*
			 * The positioning and classification element.
			 */
			Element attributes = (Element) npcs.getElementsByTagName("ATTRIBUTES").item(0);
			/*
			 * The X position of the non-player character.
			 */
			int positionX = Integer.parseInt(attributes.getAttribute("X"));
			/*
			 * The Y position of the non-player character.
			 */
			int positionY = Integer.parseInt(attributes.getAttribute("Y"));
			/*
			 * The height level of the non-player character.
			 */
			int height = Integer.parseInt(attributes.getAttribute("Z"));
			/*
			 * Adds the non-player character into the virtual world.
			 */
			Mob mob = new Mob(npcIdentity);
			mob.setPosition(new Position(positionX, positionY, height));
			mob.setUpdateRequired(true);
			Game.getSingleton().registerMob(mob);
		}
	}
}