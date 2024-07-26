package ordermanagementsystem;

import javax.swing.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Color;
import java.awt.Font;

public class ConfirmPage extends JFrame implements ActionListener{
    
    private final int WIDTH = 600, HEIGHT = 800;
    private JLabel l1, l2;
    private JButton b1, b2;
    private Font cFont = new Font("Arial",Font.BOLD,50);
    private int width;

    public ConfirmPage(){
        setSize(WIDTH, HEIGHT);
        setLocationRelativeTo(null);
        setTitle("Menu Position Configuration");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLayout(null);


        String s1 = "Your current menu items will be forgotten!";
        l1 = new JLabel();
        l1.setText("<html>" + s1 + "</html>");
        l1.setFont(cFont);
        l1.setForeground(new Color(255,0,0));
        width = calculateWidth(l1);
        l1.setBounds(80, 50, 500, 275);
        add(l1);

        l2 = new JLabel("Confirm by clicking this button:");
        l2.setFont(cFont.deriveFont(30F));
        width = calculateWidth(l2);
        l2.setBounds(WIDTH/2 - width/2, 300, width, 50);
        add(l2);

        b1 = new JButton("Confirm");
        b1.setBounds(150,400,300,80);
        b1.addActionListener(this); 
        add(b1);

        b2 = new JButton("Go back");
        b2.setBounds(150,500,300,80);
        b2.addActionListener(this); 
        add(b2);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == b1){
            this.dispose();
            new ButtonProgrammer();
        }else if(e.getSource() == b2){
            this.dispose();
            new Menu();
        }
    }

    private int calculateWidth(JLabel label){
        return label.getFontMetrics(label.getFont()).stringWidth(label.getText());
    }
    
}
