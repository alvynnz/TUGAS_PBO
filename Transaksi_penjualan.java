import java.util.ArrayList;
import java.util.Date;

public class Transaksi_penjualan {
    private String kodeTransaksi;
    private Date tanggal;
    private String kasir;
    private String namaPelanggan;
    private ArrayList<Detail_transaksi> listDetail;

    public Transaksi_penjualan(String kodeTransaksi, String kasir) {
        this.kodeTransaksi = kodeTransaksi;
        this.kasir = kasir;
        this.tanggal = new Date();
        this.namaPelanggan = "-";
        this.listDetail = new ArrayList<>();
    }

    // OPTIONAL: kalau kamu mau langsung isi pelanggan dari constructor
    public Transaksi_penjualan(String kodeTransaksi, String kasir, String namaPelanggan) {
        this.kodeTransaksi = kodeTransaksi;
        this.kasir = kasir;
        this.tanggal = new Date();
        this.namaPelanggan = (namaPelanggan == null || namaPelanggan.isBlank()) ? "-" : namaPelanggan;
        this.listDetail = new ArrayList<>();
    }

    public void tambahDetail(Detail_transaksi detail) {
        listDetail.add(detail);
    }

    public double hitungTotal() {
        double total = 0;
        for (Detail_transaksi d : listDetail) {
            total += d.hitungSubtotal();
        }
        return total;
    }

    public ArrayList<Detail_transaksi> getListDetail() {
        return listDetail;
    }

    // ===== GETTER untuk laporan =====
    public String getKodeTransaksi() { return kodeTransaksi; }
    public Date getTanggal() { return tanggal; }
    public String getKasir() { return kasir; }

    public String getNamaPelanggan() { return namaPelanggan; }
    public void setNamaPelanggan(String namaPelanggan) {
        this.namaPelanggan = (namaPelanggan == null || namaPelanggan.isBlank()) ? "-" : namaPelanggan;
    }

    public void tampilkanTransaksi() {
    java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd-MM-yyyy HH:mm");
    System.out.println("===== TRANSAKSI PENJUALAN =====");
    System.out.println("Kode   : " + kodeTransaksi);
    System.out.println("Tanggal: " + sdf.format(tanggal));
    System.out.println("Kasir  : " + kasir);
    System.out.println("\n-- DETAIL --");

    for (Detail_transaksi d : listDetail) {
        System.out.println(d.toString());
    }

    System.out.println("\nTOTAL BAYAR : " + hitungTotal());
    System.out.println("================================");
}

}
