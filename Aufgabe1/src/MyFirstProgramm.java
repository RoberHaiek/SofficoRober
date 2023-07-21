package version0;
import java.io.File;
import java.io.IOException;


public class MyFirstProgramm {


	public static void main( String [] args ) throws IOException, InterruptedException {

		MyFirstPrototype prototyp = new MyFirstPrototype( new File( args[ 0 ] ),  new File( args[ 1 ] ) );

		while( true ) {

			if( ! prototyp.doIt() )

				MyHelper.helpMe( new File( args[ 0 ] ),  new File( args[ 1 ] ) );

			Thread.sleep( 10000 );
		}
	}
}
