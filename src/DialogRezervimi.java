import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;

public class DialogRezervimi extends JDialog {
    private int netet = 1;
    private int persona = 1;
    private boolean uKonfirmua = false;

    private JLabel lblNumriNeteve;
    private JLabel lblNumriPersonave;
    private JLabel lblTotaliLive;

    private JCheckBox chkMengjes;
    private JCheckBox chkDarke;
    private JCheckBox chkSpa;
    private JCheckBox chkParking;
    private KomponenteModerne.FusheTekstiERrethuar txtKerkesaTjera;

    private final double CMIMI_MENGJES = 10.0; // për person / natë
    private final double CMIMI_DARKE = 18.0;   // për person / natë
    private final double CMIMI_SPA = 15.0;     // për person / natë
    private final double CMIMI_PARKING = 8.0;  // për natë

    public DialogRezervimi(JFrame prindi, Dhoma dhoma, String emriKlientit) {
        super(prindi, "Rezervo Dhomën #" + dhoma.getNumri(), true);
        setSize(490, 610);
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(17, 24, 39)); // Midnight Slate

        // 1. Header
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(new Color(17, 24, 39));
        header.setBorder(new EmptyBorder(16, 22, 8, 22));

        JLabel lblTitulli = new JLabel("Rezervim i Ri • Dhoma #" + dhoma.getNumri());
        lblTitulli.setIcon(KomponenteModerne.Ikonat.hotel(18, new Color(245, 158, 11)));
        lblTitulli.setIconTextGap(8);
        lblTitulli.setForeground(new Color(245, 158, 11));
        lblTitulli.setFont(new Font("Segoe UI", Font.BOLD, 16));

        JLabel lblNentitulli = new JLabel(dhoma.getLloji() + "  (€" + dhoma.getCmimiPerNate() + " / natë)");
        lblNentitulli.setForeground(new Color(148, 163, 184));
        lblNentitulli.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblNentitulli.setBorder(new EmptyBorder(4, 26, 0, 0));

        header.add(lblTitulli, BorderLayout.NORTH);
        header.add(lblNentitulli, BorderLayout.SOUTH);
        add(header, BorderLayout.NORTH);

        // 2. Karta Qendrore e Rrethuar
        JPanel qendraWrapper = new JPanel(new BorderLayout());
        qendraWrapper.setOpaque(false);
        qendraWrapper.setBorder(new EmptyBorder(4, 20, 8, 20));

        KomponenteModerne.PanelIRrethuar karta = new KomponenteModerne.PanelIRrethuar(
                new BorderLayout(10, 12), new Color(30, 41, 59), 24, new Color(51, 65, 85), false);
        karta.setBorder(new EmptyBorder(16, 18, 16, 18));

        // Pjesa e sipërme e kartës: Kohëzgjatja (Netët) dhe Numri i Personave
        JPanel paneliNumrave = new JPanel(new GridLayout(2, 1, 8, 10));
        paneliNumrave.setOpaque(false);

        // Rreshti 1: Kohëzgjatja (Netët)
        JPanel rreshtiNeteve = new JPanel(new BorderLayout());
        rreshtiNeteve.setOpaque(false);

        JLabel lblPyetjaNete = new JLabel("Kohëzgjatja (Netët):");
        lblPyetjaNete.setForeground(new Color(226, 232, 240));
        lblPyetjaNete.setFont(new Font("Segoe UI", Font.BOLD, 14));

        JPanel kontrolletNete = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        kontrolletNete.setOpaque(false);

        KomponenteModerne.ButonModern btnMinusNete = krijoButonVogel("−");
        KomponenteModerne.PanelIRrethuar kutiaNete = krijoKutiVlere();
        lblNumriNeteve = new JLabel("1 natë");
        lblNumriNeteve.setForeground(Color.WHITE);
        lblNumriNeteve.setFont(new Font("Segoe UI", Font.BOLD, 14));
        kutiaNete.add(lblNumriNeteve);
        KomponenteModerne.ButonModern btnPlusNete = krijoButonVogel("+");

        btnMinusNete.addActionListener(e -> {
            if (netet > 1) { netet--; perditesoPamjen(dhoma.getCmimiPerNate()); }
        });
        btnPlusNete.addActionListener(e -> {
            if (netet < 60) { netet++; perditesoPamjen(dhoma.getCmimiPerNate()); }
        });

