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
	 * @return Returnt een lijst met alle personen uit de database
	 */
	public DefaultListModel<Medewerker> refreshMedewerkerQuery() {
		String persoonQuery = "select top(" + limiet + ") m.*, c.naam as cnaam " +
                                        "from functie f " +
                                        "JOIN cursus AS c ON c.functie_nr = f.functie_nr " +
                                        "JOIN medewerker m ON m.functie_nr = f.functie_nr " +
                                        "ORDER BY medew_nr;";
		return refreshMedewerkers(persoonQuery);
	}

        public DefaultListModel<Medewerker> refreshMedewerkerQuery(String orderBy) {
				String persoonQuery = "select top(" + limiet + ") m.*, c.naam as cnaam " +
                                        "from functie f " +
                                        "JOIN cursus AS c ON c.functie_nr = f.functie_nr " +
                                        "JOIN medewerker m ON m.functie_nr = f.functie_nr "
                                        + orderBy + ";";
		return refreshMedewerkers(persoonQuery);
	}

	/**
	 * @return Returnt een lijst met alle webhistorie uit de database
	 */
	public DefaultListModel<Webhistorie> refreshHistorieQuery() {
		String historieQuery = "select top(" + limiet + ") * from webhistorie ;";
		return refreshHistorie(historieQuery);
	}

        public DefaultListModel<Webhistorie> refreshHistorieQuery(String orderBy) {
				String historieQuery = "select top(" + limiet + ") * from webhistorie "
                                        + orderBy + ";";
		return refreshHistorie(historieQuery);
	}        

	/**
	 * @return Returnt een lijst met  personen uit de database kloppend aan het opgegeven filter
	 */
	public DefaultListModel<Medewerker> refreshFilterMedewerkerQuery(String voornaam) {
		String persoonQuery = "select top(" + limiet + ") m.*, c.naam as cnaam " +
                                        "from functie f " +
                                        "JOIN cursus AS c ON c.functie_nr = f.functie_nr " +
                                        "JOIN medewerker m ON m.functie_nr = f.functie_nr " +
                                        "WHERE m.naam LIKE '%" + voornaam + "%' ORDER BY medew_nr ;";
                return refreshMedewerkers(persoonQuery);
	}

        public DefaultListModel<Medewerker> refreshFilterMedewerkerQuery(String voornaam, String orderBy) {
		String persoonQuery = "select m.*, c.naam as cnaam " +
                                        "from functie f " +
                                        "JOIN cursus AS c ON c.functie_nr = f.functie_nr " +
                                        "JOIN medewerker m ON m.functie_nr = f.functie_nr " + 
                                        "WHERE m.naam LIKE '%" + voornaam + "%' " + orderBy +";";
            return refreshMedewerkers(persoonQuery);
	}
        
        /**
	 * @return Returnt een lijst met webhistorie uit de database kloppend aan het opgegeven filter
	 */
	public DefaultListModel<Webhistorie> refreshFilterHistorieQuery(String klant_id) {
		String historieQuery = "select * from webhistorie" + 
                                      " WHERE klant_id LIKE '%" + klant_id + "%' ORDER BY klant_id;";
                return refreshHistorie(historieQuery);
	}
        
	/**
	 * @return lijst met medewerkers, naar de hand van de query uit een methode hierboven
	 */
	private DefaultListModel<Medewerker> refreshMedewerkers(String query) {
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
                String query = "select top(" + limiet + ") m.*, c.naam as cnaam " +
                                "from functie f " +
                                "JOIN cursus AS c ON c.functie_nr = f.functie_nr " +
                                "JOIN medewerker m ON m.functie_nr = f.functie_nr " +
                                "where m.medew_nr =" + medew_nr + ";";
                ResultSet result = statement.executeQuery(query);
                while(result.next()) {
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
	 * @return lijst met webhistorie, naar de hand van de query uit een methode hierboven
	 */
	private DefaultListModel<Webhistorie> refreshHistorie(String query) {
		DefaultListModel<Webhistorie> webhistorie = new DefaultListModel();

		try {
			ResultSet result = statement.executeQuery(query);

			while (result.next()) {
                                Webhistorie w = new Webhistorie();
                                w.setTitel_cd(result.getString("titel_cd"));
                                w.setKlant_id(result.getString("klant_id"));
                                w.setJaar(result.getString("jaar"));
                                w.setMaand(result.getString("maand"));
                                w.setN_bezoek(result.getString("n_bezoek"));
                                w.setN_post(result.getString("n_post"));
                                w.setN_koop(result.getString("n_koop"));
                                w.setN_retour(result.getString("n_retour"));
                                w.setB_koop(result.getString("b_koop"));
                                w.setB_retour(result.getString("b_retour"));
                                w.setTitel_cd(result.getString("titel_cd"));
                                webhistorie.addElement(w);
                                
			}
			result.close();
		}
		catch (SQLException ex) {
			Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
		}
		return webhistorie;

    }
        public Webhistorie getHistorie(int klant_id) {
            try {
                String query = "select top(" + limiet + ")  * from webhistorie" +
                                "where klant_id =" + klant_id + ";";
                ResultSet result = statement.executeQuery(query);
                while(result.next()) {
                                Webhistorie w = new Webhistorie();
                                w.setTitel_cd(result.getString("titel_cd"));
                                w.setKlant_id(result.getString("klant_id"));
                                w.setJaar(result.getString("jaar"));
                                w.setMaand(result.getString("maand"));
                                w.setN_bezoek(result.getString("n_bezoek"));
                                w.setN_post(result.getString("n_post"));
                                w.setN_koop(result.getString("n_koop"));
                                w.setN_retour(result.getString("n_retour"));
                                w.setB_koop(result.getString("b_koop"));
                                w.setB_retour(result.getString("b_retour"));
                                w.setTitel_cd(result.getString("titel_cd"));
                    return w;
                }
            }
            catch(SQLException e) {
                System.out.println(e);
            }
            return null;
        }        
        
        /**
         * Aangepaste gegevens van een medewerker doorsturen naar de database
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

