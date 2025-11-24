package com.curadoria.util;
import java.awt.*;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.geom.RoundRectangle2D;

public class UtilMaterial {

    //___Paleta de Cores___
    public static final Color BG = Color.decode("#E8F1F2");
    public static final Color PRIMARY = Color.decode("#247BA0");
    public static final Color PRIMARY_DARK = Color.decode("#13293D"); 
    public static final Color TEXT = PRIMARY_DARK;
    public static final String BG_HEX = "#E8F1F2";

    //____Fontes____
    public static final Font H1 = new Font("Segoe UI", Font.BOLD, 22);
    public static final Font PLAIN = new Font("Segoe UI", Font.PLAIN, 15);
    public static final Font BUTTON = new Font("Segoe UI", Font.BOLD, 14);

    //____Título____
    public static JLabel h1(String text) {
        JLabel l = new JLabel(text, SwingConstants.CENTER);
        l.setFont(H1);
        l.setForeground(PRIMARY_DARK);
        return l;
    }

    //____botão____
    public static JButton materialButton(String text) {
        JButton btn = new RoundedButton(text, 12); // Raio de 12px
        btn.setFont(BUTTON);
        btn.setBackground(PRIMARY);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        return btn;
    }

    //____Classe interna - botão arredondado
    private static class RoundedButton extends JButton {
        private int radius;

        public RoundedButton(String text, int radius) {
            super(text);
            this.radius = radius;
            setContentAreaFilled(false); // Torna a área de conteúdo transparente
            setOpaque(false); 
        }

        @Override
        public Border getBorder() {
            // Define um padding interno
            return BorderFactory.createEmptyBorder(8, 20, 8, 20); 
        }
        
        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            
            //___cor de fundo___
            if (getModel().isArmed()) {
                g2.setColor(PRIMARY_DARK); 
            } else if (getModel().isRollover()) {
                g2.setColor(PRIMARY.brighter()); 
            } else {
                g2.setColor(getBackground());
            }

            g2.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), radius, radius));
            
  
            super.paintComponent(g2); 
            g2.dispose();
        }
    }
}