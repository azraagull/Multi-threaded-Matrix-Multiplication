
## Proje: Matris Çarpımı

Bu proje, Java dilinde matris çarpımını paralel iş parçacıkları kullanarak gerçekleştiren bir uygulamayı içerir. Her bir iş parçacığı bir matrisin satırını hesaplar, bu da büyük matrisler için performans iyileştirmeleri sağlar.

### İçindekiler
1. [Kullanım](#kullanım)
2. [Özellikler](#özellikler)
3. [Örnek Çıktı](#örnek-çıktı)

### Kullanım
Programı çalıştırmak için şu komutu kullanın:
```bash
javac MatrixMultiplication.java
java MatrixMultiplication <row1> <col1> <fileA> <fileB>
```
- `<row1>`: İlk matrisin satır sayısı.
- `<col1>`: İlk matrisin sütun sayısı (ve aynı zamanda ikinci matrisin satır sayısı).
- `<fileA>`: İlk matrisin dosya yolu.
- `<fileB>`: İkinci matrisin dosya yolu.

Örnek:
```bash
java MatrixMultiplication 3 3 matrixA.txt matrixB.txt
```

### Özellikler
- **Matris Okuma**: Program, matris verilerini dosyalardan okur ve içeri aktarır.
- **Paralel Hesaplama**: Her matris satırı için ayrı bir iş parçacığı kullanarak matris çarpımını gerçekleştirir.
- **Sonuç Matrisinin Yazdırılması**: Hesaplanan sonuç matrisi konsola yazdırılır.
- **Zaman Ölçümü**: Her iş parçacığının çalışma süresi nanosaniye cinsinden ölçülür ve raporlanır.
- **Hata Kontrolü**: Dosya okuma ve matris boyutlarının uyumsuzluğu gibi hatalar uygun mesajlarla raporlanır.

### Örnek Çıktı
#### Girdi Dosyaları
**matrixA.txt**
```
1 2 3
4 5 6
7 8 9
```

**matrixB.txt**
```
9 8 7
6 5 4
3 2 1
```

#### Program Çıktısı
```
Matrix A:
1 2 3 
4 5 6 
7 8 9 

Matrix B:
9 8 7 
6 5 4 
3 2 1 

Result Matrix:
30 24 18 
84 69 54 
138 114 90 

Thread Execution Times (in nanoseconds):
Thread 1: 123456
Thread 2: 789012
Thread 3: 345678
```

