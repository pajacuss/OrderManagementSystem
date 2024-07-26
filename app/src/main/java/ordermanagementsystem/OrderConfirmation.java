package ordermanagementsystem;

import javax.swing.*;
import java.sql.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Font;

public class OrderConfirmation extends JFrame implements ActionListener{

    private final int WIDTH = 480, HEIGHT = 800;
    private JLabel l1, l2;
    private JButton bConfirm, bGoBack;
    private int width;
    private Font wFont = new Font("Arial",Font.BOLD,50), cFont = new Font("SansSerif",Font.PLAIN, 24);

    public OrderConfirmation(){
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setTitle("Order Confirmation");
        setResizable(false);
        setLayout(null);

        l1 = new JLabel("Confirm order");
        l1.setFont(wFont);
        width = calculateWidth(l1);
        l1.setBounds(WIDTH/2 - width/2, 30, width, 40);
        add(l1);

        int labelY = 80;
        for (String key : Menu.currentOrder.keySet()) {
            float price = getPrice(key);
            int amount = Menu.currentOrder.get(key);
            String s1 = key + " x" + amount+", " + price * amount;
            JLabel label = new JLabel(s1);
            label.setFont(cFont);
            width = calculateWidth(label);
            label.setBounds(WIDTH/2 - width/2, labelY, width, 30);
            add(label);
            labelY += 40;
            if(labelY == 640){
                break;
            }
        }

        l2 = new JLabel("Order price: " + Menu.currentOrderPrice);
        l2.setFont(wFont.deriveFont(40F));
        width = calculateWidth(l2);
        l2.setBounds(WIDTH/2 - width/2, 540, width, 40);
        add(l2);

        bConfirm = new JButton("Confirm");
        bConfirm.setBounds(80, 680, 150, 50);
        bConfirm.addActionListener(this); 
        add(bConfirm);

        bGoBack = new JButton("Go back");
        bGoBack.setBounds(80 + 150 + 10, 680,150, 50);
        bGoBack.addActionListener(this); 
        add(bGoBack);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == bConfirm){
            confirmOrder();
        }else if(e.getSource() == bGoBack){
            setVisible(false);
            new Menu();
        }
    }

    private void recordOrder(){
        Conn conn = new Conn();
        int id = 0;
        String order = "";
        for (String key : Menu.currentOrder.keySet()) {
            int amount = Menu.currentOrder.get(key);
            order += key + " x" + amount+", ";
        }
        order = order.substring(0,order.length() - 2);
        try {
            ResultSet rs = conn.statement.executeQuery("SELECT * FROM orderhistory");
            while(rs.next()){
                id = rs.getInt("id");
            }
            id++;
            rs.close();
            conn.statement.executeUpdate("INSERT INTO orderhistory VALUES(" + id + ", '" + order + "'," + Menu.currentOrderPrice + ")");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void confirmOrder(){
        recordOrder();
        Menu.currentOrder.clear();
        Menu.currentOrderPrice = 0;
        setVisible(false);
        new Menu();
    }

    private float getPrice(String name){
        float price = 0F;
        Conn conn = new Conn();
        try {
            ResultSet rs = conn.statement.executeQuery("SELECT * FROM menupositions WHERE name = '" + name + "'");
            while (rs.next()) {
                price = rs.getFloat("price");
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return price;
    }

    private int calculateWidth(JLabel label){
        return label.getFontMetrics(label.getFont()).stringWidth(label.getText());
    }
    
}
