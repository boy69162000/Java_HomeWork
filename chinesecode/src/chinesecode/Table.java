package chinesecode;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.nio.file.Files;

public class Table {
	private Path file;
	private Charset charset = Charset.forName("US-ASCII");
	private int NHC2DOS[] = new int[65536];
	private int DOS2NHC[] = new int[65536];

	Table(String path) {
		file = Paths.get(path);
		Arrays.fill(NHC2DOS, 0);
		Arrays.fill(DOS2NHC, 0);
		this.fillTable();
	}
	
	private void fillTable() {
		try (BufferedReader reader = Files.newBufferedReader(file, charset)) {
			String s = null;
			int line = 0;
			while ((s = reader.readLine()) != null) {
				int NHC = -1;
				int DOS = -1;
				if (++line < 3) {
					continue;
				}
				
				if (s.length() >= 46) {
					NHC = this.convertStringToWord(s.substring(32, 36));
					DOS = this.convertStringToWord(s.substring(42, 46));
				}
				if (NHC >= 0 && NHC < 65536) {
					NHC2DOS[NHC] = DOS >= 0 && DOS < 65536 ? DOS : 0;
				}
				if (DOS >= 0 && DOS < 65536) {
					DOS2NHC[DOS] = NHC >= 0 && NHC < 65536 ? NHC : 0;
				}
			}
		} catch (IOException x) {
			System.err.format("IOException: %s%n", x);
		}
	}
	
	public int convertNHC2DOS(int NHC) {
		return NHC2DOS[NHC];
	}
	
	public String convertNHC2DOS(String NHC) {
		int DOS = NHC2DOS[this.convertStringToWord(NHC)];
		return String.format("%04x", DOS);
	}
	
	public int convertDOS2NHC(int DOS) {
		return DOS2NHC[DOS];
	}
	
	public String convertDOS2NHC(String DOS) {
		int NHC = DOS2NHC[this.convertStringToWord(DOS)];
		return String.format("%04x", NHC);
	}
	
	public int convertStringToWord(String s) {
		int word;
		try {
			word = Integer.parseInt(s, 16);
		} catch (NumberFormatException e) {
			word = -1;
		}
		
		return word;
	}
}