        kontrolletNete.add(btnMinusNete);
        kontrolletNete.add(kutiaNete);
        kontrolletNete.add(btnPlusNete);

        rreshtiNeteve.add(lblPyetjaNete, BorderLayout.WEST);
        rreshtiNeteve.add(kontrolletNete, BorderLayout.EAST);

        // Rreshti 2: Sa veta do të rrijnë
        JPanel rreshtiPersonave = new JPanel(new BorderLayout());
        rreshtiPersonave.setOpaque(false);

        JLabel lblPyetjaPersona = new JLabel("Numri i personave:");
        lblPyetjaPersona.setIcon(KomponenteModerne.Ikonat.perdorues(15, new Color(245, 158, 11)));
        lblPyetjaPersona.setIconTextGap(6);
        lblPyetjaPersona.setForeground(new Color(226, 232, 240));
        lblPyetjaPersona.setFont(new Font("Segoe UI", Font.BOLD, 14));

        JPanel kontrolletPersona = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        kontrolletPersona.setOpaque(false);

        KomponenteModerne.ButonModern btnMinusPersona = krijoButonVogel("−");
        KomponenteModerne.PanelIRrethuar kutiaPersona = krijoKutiVlere();
        lblNumriPersonave = new JLabel("1 person");
        lblNumriPersonave.setForeground(Color.WHITE);
        lblNumriPersonave.setFont(new Font("Segoe UI", Font.BOLD, 14));
        kutiaPersona.add(lblNumriPersonave);
        KomponenteModerne.ButonModern btnPlusPersona = krijoButonVogel("+");

        btnMinusPersona.addActionListener(e -> {
            if (persona > 1) { persona--; perditesoPamjen(dhoma.getCmimiPerNate()); }
        });
        btnPlusPersona.addActionListener(e -> {
            if (persona < 10) { persona++; perditesoPamjen(dhoma.getCmimiPerNate()); }
        });

        kontrolletPersona.add(btnMinusPersona);
        kontrolletPersona.add(kutiaPersona);
        kontrolletPersona.add(btnPlusPersona);

        rreshtiPersonave.add(lblPyetjaPersona, BorderLayout.WEST);
        rreshtiPersonave.add(kontrolletPersona, BorderLayout.EAST);

        paneliNumrave.add(rreshtiNeteve);
        paneliNumrave.add(rreshtiPersonave);

        // Pjesa e mesit: Shtesat (Mëngjes, Darkë, Spa, Parking + Kërkesa të tjera)
        JPanel paneliShtesave = new JPanel(new BorderLayout(6, 6));
        paneliShtesave.setOpaque(false);
        paneliShtesave.setBorder(new EmptyBorder(6, 0, 6, 0));

        JLabel lblTitullShtesa = new JLabel("Shërbime shtesë (Opsionale):");
        lblTitullShtesa.setForeground(new Color(245, 158, 11));
        lblTitullShtesa.setFont(new Font("Segoe UI", Font.BOLD, 13));

        JPanel gridCheckboxes = new JPanel(new GridLayout(2, 2, 6, 6));
        gridCheckboxes.setOpaque(false);

        chkMengjes = krijoCheckBoxShtese("Mëngjes Bufe (+€10/pers)");
        chkDarke = krijoCheckBoxShtese("Darkë Tradicionale (+€18/pers)");
        chkSpa = krijoCheckBoxShtese("Spa & Pishinë (+€15/pers)");
        chkParking = krijoCheckBoxShtese("Parking VIP (+€8/natë)");

        chkMengjes.addActionListener(e -> perditesoPamjen(dhoma.getCmimiPerNate()));
        chkDarke.addActionListener(e -> perditesoPamjen(dhoma.getCmimiPerNate()));
        chkSpa.addActionListener(e -> perditesoPamjen(dhoma.getCmimiPerNate()));
        chkParking.addActionListener(e -> perditesoPamjen(dhoma.getCmimiPerNate()));

        gridCheckboxes.add(chkMengjes);
        gridCheckboxes.add(chkDarke);
        gridCheckboxes.add(chkSpa);
        gridCheckboxes.add(chkParking);

        JPanel paneliKerkeses = new JPanel(new BorderLayout(4, 4));
        paneliKerkeses.setOpaque(false);
        paneliKerkeses.setBorder(new EmptyBorder(6, 0, 0, 0));

