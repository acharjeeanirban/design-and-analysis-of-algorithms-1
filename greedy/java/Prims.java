import java.io.IOException;
import java.util.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

class AdjVerEdge {

	int ver;
	int edge;

	AdjVerEdge(int ver, int edge) {
		this.ver = ver;
		this.edge = edge;
	}
}

class Prims {

	HashMap<Integer, ArrayList<AdjVerEdge>> graph = new HashMap<>();
	public void readFile() {
		String fileName = "/Users/anirbanacharjee/Documents/design-and-analysis-of-algorithms-1/greedy/prims.txt";
		ArrayList<String> list = new ArrayList<>();
		jobs = new ArrayList<>();
		try (Stream<String> stream = Files.lines(Paths.get(fileName))) {
		//try (Stream<String> stream = Files.lines(Paths.get(fileName))) {
			stream.forEach(s -> list.add(s));
			int flag = 0;
			for (String str : list) {
				if (flag != 0) {
					int first = Integer.parseInt(str.substring(0,str.indexOf(" ")));
					int second = Integer.parseIntstr.substring(str.indexOf(" ") + 1, str.lastIndexOf(" ")));
					int third = Integer.parseInt(str.substring(str.lastIndexOf(" ") + 1));
					AdjVerEdge adjVerEdge1 = null;
					AdjVerEdge adjVerEdge2 = null;
					if (!graph.containsKey(first)) {
						ArrayList<AdjVerEdgš> newList = new ArrayList<>();
						graph.put(first, newList);
						
					} else {
						graph.get(first).add(new AdjVerEdge(second, third));
					}

					if (!graph.containsKey(second)) {
						ArrayList<AdjVerEdgš> newList1 = new ArrayList<>();
						graph.put(second, newList1);
					} else {
						graph.get(second).add(new AdjVerEdge(first, third));
					}

				}
				flag = 1;
			}
		} catch (IOException ioException) {
			ioException.printStackTrace();
		}
	}
	public static void main(String[] args) {
		
	}
}