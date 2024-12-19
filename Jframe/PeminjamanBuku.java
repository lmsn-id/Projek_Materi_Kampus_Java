package Jframe;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PeminjamanBuku extends JFrame {

    private JLabel kodePeminjaman, namaPeminjam, status, kodeBuku, judulBuku, tarifBuku, lamaPinjam, potongan, total;
    private JTextField ikp, inpm, istatus, ikb, inb, itb, ilp, ipot, itot;
    private JButton prs, prs2, hps, exit, prstotal;

    public PeminjamanBuku() {
        this.setTitle("Peminjaman Buku");
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        kodePeminjaman = new JLabel("Kode Peminjaman : ");
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(kodePeminjaman, gbc);

        ikp = new JTextField(15);
        gbc.gridx = 1;
        gbc.gridy = 0;
        add(ikp, gbc);

        namaPeminjam = new JLabel("Nama Peminjam :");
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(namaPeminjam, gbc);

        inpm = new JTextField(15);
        gbc.gridx = 1;
        gbc.gridy = 1;
        add(inpm, gbc);

        status = new JLabel("Status :");
        gbc.gridx = 0;
        gbc.gridy = 2;
        add(status, gbc);

        istatus = new JTextField(15);
        gbc.gridx = 1;
        gbc.gridy = 2;
        istatus.setEditable(false);
        add(istatus, gbc);

        prs = new JButton("Proses");
        prs.setPreferredSize(new Dimension(80, 30));
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.weightx = 0;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.WEST;
        add(prs, gbc);

        kodeBuku = new JLabel("Kode Buku :");
        gbc.gridx = 0;
        gbc.gridy = 4;
        add(kodeBuku, gbc);

        ikb = new JTextField(15);
        gbc.gridx = 1;
        gbc.gridy = 4;
        add(ikb, gbc);

        judulBuku = new JLabel("Judul Buku :");
        gbc.gridx = 0;
        gbc.gridy = 5;
        add(judulBuku, gbc);

        inb = new JTextField(15);
        gbc.gridx = 1;
        gbc.gridy = 5;
        inb.setEditable(false);
        add(inb, gbc);

        tarifBuku = new JLabel("Tarif Buku :");
        gbc.gridx = 0;
        gbc.gridy = 6;
        add(tarifBuku, gbc);

        itb = new JTextField(15);
        gbc.gridx = 1;
        gbc.gridy = 6;
        itb.setEditable(false);
        add(itb, gbc);

        lamaPinjam = new JLabel("Lama Pinjam :");
        gbc.gridx = 0;
        gbc.gridy = 7;
        add(lamaPinjam, gbc);

        ilp = new JTextField(15);
        gbc.gridx = 1;
        gbc.gridy = 7;
        add(ilp, gbc);

        potongan = new JLabel("Potongan :");
        gbc.gridx = 0;
        gbc.gridy = 8;
        add(potongan, gbc);

        ipot = new JTextField(15);
        gbc.gridx = 1;
        gbc.gridy = 8;
        ipot.setEditable(false);
        add(ipot, gbc);

        total = new JLabel("Total :");
        gbc.gridx = 0;
        gbc.gridy = 9;
        add(total, gbc);

        itot = new JTextField(15);
        gbc.gridx = 1;
        gbc.gridy = 9;
        itot.setEditable(false);
        add(itot, gbc);

        prs2 = new JButton("Proses Buku");
        prs2.setPreferredSize(new Dimension(108, 30));
        gbc.gridx = 3;
        gbc.gridy = 4;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.WEST;
        add(prs2, gbc);

        hps = new JButton("Hapus");
        hps.setPreferredSize(new Dimension(108, 30));
        gbc.gridx = 3;
        gbc.gridy = 6;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.WEST;
        add(hps, gbc);

        exit = new JButton("Keluar");
        exit.setPreferredSize(new Dimension(108, 30));
        gbc.gridx = 3;
        gbc.gridy = 7;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.WEST;
        add(exit, gbc);

        prstotal = new JButton("Proses Total");
        gbc.gridx = 3;
        gbc.gridy = 8;
        add(prstotal, gbc);

        prs.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String kode = ikp.getText().trim();
                if (kode.equals("A001")) {
                    istatus.setText("Anggota");
                } else if (kode.equals("B002")) {
                    istatus.setText("Bukan Anggota");
                } else if (kode.equals("C003")) {
                    istatus.setText("Staff");
                } else if (kode.equals("D004")) {
                    istatus.setText("Dosen");
                } else {
                    istatus.setText("Kode tidak dikenali");
                }
            }
        });

        prs2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String kode = ikb.getText().trim();
                switch (kode) {
                    case "1111":
                        inb.setText("Bahasa Pemrograman");
                        itb.setText("20000");
                        break;
                    case "2222":
                        inb.setText("Database");
                        itb.setText("10000");
                        break;
                    case "3333":
                        inb.setText("Manajemen Proyek");
                        itb.setText("15000");
                        break;
                    case "4444":
                        inb.setText("Kecerdasan Buatan");
                        itb.setText("15000");
                        break;
                    case "5555":
                        inb.setText("Framework Laravel");
                        itb.setText("16000");
                        break;
                    default:
                        inb.setText("Kode Tidak Ada");
                        itb.setText("0");
                        break;
                }
            }
        });

        prstotal.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int tarif = Integer.parseInt(itb.getText());
                    int lamaPinjam = Integer.parseInt(ilp.getText().trim());
                    String statusPeminjam = istatus.getText().trim();
                    int total = tarif;
                    double potongan = 0;

                    if (lamaPinjam >= 10) {
                        if (statusPeminjam.equalsIgnoreCase("Anggota")) {
                            potongan = total * 0.1;
                        } else if (statusPeminjam.equalsIgnoreCase("Dosen")) {
                            potongan = total * 0.2;
                        }
                    }

                    total -= potongan;
                    ipot.setText("Rp. " + potongan);
                    itot.setText("Rp. " + total);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Input tidak valid. Pastikan memasukkan lama pinjaman.");
                }
            }
        });

        hps.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int response = JOptionPane.showConfirmDialog(null, "Apakah Anda yakin ingin menghapus data?",
                        "Konfirmasi Hapus",
                        JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

                if (response == JOptionPane.YES_OPTION) {
                    ikp.setText("");
                    inpm.setText("");
                    istatus.setText("");
                    ikb.setText("");
                    inb.setText("");
                    itb.setText("");
                    ilp.setText("");
                    ipot.setText("");
                    itot.setText("");
                }
            }
        });

        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int x = JOptionPane.showConfirmDialog(null, "Apakah Anda yakin ingin keluar?", "Konfirmasi",
                        JOptionPane.YES_NO_OPTION);
                if (x == JOptionPane.YES_OPTION) {
                    dispose();
                }
            }
        });

        this.setSize(550, 600);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    public static void main(String[] args) {
        new PeminjamanBuku();
    }
}