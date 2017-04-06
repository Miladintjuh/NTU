package degoudengids;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.table.DefaultTableModel;

public class Database {

	private Connection connection;
	private Statement statement;
        private Medewerker medewerker;
        private int limiet = 500;
	/**
	 * Hier wordt de database met het programma geconnect.
	 */
	public Database() {
		connection = Datasource.getConnection();
                
		try {
			statement = connection.createStatement();
		}
		catch (SQLException ex) {
			Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	/**
	 * @return Returnt een lijst met alle medewerkers uit de database
	 */
	public DefaultListModel<Medewerker> refreshPersoonQuery() {
		String persoonQuery = "select m.*, c.cnaam " +
                                        "from functie f " +
                                        "JOIN cursus AS c ON c.functie_nr = f.functie_nr " +
                                        "JOIN medewerker m ON m.functie_nr = f.functie_nr " +
                                        "ORDER BY medew_nr " +
                                        "LIMIT " + limiet +";";
		return refreshPersonen(persoonQuery);
	}

        public DefaultListModel<Medewerker> refreshPersoonQuery(String orderBy) {
		String persoonQuery = "select m.*, c.cnaam " +
                                        "from functie f " +
                                        "JOIN cursus AS c ON c.functie_nr = f.functie_nr " +
                                        "JOIN medewerker m ON m.functie_nr = f.functie_nr "
                                        + orderBy + 
                                        " LIMIT " + limiet +";";
		return refreshPersonen(persoonQuery);
	}


	/**
	 * @return Returnt een lijst met medewerkers uit de database kloppend aan het opgegeven filter
	 */
	public DefaultListModel<Medewerker> refreshFilterPersoonQuery(String voornaam) {
		String persoonQuery = "select m.*, c.cnaam " +
                                        "from functie f " +
                                        "JOIN cursus AS c ON c.functie_nr = f.functie_nr " +
                                        "JOIN medewerker m ON m.functie_nr = f.functie_nr " +
                                        "WHERE m.naam LIKE '%" + voornaam + "%' ORDER BY medew_nr " +
                                        "LIMIT " + limiet +";";
		return refreshPersonen(persoonQuery);
	}

        public DefaultListModel<Medewerker> refreshFilterPersoonQuery(String voornaam, String orderBy) {
		String persoonQuery = "select m.*, c.cnaam " +
                                        "from functie f " +
                                        "JOIN cursus AS c ON c.functie_nr = f.functie_nr " +
                                        "JOIN medewerker m ON m.functie_nr = f.functie_nr " + 
                                        "WHERE m.naam LIKE '%" + voornaam + "%' " + orderBy +
                                        " LIMIT " + limiet +";";
		return refreshPersonen(persoonQuery);
	}



	/**
	 * @return lijst met medewerkers, naar de hand van de query uit een methode hierboven
	 */
	private DefaultListModel<Medewerker> refreshPersonen(String query) {
		DefaultListModel<Medewerker> medewerkers = new DefaultListModel();

		try {
			ResultSet result = statement.executeQuery(query);

			while (result.next()) {
				Medewerker m = new Medewerker();
                                m.setMedew_nr(result.getInt("medew_nr"));
                                m.setNaam(result.getString("naam"));
                                m.setAchterNaam(result.getString("achternaam"));
                                m.setEmail(result.getString("email"));
                                m.setWoonplaats(result.getString("woonplaats"));
                                m.setFunctie_nr(result.getString("functie_nr"));
                                m.setCursus_naam(result.getString("cnaam"));
                                medewerkers.addElement(m);
                                
			}
			result.close();
		}
		catch (SQLException ex) {
			Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
		}
		return medewerkers;

    }
        public Medewerker getMedewerker(int medew_nr) {
            try {
                String query = "select m.*, c.cnaam " +
                                "from functie f " +
                                "JOIN cursus AS c ON c.functie_nr = f.functie_nr " +
                                "JOIN medewerker m ON m.functie_nr = f.functie_nr " +
                                "where m.medew_nr =" + medew_nr + ";";
                ResultSet result = statement.executeQuery(query);
                
                for (int i = 0; i < limiet; i++) {
                    
                
              //  while(result.next()) {
                    Medewerker m = new Medewerker();

                    m.setMedew_nr(result.getInt("medew_nr"));
                    m.setNaam(result.getString("naam"));
                    m.setAchterNaam(result.getString("achternaam"));
                    m.setEmail(result.getString("email"));
                    m.setWoonplaats(result.getString("woonplaats"));
                    m.setFunctie_nr(result.getString("functie_nr"));
                    m.setCursus_naam(result.getString("cnaam"));

                    return m;
                }
            }
            catch(SQLException e) {
                System.out.println(e);
            }
            return null;
        }

        /**
         * Aangepaste gegevens van een persoon doorsturen naar de database
        */
	public void aanpassenPersoonGegevens(Medewerker persoon) {
	    try {
		String statement = "UPDATE medewerker SET naam = ?, achternaam = ?, email = ?, woonplaats = ? WHERE medew_nr = " + persoon.getMedew_nr()+ ";";
		PreparedStatement pstat = connection.prepareStatement(statement);

			pstat.setString	(1, persoon.getNaam());
			pstat.setString	(2, persoon.getAchterNaam());
			pstat.setString	(3, persoon.getEmail());
			pstat.setString	(4, persoon.getWoonplaats());

		pstat.executeUpdate();
		pstat.close();
	    }

	    catch(Exception e) {

	    }
	}
 
}

