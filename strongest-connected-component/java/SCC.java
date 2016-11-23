import java.io.IOException;
import java.util.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

class SCC {

	HashMap<Integer, ArrayList<Integer>> graph = new HashMap<>();
	HashMap<Integer, ArrayList<Integer>> graphDuplicate = new HashMap<>();
	HashMap<Integer, ArrayList<Integer>> reversedGraph = new HashMap<>();
	HashMap<Integer, Boolean> visitedGraph = new HashMap<>();
	HashMap<Integer, Boolean> visitedGraphSecondpass = new HashMap<>();
	int totalNodes = 0;
	int finishingTimes[];
	public void readFile() {
		String fileName = "/Users/anirbanacharjee/Documents/design-and-analysis-of-algorithms-1/strongest-connected-component/scc.txt";
		List<String> list = new ArrayList<>();
		int max = -1;
		try (Stream<String> stream = Files.lines(Paths.get(fileName))) {
			stream.forEach(s -> list.add(s));
			for (String str : list) {
					String arr[] = str.split(" ");
					
					int first = Integer.parseInt(arr[0]);
					int second = Integer.parseInt(arr[1]);

					int m = Math.max(first, second);

					max = Math.max(max,m);

					// visitedGraph.put(first, false);
					// visitedGraph.put(second, false);


					// visitedGraphSecondpass.put(first, false);
					// visitedGraphSecondpass.put(second, false);

					if (graph.containsKey(first)) {
						graph.get(first).add(second);
						//graphDuplicate.get(first).add(second);
					} else {
						ArrayList<Integer> list1 = new ArrayList<Integer>();
						list1.add(second);
						graph.put(first, list1);
						//graphDuplicate.put(first, list1);
					}
			}

		} catch (IOException ioException) {
			ioException.printStackTrace();
		}

		totalNodes = max;

		System.out.println("Total size = " + totalNodes);
		System.out.println("Total size = " + max);
		finishingTimes = new int[totalNodes+1];

		//System.out.println("graph : " + graph);

		

		System.out.println("DONE");

		for (int i = totalNodes; i >= 1; i--) {
			visitedGraphSecondpass.put(i, false);
			visitedGraph.put(i, false);
		}

	}

	int time=1;

	public void doDFSFirstPass(int start) {
		Stack<Integer> stack = new Stack<Integer>();
		stack.push(start);
		visitedGraph.put(start, true);
		while (!stack.isEmpty()) {
			int i = stack.pop();
			if (reversedGraph.containsKey(i)) {
				boolean firstToComplete = true;
				for (int val : reversedGraph.get(i)) {
					if (!visitedGraph.get(val)) {
						visitedGraph.put(val, true);
						stack.push(i);
						stack.push(val);
						firstToComplete = false;
						break;
					}
				}

				if (firstToComplete) {
					finishingTimes[i] = time++;
				}

			} else {
				finishingTimes[i] = time++;
			}

		}

	}
	ArrayList<Integer> maxCount = new ArrayList<Integer>();
	PriorityQueue<Integer> pq = new PriorityQueue<>(Collections.reverseOrder());
	public void doDFSSecondPass(int start) {
		Stack<Integer> stack = new Stack<Integer>();
		stack.push(start);
		visitedGraphSecondpass.put(start, true);
		int c = 1;
		while (!stack.isEmpty()) {
			int i = stack.pop();
			if (graphDuplicate.containsKey(i)) {
				//boolean firstToComplete = true;
				for (int val : graphDuplicate.get(i)) {
					if (!visitedGraphSecondpass.get(val)) {
						c++;
						visitedGraphSecondpass.put(val, true);
						stack.push(val);
						//firstToComplete = false;
					}
				}

				// if (firstToComplete) {
				// 	finishingTimes[i] = time++;
				// }

			} else {
				//System.out.println("TTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTT");
			}

		}

		pq.offer(c);

		//maxCount.add(c);

	}

	public void changeGraphlabel() {
		for (Map.Entry<Integer, ArrayList<Integer>> entry : graph.entrySet()) {
			int key = entry.getKey();
			ArrayList<Integer> list = new ArrayList<>();
			for (int i : entry.getValue()) {
				list.add(finishingTimes[i]);
			}
			//int key = entry.getKey();
			//graphDuplicate.remove(key);
			graphDuplicate.put(finishingTimes[key], list);
		}
	}

	public void reverseGraph() {
		for (Map.Entry<Integer, ArrayList<Integer>> entry : graph.entrySet()) {
			int key =entry.getKey();
			for (int i : entry.getValue()) {
				if (reversedGraph.containsKey(i)) {
					reversedGraph.get(i).add(key);
				} else {
					ArrayList<Integer> list1 = new ArrayList<Integer>();
					list1.add(key);
					reversedGraph.put(i, list1);
				}
			}
		}
	}


	public int doSCC() {
		reverseGraph();

		//System.out.println("reverseGraph : " + reversedGraph);

		for (int i = totalNodes; i >= 1; i--) {
			if (!visitedGraph.get(i) && reversedGraph.containsKey(i)) {
				doDFSFirstPass(i);	
			}
			
		}


		//diagonostics
		// for (int i = 1; i <= totalNodes; i++) {
		// 	System.out.println("i = " + i + ", " + finishingTimes[i]) ;
		// }

		changeGraphlabel();

		int totalScc = 0;

		for (int i = totalNodes; i >= 1; i--) {
			if (visitedGraphSecondpass.containsKey(i) && !visitedGraphSecondpass.get(i)) {
				totalScc++;
				doDFSSecondPass(i);
			}
		}


		return totalScc;

	}
	public static void main(String[] args) {
		SCC scc = new SCC();
		scc.readFile();

		System.out.println("total scc = " + scc.doSCC());
		//	System.out.println(scc.maxCount);

		for (int i = 0; i < 5; i++) {
			System.out.print(scc.pq.poll()+",");
		}
		//System.out.println(scc.pq);
		
	}
}