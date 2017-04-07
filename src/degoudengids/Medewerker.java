package degoudengids;

public class Medewerker {
    private int medw_nr;
    private String naam;
    private String achterNaam;
    private String datum_in_dienst;
    private String email;
    private String functie;
    private String cnaam;

    public String getCursus_naam() {
        return cnaam;
    }

    public void setCursus_naam(String cursus_naam) {
        this.cnaam = cursus_naam;
    }

    public int getMedw_nr() {
        return medw_nr;
    }

    public void setMedw_nr(int medw_nr) {
        this.medw_nr = medw_nr;
    }

    public String getFunctie() {
        return functie;
    }

    public void setFunctie(String functie) {
        this.functie = functie;
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

    public String getDatum_in_dienst() {
        return datum_in_dienst;
    }

    public void setDatum_in_dienst(String datum_in_dienst) {
        this.datum_in_dienst = datum_in_dienst;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


}
