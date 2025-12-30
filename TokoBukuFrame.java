import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.text.*;

import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

public class TokoBukuFrame extends JFrame {

    private TokoBuku tokoBuku;
    private UserSystem currentUser;

    // ====== BUKU ======
    private JTable tblBuku;
    private DefaultTableModel modelBuku;

    private JTextField txtId;
    private JTextField txtJudul;
    private JTextField txtPenulis;
    private JTextField txtTahun;
    private JTextField txtHarga;
    private JTextField txtStok;

    private JComboBox<String> cbPenerbit;
    private JComboBox<KategoriBuku> cbKategori;

    private final List<KategoriBuku> masterKategori = new ArrayList<>();
    private final List<String> masterPenerbit = new ArrayList<>();

    // ====== TRANSAKSI ======
    private JTable tblKeranjang;
    private DefaultTableModel modelKeranjang;
    private JLabel lblTotal;

    private JTable tblInventoriTrx;
    private DefaultTableModel modelInventoriTrx;

    private JTextField txtBayar;
    private JLabel lblKembalian;

    // pelanggan transaksi
    private JComboBox<Pelanggan> cbPelangganTrx;

    // ====== RIWAYAT TRANSAKSI (UNTUK LAPORAN) ======
    private static class TrxRecord {
        String kode;
        Date tanggal;
        String kasir;
        String pelanggan;
        long total;
        long bayar;
        long kembalian;

        TrxRecord(String kode, Date tanggal, String kasir, String pelanggan, long total, long bayar, long kembalian) {
            this.kode = kode;
            this.tanggal = tanggal;
            this.kasir = kasir;
            this.pelanggan = pelanggan;
            this.total = total;
            this.bayar = bayar;
            this.kembalian = kembalian;
        }
    }

    private final List<TrxRecord> riwayatTransaksi = new ArrayList<>();

    // ====== LAPORAN ======
    private JTable tblLaporanStok;
    private DefaultTableModel modelLaporanStok;

    private JTable tblLaporanTrx;
    private DefaultTableModel modelLaporanTrx;

    private JLabel lblTotalJudul;
    private JLabel lblTotalStok;
    private JLabel lblNilaiStok;

    private JLabel lblTotalTransaksi;
    private JLabel lblOmset;

    // ====== PELANGGAN ======
    private final List<Pelanggan> masterPelanggan = new ArrayList<>();

    private JTable tblPelanggan;
    private DefaultTableModel modelPelanggan;

    private JTextField txtIdPelanggan;
    private JTextField txtNamaPelanggan;
    private JTextField txtNoTelpPelanggan;
    private JTextArea txtAlamatPelanggan;

    // ====== NAV ======
    private CardLayout contentLayout;
    private JPanel contentPanel;

    private Image userBgImage;

    // ===== THEME =====
    private final Color NAVBAR_BG       = new Color(228, 228, 228);
    private final Color TEAL_BG         = new Color(41, 117, 112);
    private final Color FORM_CARD_BG    = new Color(204, 230, 228);
    private final Color TAB_BG          = new Color(245, 245, 245);
    private final Color TAB_BORDER      = new Color(180, 180, 180);
    private final Color TABLE_HEADER_BG = new Color(10, 34, 57);
    private final Color TABLE_ROW_BG    = new Color(51, 142, 127);
    private final Color TABLE_GRID      = new Color(0, 90, 80);

    private final Font FONT_TITLE     = new Font("Segoe UI", Font.BOLD, 18);
    private final Font FONT_LABEL_BIG = new Font("Segoe UI", Font.BOLD, 18);
    private final Font FONT_LABEL     = new Font("Segoe UI", Font.PLAIN, 13);
    private final Font FONT_TAB       = new Font("Segoe UI", Font.BOLD, 16);
    private final Font FONT_TABLE     = new Font("Segoe UI", Font.PLAIN, 13);

    public TokoBukuFrame(UserSystem user) {
        this.currentUser = user;
        this.tokoBuku = new TokoBuku();

        if (currentUser != null && "PEMBELI".equalsIgnoreCase(currentUser.getRole())) {
            ImageIcon bgIcon = new ImageIcon("Image/bahlil.jpg");
            if (bgIcon.getIconWidth() > 0) userBgImage = bgIcon.getImage();
        }

        initDataContoh();
        initUI();
        muatDataKeTabel();
        refreshMasterDataCombos();
        refreshPelangganTable();
        refreshLaporan();
        refreshInventoriTransaksi();
    }

    public TokoBukuFrame() {
        this(null);
    }

    private void initDataContoh() {
        masterKategori.clear();
        masterKategori.add(new KategoriBuku("K001", "Fiksi", "Novel, cerpen, cerita fiksi"));
        masterKategori.add(new KategoriBuku("K002", "Non-Fiksi", "Biografi, sejarah, fakta"));
        masterKategori.add(new KategoriBuku("K003", "Pendidikan", "Pelajaran, modul, akademik"));
        masterKategori.add(new KategoriBuku("K004", "Referensi", "Kamus, ensiklopedia, panduan"));
        masterKategori.add(new KategoriBuku("K005", "Hiburan", "Komik, humor, ringan"));
        masterKategori.add(new KategoriBuku("K006", "Agama", "Kajian, tuntunan, religi"));
        masterKategori.add(new KategoriBuku("K007", "Anak-anak", "Buku anak, cerita bergambar"));
        masterKategori.add(new KategoriBuku("K008", "E-book", "Buku digital"));

        masterPenerbit.clear();
        Collections.addAll(masterPenerbit,
                "Greenbook.ID",
                "Gramedia Pustaka Utama",
                "Mizan Publishing",
                "Penerbit Erlangga",
                "Kepustakaan Populer Gramedia (KPG)",
                "Bentang Pustaka",
                "Pustaka Alvabet",
                "GagasMedia",
                "Pustaka Hidayah",
                "Penerbit Republika",
                "Penerbit Buku Kompas",
                "Serambi Ilmu Semesta",
                "Penerbit Ombak",
                "PT Grasindo",
                "Kepustakaan Popular Gramedia (KPG Kids)",
                "PT Elex Media Komputindo",
                "Qanita",
                "Media Kita",
                "Bentang Belia",
                "Elex Media Komputindo (EMK Press)",
                "Matahati",
                "PT Visimedia",
                "Bentang Budaya",
                "PT Grafindo Media Pratama",
                "BIP (Bumi Aksara)",
                "Penebar Swadaya",
                "PT RajaGrafindo Persada",
                "Araska Publisher",
                "Buku Fixi",
                "Mahaka Media",
                "KPG Novel",
                "Elex Media Komputindo (Level Comics)",
                "Indiva Media Kreasi",
                "PT. Gagas Media",
                "Marjin Kiri"
        );

        KategoriBuku fiksi = masterKategori.get(0);
        KategoriBuku nonFiksi = masterKategori.get(1);

        tokoBuku.tambahBuku(new Buku("B001", "Laskar Pelangi", "Andrea Hirata",
                "Bentang Pustaka", 2005, 75000, 10, fiksi));

        tokoBuku.tambahBuku(new Buku("B002", "Sapiens", "Yuval Noah Harari",
                "Gramedia Pustaka Utama", 2017, 120000, 5, nonFiksi));

        tokoBuku.tambahBuku(new Buku("B003", "Bumi Manusia", "Pramoedya A. Toer",
                "Hasta Mitra", 1980, 85000, 7, fiksi));

        // pelanggan contoh
        masterPelanggan.clear();
        masterPelanggan.add(new Pelanggan(1, "Budi", "Jakarta", "08123456789"));
        masterPelanggan.add(new Pelanggan(2, "Siti", "Bandung", "08234567890"));
        masterPelanggan.add(new Pelanggan(3, "Andi", "Surabaya", "08345678901"));
    }

