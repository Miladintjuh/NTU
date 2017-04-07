package degoudengids;

public class Medewerker {
        private int medew_nr;
	private String naam;
	private String achterNaam;
	private String woonplaats;
	private String email;
        private String functie_nr;
        private String cnaam;

    public String getCursus_naam() {
        return cnaam;
    }

    public void setCursus_naam(String cursus_naam) {
        this.cnaam = cursus_naam;
    }
        
    public int getMedew_nr() {
        return medew_nr;
    }

    public String getFunctie_nr() {
        return functie_nr;
    }

    public void setFunctie_nr(String functie_nr) {
        this.functie_nr = functie_nr;
    }

    public void setMedew_nr(int medew_nr) {
        this.medew_nr = medew_nr;
    }

    public String getNaam() {
        return naam;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public String getAchterNaam() {
        return achterNaam;
    }

    public void setAchterNaam(String achterNaam) {
        this.achterNaam = achterNaam;
    }

    public String getWoonplaats() {
        return woonplaats;
    }

    public void setWoonplaats(String woonplaats) {
        this.woonplaats = woonplaats;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
	


}
