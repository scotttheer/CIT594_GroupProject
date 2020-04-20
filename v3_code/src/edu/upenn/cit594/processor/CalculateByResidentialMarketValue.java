package edu.upenn.cit594.processor;

import edu.upenn.cit594.data.Property;

public class CalculateByResidentialMarketValue implements CalculateMethod{

	@Override
	public double calculate(Property p) {
		return p.getMarketValue();
	}

}
