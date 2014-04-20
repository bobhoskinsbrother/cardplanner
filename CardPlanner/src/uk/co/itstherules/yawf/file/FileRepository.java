package uk.co.itstherules.yawf.file;

import java.io.File;
import java.io.FilenameFilter;

public class FileRepository {

	public Long latestIn(File uploadedDirectory) throws NoRepositoryReferenceExists {
		if(!uploadedDirectory.exists()) {
			throw new IllegalArgumentException("Directory does not exist");
		}
		String[] list = uploadedDirectory.list(new OnlyNumbersFilenameFilter());
		long largest = -1; 
		for (String fileNumber : list) {
	        long candidate = Long.parseLong(fileNumber);
			if(candidate > largest) { largest = candidate; }
        }
		if(largest==-1) {
			throw new NoRepositoryReferenceExists();
		}
		return new Long(largest);
    }

	public Long nextIncrementIn(File uploadedDirectory) {
		try {
	        return new Long(latestIn(uploadedDirectory).longValue()+1L);
        } catch (NoRepositoryReferenceExists e) {
	        return new Long(0);
        }
    }
	
	// for M(h)ac writing files to directory after browsing
	private static class OnlyNumbersFilenameFilter implements FilenameFilter {
		public boolean accept(File dir, String name) {
			try {
				Long.parseLong(name);
				return true;
			} catch (Exception e) {
				return false;
			}
        }
	}
}
