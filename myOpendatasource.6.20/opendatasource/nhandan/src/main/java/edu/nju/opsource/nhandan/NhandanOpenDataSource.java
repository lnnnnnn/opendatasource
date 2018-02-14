package edu.nju.opsource.nhandan;

import edu.nju.opensource.common.action.OpenDataSource;


public class NhandanOpenDataSource extends OpenDataSource{
	/**
	 * @param configXml
	 */
	/**
	 * @param configXml
	 */
	public NhandanOpenDataSource(String configXml) {
		// TODO Auto-generated constructor stub
		super(configXml);
	}

	public static void main(String[] args){
		NhandanOpenDataSource ds = new NhandanOpenDataSource("opensource.xml");
		try {
			ds.run();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
