package Aplikasi_Kasir;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Date;
import java.text.SimpleDateFormat;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.JFrame;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextArea;
import java.awt.print.PrinterException;
import javax.swing.*;
import java.awt.*;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterJob;
import Aplikasi_Kasir.Koneksi; // Jika ada dua Koneksi, ini bisa jadi konflik.



public class Aplikasi extends javax.swing.JFrame {
     private StrukPrinter strukPrinter; 

    String Tanggal;
    private DefaultTableModel model;
    public void totalBiaya() {
    int jumlahBaris = jTable.getRowCount();
    int totalBiaya = 0; // Variabel untuk menyimpan total biaya

    // Loop melalui setiap baris pada tabel
    for (int i = 0; i < jumlahBaris; i++) {
        int jumlahBarang = 0;
        int hargaBarang = 0;

        // Periksa apakah kolom Harga dan Jumlah tidak kosong
        if (jTable.getValueAt(i, 2) != null && jTable.getValueAt(i, 3) != null) {
            try {
                // Ambil nilai dari kolom Harga (indeks 2) dan Jumlah (indeks 3)
                hargaBarang = Integer.parseInt(jTable.getValueAt(i, 2).toString());
                jumlahBarang = Integer.parseInt(jTable.getValueAt(i, 3).toString());
                
                // Hitung total biaya per baris dan tambahkan ke total keseluruhan
                totalBiaya += jumlahBarang * hargaBarang;
            } catch (NumberFormatException e) {
                System.err.println("Kesalahan format angka pada baris " + i);
            }
        }
    }

    // Tampilkan total biaya pada teks
    txtTotalBayar.setText(String.valueOf(totalBiaya));
    txtTampil.setText(String.format("Rp %,d,00", totalBiaya)); // Format dengan ribuan
}
    

    private void autonumber() {
    DefaultTableModel model = (DefaultTableModel) jTable.getModel(); // Ambil model tabel
    int jumlahBaris = jTable.getRowCount();

    for (int i = 0; i < jumlahBaris; i++) {
        // Format nomor meja: M-01, M-02, dst.
        String nomorMeja = "M" + String.format("%02d", i + 1);

        // Set nilai di kolom No Meja (indeks 0)
        model.setValueAt(nomorMeja, i, 0);
    }
}

    public void loadData(){
        DefaultTableModel model = (DefaultTableModel) jTable.getModel();
    String[][] dataPesanan = new String[model.getRowCount()][4];

for (int i = 0; i < model.getRowCount(); i++) {
    dataPesanan[i][0] = model.getValueAt(i, 0).toString(); // Menu
    dataPesanan[i][1] = model.getValueAt(i, 1).toString(); // Qty
    dataPesanan[i][2] = model.getValueAt(i, 2).toString(); // Harga
    dataPesanan[i][3] = model.getValueAt(i, 3).toString(); // Total
}

        model.addRow(new Object[]{
            txtNoMeja.getText(),
            txtMenuMakanan.getText(),
            txtHarga.getText(),
            txtJumlah.getText(),
            txtTotalBayar.getText()    
        });
        
    }
    
    public void kosong(){
        DefaultTableModel model = (DefaultTableModel)jTable.getModel();
        
        while (model.getRowCount ()>0){
            model.removeRow(0);
        } 
    }
    
    public void utama(){
        txtNoMeja.setText("");
        txtMenuMakanan.setText("");
        txtHarga.setText("");
        txtJumlah.setText("");
        autonumber();
    }
    public void clear(){
        txtNamaPelanggan.setText("");
        txtNoMeja.setText("");
        txtTotalBayar.setText("0");
        txtBayar.setText("0");
        txtKembalian.setText("0");
        txtTampil.setText("0");
        
    }
    
    public void clear2(){
        txtMenuMakanan.setText("");
        txtHarga.setText("");
        txtJumlah.setText("");
    }
    
