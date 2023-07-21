package version0;
import java.io.File;
import java.io.IOException;


public class MyFirstProgram {


	public static void main( String [] args ) throws IOException, InterruptedException {

		MyFirstPrototype prototyp = new MyFirstPrototype( new File( args[ 0 ] ),  new File( args[ 1 ] ) );

		while( true ) {

			if( ! prototyp.doIt() )

				FileContentCopier.helpMe( new File( args[ 0 ] ),  new File( args[ 1 ] ) );

			Thread.sleep( 10000 );
		}
	}
}
