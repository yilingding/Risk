package test;

import static org.junit.Assert.*;

import org.junit.Test;

import model.DiceCollection;

import model.Die;

public class DiceCollectionTest {

	@Test
	public void testDiceCollection() {
		DiceCollection d = new DiceCollection(2);

		d.setAmount(3);
		d.roll();
		int great = d.getGreatest();
		int secondGreat = d.getSecondGreatest();
		assertTrue(great >= 1 && great <= 6);
		
		assertEquals(3, d.size());
		assertTrue(great >= secondGreat);
		
		d.setAmount(2);
		d.roll();
		great = d.getGreatest();
		secondGreat = d.getSecondGreatest();
		assertTrue(great >= 1 && great <= 6);
		assertEquals(2, d.size());
		assertTrue(great >= secondGreat);
	}

	
	@Test
	public void testDiceCompare() {
		DiceCollection d = new DiceCollection(2);
		
		Die d1=d.getDieInCollection(0);
		Die d2=d.getDieInCollection(1);
		
		d1.roll();
		d2.roll();
		int d1val=d1.getValue();
		int d2val=d2.getValue();
		assertTrue((d1val-d2val)==d1.compareTo(d2));
		
	}
	
	@Test
	public void testSetAmount() {
		DiceCollection d = new DiceCollection(2);
		
		d.setAmount(3);
		assertTrue(d.size()==3);
		
		d.setAmount(1);
		assertTrue(d.size()==1);
		
	}


}
