package version0;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class MyFirstPrototype {

	private File i;
	private File y;

	public MyFirstPrototype(File filea, File fileb) {
		this.i = filea;
		this.y = fileb;
	}

	public boolean doIt() throws IOException {

		BufferedReader rg = new BufferedReader(new FileReader(i));
		BufferedReader rr = new BufferedReader(new FileReader(y));
		String r = null;
		String g = null;

		while (true) {
			if ((r = rg.readLine()) == null || (g = rr.readLine()) == null
			|| r.compareTo(g) != 0)
				break;
		}

		if (r == null && g == null)
			return true;

		return false;
	}

}
