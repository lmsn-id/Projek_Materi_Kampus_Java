package Jframe;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Percabangan extends JFrame {

    private JLabel kode, jabatan, gaji;
    private JTextField inpjabatan, inpgaji;
    private JComboBox<String> cbkode;

    public Percabangan() {
        this.setTitle("Percabangan");
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        kode = new JLabel("Kode Karyawan : ");
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(kode, gbc);

        jabatan = new JLabel("Jabatan : ");
        gbc.gridx = 0;
        gbc.gridy = 2;
        add(jabatan, gbc);

        gaji = new JLabel("Gaji Pokok : ");
        gbc.gridx = 0;
        gbc.gridy = 3;
        add(gaji, gbc);

        cbkode = new JComboBox<>();
        cbkode.addItem("1111");
        cbkode.addItem("2222");
        cbkode.addItem("3333");
        cbkode.addItem("4444");
        cbkode.addItem("5555");
        gbc.gridx = 1;
        gbc.gridy = 1;
        add(cbkode, gbc);

        inpjabatan = new JTextField(10);
        gbc.gridx = 1;
        gbc.gridy = 2;
        inpjabatan.setEditable(false);
        add(inpjabatan, gbc);

        inpgaji = new JTextField(10);
        gbc.gridx = 1;
        gbc.gridy = 3;
        inpgaji.setEditable(false);
        add(inpgaji, gbc);

        cbkode.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String kodeInput = (String) cbkode.getSelectedItem();

                if (kodeInput.equals("1111")) {
                    inpjabatan.setText("Direktur");
                    inpgaji.setText("5000000");
                } else if (kodeInput.equals("2222")) {
                    inpjabatan.setText("Manager");
                    inpgaji.setText("4000000");
                } else if (kodeInput.equals("3333")) {
                    inpjabatan.setText("Asisten Direktur");
                    inpgaji.setText("3000000");
                } else if (kodeInput.equals("4444")) {
                    inpjabatan.setText("Kepala Bagian");
                    inpgaji.setText("2000000");
                } else if (kodeInput.equals("5555")) {
                    inpjabatan.setText("Staff");
                    inpgaji.setText("1000000");
                } else {
                    inpjabatan.setText("Tidak Diketahui");
                    inpgaji.setText("");
                }
            }
        });

        this.setSize(400, 300);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    public static void main(String[] args) {
        new Percabangan();
    }
}
