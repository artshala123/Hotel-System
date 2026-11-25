import java.awt.Color;
import java.util.ArrayList;

public class SherbimiHotelit {
    private ArrayList<Perdoruesi> perdoruesit;
    private ArrayList<Dhoma> dhomat;

    public SherbimiHotelit() {
        perdoruesit = new ArrayList<>();
        dhomat = new ArrayList<>();
        ngarkoTeDhenatFillestare();
    }

    private void ngarkoTeDhenatFillestare() {
        perdoruesit.add(new Perdoruesi("admin", "admin123", "ADMIN"));
        perdoruesit.add(new Perdoruesi("klient", "1234", "KLIENT"));

        // 1. Dardania Classic Single
        Dhoma d1 = new Dhoma(101, "Dardania Classic Single", 45.0,
                "Wi-Fi • Smart TV • Kondicioner",
                "Dhome e rehatshme dhe e qete ne zemer te Prishtines, me krevat ortopedik, tavoline pune dhe izolim te plote akustik.",
                new Color(30, 64, 175));
        d1.shtoVleresim(new Vleresimi("Driton_B", 4, "Lokacion perfekt afer qendres, dhome e paster dhe staf shume mikprites.", "12/11/2026"));
        d1.shtoVleresim(new Vleresimi("Elena_M", 5, "Cmim shume i arsyeshem per kushtet qe ofron. Do te kthehem perseri!", "18/11/2026"));
        dhomat.add(d1);

        // 2. Ulpiana Comfort Twin
        Dhoma d2 = new Dhoma(102, "Ulpiana Comfort Twin", 65.0,
                "2 Krevate • Wi-Fi 5G • Mengjes",
                "E frymezuar nga historia e Ulpianes antike, ideale per miq ose kolege me dy krevate te ndare, Smart TV 4K dhe mengjes tradicional.",
                new Color(15, 118, 110));
        d2.shtoVleresim(new Vleresimi("Luan_K", 4, "Shume komode per udhetime pune, interneti punonte pa asnje problem.", "05/11/2026"));
        d2.shtoVleresim(new Vleresimi("Sara_H", 3, "Dhome e mire, por parkimi ishte pak i zene ne mbremje.", "20/11/2026"));
        dhomat.add(d2);

        // 3. Arbëria Garden Double
        Dhoma d3 = new Dhoma(103, "Arberia Garden Double", 75.0,
                "Krevat King • Ballkon • Mengjes",
                "Dhome e ndritshme me pamje nga lagjja e gjelber e Arberise, me ballkon privat, aparat kafeje dhe mengjes bufe te perfshire.",
                new Color(4, 120, 87));
        d3.shtoVleresim(new Vleresimi("Besnik_G", 5, "Mengjesi ne ballkon me pamje nga Arberia ishte fantastik!", "10/11/2026"));
        d3.shtoVleresim(new Vleresimi("Rina_S", 5, "Pasterti maksimale dhe krevat shume i rehatshem.", "22/11/2026"));
        dhomat.add(d3);

        // 4. Gërmia Park Deluxe
        Dhoma d4 = new Dhoma(201, "Germia Park Deluxe", 110.0,
                "Pamje nga Germia • Spa • Minibar",
                "Qetesi dhe ajer i paster me pamje drejt Parkut te Germise. Perfshin qasje falas ne Spa, pishine te brendshme dhe minibar.",
                new Color(109, 40, 217));
        d4.shtoVleresim(new Vleresimi("Arta_K", 5, "Relaks i vertete! Spa dhe pishina ishin te mrekullueshme.", "14/11/2026"));
        d4.shtoVleresim(new Vleresimi("Valon_R", 4, "Dhome e madhe dhe elegante, pamja ne mengjes eshte super.", "19/11/2026"));
        d4.shtoVleresim(new Vleresimi("Nora_T", 5, "Sherbimi me i mire ne Prishtine, cdo detaj ishte i kuruar.", "24/11/2026"));
        dhomat.add(d4);

        // 5. Dragodan Sunset Suite
        Dhoma d5 = new Dhoma(202, "Dragodan Sunset Suite", 135.0,
                "Pamje Panoramike • Ballkon • Parking",
                "Shijo perendimin e diellit mbi gjithe qytetin e Prishtines nga tarraca e kesaj suite moderne me sallon te vecante.",
                new Color(180, 83, 9));
        d5.shtoVleresim(new Vleresimi("Faton_P", 5, "Pamja e Prishtines naten nga kjo dhome eshte e pakrahasueshme.", "08/11/2026"));
        d5.shtoVleresim(new Vleresimi("Blerta_D", 4, "Ambient shume romantik dhe modern.", "21/11/2026"));
        dhomat.add(d5);

        // 6. Newborn Business Executive (Ndryshuar nga Rugova në Newborn Prishtina)
        Dhoma d6 = new Dhoma(203, "Newborn Business Executive", 125.0,
                "Zyre Pune • Fiber Optik • Mengjes",
                "E pozicionuar me pamje nga monumenti Newborn ne qender te Prishtines, me kend te dedikuar zyre dhe internet ultra te shpejte.",
                new Color(51, 65, 85));
        d6.shtoVleresim(new Vleresimi("Ilir_Z", 4, "Ideale per pune, tavolina ergonomike dhe qetesi absolute.", "15/11/2026"));
        d6.shtoVleresim(new Vleresimi("Krenar_V", 5, "Sherbim i shpejte ne dhome dhe kafe ekspres shume e mire.", "23/11/2026"));
        dhomat.add(d6);

        // 7. Sheshi Nënë Tereza Family
        Dhoma d7 = new Dhoma(301, "Sheshi Nene Tereza Family", 155.0,
                "2 Dhoma • Kuzhine • Parking VIP",
                "Apartament i bollshem familjar vetem disa hapa larg Bulevardit Nene Tereza, me dy dhoma gjumi dhe kuzhine te kompletuar.",
                new Color(14, 116, 144));
        d7.shtoVleresim(new Vleresimi("Vlora_A", 5, "Perfekte per familje me femije, hapesire e madhe dhe shume e sigurt.", "11/11/2026"));
        d7.shtoVleresim(new Vleresimi("Gzim_L", 4, "Cdo gje afer, sheshi dhe restorantet me te mira jane 2 minuta larg.", "17/11/2026"));
        dhomat.add(d7);

        // 8. Prishtina Royal Presidential
        Dhoma d8 = new Dhoma(302, "Prishtina Royal Presidential", 260.0,
                "Jacuzzi Privat • Sauna • Butler 24/7",
                "Suita mbreterore e hotelit me Jacuzzi privat, sauna personale, sherbim 24 ore dhe transferte VIP nga Aeroporti Adem Jashari.",
                new Color(159, 18, 57));
        d8.shtoVleresim(new Vleresimi("Dardan_H", 5, "Eksperience luksoze ne cdo aspekt, Jacuzzi dhe sherbimi ishin 10/10.", "16/11/2026"));
        d8.shtoVleresim(new Vleresimi("Teuta_B", 5, "Thjesht e mrekullueshme! Ja vlen cdo cent.", "25/11/2026"));
        dhomat.add(d8);

        // 9. Skylight Prishtina Penthouse
        Dhoma d9 = new Dhoma(303, "Skylight Prishtina Penthouse", 320.0,
                "Tarrace 360° • All-Inclusive • VIP",
                "Ne katin e fundit te hotelit me tarrace private 360 grade mbi kryeqytet, pije dhe ushqime te perfshira gjate gjithe dites.",
                new Color(30, 41, 59));
        d9.shtoVleresim(new Vleresimi("Agon_M", 5, "Vendi me ekskluziv ne Prishtine. Tarraca private eshte enderr!", "09/11/2026"));
        d9.shtoVleresim(new Vleresimi("Edona_K", 5, "Sherbimi All-Inclusive dhe pamja e qytetit te lene pa fjale.", "24/11/2026"));
        dhomat.add(d9);
    }

