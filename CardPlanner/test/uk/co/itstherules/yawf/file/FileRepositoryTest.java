package uk.co.itstherules.yawf.file;

import java.io.File;

import junit.framework.Assert;

import org.junit.Test;

public class FileRepositoryTest {
	
	@Test(expected=IllegalArgumentException.class) public void forNonExistentDirectory() {
		File directory = new File("test-resource/i_do-not_exist");
		FileRepository unit = new FileRepository();
		unit.nextIncrementIn(directory);
	}

	@Test public void for2SequentialFiles() throws Exception {
		File directory = new File("test-resource/twofiles");
		FileRepository unit = new FileRepository();
		Assert.assertEquals(Long.valueOf(1), unit.latestIn(directory));
		Assert.assertEquals(Long.valueOf(2), unit.nextIncrementIn(directory));
	}
	
	@Test public void for2NonSequentialFiles() throws Exception {
		File directory = new File("test-resource/twofiles_not_sequential");
		FileRepository unit = new FileRepository();
		Assert.assertEquals(Long.valueOf(4), unit.latestIn(directory));
		Assert.assertEquals(Long.valueOf(5), unit.nextIncrementIn(directory));
	}

	@Test public void for2FilesWithDSStoreFile() throws NoRepositoryReferenceExists {
		File directory = new File("test-resource/twofiles_with_ds_store");
		FileRepository unit = new FileRepository();
		Assert.assertEquals(Long.valueOf(1), unit.latestIn(directory));
		Assert.assertEquals(Long.valueOf(2), unit.nextIncrementIn(directory));
	}
}
