package org.dbConnection;

import static org.junit.Assert.*;

import java.sql.SQLException;
import java.util.LinkedList;

import org.junit.Test;
import org.quasar.route.dbConnection.DBConnection;

public class DBConnectionTest {

	@Test
	public void throwExceptionBecauseThereIsNoConnectionToDatabase() {
		DBConnection db = new DBConnection();	
		NullPointerException e = new NullPointerException();
		//without starting a connection with the database it is impossible to request POIs
		Exception exception = assertThrows(NullPointerException.class, () ->  db.getPOI());
		
		assertEquals(e, exception);
	}
	
	public void replicationOfPOIsSuccessfullAfterConnectionToDatabase() {
		DBConnection db = new DBConnection();
		//before starting a connection there are no
		db.start();
		db.getPOI();
		db.close();
		
		assertEquals(32, db.getResult().size());
	}

}