        JLabel lblKerkese = new JLabel("Kërkesa të tjera shtesë (p.sh. krevat për fëmijë, transfer aeroporti):");
        lblKerkese.setForeground(new Color(148, 163, 184));
        lblKerkese.setFont(new Font("Segoe UI", Font.PLAIN, 12));

        txtKerkesaTjera = new KomponenteModerne.FusheTekstiERrethuar(20, 16);
        txtKerkesaTjera.setBackground(new Color(15, 23, 42));
        txtKerkesaTjera.setForeground(Color.WHITE);
        txtKerkesaTjera.setCaretColor(Color.WHITE);

        paneliKerkeses.add(lblKerkese, BorderLayout.NORTH);
        paneliKerkeses.add(txtKerkesaTjera, BorderLayout.CENTER);

        paneliShtesave.add(lblTitullShtesa, BorderLayout.NORTH);
        paneliShtesave.add(gridCheckboxes, BorderLayout.CENTER);
        paneliShtesave.add(paneliKerkeses, BorderLayout.SOUTH);

        // Pjesa e poshtme e kartës: Totali Live
        KomponenteModerne.PanelIRrethuar kutiaTotalit = new KomponenteModerne.PanelIRrethuar(
                new GridBagLayout(), new Color(15, 23, 42), 18, new Color(5, 150, 105), false);
        kutiaTotalit.setPreferredSize(new Dimension(380, 46));

        lblTotaliLive = new JLabel("Totali për pagesë: €" + dhoma.getCmimiPerNate());
        lblTotaliLive.setForeground(new Color(52, 211, 153));
        lblTotaliLive.setFont(new Font("Segoe UI", Font.BOLD, 16));
        kutiaTotalit.add(lblTotaliLive);

        karta.add(paneliNumrave, BorderLayout.NORTH);
        karta.add(paneliShtesave, BorderLayout.CENTER);
        karta.add(kutiaTotalit, BorderLayout.SOUTH);

        qendraWrapper.add(karta, BorderLayout.CENTER);
        add(qendraWrapper, BorderLayout.CENTER);

        // 3. Butonat poshtë
        JPanel bottomPanel = new JPanel(new GridLayout(1, 2, 12, 0));
        bottomPanel.setBackground(new Color(17, 24, 39));
        bottomPanel.setBorder(new EmptyBorder(6, 20, 18, 20));

        KomponenteModerne.ButonModern btnKonfirmo = new KomponenteModerne.ButonModern(
                "Konfirmo Rezervimin ✓", new Color(5, 150, 105), Color.WHITE, 20);
        btnKonfirmo.addActionListener(e -> {
            uKonfirmua = true;
            dispose();
        });

        KomponenteModerne.ButonModern btnAnulo = new KomponenteModerne.ButonModern(
                "Anulo", new Color(190, 18, 60), Color.WHITE, 20);
        btnAnulo.addActionListener(e -> {
            uKonfirmua = false;
            dispose();
        });

        bottomPanel.add(btnKonfirmo);
        bottomPanel.add(btnAnulo);
        add(bottomPanel, BorderLayout.SOUTH);