    public void tambahTransaksi(){
        int jumlah , harga , total ;
        
        jumlah = Integer.valueOf(txtJumlah.getText());
        harga = Integer.valueOf(txtHarga.getText());
        total = jumlah * harga;
        
        txtTotalBayar.setText(String.valueOf(total));
        
        loadData();
        totalBiaya();
        clear2();
    }
        
    public Aplikasi() {
        initComponents();
        StrukPrinter strukPrinter = new StrukPrinter(); // 

        model = new DefaultTableModel();
        jTable.setModel(model);
        
        model.addColumn("No Meja");
        model.addColumn("Menu Makanan");
        model.addColumn("Harga");
        model.addColumn("Jumlah");
        model.addColumn("Total");
        
        utama();
        Date  date = new Date();
        String tanggal = txtTanggal.getText();
        SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd");
        txtTanggal.setText(s.format(date));
        txtTotalBayar.setText("0");
        txtBayar.setText("0");
        txtKembalian.setText("0");
        
    
    }

    // Method untuk menambahkan listener pada tombol
    private void addListeners() {
        btnPrint.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPrintActionPerformed(evt);
            }
        });
    }

    
    
public class AplikasiKasir {
    public static void main(String[] args) {
        // Membuat frame utama
        JFrame frame = new JFrame("Struk Pembayaran");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Membuat JTextArea untuk menampilkan struk
        JTextArea strukArea = new JTextArea(20, 40); // Ukuran area teks: 20 baris, 30 kolom
        strukArea.setText("Kode Pembayaran: 123456\nTotal: Rp 100,000\nTanggal: 1 Januari 2025");

        // Membungkus JTextArea dengan JScrollPane agar ada scroll
        JScrollPane scrollPane = new JScrollPane(strukArea);

        // Menambahkan komponen ke frame
        frame.add(scrollPane, BorderLayout.CENTER);

        // Membuat tombol Print
        JButton btnPrint = new JButton("Print");
        frame.add(btnPrint, BorderLayout.SOUTH);

        // Menangani event klik tombol Print
        btnPrint.addActionListener(e -> {
            try {
                boolean printed = strukArea.print();
                if (printed) {
                    System.out.println("Struk berhasil dicetak.");
                } else {
                    System.out.println("Struk gagal dicetak.");
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        // Pengaturan ukuran dan visibilitas frame
        frame.setSize(400, 300);
        frame.setVisible(true);
    }
}
 private void printStruk() {
        try {
            PrinterJob printerJob = PrinterJob.getPrinterJob();
            printerJob.setPrintable(new Printable() {
                @Override
                public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
                    if (pageIndex > 0) {
                        return Printable.NO_SUCH_PAGE;
                    }

                    // Mengatur ukuran halaman untuk mencetak
                    Graphics2D g2d = (Graphics2D) graphics;
                    g2d.translate(pageFormat.getImageableX(), pageFormat.getImageableY());

                    // Cetak isi dari JTextArea
                    AreaStruk.printAll(graphics);

                    return Printable.PAGE_EXISTS;
                }
            });

            if (printerJob.printDialog()) {
                printerJob.print();
            } else {
                System.out.println("Pencetakan dibatalkan.");
            }
        } catch (PrinterException ex) {
            ex.printStackTrace();  // Menampilkan error jika ada masalah
        }
    }

    



    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtNamaPelanggan = new javax.swing.JTextField();
        txtNoMeja = new javax.swing.JTextField();
        txtTanggal = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        txtMenuMakanan = new javax.swing.JTextField();
        txtHarga = new javax.swing.JTextField();
        txtJumlah = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable = new javax.swing.JTable();
        btnTambah = new javax.swing.JButton();
        btnHapus = new javax.swing.JButton();
        btnSimpan = new javax.swing.JButton();
        txtTampil = new javax.swing.JTextField();
        btnTotalBayar = new javax.swing.JButton();
        btnBayar = new javax.swing.JButton();
        btnKembalian = new javax.swing.JButton();
        txtBayar = new javax.swing.JTextField();
        txtTotalBayar = new javax.swing.JTextField();
        txtKembalian = new javax.swing.JTextField();
        btnPrint = new javax.swing.JButton();
        btnlaporan = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(51, 87, 75));

        jPanel1.setBackground(new java.awt.Color(0, 102, 102));

        jLabel1.setFont(new java.awt.Font("Times New Roman", 1, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Aplikasi Kasir Rajo Seblak");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(255, 255, 255)
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(16, 16, 16))
        );

        jLabel2.setText("Nama Pelanggan");

        jLabel3.setText("No Meja");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("Tanggal");

        txtTanggal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTanggalActionPerformed(evt);
            }
        });

        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("Menu Makanan");

        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("Harga");

        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setText("Jumlah");

        txtMenuMakanan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMenuMakananActionPerformed(evt);
            }
        });
        txtMenuMakanan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtMenuMakananKeyReleased(evt);
            }
        });

        txtHarga.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtHargaActionPerformed(evt);
            }
        });

        txtJumlah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtJumlahActionPerformed(evt);
            }
        });

        jTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(jTable);

        btnTambah.setText("Tambah");
        btnTambah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTambahActionPerformed(evt);
            }
        });

        btnHapus.setText("Hapus");
        btnHapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHapusActionPerformed(evt);
            }
        });

        btnSimpan.setText("Simpan");
        btnSimpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSimpanActionPerformed(evt);
            }
        });

        txtTampil.setBackground(new java.awt.Color(255, 204, 255));
        txtTampil.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        txtTampil.setText("RP.");

        btnTotalBayar.setText("Total Bayar");

        btnBayar.setText("Bayar");
        btnBayar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBayarActionPerformed(evt);
            }
        });

        btnKembalian.setText("Kembalian");

        txtBayar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtBayarActionPerformed(evt);
            }
        });

        txtTotalBayar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTotalBayarActionPerformed(evt);
            }
        });

        btnPrint.setBackground(new java.awt.Color(0, 102, 102));
        btnPrint.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnPrint.setText("Print");
        btnPrint.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPrintActionPerformed(evt);
            }
        });

        btnlaporan.setText("Laporan");
        btnlaporan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnlaporanActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addGap(7, 7, 7)
                                .addComponent(btnSimpan)
                                .addGap(12, 12, 12)
                                .addComponent(txtTampil, javax.swing.GroupLayout.PREFERRED_SIZE, 410, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(btnKembalian, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addGroup(layout.createSequentialGroup()
                                            .addComponent(btnTotalBayar)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(txtTotalBayar))
                                        .addGroup(layout.createSequentialGroup()
                                            .addComponent(btnBayar, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(txtBayar, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                            .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(txtKembalian, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(txtNamaPelanggan)
                                            .addComponent(txtNoMeja, javax.swing.GroupLayout.DEFAULT_SIZE, 167, Short.MAX_VALUE)))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                            .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, 184, Short.MAX_VALUE)
                                            .addComponent(txtMenuMakanan))
                                        .addGap(62, 62, 62)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(txtHarga, javax.swing.GroupLayout.DEFAULT_SIZE, 181, Short.MAX_VALUE)
                                            .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(0, 0, Short.MAX_VALUE)
                                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(txtTanggal, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(55, 55, 55)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(txtJumlah, javax.swing.GroupLayout.DEFAULT_SIZE, 193, Short.MAX_VALUE)
                                            .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addGap(0, 0, Short.MAX_VALUE))))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 779, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(btnTambah, javax.swing.GroupLayout.DEFAULT_SIZE, 78, Short.MAX_VALUE)
                                            .addComponent(btnHapus, javax.swing.GroupLayout.DEFAULT_SIZE, 78, Short.MAX_VALUE)
                                            .addComponent(btnlaporan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addGap(0, 0, Short.MAX_VALUE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addGap(0, 0, Short.MAX_VALUE)
                                        .addComponent(btnPrint, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)))))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtNamaPelanggan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtTanggal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtNoMeja, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(48, 48, 48)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jLabel6)
                    .addComponent(jLabel7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtMenuMakanan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtHarga, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtJumlah, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(btnTambah, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnHapus, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnlaporan, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnPrint, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 237, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(5, 5, 5)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnSimpan, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(7, 7, 7)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(btnTotalBayar)
                                    .addComponent(txtTotalBayar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(btnBayar)
                                    .addComponent(txtBayar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnKembalian)
                            .addComponent(txtKembalian, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(txtTampil, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(57, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnBayarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBayarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnBayarActionPerformed

    private void btnTambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTambahActionPerformed
        tambahTransaksi();
    }//GEN-LAST:event_btnTambahActionPerformed

    private void btnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSimpanActionPerformed
    DefaultTableModel model = (DefaultTableModel) jTable.getModel();
    String namaPelanggan = txtNamaPelanggan.getText();
    String noMeja = txtNoMeja.getText();
    String tanggal = txtTanggal.getText();

    Connection conn = null;
    try {
    String jdbcDriver = "com.mysql.cj.jdbc.Driver";
    Class.forName(jdbcDriver);

    String url = "jdbc:mysql://localhost:3306/dbrajoseblak";
    String user = "root";
    String pass = "";

    conn = DriverManager.getConnection(url, user, pass);

    // Query untuk memasukkan data ke tabel penjualan termasuk Total_Bayar
    String sqlPenjualan = "INSERT INTO penjualan (Nama_Pelanggan, No_Meja, Menu_Makanan, Harga, Jumlah, Tanggal, Total_Beli, Total_Bayar) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
    PreparedStatement penjualanStmt = conn.prepareStatement(sqlPenjualan);

    int baris = jTable.getRowCount();
    double totalBayar = 0;

    for (int i = 0; i < baris; i++) {
        // Ambil data dari JTable
        String menuMakanan = jTable.getValueAt(i, 1).toString(); // Kolom 1: Menu Makanan
        double harga = Double.parseDouble(jTable.getValueAt(i, 2).toString()); // Kolom 2: Harga
        int jumlah = Integer.parseInt(jTable.getValueAt(i, 3).toString()); // Kolom 3: Jumlah
        double total = Double.parseDouble(jTable.getValueAt(i, 4).toString()); // Kolom 4: Total
        totalBayar += total;  // Menambahkan total ke totalBayar

        // Validasi
        if (menuMakanan == null || menuMakanan.trim().isEmpty()) {
            System.out.println("Kolom Menu Makanan kosong pada baris " + i);
            continue;
        }

        // Masukkan ke database
        penjualanStmt.setString(1, namaPelanggan);   // Nama pelanggan
        penjualanStmt.setString(2, noMeja);          // No meja
        penjualanStmt.setString(3, menuMakanan);     // Menu makanan
        penjualanStmt.setDouble(4, harga);           // Harga
        penjualanStmt.setInt(5, jumlah);             // Jumlah
        penjualanStmt.setString(6, tanggal);         // Tanggal
        penjualanStmt.setDouble(7, total);           // Total per item
        penjualanStmt.setDouble(8, totalBayar);      // Total Bayar

        penjualanStmt.executeUpdate();
    }

    // Cetak log untuk debugging
    System.out.println("Data berhasil disimpan ke database dengan total bayar: " + totalBayar);


    // Cetak log untuk debugging
    System.out.println("Data berhasil disimpan ke database!");

    // Tambahkan kode pembuatan struk di sini
    StringBuilder struk = new StringBuilder();
    struk.append("===============================\n");
    struk.append("        RAJO SEBLAK\n");
    struk.append("     Jl. Parak Gadang No. 1\n");
    struk.append("       Telp: 083187018014\n");
    struk.append("===============================\n");
    struk.append("Nama Pelanggan: ").append(namaPelanggan).append("\n");
    struk.append("No Meja       : ").append(noMeja).append("\n");
    struk.append("Tanggal        : ").append(tanggal).append("\n");
    struk.append("===============================\n");
    struk.append("Menu            Jumlah  Harga   Total\n");

    for (int i = 0; i < baris; i++) {
        String menuMakanan = jTable.getValueAt(i, 1).toString();
        int jumlah = Integer.parseInt(jTable.getValueAt(i, 3).toString());
        double harga = Double.parseDouble(jTable.getValueAt(i, 2).toString());
        double total = Double.parseDouble(jTable.getValueAt(i, 4).toString());

        // Format baris
        struk.append(String.format("%-15s %5d %7.0f %8.0f\n", menuMakanan, jumlah, harga, total));
    }

    double bayar = Double.parseDouble(txtBayar.getText()); // Ambil nilai dari input bayar
    double kembalian = bayar - totalBayar;
    
    

    struk.append("===============================\n");
    struk.append(String.format("Total Bayar: Rp. %.0f\n", totalBayar));
    struk.append(String.format("Bayar      : Rp. %.0f\n", bayar));
    struk.append(String.format("Kembalian  : Rp. %.0f\n", kembalian));
    struk.append("===============================\n");
    struk.append("Terima kasih atas kunjungan Anda!\n");

    JTextArea strukArea = new JTextArea(struk.toString());
    strukArea.setEditable(false);

    // Tambahkan JScrollPane untuk scroll jika struk panjang
    JScrollPane scrollPane = new JScrollPane(strukArea);

    // Tampilkan struk di JFrame
    JFrame frame = new JFrame("Struk Pembayaran");
    frame.add(scrollPane);
    frame.setSize(400, 500);
    frame.setLocationRelativeTo(null);
    frame.setVisible(true);

} catch (Exception e) {
    e.printStackTrace();
} finally {
    try {
        if (conn != null) conn.close();
    } catch (Exception e) {
        e.printStackTrace();
    }
}

// Membersihkan form dan reset tampilan
clear();
utama();
autonumber();
kosong();
txtTampil.setText("Rp.0");
     // TODO add your handling code here:
    }//GEN-LAST:event_btnSimpanActionPerformed

    private void txtBayarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtBayarActionPerformed
        int total, bayar ,kembalian;
        total = Integer.valueOf(txtTotalBayar.getText());
        bayar = Integer.valueOf(txtBayar.getText());
        
        if (total > bayar){
            JOptionPane.showMessageDialog(null,"Uang Cukup Untuk Melakukan Pemabayaran");
        } else {
            kembalian= bayar - total;
            txtKembalian.setText(String.valueOf(kembalian));
        }
            
    }//GEN-LAST:event_txtBayarActionPerformed

    private void btnHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHapusActionPerformed
        DefaultTableModel model = (DefaultTableModel) jTable.getModel();
        int row = jTable.getSelectedRow();
        model.removeRow(row);
        totalBiaya();
        txtBayar.setText("0");
        txtKembalian.setText("0");
        
    }//GEN-LAST:event_btnHapusActionPerformed

    private void txtJumlahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtJumlahActionPerformed
        tambahTransaksi();        // TODO add your handling code here:
    }//GEN-LAST:event_txtJumlahActionPerformed

    private void txtTotalBayarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTotalBayarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTotalBayarActionPerformed

    private void txtMenuMakananActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMenuMakananActionPerformed
     
    }//GEN-LAST:event_txtMenuMakananActionPerformed

    private void txtHargaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtHargaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtHargaActionPerformed

    private void txtMenuMakananKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtMenuMakananKeyReleased
    String menu = txtMenuMakanan.getText();
