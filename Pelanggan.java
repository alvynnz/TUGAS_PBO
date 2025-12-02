public class Pelanggan {
    private int idPelanggan;
    private String nama;
    private String alamat;
    private String noTelepon;

    public Pelanggan(int idPelanggan, String nama, String alamat, String noTelepon) {
        this.idPelanggan = idPelanggan;
        this.nama = nama;
        this.alamat = alamat;
        this.noTelepon = noTelepon;
    }

    public int getIdPelanggan() {
        return idPelanggan;
    }

    public void setIdPelanggan(int idPelanggan) {
        this.idPelanggan = idPelanggan;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getNoTelepon() {
        return noTelepon;
    }

    public void setNoTelepon(String noTelepon) {
        this.noTelepon = noTelepon;
    }

    public String getInfoPelanggan() {
        return "Pelanggan [ID=" + idPelanggan + 
               ", Nama=" + nama + 
               ", Alamat=" + alamat + 
               ", No Telepon=" + noTelepon + "]";
    }
}