    private void initUI() {
        setTitle("TOKO buku GEN Z - Dashboard");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1100, 650);
        setLocationRelativeTo(null);

        JPanel root = new JPanel(new BorderLayout());
        root.setBackground(TEAL_BG);
        setContentPane(root);

        // ===== NAVBAR =====
        JPanel navbar = new JPanel(new BorderLayout());
        navbar.setBackground(NAVBAR_BG);
        navbar.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, TAB_BORDER));

        JPanel logoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        logoPanel.setOpaque(false);

        JPanel logoBox = new JPanel() {
            @Override protected void paintComponent(Graphics g) {
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
        JToggleButton tabTransaksi = createTabButton("Transaksi");
        JToggleButton tabPelanggan = createTabButton("Pelanggan");
        JToggleButton tabLaporan = createTabButton("Laporan");

        boolean isPembeli = (currentUser != null && "PEMBELI".equalsIgnoreCase(currentUser.getRole()));

        // pembeli tidak boleh lihat pelanggan & laporan
        tabPelanggan.setVisible(!isPembeli);
        tabLaporan.setVisible(!isPembeli);

        ButtonGroup group = new ButtonGroup();
        group.add(tabBuku);
        group.add(tabTransaksi);
        group.add(tabPelanggan);
        group.add(tabLaporan);

        tabBuku.setSelected(true);

        tabPanel.add(tabBuku);
        tabPanel.add(tabTransaksi);
        if (!isPembeli) {
            tabPanel.add(tabPelanggan);
            tabPanel.add(tabLaporan);
        }

        JPanel profilePanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 5));
        profilePanel.setOpaque(false);

        String profileText = "👤 ";
        if (currentUser != null) profileText += currentUser.getUsername() + " (" + currentUser.getRole() + ")";
        else profileText += "Guest";

        JLabel lblProfile = new JLabel(profileText);
        lblProfile.setFont(FONT_LABEL);
        profilePanel.add(lblProfile);

        navbar.add(logoPanel, BorderLayout.WEST);
        navbar.add(tabPanel, BorderLayout.CENTER);
        navbar.add(profilePanel, BorderLayout.EAST);
        root.add(navbar, BorderLayout.NORTH);

        // ===== CONTENT =====
        contentLayout = new CardLayout();
        contentPanel = new JPanel(contentLayout);
        contentPanel.setBackground(TEAL_BG);

        JPanel bukuPanel = (isPembeli && userBgImage != null) ? createBukuPanel(userBgImage) : createBukuPanel(null);
        JPanel transaksiPanel = createTransaksiPanel();

        contentPanel.add(bukuPanel, "BUKU");
        contentPanel.add(transaksiPanel, "TRANSAKSI");

        if (!isPembeli) {
            contentPanel.add(createPelangganPanel(), "PELANGGAN");
            contentPanel.add(createLaporanPanel(), "LAPORAN");
        }

        root.add(contentPanel, BorderLayout.CENTER);

        tabBuku.addActionListener(e -> contentLayout.show(contentPanel, "BUKU"));

        tabTransaksi.addActionListener(e -> {
            refreshInventoriTransaksi();
            contentLayout.show(contentPanel, "TRANSAKSI");
        });

        if (!isPembeli) {
            tabPelanggan.addActionListener(e -> {
                refreshPelangganTable();
                contentLayout.show(contentPanel, "PELANGGAN");
            });

            tabLaporan.addActionListener(e -> {
                refreshLaporan();
                contentLayout.show(contentPanel, "LAPORAN");
            });
        }
    }

    // =========================
    // PANEL BUKU
    // =========================
    private JPanel createBukuPanel(Image bgImage) {
        JPanel panel;
        if (bgImage != null) panel = new BackgroundPanel(bgImage, TEAL_BG, 0.7f);
        else {
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
        txtTahun = new JTextField();
        txtHarga = new JTextField();
        txtStok = new JTextField();

        styleField(txtId);
        styleField(txtJudul);
        styleField(txtPenulis);
        styleField(txtTahun);
        styleField(txtHarga);
        styleField(txtStok);

        // === FILTER ANGKA (BIAR TIDAK BISA HURUF) ===
        setDigitsOnly(txtTahun);
        setDigitsOnly(txtStok);
        setDecimalOnly(txtHarga);

        cbPenerbit = new JComboBox<>();
        styleComboString(cbPenerbit);

        cbKategori = new JComboBox<>();
        styleComboKategori(cbKategori);

        cbKategori.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                                                         boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof KategoriBuku) {
                    KategoriBuku k = (KategoriBuku) value;
                    String text = k.getNamaKategori();
                    if (text == null || text.isEmpty()) text = "-- Pilih Kategori --";
                    setText(text);
                }
                return this;
            }
        });

        cbKategori.addActionListener(e -> applyKategoriLogic());

        gbc.weightx = 0;
        gbc.gridx = 0;
        gbc.gridy = 0; addLabelBig(formCard, gbc, "ID Buku");
        gbc.gridy++;    addLabelBig(formCard, gbc, "Penulis");
        gbc.gridy++;    addLabelBig(formCard, gbc, "Tahun Terbit");
        gbc.gridy++;    addLabelBig(formCard, gbc, "Stok");

        gbc.weightx = 1;
        gbc.gridx = 1;
        gbc.gridy = 0; formCard.add(txtId, gbc);
        gbc.gridy++;    formCard.add(txtPenulis, gbc);
        gbc.gridy++;    formCard.add(txtTahun, gbc);
        gbc.gridy++;    formCard.add(txtStok, gbc);

        gbc.weightx = 0;
        gbc.gridx = 2;
        gbc.gridy = 0; addLabelNormal(formCard, gbc, "Judul");
        gbc.gridy++;    addLabelNormal(formCard, gbc, "Kategori");   // ⬅ pindah ke atas
        gbc.gridy++;    addLabelNormal(formCard, gbc, "Harga");
        gbc.gridy++;    addLabelNormal(formCard, gbc, "Penerbit");   // ⬅ pindah ke bawah

        gbc.weightx = 1;
        gbc.gridx = 3;
        gbc.gridy = 0; formCard.add(txtJudul, gbc);
        gbc.gridy++;    formCard.add(cbKategori, gbc);               // ⬅ kategori di sini
        gbc.gridy++;    formCard.add(txtHarga, gbc);
        gbc.gridy++;    formCard.add(cbPenerbit, gbc);               // ⬅ penerbit di sini

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

        String[] kolom = {"ID", "Judul", "Penulis", "Penerbit", "Tahun", "Harga", "Stok", "Kategori"};
        modelBuku = new DefaultTableModel(kolom, 0) {
            @Override public boolean isCellEditable(int row, int column) { return false; }
        };
        tblBuku = new JTable(modelBuku);
        styleTable(tblBuku);

        JScrollPane scroll = new JScrollPane(tblBuku);
        scroll.getViewport().setBackground(TABLE_ROW_BG);
        tableWrapper.add(scroll, BorderLayout.CENTER);

        boolean isUserWithBg = (currentUser != null &&
                "PEMBELI".equalsIgnoreCase(currentUser.getRole()) &&
                bgImage != null);
        if (isUserWithBg) makeTableTransparent(tblBuku, scroll);

        panel.add(tableWrapper, BorderLayout.CENTER);

        if (currentUser != null && "PEMBELI".equalsIgnoreCase(currentUser.getRole())) {
            formWrapper.setVisible(false);
        }

        btnAdd.addActionListener(e -> {
            tambahBukuDariForm();
            // setelah tambah buku -> inventori transaksi ikut refresh
            refreshInventoriTransaksi();
        });

        btnDelete.addActionListener(e -> {
            hapusBukuTerpilih();
            refreshInventoriTransaksi();
        });

        btnRefresh.addActionListener(e -> {
            muatDataKeTabel();
            refreshMasterDataCombos();
            refreshInventoriTransaksi();
            refreshLaporan();
        });

        return panel;
    }

    private void refreshMasterDataCombos() {
        DefaultComboBoxModel<String> penerbitModel = new DefaultComboBoxModel<>();
        penerbitModel.addElement("-- Pilih Penerbit --");
        for (String p : masterPenerbit) penerbitModel.addElement(p);
        cbPenerbit.setModel(penerbitModel);

        DefaultComboBoxModel<KategoriBuku> kategoriModel = new DefaultComboBoxModel<>();
        kategoriModel.addElement(new KategoriBuku("", "-- Pilih Kategori --", ""));
        for (KategoriBuku k : masterKategori) kategoriModel.addElement(k);
        cbKategori.setModel(kategoriModel);

        cbPenerbit.setSelectedIndex(0);
        cbKategori.setSelectedIndex(0);
    }

    private void muatDataKeTabel() {
        if (modelBuku == null) return;
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
                    (b.getKategori() != null ? b.getKategori().getNamaKategori() : "-")
            });
        }
    }

