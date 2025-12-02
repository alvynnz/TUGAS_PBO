public class Buku {

    private String idBuku;
    private String judul;
    private String penulis;
    private String penerbit;
    private int tahunTerbit;
    private double harga;
    private int stok;

    private kategoriBuku kategori;

    public Buku(String idBuku, String judul, String penulis, String penerbit,
                int tahunTerbit, double harga, int stok, KategoriBuku kategori) {

        this.idBuku = idBuku;
        this.judul = judul;
        this.penulis = penulis;
        this.penerbit = penerbit;
        this.tahunTerbit = tahunTerbit;
        this.harga = harga;
        this.stok = stok;
        this.kategori = kategori;
    }

    public String getIdBuku() {
        return idBuku;
    }

    public void setIdBuku(String idBuku) {
        this.idBuku = idBuku;
    }

    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public String getPenulis() {
        return penulis;
    }

    public void setPenulis(String penulis) {
        this.penulis = penulis;
    }

    public String getPenerbit() {
        return penerbit;
    }

    public void setPenerbit(String penerbit) {
        this.penerbit = penerbit;
    }

    public int getTahunTerbit() {
        return tahunTerbit;
    }

    public void setTahunTerbit(int tahunTerbit) {
        this.tahunTerbit = tahunTerbit;
    }

    public double getHarga() {
        return harga;
    }

    public void setHarga(double harga) {
        this.harga = harga;
    }

    public int getStok() {
        return stok;
    }

    public void setStok(int stok) {
        this.stok = stok;
    }

    public KategoriBuku getKategori() {
        return kategori;
    }

    public void setKategori(KategoriBuku kategori) {
        this.kategori = kategori;
    }

    public void tampilkanDetail() {
        System.out.println("ID Buku     : " + idBuku);
        System.out.println("Judul       : " + judul);
        System.out.println("Penulis     : " + penulis);
        System.out.println("Penerbit    : " + penerbit);
        System.out.println("Tahun Terbit: " + tahunTerbit);
        System.out.println("Harga       : " + harga);
        System.out.println("Stok        : " + stok);

        System.out.println("Kategori    : " + kategori.getNamaKategori());
    }
}
