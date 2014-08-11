package com.jskaleel.fte.parse;

import java.util.Vector;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class XMLHelper extends DefaultHandler {
	public static Vector<PostValue> vecPostValue;
	PostValue postvalue;
	StringBuffer currentString;
	
	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		currentString = new StringBuffer();
		if(localName.equalsIgnoreCase("books"))
		{
			if(vecPostValue !=null && vecPostValue.size()>0)
				vecPostValue.clear();
			else
				vecPostValue=new Vector<PostValue>();
		}
		
		else if(localName.equalsIgnoreCase("book"))
		{
			postvalue=new PostValue();
		}
		
	}
	
	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		if(localName.equalsIgnoreCase("title"))
		{
			postvalue.title=currentString.toString();
		}
		
		else if(localName.equalsIgnoreCase("author"))
		{
			postvalue.author=currentString.toString();
		}
		
		else if(localName.equalsIgnoreCase("image"))
		{
			postvalue.image=currentString.toString();
		}
		
		else if(localName.equalsIgnoreCase("epub"))
		{
			postvalue.epub=currentString.toString();
		}
	
		else if(localName.equalsIgnoreCase("book"))
		{
			vecPostValue.add(postvalue);
		}
	}
	
	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {
		currentString.append(ch, start, length);
	}
	
	public Vector<PostValue> getData()
	{
		return vecPostValue;
	}

}
