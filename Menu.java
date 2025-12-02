import java.util.Scanner;

public class Menu {

    private Scanner input;

    public Menu() {
        input = new Scanner(System.in);
    }

    public void tampilkanMenuUtama() {
        int pilihan;

        do {
            System.out.println("=====================================");
            System.out.println("        SISTEM TOKO BUKU");
            System.out.println("=====================================");
            System.out.println("1. Kelola Buku");
            System.out.println("2. Kelola Pelanggan");
            System.out.println("3. Kelola Transaksi");
            System.out.println("4. Laporan");
            System.out.println("0. Keluar");
            System.out.print("Pilih menu: ");

            pilihan = input.nextInt();

            switch (pilihan) {
                case 1:
                    menuBuku();
                    break;
                case 2:
                    menuPelanggan();
                    break;
                case 3:
                    menuTransaksi();
                    break;
                case 4:
                    menuLaporan();
                    break;
                case 0:
                    System.out.println("Terima kasih telah menggunakan sistem!");
                    break;
                default:
                    System.out.println("Pilihan tidak valid!");
            }

        } while (pilihan != 0);
    }

    private void menuBuku() {
        System.out.println(">> Menu Kelola Buku (coming soon)");
    }

    private void menuPelanggan() {
        System.out.println(">> Menu Kelola Pelanggan (coming soon)");
    }

    private void menuTransaksi() {
        System.out.println(">> Menu Transaksi Penjualan (coming soon)");
    }

    private void menuLaporan() {
        System.out.println(">> Menu Laporan (coming soon)");
    }
}
