import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class SignUpDialog extends JDialog {

    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JPasswordField txtConfirm;
    private JButton btnDaftar, btnBatal;

    private final Font FONT_NORMAL = new Font("Segoe UI", Font.PLAIN, 13);
    private final Font FONT_BUTTON = new Font("Segoe UI", Font.BOLD, 13);

    private LoginFrame parent;

    public SignUpDialog(LoginFrame parent) {
        super(parent, "Sign Up - Toko Buku Gen Z", true);
        this.parent = parent;

        setSize(360, 260);
        setLocationRelativeTo(parent);
        setResizable(false);

        initComponents();
    }

    private void initComponents() {
        JPanel root = new JPanel(new BorderLayout());
        root.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        setContentPane(root);

        JPanel card = new JPanel(new GridBagLayout());
        card.setBackground(new Color(245, 245, 250));
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(180, 180, 200)),
                BorderFactory.createEmptyBorder(12, 12, 12, 12)
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 4, 6, 4);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        txtUsername = new JTextField();
        txtPassword = new JPasswordField();
        txtConfirm = new JPasswordField();

        styleField(txtUsername);
        styleField(txtPassword);
        styleField(txtConfirm);

        JLabel lblUser = new JLabel("Username");
        JLabel lblPass = new JLabel("Password");
        JLabel lblConf = new JLabel("Konfirmasi Password");
        lblUser.setFont(FONT_NORMAL);
        lblPass.setFont(FONT_NORMAL);
        lblConf.setFont(FONT_NORMAL);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0;
        card.add(lblUser, gbc);

        gbc.gridy++;
        card.add(lblPass, gbc);

        gbc.gridy++;
        card.add(lblConf, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 1;
        card.add(txtUsername, gbc);

        gbc.gridy++;
        card.add(txtPassword, gbc);

        gbc.gridy++;
        card.add(txtConfirm, gbc);

        JPanel panelBtn = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 5));
        panelBtn.setOpaque(false);

        btnDaftar = new JButton("Daftar");
        btnBatal = new JButton("Batal");

        styleButton(btnDaftar);
        styleButton(btnBatal);

        panelBtn.add(btnBatal);
        panelBtn.add(btnDaftar);

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        card.add(panelBtn, gbc);

        root.add(card, BorderLayout.CENTER);

        btnDaftar.addActionListener(e -> doSignUp());
        btnBatal.addActionListener(e -> dispose());
        txtConfirm.addActionListener(e -> doSignUp());
    }

    private void styleField(JTextField field) {
        field.setFont(FONT_NORMAL);
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(180, 180, 200)),
                BorderFactory.createEmptyBorder(4, 6, 4, 6)
        ));
    }

    private void styleButton(JButton btn) {
        btn.setFont(FONT_BUTTON);
        btn.setBackground(new Color(60, 120, 200));
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setBorder(BorderFactory.createEmptyBorder(6, 14, 6, 14));
    }

    private void doSignUp() {
        String username = txtUsername.getText().trim();
        String pass = new String(txtPassword.getPassword());
        String conf = new String(txtConfirm.getPassword());

        if (username.isEmpty() || pass.isEmpty() || conf.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Semua field wajib diisi 😅",
                    "Validasi", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (!pass.equals(conf)) {
            JOptionPane.showMessageDialog(this,
                    "Password dan konfirmasi tidak sama 🙃",
                    "Validasi", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int idBaru = parent.getNextUserId();
        UserSystem newUser = new UserSystem(idBaru, username, pass, "PEMBELI");
        parent.addUser(newUser);

        JOptionPane.showMessageDialog(this,
                "Akun berhasil dibuat! Silakan login 🎉",
                "Sukses", JOptionPane.INFORMATION_MESSAGE);

        dispose();
    }
}
