package ordermanagementsystem;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.awt.Font;
import java.awt.Color;
import java.awt.Image;

public class ButtonProgrammer extends JFrame implements ActionListener{

    private final int WIDTH = 600, HEIGHT = 800;
    private JLabel lWelcome, lName, lPrice;
    private JButton bAdd, bStop;
    private JTextField tfName, tfPrice;
    private int count = 1;
    private int width;
    private Font cFont = new Font("Arial",Font.BOLD,40);


    public ButtonProgrammer(){
        clearTable();
        getContentPane().setBackground(Color.LIGHT_GRAY);
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setTitle("Menu Position Configuration");
        setResizable(false);
        setLayout(null);

        ImageIcon icon = new ImageIcon(getClass().getResource("/OMlogo.png"));
        Image image = icon.getImage();
        setIconImage(image);
        
        lWelcome = new JLabel("Add your menu positions");
        lWelcome.setFont(cFont);
        width = calculateWidth(lWelcome);
        lWelcome.setBounds(WIDTH/2 - width/2, 40, width, 50);
        add(lWelcome);

        lName = new JLabel("Enter name of item no."+count+" :");
        lName.setFont(cFont.deriveFont(26F));
        width = calculateWidth(lName);
        lName.setBounds(WIDTH/2 - width/2, 120, width, 50);
        add(lName);
        
        tfName = new JTextField();
        tfName.setFont(cFont.deriveFont(24F));
        tfName.setBounds(200, 180, 200, 40);
        add(tfName);

        lPrice = new JLabel("Enter price of item no."+count+" :");
        lPrice.setFont(cFont.deriveFont(26F));
        width = calculateWidth(lPrice);
        lPrice.setBounds(WIDTH/2 - width/2, 240, width, 50);
        add(lPrice);
        
        tfPrice = new JTextField();
        tfPrice.setFont(cFont.deriveFont(24F));
        tfPrice.setBounds(200, 300, 200, 40);
        add(tfPrice);

        bAdd = new JButton("Add item");
        bAdd.setBounds(225,360,150,60);
        bAdd.addActionListener(this); 
        add(bAdd);

        bStop = new JButton("Stop");
        bStop.setBounds(225,420,150,60);
        bStop.addActionListener(this); 
        add(bStop);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == bAdd){
            addItem();
        }else if(e.getSource() == bStop){
            this.dispose();
            new Menu();
        }
    }

    private int calculateWidth(JLabel label){
        return label.getFontMetrics(label.getFont()).stringWidth(label.getText());
    }

    private void addItem(){
        if(tfName.getText().isEmpty()|| tfPrice.getText().isEmpty()){
            JOptionPane.showMessageDialog(null,"Incorrect item name or price!");
            return;
        }
        float price = Float.parseFloat(tfPrice.getText().strip());
        Conn conn = new Conn();
        try {
            conn.statement.executeUpdate("INSERT INTO menupositions VALUES(" + count + ",'" + tfName.getText().strip() + "'," + price + ")");
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NumberFormatException nfe){
            JOptionPane.showMessageDialog(null, "Please enter a valid number for the price!");
        }
        count++;
        if(count == 24){
            JOptionPane.showMessageDialog(null, "Maximum capacity of items reached");
            this.dispose();
            new Menu();
        }
        lName.setText("Enter name of item no."+count+" :");
        width = calculateWidth(lName);
        lName.setBounds(WIDTH/2 - width/2, 120, width, 50);
        
        lPrice.setText("Enter price of item no."+count+" :");
        width = calculateWidth(lPrice);
        lPrice.setBounds(WIDTH/2 - width/2, 240, width, 50);
        
        tfName.setText("");
        tfPrice.setText("");

    }

    private void clearTable() {
        Conn conn = new Conn();
        try {
            conn.statement.executeUpdate("DELETE FROM menupositions");
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error clearing the table: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
}
