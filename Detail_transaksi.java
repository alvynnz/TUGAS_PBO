public class Detail_transaksi {

    private Buku buku;
    private int jumlahBeli;

    public Detail_transaksi(Buku buku, int jumlahBeli) {
        this.buku = buku;
        this.jumlahBeli = jumlahBeli;
    }

    public double hitungSubtotal() {
        return jumlahBeli * buku.getHarga();
    }

    public Buku getBuku() {
        return buku;
    }

    public int getJumlahBeli() {
        return jumlahBeli;
    }

    @Override
    public String toString() {
        return  "Judul : " + buku.getJudul() +
                " | Penulis : " + buku.getPenulis() +
                " | Harga : " + buku.getHarga() +
                " | Jumlah : " + jumlahBeli +
                " | Subtotal : " + hitungSubtotal();
    }
}
