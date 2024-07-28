package ordermanagementsystem;
import javax.swing.*;
import java.sql.*;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.ArrayList;

public class OrderHistory extends JFrame implements ActionListener{
    
    private final int WIDTH = 600, HEIGHT = 800;
    private int width;
    private JLabel lWelcome;
    private JButton bClear, bBack;
    private Font wFont = new Font("Arial",Font.BOLD,45), cFont = new Font("SansSerif",Font.PLAIN, 16);
    private List<String> orderHistoryList;

    public OrderHistory(){
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setTitle("Order History");
        setResizable(false);
        setLayout(null);

        getOrderHistory();

        lWelcome = new JLabel("Order history");
        lWelcome.setFont(wFont);
        width = calculateWidth(lWelcome);
        lWelcome.setBounds(WIDTH/2 - width/2, 30, width, 50);
        add(lWelcome);

        int currentY = 100;
        if(!orderHistoryList.isEmpty()){
            for(int i = orderHistoryList.size() - 1; i>=0; i--){
                JLabel label = new JLabel();
                label.setText("<html>"+orderHistoryList.get(i) + "</html>");
                label.setFont(cFont);
                width = calculateWidth(label);
                int height = 30;
                if(width > WIDTH){
                    height = 60;
                }
                label.setBounds(20, currentY, width, height);
                add(label);
                currentY += height;
                if(currentY > 700){
                    break;
                }   
            }
        }

        bBack = new JButton("Go Back");
        bBack.setBounds(140, 700, 150, 50);
        bBack.addActionListener(this);
        add(bBack);

        bClear = new JButton("Clear History");
        bClear.setBounds(140 + 20 +150, 700, 150, 50);
        bClear.addActionListener(this);
        add(bClear);


        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == bBack){
            this.dispose();
            new Menu();
        }else if(e.getSource() == bClear){
            bClearHandle();
        }
    }

    private int calculateWidth(JLabel label){
        return label.getFontMetrics(label.getFont()).stringWidth(label.getText());
    }

    private void getOrderHistory(){
        Conn conn = new Conn();
        orderHistoryList = new ArrayList<>();
        try {
            ResultSet rs = conn.statement.executeQuery("SELECT * FROM orderhistory");
            while(rs.next()){
                String record = "No."+ rs.getInt("id") + ", " + rs.getString("order") + ", total = " + rs.getFloat("orderprice");
                orderHistoryList.add(record);
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void bClearHandle(){
        Conn conn = new Conn();
        try {
            conn.statement.executeUpdate("DELETE FROM orderhistory");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        JOptionPane.showMessageDialog(null, "Order history cleared!");
        this.dispose();
        new OrderHistory();
    }

}