private void tambahBukuDariForm() {
    try {
        String id = txtId.getText().trim();
        String judul = txtJudul.getText().trim();
        String penulis = txtPenulis.getText().trim();
        String tahunStr = txtTahun.getText().trim();
        String hargaStr = txtHarga.getText().trim();
        String stokStr  = txtStok.getText().trim();

        // ===== VALIDASI WAJIB =====
        if (id.isEmpty() || judul.isEmpty()) {
            JOptionPane.showMessageDialog(this, "ID dan Judul wajib diisi.",
                    "Validasi", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // ✅ Validasi ID unik
        if (isIdBukuSudahAda(id)) {
            JOptionPane.showMessageDialog(this,
                    "ID Buku \"" + id + "\" sudah digunakan. Gunakan ID lain.",
                    "Validasi", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // ===== VALIDASI ANGKA (tahun/harga/stok tidak boleh huruf) =====
        if (!tahunStr.matches("\\d+")) {
            JOptionPane.showMessageDialog(this, "Tahun harus berupa angka.",
                    "Validasi", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (!hargaStr.matches("\\d+(\\.\\d+)?")) { // boleh 75000 atau 75000.5
            JOptionPane.showMessageDialog(this, "Harga harus berupa angka.",
                    "Validasi", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (!stokStr.matches("\\d+")) {
            JOptionPane.showMessageDialog(this, "Stok harus berupa angka.",
                    "Validasi", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int tahun = Integer.parseInt(tahunStr);
        double harga = Double.parseDouble(hargaStr);
        int stok = Integer.parseInt(stokStr);

        String penerbit = (String) cbPenerbit.getSelectedItem();
        if (penerbit == null || penerbit.startsWith("--")) {
            JOptionPane.showMessageDialog(this, "Pilih penerbit dulu.",
                    "Validasi", JOptionPane.WARNING_MESSAGE);
            return;
        }

        KategoriBuku kategori = (KategoriBuku) cbKategori.getSelectedItem();
        if (kategori == null || kategori.getNamaKategori() == null ||
                kategori.getNamaKategori().trim().isEmpty() ||
                kategori.getNamaKategori().startsWith("--")) {
            JOptionPane.showMessageDialog(this, "Pilih kategori dulu.",
                    "Validasi", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // ===== SIMPAN =====
        Buku b = new Buku(id, judul, penulis, penerbit, tahun, harga, stok, kategori);
        tokoBuku.tambahBuku(b);

        // refresh UI
        muatDataKeTabel();
        refreshMasterDataCombos();
        bersihkanForm();

        // kalau kamu punya laporan admin
        try { refreshLaporan(); } catch (Exception ignored) {}

    } catch (Exception ex) {
        JOptionPane.showMessageDialog(this,
                "Input tidak valid. Periksa kembali data.",
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
            if (b.getIdBuku().equals(idBuku)) { target = b; break; }
        }
        if (target != null) {
            int konfirmasi = JOptionPane.showConfirmDialog(this,
                    "Yakin mau hapus \"" + target.getJudul() + "\" ?",
                    "Konfirmasi", JOptionPane.YES_NO_OPTION);
            if (konfirmasi == JOptionPane.YES_OPTION) {
                list.remove(target);
                muatDataKeTabel();
                refreshMasterDataCombos();
                refreshLaporan();
            }
        }
    }

    private void bersihkanForm() {
        txtId.setText("");
        txtJudul.setText("");
        txtPenulis.setText("");
        txtTahun.setText("");
        txtHarga.setText("");
        txtStok.setText("");
        if (cbPenerbit != null) cbPenerbit.setSelectedIndex(0);
        if (cbKategori != null) cbKategori.setSelectedIndex(0);
    }

    private void applyKategoriLogic() {
        KategoriBuku k = (KategoriBuku) cbKategori.getSelectedItem();
        if (k != null && "E-book".equalsIgnoreCase(k.getNamaKategori())) {
            cbPenerbit.setModel(new DefaultComboBoxModel<>(new String[]{"Digital / E-book"}));
            cbPenerbit.setEnabled(false);
        } else {
            cbPenerbit.setEnabled(true);
            DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();
            model.addElement("-- Pilih Penerbit --");
            for (String p : masterPenerbit) model.addElement(p);
            cbPenerbit.setModel(model);
            cbPenerbit.setSelectedIndex(0);
        }
    }

    // =========================
    // PANEL TRANSAKSI (FIX QTY + BAYAR/KEMBALIAN + PELANGGAN + REFRESH)
    // =========================
    private JPanel createTransaksiPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(TEAL_BG);
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JLabel title = new JLabel("Transaksi Penjualan");
        title.setFont(new Font("Segoe UI", Font.BOLD, 20));
        title.setForeground(Color.WHITE);

        JButton btnRefresh = new JButton("Refresh");
        btnRefresh.setFont(FONT_LABEL);

        JPanel header = new JPanel(new BorderLayout());
        header.setOpaque(false);
        header.add(title, BorderLayout.WEST);
        header.add(btnRefresh, BorderLayout.EAST);
        panel.add(header, BorderLayout.NORTH);

        JPanel center = new JPanel(new GridBagLayout());
        center.setOpaque(false);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weighty = 1;

        // ===== INVENTORI =====
        modelInventoriTrx = new DefaultTableModel(
                new String[]{"ID", "Judul", "Harga", "Stok", "Kategori"}, 0
        ) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };

        tblInventoriTrx = new JTable(modelInventoriTrx);
        styleTable(tblInventoriTrx);

        JScrollPane spInventori = new JScrollPane(tblInventoriTrx);
        spInventori.getViewport().setBackground(TABLE_ROW_BG);

        // isi awal
        refreshInventoriTransaksi();

        // ===== KERANJANG =====
        modelKeranjang = new DefaultTableModel(
                new String[]{"ID", "Judul", "Qty", "Harga", "Subtotal"}, 0
        ) {
            @Override public boolean isCellEditable(int r, int c) { return c == 2; } // Qty edit
        };

        tblKeranjang = new JTable(modelKeranjang);
        styleTable(tblKeranjang);

        JScrollPane spKeranjang = new JScrollPane(tblKeranjang);
        spKeranjang.getViewport().setBackground(TABLE_ROW_BG);

        gbc.gridx = 0; gbc.gridy = 0; gbc.weightx = 1;
        center.add(wrapCard("Daftar Buku", spInventori), gbc);

        gbc.gridx = 1; gbc.gridy = 0; gbc.weightx = 1;
        center.add(wrapCard("Keranjang", spKeranjang), gbc);

        panel.add(center, BorderLayout.CENTER);

        // ===== BOTTOM =====
        JButton btnTambah = new JButton("Tambah ke Keranjang");
        JButton btnHapus  = new JButton("Hapus Item");
        JButton btnBayar  = new JButton("Bayar");

        btnTambah.setFont(FONT_LABEL);
        btnHapus.setFont(FONT_LABEL);
        btnBayar.setFont(FONT_LABEL);

        lblTotal = new JLabel("Total: Rp 0");
        lblTotal.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblTotal.setForeground(Color.WHITE);

        JLabel lblPelanggan = new JLabel("Pelanggan:");
        lblPelanggan.setFont(FONT_LABEL);
        lblPelanggan.setForeground(Color.WHITE);

        cbPelangganTrx = new JComboBox<>();
        cbPelangganTrx.setFont(FONT_LABEL);
        cbPelangganTrx.setBackground(Color.WHITE);
        cbPelangganTrx.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                                                          boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof Pelanggan p) {
                    setText(p.getIdPelanggan() + " - " + p.getNama());
                }
                return this;
            }
        });
        refreshComboPelangganTransaksi();

        JLabel lblBayarText = new JLabel("Bayar:");
        lblBayarText.setFont(FONT_LABEL);
        lblBayarText.setForeground(Color.WHITE);

        txtBayar = new JTextField(10);
        txtBayar.setFont(FONT_LABEL);
        setDigitsOnly(txtBayar);

        JButton btnHitung = new JButton("Hitung");
        btnHitung.setFont(FONT_LABEL);

        lblKembalian = new JLabel("Kembalian: Rp 0");
        lblKembalian.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblKembalian.setForeground(Color.WHITE);

        JPanel action = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 8));
        action.setOpaque(false);

        action.add(lblPelanggan);
        action.add(cbPelangganTrx);
        action.add(lblTotal);
        action.add(lblBayarText);
        action.add(txtBayar);
        action.add(btnHitung);
        action.add(lblKembalian);
        action.add(btnTambah);
        action.add(btnHapus);
        action.add(btnBayar);

        panel.add(action, BorderLayout.SOUTH);

        // ===== EVENTS =====

        btnRefresh.addActionListener(e -> {
            refreshInventoriTransaksi();
            muatDataKeTabel();
            refreshComboPelangganTransaksi();
            refreshLaporan();
        });

        btnHitung.addActionListener(e -> {
            long total = getTotalKeranjangAsLong();
            Long bayar = parseLongSafe(txtBayar.getText().trim());
            if (bayar == null) {
                JOptionPane.showMessageDialog(this, "Bayar harus angka.", "Validasi", JOptionPane.WARNING_MESSAGE);
                return;
            }
            long kembali = bayar - total;
            if (kembali < 0) {
                JOptionPane.showMessageDialog(this, "Uang bayar kurang.", "Validasi", JOptionPane.WARNING_MESSAGE);
                lblKembalian.setText("Kembalian: Rp 0");
                return;
            }
            lblKembalian.setText("Kembalian: Rp " + kembali);
        });

        // === TAMBAH (FIX: Qty LANGSUNG UPDATE) ===
        btnTambah.addActionListener(e -> {
            int row = tblInventoriTrx.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(this, "Pilih buku dulu.", "Info", JOptionPane.WARNING_MESSAGE);
                return;
            }

            String id = modelInventoriTrx.getValueAt(row, 0).toString();
            String judul = modelInventoriTrx.getValueAt(row, 1).toString();
            double harga = Double.parseDouble(modelInventoriTrx.getValueAt(row, 2).toString());
            int stok = Integer.parseInt(modelInventoriTrx.getValueAt(row, 3).toString());

            if (stok <= 0) {
                JOptionPane.showMessageDialog(this, "Stok habis.", "Info", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // kalau sudah ada -> qty++ langsung
            for (int i = 0; i < modelKeranjang.getRowCount(); i++) {
                if (modelKeranjang.getValueAt(i, 0).toString().equals(id)) {
                    int qtyLama = Integer.parseInt(modelKeranjang.getValueAt(i, 2).toString());
                    int qtyBaru = qtyLama + 1;

                    if (qtyBaru > stok) {
                        JOptionPane.showMessageDialog(this, "Qty melebihi stok.", "Info", JOptionPane.WARNING_MESSAGE);
                        return;
                    }

                    modelKeranjang.setValueAt(qtyBaru, i, 2);
                    modelKeranjang.setValueAt(qtyBaru * harga, i, 4);
                    recalcAllSubtotalsAndTotal();
                    tblKeranjang.setRowSelectionInterval(i, i);
                    return;
                }
            }

            // item baru
            modelKeranjang.addRow(new Object[]{id, judul, 1, harga, harga});
            recalcAllSubtotalsAndTotal();
        });

        btnHapus.addActionListener(e -> {
            int row = tblKeranjang.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(this, "Pilih item keranjang dulu.", "Info", JOptionPane.WARNING_MESSAGE);
                return;
            }
            modelKeranjang.removeRow(row);
            recalcAllSubtotalsAndTotal();
        });

        // qty edit manual -> langsung update subtotal/total
        modelKeranjang.addTableModelListener(new TableModelListener() {
            private boolean isAdjusting = false;

            @Override
            public void tableChanged(TableModelEvent e) {
                if (isAdjusting) return;
                if (e.getType() != TableModelEvent.UPDATE) return;
                if (e.getColumn() != 2) return;

                int r = e.getFirstRow();
                if (r < 0) return;

                try {
                    String id = modelKeranjang.getValueAt(r, 0).toString();
                    Buku b = cariBukuById(id);
                    int stokMax = (b != null) ? b.getStok() : Integer.MAX_VALUE;

                    int qty = Integer.parseInt(modelKeranjang.getValueAt(r, 2).toString());
                    if (qty < 1) qty = 1;
                    if (qty > stokMax) qty = stokMax;

                    isAdjusting = true;
                    modelKeranjang.setValueAt(qty, r, 2);
                    recalcRowSubtotal(r);
                    isAdjusting = false;

                    recalcAllSubtotalsAndTotal();
                } catch (Exception ex) {
                    isAdjusting = true;
                    modelKeranjang.setValueAt(1, r, 2);
                    recalcRowSubtotal(r);
                    isAdjusting = false;

                    recalcAllSubtotalsAndTotal();
                }
            }
        });

        btnBayar.addActionListener(e -> {
            if (modelKeranjang.getRowCount() == 0) {
                JOptionPane.showMessageDialog(this, "Keranjang kosong.", "Info", JOptionPane.WARNING_MESSAGE);
                return;
            }

            Pelanggan pelanggan = (Pelanggan) cbPelangganTrx.getSelectedItem();
            if (pelanggan == null) {
                JOptionPane.showMessageDialog(this, "Pilih pelanggan dulu.", "Validasi", JOptionPane.WARNING_MESSAGE);
                return;
            }

            long total = getTotalKeranjangAsLong();

            Long bayar = parseLongSafe(txtBayar.getText().trim());
            if (bayar == null) {
                JOptionPane.showMessageDialog(this, "Bayar harus angka.", "Validasi", JOptionPane.WARNING_MESSAGE);
                return;
            }

            long kembali = bayar - total;
            if (kembali < 0) {
                JOptionPane.showMessageDialog(this, "Uang bayar kurang.", "Validasi", JOptionPane.WARNING_MESSAGE);
                return;
            }


            // validasi stok & kurangi stok
            for (int i = 0; i < modelKeranjang.getRowCount(); i++) {
                String id = modelKeranjang.getValueAt(i, 0).toString();
                int qty = Integer.parseInt(modelKeranjang.getValueAt(i, 2).toString());

                Buku b = cariBukuById(id);
                if (b == null) continue;

                if (qty > b.getStok()) {
                    JOptionPane.showMessageDialog(this,
                            "Qty untuk \"" + b.getJudul() + "\" melebihi stok.",
                            "Validasi", JOptionPane.WARNING_MESSAGE);
                    return;
                }
            }

            // kurangi stok setelah validasi ok
            for (int i = 0; i < modelKeranjang.getRowCount(); i++) {
                String id = modelKeranjang.getValueAt(i, 0).toString();
                int qty = Integer.parseInt(modelKeranjang.getValueAt(i, 2).toString());
                Buku b = cariBukuById(id);
                if (b != null) b.setStok(Math.max(0, b.getStok() - qty));
            }

            // simpan riwayat untuk laporan
            String kode = generateKodeTransaksi();
            Date tgl = new Date();
            String kasir = getNamaKasir();

            riwayatTransaksi.add(new TrxRecord(
                    kode, tgl, kasir, pelanggan.getNama(), total, bayar, kembali
            ));

            JOptionPane.showMessageDialog(this,
                    "Pembayaran berhasil!\n" +
                            "Pelanggan: " + pelanggan.getNama() + "\n" +
                            "Total: Rp " + total + "\n" +
                            "Bayar: Rp " + bayar + "\n" +
                            "Kembalian: Rp " + kembali,
                    "Sukses", JOptionPane.INFORMATION_MESSAGE);

            // reset
            modelKeranjang.setRowCount(0);
            recalcAllSubtotalsAndTotal();
            txtBayar.setText("");
            lblKembalian.setText("Kembalian: Rp 0");

            // refresh UI lain
            muatDataKeTabel();
            refreshInventoriTransaksi();
            refreshLaporan();
        });

        return panel;
    }

    private void refreshComboPelangganTransaksi() {
        if (cbPelangganTrx == null) return;
        DefaultComboBoxModel<Pelanggan> m = new DefaultComboBoxModel<>();
        for (Pelanggan p : masterPelanggan) m.addElement(p);
        cbPelangganTrx.setModel(m);
        if (m.getSize() > 0) cbPelangganTrx.setSelectedIndex(0);
    }

    private void refreshInventoriTransaksi() {
        if (modelInventoriTrx == null) return;
        modelInventoriTrx.setRowCount(0);
        for (Buku b : tokoBuku.getInventoriBuku()) {
            modelInventoriTrx.addRow(new Object[]{
                    b.getIdBuku(),
                    b.getJudul(),
                    b.getHarga(),
                    b.getStok(),
                    (b.getKategori() != null ? b.getKategori().getNamaKategori() : "-")
            });
        }
    }

    // ===== helper subtotal/total transaksi =====
    private void recalcRowSubtotal(int row) {
        if (modelKeranjang == null) return;
        if (row < 0 || row >= modelKeranjang.getRowCount()) return;

        int qty = Integer.parseInt(modelKeranjang.getValueAt(row, 2).toString());
        double harga = Double.parseDouble(modelKeranjang.getValueAt(row, 3).toString());
        modelKeranjang.setValueAt(qty * harga, row, 4);
    }

    private long getTotalKeranjangAsLong() {
        if (modelKeranjang == null) return 0L;

        double total = 0;
        for (int i = 0; i < modelKeranjang.getRowCount(); i++) {
            Object subObj = modelKeranjang.getValueAt(i, 4);
            if (subObj == null) continue;
            total += Double.parseDouble(subObj.toString());
        }
        return (long) total;
    }

    private void recalcAllSubtotalsAndTotal() {
        if (modelKeranjang == null) return;

        double total = 0;
        for (int i = 0; i < modelKeranjang.getRowCount(); i++) {
            int qty = Integer.parseInt(modelKeranjang.getValueAt(i, 2).toString());
            double harga = Double.parseDouble(modelKeranjang.getValueAt(i, 3).toString());
            double sub = qty * harga;
            modelKeranjang.setValueAt(sub, i, 4);
            total += sub;
        }

        if (lblTotal != null) lblTotal.setText("Total: Rp " + (long) total);
    }

    private Buku cariBukuById(String id) {
        for (Buku b : tokoBuku.getInventoriBuku()) {
            if (b.getIdBuku().equalsIgnoreCase(id)) return b;
        }
        return null;
    }

    private String generateKodeTransaksi() {
        return "TRX-" + new SimpleDateFormat("yyyyMMdd-HHmmss").format(new Date());
    }

    private String getNamaKasir() {
        if (currentUser == null) return "Guest";
        return currentUser.getUsername();
    }

    // ✅ cek ID buku sudah dipakai atau belum
private boolean isIdBukuSudahAda(String id) {
    for (Buku b : tokoBuku.getInventoriBuku()) {
        if (b.getIdBuku().equalsIgnoreCase(id)) return true;
    }
    return false;
}


    // =========================
    // PANEL PELANGGAN
    // =========================
    private JPanel createPelangganPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(TEAL_BG);
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JLabel title = new JLabel("Manajemen Pelanggan");
        title.setFont(new Font("Segoe UI", Font.BOLD, 20));
        title.setForeground(Color.WHITE);

        JPanel header = new JPanel(new BorderLayout());
        header.setOpaque(false);
        header.add(title, BorderLayout.WEST);
        panel.add(header, BorderLayout.NORTH);

        JPanel formCard = new JPanel(new GridBagLayout());
        formCard.setBackground(FORM_CARD_BG);
        formCard.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(150, 170, 170)),
                BorderFactory.createEmptyBorder(14, 16, 14, 16)
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        txtIdPelanggan = new JTextField();
        txtNamaPelanggan = new JTextField();
        txtNoTelpPelanggan = new JTextField();
        txtAlamatPelanggan = new JTextArea(3, 20);
        txtAlamatPelanggan.setLineWrap(true);
        txtAlamatPelanggan.setWrapStyleWord(true);

        styleField(txtIdPelanggan);
        styleField(txtNamaPelanggan);
        styleField(txtNoTelpPelanggan);

        setDigitsOnly(txtIdPelanggan);

        JScrollPane scAlamat = new JScrollPane(txtAlamatPelanggan);
        scAlamat.setBorder(BorderFactory.createLineBorder(new Color(180, 190, 190)));

        JLabel l1 = new JLabel("ID Pelanggan"); l1.setFont(FONT_LABEL_BIG);
        JLabel l2 = new JLabel("Nama");         l2.setFont(FONT_LABEL_BIG);
        JLabel l3 = new JLabel("Alamat");       l3.setFont(FONT_LABEL_BIG);
        JLabel l4 = new JLabel("No Telepon");   l4.setFont(FONT_LABEL_BIG);

        JButton btnTambah = new JButton("Tambah");
        JButton btnUpdate = new JButton("Update");
        JButton btnHapus  = new JButton("Hapus");
        JButton btnReset  = new JButton("Reset");

        btnTambah.setFont(FONT_LABEL);
        btnUpdate.setFont(FONT_LABEL);
        btnHapus.setFont(FONT_LABEL);
        btnReset.setFont(FONT_LABEL);

        gbc.gridx = 0; gbc.gridy = 0; gbc.weightx = 0; formCard.add(l1, gbc);
        gbc.gridx = 1; gbc.weightx = 1; formCard.add(txtIdPelanggan, gbc);

        gbc.gridx = 0; gbc.gridy = 1; gbc.weightx = 0; formCard.add(l2, gbc);
        gbc.gridx = 1; gbc.weightx = 1; formCard.add(txtNamaPelanggan, gbc);

        gbc.gridx = 0; gbc.gridy = 2; gbc.weightx = 0; gbc.anchor = GridBagConstraints.NORTHWEST;
        formCard.add(l3, gbc);
        gbc.gridx = 1; gbc.weightx = 1; gbc.fill = GridBagConstraints.BOTH;
        formCard.add(scAlamat, gbc);

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0; gbc.gridy = 3; gbc.weightx = 0; formCard.add(l4, gbc);
        gbc.gridx = 1; gbc.weightx = 1; formCard.add(txtNoTelpPelanggan, gbc);

        JPanel action = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        action.setOpaque(false);
        action.add(btnTambah);
        action.add(btnUpdate);
        action.add(btnHapus);
        action.add(btnReset);

        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 2;
        formCard.add(action, gbc);

        String[] col = {"ID", "Nama", "Alamat", "No Telepon"};
        modelPelanggan = new DefaultTableModel(col, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        tblPelanggan = new JTable(modelPelanggan);
        styleTable(tblPelanggan);

        JScrollPane scroll = new JScrollPane(tblPelanggan);

        JPanel mid = new JPanel(new BorderLayout(0, 12));
        mid.setOpaque(false);
        mid.add(formCard, BorderLayout.NORTH);
        mid.add(scroll, BorderLayout.CENTER);
        panel.add(mid, BorderLayout.CENTER);

        refreshPelangganTable();

        tblPelanggan.getSelectionModel().addListSelectionListener(e -> {
            int r = tblPelanggan.getSelectedRow();
            if (r < 0) return;

            txtIdPelanggan.setText(modelPelanggan.getValueAt(r, 0).toString());
            txtNamaPelanggan.setText(modelPelanggan.getValueAt(r, 1).toString());
            txtAlamatPelanggan.setText(modelPelanggan.getValueAt(r, 2).toString());
            txtNoTelpPelanggan.setText(modelPelanggan.getValueAt(r, 3).toString());

            txtIdPelanggan.setEnabled(false);
        });

        btnReset.addActionListener(e -> resetFormPelanggan());

        btnTambah.addActionListener(e -> {
            Integer id = parseIntSafe(txtIdPelanggan.getText().trim());
            String nama = txtNamaPelanggan.getText().trim();
            String alamat = txtAlamatPelanggan.getText().trim();
            String telp = txtNoTelpPelanggan.getText().trim();

            if (id == null || id <= 0 || nama.isEmpty()) {
                JOptionPane.showMessageDialog(this, "ID (angka) dan Nama wajib diisi.", "Validasi", JOptionPane.WARNING_MESSAGE);
                return;
            }

            if (cariPelangganById(id) != null) {
                JOptionPane.showMessageDialog(this, "ID Pelanggan sudah ada.", "Validasi", JOptionPane.WARNING_MESSAGE);
                return;
            }

            masterPelanggan.add(new Pelanggan(id, nama, alamat, telp));
            refreshPelangganTable();
            refreshComboPelangganTransaksi();
            resetFormPelanggan();
        });

        btnUpdate.addActionListener(e -> {
            int r = tblPelanggan.getSelectedRow();
            if (r < 0) {
                JOptionPane.showMessageDialog(this, "Pilih pelanggan di tabel dulu.", "Info", JOptionPane.WARNING_MESSAGE);
                return;
            }

            Integer id = parseIntSafe(txtIdPelanggan.getText().trim());
            if (id == null) return;

            Pelanggan p = cariPelangganById(id);
            if (p == null) return;

            String nama = txtNamaPelanggan.getText().trim();
            String alamat = txtAlamatPelanggan.getText().trim();
            String telp = txtNoTelpPelanggan.getText().trim();

            if (nama.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Nama wajib diisi.", "Validasi", JOptionPane.WARNING_MESSAGE);
                return;
            }

            p.setNama(nama);
            p.setAlamat(alamat);
            p.setNoTelepon(telp);

            refreshPelangganTable();
            refreshComboPelangganTransaksi();
            resetFormPelanggan();
        });

        btnHapus.addActionListener(e -> {
            int r = tblPelanggan.getSelectedRow();
            if (r < 0) {
                JOptionPane.showMessageDialog(this, "Pilih pelanggan di tabel dulu.", "Info", JOptionPane.WARNING_MESSAGE);
                return;
            }

            int id = Integer.parseInt(modelPelanggan.getValueAt(r, 0).toString());
            Pelanggan p = cariPelangganById(id);
            if (p == null) return;

            int ok = JOptionPane.showConfirmDialog(this,
                    "Yakin hapus pelanggan: " + p.getNama() + " ?",
                    "Konfirmasi", JOptionPane.YES_NO_OPTION);
            if (ok == JOptionPane.YES_OPTION) {
                masterPelanggan.remove(p);
                refreshPelangganTable();
                refreshComboPelangganTransaksi();
                resetFormPelanggan();
            }
        });

        return panel;
    }

    private void refreshPelangganTable() {
        if (modelPelanggan == null) return;
        modelPelanggan.setRowCount(0);

        for (Pelanggan p : masterPelanggan) {
            modelPelanggan.addRow(new Object[]{
                    p.getIdPelanggan(),
                    p.getNama(),
                    p.getAlamat(),
                    p.getNoTelepon()
            });
        }
    }

    private Pelanggan cariPelangganById(int id) {
        for (Pelanggan p : masterPelanggan) {
            if (p.getIdPelanggan() == id) return p;
        }
        return null;
    }

    private void resetFormPelanggan() {
        if (txtIdPelanggan != null) txtIdPelanggan.setText("");
        if (txtNamaPelanggan != null) txtNamaPelanggan.setText("");
        if (txtAlamatPelanggan != null) txtAlamatPelanggan.setText("");
        if (txtNoTelpPelanggan != null) txtNoTelpPelanggan.setText("");

        if (txtIdPelanggan != null) txtIdPelanggan.setEnabled(true);
        if (tblPelanggan != null) tblPelanggan.clearSelection();
    }

    // =========================
    // PANEL LAPORAN
    // =========================
    private JPanel createLaporanPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(TEAL_BG);
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JPanel summary = new JPanel(new GridLayout(1, 2, 12, 0));
        summary.setOpaque(false);

        JPanel cardStok = new JPanel(new GridBagLayout());
        cardStok.setBackground(FORM_CARD_BG);
        cardStok.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(150, 170, 170)),
                BorderFactory.createEmptyBorder(12, 14, 12, 14)
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 6, 6, 6);
        gbc.anchor = GridBagConstraints.WEST;

        JLabel t1 = new JLabel("Ringkasan Stok");
        t1.setFont(new Font("Segoe UI", Font.BOLD, 16));

        lblTotalJudul = new JLabel("Total Judul: 0");
        lblTotalStok  = new JLabel("Total Stok: 0");
        lblNilaiStok  = new JLabel("Nilai Stok: Rp 0");

        lblTotalJudul.setFont(FONT_LABEL);
        lblTotalStok.setFont(FONT_LABEL);
        lblNilaiStok.setFont(FONT_LABEL);

        gbc.gridx = 0; gbc.gridy = 0; cardStok.add(t1, gbc);
        gbc.gridy++; cardStok.add(lblTotalJudul, gbc);
        gbc.gridy++; cardStok.add(lblTotalStok, gbc);
        gbc.gridy++; cardStok.add(lblNilaiStok, gbc);

        JPanel cardTrx = new JPanel(new GridBagLayout());
        cardTrx.setBackground(FORM_CARD_BG);
        cardTrx.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(150, 170, 170)),
                BorderFactory.createEmptyBorder(12, 14, 12, 14)
        ));

        JLabel t2 = new JLabel("Ringkasan Transaksi");
        t2.setFont(new Font("Segoe UI", Font.BOLD, 16));

        lblTotalTransaksi = new JLabel("Total Transaksi: 0");
        lblOmset          = new JLabel("Total Omset: Rp 0");
        lblTotalTransaksi.setFont(FONT_LABEL);
        lblOmset.setFont(FONT_LABEL);

        gbc.gridx = 0; gbc.gridy = 0; cardTrx.add(t2, gbc);
        gbc.gridy++; cardTrx.add(lblTotalTransaksi, gbc);
        gbc.gridy++; cardTrx.add(lblOmset, gbc);

        summary.add(cardStok);
        summary.add(cardTrx);
        panel.add(summary, BorderLayout.NORTH);

        JTabbedPane tabs = new JTabbedPane();
        tabs.setFont(new Font("Segoe UI", Font.BOLD, 14));

        JPanel tabStok = new JPanel(new BorderLayout());
        tabStok.setBackground(TEAL_BG);
        tabStok.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));

        String[] colStok = {"ID", "Judul", "Penerbit", "Kategori", "Harga", "Stok", "Nilai"};
        modelLaporanStok = new DefaultTableModel(colStok, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        tblLaporanStok = new JTable(modelLaporanStok);
        styleTable(tblLaporanStok);
        tabStok.add(new JScrollPane(tblLaporanStok), BorderLayout.CENTER);

        JPanel tabTrx = new JPanel(new BorderLayout());
        tabTrx.setBackground(TEAL_BG);
        tabTrx.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));

        String[] colTrx = {"Kode", "Tanggal", "Kasir", "Pelanggan", "Total", "Bayar", "Kembalian"};
        modelLaporanTrx = new DefaultTableModel(colTrx, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        tblLaporanTrx = new JTable(modelLaporanTrx);
        styleTable(tblLaporanTrx);
        tabTrx.add(new JScrollPane(tblLaporanTrx), BorderLayout.CENTER);

        tabs.addTab("Stok Buku", tabStok);
        tabs.addTab("Transaksi", tabTrx);

        panel.add(tabs, BorderLayout.CENTER);

        refreshLaporan();
        return panel;
    }

    private void refreshLaporan() {
        // stok
        if (modelLaporanStok != null) modelLaporanStok.setRowCount(0);

        int totalJudul = 0;
        int totalStok = 0;
        double nilaiStok = 0;

        List<Buku> list = tokoBuku.getInventoriBuku();
        totalJudul = list.size();

        for (Buku b : list) {
            int stok = b.getStok();
            double harga = b.getHarga();
            double nilai = stok * harga;

            totalStok += stok;
            nilaiStok += nilai;

            if (modelLaporanStok != null) {
                modelLaporanStok.addRow(new Object[]{
                        b.getIdBuku(),
                        b.getJudul(),
                        b.getPenerbit(),
                        (b.getKategori() != null ? b.getKategori().getNamaKategori() : "-"),
                        harga,
                        stok,
                        nilai
                });
            }
        }

        if (lblTotalJudul != null) lblTotalJudul.setText("Total Judul: " + totalJudul);
        if (lblTotalStok != null)  lblTotalStok.setText("Total Stok: " + totalStok);
        if (lblNilaiStok != null)  lblNilaiStok.setText("Nilai Stok: Rp " + (long) nilaiStok);

        // transaksi
        if (modelLaporanTrx != null) modelLaporanTrx.setRowCount(0);

        long omset = 0;
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm");

        for (TrxRecord r : riwayatTransaksi) {
            omset += r.total;
            modelLaporanTrx.addRow(new Object[]{
                    r.kode,
                    sdf.format(r.tanggal),
                    r.kasir,
                    r.pelanggan,
                    r.total,
                    r.bayar,
                    r.kembalian
            });
        }

        if (lblTotalTransaksi != null) lblTotalTransaksi.setText("Total Transaksi: " + riwayatTransaksi.size());
        if (lblOmset != null)          lblOmset.setText("Total Omset: Rp " + omset);
    }

    // =========================
    // UI HELPERS
    // =========================
    private JPanel wrapCard(String title, JComponent content) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(FORM_CARD_BG);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(150, 170, 170)),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));

        JLabel lbl = new JLabel(title);
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lbl.setBorder(BorderFactory.createEmptyBorder(0, 0, 8, 0));

        card.add(lbl, BorderLayout.NORTH);
        card.add(content, BorderLayout.CENTER);
        return card;
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
        btn.addChangeListener(e -> btn.setBackground(btn.isSelected() ? Color.WHITE : TAB_BG));
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
        JButton btn = new JButton(new ImageIcon(img));
        btn.setPreferredSize(new Dimension(60, 60));
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createLineBorder(new Color(150, 150, 150)));
        btn.setBackground(Color.WHITE);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
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

    private void styleComboString(JComboBox<String> combo) {
        combo.setFont(FONT_LABEL);
        combo.setBackground(Color.WHITE);
        combo.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(180, 190, 190)),
                BorderFactory.createEmptyBorder(3, 6, 3, 6)
        ));
        combo.setPrototypeDisplayValue("XXXXXXXXXXXXXXXXXXXXXXXXXXXX");
    }

    private void styleComboKategori(JComboBox<KategoriBuku> combo) {
        combo.setFont(FONT_LABEL);
        combo.setBackground(Color.WHITE);
        combo.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(180, 190, 190)),
                BorderFactory.createEmptyBorder(3, 6, 3, 6)
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

    private void styleTable(JTable table) {
        table.setFont(FONT_TABLE);
        table.setRowHeight(26);
        table.setBackground(TABLE_ROW_BG);
        table.setForeground(Color.WHITE);
        table.setGridColor(TABLE_GRID);
        table.setSelectionBackground(new Color(0, 70, 80));
        table.setSelectionForeground(Color.WHITE);

        JTableHeader header = table.getTableHeader();
        header.setBackground(TABLE_HEADER_BG);
        header.setForeground(Color.WHITE);
        header.setFont(FONT_TABLE);
        header.setReorderingAllowed(false);
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
                int w = getWidth(), h = getHeight();
                g2.drawImage(bg, 0, 0, w, h, this);

                g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, overlayAlpha));
                g2.setColor(overlayColor);
                g2.fillRect(0, 0, w, h);
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
                if (c instanceof JComponent jc) {
                    jc.setOpaque(true);
                    jc.setBackground(isSelected ? selectedBg : normalBg);
                    jc.setForeground(Color.WHITE);
                }
                return c;
            }
        };

        for (int i = 0; i < table.getColumnModel().getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(renderer);
        }
    }

    // =========================
    // INPUT FILTER (ANTI HURUF)
    // =========================
    private void setDigitsOnly(JTextField field) {
        AbstractDocument doc = (AbstractDocument) field.getDocument();
        doc.setDocumentFilter(new DocumentFilter() {
            @Override
            public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr)
                    throws BadLocationException {
                if (string == null) return;
                if (string.matches("\\d+")) super.insertString(fb, offset, string, attr);
            }

            @Override
            public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs)
                    throws BadLocationException {
                if (text == null) return;
                if (text.matches("\\d*")) super.replace(fb, offset, length, text, attrs);
            }
        });
    }

    private void setDecimalOnly(JTextField field) {
        AbstractDocument doc = (AbstractDocument) field.getDocument();
        doc.setDocumentFilter(new DocumentFilter() {
            @Override
            public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr)
                    throws BadLocationException {
                if (string == null) return;
                String cur = fb.getDocument().getText(0, fb.getDocument().getLength());
                String next = cur.substring(0, offset) + string + cur.substring(offset);
                if (next.matches("\\d*(\\.\\d*)?")) super.insertString(fb, offset, string, attr);
            }

            @Override
            public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs)
                    throws BadLocationException {
                if (text == null) return;
                String cur = fb.getDocument().getText(0, fb.getDocument().getLength());
                String next = cur.substring(0, offset) + text + cur.substring(offset + length);
                if (next.matches("\\d*(\\.\\d*)?")) super.replace(fb, offset, length, text, attrs);
            }
        });
    }

    // =========================
    // PARSE SAFE
    // =========================
    private Integer parseIntSafe(String s) {
        try { return Integer.parseInt(s); }
        catch (Exception e) { return null; }
    }

    private Long parseLongSafe(String s) {
        try { return Long.parseLong(s); }
        catch (Exception e) { return null; }
    }

    private Double parseDoubleSafe(String s) {
        try { return Double.parseDouble(s); }
        catch (Exception e) { return null; }
    }
}
