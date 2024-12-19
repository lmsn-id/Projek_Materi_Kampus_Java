package Jframe;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class FormWarungNasi extends JFrame {

    private JCheckBox nasiGoreng, mieGoreng, sotoAyam, sopKambing, nasiPutih;
    private JTextField hargaNasiGorengField, hargaMieGorengField, hargaSotoAyamField, hargaSopKambingField,
            hargaNasiPutihField;
    private JTextField inputJumlahNasiGorengField, inputJumlahMieGorengField, inputJumlahSotoAyamField,
            inputJumlahSopKambingField, inputJumlahNasiPutihField;
    private JTextField totalHargaLabel;
    private JLabel detailPesananLabel;
    private JButton hitungButton, resetButton, keluarButton;

    public FormWarungNasi() {
        setTitle("Warung Penjualan");

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel label = new JLabel("Warung Nasi Laris Terus", JLabel.CENTER);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 3;
        add(label, gbc);

        JLabel menuLabel = new JLabel("Menu Makanan");
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        add(menuLabel, gbc);

        JLabel hargaLabel = new JLabel("Harga", JLabel.CENTER);
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        add(hargaLabel, gbc);

        JLabel jumlahLabel = new JLabel("Jumlah Beli");
        gbc.gridx = 2;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        add(jumlahLabel, gbc);

        nasiGoreng = new JCheckBox("Nasi Goreng");
        gbc.gridx = 0;
        gbc.gridy = 3;
        add(nasiGoreng, gbc);

        mieGoreng = new JCheckBox("Mie Goreng");
        gbc.gridx = 0;
        gbc.gridy = 4;
        add(mieGoreng, gbc);

        sotoAyam = new JCheckBox("Soto Ayam");
        gbc.gridx = 0;
        gbc.gridy = 5;
        add(sotoAyam, gbc);

        sopKambing = new JCheckBox("Sop Kambing");
        gbc.gridx = 0;
        gbc.gridy = 6;
        add(sopKambing, gbc);

        nasiPutih = new JCheckBox("Nasi Putih");
        gbc.gridx = 0;
        gbc.gridy = 7;
        add(nasiPutih, gbc);

        hargaNasiGorengField = new JTextField(5);
        hargaNasiGorengField.setVisible(false);
        gbc.gridx = 1;
        gbc.gridy = 3;
        add(hargaNasiGorengField, gbc);

        hargaMieGorengField = new JTextField(5);
        hargaMieGorengField.setVisible(false);
        gbc.gridx = 1;
        gbc.gridy = 4;
        add(hargaMieGorengField, gbc);

        hargaSotoAyamField = new JTextField(5);
        hargaSotoAyamField.setVisible(false);
        gbc.gridx = 1;
        gbc.gridy = 5;
        add(hargaSotoAyamField, gbc);

        hargaSopKambingField = new JTextField(5);
        hargaSopKambingField.setVisible(false);
        gbc.gridx = 1;
        gbc.gridy = 6;
        add(hargaSopKambingField, gbc);

        hargaNasiPutihField = new JTextField(5);
        hargaNasiPutihField.setVisible(false);
        gbc.gridx = 1;
        gbc.gridy = 7;
        add(hargaNasiPutihField, gbc);

        inputJumlahNasiGorengField = new JTextField(5);
        inputJumlahNasiGorengField.setVisible(false);
        gbc.gridx = 2;
        gbc.gridy = 3;
        add(inputJumlahNasiGorengField, gbc);

        inputJumlahMieGorengField = new JTextField(5);
        inputJumlahMieGorengField.setVisible(false);
        gbc.gridx = 2;
        gbc.gridy = 4;
        add(inputJumlahMieGorengField, gbc);

        inputJumlahSotoAyamField = new JTextField(5);
        inputJumlahSotoAyamField.setVisible(false);
        gbc.gridx = 2;
        gbc.gridy = 5;
        add(inputJumlahSotoAyamField, gbc);

        inputJumlahSopKambingField = new JTextField(5);
        inputJumlahSopKambingField.setVisible(false);
        gbc.gridx = 2;
        gbc.gridy = 6;
        add(inputJumlahSopKambingField, gbc);

        inputJumlahNasiPutihField = new JTextField(5);
        inputJumlahNasiPutihField.setVisible(false);
        gbc.gridx = 2;
        gbc.gridy = 7;
        add(inputJumlahNasiPutihField, gbc);

        JLabel JumlahHarga = new JLabel("Jumlah Harga : ", JLabel.CENTER);
        gbc.gridx = 0;
        gbc.gridy = 8;
        gbc.gridwidth = 1;
        add(JumlahHarga, gbc);

        totalHargaLabel = new JTextField(10);
        totalHargaLabel.setEditable(false);
        gbc.gridx = 1;
        gbc.gridy = 8;
        gbc.gridwidth = 2;
        add(totalHargaLabel, gbc);

        JLabel detailLabel = new JLabel("Detail Pesanan : ", JLabel.CENTER);
        gbc.gridx = 0;
        gbc.gridy = 9;
        gbc.gridwidth = 1;
        add(detailLabel, gbc);

        detailPesananLabel = new JLabel();
        detailPesananLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        detailPesananLabel.setPreferredSize(new Dimension(250, 100));
        detailPesananLabel.setVerticalAlignment(SwingConstants.TOP);
        gbc.gridx = 1;
        gbc.gridy = 9;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.BOTH;
        add(detailPesananLabel, gbc);

        hitungButton = new JButton("Hitung");
        gbc.gridx = 0;
        gbc.gridy = 10;
        gbc.gridwidth = 1;
        add(hitungButton, gbc);

        resetButton = new JButton("Reset");
        gbc.gridx = 1;
        gbc.gridy = 10;
        gbc.gridwidth = 1;
        add(resetButton, gbc);

        keluarButton = new JButton("Keluar");
        gbc.gridx = 2;
        gbc.gridy = 10;
        gbc.gridwidth = 1;
        add(keluarButton, gbc);

        nasiGoreng.addItemListener(new CheckboxListener(hargaNasiGorengField, inputJumlahNasiGorengField, "15000"));
        mieGoreng.addItemListener(new CheckboxListener(hargaMieGorengField, inputJumlahMieGorengField, "12000"));
        sotoAyam.addItemListener(new CheckboxListener(hargaSotoAyamField, inputJumlahSotoAyamField, "20000"));
        sopKambing.addItemListener(new CheckboxListener(hargaSopKambingField, inputJumlahSopKambingField, "25000"));
        nasiPutih.addItemListener(new CheckboxListener(hargaNasiPutihField, inputJumlahNasiPutihField, "5000"));

        hitungButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                hitungTotal();
            }
        });

        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resetFields();
            }
        });

        keluarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int x = JOptionPane.showConfirmDialog(null, "Apakah Anda yakin ingin keluar?", "Konfirmasi",
                        JOptionPane.YES_NO_OPTION);
                if (x == JOptionPane.YES_OPTION) {
                    dispose();
                }
            }
        });

        setSize(400, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    class CheckboxListener implements ItemListener {
        private JTextField hargaField;
        private JTextField jumlahField;
        private String harga;

        public CheckboxListener(JTextField hargaField, JTextField jumlahField, String harga) {
            this.hargaField = hargaField;
            this.jumlahField = jumlahField;
            this.harga = harga;
        }

        @Override
        public void itemStateChanged(ItemEvent e) {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                hargaField.setVisible(true);
                jumlahField.setVisible(true);
                hargaField.setText(harga);
            } else {
                hargaField.setVisible(false);
                jumlahField.setVisible(false);
                jumlahField.setText("");
                hargaField.setText("");
            }

            hargaField.getParent().revalidate();
            hargaField.getParent().repaint();
            jumlahField.getParent().revalidate();
            jumlahField.getParent().repaint();
        }
    }

    private void hitungTotal() {
        int total = 0;
        StringBuilder detailHarga = new StringBuilder();

        if (!inputJumlahNasiGorengField.getText().isEmpty()) {
            int harga = Integer.parseInt(hargaNasiGorengField.getText());
            int jumlah = Integer.parseInt(inputJumlahNasiGorengField.getText());
            total += harga * jumlah;
            detailHarga.append("Nasi Goreng x").append(jumlah).append(" = ").append(harga * jumlah).append("<br>");
        }

        if (!inputJumlahMieGorengField.getText().isEmpty()) {
            int harga = Integer.parseInt(hargaMieGorengField.getText());
            int jumlah = Integer.parseInt(inputJumlahMieGorengField.getText());
            total += harga * jumlah;
            detailHarga.append("Mie Goreng x").append(jumlah).append(" = ").append(harga * jumlah).append("<br>");
        }

        if (!inputJumlahSotoAyamField.getText().isEmpty()) {
            int harga = Integer.parseInt(hargaSotoAyamField.getText());
            int jumlah = Integer.parseInt(inputJumlahSotoAyamField.getText());
            total += harga * jumlah;
            detailHarga.append("Soto Ayam x").append(jumlah).append(" = ").append(harga * jumlah).append("<br>");
        }

        if (!inputJumlahSopKambingField.getText().isEmpty()) {
            int harga = Integer.parseInt(hargaSopKambingField.getText());
            int jumlah = Integer.parseInt(inputJumlahSopKambingField.getText());
            total += harga * jumlah;
            detailHarga.append("Sop Kambing x").append(jumlah).append(" = ").append(harga * jumlah).append("<br>");
        }

        if (!inputJumlahNasiPutihField.getText().isEmpty()) {
            int harga = Integer.parseInt(hargaNasiPutihField.getText());
            int jumlah = Integer.parseInt(inputJumlahNasiPutihField.getText());
            total += harga * jumlah;
            detailHarga.append("Nasi Putih x").append(jumlah).append(" = ").append(harga * jumlah).append("<br>");
        }

        totalHargaLabel.setText(String.valueOf(total));
        detailPesananLabel.setText("<html>" + detailHarga.toString() + "</html>");
    }

    private void resetFields() {
        hargaNasiGorengField.setVisible(false);
        inputJumlahNasiGorengField.setVisible(false);
        hargaMieGorengField.setVisible(false);
        inputJumlahMieGorengField.setVisible(false);
        hargaSotoAyamField.setVisible(false);
        inputJumlahSotoAyamField.setVisible(false);
        hargaSopKambingField.setVisible(false);
        inputJumlahSopKambingField.setVisible(false);
        hargaNasiPutihField.setVisible(false);
        inputJumlahNasiPutihField.setVisible(false);

        inputJumlahNasiGorengField.setText("");
        inputJumlahMieGorengField.setText("");
        inputJumlahSotoAyamField.setText("");
        inputJumlahSopKambingField.setText("");
        inputJumlahNasiPutihField.setText("");
        totalHargaLabel.setText("");
        detailPesananLabel.setText("<html></html>");
        nasiGoreng.setSelected(false);
        mieGoreng.setSelected(false);
        sotoAyam.setSelected(false);
        sopKambing.setSelected(false);
        nasiPutih.setSelected(false);
    }

    public static void main(String[] args) {
        new FormWarungNasi();
    }
}
