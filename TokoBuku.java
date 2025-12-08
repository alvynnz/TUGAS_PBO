import java.util.ArrayList;
import java.util.List;

public class TokoBuku {
    private List<Buku> inventoriBuku = new ArrayList<>();
    private List<Pelanggan> daftarPelanggan = new ArrayList<>();
    private List<Transaksi_penjualan> riwayatTransaksi = new ArrayList<>();

    public void tambahBuku(Buku baru) {
        inventoriBuku.add(baru);
    }

    public Buku cariBuku(String kunci) {
        for (Buku b : inventoriBuku) {
            if (b.getJudul().equalsIgnoreCase(kunci) || 
                b.getKategori().getNamaKategori().equalsIgnoreCase(kunci)) {
                return b;
            }
        }
        return null;
    }

    public void prosesTransaksi(Transaksi_penjualan t) {
        riwayatTransaksi.add(t);
        System.out.println("Transaksi berhasil disimpan!");
    }

    public void perbaruiStok(Buku buku, int terjual) {
        if (buku.getStok() >= terjual) {
            buku.setStok(buku.getStok() - terjual);
        } else {
            System.out.println("Stok buku tidak mencukupi!");
        }
    }

    public void tambahPelanggan(Pelanggan p) {
        daftarPelanggan.add(p);
    }

    public List<Transaksi_penjualan> getRiwayatTransaksi() {
        return riwayatTransaksi;
    }

    public List<Buku> getInventoriBuku() {
        return inventoriBuku;
    }
    
    public List<Pelanggan> getDaftarPelanggan() {
        return daftarPelanggan;
    }
}

