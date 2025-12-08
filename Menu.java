import java.util.Scanner;
import java.util.List;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Menu {

    private Scanner input;
    private TokoBuku tokoBuku;
    private Kasir kasirAktif;
    private UserSystem user;

    public Menu() {
        input = new Scanner(System.in);
        tokoBuku = new TokoBuku();
        
        // Inisialisasi data awal
        inisialisasiData();
    }
    
    private void inisialisasiData() {
        // Membuat kategori buku
        KategoriBuku fiksi = new KategoriBuku("K001", "Fiksi", "Buku-buku cerita fiksi");
        KategoriBuku nonFiksi = new KategoriBuku("K002", "Non-Fiksi", "Buku-buku pengetahuan");
        
        // Menambahkan buku ke inventori
        tokoBuku.tambahBuku(new Buku("B001", "Laskar Pelangi", "Andrea Hirata", "Bentang Pustaka", 2005, 75000, 10, fiksi));
        tokoBuku.tambahBuku(new Buku("B002", "Sapiens", "Yuval Noah Harari", "Gramedia", 2017, 120000, 5, nonFiksi));
        tokoBuku.tambahBuku(new Buku("B003", "Bumi Manusia", "Pramoedya Ananta Toer", "Hasta Mitra", 1980, 85000, 7, fiksi));
        
        // Menambahkan pelanggan
        tokoBuku.tambahPelanggan(new Pelanggan(1, "Ahmad Fadli", "Jakarta", "08123456789"));
        tokoBuku.tambahPelanggan(new Pelanggan(2, "Siti Nurhaliza", "Bandung", "08234567890"));
        
        // Membuat user kasir
        user = new UserSystem(1, "admin", "admin123", "Kasir");
        kasirAktif = new Kasir(1, "Admin Kasir", user);
    }

    public void tampilkanMenuUtama() {
        int pilihan;

        do {
            System.out.println("=====================================");
            System.out.println("        SISTEM TOKO BUKU");
            System.out.println("=====================================");
            System.out.println("1. Kelola Buku");
            System.out.println("2. Kelola Pelanggan");
            System.out.println("3. Kelola Transaksi");
            System.out.println("4. Laporan");
            System.out.println("0. Keluar");
            System.out.print("Pilih menu: ");

            pilihan = input.nextInt();
            input.nextLine(); // Membersihkan buffer

            switch (pilihan) {
                case 1:
                    menuBuku();
                    break;
                case 2:
                    menuPelanggan();
                    break;
                case 3:
                    menuTransaksi();
                    break;
                case 4:
                    menuLaporan();
                    break;
                case 0:
                    System.out.println("Terima kasih telah menggunakan sistem!");
                    break;
                default:
                    System.out.println("Pilihan tidak valid!");
            }

        } while (pilihan != 0);
    }

    private void menuBuku() {
        int pilihan;
        
        do {
            System.out.println("\n>> Menu Kelola Buku");
            System.out.println("1. Tambah Buku");
            System.out.println("2. Lihat Semua Buku");
            System.out.println("3. Cari Buku");
            System.out.println("4. Update Buku");
            System.out.println("5. Kembali");
            System.out.print("Pilih menu: ");
            
            pilihan = input.nextInt();
            input.nextLine(); // Membersihkan buffer
            
            switch (pilihan) {
                case 1:
                    tambahBuku();
                    break;
                case 2:
                    lihatSemuaBuku();
                    break;
                case 3:
                    cariBuku();
                    break;
                case 4:
                    updateBuku();
                    break;
                case 5:
                    System.out.println("Kembali ke menu utama");
                    break;
                default:
                    System.out.println("Pilihan tidak valid!");
            }
        } while (pilihan != 5);
    }
    
    private void tambahBuku() {
        System.out.println("\n--- Tambah Buku Baru ---");
        
        System.out.print("ID Buku: ");
        String idBuku = input.nextLine();
        
        System.out.print("Judul: ");
        String judul = input.nextLine();
        
        System.out.print("Penulis: ");
        String penulis = input.nextLine();
        
        System.out.print("Penerbit: ");
        String penerbit = input.nextLine();
        
        System.out.print("Tahun Terbit: ");
        int tahunTerbit = input.nextInt();
        
        System.out.print("Harga: ");
        double harga = input.nextDouble();
        
        System.out.print("Stok: ");
        int stok = input.nextInt();
        input.nextLine(); // Membersihkan buffer
        
        // Tampilkan kategori yang tersedia
        System.out.println("\nKategori yang tersedia:");
        System.out.println("1. Fiksi");
        System.out.println("2. Non-Fiksi");
        System.out.print("Pilih kategori: ");
        int pilihanKategori = input.nextInt();
        input.nextLine(); // Membersihkan buffer
        
        KategoriBuku kategori;
        if (pilihanKategori == 1) {
            kategori = new KategoriBuku("K001", "Fiksi", "Buku-buku cerita fiksi");
        } else {
            kategori = new KategoriBuku("K002", "Non-Fiksi", "Buku-buku pengetahuan");
        }
        
        Buku bukuBaru = new Buku(idBuku, judul, penulis, penerbit, tahunTerbit, harga, stok, kategori);
        tokoBuku.tambahBuku(bukuBaru);
        
        System.out.println("Buku berhasil ditambahkan!");
    }
    
    private void lihatSemuaBuku() {
        System.out.println("\n--- Daftar Buku ---");
        
        // Untuk demo, kita akan menampilkan beberapa buku yang ada di toko
        // Dalam implementasi nyata, kita perlu method getInventoriBuku() di TokoBuku
        System.out.println("ID\tJudul\t\tPenulis\t\tHarga\tStok");
        System.out.println("------------------------------------------------------------");
        
        // Menampilkan 3 buku contoh
        Buku buku1 = tokoBuku.cariBuku("Laskar Pelangi");
        if (buku1 != null) {
            System.out.println(buku1.getIdBuku() + "\t" + buku1.getJudul() + "\t" + 
                              buku1.getPenulis() + "\t" + buku1.getHarga() + "\t" + buku1.getStok());
        }
        
        Buku buku2 = tokoBuku.cariBuku("Sapiens");
        if (buku2 != null) {
            System.out.println(buku2.getIdBuku() + "\t" + buku2.getJudul() + "\t" + 
                              buku2.getPenulis() + "\t" + buku2.getHarga() + "\t" + buku2.getStok());
        }
        
        Buku buku3 = tokoBuku.cariBuku("Bumi Manusia");
        if (buku3 != null) {
            System.out.println(buku3.getIdBuku() + "\t" + buku3.getJudul() + "\t" + 
                              buku3.getPenulis() + "\t" + buku3.getHarga() + "\t" + buku3.getStok());
        }
    }
    
    private void cariBuku() {
        System.out.println("\n--- Cari Buku ---");
        System.out.print("Masukkan judul atau kategori buku: ");
        String kunci = input.nextLine();
        
        Buku buku = tokoBuku.cariBuku(kunci);
        if (buku != null) {
            System.out.println("Buku ditemukan:");
            buku.tampilkanDetail();
        } else {
            System.out.println("Buku tidak ditemukan!");
        }
    }
    
    private void updateBuku() {
        System.out.println("\n--- Update Informasi Buku ---");
        System.out.print("Masukkan judul buku yang akan diupdate: ");
        String judul = input.nextLine();
        
        Buku buku = tokoBuku.cariBuku(judul);
        if (buku != null) {
            System.out.println("Informasi buku saat ini:");
            buku.tampilkanDetail();
            
            System.out.println("\nMasukkan informasi baru:");
            System.out.print("Judul (kosongkan jika tidak diubah): ");
            String judulBaru = input.nextLine();
            if (!judulBaru.isEmpty()) {
                buku.setJudul(judulBaru);
            }
            
            System.out.print("Penulis (kosongkan jika tidak diubah): ");
            String penulisBaru = input.nextLine();
            if (!penulisBaru.isEmpty()) {
                buku.setPenulis(penulisBaru);
            }
            
            System.out.print("Harga (0 jika tidak diubah): ");
            double hargaBaru = input.nextDouble();
            if (hargaBaru > 0) {
                buku.setHarga(hargaBaru);
            }
            
            System.out.print("Stok (-1 jika tidak diubah): ");
            int stokBaru = input.nextInt();
            if (stokBaru >= 0) {
                buku.setStok(stokBaru);
            }
            input.nextLine(); // Membersihkan buffer
            
            System.out.println("Informasi buku berhasil diperbarui!");
            buku.tampilkanDetail();
        } else {
            System.out.println("Buku tidak ditemukan!");
        }
    }

    private void menuPelanggan() {
        int pilihan;
        
        do {
            System.out.println("\n>> Menu Kelola Pelanggan");
            System.out.println("1. Tambah Pelanggan");
            System.out.println("2. Lihat Semua Pelanggan");
            System.out.println("3. Cari Pelanggan");
            System.out.println("4. Update Pelanggan");
            System.out.println("5. Kembali");
            System.out.print("Pilih menu: ");
            
            pilihan = input.nextInt();
            input.nextLine(); // Membersihkan buffer
            
            switch (pilihan) {
                case 1:
                    tambahPelanggan();
                    break;
                case 2:
                    lihatSemuaPelanggan();
                    break;
                case 3:
                    cariPelanggan();
                    break;
                case 4:
                    updatePelanggan();
                    break;
                case 5:
                    System.out.println("Kembali ke menu utama");
                    break;
                default:
                    System.out.println("Pilihan tidak valid!");
            }
        } while (pilihan != 5);
    }
    
    private void tambahPelanggan() {
        System.out.println("\n--- Tambah Pelanggan Baru ---");
        
        System.out.print("ID Pelanggan: ");
        int idPelanggan = input.nextInt();
        input.nextLine(); // Membersihkan buffer
        
        System.out.print("Nama: ");
        String nama = input.nextLine();
        
        System.out.print("Alamat: ");
        String alamat = input.nextLine();
        
        System.out.print("No Telepon: ");
        String noTelepon = input.nextLine();
        
        Pelanggan pelangganBaru = new Pelanggan(idPelanggan, nama, alamat, noTelepon);
        tokoBuku.tambahPelanggan(pelangganBaru);
        
        System.out.println("Pelanggan berhasil ditambahkan!");
    }
    
    private void lihatSemuaPelanggan() {
        System.out.println("\n--- Daftar Pelanggan ---");
        
        // Untuk demo, kita akan menampilkan beberapa pelanggan yang ada di toko
        // Dalam implementasi nyata, kita perlu method getDaftarPelanggan() di TokoBuku
        System.out.println("ID\tNama\t\tAlamat\t\tNo Telepon");
        System.out.println("------------------------------------------------------------");
        
        // Menampilkan 2 pelanggan contoh
        Pelanggan pelanggan1 = new Pelanggan(1, "Ahmad Fadli", "Jakarta", "08123456789");
        System.out.println(pelanggan1.getIdPelanggan() + "\t" + pelanggan1.getNama() + "\t\t" + 
                          pelanggan1.getAlamat() + "\t" + pelanggan1.getNoTelepon());
        
        Pelanggan pelanggan2 = new Pelanggan(2, "Siti Nurhaliza", "Bandung", "08234567890");
        System.out.println(pelanggan2.getIdPelanggan() + "\t" + pelanggan2.getNama() + "\t\t" + 
                          pelanggan2.getAlamat() + "\t" + pelanggan2.getNoTelepon());
    }
    
    private void cariPelanggan() {
        System.out.println("\n--- Cari Pelanggan ---");
        System.out.print("Masukkan nama pelanggan: ");
        String nama = input.nextLine();
        
        // Dalam implementasi nyata, kita perlu method cariPelanggan() di TokoBuku
        // Untuk demo, kita akan mencari secara manual
        if (nama.equalsIgnoreCase("Ahmad Fadli")) {
            Pelanggan pelanggan = new Pelanggan(1, "Ahmad Fadli", "Jakarta", "08123456789");
            System.out.println(pelanggan.getInfoPelanggan());
        } else if (nama.equalsIgnoreCase("Siti Nurhaliza")) {
            Pelanggan pelanggan = new Pelanggan(2, "Siti Nurhaliza", "Bandung", "08234567890");
            System.out.println(pelanggan.getInfoPelanggan());
        } else {
            System.out.println("Pelanggan tidak ditemukan!");
        }
    }
    
    private void updatePelanggan() {
        System.out.println("\n--- Update Informasi Pelanggan ---");
        System.out.print("Masukkan nama pelanggan yang akan diupdate: ");
        String nama = input.nextLine();
        
        // Dalam implementasi nyata, kita perlu method cariPelanggan() di TokoBuku
        // Untuk demo, kita akan mencari secara manual
        Pelanggan pelanggan = null;
        if (nama.equalsIgnoreCase("Ahmad Fadli")) {
            pelanggan = new Pelanggan(1, "Ahmad Fadli", "Jakarta", "08123456789");
        } else if (nama.equalsIgnoreCase("Siti Nurhaliza")) {
            pelanggan = new Pelanggan(2, "Siti Nurhaliza", "Bandung", "08234567890");
        }
        
        if (pelanggan != null) {
            System.out.println("Informasi pelanggan saat ini:");
            System.out.println(pelanggan.getInfoPelanggan());
            
            System.out.println("\nMasukkan informasi baru:");
            System.out.print("Nama (kosongkan jika tidak diubah): ");
            String namaBaru = input.nextLine();
            if (!namaBaru.isEmpty()) {
                pelanggan.setNama(namaBaru);
            }
            
            System.out.print("Alamat (kosongkan jika tidak diubah): ");
            String alamatBaru = input.nextLine();
            if (!alamatBaru.isEmpty()) {
                pelanggan.setAlamat(alamatBaru);
            }
            
            System.out.print("No Telepon (kosongkan jika tidak diubah): ");
            String noTeleponBaru = input.nextLine();
            if (!noTeleponBaru.isEmpty()) {
                pelanggan.setNoTelepon(noTeleponBaru);
            }
            
            System.out.println("Informasi pelanggan berhasil diperbarui!");
            System.out.println(pelanggan.getInfoPelanggan());
        } else {
            System.out.println("Pelanggan tidak ditemukan!");
        }
    }

    private void menuTransaksi() {
        int pilihan;
        
        do {
            System.out.println("\n>> Menu Transaksi Penjualan");
            System.out.println("1. Buat Transaksi Baru");
            System.out.println("2. Kembali");
            System.out.print("Pilih menu: ");
            
            pilihan = input.nextInt();
            input.nextLine(); // Membersihkan buffer
            
            switch (pilihan) {
                case 1:
                    buatTransaksi();
                    break;
                case 2:
                    System.out.println("Kembali ke menu utama");
                    break;
                default:
                    System.out.println("Pilihan tidak valid!");
            }
        } while (pilihan != 2);
    }
    
    private void buatTransaksi() {
        System.out.println("\n--- Buat Transaksi Baru ---");
        
        // Generate kode transaksi
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String kodeTransaksi = "TRX-" + sdf.format(new Date());
        
        // Buat objek transaksi
        Transaksi_penjualan transaksi = new Transaksi_penjualan(kodeTransaksi, kasirAktif.getNamaKasir());
        
        // Tambahkan detail transaksi
        int pilihan;
        do {
            System.out.println("\n--- Tambah Item ke Transaksi ---");
            System.out.print("Masukkan judul buku: ");
            String judulBuku = input.nextLine();
            
            Buku buku = tokoBuku.cariBuku(judulBuku);
            if (buku != null) {
                System.out.println("Informasi buku:");
                buku.tampilkanDetail();
                
                System.out.print("Masukkan jumlah beli: ");
                int jumlahBeli = input.nextInt();
                input.nextLine(); // Membersihkan buffer
                
                if (jumlahBeli <= buku.getStok()) {
                    // Tambahkan detail transaksi
                    Detail_transaksi detail = new Detail_transaksi(buku, jumlahBeli);
                    transaksi.tambahDetail(detail);
                    
                    // Update stok
                    tokoBuku.perbaruiStok(buku, jumlahBeli);
                    
                    System.out.println("Item berhasil ditambahkan ke transaksi!");
                } else {
                    System.out.println("Stok tidak mencukupi! Stok tersedia: " + buku.getStok());
                }
            } else {
                System.out.println("Buku tidak ditemukan!");
            }
            
            System.out.print("Tambah item lagi? (1=Ya, 0=Tidak): ");
            pilihan = input.nextInt();
            input.nextLine(); // Membersihkan buffer
        } while (pilihan == 1);
        
        // Tampilkan detail transaksi
        System.out.println("\n--- Detail Transaksi ---");
        transaksi.tampilkanTransaksi();
        
        // Simpan transaksi
        tokoBuku.prosesTransaksi(transaksi);
    }

    private void menuLaporan() {
        int pilihan;
        
        do {
            System.out.println("\n>> Menu Laporan");
            System.out.println("1. Lihat Semua Transaksi");
            System.out.println("2. Laporan Penjualan Harian");
            System.out.println("3. Kembali");
            System.out.print("Pilih menu: ");
            
            pilihan = input.nextInt();
            input.nextLine(); // Membersihkan buffer
            
            switch (pilihan) {
                case 1:
                    lihatSemuaTransaksi();
                    break;
                case 2:
                    laporanPenjualanHarian();
                    break;
                case 3:
                    System.out.println("Kembali ke menu utama");
                    break;
                default:
                    System.out.println("Pilihan tidak valid!");
            }
        } while (pilihan != 3);
    }
    
    private void lihatSemuaTransaksi() {
        System.out.println("\n--- Laporan Semua Transaksi ---");
        
        List<Transaksi_penjualan> riwayat = tokoBuku.getRiwayatTransaksi();
        if (riwayat.isEmpty()) {
            System.out.println("Belum ada transaksi!");
        } else {
            for (Transaksi_penjualan transaksi : riwayat) {
                transaksi.tampilkanTransaksi();
                System.out.println("-------------------------------------");
            }
        }
    }
    
    private void laporanPenjualanHarian() {
        System.out.println("\n--- Laporan Penjualan Harian ---");
        
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        String tanggalHariIni = sdf.format(new Date());
        
        System.out.println("Tanggal: " + tanggalHariIni);
        System.out.println("Kasir: " + kasirAktif.getNamaKasir());
        
        double totalPenjualan = 0;
        int totalItem = 0;
        
        List<Transaksi_penjualan> riwayat = tokoBuku.getRiwayatTransaksi();
        if (riwayat.isEmpty()) {
            System.out.println("Belum ada transaksi hari ini!");
        } else {
            for (Transaksi_penjualan transaksi : riwayat) {
                // Dalam implementasi nyata, kita perlu filter berdasarkan tanggal
                transaksi.tampilkanTransaksi();
                System.out.println("-------------------------------------");
                
                totalPenjualan += transaksi.hitungTotal();
                totalItem += transaksi.getListDetail().size();
            }
            
            System.out.println("\n--- Ringkasan Penjualan ---");
            System.out.println("Total Transaksi: " + riwayat.size());
            System.out.println("Total Item Terjual: " + totalItem);
            System.out.println("Total Penjualan: Rp " + totalPenjualan);
        }
    }
}