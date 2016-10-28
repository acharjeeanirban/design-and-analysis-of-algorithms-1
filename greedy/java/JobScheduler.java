import java.io.IOException;
import java.util.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;


class Job {

	long weight;
	long length;
	long weightMinusLength;
	double ratio;
	Job (long weight, long length) {
		this.weight = weight;
		this.length = length;
		this.weightMinusLength = this.weight - this.length;
		this.ratio = (double)this.weight/(double)this.length;
	}
}

class JobComparatorWeightMinusLength implements Comparator<Job> {

	public int compare(Job first, Job second) {
		if (first.weightMinusLength != second.weightMinusLength) {
			return (int)(second.weightMinusLength - first.weightMinusLength);	
		} else {
			return (int)(second.weight - first.weight);
		}
		
	}
} 

class JobComparatorRatio implements Comparator<Job> {

	public int compare(Job first, Job second) {
		double firstRatio = first.ratio;
		double secondRatio = second.ratio;
		if (secondRatio > firstRatio) {
			return 1;
		} 

		if (secondRatio < firstRatio) {
			return -1;
		}

		return 0;
	}
}


class JobScheduler {

	ArrayList<Job> jobs;

	public void readFile() {
		String fileName = "/Users/anirbanacharjee/Documents/design-and-analysis-of-algorithms-1/greedy/jobs.txt";
		ArrayList<String> list = new ArrayList<>();
		jobs = new ArrayList<>();
		try (Stream<String> stream = Files.lines(Paths.get(fileName))) {
		//try (Stream<String> stream = Files.lines(Paths.get(fileName))) {
			stream.forEach(s -> list.add(s));
			int flag = 0;
			for (String str : list) {
				if (flag != 0) {
					String first = str.substring(0,str.indexOf(" "));
					String second = str.substring(str.indexOf(" ") + 1);
					jobs.add(new Job(Long.parseLong(first), Long.parseLong(second)));
				}
				flag = 1;
			}
		} catch (IOException ioException) {
			ioException.printStackTrace();
		}
	}

	public long getJobCompletionTimeMinus() {
		Collections.sort(jobs, new JobComparatorWeightMinusLength());
		long totalCompletionTime = 0;
		long totalTime = 0;
		for (Job job : jobs) {
			//System.out.println(job.weight + " " + job.length);
			totalTime += job.length;
			totalCompletionTime += job.weight*totalTime;
		}

		return totalCompletionTime;
	}

	public long getJobCompletionTimeRatio() {
		Collections.sort(jobs, new JobComparatorRatio());
		long totalCompletionTime = 0;
		long totalTime = 0;
		for (Job job : jobs) {
			//System.out.println(job.weight + " " + job.length + " " + job.ratio);
			totalTime += job.length;
			totalCompletionTime += job.weight*totalTime;
		}

		return totalCompletionTime;
	}

	public static void main(String[] args) {
		JobScheduler jobScheduler = new JobScheduler();
		jobScheduler.readFile();
		System.out.println("The completion time for minus is = " + jobScheduler.getJobCompletionTimeMinus());
		System.out.println("The completion time for minus is = " + jobScheduler.getJobCompletionTimeRatio());
	}
}