        setLocationRelativeTo(prindi);
        setVisible(true);
    }

    private KomponenteModerne.ButonModern krijoButonVogel(String teksti) {
        KomponenteModerne.ButonModern btn = new KomponenteModerne.ButonModern(
                teksti, new Color(51, 65, 85), Color.WHITE, 14);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 15));
        btn.setPreferredSize(new Dimension(40, 34));
        return btn;
    }

    private KomponenteModerne.PanelIRrethuar krijoKutiVlere() {
        KomponenteModerne.PanelIRrethuar kutia = new KomponenteModerne.PanelIRrethuar(
                new GridBagLayout(), new Color(15, 23, 42), 14, new Color(245, 158, 11), false);
        kutia.setPreferredSize(new Dimension(105, 36));
        return kutia;
    }

    private JCheckBox krijoCheckBoxShtese(String teksti) {
        JCheckBox chk = new JCheckBox(teksti);
        chk.setOpaque(false);
        chk.setForeground(new Color(226, 232, 240));
        chk.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        chk.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return chk;
    }

    private void perditesoPamjen(double cmimiDhomes) {
        lblNumriNeteve.setText(netet + (netet == 1 ? " natë" : " netë"));
        lblNumriPersonave.setText(persona + (persona == 1 ? " person" : " persona"));
        double totali = llogaritTotalin(cmimiDhomes);
        lblTotaliLive.setText(String.format("Totali për pagesë: €%.1f", totali));
    }

    public double llogaritTotalin(double cmimiDhomes) {
        double kostoDhomes = cmimiDhomes * netet;
        double kostoShtesave = 0;
        if (chkMengjes.isSelected()) kostoShtesave += CMIMI_MENGJES * persona * netet;
        if (chkDarke.isSelected()) kostoShtesave += CMIMI_DARKE * persona * netet;
        if (chkSpa.isSelected()) kostoShtesave += CMIMI_SPA * persona * netet;
        if (chkParking.isSelected()) kostoShtesave += CMIMI_PARKING * netet;
        return kostoDhomes + kostoShtesave;
    }

    public String getPershkrimiShtesave() {
        ArrayList<String> lista = new ArrayList<>();
        if (chkMengjes.isSelected()) lista.add("Mëngjes");
        if (chkDarke.isSelected()) lista.add("Darkë");
        if (chkSpa.isSelected()) lista.add("Spa & Pishinë");
        if (chkParking.isSelected()) lista.add("Parking VIP");

        String tekstiLire = txtKerkesaTjera.getText().trim();
        if (!tekstiLire.isEmpty()) {
            lista.add(tekstiLire);
        }

        return lista.isEmpty() ? "Asnjë shtesë" : String.join(", ", lista);
    }

    public boolean isUKonfirmua() { return uKonfirmua; }
    public int getNetet() { return netet; }
    public int getPersona() { return persona; }

    // Dritarja moderne e Faturës së Rezervimit
    public static void shfaqFaturenModerne(JFrame prindi, Dhoma d, String klienti, int netet, int persona, String shtesat, double totali) {
        JDialog dFatura = new JDialog(prindi, "Fatura e Rezervimit", true);
        dFatura.setSize(450, 410);
        dFatura.setLayout(new BorderLayout());
        dFatura.getContentPane().setBackground(new Color(17, 24, 39));

        JPanel wrapper = new JPanel(new BorderLayout(12, 12));
        wrapper.setOpaque(false);
        wrapper.setBorder(new EmptyBorder(18, 20, 18, 20));

        KomponenteModerne.PanelIRrethuar kartaFatures = new KomponenteModerne.PanelIRrethuar(
                new BorderLayout(8, 10), new Color(30, 41, 59), 24, new Color(5, 150, 105), false);
        kartaFatures.setBorder(new EmptyBorder(18, 20, 18, 20));

        JLabel lblTitull = new JLabel("REZERVIMI U KONFIRMUA ✓", SwingConstants.CENTER);
        lblTitull.setForeground(new Color(52, 211, 153));
        lblTitull.setFont(new Font("Segoe UI", Font.BOLD, 16));

        JLabel lblDetajet = new JLabel("<html><div style='color:#E2E8F0; font-size:12px; line-height:1.65;'>" +
                "<b>Hoteli:</b> Grand Hotel Prishtina<br>" +
                "<b>Klienti:</b> " + klienti + "<br>" +
                "<b>Dhoma:</b> #" + d.getNumri() + " (" + d.getLloji() + ")<br>" +
                "<b>Kohëzgjatja:</b> " + netet + (netet == 1 ? " natë" : " netë") + "  •  " + persona + (persona == 1 ? " person" : " persona") + "<br>" +
                "<b>Shtesat:</b> " + shtesat + "<br>" +
                "<hr color='#475569'>" +
                "<span style='color:#F59E0B; font-size:14px;'><b>TOTALI PËR PAGESË: €" + String.format("%.1f", totali) + "</b></span>" +
                "</div></html>");

        kartaFatures.add(lblTitull, BorderLayout.NORTH);
        kartaFatures.add(lblDetajet, BorderLayout.CENTER);

        KomponenteModerne.ButonModern btnMbyll = new KomponenteModerne.ButonModern(
                "Në rregull", new Color(5, 150, 105), Color.WHITE, 20);
        btnMbyll.addActionListener(e -> dFatura.dispose());

        wrapper.add(kartaFatures, BorderLayout.CENTER);
        wrapper.add(btnMbyll, BorderLayout.SOUTH);

        dFatura.add(wrapper);
        dFatura.setLocationRelativeTo(prindi);
        dFatura.setVisible(true);
    }
}