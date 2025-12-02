public class kategoriBuku {

    private String idKategori;
    private String namaKategori;
    private String deskripsi;

    public KategoriBuku(String idKategori, String namaKategori, String deskripsi) {
        this.idKategori = idKategori;
        this.namaKategori = namaKategori;
        this.deskripsi = deskripsi;
    }

    public String getIdKategori() {
        return idKategori;
    }

    public void setIdKategori(String idKategori) {
        this.idKategori = idKategori;
    }

    public String getNamaKategori() {
        return namaKategori;
    }

    public void setNamaKategori(String namaKategori) {
        this.namaKategori = namaKategori;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public void tampilkanDetail() {
        System.out.println("ID Kategori : " + idKategori);
        System.out.println("Nama        : " + namaKategori);
        System.out.println("Deskripsi   : " + deskripsi);
    }
}
