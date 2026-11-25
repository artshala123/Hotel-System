import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Vleresimi {
    private String autori;
    private int yjet; // 1 deri 5
    private String komenti;
    private String dataPublikimit;

    public Vleresimi(String autori, int yjet, String komenti, String dataPublikimit) {
        this.autori = autori;
        this.yjet = Math.max(1, Math.min(5, yjet));
        this.komenti = komenti;
        this.dataPublikimit = dataPublikimit;
    }

    public Vleresimi(String autori, int yjet, String komenti) {
        this(autori, yjet, komenti, LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
    }

    public String getAutori() { return autori; }
    public int getYjet() { return yjet; }
    public String getKomenti() { return komenti; }
    public String getDataPublikimit() { return dataPublikimit; }

    public String getVleresimiMeFjale() {
        switch (yjet) {
            case 5: return "Shkelqyeshem";
            case 4: return "Shume mire";
            case 3: return "Mire";
            case 2: return "Mjaftueshem";
            default: return "I dobet";
        }
    }
}