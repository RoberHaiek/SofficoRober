package version0;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


public class MyFirstProgram {

	public static void main( String [] args ) throws IOException, InterruptedException {

		Path firstFilePath = Paths.get(args[0]).normalize();
		Path secondFilePath = Paths.get(args[1]).normalize();
		version0.MyFirstPrototype prototype = new MyFirstPrototype( firstFilePath.toFile(),  secondFilePath.toFile() );
		Lock lock = new ReentrantLock();
		while( true ) {
			try {
				lock.lock();
				try {
					if (!prototype.isIdentical())
						FileContentCopier.copySourceToTarget(new File(args[0]), new File(args[1]));
				}finally {
					lock.unlock();
				}
				Thread.sleep(Constants.THREAD_SLEEP_TIME_MS);

			}catch (FileNotFoundException e){
				break;
			}
		}
	}
}
