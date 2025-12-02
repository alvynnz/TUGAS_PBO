public class Detail_transaksi {
private String idBuku;
    private String judul;
    private String penulis;
    private String penerbit;
    private int tahunTerbit;
    private double harga;
    private int stok;   
    private int jumlahBeli; 

    public Detail_transaksi(String idBuku, String judul, String penulis, String penerbit, int tahunTerbit, double harga, int stok, int jumlahBeli) {

        this.idBuku = idBuku;
        this.judul = judul;
        this.penulis = penulis;
        this.penerbit = penerbit;
        this.tahunTerbit = tahunTerbit;
        this.harga = harga;
        this.stok = stok;
        this.jumlahBeli = jumlahBeli;
    }

    public double hitungSubtotal() {
        return jumlahBeli * harga;
    }

    public String getIdBuku() {
        return idBuku;
    }

    public String getJudul() {
        return judul;
    }

    public String getPenulis() {
        return penulis;
    }

    public String getPenerbit() {
        return penerbit;
    }

    public int getTahunTerbit() {
        return tahunTerbit;
    }

    public double getHarga() {
        return harga;
    }

    public int getStok() {
        return stok;
    }

    public int getJumlahBeli() {
        return jumlahBeli;
    }

    @Override
    public String toString() {
        return  "ID Buku : " + idBuku +
                " | Judul : " + judul +
                " | Penulis : " + penulis +
                " | Penerbit : " + penerbit +
                " | Tahun : " + tahunTerbit +
                " | Harga : " + harga +
                " | Beli : " + jumlahBeli +
                " | Subtotal : " + hitungSubtotal();
    }
}