    public Perdoruesi kycu(String username, String password) {
        for (Perdoruesi p : perdoruesit) {
            if (p.getUsername().equalsIgnoreCase(username.trim()) && p.getPassword().equals(password)) {
                return p;
            }
        }
        return null;
    }

    public boolean regjistroKlient(String username, String password) {
        for (Perdoruesi p : perdoruesit) {
            if (p.getUsername().equalsIgnoreCase(username.trim())) {
                return false;
            }
        }
        perdoruesit.add(new Perdoruesi(username.trim(), password, "KLIENT"));
        return true;
    }

    public ArrayList<Dhoma> merrTeGjithaDhomat() { return dhomat; }

    public ArrayList<Dhoma> kerkoDhomat(String teksti, boolean vetemTeLira) {
        ArrayList<Dhoma> rezultatet = new ArrayList<>();
        String kerkimi = teksti.toLowerCase().trim();
        for (Dhoma d : dhomat) {
            if (vetemTeLira && d.isEZene()) continue;
            if (kerkimi.isEmpty() ||
                d.getLloji().toLowerCase().contains(kerkimi) ||
                d.getSherbimet().toLowerCase().contains(kerkimi) ||
                String.valueOf(d.getNumri()).contains(kerkimi)) {
                rezultatet.add(d);
            }
        }
        return rezultatet;
    }

    public void shtoDhome(Dhoma d) {
        dhomat.add(d);
    }

    public void fshiDhome(Dhoma d) {
        dhomat.remove(d);
    }
}