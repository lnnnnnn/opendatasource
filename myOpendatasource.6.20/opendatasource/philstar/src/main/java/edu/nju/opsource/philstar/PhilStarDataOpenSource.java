package edu.nju.opsource.philstar;

import java.io.IOException;

import org.jdom2.JDOMException;

import edu.nju.opensource.common.action.OpenDataSource;

public class PhilStarDataOpenSource extends OpenDataSource{
	public PhilStarDataOpenSource(){
		super();
	}
	
	public PhilStarDataOpenSource(String configXml){
		super( configXml );
	}
	
	public static void main(String[] args){
		OpenDataSource ds = new PhilStarDataOpenSource("opensource.xml");
		try {
			ds.run();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
