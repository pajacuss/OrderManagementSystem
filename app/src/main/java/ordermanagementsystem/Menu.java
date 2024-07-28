package ordermanagementsystem;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.ArrayList;
import java.awt.Font;
import java.sql.*;
import java.awt.Color;
import java.awt.Image;
public class Menu extends JFrame implements ActionListener{
    private final int WIDTH = 1280, HEIGHT = 860;
    private JButton bConfirm, bProgram, bHistory, bClearOrder;
    private JLabel lWelcome, lCurrentOrder;
    public static Map<String, Integer> currentOrder = new LinkedHashMap<>(); //name, amount
    public static float currentOrderPrice = 0;
    public List<String> positions;
    private int width;
    private Font cFont = new Font("Arial", Font.BOLD, 40);

    public Menu(){
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(Color.LIGHT_GRAY);
        setTitle("Order Managment System");
        setResizable(false);
        setLayout(null);

        ImageIcon icon = new ImageIcon(getClass().getResource("/OMlogo.png"));
        Image image = icon.getImage();
        setIconImage(image);

        lWelcome = new JLabel("Order Management System");
        lWelcome.setFont(cFont);
        width = calculateWidth(lWelcome);
        lWelcome.setBounds(WIDTH/2 - width/2, 10, width, 50);
        add(lWelcome);

        int currentX = 40;
        int currentY = 100;
        getButtons();
        for (String name : positions){
            JButton button = new JButton("<html>" + name + "</html>"); 
            if(currentX == 1240){
                currentX = 40;
                currentY += 150;
            }
            button.setBounds(currentX, currentY, 140, 140);
            currentX += 150;
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    setVisible(false);
                    new ItemAdder(name);
                }
            });
            add(button);
        }

        lCurrentOrder = new JLabel("Current Order: ");
        lCurrentOrder.setFont(cFont.deriveFont(20F));
        width = calculateWidth(lCurrentOrder);
        lCurrentOrder.setBounds(80, 550, width, 50);
        add(lCurrentOrder);

        int row = 600;
        if(!currentOrder.isEmpty()){
            for (String key : currentOrder.keySet()) {
                float price = getPrice(key);
                int amount = currentOrder.get(key);
                JLabel label = new JLabel(key + " x" + amount+", " + price * amount);
                label.setFont(cFont.deriveFont(16F));
                width = calculateWidth(label);
                label.setBounds(80, row, width, 20);
                add(label);
                row += 25;
            }
        }
        
        bProgram = new JButton("Set Menu Positions");
        bProgram.setBounds(340,600,150,150);
        bProgram.addActionListener(this); 
        add(bProgram);

        bConfirm = new JButton("Confirm Order");
        bConfirm.setBounds(540,600,150,150);
        bConfirm.addActionListener(this); 
        add(bConfirm);

        bHistory = new JButton("Order History");
        bHistory.setBounds(740,600,150,150);
        bHistory.addActionListener(this); 
        add(bHistory);

        bClearOrder = new JButton("Clear Order");
        bClearOrder.setBounds(940,600,150,150);
        bClearOrder.addActionListener(this); 
        add(bClearOrder);


        setVisible(true);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == bProgram){
            programHandle();
        }else if(e.getSource() == bConfirm){
            this.dispose();
            new OrderConfirmation();
        }else if(e.getSource() == bHistory){
            this.dispose();
            new OrderHistory();
        }else if(e.getSource() == bClearOrder){
            clearOrderHandle();
        }
    }

    private void getButtons(){
        Conn conn = new Conn();
        positions = new ArrayList<>();
        try {
            ResultSet rs = conn.statement.executeQuery("SELECT * FROM menupositions");
            while(rs.next()){
                positions.add(rs.getString("name"));
            }
            if(positions.size() == 0){
                for(int j = 1; j <= 24; j++){
                    positions.add("Item " + j);
                }
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void clearOrderHandle(){
        Menu.currentOrder.clear();
        Menu.currentOrderPrice = 0F;
        JOptionPane.showMessageDialog(null, "Order cleared");
        this.dispose();
        new Menu();
    }

    private void programHandle(){
        if(positions.get(0).equals("Item 1")){
            this.dispose();
            new ButtonProgrammer();
        }else{
            this.dispose();
            new ConfirmPage();
        }
    }

    private int calculateWidth(JLabel label){
        return label.getFontMetrics(label.getFont()).stringWidth(label.getText());
    }
    private float getPrice(String name){
        float price = 0F;
        Conn conn = new Conn();
        try {
            ResultSet rs = conn.statement.executeQuery("SELECT * FROM menupositions WHERE name = '" +name+ "'");
            while (rs.next()) {
                price = rs.getFloat("price");
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return price;
    }
    public static void main(String[] args) {
       new Menu();
    }
}