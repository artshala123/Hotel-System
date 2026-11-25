import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class LoginGUI extends JFrame {
    private SherbimiHotelit sherbimi;
    private KomponenteModerne.FusheTekstiERrethuar txtUser;
    private KomponenteModerne.FusheFjalekalimiERrethuar txtPass;

    public LoginGUI(SherbimiHotelit sherbimi) {
        this.sherbimi = sherbimi;

        setTitle("Grand Hotel Prishtina - Kyçja në Sistem");
        setSize(460, 470);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridBagLayout());
        getContentPane().setBackground(new Color(17, 24, 39)); // Midnight Slate

        KomponenteModerne.PanelIRrethuar kartaLogin = new KomponenteModerne.PanelIRrethuar(
                new BorderLayout(12, 14), new Color(30, 41, 59), 28, new Color(51, 65, 85), false);
        kartaLogin.setPreferredSize(new Dimension(380, 390));
        kartaLogin.setBorder(new EmptyBorder(26, 30, 26, 30));

        // Header
        JPanel header = new JPanel(new GridLayout(2, 1, 4, 4));
        header.setOpaque(false);

        JLabel lblTitulli = new JLabel("GRAND HOTEL PRISHTINA", SwingConstants.CENTER);
        lblTitulli.setIcon(KomponenteModerne.Ikonat.hotel(22, new Color(245, 158, 11)));
        lblTitulli.setIconTextGap(10);
        lblTitulli.setForeground(new Color(245, 158, 11));
        lblTitulli.setFont(new Font("Segoe UI", Font.BOLD, 19));

        JLabel lblInfo = new JLabel("Admin: admin / admin123   •   Klient: klient / 1234", SwingConstants.CENTER);
        lblInfo.setForeground(new Color(148, 163, 184));
        lblInfo.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        header.add(lblTitulli);
        header.add(lblInfo);

        // Fushat e rrethuara
        JPanel forma = new JPanel(new GridLayout(4, 1, 4, 6));
        forma.setOpaque(false);

        JLabel lblUser = new JLabel("Emri i përdoruesit (Username)");
        lblUser.setForeground(new Color(226, 232, 240));
        lblUser.setFont(new Font("Segoe UI", Font.BOLD, 12));

        txtUser = new KomponenteModerne.FusheTekstiERrethuar(18, 18);
        txtUser.setBackground(new Color(15, 23, 42));
        txtUser.setForeground(Color.WHITE);
        txtUser.setCaretColor(Color.WHITE);

        JLabel lblPass = new JLabel("Fjalëkalimi (Password)");
        lblPass.setForeground(new Color(226, 232, 240));
        lblPass.setFont(new Font("Segoe UI", Font.BOLD, 12));

        txtPass = new KomponenteModerne.FusheFjalekalimiERrethuar(18, 18);
        txtPass.setBackground(new Color(15, 23, 42));
        txtPass.setForeground(Color.WHITE);
        txtPass.setCaretColor(Color.WHITE);

        forma.add(lblUser);
        forma.add(txtUser);
        forma.add(lblPass);
        forma.add(txtPass);

        // Butonat modernë
        JPanel butonat = new JPanel(new GridLayout(2, 1, 8, 8));
        butonat.setOpaque(false);

        KomponenteModerne.ButonModern btnLogin = new KomponenteModerne.ButonModern(
                "Hyr në Sistem →", new Color(5, 150, 105), Color.WHITE, 20);
        KomponenteModerne.ButonModern btnRegister = new KomponenteModerne.ButonModern(
                "Krijo Llogari të Re (Klient)", new Color(51, 65, 85), Color.WHITE, 20);

        btnLogin.addActionListener(e -> procesoLogin());
        txtPass.addActionListener(e -> procesoLogin());
        btnRegister.addActionListener(e -> procesoRegister());

        butonat.add(btnLogin);
        butonat.add(btnRegister);

        kartaLogin.add(header, BorderLayout.NORTH);
        kartaLogin.add(forma, BorderLayout.CENTER);
        kartaLogin.add(butonat, BorderLayout.SOUTH);

        add(kartaLogin);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void procesoLogin() {
        String user = txtUser.getText();
        String pass = new String(txtPass.getPassword());
        Perdoruesi p = sherbimi.kycu(user, pass);

        if (p != null) {
            dispose();
            new HotelGUI(sherbimi, p);
        } else {
            JOptionPane.showMessageDialog(this, "Kredenciale te gabuara! Provo perseri.", "Gabim", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void procesoRegister() {
        String user = txtUser.getText().trim();
        String pass = new String(txtPass.getPassword()).trim();
        if (user.isEmpty() || pass.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ploteso username dhe password!");
            return;
        }
        if (sherbimi.regjistroKlient(user, pass)) {
            JOptionPane.showMessageDialog(this, "Llogaria u krijua me sukses! Tani kliko 'Hyr në Sistem'.");
        } else {
            JOptionPane.showMessageDialog(this, "Ky username ekziston!", "Gabim", JOptionPane.WARNING_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SherbimiHotelit sherbimi = new SherbimiHotelit();
        SwingUtilities.invokeLater(() -> new LoginGUI(sherbimi));
    }
}