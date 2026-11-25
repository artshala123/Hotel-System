import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class HartaHotelitDialog extends JDialog {
    // Koordinatat e hotelit (Prishtinë)
    private final double HOTEL_LAT = 42.6629;
    private final double HOTEL_LON = 21.1655;
    private final String ADRESA = "Bulevardi Nënë Tereza, Nr. 15, Prishtinë";

    private PaneliHartesInteraktive paneliHartes;

    public HartaHotelitDialog(JFrame prindi) {
        super(prindi, "📍 Harta Interaktive - Grand Hotel", true);
        setSize(740, 560);
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(236, 240, 245));

        // 1. Pjesa e sipërme
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(new Color(18, 22, 30));
        topPanel.setBorder(new EmptyBorder(12, 18, 12, 18));

        JPanel tekstet = new JPanel(new GridLayout(2, 1, 2, 2));
        tekstet.setOpaque(false);
        JLabel lblTitulli = new JLabel("🏨 Grand Hotel - Harta Interaktive (Tërhiqe me maus për ta lëvizur)");
        lblTitulli.setForeground(new Color(241, 196, 15));
        lblTitulli.setFont(new Font("Segoe UI", Font.BOLD, 15));

        JLabel lblAdresa = new JLabel("Adresa: " + ADRESA);
        lblAdresa.setForeground(Color.LIGHT_GRAY);
        lblAdresa.setFont(new Font("Segoe UI", Font.PLAIN, 12));

        tekstet.add(lblTitulli);
        tekstet.add(lblAdresa);

        // Butonat e kontrollit të hartës (+, -, Qendërzim)
        JPanel kontrolletHartes = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 2));
        kontrolletHartes.setOpaque(false);

        JButton btnZoomIn = krijoButon("+", new Color(52, 73, 94));
        JButton btnZoomOut = krijoButon("-", new Color(52, 73, 94));
        JButton btnQender = krijoButon("🎯 Kthehu te Hoteli", new Color(230, 126, 34));

        btnZoomIn.addActionListener(e -> paneliHartes.ndryshoZoom(1));
        btnZoomOut.addActionListener(e -> paneliHartes.ndryshoZoom(-1));
        btnQender.addActionListener(e -> paneliHartes.shkoTeHoteli());

        kontrolletHartes.add(btnZoomIn);
        kontrolletHartes.add(btnZoomOut);
        kontrolletHartes.add(btnQender);

        topPanel.add(tekstet, BorderLayout.WEST);
        topPanel.add(kontrolletHartes, BorderLayout.EAST);
        add(topPanel, BorderLayout.NORTH);

        // 2. Qendra: Harta që lëviz me maus dhe bën zoom (brenda kornizës së rrethuar)
        KomponenteModerne.PanelIRrethuar kornizaHartes = new KomponenteModerne.PanelIRrethuar(
                new BorderLayout(), Color.WHITE, 22, new Color(210, 215, 222), false);
        kornizaHartes.setBorder(new EmptyBorder(10, 10, 10, 10));

        paneliHartes = new PaneliHartesInteraktive();
        kornizaHartes.add(paneliHartes, BorderLayout.CENTER);

        JPanel qendraWrapper = new JPanel(new BorderLayout());
        qendraWrapper.setOpaque(false);
        qendraWrapper.setBorder(new EmptyBorder(12, 16, 4, 16));
        qendraWrapper.add(kornizaHartes, BorderLayout.CENTER);
        add(qendraWrapper, BorderLayout.CENTER);

        // 3. Pjesa e poshtme
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 12, 10));
        bottomPanel.setBackground(new Color(236, 240, 245));

        JButton btnGoogleMaps = krijoButon("🌐 Hap në Google Maps", new Color(39, 174, 96));
        btnGoogleMaps.addActionListener(e -> hapNeShfletues());

        JButton btnMbyll = krijoButon("Mbyll", new Color(192, 57, 43));
        btnMbyll.addActionListener(e -> dispose());

        bottomPanel.add(btnGoogleMaps);
        bottomPanel.add(btnMbyll);
        add(bottomPanel, BorderLayout.SOUTH);

        setLocationRelativeTo(prindi);
        setVisible(true);
    }

    private JButton krijoButon(String teksti, Color bg) {
        return new KomponenteModerne.ButonModern(teksti, bg, Color.WHITE, 18);
    }

    private void hapNeShfletues() {
        try {
            String url = "https://www.google.com/maps/search/?api=1&query=" + HOTEL_LAT + "," + HOTEL_LON;
            Desktop.getDesktop().browse(new URI(url));
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Nuk u hap dot shfletuesi!");
        }
    }

    // Paneli që vizaton dhe lëviz pllakat (tiles) e OpenStreetMap
    private class PaneliHartesInteraktive extends JPanel {
        private int zoom = 15;
        private double qendraX;
        private double qendraY;
        private Point pikaKlikimit;
        private Map<String, BufferedImage> cachePllakave = new HashMap<>();

        public PaneliHartesInteraktive() {
            setCursor(new Cursor(Cursor.MOVE_CURSOR));
            shkoTeHoteli();

            addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    pikaKlikimit = e.getPoint();
                }
            });

            addMouseMotionListener(new MouseMotionAdapter() {
                @Override
                public void mouseDragged(MouseEvent e) {
                    if (pikaKlikimit != null) {
                        int dx = e.getX() - pikaKlikimit.x;
                        int dy = e.getY() - pikaKlikimit.y;
                        qendraX -= dx;
                        qendraY -= dy;
                        pikaKlikimit = e.getPoint();
                        repaint();
                    }
                }
            });

            addMouseWheelListener(e -> {
                if (e.getWheelRotation() < 0) {
                    ndryshoZoom(1);
                } else {
                    ndryshoZoom(-1);
                }
            });
        }

        public void shkoTeHoteli() {
            qendraX = lonNePiksel(HOTEL_LON, zoom);
            qendraY = latNePiksel(HOTEL_LAT, zoom);
            repaint();
        }

        public void ndryshoZoom(int hapi) {
            int zoomRi = zoom + hapi;
            if (zoomRi < 3 || zoomRi > 18) return;

            double shumezuesi = Math.pow(2, hapi);
            qendraX *= shumezuesi;
            qendraY *= shumezuesi;
            zoom = zoomRi;
            repaint();
        }

        private double lonNePiksel(double lon, int z) {
            return ((lon + 180.0) / 360.0) * (256.0 * Math.pow(2, z));
        }

        private double latNePiksel(double lat, int z) {
            double sinLat = Math.sin(Math.toRadians(lat));
            double y = 0.5 - Math.log((1 + sinLat) / (1 - sinLat)) / (4 * Math.PI);
            return y * (256.0 * Math.pow(2, z));
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            int gjeresia = getWidth();
            int lartesia = getHeight();

            double majtas = qendraX - gjeresia / 2.0;
            double lart = qendraY - lartesia / 2.0;

            int minTileX = (int) Math.floor(majtas / 256.0);
            int maxTileX = (int) Math.floor((majtas + gjeresia) / 256.0);
            int minTileY = (int) Math.floor(lart / 256.0);
            int maxTileY = (int) Math.floor((lart + lartesia) / 256.0);

            int maxTiles = (int) Math.pow(2, zoom);

            for (int tx = minTileX; tx <= maxTileX; tx++) {
                for (int ty = minTileY; ty <= maxTileY; ty++) {
                    if (ty < 0 || ty >= maxTiles) continue;
                    int wrappedX = ((tx % maxTiles) + maxTiles) % maxTiles;

                    int drawX = (int) (tx * 256 - majtas);
                    int drawY = (int) (ty * 256 - lart);

                    BufferedImage img = merrPllaken(zoom, wrappedX, ty);
                    if (img != null) {
                        g2.drawImage(img, drawX, drawY, null);
                    } else {
                        g2.setColor(new Color(230, 234, 238));
                        g2.fillRect(drawX, drawY, 256, 256);
                        g2.setColor(new Color(210, 214, 220));
                        g2.drawRect(drawX, drawY, 256, 256);
                    }
                }
            }

            // Vizatimi i shënjuesit (Pin) të kuq mbi lokacionin e hotelit
            int hotelScreenX = (int) (lonNePiksel(HOTEL_LON, zoom) - majtas);
            int hotelScreenY = (int) (latNePiksel(HOTEL_LAT, zoom) - lart);

            // Hija e pin-it
            g2.setColor(new Color(0, 0, 0, 80));
            g2.fillOval(hotelScreenX - 8, hotelScreenY - 4, 16, 8);

            // Trupi i pin-it
            g2.setColor(new Color(231, 76, 60));
            g2.fillOval(hotelScreenX - 12, hotelScreenY - 34, 24, 24);
            int[] xPikat = {hotelScreenX - 10, hotelScreenX + 10, hotelScreenX};
            int[] yPikat = {hotelScreenY - 16, hotelScreenY - 16, hotelScreenY};
            g2.fillPolygon(xPikat, yPikat, 3);

            // Pika e bardhë në mes
            g2.setColor(Color.WHITE);
            g2.fillOval(hotelScreenX - 5, hotelScreenY - 27, 10, 10);

            // Etiketa e rrethuar "Grand Hotel" sipër pin-it
            g2.setColor(new Color(20, 24, 32, 230));
            g2.fillRoundRect(hotelScreenX - 48, hotelScreenY - 60, 96, 22, 12, 12);
            g2.setColor(Color.WHITE);
            g2.setFont(new Font("Segoe UI", Font.BOLD, 11));
            g2.drawString("Grand Hotel", hotelScreenX - 34, hotelScreenY - 45);
        }

        private BufferedImage merrPllaken(int z, int x, int y) {
            String celesi = z + "/" + x + "/" + y;
            if (cachePllakave.containsKey(celesi)) {
                return cachePllakave.get(celesi);
            }

            cachePllakave.put(celesi, null);
            new Thread(() -> {
                try {
                    URL url = new URL("https://tile.openstreetmap.org/" + z + "/" + x + "/" + y + ".png");
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestProperty("User-Agent", "GrandHotelJavaApp/1.0");
                    BufferedImage img = ImageIO.read(conn.getInputStream());
                    if (img != null) {
                        cachePllakave.put(celesi, img);
                        SwingUtilities.invokeLater(this::repaint);
                    }
                } catch (Exception ignored) {}
            }).start();

            return null;
        }
    }
}