/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modele;

import com.mysql.jdbc.PreparedStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Carbon5
 */
public class PlaceDAO extends DAO<Place>{

    public PlaceDAO(Connection conn) {
		super(conn);
		// TODO Auto-generated constructor stub
	}

    @Override
    public boolean delete(Place obj) {
    	try {
            this.connect
                .createStatement(
                    ResultSet.TYPE_SCROLL_INSENSITIVE, 
                    ResultSet.CONCUR_UPDATABLE
               ).executeUpdate(
                    "DELETE FROM place WHERE NumPlace = " + obj.getNumPlace()
               );

        } catch (SQLException e) {
                e.printStackTrace();
        }
        return true;
    }

	@Override
	public boolean create(Place obj) {
		try {
			ResultSet result = this.connect
                    .createStatement(
                     ResultSet.TYPE_SCROLL_INSENSITIVE, 
                     ResultSet.CONCUR_UPDATABLE
                     ).executeQuery(
                     "SELECT NEXTVAL('ud_id_seq') as IdParking"
                     );
			if(result.first()){
				long id = result.getLong("IdParking");
                java.sql.PreparedStatement prepare= 
                		this.connect.prepareStatement("INSERT INTO place (NumPlace, NumPark, IsOccupied , IdParking) VALUES(?, ?, ?, ?)"
                										);
                prepare.setInt(1, obj.getNumPlace());
                prepare.setInt(2, obj.getNumPark());
                prepare.setInt(1, obj.getIsOccupied());
                prepare.setLong	(2, id);

                prepare.executeUpdate();
            }
        } catch (SQLException e) {
                e.printStackTrace();
        }
        return true;
	}

	@Override
	public boolean update(Place obj) {
		try {	
            this .connect	
                 .createStatement(
                    ResultSet.TYPE_SCROLL_INSENSITIVE, 
                    ResultSet.CONCUR_UPDATABLE
                 ).executeUpdate(
                    "UPDATE place SET NumPlace = '" + obj.getNumPlace() + "'"+
                    " WHERE NumPlace = " + obj.getNumPlace()
                 );
        } catch (SQLException e) {
                e.printStackTrace();
        }
        return true;
	}

	@Override
	public Place find() {
		Place ud = new Place();
        try {
            ResultSet result = this .connect
                                    .createStatement(
                                            	ResultSet.TYPE_SCROLL_INSENSITIVE, 
                                                ResultSet.CONCUR_UPDATABLE
                                             ).executeQuery(
                                                "SELECT * FROM place" 
                                             );
            if(result.first())
            		ud = new Place(result.getInt("NumPlace"), result.getInt("NumPark"), result.getInt("IsOccupied")); 
        } catch (SQLException e) {
                e.printStackTrace();
        }
        return ud;
	}
}
