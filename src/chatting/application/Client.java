package chatting.application;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.text.*;
import java.util.*;
import javax.swing.*;
import javax.swing.border.*;

public class Client implements ActionListener {

    // Declearing Variables here(e.g. JTextField text, JPanel a1 etc...)
    // so that we can use it in constructor as well as outside it
    static JPanel a1;
    JTextField text;// (in Abstract function ActionPerformed Text field is use so that it will
                    // displayed on our panel)
    static Box vertical = Box.createVerticalBox();
    static DataOutputStream dout;
    static JFrame f = new JFrame();

    // constructor starts here
    Client() {

        f.setLayout(null);
        JPanel p1 = new JPanel(); // green header panel
        p1.setBackground(new Color(7, 94, 84));
        p1.setBounds(0, 0, 450, 70);
        p1.setLayout(null);
        f.add(p1);

        // back image icon code on green panel
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/3.png"));
        Image i2 = i1.getImage().getScaledInstance(25, 25, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel back = new JLabel(i3);
        back.setBounds(5, 20, 25, 25);
        p1.add(back);
        // back image event i.e. close the window using system.exit
        back.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent ae) {
                System.exit(0);
            }
        });

        // profile image icon
        ImageIcon i4 = new ImageIcon(ClassLoader.getSystemResource("icons/2.png"));
        Image i5 = i4.getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT);
        ImageIcon i6 = new ImageIcon(i5);
        JLabel profile = new JLabel(i6);
        profile.setBounds(40, 10, 50, 50);
        p1.add((profile));
        // video image icon
        ImageIcon i7 = new ImageIcon(ClassLoader.getSystemResource("icons/video.png"));
        Image i8 = i7.getImage().getScaledInstance(25, 25, Image.SCALE_DEFAULT);
        ImageIcon i9 = new ImageIcon(i8);
        JLabel video = new JLabel(i9);
        video.setBounds(300, 20, 25, 25);
        p1.add(video);
        // phone image icon
        ImageIcon i10 = new ImageIcon(ClassLoader.getSystemResource("icons/phone.png"));
        Image i11 = i10.getImage().getScaledInstance(27, 27, Image.SCALE_DEFAULT);
        ImageIcon i12 = new ImageIcon(i11);
        JLabel phone = new JLabel(i12);
        phone.setBounds(340, 20, 27, 27);
        p1.add(phone);
        // more option image icon
        ImageIcon i13 = new ImageIcon(ClassLoader.getSystemResource("icons/3icon.png"));
        Image i14 = i13.getImage().getScaledInstance(22, 22, Image.SCALE_DEFAULT);
        ImageIcon i15 = new ImageIcon(i14);
        JLabel more = new JLabel(i15);
        more.setBounds(375, 22, 22, 22);
        p1.add(more);

        // Name text on green panel
        JLabel name = new JLabel("Mintu");
        name.setBounds(110, 15, 100, 18);
        name.setForeground(Color.white);
        name.setFont(new Font("open_sans", Font.BOLD, 17));
        p1.add(name);
        // Active now text on green panel
        JLabel status = new JLabel("Active Now");
        status.setBounds(110, 35, 100, 18);
        status.setForeground(Color.white);
        status.setFont(new Font("San_Serif", Font.BOLD, 14));
        p1.add(status);

        // below header area
        a1 = new JPanel();
        a1.setBounds(5, 75, 440, 570);
        f.add(a1);

        // Text area (Bottom most)
        text = new JTextField(); // text is already decleared above constructor so no need to declear again
                                 // (JTextField text;)
        text.setBounds(5, 655, 310, 40);
        text.setFont(new Font("San_Serif", Font.PLAIN, 16));
        f.add(text);

        // Send Button for sending message
        JButton send = new JButton("Send");
        send.setBounds(320, 655, 123, 40);
        send.setBackground(new Color(7, 94, 84));
        send.setForeground(Color.white);
        send.setFont(new Font("San_Serif", Font.PLAIN, 17));
        send.addActionListener(this);
        // functionality on clicking send button will decleared here
        f.add(send);

        // window properties like size , color,location
        f.setSize(450, 700);
        f.setLocation(800, 50);
        f.setUndecorated(true);
        f.getContentPane().setBackground(Color.white);

        f.setVisible(true);
    }
    // Constructor Client ends here :)

    // since we implemented ActionListener we have to define its abstract method
    public void actionPerformed(ActionEvent ae) {
        try {
            String out = text.getText(); // text is a object and getText method is use to get the TextData
            // System.out.println(out); // this will print the text on console

            // JLabel output = new JLabel(out);

            JPanel p2 = formatLabel(out);
            // p2.add(output);

            a1.setLayout(new BorderLayout());

            JPanel right = new JPanel(new BorderLayout());
            right.add(p2, BorderLayout.LINE_END);
            vertical.add(right);
            vertical.add(Box.createVerticalStrut(15));

            a1.add(vertical, BorderLayout.PAGE_START);
            dout.writeUTF(out);// i forget to write this and this is a big mistake

            text.setText("");// to empty the writing text

            // below 3 functions are necessary to refresh page after clicking send button!
            f.repaint();
            f.invalidate();
            f.validate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static JPanel formatLabel(String out) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JLabel output = new JLabel("<html> <p style=\"width:150px\">" + out + "</p> </html>");
        output.setFont(new Font("Tahoma", Font.PLAIN, 16));
        output.setBackground(new Color(73, 211, 102));
        output.setOpaque(true);
        output.setBorder(new EmptyBorder(15, 15, 15, 50));

        panel.add(output);

        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm");

        JLabel time = new JLabel();
        time.setText(sdf.format(cal.getTime()));
        panel.add(time);
        return panel;

    }

    public static void main(String gg[]) {
        new Client();
        try {

            Socket s = new Socket("127.0.0.1", 6001);
            DataInputStream din = new DataInputStream(s.getInputStream());
            dout = new DataOutputStream(s.getOutputStream()); // already decleared globally
            while (true) {
                a1.setLayout(new BorderLayout());
                // We are using protocols like readUTF() so that we can read msg infinitly and
                // with the help of data imput stream we read impupt the method readUTF return
                // string
                String msg = din.readUTF();
                JPanel panel = formatLabel(msg);

                JPanel left = new JPanel(new BorderLayout());
                left.add(panel, BorderLayout.LINE_START);
                vertical.add(left);// i missed this line too
                vertical.add(Box.createVerticalStrut(15));
                a1.add(vertical);
                f.validate(); // to refresh panel
            }
        } catch (Exception e) {
            e.printStackTrace();// handle exception
        }
    }

}