if (!menu.isEmpty()) {
    try {
        // Koneksi ke database
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/dbrajoseblak", "root", "");

        // Query untuk mengambil harga berdasarkan Menu Makanan
        String query = "SELECT `Harga` FROM `table_menu` WHERE `Menu Makanan` = ?";
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setString(1, menu); // Set nilai menu dari input pengguna

        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            // Ambil harga dari hasil query
            double harga = rs.getDouble("Harga");

            // Format harga menjadi bilangan bulat tanpa desimal
            txtHarga.setText(String.valueOf((int) harga)); 
        } else {
            // Jika menu tidak ditemukan
            txtHarga.setText("Menu tidak ditemukan");
        }

        // Tutup ResultSet, PreparedStatement, dan koneksi
        rs.close();
        stmt.close();
        conn.close();
    } catch (SQLException ex) {
        // Cetak error di konsol dan tampilkan pesan error ke pengguna
        ex.printStackTrace();
        JOptionPane.showMessageDialog(null, "Terjadi kesalahan: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }
} else {
    txtHarga.setText(""); // Kosongkan harga jika input kosong
}


    }//GEN-LAST:event_txtMenuMakananKeyReleased

    private void btnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrintActionPerformed
    
    String struk = "=============================\n";
    struk += "           RAJO SEBLAK\n";
    struk += "    Jl. Parak Gadang No. 1\n";
    struk += "    Telp: 083187018014\n";
    struk += "=============================\n";
    struk += "Nama Pelanggan: " + txtNamaPelanggan.getText() + "\n";
    struk += "No Meja       : " + txtNoMeja.getText() + "\n";
    struk += "Tanggal       : " + txtTanggal.getText() + "\n";
    struk += "-----------------------------\n";
    struk += "Menu       Jumlah   Harga   Total\n";

    for (int i = 0; i < jTable.getRowCount(); i++) {
        String menu = jTable.getValueAt(i, 1) != null ? jTable.getValueAt(i, 1).toString() : "";
        String jumlah = jTable.getValueAt(i, 3) != null ? jTable.getValueAt(i, 3).toString() : "";
        String harga = jTable.getValueAt(i, 2) != null ? jTable.getValueAt(i, 2).toString() : "";
        String total = jTable.getValueAt(i, 4) != null ? jTable.getValueAt(i, 4).toString() : "";
        struk += String.format("%-10s %-8s %-8s %-8s\n", menu, jumlah, harga, total);
    }

    struk += "=============================\n";
    struk += "Total Bayar : Rp. " + btnTotalBayar.getText() + "\n";
    struk += "Bayar       : Rp. " + btnBayar.getText() + "\n";
    struk += "Kembalian   : Rp. " + btnKembalian.getText() + "\n";
    struk += "=============================\n";
    struk += "Terima kasih atas kunjungan Anda!\n";

    
    // Tampilkan teks struk ke StrukPrinter
    strukPrinter.setStruk(struk);
    // Cetak struk
    strukPrinter.cetakStruk();


    
    


    }//GEN-LAST:event_btnPrintActionPerformed

    private void txtTanggalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTanggalActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTanggalActionPerformed

    private void btnlaporanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnlaporanActionPerformed
      Laporan_Penjualan laporanpenjualan = new Laporan_Penjualan();
      laporanpenjualan.setVisible(true);
      this.dispose();

      
    }//GEN-LAST:event_btnlaporanActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Aplikasi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Aplikasi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Aplikasi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Aplikasi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Aplikasi().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBayar;
    private javax.swing.JButton btnHapus;
    private javax.swing.JButton btnKembalian;
    private javax.swing.JButton btnPrint;
    private javax.swing.JButton btnSimpan;
    private javax.swing.JButton btnTambah;
    private javax.swing.JButton btnTotalBayar;
    private javax.swing.JButton btnlaporan;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable;
    private javax.swing.JTextField txtBayar;
    private javax.swing.JTextField txtHarga;
    private javax.swing.JTextField txtJumlah;
    private javax.swing.JTextField txtKembalian;
    private javax.swing.JTextField txtMenuMakanan;
    private javax.swing.JTextField txtNamaPelanggan;
    private javax.swing.JTextField txtNoMeja;
    private javax.swing.JTextField txtTampil;
    private javax.swing.JTextField txtTanggal;
    private javax.swing.JTextField txtTotalBayar;
    // End of variables declaration//GEN-END:variables
}
