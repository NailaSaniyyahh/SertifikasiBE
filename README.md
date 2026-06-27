# Mini Commerce Kampus

Dibuat untuk memenuhi project akhir sertifikasi back-end java springboot oleh Arutala Lab.
Terdiri dari 2 service yaitu catalog-service dan order-service yang saling berkomunikasi menggunakan REST API (HTTP) dan masing-masing menggunakan database PostgreSQL melalui DBeaver.

### Catalog Service
Fitur: 
- Menambahkan produk
- Melihat daftar produk
- Melihat detail produk
- Mengubah stok produk
- Mengubah status produk (ACTIVE/INACTIVE)

### Order Service 
Fitur: 
- Membuat pesanan
- Melihat daftar pesanan
- Melihat detail pesanan
- Membayar pesanan
- Membatalkan pesanan

## Cara menjalankan proyek
### 1. clone repository
### 2. buat database postgresql di dbeaver yang terdiri dari:
- `catalog_db`
- `order_db`
### 3. sesuaikan password database pada masing-masing service
### 4. run catalog-service
### 5. run order-service
### 6. lakukan semua yang ada pada postman collection dan jangan lupa cek secara berkala database
