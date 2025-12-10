import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.util.List;

public class TokoBukuFrame extends JFrame {

    private TokoBuku tokoBuku;
    private UserSystem currentUser;

    private JTable tblBuku;
    private DefaultTableModel modelBuku;

    private JTextField txtId;
    private JTextField txtJudul;
    private JTextField txtPenulis;
    private JTextField txtPenerbit;
    private JTextField txtTahun;
    private JTextField txtHarga;
    private JTextField txtStok;
    private JTextField txtIdKategori;
    private JTextField txtNamaKategori;

    private CardLayout contentLayout;
    private JPanel contentPanel;

    private Image userBgImage;

    private final Color NAVBAR_BG       = new Color(228, 228, 228);
    private final Color TEAL_BG         = new Color(41, 117, 112);
    private final Color FORM_CARD_BG    = new Color(204, 230, 228);
    private final Color TAB_BG          = new Color(245, 245, 245);
    private final Color TAB_BORDER      = new Color(180, 180, 180);
    private final Color TABLE_HEADER_BG = new Color(10, 34, 57);
    private final Color TABLE_ROW_BG    = new Color(51, 142, 127);
    private final Color TABLE_GRID      = new Color(0, 90, 80);

    private final Font FONT_TITLE    = new Font("Segoe UI", Font.BOLD, 18);
    private final Font FONT_LABEL_BIG= new Font("Segoe UI", Font.BOLD, 18);
    private final Font FONT_LABEL    = new Font("Segoe UI", Font.PLAIN, 13);
    private final Font FONT_TAB      = new Font("Segoe UI", Font.BOLD, 16);
    private final Font FONT_TABLE    = new Font("Segoe UI", Font.PLAIN, 13);

    public TokoBukuFrame(UserSystem user) {
        this.currentUser = user;
        this.tokoBuku = new TokoBuku();

        if (currentUser != null && "PEMBELI".equalsIgnoreCase(currentUser.getRole())) {
            ImageIcon bgIcon = new ImageIcon("Image/bahlil.jpg");
            if (bgIcon.getIconWidth() > 0) {
                userBgImage = bgIcon.getImage();
            }
        }

        initDataContoh();
        initUI();
        muatDataKeTabel();
    }

    public TokoBukuFrame() {
        this(null);
    }

    private void initDataContoh() {
        KategoriBuku fiksi = new KategoriBuku("K001", "Fiksi", "Novel dan cerita fiksi");
        KategoriBuku nonFiksi = new KategoriBuku("K002", "Non-Fiksi", "Buku pengetahuan");

        tokoBuku.tambahBuku(new Buku("B001", "Laskar Pelangi", "Andrea Hirata",
                "Bentang Pustaka", 2005, 75000, 10, fiksi));

        tokoBuku.tambahBuku(new Buku("B002", "Sapiens", "Yuval Noah Harari",
                "Gramedia", 2017, 120000, 5, nonFiksi));

        tokoBuku.tambahBuku(new Buku("B003", "Bumi Manusia", "Pramoedya A. Toer",
                "Hasta Mitra", 1980, 85000, 7, fiksi));
    }

    private void initUI() {
        setTitle("TOKO buku GEN Z - Dashboard");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1100, 650);
        setLocationRelativeTo(null);

        JPanel root = new JPanel(new BorderLayout());
        root.setBackground(TEAL_BG);
        setContentPane(root);

