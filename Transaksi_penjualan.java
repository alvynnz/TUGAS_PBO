import java.util.ArrayList;
import java.util.Date;
import java.text.SimpleDateFormat;



public class Transaksi_penjualan {
    private String kodeTransaksi;
    private Date tanggal;
    private String kasir;
    private ArrayList<Detail_transaksi> listDetail;

public String getKodeTransaksi() { return kodeTransaksi; }
    public Date getTanggal() { return tanggal; }
    public String getKasir() { return kasir; }

    private String trxKode(Transaksi_penjualan trx) { return trx.getKodeTransaksi(); }
private String trxTanggal(Transaksi_penjualan trx) { 
    return new java.text.SimpleDateFormat("dd-MM-yyyy").format(trx.getTanggal());
}
private String trxKasir(Transaksi_penjualan trx) { return trx.getKasir(); }


    public Transaksi_penjualan(String kodeTransaksi, String kasir) {
        this.kodeTransaksi = kodeTransaksi;
        this.kasir = kasir;
        this.tanggal = new Date();
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

    public void tampilkanTransaksi() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        System.out.println("===== TRANSAKSI PENJUALAN =====");
        System.out.println("Kode : " + kodeTransaksi);
        System.out.println("Tanggal : " + sdf.format(tanggal));
        System.out.println("Kasir : " + kasir);
        System.out.println("\n-- DETAIL --");
        
        for (Detail_transaksi d : listDetail) {
            System.out.println(d.toString());
        }

        System.out.println("\nTOTAL BAYAR : " + hitungTotal());
    }

    public ArrayList<Detail_transaksi> getListDetail() {
        return listDetail;
    }

    
}
