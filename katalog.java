package tubes;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

class Data {
	String id;
	String judul;
	String penulis;
	String property;
	Data next;
	Data prev;

}

class DoubleLinkedList {
	Data first;
	Data last;

	void insertAwal(Data input) {
		input.next = last;
		input.prev = first;

		first = input;
		last = input; 
	}

	void insertFront(Data input) {
		input.next = first;
		first.prev = input;
		first = input;
	}

	boolean isEmpty() {
		return (first == null);
		// isEmpty bernilai benar jika datanya kosong
	}

	void display() {
		Data current = first;
		while (current != null) {
			System.out.println(current.judul);
			current = current.next;
		}
	}

	String search(String cari) {
		Data current = first;// data dicari dari awal
		Data tmp = null;
		boolean found = false;
		String hasil = "";
		while (current != null) {// jka datanya tidak kosong
			if (current.judul.contains(cari)) {//jika current.judul mengandung data yang dicari
				tmp = current;// data yang ditemukan disimpan dalam tmp
				found = true;// found menjadi true
				hasil += tmp.id + "\n"; 
				hasil += tmp.judul + "\n";
				hasil += tmp.penulis + "\n";
				//cari id, judul, dan penulis di baris yang sama
				hasil += tmp.property + "\n \n";
				//cari property di bawah baris judul
			}
			current = current.next;
		}
		if (!found) {
			System.out.println("maaf data yang anda cari tidak ada");
		}
		return hasil;
	}

	String search1(String cari) {
		Data current = first; // data dicari dari awal
		Data tmp = null;
		boolean found = false;
		String hasil = "";
		while (current != null) {
			if (current.penulis.contains(cari)) { //jika dalam current.penulist terdapat data yang sama dengan data yang dicari 
				tmp = current;// maka tmp = current.
				found = true;// booelan found menjadi true
				hasil += tmp.id + "\n"; 
				hasil += tmp.judul + "\n";
				hasil += tmp.penulis + "\n";
				//cari id, judul, dan penulis di baris yang sama
				hasil += tmp.property + "\n \n";
				// kemudian cari property yang ada dibawah baris judul
			}
			current = current.next;
		}
		if (!found) {// jika tidak ketemu
			System.out.println("maaf data yang anda cari tidak ada");
		}
		return hasil;
	}
}

public class katalog {

	public static void main(String[] args) {
		Scanner masukkan = new Scanner(System.in);
		File file = new File("C:/Users/Muhammath/Documents/workspace/Woodpecker/src/tubes/GUTINDEX.ALL");
		//baca file GUTINDEX dengan memasukkan pathnya
		BufferedReader reader = null;
		try {
			DoubleLinkedList dll = new DoubleLinkedList();
			reader = new BufferedReader(new FileReader(file));
			
			String text = null;
			String strings = "";
			String id = "";
			String judul;
			String penulis = "";
			String property = "";
			//set data awal
			
			while ((text = reader.readLine()) != null) { // baca text setiap baris
				if (text.length() != 0) { // jika panjang data text tidak sama dengan nol.
					String[] splitted = text.split("[ ]{3,}"); // split data menjadi dua dengan jarak 3 spasi atau lebih
					strings = strings + splitted[0]; // variable string berada pada bagian pertama
					if (splitted.length > 1) {
						id = splitted[1]; // variable id berada pada bagian kedua
					}
				} else {
					String[] splitted = strings.split(", by "); // split variable strings menjadi 2 yang dipisahkan oleh kata "by"
					
					judul = splitted[0];// judul berada pada bagian pertama sebelum "by"
					if (splitted.length > 1) { // jika data yang telah di split lebih dari 1 baris
						int index = splitted[1].indexOf("["); //maka data yang diawali dengan "[" merupakan property
							if (index >= 0) { 
							penulis = splitted[1].substring(0, index); //bagian pertama merupakan penulis
							property = splitted[1].substring(index);//bagian kedua merupakan property
						} else {
							penulis = splitted[1];

						}

						Data tmp = new Data(); // buat sebuah objek tmp dari class Data
						tmp.judul = judul; 
						tmp.penulis = penulis;
						tmp.property = property;
						tmp.id = id;
						// masukkan judul, penulis, property, dan id ke dalam tmp
						dll.insertAwal(tmp);// memasukkan data tmp kedalam DLLD

					}
					strings = "";

				}
			}

			System.out.println("Cari data berdasarkan : ");
			System.out.println("1. Judul");
			System.out.println("2. Penulis");
			System.out.print("Pilihan : ");
			int pilihan = masukkan.nextInt();
			masukkan.nextLine();
			Data tmp;
			String cari;
			if (pilihan == 1) {
				System.out.print("masukkan judul :");
				cari = masukkan.nextLine();
				System.out.println(dll.search(cari));
			} else if (pilihan == 2) {
				System.out.print("masukkan penulis :");
				cari = masukkan.nextLine();
				System.out.println(dll.search1(cari));
			} else {
				System.out.println("maaf pilihan tersebut tidak ada");
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (reader != null) {
					reader.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
