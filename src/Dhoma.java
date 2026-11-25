import java.awt.Color;
import java.util.ArrayList;

public class Dhoma {
    private int numri;
    private String lloji;
    private double cmimiPerNate;
    private String sherbimet;
    private String pershkrimi;
    private boolean eZene;
    private String rezervuarNga;
    private int netetERezervuara;
    private int numriPersonave;
    private String shtesatERezervimit;
    private double totaliRezervimit;
    private Color ngjyraKartes;
    private ArrayList<Vleresimi> vleresimet;

    public Dhoma(int numri, String lloji, double cmimiPerNate, String sherbimet, String pershkrimi, Color ngjyraKartes) {
        this.numri = numri;
        this.lloji = lloji;
        this.cmimiPerNate = cmimiPerNate;
        this.sherbimet = sherbimet;
        this.pershkrimi = pershkrimi;
        this.eZene = false;
        this.rezervuarNga = "";
        this.netetERezervuara = 0;
        this.numriPersonave = 0;
        this.shtesatERezervimit = "";
        this.totaliRezervimit = 0.0;
        this.ngjyraKartes = ngjyraKartes;
        this.vleresimet = new ArrayList<>();
    }

    public int getNumri() { return numri; }
    public void setNumri(int numri) { this.numri = numri; }

    public String getLloji() { return lloji; }
    public void setLloji(String lloji) { this.lloji = lloji; }

    public double getCmimiPerNate() { return cmimiPerNate; }
    public void setCmimiPerNate(double cmimiPerNate) { this.cmimiPerNate = cmimiPerNate; }

    public String getSherbimet() { return sherbimet; }
    public void setSherbimet(String sherbimet) { this.sherbimet = sherbimet; }

    public String getPershkrimi() { return pershkrimi; }
    public void setPershkrimi(String pershkrimi) { this.pershkrimi = pershkrimi; }

    public boolean isEZene() { return eZene; }
    public String getRezervuarNga() { return rezervuarNga; }
    public int getNetetERezervuara() { return netetERezervuara; }
    public int getNumriPersonave() { return numriPersonave; }
    public String getShtesatERezervimit() { return shtesatERezervimit; }
    public Color getNgjyraKartes() { return ngjyraKartes; }

    public void rezervo(String emriKlientit, int netet, int persona, String shtesat, double totali) {
        this.eZene = true;
        this.rezervuarNga = emriKlientit;
        this.netetERezervuara = netet;
        this.numriPersonave = persona;
        this.shtesatERezervimit = shtesat;
        this.totaliRezervimit = totali;
    }

    public void liro() {
        this.eZene = false;
        this.rezervuarNga = "";
        this.netetERezervuara = 0;
        this.numriPersonave = 0;
        this.shtesatERezervimit = "";
        this.totaliRezervimit = 0.0;
    }

    public double llogaritTotalinAktual() {
        return eZene ? totaliRezervimit : 0.0;
    }

    public ArrayList<Vleresimi> getVleresimet() { return vleresimet; }

    public void shtoVleresim(Vleresimi v) {
        vleresimet.add(0, v);
    }

    public double getMesatarjaYjeve() {
        if (vleresimet.isEmpty()) return 0.0;
        double shuma = 0;
        for (Vleresimi v : vleresimet) {
            shuma += v.getYjet();
        }
        return shuma / vleresimet.size();
    }

    public String getMesatarjaFormatuar() {
        if (vleresimet.isEmpty()) return "Pa vleresime";
        return String.format("%.1f / 5.0  (%d vleresime)", getMesatarjaYjeve(), vleresimet.size());
    }
}