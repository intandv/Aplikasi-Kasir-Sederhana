package Aplikasi_Kasir;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class Koneksi {

    static Connection getKoneksi() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    private Connection conn = null;
    private String driver = "com.mysql.cj.jdbc.Driver";
    private String url = "jdbc:mysql://localhost:3306/dbrajoseblak"; // URL koneksi ke database

    public Koneksi() {
        try {
            // Memuat driver MySQL JDBC
            Class.forName(driver);
            // Menghubungkan ke database
            conn = DriverManager.getConnection(url, "root", "");
            System.out.println("Database connection established.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Method untuk mengambil data harga dan menu makanan
    public List<MenuMakanan> getMenuMakanan() {
        List<MenuMakanan> listMenu = new ArrayList<>();
        String query = "SELECT MenuMakanan, Harga FROM table_m"; // Query untuk mengambil data
        try {
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();  // Menjalankan query dan mendapatkan hasilnya
            
            while (rs.next()) {
                String menuMakanan = rs.getString("MenuMakanan");
                String harga = rs.getString("Harga");
                listMenu.add(new MenuMakanan(menuMakanan, harga));  // Menambahkan objek MenuMakanan ke list
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listMenu;
    }

    // Menutup koneksi
    public void closeConnection() {
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Kelas MenuMakanan untuk menampung data menu dan harga
    public static class MenuMakanan {
        private String menuMakanan;
        private String harga;

        public MenuMakanan(String menuMakanan, String harga) {
            this.menuMakanan = menuMakanan;
            this.harga = harga;
        }

        public String getMenuMakanan() {
            return menuMakanan;
        }

        public String getHarga() {
            return harga;
        }
    }

    // Main method untuk menjalankan aplikasi
    public static void main(String[] args) {
        Koneksi koneksi = new Koneksi();  // Membuat objek koneksi
        List<MenuMakanan> menu = koneksi.getMenuMakanan();  // Mengambil data menu dan harga dari database

        // Menampilkan hasil di console
        System.out.println("Menu Makanan dan Harga:");
        for (MenuMakanan m : menu) {
            System.out.println("Menu: " + m.getMenuMakanan() + ", Harga: " + m.getHarga());
        }
        
        koneksi.closeConnection();  // Menutup koneksi setelah digunakan
    }
}
