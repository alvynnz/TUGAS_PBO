import java.util.ArrayList;
import java.util.List;

public class TokoBuku {
    private List<Buku> inventoriBuku = new ArrayList<>();
    private List<Pelanggan> daftarPelanggan = new ArrayList<>();
    private List<TransaksiPenjualan> riwayatTransaksi = new ArrayList<>();

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

    public void prosesTransaksi(TransaksiPenjualan t) {
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

    public List<TransaksiPenjualan> getRiwayatTransaksi() {
        return riwayatTransaksi;
    }
}

