package fileUtils;

import java.io.File;

public class JsonFiles {

	//checking the number of files present in the input directory where .json files should exist.
	static int fileCount(String path) {
		File fileDir = new File(path);
		int fileCounts = fileDir.list().length;
		int nos = 0;
		System.out.println(fileCounts);
		if (fileCounts < 1) {
			System.out.println("Your Directory don't have any files");
		} else {
			nos = fileCounts;
		}
		return nos;
	}

	

}
