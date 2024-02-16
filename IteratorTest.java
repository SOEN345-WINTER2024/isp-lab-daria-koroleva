import static org.junit.Assert.*;

import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.ConcurrentModificationException;
import java.util.NoSuchElementException;

import org.junit.Before;
import org.junit.Test;


public class IteratorTest {
	
	private List<String> list; 
	private Iterator<String> iterator;
	
	@Before
	public void setUp() {
		
		 list = new ArrayList<>();	
		 
		 list.add("Athena");
		 list.add("Hercules");
		 
		 iterator =list.iterator();		 
	}
	
	
	 /* 
	    C1 More values	  
	   	C2 Returns non-null object
		C3 remove() supported
		C4 remove() constraint satisfied
		C5 Concurrent modification 
	*/
	 
	//hasNext() tests

	@Test
	public void testHasNext_C1_C5_TT() {       
	    assertTrue(iterator.hasNext());		
	}
	
	@Test
	public void testHasNext_C1_C5_FT() {
		iterator.next();
		iterator.next();
		assertFalse(iterator.hasNext());		
	}
	
	//This one will fail, probably for java implementation of iterator
	//An structural modification supposed to generate this ConcurrentModificationException
	@Test(expected=ConcurrentModificationException.class)
	public void testHasNext_C1_C5_TF() {
		//This is a structural modification
		//list is change after iterator is created		
		//When an iterator is created, it is expected to reflect the state of the collection 
		//at the point of its creation. 
		list.add("Zeus");
		iterator.hasNext();		
	}
	
	
    //next() tests
	@Test
	public void testNext_C1_C2_C5_TTT() { 
		assertEquals("Athena",iterator.next());	
	}
	
	@Test(expected=NoSuchElementException.class)
	public void testNext_C1_C2_C5_FFT() {
		iterator.next();
		iterator.next();
		iterator.next();			
	}
	
	@Test
	public void testNext_C1_C2_C5_TFT() {		
		list = new ArrayList<>();
		list.add(null);
		
		iterator = list.iterator();		
		assertEquals(null,iterator.next());		
	}
	
	@Test(expected=ConcurrentModificationException.class)
	public void testNext_C1_C2_C5_TFF() {
		
		list.add(null);
		iterator.next();
		
	}
	
	//remove() tests
	@Test
	public void testRemove_C1_C2_C3_C4_C5_TTTTT() {
		
		iterator.next();
		iterator.remove();
		
		assertFalse(list.contains("Athena"));
		
	}
	
	@Test
	public void testRemove_C1_C2_C3_C4_C5_FFTTT() {
		iterator.next();
		iterator.next();
		
		iterator.remove();
		assertFalse(list.contains("Zeus"));	
		
		
	}
	@Test
	public void testRemove_C1_C2_C3_C4_C5_TFTTT() {
		list.add(null);
		list.add("Zeus");
		iterator = list.iterator();
		iterator.next();
		iterator.next();
		iterator.next();
		iterator.remove();
		
		assertFalse(list.contains(null));		
	}
	@Test(expected=UnsupportedOperationException.class)
	public void testRemove_C1_C2_C3_C4_C5_TTFTT() {
		
		 list = Collections.unmodifiableList (list);
		 iterator = list.iterator();
	      
		 iterator.next();
		 iterator.remove();
	}
	
	@Test(expected=IllegalStateException.class)
	public void testRemove_C1_C2_C3_C4_C5_TTTFT() {
		
		iterator.remove();
	}
	
	@Test(expected=ConcurrentModificationException.class)
	public void testRemove_C1_C2_C3_C4_C5_TTTTF() {
		iterator.next();
		list.add("Zeus");
		iterator.remove();
	}
			

}

