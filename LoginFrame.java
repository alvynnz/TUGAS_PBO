import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class LoginFrame extends JFrame {

    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JButton btnLogin, btnSignUp;

    private List<UserSystem> users = new ArrayList<>();

    public LoginFrame() {
        setTitle("TOKO buku GEN Z - Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 600);
        setLocationRelativeTo(null);
        setResizable(false);

        initDummyUsers();
        initComponents();
    }

    private void initDummyUsers() {

        users.add(new UserSystem(1, "admin", "admin123", "ADMIN"));
        users.add(new UserSystem(2, "user", "user123", "PEMBELI"));
    }

    public void addUser(UserSystem user) {
        users.add(user);
    }

    public int getNextUserId() {
        return users.size() + 1;
    }

    private void initComponents() {
        JPanel root = new JPanel(new BorderLayout());
        root.setBackground(new Color(41, 117, 112));
        setContentPane(root);

        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setBackground(new Color(228, 228, 228));
        topBar.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(180, 180, 180)));

        JPanel logoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        logoPanel.setOpaque(false);

        JPanel logoBox = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(new Color(252, 206, 82)); // kuning
                g.fillRoundRect(0, 0, getWidth(), getHeight(), 8, 8);
                g.setColor(Color.WHITE);
                g.fillRoundRect(8, 10, getWidth() - 16, 4, 4, 4);
                g.fillRoundRect(8, 18, getWidth() - 16, 4, 4, 4);
            }
        };
        logoBox.setPreferredSize(new Dimension(32, 32));

        JLabel lblApp = new JLabel("TOKO buku GEN Z");
        lblApp.setFont(new Font("Segoe UI", Font.BOLD, 18));

        logoPanel.add(logoBox);
        logoPanel.add(lblApp);

        topBar.add(logoPanel, BorderLayout.WEST);
        root.add(topBar, BorderLayout.NORTH);

        JPanel middle = new JPanel(new GridLayout(1, 2));
        middle.setOpaque(false);
        root.add(middle, BorderLayout.CENTER);

        JPanel left = new JPanel();
        left.setBackground(new Color(41, 117, 112));
        left.setLayout(new BoxLayout(left, BoxLayout.Y_AXIS));
        left.setBorder(BorderFactory.createEmptyBorder(40, 60, 40, 40));

        JLabel lblT1 = new JLabel("TOKO");
        JLabel lblT2 = new JLabel("buku");
        JLabel lblT3 = new JLabel("GEN Z");
        JLabel lblTag = new JLabel("MEMBACALAH SEBELUM DILARANG");

        lblT1.setFont(new Font("Segoe UI", Font.BOLD, 38));
        lblT2.setFont(new Font("Segoe UI", Font.BOLD, 38));
        lblT3.setFont(new Font("Segoe UI", Font.BOLD, 38));
        lblTag.setFont(new Font("Segoe UI", Font.PLAIN, 16));

        lblT1.setForeground(Color.WHITE);
        lblT2.setForeground(Color.WHITE);
        lblT3.setForeground(Color.WHITE);
        lblTag.setForeground(Color.WHITE);

        lblT1.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        lblT2.setBorder(BorderFactory.createEmptyBorder(0, 30, 0, 0));
        lblT3.setBorder(BorderFactory.createEmptyBorder(0, 60, 0, 0));

        left.add(lblT1);
        left.add(lblT2);
        left.add(lblT3);
        left.add(Box.createVerticalStrut(10));
        left.add(lblTag);
        left.add(Box.createVerticalStrut(30));

        txtUsername = new JTextField();
        txtPassword = new JPasswordField();
        styleInputField(txtUsername);
        styleInputField(txtPassword);

        JPanel gmailBox = createInputBox("Gmail", txtUsername);
        JPanel sandiBox = createInputBox("Sandi", txtPassword);

        left.add(gmailBox);
        left.add(Box.createVerticalStrut(16));
        left.add(sandiBox);
        left.add(Box.createVerticalStrut(20));

        JPanel btnRow = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        btnRow.setOpaque(false);

        btnLogin = new JButton("LOG_IN >");
        btnLogin.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnLogin.setBackground(new Color(228, 228, 228));
        btnLogin.setForeground(Color.BLACK);
        btnLogin.setFocusPainted(false);
        btnLogin.setBorder(BorderFactory.createEmptyBorder(8, 18, 8, 18));
        btnLogin.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        btnSignUp = new JButton("sign up");
        btnSignUp.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        btnSignUp.setContentAreaFilled(false);
        btnSignUp.setBorderPainted(false);
        btnSignUp.setFocusPainted(false);
        btnSignUp.setForeground(Color.WHITE);
        btnSignUp.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        btnRow.add(btnLogin);
        btnRow.add(Box.createHorizontalStrut(15));
        btnRow.add(btnSignUp);

        left.add(btnRow);

        left.add(Box.createVerticalStrut(10));
        JLabel lblInfo = new JLabel("admin/admin123 · user/user123");
        lblInfo.setFont(new Font("Segoe UI", Font.ITALIC, 11));
        lblInfo.setForeground(new Color(230, 230, 230));
        left.add(lblInfo);

        middle.add(left);

        JPanel right = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                int w = getWidth();
                int h = getHeight();

                g2.setColor(new Color(252, 206, 82));
                g2.fillOval(w / 4, h / 6, w / 2 + 100, h / 2 + 60);

                g2.fillOval(w / 4 - 40, h / 5 + 40, 40, 40);
                g2.fillOval(w / 4 + 40, h / 5 - 30, 28, 28);
                g2.fillOval(w / 4 + 220, h / 5 - 10, 24, 24);

                int bx = w / 3;
                int by = h / 3;
                int bw = 160;
                int bh = 40;

                g2.setColor(Color.WHITE);
                g2.fillRoundRect(bx, by, bw, bh, 10, 10);
                g2.setColor(new Color(120, 160, 255));
                g2.fillRoundRect(bx + 10, by + 8, bw, bh, 10, 10);
                g2.setColor(new Color(240, 90, 90));
                g2.fillRoundRect(bx + 20, by + 16, bw, bh, 10, 10);
            }
        };
        right.setOpaque(false);
        right.setBorder(BorderFactory.createEmptyBorder(40, 20, 40, 40));
        middle.add(right);

        btnLogin.addActionListener(e -> doLogin());
        btnSignUp.addActionListener(e -> openSignUpDialog());
        txtPassword.addActionListener(e -> doLogin());
    }

    private JPanel createInputBox(String title, JComponent field) {
        JPanel box = new JPanel();
        box.setLayout(new BorderLayout());
        box.setBackground(Color.WHITE);
        box.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                BorderFactory.createEmptyBorder(6, 10, 10, 10)
        ));

        JLabel lblTitle = new JLabel(title);
        lblTitle.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblTitle.setForeground(new Color(120, 120, 120));

        box.add(lblTitle, BorderLayout.NORTH);
        box.add(field, BorderLayout.CENTER);

        return box;
    }

    private void styleInputField(JTextField field) {
        field.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        field.setBackground(new Color(245, 245, 245));
        field.setBorder(BorderFactory.createEmptyBorder(4, 2, 4, 2));
        field.setCaretColor(Color.BLACK);
    }

    private void openSignUpDialog() {
        SignUpDialog dialog = new SignUpDialog(this);
        dialog.setVisible(true);
    }

    private void doLogin() {
        String username = txtUsername.getText().trim();
        String password = new String(txtPassword.getPassword());

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Isi dulu email & sandi.",
                    "Validasi", JOptionPane.WARNING_MESSAGE);
            return;
        }

        for (UserSystem u : users) {
            if (u.login(username, password)) {
                JOptionPane.showMessageDialog(this,
                        "Login sebagai " + u.getRole(),
                        "Welcome", JOptionPane.INFORMATION_MESSAGE);

                TokoBukuFrame frame = new TokoBukuFrame(u);
                frame.setVisible(true);
                dispose();
                return;
            }
        }

        JOptionPane.showMessageDialog(this,
                "Email / sandi salah.",
                "Gagal Login", JOptionPane.ERROR_MESSAGE);
    }
}
