package Jframe;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FormPenjualanBarang extends JFrame {

    private JLabel NK, KK, KT, KB, NB, HB, Satuan, JB, DSK, TH, UB, UK;
    private JTextField NKF, KTF, NBF, HBF, SatuanF, JBF, DSKF, THF, UBF, UKF;
    private JComboBox<String> CKK, CKB;
    private JButton bhapus, bdiskon, bproses, buangbayar;

    public FormPenjualanBarang() {

        super("Form Penjualan Barang");
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        NK = new JLabel("Nama Kosumen : ");
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(NK, gbc);

        NKF = new JTextField(10);
        gbc.gridx = 1;
        gbc.gridy = 0;
        add(NKF, gbc);

        KK = new JLabel("Kode Konsumen: ");
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(KK, gbc);

        CKK = new JComboBox<>();
        CKK.addItem("KS001");
        CKK.addItem("KS002");
        CKK.addItem("KS003");
        gbc.gridx = 1;
        gbc.gridy = 1;
        add(CKK, gbc);

        KT = new JLabel("Keterangan : ");
        gbc.gridx = 0;
        gbc.gridy = 2;
        add(KT, gbc);

        KTF = new JTextField(10);
        gbc.gridx = 1;
        gbc.gridy = 2;
        KTF.setEditable(false);
        add(KTF, gbc);

        KB = new JLabel("Kode Barang : ");
        gbc.gridx = 0;
        gbc.gridy = 3;
        add(KB, gbc);

        CKB = new JComboBox<>();
        CKB.addItem("B001");
        CKB.addItem("B002");
        CKB.addItem("B003");
        CKB.addItem("B004");
        gbc.gridx = 1;
        gbc.gridy = 3;
        add(CKB, gbc);

        NB = new JLabel("Nama Barang : ");
        gbc.gridx = 0;
        gbc.gridy = 4;
        add(NB, gbc);

        NBF = new JTextField(10);
        gbc.gridx = 1;
        gbc.gridy = 4;
        NBF.setEditable(false);
        add(NBF, gbc);

        HB = new JLabel("Harga Barang : ");
        gbc.gridx = 0;
        gbc.gridy = 5;
        add(HB, gbc);

        HBF = new JTextField(10);
        gbc.gridx = 1;
        gbc.gridy = 5;
        HBF.setEditable(false);
        add(HBF, gbc);

        Satuan = new JLabel("Satuan : ");
        gbc.gridx = 0;
        gbc.gridy = 6;
        add(Satuan, gbc);

        SatuanF = new JTextField(10);
        gbc.gridx = 1;
        gbc.gridy = 6;
        SatuanF.setEditable(false);
        add(SatuanF, gbc);

        JB = new JLabel("Jumlah Beli : ");
        gbc.gridx = 0;
        gbc.gridy = 7;
        add(JB, gbc);

        JBF = new JTextField(10);
        gbc.gridx = 1;
        gbc.gridy = 7;
        add(JBF, gbc);

        DSK = new JLabel("Diskon : ");
        gbc.gridx = 0;
        gbc.gridy = 8;
        add(DSK, gbc);

        DSKF = new JTextField(10);
        gbc.gridx = 1;
        gbc.gridy = 8;
        DSKF.setEditable(false);
        add(DSKF, gbc);

        TH = new JLabel("Total Harga : ");
        gbc.gridx = 0;
        gbc.gridy = 9;
        add(TH, gbc);

        THF = new JTextField(10);
        gbc.gridx = 1;
        gbc.gridy = 9;
        THF.setEditable(false);
        add(THF, gbc);

        UB = new JLabel("Uang Bayar : ");
        gbc.gridx = 0;
        gbc.gridy = 10;
        add(UB, gbc);

        UBF = new JTextField(10);
        gbc.gridx = 1;
        gbc.gridy = 10;
        add(UBF, gbc);

        UK = new JLabel("Uang Kembalian : ");
        gbc.gridx = 1;
        gbc.gridy = 11;
        add(UK, gbc);

        UKF = new JTextField(10);
        gbc.gridx = 3;
        gbc.gridy = 11;
        UKF.setEditable(false);
        add(UKF, gbc);

        bhapus = new JButton("Hapus");
        gbc.gridx = 0;
        gbc.gridy = 11;
        add(bhapus, gbc);

        bdiskon = new JButton("Diskon");
        gbc.gridx = 3;
        gbc.gridy = 8;
        add(bdiskon, gbc);

        bproses = new JButton("Proses");
        gbc.gridx = 3;
        gbc.gridy = 9;
        add(bproses, gbc);

        buangbayar = new JButton("Uang Bayar");
        gbc.gridx = 3;
        gbc.gridy = 10;
        add(buangbayar, gbc);

        setSize(400, 600);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        CKK.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String kodeInput = (String) CKK.getSelectedItem();

                if (kodeInput.equals("KS001")) {
                    KTF.setText("Members");
                } else if (kodeInput.equals("KS002")) {
                    KTF.setText("Non Members");
                } else if (kodeInput.equals("KS003")) {
                    KTF.setText("Konsumen Biasa");
                } else {
                    KTF.setText("Tidak Diketahui");

                }
            }
        });

        CKB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String kodeInput = (String) CKB.getSelectedItem();

                if (kodeInput.equals("B001")) {
                    NBF.setText("Pena");
                    HBF.setText("10000");
                    SatuanF.setText("pcs");
                } else if (kodeInput.equals("B002")) {
                    NBF.setText("Pensil");
                    HBF.setText("5000");
                    SatuanF.setText("pack");
                } else if (kodeInput.equals("B003")) {
                    NBF.setText("Buku");
                    HBF.setText("120000");
                    SatuanF.setText("Lusin");
                } else if (kodeInput.equals("B004")) {
                    NBF.setText("Penghapus");
                    HBF.setText("50000");
                    SatuanF.setText("box");
                } else {
                    NBF.setText("Tidak Di ketahui");
                    HBF.setText("Tidak Di ketahui");
                    SatuanF.setText("Tidak Di ketahui");

                }
            }
        });

        bdiskon.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                double hargaBarang, diskon;
                int jumlahBeli;

                try {
                    jumlahBeli = Integer.parseInt(JBF.getText());
                    hargaBarang = Double.parseDouble(HBF.getText());

                    if (jumlahBeli > 10) {
                        diskon = hargaBarang * 0.1;
                    } else {
                        diskon = 0;
                    }

                    DSKF.setText(String.valueOf(diskon));

                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Input tidak valid! Masukkan angka yang benar.");
                }
            }
        });

        bproses.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                double hargaBarang, diskon, totalbayar;
                int jumlahBeli;

                hargaBarang = Double.parseDouble(HBF.getText());
                diskon = Double.parseDouble(DSKF.getText());
                jumlahBeli = Integer.parseInt(JBF.getText());

                totalbayar = (hargaBarang * jumlahBeli) - diskon;
                THF.setText(String.valueOf(totalbayar));

            }
        });

        buangbayar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                double totalharga, uangbayar, kembalian;

                totalharga = Double.parseDouble(THF.getText());
                uangbayar = Double.parseDouble(UBF.getText());

                if (uangbayar >= totalharga) {
                    kembalian = uangbayar - totalharga;
                    UKF.setText(String.valueOf(kembalian));
                } else {
                    UKF.setText("Uang Bayar Kurang");
                }

            }
        });

        bhapus.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                NKF.setText(" ");
                CKK.setSelectedItem(null);
                CKB.setSelectedItem(null);
                KTF.setText(" ");
                NBF.setText(" ");
                HBF.setText(" ");
                SatuanF.setText(" ");
                JBF.setText(" ");
                DSKF.setText(" ");
                THF.setText(" ");
                UBF.setText(" ");
                UKF.setText(" ");
            }
        });
    }

    public static void main(String[] args) {
        new FormPenjualanBarang();
    }

}
