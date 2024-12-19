package Jframe;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class bmi extends JFrame {
    private JTextField beratField;
    private JTextField tinggiField;
    private JButton hitungButton;
    private JTextArea hasilArea;

    public bmi() {
        setTitle("Body Mass Index");
        setSize(300, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        JLabel beratLabel = new JLabel("Berat Badan (kg):");
        beratLabel.setBounds(10, 20, 150, 25);
        add(beratLabel);

        beratField = new JTextField();
        beratField.setBounds(150, 20, 100, 25);
        add(beratField);

        JLabel tinggiLabel = new JLabel("Tinggi Badan (cm):");
        tinggiLabel.setBounds(10, 60, 150, 25);
        add(tinggiLabel);

        tinggiField = new JTextField();
        tinggiField.setBounds(150, 60, 100, 25);
        add(tinggiField);

        hitungButton = new JButton("Hitung");
        hitungButton.setBounds(10, 100, 240, 25);
        add(hitungButton);

        hasilArea = new JTextArea();
        hasilArea.setBounds(10, 140, 240, 100);
        hasilArea.setEditable(false);
        add(hasilArea);

        hitungButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                hitungBMI();
            }
        });
    }

    private void hitungBMI() {
        try {
            double berat = Double.parseDouble(beratField.getText());
            double tinggiCm = Double.parseDouble(tinggiField.getText());
            double tinggiM = tinggiCm / 100;

            double bmi = berat / (tinggiM * tinggiM);
            String kategori;
            if (bmi < 18.5) {
                kategori = "Anda termasuk berbadan kurus";
            } else if (bmi >= 18.5 && bmi < 25) {
                kategori = "Anda termasuk berbadan langsing";
            } else {
                kategori = "Anda termasuk berbadan gemuk";
            }

            hasilArea.setText(String.format("Nilai BMI anda adalah %.4f\n%s", bmi, kategori));
        } catch (NumberFormatException e) {
            hasilArea.setText("Input tidak valid. Silakan masukkan angka.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            bmi frame = new bmi();
            frame.setVisible(true);
        });
    }
}