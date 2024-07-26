package ordermanagementsystem;
import javax.swing.*;
import java.sql.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.awt.Font;

public class ItemAdder extends JFrame implements ActionListener{
    private final int WIDTH = 480, HEIGHT = 600;
    private JLabel lName, lPrice, lAmount;
    private JTextField tfNumberOf;
    private JButton bAdd, bBack;
    private int width;
    private Font cFont = new Font("Arial", Font.BOLD, 40), inputFont = new Font("SansSerif",Font.PLAIN, 24);
    private float price;
    private String name;

    public ItemAdder(String name){
        this.name = name;
        setSize(WIDTH, HEIGHT);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Add Item");
        setResizable(false);
        setLayout(null);

        getPrice(name);

        lName = new JLabel("Add " + name);
        lName.setFont(cFont);
        width = calculateWidth(lName);
        lName.setBounds(WIDTH/2 - width/2, 40, width, 50);
        add(lName);

        lPrice = new JLabel("Price: " + price);
        lPrice.setFont(cFont.deriveFont(30F));
        width = calculateWidth(lPrice);
        lPrice.setBounds(WIDTH/2 - width/2, 120, width, 50);
        add(lPrice);

        lAmount = new JLabel("Enter the amount:");
        lAmount.setFont(cFont.deriveFont(25F));
        width = calculateWidth(lAmount);
        lAmount.setBounds(WIDTH/2 - width/2, 200, width, 50);
        add(lAmount);

        tfNumberOf = new JTextField();
        tfNumberOf.setFont(inputFont);
        tfNumberOf.setBounds(WIDTH/2 - 150/2,260,150,50);
        add(tfNumberOf);

        bAdd = new JButton("Add position");
        bAdd.setFont(cFont.deriveFont(16F));
        bAdd.setBounds(WIDTH/2 - 150/2, 340, 150, 50);
        add(bAdd);
        bAdd.addActionListener(this);

        bBack = new JButton("Go Back");
        bBack.setFont(cFont.deriveFont(16F));
        bBack.setBounds(WIDTH/2 - 150/2, 400, 150, 50);
        add(bBack);
        bBack.addActionListener(this);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == bAdd){
            addItem();
        }else if(e.getSource() == bBack){
            this.dispose();
            new Menu();
        }
    }

    private int calculateWidth(JLabel label){
        return label.getFontMetrics(label.getFont()).stringWidth(label.getText());
    }

    private void getPrice(String name){
        Conn conn = new Conn();
        try{
            ResultSet rs = conn.statement.executeQuery("SELECT * FROM menupositions WHERE name = '"+name+"'");
            while(rs.next()){
                price = rs.getFloat("price");
            }
            rs.close();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    private void addItem(){
        int amount = Integer.parseInt(tfNumberOf.getText());
        Menu.currentOrder.put(name, amount );
        Menu.currentOrderPrice += price * amount;
        this.dispose();
        new Menu();
    }
}
