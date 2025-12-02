public class Kasir{
    private int Id_kasir;
    private String nama_kasir;
    private UserSystem_user;

    public kasir (int Id_kasir String nama_kasir, UserSystem_user){
        this.Id_kasir=Id_kasir;
        this.nama_kasir=nama_kasir;
        this.user=user;
    }
    public int get Id_kasir(){
        retrun Id_kasir;

    }
    public void set Id_kasir(int Id_kasir){
        this.Id_kasir=Id_kasir

    }
    public String get nama_kasir(){
        retrun nama_kasir;
    }
    public void set nama_kasir(String nama_kasir){
        this.nama_kasir=nama_kasir;
    }
    public UserSystem_user get user(){
        retrun user;
    }
    public void set user (UserSystem_user){
        this.user=user;
    }

    public String get infokasir(){
        retrun "kasir {" + "id = " + Id_kasir + "nama= " nama_kasir + + "user= " + user getusername()+ +
        "}";
    }


}