        JPanel navbar = new JPanel(new BorderLayout());
        navbar.setBackground(NAVBAR_BG);
        navbar.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, TAB_BORDER));

        JPanel logoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        logoPanel.setOpaque(false);

        JPanel logoBox = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(new Color(252, 206, 82));
                g.fillRoundRect(0, 0, getWidth(), getHeight(), 8, 8);
                g.setColor(Color.WHITE);
                g.fillRoundRect(8, 10, getWidth() - 16, 4, 4, 4);
                g.fillRoundRect(8, 18, getWidth() - 16, 4, 4, 4);
            }
        };
        logoBox.setPreferredSize(new Dimension(32, 32));

        JLabel lblAppName = new JLabel("TOKO buku GEN Z");
        lblAppName.setFont(FONT_TITLE);

        logoPanel.add(logoBox);
        logoPanel.add(lblAppName);

        JPanel tabPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 16, 8));
        tabPanel.setOpaque(false);

        JToggleButton tabBuku = createTabButton("Buku");
        JToggleButton tabPelanggan = createTabButton("Pelanggan");
        JToggleButton tabTransaksi = createTabButton("Transaksi");
        JToggleButton tabLaporan = createTabButton("Laporan");

        ButtonGroup group = new ButtonGroup();
        group.add(tabBuku);
        group.add(tabPelanggan);
        group.add(tabTransaksi);
        group.add(tabLaporan);
        tabBuku.setSelected(true);

        tabPanel.add(tabBuku);
        tabPanel.add(tabPelanggan);
        tabPanel.add(tabTransaksi);
        tabPanel.add(tabLaporan);

        JPanel profilePanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 5));
        profilePanel.setOpaque(false);

        String profileText = "👤 ";
        if (currentUser != null) {
            profileText += currentUser.getUsername() + " (" + currentUser.getRole() + ")";
        } else {
            profileText += "Guest";
        }
        JLabel lblProfile = new JLabel(profileText);
        lblProfile.setFont(FONT_LABEL);

        profilePanel.add(lblProfile);

        navbar.add(logoPanel, BorderLayout.WEST);
        navbar.add(tabPanel, BorderLayout.CENTER);
        navbar.add(profilePanel, BorderLayout.EAST);

        root.add(navbar, BorderLayout.NORTH);

        contentLayout = new CardLayout();
        contentPanel = new JPanel(contentLayout);
        contentPanel.setBackground(TEAL_BG);

        JPanel bukuPanel;
        if (currentUser != null &&
                "PEMBELI".equalsIgnoreCase(currentUser.getRole()) &&
                userBgImage != null) {
            bukuPanel = createBukuPanel(userBgImage);
        } else {
            bukuPanel = createBukuPanel(null);
        }

        JPanel pelangganPanel = createPlaceholderPanel("Pelanggan");
        JPanel transaksiPanel = createPlaceholderPanel("Transaksi");
        JPanel laporanPanel = createPlaceholderPanel("Laporan");

        contentPanel.add(bukuPanel, "BUKU");
        contentPanel.add(pelangganPanel, "PELANGGAN");
        contentPanel.add(transaksiPanel, "TRANSAKSI");
        contentPanel.add(laporanPanel, "LAPORAN");

        root.add(contentPanel, BorderLayout.CENTER);

        tabBuku.addActionListener(e -> contentLayout.show(contentPanel, "BUKU"));
        tabPelanggan.addActionListener(e -> contentLayout.show(contentPanel, "PELANGGAN"));
        tabTransaksi.addActionListener(e -> contentLayout.show(contentPanel, "TRANSAKSI"));
        tabLaporan.addActionListener(e -> contentLayout.show(contentPanel, "LAPORAN"));
    }

    private JPanel createBukuPanel(Image bgImage) {
        JPanel panel;
        if (bgImage != null) {
            panel = new BackgroundPanel(bgImage, TEAL_BG, 0.7f);
        } else {
            panel = new JPanel();
            panel.setBackground(TEAL_BG);
        }
        panel.setLayout(new BorderLayout());

        JPanel topArea = new JPanel(new BorderLayout());
        topArea.setOpaque(false);
        topArea.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));

        JPanel formCard = new JPanel(new GridBagLayout());
        formCard.setBackground(FORM_CARD_BG);
        formCard.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(150, 170, 170)),
                BorderFactory.createEmptyBorder(20, 30, 20, 30)
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        txtId = new JTextField();
        txtJudul = new JTextField();
        txtPenulis = new JTextField();
        txtPenerbit = new JTextField();
        txtTahun = new JTextField();
        txtHarga = new JTextField();
        txtStok = new JTextField();
        txtIdKategori = new JTextField();
        txtNamaKategori = new JTextField();

        styleField(txtId);
        styleField(txtJudul);
        styleField(txtPenulis);
        styleField(txtPenerbit);
        styleField(txtTahun);
        styleField(txtHarga);
        styleField(txtStok);
        styleField(txtIdKategori);
        styleField(txtNamaKategori);

        gbc.weightx = 0;
        gbc.gridx = 0;
        gbc.gridy = 0;
        addLabelBig(formCard, gbc, "ID Buku");
        gbc.gridy++;
        addLabelBig(formCard, gbc, "Penulis");
        gbc.gridy++;
        addLabelBig(formCard, gbc, "Tahun Terbit");
        gbc.gridy++;
        addLabelBig(formCard, gbc, "Stok");
        gbc.gridy++;
        addLabelBig(formCard, gbc, "Nama Kategori");

        gbc.weightx = 1;
        gbc.gridx = 1;
        gbc.gridy = 0;
        formCard.add(txtId, gbc);
        gbc.gridy++;
        formCard.add(txtPenulis, gbc);
        gbc.gridy++;
        formCard.add(txtTahun, gbc);
        gbc.gridy++;
        formCard.add(txtStok, gbc);
        gbc.gridy++;
        formCard.add(txtNamaKategori, gbc);

        gbc.weightx = 0;
        gbc.gridx = 2;
        gbc.gridy = 0;
        addLabelNormal(formCard, gbc, "Judul");
        gbc.gridy++;
        addLabelNormal(formCard, gbc, "Penerbit");
        gbc.gridy++;
        addLabelNormal(formCard, gbc, "Harga");
        gbc.gridy++;
        addLabelNormal(formCard, gbc, "ID Kategori");

        gbc.weightx = 1;
        gbc.gridx = 3;
        gbc.gridy = 0;
        formCard.add(txtJudul, gbc);
        gbc.gridy++;
        formCard.add(txtPenerbit, gbc);
        gbc.gridy++;
        formCard.add(txtHarga, gbc);
        gbc.gridy++;
        formCard.add(txtIdKategori, gbc);

        JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 10));
        actionPanel.setOpaque(false);

        JButton btnDelete  = createIconButton("trashicon.png");
        JButton btnAdd     = createIconButton("plus.png");
        JButton btnRefresh = createIconButton("refresh.png");

        actionPanel.add(btnDelete);
        actionPanel.add(btnAdd);
        actionPanel.add(btnRefresh);

        JPanel formWrapper = new JPanel(new BorderLayout());
        formWrapper.setOpaque(false);
        formWrapper.add(formCard, BorderLayout.CENTER);
        formWrapper.add(actionPanel, BorderLayout.SOUTH);

        topArea.add(formWrapper, BorderLayout.CENTER);
        panel.add(topArea, BorderLayout.NORTH);

        JPanel tableWrapper = new JPanel(new BorderLayout());
        tableWrapper.setOpaque(false);
        tableWrapper.setBorder(BorderFactory.createEmptyBorder(0, 15, 15, 15));

        String[] kolom = {"ID", "Judul", "Penulis", "Penerbit", "Tahun", "Harga", "Stok", "kategori"};
        modelBuku = new DefaultTableModel(kolom, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tblBuku = new JTable(modelBuku);
        tblBuku.setFont(FONT_TABLE);
        tblBuku.setRowHeight(26);
        tblBuku.setBackground(TABLE_ROW_BG);
        tblBuku.setForeground(Color.WHITE);
        tblBuku.setGridColor(TABLE_GRID);
        tblBuku.setSelectionBackground(new Color(0, 70, 80));
        tblBuku.setSelectionForeground(Color.WHITE);

        JTableHeader header = tblBuku.getTableHeader();
        header.setBackground(TABLE_HEADER_BG);
        header.setForeground(Color.WHITE);
        header.setFont(FONT_TABLE);
        header.setReorderingAllowed(false);

        JScrollPane scroll = new JScrollPane(tblBuku);
        scroll.getViewport().setBackground(TABLE_ROW_BG);
        tableWrapper.add(scroll, BorderLayout.CENTER);

        boolean isUserWithBg = (currentUser != null &&
                "PEMBELI".equalsIgnoreCase(currentUser.getRole()) &&
                bgImage != null);

        if (isUserWithBg) {
            makeTableTransparent(tblBuku, scroll);
        }

        panel.add(tableWrapper, BorderLayout.CENTER);

        if (currentUser != null && "PEMBELI".equalsIgnoreCase(currentUser.getRole())) {
            formWrapper.setVisible(false);
        }

        btnAdd.addActionListener(e -> tambahBukuDariForm());
        btnDelete.addActionListener(e -> hapusBukuTerpilih());
        btnRefresh.addActionListener(e -> muatDataKeTabel());

        return panel;
    }

    private JPanel createPlaceholderPanel(String title) {
        JPanel p = new JPanel(new BorderLayout());
        p.setBackground(TEAL_BG);
        JLabel lbl = new JLabel(title + " (belum diimplementasikan)");
        lbl.setHorizontalAlignment(SwingConstants.CENTER);
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lbl.setForeground(Color.WHITE);
        p.add(lbl, BorderLayout.CENTER);
        return p;
    }

    private JToggleButton createTabButton(String text) {
        JToggleButton btn = new JToggleButton(text);
        btn.setFont(FONT_TAB);
        btn.setBackground(TAB_BG);
        btn.setForeground(Color.BLACK);
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(TAB_BORDER, 1, true),
                BorderFactory.createEmptyBorder(6, 20, 6, 20)
        ));
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.addChangeListener(e -> {
            if (btn.isSelected()) btn.setBackground(Color.WHITE);
            else btn.setBackground(TAB_BG);
        });
        return btn;
    }

    private JButton createIconButton(String imageFileName) {
        ImageIcon icon = new ImageIcon("Image/" + imageFileName);

        if (icon.getIconWidth() <= 0) {
            JButton fallback = new JButton("?");
            fallback.setFont(new Font("Segoe UI", Font.BOLD, 20));
            fallback.setPreferredSize(new Dimension(60, 60));
            fallback.setFocusPainted(false);
            fallback.setBorder(BorderFactory.createLineBorder(new Color(150, 150, 150)));
            fallback.setBackground(Color.WHITE);
            fallback.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            return fallback;
        }

        Image img = icon.getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(img);

        JButton btn = new JButton(scaledIcon);
        btn.setPreferredSize(new Dimension(60, 60));
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createLineBorder(new Color(150, 150, 150)));
        btn.setBackground(Color.WHITE);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setContentAreaFilled(true);
        return btn;
    }

    private void styleField(JTextField field) {
        field.setFont(FONT_LABEL);
        field.setBackground(new Color(232, 236, 235));
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(180, 190, 190)),
                BorderFactory.createEmptyBorder(6, 8, 6, 8)
        ));
    }

    private void addLabelBig(JPanel panel, GridBagConstraints gbc, String text) {
        JLabel lbl = new JLabel(text);
        lbl.setFont(FONT_LABEL_BIG);
        lbl.setForeground(Color.BLACK);
        panel.add(lbl, gbc);
    }

    private void addLabelNormal(JPanel panel, GridBagConstraints gbc, String text) {
        JLabel lbl = new JLabel(text);
        lbl.setFont(FONT_LABEL);
        lbl.setForeground(Color.BLACK);
        panel.add(lbl, gbc);
    }

    private static class BackgroundPanel extends JPanel {
        private final Image bg;
        private final Color overlayColor;
        private final float overlayAlpha;

        public BackgroundPanel(Image bg, Color overlayColor, float overlayAlpha) {
            this.bg = bg;
            this.overlayColor = overlayColor;
            this.overlayAlpha = overlayAlpha;
            setOpaque(false);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (bg != null) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                int w = getWidth();
                int h = getHeight();

                g2.drawImage(bg, 0, 0, w, h, this);

                if (overlayColor != null && overlayAlpha > 0f && overlayAlpha <= 1f) {
                    g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, overlayAlpha));
                    g2.setColor(overlayColor);
                    g2.fillRect(0, 0, w, h);
                }

                g2.dispose();
            }
        }
    }

    private void makeTableTransparent(JTable table, JScrollPane scroll) {
        table.setOpaque(false);
        scroll.setOpaque(false);
        scroll.getViewport().setOpaque(false);

        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer() {
            private final Color normalBg   = new Color(51, 142, 127, 170);
            private final Color selectedBg = new Color(0, 70, 80, 200);

            @Override
            public Component getTableCellRendererComponent(JTable tbl, Object value,
                                                           boolean isSelected, boolean hasFocus,
                                                           int row, int column) {
                Component c = super.getTableCellRendererComponent(tbl, value, isSelected, hasFocus, row, column);
                if (c instanceof JComponent) {
                    JComponent jc = (JComponent) c;
                    if (isSelected) {
                        jc.setBackground(selectedBg);
                    } else {
                        jc.setBackground(normalBg);
                    }
                    jc.setForeground(Color.WHITE);
                    jc.setOpaque(true);
                }
                return c;
            }
        };

        for (int i = 0; i < table.getColumnModel().getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(renderer);
        }
    }

    private void muatDataKeTabel() {
        modelBuku.setRowCount(0);
        List<Buku> list = tokoBuku.getInventoriBuku();
        for (Buku b : list) {
            modelBuku.addRow(new Object[]{
                    b.getIdBuku(),
                    b.getJudul(),
                    b.getPenulis(),
                    b.getPenerbit(),
                    b.getTahunTerbit(),
                    b.getHarga(),
                    b.getStok(),
                    b.getKategori().getNamaKategori()
            });
        }
    }

    private void tambahBukuDariForm() {
        try {
            String id = txtId.getText().trim();
            String judul = txtJudul.getText().trim();
            String penulis = txtPenulis.getText().trim();
            String penerbit = txtPenerbit.getText().trim();
            int tahun = Integer.parseInt(txtTahun.getText().trim());
            double harga = Double.parseDouble(txtHarga.getText().trim());
            int stok = Integer.parseInt(txtStok.getText().trim());
            String idKat = txtIdKategori.getText().trim();
            String namaKat = txtNamaKategori.getText().trim();

            if (id.isEmpty() || judul.isEmpty()) {
                JOptionPane.showMessageDialog(this, "ID dan Judul wajib diisi.",
                        "Validasi", JOptionPane.WARNING_MESSAGE);
                return;
            }

            KategoriBuku kategori = new KategoriBuku(idKat, namaKat, "");
            Buku b = new Buku(id, judul, penulis, penerbit, tahun, harga, stok, kategori);
            tokoBuku.tambahBuku(b);
            muatDataKeTabel();
            bersihkanForm();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this,
                    "Tahun, Harga, dan Stok harus angka.",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void hapusBukuTerpilih() {
        int row = tblBuku.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this,
                    "Pilih dulu buku yang akan dihapus.",
                    "Info", JOptionPane.WARNING_MESSAGE);
            return;
        }
        String idBuku = (String) modelBuku.getValueAt(row, 0);
        List<Buku> list = tokoBuku.getInventoriBuku();
        Buku target = null;
        for (Buku b : list) {
            if (b.getIdBuku().equals(idBuku)) {
                target = b;
                break;
            }
        }
        if (target != null) {
            int konfirmasi = JOptionPane.showConfirmDialog(this,
                    "Yakin mau hapus \"" + target.getJudul() + "\" ?",
                    "Konfirmasi", JOptionPane.YES_NO_OPTION);
            if (konfirmasi == JOptionPane.YES_OPTION) {
                list.remove(target);
                muatDataKeTabel();
            }
        }
    }

    private void bersihkanForm() {
        txtId.setText("");
        txtJudul.setText("");
        txtPenulis.setText("");
        txtPenerbit.setText("");
        txtTahun.setText("");
        txtHarga.setText("");
        txtStok.setText("");
        txtIdKategori.setText("");
        txtNamaKategori.setText("");
    }
}
