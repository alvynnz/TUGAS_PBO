public class Kasir {
    private int idKasir;
    private String namaKasir;
    private UserSystem user;

    public Kasir(int idKasir, String namaKasir, UserSystem user) {
        this.idKasir = idKasir;
        this.namaKasir = namaKasir;
        this.user = user;
    }

    public int getIdKasir() {
        return idKasir;
    }

    public void setIdKasir(int idKasir) {
        this.idKasir = idKasir;
    }

    public String getNamaKasir() {
        return namaKasir;
    }

    public void setNamaKasir(String namaKasir) {
        this.namaKasir = namaKasir;
    }

    public UserSystem getUser() {
        return user;
    }

    public void setUser(UserSystem user) {
        this.user = user;
    }

    public String getInfoKasir() {
        return "Kasir { " +
                "ID = " + idKasir +
                ", Nama = " + namaKasir +
                ", User = " + user.getUsername() +
                " }";
    }
}
