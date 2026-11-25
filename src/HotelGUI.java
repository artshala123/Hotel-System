import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class HotelGUI extends JFrame {
    private SherbimiHotelit sherbimi;
    private Perdoruesi perdoruesiAktual;

    private CardLayout cardLayout;
    private JPanel paneliKryesorMeFaqe;

    private JPanel paneliDhomave;
    private KomponenteModerne.FusheTekstiERrethuar txtKerkimi;
    private JCheckBox chkVetemTeLira;
    private JLabel lblStatistikat;

    private JPanel faqjaDetajeve;
    private int yjetEZgjedhura = 5;

    public HotelGUI(SherbimiHotelit sherbimi, Perdoruesi perdoruesiAktual) {
        this.sherbimi = sherbimi;
        this.perdoruesiAktual = perdoruesiAktual;

        setTitle("Grand Hotel Prishtina - Paneli i " + perdoruesiAktual.getRoli());
        setSize(1020, 740);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(241, 245, 249));

        // 1. Shiriti i sipërm (Header)
        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setBackground(new Color(17, 24, 39)); // Midnight Slate
        topBar.setBorder(new EmptyBorder(10, 20, 10, 20));

        JPanel majtasHeader = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        majtasHeader.setOpaque(false);

        KomponenteModerne.ButonModern btnHome = new KomponenteModerne.ButonModern(
                "Grand Hotel Prishtina", new Color(30, 41, 59), new Color(245, 158, 11), 18);
        btnHome.setIcon(KomponenteModerne.Ikonat.hotel(18, new Color(245, 158, 11)));
        btnHome.setIconTextGap(8);
        btnHome.setFont(new Font("Segoe UI", Font.BOLD, 15));
        btnHome.setToolTipText("Kthehu në faqen kryesore (Home)");
        btnHome.addActionListener(e -> {
            rifreskoDhomat();
            cardLayout.show(paneliKryesorMeFaqe, "LISTA");
        });

        JLabel lblPerdoruesi = new JLabel("|   " + perdoruesiAktual.getUsername() + " (" + perdoruesiAktual.getRoli() + ")");
        lblPerdoruesi.setIcon(KomponenteModerne.Ikonat.perdorues(15, new Color(148, 163, 184)));
        lblPerdoruesi.setIconTextGap(6);
        lblPerdoruesi.setForeground(new Color(203, 213, 225));
        lblPerdoruesi.setFont(new Font("Segoe UI", Font.BOLD, 13));

        majtasHeader.add(btnHome);
        majtasHeader.add(lblPerdoruesi);

        JPanel butonatDjathtas = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 2));
        butonatDjathtas.setOpaque(false);

        KomponenteModerne.ButonModern btnHarta = new KomponenteModerne.ButonModern(
                "Lokacioni", new Color(30, 64, 175), Color.WHITE, 20);
        btnHarta.setIcon(KomponenteModerne.Ikonat.lokacion(16, Color.WHITE));
        btnHarta.setIconTextGap(6);
        btnHarta.addActionListener(e -> new HartaHotelitDialog(this));
        butonatDjathtas.add(btnHarta);

        if (perdoruesiAktual.getRoli().equals("ADMIN")) {
            KomponenteModerne.ButonModern btnShtoDhome = new KomponenteModerne.ButonModern(
                    "+ Shto Dhome", new Color(217, 119, 6), Color.WHITE, 20);
            btnShtoDhome.addActionListener(e -> hapDialogunShtoOseEditoDhome(null));
            butonatDjathtas.add(btnShtoDhome);
        }

        KomponenteModerne.ButonModern btnDil = new KomponenteModerne.ButonModern(
                "Dil (Logout)", new Color(190, 18, 60), Color.WHITE, 20);
        btnDil.addActionListener(e -> {
            dispose();
            new LoginGUI(sherbimi);
        });
        butonatDjathtas.add(btnDil);

        topBar.add(majtasHeader, BorderLayout.WEST);
        topBar.add(butonatDjathtas, BorderLayout.EAST);
        add(topBar, BorderLayout.NORTH);

        // 2. Kontejneri me dy faqe ("LISTA" dhe "DETAJET")
        cardLayout = new CardLayout();
        paneliKryesorMeFaqe = new JPanel(cardLayout);

        // --- FAQJA 1: LISTA E DHOMAVE ---
        JPanel faqjaListes = new JPanel(new BorderLayout());
        faqjaListes.setBackground(new Color(241, 245, 249));

        JPanel shiritiFiltrave = new JPanel(new BorderLayout());
        shiritiFiltrave.setBackground(new Color(30, 41, 59));
        shiritiFiltrave.setBorder(new EmptyBorder(10, 20, 10, 20));

        JPanel majtasFiltra = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        majtasFiltra.setOpaque(false);

        txtKerkimi = new KomponenteModerne.FusheTekstiERrethuar(18, 20);
        txtKerkimi.setBackground(new Color(15, 23, 42));
        txtKerkimi.setForeground(Color.WHITE);
        txtKerkimi.setCaretColor(Color.WHITE);
        txtKerkimi.addActionListener(e -> rifreskoDhomat());

        KomponenteModerne.ButonModern btnKerko = new KomponenteModerne.ButonModern(
                "Kerko", new Color(30, 64, 175), Color.WHITE, 20);
        btnKerko.setIcon(KomponenteModerne.Ikonat.kerko(14, Color.WHITE));
        btnKerko.setIconTextGap(6);
        btnKerko.addActionListener(e -> rifreskoDhomat());

        chkVetemTeLira = new JCheckBox("Vetem dhomat e lira");
        chkVetemTeLira.setOpaque(false);
        chkVetemTeLira.setForeground(new Color(226, 232, 240));
        chkVetemTeLira.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        chkVetemTeLira.setCursor(new Cursor(Cursor.HAND_CURSOR));
        chkVetemTeLira.addActionListener(e -> rifreskoDhomat());

        majtasFiltra.add(txtKerkimi);
        majtasFiltra.add(btnKerko);
        majtasFiltra.add(chkVetemTeLira);

        lblStatistikat = new JLabel();
        lblStatistikat.setForeground(new Color(52, 211, 153));
        lblStatistikat.setFont(new Font("Segoe UI", Font.BOLD, 13));

        shiritiFiltrave.add(majtasFiltra, BorderLayout.WEST);
        shiritiFiltrave.add(lblStatistikat, BorderLayout.EAST);
        faqjaListes.add(shiritiFiltrave, BorderLayout.NORTH);

        paneliDhomave = new JPanel(new GridLayout(0, 3, 18, 18));
        paneliDhomave.setBackground(new Color(241, 245, 249));
        paneliDhomave.setBorder(new EmptyBorder(18, 18, 18, 18));

        JScrollPane scrollLista = new JScrollPane(paneliDhomave);
        scrollLista.setBorder(null);
        scrollLista.getVerticalScrollBar().setUnitIncrement(16);
        faqjaListes.add(scrollLista, BorderLayout.CENTER);

        // --- FAQJA 2: FAQJA E DETAJEVE TË DHOMËS ---
        faqjaDetajeve = new JPanel(new BorderLayout());
        faqjaDetajeve.setBackground(new Color(241, 245, 249));

        paneliKryesorMeFaqe.add(faqjaListes, "LISTA");
        paneliKryesorMeFaqe.add(faqjaDetajeve, "DETAJET");
        add(paneliKryesorMeFaqe, BorderLayout.CENTER);

        rifreskoDhomat();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void hapFaqenEDhomes(Dhoma d) {
        this.yjetEZgjedhura = 5;
        faqjaDetajeve.removeAll();

        JPanel shiritiKthimit = new JPanel(new BorderLayout());
        shiritiKthimit.setBackground(new Color(30, 41, 59));
        shiritiKthimit.setBorder(new EmptyBorder(10, 20, 10, 20));

        KomponenteModerne.ButonModern btnKthehu = new KomponenteModerne.ButonModern(
                "← Kthehu te dhomat", new Color(51, 65, 85), Color.WHITE, 20);
        btnKthehu.addActionListener(e -> {
            rifreskoDhomat();
            cardLayout.show(paneliKryesorMeFaqe, "LISTA");
        });

        JLabel lblRruga = new JLabel("Dhoma #" + d.getNumri() + "  /  " + d.getLloji() + "  (" + d.getMesatarjaFormatuar() + ")");
        lblRruga.setIcon(KomponenteModerne.Ikonat.yll(16, true));
        lblRruga.setIconTextGap(6);
        lblRruga.setForeground(new Color(245, 158, 11));
        lblRruga.setFont(new Font("Segoe UI", Font.BOLD, 14));

        shiritiKthimit.add(btnKthehu, BorderLayout.WEST);
        shiritiKthimit.add(lblRruga, BorderLayout.EAST);
        faqjaDetajeve.add(shiritiKthimit, BorderLayout.NORTH);

        JPanel permbajtja = new JPanel(new GridLayout(1, 2, 20, 0));
        permbajtja.setOpaque(false);
        permbajtja.setBorder(new EmptyBorder(20, 20, 20, 20));

        // --- KOLONA MAJTAS ---
        KomponenteModerne.PanelIRrethuar kolonaMajtas = new KomponenteModerne.PanelIRrethuar(
                new BorderLayout(12, 12), Color.WHITE, 24, new Color(203, 213, 225), false);
        kolonaMajtas.setBorder(new EmptyBorder(18, 18, 18, 18));

        JPanel baneri = new JPanel(new GridBagLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                Color c1 = d.isEZene() ? new Color(100, 116, 139) : d.getNgjyraKartes();
                g2.setPaint(new GradientPaint(0, 0, c1, getWidth(), getHeight(), c1.darker()));
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
                g2.dispose();
            }
        };
        baneri.setOpaque(false);
        baneri.setPreferredSize(new Dimension(350, 125));
        JLabel lblBaner = new JLabel("<html><center><font size='4'>Dhoma #" + d.getNumri() + "</font><br><b><font size='6'>" + d.getLloji() + "</font></b></center></html>");
        lblBaner.setForeground(Color.WHITE);
        baneri.add(lblBaner);

        JPanel infoDetajuar = new JPanel();
        infoDetajuar.setLayout(new BoxLayout(infoDetajuar, BoxLayout.Y_AXIS));
        infoDetajuar.setOpaque(false);

        JLabel lblCmimi = new JLabel("Çmimi: €" + d.getCmimiPerNate() + " / natë");
        lblCmimi.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblCmimi.setForeground(new Color(5, 150, 105));

        JLabel lblYjetMesatare = new JLabel(d.getMesatarjaFormatuar());
        lblYjetMesatare.setIcon(KomponenteModerne.Ikonat.yll(18, true));
        lblYjetMesatare.setIconTextGap(6);
        lblYjetMesatare.setFont(new Font("Segoe UI", Font.BOLD, 15));
        lblYjetMesatare.setForeground(new Color(217, 119, 6));

        JLabel lblTitullPershkrim = new JLabel("Përshkrimi i dhomës:");
        lblTitullPershkrim.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblTitullPershkrim.setForeground(new Color(15, 23, 42));
        lblTitullPershkrim.setBorder(new EmptyBorder(12, 0, 4, 0));

        JTextArea areaPershkrimi = new JTextArea(d.getPershkrimi());
        areaPershkrimi.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        areaPershkrimi.setForeground(new Color(51, 65, 85));
        areaPershkrimi.setLineWrap(true);
        areaPershkrimi.setWrapStyleWord(true);
        areaPershkrimi.setEditable(false);
        areaPershkrimi.setOpaque(false);

        JLabel lblSherbimet = new JLabel("<html><b>Shërbimet e përfshira:</b><br>" + d.getSherbimet() + "</html>");
        lblSherbimet.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lblSherbimet.setForeground(new Color(30, 41, 59));
        lblSherbimet.setBorder(new EmptyBorder(12, 0, 12, 0));

        infoDetajuar.add(lblCmimi);
        infoDetajuar.add(Box.createVerticalStrut(6));
        infoDetajuar.add(lblYjetMesatare);
        infoDetajuar.add(lblTitullPershkrim);
        infoDetajuar.add(areaPershkrimi);
        infoDetajuar.add(lblSherbimet);

        JPanel paneliVeprimevePoshte = krijoPanelinEButonavePerDhomen(d, true);

        kolonaMajtas.add(baneri, BorderLayout.NORTH);
        kolonaMajtas.add(infoDetajuar, BorderLayout.CENTER);
        kolonaMajtas.add(paneliVeprimevePoshte, BorderLayout.SOUTH);

        // --- KOLONA DJATHTAS ---
        KomponenteModerne.PanelIRrethuar kolonaDjathtas = new KomponenteModerne.PanelIRrethuar(
                new BorderLayout(10, 10), Color.WHITE, 24, new Color(203, 213, 225), false);
        kolonaDjathtas.setBorder(new EmptyBorder(18, 18, 18, 18));

        JLabel lblTitullKomentet = new JLabel("Vlerësimet dhe Komentet (" + d.getVleresimet().size() + ")");
        lblTitullKomentet.setIcon(KomponenteModerne.Ikonat.yll(18, true));
        lblTitullKomentet.setIconTextGap(8);
        lblTitullKomentet.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblTitullKomentet.setForeground(new Color(15, 23, 42));

        JPanel listaKomenteve = new JPanel();
        listaKomenteve.setLayout(new BoxLayout(listaKomenteve, BoxLayout.Y_AXIS));
        listaKomenteve.setBackground(new Color(248, 250, 252));
        listaKomenteve.setBorder(new EmptyBorder(8, 8, 8, 8));

        for (Vleresimi v : d.getVleresimet()) {
            KomponenteModerne.PanelIRrethuar kKoment = new KomponenteModerne.PanelIRrethuar(
                    new BorderLayout(4, 4), Color.WHITE, 16, new Color(226, 232, 240), false);
            kKoment.setBorder(new EmptyBorder(10, 12, 10, 12));
            kKoment.setMaximumSize(new Dimension(Integer.MAX_VALUE, 100));

            JPanel lartKoment = new JPanel(new BorderLayout());
            lartKoment.setOpaque(false);

            JLabel lblAut = new JLabel(v.getAutori() + "  •  " + v.getDataPublikimit() + " (" + v.getVleresimiMeFjale() + ")");
            lblAut.setIcon(KomponenteModerne.Ikonat.perdorues(14, new Color(30, 64, 175)));
            lblAut.setIconTextGap(6);
            lblAut.setFont(new Font("Segoe UI", Font.BOLD, 12));
            lblAut.setForeground(new Color(30, 41, 59));

            JPanel panelYjetKomentit = new JPanel(new FlowLayout(FlowLayout.RIGHT, 2, 0));
            panelYjetKomentit.setOpaque(false);
            for (int y = 1; y <= 5; y++) {
                panelYjetKomentit.add(new JLabel(KomponenteModerne.Ikonat.yll(14, y <= v.getYjet())));
            }

            lartKoment.add(lblAut, BorderLayout.WEST);
            lartKoment.add(panelYjetKomentit, BorderLayout.EAST);

            JLabel lblTxt = new JLabel("<html><p style='width:350px;'>" + v.getKomenti() + "</p></html>");
            lblTxt.setFont(new Font("Segoe UI", Font.PLAIN, 12));
            lblTxt.setForeground(new Color(71, 85, 105));

            kKoment.add(lartKoment, BorderLayout.NORTH);
            kKoment.add(lblTxt, BorderLayout.CENTER);
            listaKomenteve.add(kKoment);
            listaKomenteve.add(Box.createVerticalStrut(8));
        }

        JScrollPane scrollKomentet = new JScrollPane(listaKomenteve);
        scrollKomentet.setBorder(null);
        scrollKomentet.getVerticalScrollBar().setUnitIncrement(16);

        // Forma për të lënë vlerësim të ri
        JPanel formaKomentit = new JPanel(new BorderLayout(6, 6));
        formaKomentit.setOpaque(false);
        formaKomentit.setBorder(new EmptyBorder(8, 0, 0, 0));

        JPanel rreshtiYjeve = new JPanel(new FlowLayout(FlowLayout.LEFT, 4, 0));
        rreshtiYjeve.setOpaque(false);
        JLabel lblZgjidhYjet = new JLabel("Lër vlerësimin tënd: ");
        lblZgjidhYjet.setFont(new Font("Segoe UI", Font.BOLD, 13));
        rreshtiYjeve.add(lblZgjidhYjet);

        JButton[] butonatYje = new JButton[5];
        for (int i = 0; i < 5; i++) {
            final int vlera = i + 1;
            JButton btnYll = new JButton(KomponenteModerne.Ikonat.yll(22, true));
            btnYll.setContentAreaFilled(false);
            btnYll.setBorderPainted(false);
            btnYll.setFocusPainted(false);
            btnYll.setMargin(new Insets(0, 1, 0, 1));
            btnYll.setCursor(new Cursor(Cursor.HAND_CURSOR));
            btnYll.addActionListener(e -> {
                yjetEZgjedhura = vlera;
                for (int j = 0; j < 5; j++) {
                    butonatYje[j].setIcon(KomponenteModerne.Ikonat.yll(22, j < vlera));
                }
            });
            butonatYje[i] = btnYll;
            rreshtiYjeve.add(btnYll);
        }

        KomponenteModerne.PanelIRrethuar kornizaTekstit = new KomponenteModerne.PanelIRrethuar(
                new BorderLayout(), new Color(248, 250, 252), 16, new Color(203, 213, 225), false);
        kornizaTekstit.setBorder(new EmptyBorder(4, 8, 4, 8));

        JTextArea txtKomentiRi = new JTextArea(3, 25);
        txtKomentiRi.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        txtKomentiRi.setOpaque(false);
        txtKomentiRi.setLineWrap(true);
        txtKomentiRi.setWrapStyleWord(true);
        kornizaTekstit.add(txtKomentiRi, BorderLayout.CENTER);

        KomponenteModerne.ButonModern btnDergoKoment = new KomponenteModerne.ButonModern(
                "Publico Vlerësimin", new Color(5, 150, 105), Color.WHITE, 20);
        btnDergoKoment.addActionListener(e -> {
            String teksti = txtKomentiRi.getText().trim();
            if (teksti.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Shkruaj nje koment para se ta publikosh!");
                return;
            }
            d.shtoVleresim(new Vleresimi(perdoruesiAktual.getUsername(), yjetEZgjedhura, teksti));
            hapFaqenEDhomes(d);
        });

        formaKomentit.add(rreshtiYjeve, BorderLayout.NORTH);
        formaKomentit.add(kornizaTekstit, BorderLayout.CENTER);
        formaKomentit.add(btnDergoKoment, BorderLayout.SOUTH);

        kolonaDjathtas.add(lblTitullKomentet, BorderLayout.NORTH);
        kolonaDjathtas.add(scrollKomentet, BorderLayout.CENTER);
        kolonaDjathtas.add(formaKomentit, BorderLayout.SOUTH);

        permbajtja.add(kolonaMajtas);
        permbajtja.add(kolonaDjathtas);
        faqjaDetajeve.add(permbajtja, BorderLayout.CENTER);

        faqjaDetajeve.revalidate();
        faqjaDetajeve.repaint();
        cardLayout.show(paneliKryesorMeFaqe, "DETAJET");
    }

    private void rifreskoDhomat() {
        paneliDhomave.removeAll();
        ArrayList<Dhoma> lista = sherbimi.kerkoDhomat(txtKerkimi.getText(), chkVetemTeLira.isSelected());

        for (Dhoma d : lista) {
            paneliDhomave.add(krijoKartenEDhomes(d));
        }

        perditesoStatistikat();
        paneliDhomave.revalidate();
        paneliDhomave.repaint();
    }

    private void perditesoStatistikat() {
        int teLira = 0;
        int teZena = 0;
        double teArdhura = 0;

        for (Dhoma d : sherbimi.merrTeGjithaDhomat()) {
            if (d.isEZene()) {
                teZena++;
                teArdhura += d.llogaritTotalinAktual();
            } else {
                teLira++;
            }
        }

        if (perdoruesiAktual.getRoli().equals("ADMIN")) {
            lblStatistikat.setText("Te lira: " + teLira + "  |  Te zena: " + teZena + "  |  Fitimi aktiv: €" + teArdhura);
        } else {
            lblStatistikat.setText("Dhoma te lira: " + teLira + " nga " + sherbimi.merrTeGjithaDhomat().size());
        }
    }

    private JPanel krijoKartenEDhomes(Dhoma d) {
        KomponenteModerne.PanelIRrethuar karta = new KomponenteModerne.PanelIRrethuar(
                new BorderLayout(8, 8), Color.WHITE, 24, new Color(203, 213, 225), true);
        karta.setBorder(new EmptyBorder(12, 12, 12, 12));
        karta.setCursor(new Cursor(Cursor.HAND_CURSOR));

        JPanel header = new JPanel(new GridBagLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                Color c1 = d.isEZene() ? new Color(100, 116, 139) : d.getNgjyraKartes();
                g2.setPaint(new GradientPaint(0, 0, c1, getWidth(), getHeight(), c1.darker()));
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 18, 18);
                g2.dispose();
            }
        };
        header.setOpaque(false);
        header.setPreferredSize(new Dimension(230, 82));

        JLabel lblNr = new JLabel("<html><center>Dhoma #" + d.getNumri() + "<br><b>" + d.getLloji() + "</b></center></html>");
        lblNr.setForeground(Color.WHITE);
        lblNr.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        header.add(lblNr);

        JPanel detajet = new JPanel(new GridLayout(4, 1, 2, 2));
        detajet.setOpaque(false);
        detajet.setBorder(new EmptyBorder(2, 4, 2, 4));

        JLabel lblCmimi = new JLabel("€" + d.getCmimiPerNate() + " / natë");
        lblCmimi.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblCmimi.setForeground(new Color(15, 23, 42));

        JLabel lblYjet = new JLabel(d.getMesatarjaFormatuar());
        lblYjet.setIcon(KomponenteModerne.Ikonat.yll(14, true));
        lblYjet.setIconTextGap(5);
        lblYjet.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblYjet.setForeground(new Color(217, 119, 6));

        JLabel lblSherbimet = new JLabel(d.getSherbimet());
        lblSherbimet.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        lblSherbimet.setForeground(new Color(100, 116, 139));

        String statusi = d.isEZene()
                ? (perdoruesiAktual.getRoli().equals("ADMIN")
                    ? "Zënë: " + d.getRezervuarNga() + " (" + d.getNumriPersonave() + " pers • " + d.getShtesatERezervimit() + ")"
                    : "E REZERVUAR")
                : "● E LIRË  (Kliko për detaje)";
        JLabel lblStatus = new JLabel(statusi);
        lblStatus.setFont(new Font("Segoe UI", Font.BOLD, 11));
        lblStatus.setForeground(d.isEZene() ? new Color(190, 18, 60) : new Color(5, 150, 105));

        detajet.add(lblCmimi);
        detajet.add(lblYjet);
        detajet.add(lblSherbimet);
        detajet.add(lblStatus);

        MouseAdapter hapFaqenListener = new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                hapFaqenEDhomes(d);
            }
        };
        karta.addMouseListener(hapFaqenListener);
        header.addMouseListener(hapFaqenListener);
        lblNr.addMouseListener(hapFaqenListener);
        detajet.addMouseListener(hapFaqenListener);
        lblCmimi.addMouseListener(hapFaqenListener);
        lblYjet.addMouseListener(hapFaqenListener);
        lblSherbimet.addMouseListener(hapFaqenListener);
        lblStatus.addMouseListener(hapFaqenListener);

        JPanel paneliButonave = krijoPanelinEButonavePerDhomen(d, false);

        karta.add(header, BorderLayout.NORTH);
        karta.add(detajet, BorderLayout.CENTER);
        karta.add(paneliButonave, BorderLayout.SOUTH);
        return karta;
    }

    // Krijon butonat e veprimit si për Klientin ashtu edhe për Adminin (Edito, Fshi, Liro)
    private JPanel krijoPanelinEButonavePerDhomen(Dhoma d, boolean brendaFaqesDetajeve) {
        JPanel paneli = new JPanel(new BorderLayout(6, 0));
        paneli.setOpaque(false);

        if (perdoruesiAktual.getRoli().equals("ADMIN")) {
            JPanel butonatAdmin = new JPanel(new GridLayout(1, d.isEZene() ? 3 : 2, 6, 0));
            butonatAdmin.setOpaque(false);

            // Butoni EDIT
            KomponenteModerne.ButonModern btnEdito = new KomponenteModerne.ButonModern(
                    "Edito", new Color(30, 64, 175), Color.WHITE, 16);
            btnEdito.setIcon(KomponenteModerne.Ikonat.edito(13, Color.WHITE));
            btnEdito.setIconTextGap(5);
            btnEdito.addActionListener(e -> {
                hapDialogunShtoOseEditoDhome(d);
                if (brendaFaqesDetajeve) hapFaqenEDhomes(d);
            });
            butonatAdmin.add(btnEdito);

            // Butoni CHECK-OUT (nëse është e zënë)
            if (d.isEZene()) {
                KomponenteModerne.ButonModern btnLiro = new KomponenteModerne.ButonModern(
                        "Liro", new Color(217, 119, 6), Color.WHITE, 16);
                btnLiro.addActionListener(e -> {
                    d.liro();
                    if (brendaFaqesDetajeve) hapFaqenEDhomes(d);
                    else rifreskoDhomat();
                });
                butonatAdmin.add(btnLiro);
            }

            // Butoni FSHI (Delete)
            KomponenteModerne.ButonModern btnFshi = new KomponenteModerne.ButonModern(
                    "Fshi", new Color(190, 18, 60), Color.WHITE, 16);
            btnFshi.setIcon(KomponenteModerne.Ikonat.fshi(13, Color.WHITE));
            btnFshi.setIconTextGap(5);
            btnFshi.addActionListener(e -> {
                int konfirmimi = JOptionPane.showConfirmDialog(this,
                        "A jeni i sigurt që dëshironi ta fshini dhomën #" + d.getNumri() + " (" + d.getLloji() + ")?",
                        "Konfirmo Fshirjen", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
                if (konfirmimi == JOptionPane.YES_OPTION) {
                    sherbimi.fshiDhome(d);
                    rifreskoDhomat();
                    if (brendaFaqesDetajeve) {
                        cardLayout.show(paneliKryesorMeFaqe, "LISTA");
                    }
                }
            });
            butonatAdmin.add(btnFshi);

            paneli.add(butonatAdmin, BorderLayout.CENTER);
        } else {
            // Butonat e KLIENTIT
            KomponenteModerne.ButonModern btnVeprimi;
            if (!d.isEZene()) {
                btnVeprimi = new KomponenteModerne.ButonModern("Rezervo Tani", new Color(5, 150, 105), Color.WHITE, 18);
                btnVeprimi.addActionListener(e -> {
                    DialogRezervimi dialog = new DialogRezervimi(this, d, perdoruesiAktual.getUsername());
                    if (dialog.isUKonfirmua()) {
                        int netet = dialog.getNetet();
                        int persona = dialog.getPersona();
                        String shtesat = dialog.getPershkrimiShtesave();
                        double totali = dialog.llogaritTotalin(d.getCmimiPerNate());

                        d.rezervo(perdoruesiAktual.getUsername(), netet, persona, shtesat, totali);
                        if (brendaFaqesDetajeve) hapFaqenEDhomes(d);
                        else rifreskoDhomat();

                        DialogRezervimi.shfaqFaturenModerne(this, d, perdoruesiAktual.getUsername(), netet, persona, shtesat, totali);
                    }
                });
            } else if (d.getRezervuarNga().equals(perdoruesiAktual.getUsername())) {
                btnVeprimi = new KomponenteModerne.ButonModern("Anulo Rezervimin", new Color(190, 18, 60), Color.WHITE, 18);
                btnVeprimi.addActionListener(e -> {
                    d.liro();
                    if (brendaFaqesDetajeve) hapFaqenEDhomes(d);
                    else rifreskoDhomat();
                });
            } else {
                btnVeprimi = new KomponenteModerne.ButonModern("E Zënë", new Color(203, 213, 225), Color.DARK_GRAY, 18);
                btnVeprimi.setEnabled(false);
            }
            paneli.add(btnVeprimi, BorderLayout.CENTER);
        }

        if (!brendaFaqesDetajeve) {
            KomponenteModerne.ButonModern btnHap = new KomponenteModerne.ButonModern(
                    "Hap →", new Color(51, 65, 85), Color.WHITE, 18);
            btnHap.addActionListener(e -> hapFaqenEDhomes(d));
            paneli.add(btnHap, BorderLayout.EAST);
        }

        return paneli;
    }

    // Dialog i përbashkët për SHTIMIN e një dhome të re ose EDITIMIN e një dhome ekzistuese
    private void hapDialogunShtoOseEditoDhome(Dhoma dhomaPerEditim) {
        boolean eshteEditim = (dhomaPerEditim != null);

        JTextField txtNr = new JTextField(eshteEditim ? String.valueOf(dhomaPerEditim.getNumri()) : "");
        JTextField txtLloji = new JTextField(eshteEditim ? dhomaPerEditim.getLloji() : "");
        JTextField txtCmimi = new JTextField(eshteEditim ? String.valueOf(dhomaPerEditim.getCmimiPerNate()) : "");
        JTextField txtSherbimet = new JTextField(eshteEditim ? dhomaPerEditim.getSherbimet() : "Wi-Fi • Smart TV • Mengjes");
        JTextArea txtPershkrimi = new JTextArea(eshteEditim ? dhomaPerEditim.getPershkrimi() : "Dhome moderne ne Prishtine me te gjitha kushtet.", 3, 22);
        txtPershkrimi.setLineWrap(true);
        txtPershkrimi.setWrapStyleWord(true);

        JPanel paneliFormes = new JPanel(new GridLayout(0, 1, 4, 4));
        paneliFormes.add(new JLabel("Numri i dhomës:"));
        paneliFormes.add(txtNr);
        paneliFormes.add(new JLabel("Emri / Lloji i dhomës (p.sh. Germia Deluxe):"));
        paneliFormes.add(txtLloji);
        paneliFormes.add(new JLabel("Çmimi për natë (€):"));
        paneliFormes.add(txtCmimi);
        paneliFormes.add(new JLabel("Shërbimet:"));
        paneliFormes.add(txtSherbimet);
        paneliFormes.add(new JLabel("Përshkrimi:"));
        paneliFormes.add(new JScrollPane(txtPershkrimi));

        int rezultati = JOptionPane.showConfirmDialog(this, paneliFormes,
                eshteEditim ? "Edito Dhomën #" + dhomaPerEditim.getNumri() : "Shto Dhomë të Re",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (rezultati == JOptionPane.OK_OPTION) {
            try {
                int nr = Integer.parseInt(txtNr.getText().trim());
                String lloji = txtLloji.getText().trim();
                double cmimi = Double.parseDouble(txtCmimi.getText().trim());
                String sherbimet = txtSherbimet.getText().trim();
                String pershkrimi = txtPershkrimi.getText().trim();

                if (lloji.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Emri i dhomes nuk mund te jete bosh!");
                    return;
                }

                if (eshteEditim) {
                    dhomaPerEditim.setNumri(nr);
                    dhomaPerEditim.setLloji(lloji);
                    dhomaPerEditim.setCmimiPerNate(cmimi);
                    dhomaPerEditim.setSherbimet(sherbimet);
                    dhomaPerEditim.setPershkrimi(pershkrimi);
                } else {
                    Dhoma dRe = new Dhoma(nr, lloji, cmimi, sherbimet, pershkrimi, new Color(30, 64, 175));
                    sherbimi.shtoDhome(dRe);
                }
                rifreskoDhomat();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Numri i dhomes dhe cmimi duhet te jene numra te sakte!", "Gabim", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}