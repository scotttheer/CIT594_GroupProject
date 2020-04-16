package edu.upenn.cit594.data;

public class Property {
	private double marketValue;
	private double totalLivableArea;
	private int ZIPCode;

	public Property(
			double marketValue,
			double totalLivableArea,
			int ZIPCode) {
		this.marketValue = marketValue;
		this.totalLivableArea = totalLivableArea;
		this.ZIPCode = ZIPCode;

	}

	public double getMarketValue() {
		return marketValue;
	}

	public double getTotalLivableArea() {
		return totalLivableArea;
	}

	public int getZIPCode() {
		return ZIPCode;
	}
}
