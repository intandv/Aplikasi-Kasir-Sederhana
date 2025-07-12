package Aplikasi_Kasir;

import java.util.Scanner;

public class Konek {
    public static void main(String[] args) {
        // Membuat objek koneksi ke database
        Konek koneksi = new Konek();
        Scanner scanner = new Scanner(System.in);

        // Meminta pengguna memasukkan nama menu
        System.out.print("Masukkan nama menu: ");
        String menu = scanner.nextLine();

        // Mengambil harga dari database berdasarkan nama menu
        String harga = koneksi.getHarga(menu);

        // Menampilkan harga
        if (harga != null) {
            System.out.println("Harga untuk " + menu + " adalah: " + harga);
        }

        // Menutup koneksi setelah selesai
        koneksi.closeConnection();
        scanner.close();
    }

    private String getHarga(String menu) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    private void closeConnection() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
