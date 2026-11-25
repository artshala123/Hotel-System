import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Path2D;

public class KomponenteModerne {

    // 1. Buton Modern me Gradient, Qoshe të Rrethuara, Efekt Hover & Press
    public static class ButonModern extends JButton {
        private Color ngjyra1;
        private Color ngjyra2;
        private int rrezja;
        private boolean eshteHover = false;
        private boolean eshteShtypur = false;

        public ButonModern(String teksti, Color ngjyraBaze, Color ngjyraTekstit, int rrezja) {
            super(teksti);
            this.ngjyra1 = ngjyraBaze;
            this.ngjyra2 = ngjyraBaze.darker();
            this.rrezja = rrezja;

            setForeground(ngjyraTekstit);
            setFont(new Font("Segoe UI", Font.BOLD, 12));
            setContentAreaFilled(false);
            setFocusPainted(false);
            setBorderPainted(false);
            setOpaque(false);
            setBorder(new EmptyBorder(8, 14, 8, 14));
            setCursor(new Cursor(Cursor.HAND_CURSOR));

            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    if (isEnabled()) { eshteHover = true; repaint(); }
                }
                @Override
                public void mouseExited(MouseEvent e) {
                    eshteHover = false; eshteShtypur = false; repaint();
                }
                @Override
                public void mousePressed(MouseEvent e) {
                    if (isEnabled()) { eshteShtypur = true; repaint(); }
                }
                @Override
                public void mouseReleased(MouseEvent e) {
                    eshteShtypur = false; repaint();
                }
            });
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            int w = getWidth();
            int h = getHeight();
            int offset = eshteShtypur ? 2 : 0;

            if (!isEnabled()) {
                g2.setColor(new Color(203, 213, 225));
                g2.fillRoundRect(0, 0, w, h, rrezja, rrezja);
            } else {
                if (!eshteShtypur) {
                    g2.setColor(new Color(15, 23, 42, 40));
                    g2.fillRoundRect(1, 3, w - 2, h - 3, rrezja, rrezja);
                }

                Color cTop = eshteHover ? ngjyra1.brighter() : ngjyra1;
                Color cBot = eshteHover ? ngjyra1 : ngjyra2;
                g2.setPaint(new GradientPaint(0, offset, cTop, 0, h, cBot));
                g2.fillRoundRect(0, offset, w, h - 2, rrezja, rrezja);

                g2.setColor(new Color(255, 255, 255, eshteHover ? 75 : 35));
                g2.drawRoundRect(0, offset, w - 1, h - 3, rrezja, rrezja);
            }

            g2.dispose();
            super.paintComponent(g);
        }
    }

    // 2. Panel i Rrethuar me Efekt Hover
    public static class PanelIRrethuar extends JPanel {
        private int rrezja;
        private Color ngjyraKufirit;
        private boolean efektHover;
        private boolean mbiPanel = false;

        public PanelIRrethuar(LayoutManager layout, Color bg, int rrezja, Color ngjyraKufirit, boolean efektHover) {
            super(layout);
            this.rrezja = rrezja;
            this.ngjyraKufirit = ngjyraKufirit;
            this.efektHover = efektHover;
            setBackground(bg);
            setOpaque(false);

            if (efektHover) {
                addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseEntered(MouseEvent e) { mbiPanel = true; repaint(); }
                    @Override
                    public void mouseExited(MouseEvent e) { mbiPanel = false; repaint(); }
                });
            }
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            int w = getWidth();
            int h = getHeight();

            g2.setColor(new Color(15, 23, 42, mbiPanel ? 38 : 16));
            g2.fillRoundRect(2, 4, w - 4, h - 4, rrezja, rrezja);

            g2.setColor(getBackground());
            g2.fillRoundRect(0, 0, w - 2, h - 3, rrezja, rrezja);

            if (ngjyraKufirit != null) {
                g2.setStroke(new BasicStroke(mbiPanel && efektHover ? 2f : 1f));
                g2.setColor(mbiPanel && efektHover ? new Color(217, 119, 6) : ngjyraKufirit);
                g2.drawRoundRect(0, 0, w - 3, h - 4, rrezja, rrezja);
            }

            g2.dispose();
            super.paintComponent(g);
        }
    }

    // 3. Fushë Teksti e Rrethuar
    public static class FusheTekstiERrethuar extends JTextField {
        private int rrezja;
        private boolean neFokus = false;

        public FusheTekstiERrethuar(int kolona, int rrezja) {
            super(kolona);
            this.rrezja = rrezja;
            setOpaque(false);
            setFont(new Font("Segoe UI", Font.PLAIN, 14));
            setBorder(new EmptyBorder(8, 14, 8, 14));

            addFocusListener(new FocusAdapter() {
                @Override
                public void focusGained(FocusEvent e) { neFokus = true; repaint(); }
                @Override
                public void focusLost(FocusEvent e) { neFokus = false; repaint(); }
            });
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(getBackground());
            g2.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, rrezja, rrezja);
            g2.setStroke(new BasicStroke(neFokus ? 2f : 1f));
            g2.setColor(neFokus ? new Color(245, 158, 11) : new Color(71, 85, 105));
            g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, rrezja, rrezja);
            g2.dispose();
            super.paintComponent(g);
        }
    }

    // 4. Fushë Fjalëkalimi e Rrethuar
    public static class FusheFjalekalimiERrethuar extends JPasswordField {
        private int rrezja;
        private boolean neFokus = false;

        public FusheFjalekalimiERrethuar(int kolona, int rrezja) {
            super(kolona);
            this.rrezja = rrezja;
            setOpaque(false);
            setFont(new Font("Segoe UI", Font.PLAIN, 14));
            setBorder(new EmptyBorder(8, 14, 8, 14));

            addFocusListener(new FocusAdapter() {
                @Override
                public void focusGained(FocusEvent e) { neFokus = true; repaint(); }
                @Override
                public void focusLost(FocusEvent e) { neFokus = false; repaint(); }
            });
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(getBackground());
            g2.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, rrezja, rrezja);
            g2.setStroke(new BasicStroke(neFokus ? 2f : 1f));
            g2.setColor(neFokus ? new Color(245, 158, 11) : new Color(71, 85, 105));
            g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, rrezja, rrezja);
            g2.dispose();
            super.paintComponent(g);
        }
    }

    // 5. Ikona Vektoriale të Pastra (pa asnjë kutiuk □)
    public static class Ikonat {

        public static Icon yll(int madhesia, boolean iMbushur) {
            return new Icon() {
                @Override
                public void paintIcon(Component c, Graphics g, int x, int y) {
                    Graphics2D g2 = (Graphics2D) g.create();
                    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                    double cx = x + madhesia / 2.0;
                    double cy = y + madhesia / 2.0;
                    double rJashtem = madhesia / 2.0 - 1;
                    double rBrendshem = rJashtem * 0.42;

                    Path2D ylli = new Path2D.Double();
                    for (int i = 0; i < 10; i++) {
                        double r = (i % 2 == 0) ? rJashtem : rBrendshem;
                        double kendi = Math.toRadians(-90 + i * 36);
                        double px = cx + r * Math.cos(kendi);
                        double py = cy + r * Math.sin(kendi);
                        if (i == 0) ylli.moveTo(px, py);
                        else ylli.lineTo(px, py);
                    }
                    ylli.closePath();

                    if (iMbushur) {
                        g2.setColor(new Color(245, 158, 11)); // Ari
                        g2.fill(ylli);
                    } else {
                        g2.setColor(new Color(148, 163, 184));
                        g2.setStroke(new BasicStroke(1.5f));
                        g2.draw(ylli);
                    }
                    g2.dispose();
                }
                @Override public int getIconWidth() { return madhesia; }
                @Override public int getIconHeight() { return madhesia; }
            };
        }

        public static Icon hotel(int madhesia, Color ngjyra) {
            return new Icon() {
                @Override
                public void paintIcon(Component c, Graphics g, int x, int y) {
                    Graphics2D g2 = (Graphics2D) g.create();
                    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    g2.setColor(ngjyra);

                    int w = madhesia - 6;
                    int h = madhesia - 4;
                    int bx = x + 3;
                    int by = y + 3;
                    g2.fillRoundRect(bx, by, w, h, 4, 4);

                    g2.setColor(new Color(17, 24, 39));
                    g2.fillRect(bx + 3, by + 3, 3, 3);
                    g2.fillRect(bx + w - 6, by + 3, 3, 3);
                    g2.fillRect(bx + 3, by + 8, 3, 3);
                    g2.fillRect(bx + w - 6, by + 8, 3, 3);
                    g2.fillRect(bx + w / 2 - 2, by + h - 5, 4, 5);
                    g2.dispose();
                }
                @Override public int getIconWidth() { return madhesia; }
                @Override public int getIconHeight() { return madhesia; }
            };
        }

        public static Icon lokacion(int madhesia, Color ngjyra) {
            return new Icon() {
                @Override
                public void paintIcon(Component c, Graphics g, int x, int y) {
                    Graphics2D g2 = (Graphics2D) g.create();
                    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    g2.setColor(ngjyra);

                    int r = madhesia - 6;
                    g2.fillOval(x + 3, y + 1, r, r);
                    int[] px = {x + 4, x + madhesia - 4, x + madhesia / 2};
                    int[] py = {y + r - 2, y + r - 2, y + madhesia - 1};
                    g2.fillPolygon(px, py, 3);

                    g2.setColor(new Color(30, 64, 175));
                    g2.fillOval(x + madhesia / 2 - 2, y + r / 2 - 1, 4, 4);
                    g2.dispose();
                }
                @Override public int getIconWidth() { return madhesia; }
                @Override public int getIconHeight() { return madhesia; }
            };
        }

        public static Icon kerko(int madhesia, Color ngjyra) {
            return new Icon() {
                @Override
                public void paintIcon(Component c, Graphics g, int x, int y) {
                    Graphics2D g2 = (Graphics2D) g.create();
                    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    g2.setColor(ngjyra);
                    g2.setStroke(new BasicStroke(2f));

                    int r = madhesia - 6;
                    g2.drawOval(x + 2, y + 2, r, r);
                    g2.drawLine(x + r, y + r, x + madhesia - 2, y + madhesia - 2);
                    g2.dispose();
                }
                @Override public int getIconWidth() { return madhesia; }
                @Override public int getIconHeight() { return madhesia; }
            };
        }

        public static Icon perdorues(int madhesia, Color ngjyra) {
            return new Icon() {
                @Override
                public void paintIcon(Component c, Graphics g, int x, int y) {
                    Graphics2D g2 = (Graphics2D) g.create();
                    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    g2.setColor(ngjyra);

                    int koka = madhesia / 3;
                    g2.fillOval(x + (madhesia - koka) / 2, y + 2, koka, koka);
                    g2.fillArc(x + 2, y + koka + 3, madhesia - 4, madhesia - koka, 0, 180);
                    g2.dispose();
                }
                @Override public int getIconWidth() { return madhesia; }
                @Override public int getIconHeight() { return madhesia; }
            };
        }

        public static Icon edito(int madhesia, Color ngjyra) {
            return new Icon() {
                @Override
                public void paintIcon(Component c, Graphics g, int x, int y) {
                    Graphics2D g2 = (Graphics2D) g.create();
                    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    g2.setColor(ngjyra);
                    g2.setStroke(new BasicStroke(1.8f));

                    g2.drawLine(x + 3, y + madhesia - 3, x + madhesia - 3, y + 3);
                    g2.drawLine(x + 2, y + madhesia - 2, x + 6, y + madhesia - 3);
                    g2.drawLine(x + 2, y + madhesia - 2, x + 3, y + madhesia - 6);
                    g2.dispose();
                }
                @Override public int getIconWidth() { return madhesia; }
                @Override public int getIconHeight() { return madhesia; }
            };
        }

        public static Icon fshi(int madhesia, Color ngjyra) {
            return new Icon() {
                @Override
                public void paintIcon(Component c, Graphics g, int x, int y) {
                    Graphics2D g2 = (Graphics2D) g.create();
                    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    g2.setColor(ngjyra);
                    g2.setStroke(new BasicStroke(1.8f));

                    g2.drawLine(x + 3, y + 4, x + madhesia - 3, y + 4);
                    g2.drawLine(x + 6, y + 2, x + madhesia - 6, y + 2);
                    g2.drawRect(x + 4, y + 4, madhesia - 8, madhesia - 6);
                    g2.dispose();
                }
                @Override public int getIconWidth() { return madhesia; }
                @Override public int getIconHeight() { return madhesia; }
            };
        }
    }
}