package daoInterface;

public interface ConfigDaoInterface {
	public int getValueByName(String name);
	public void updateByName(String name, int value);
	int getUpperBoundLimit();
	void setUpperBoundLimit(int value, int enable);
}
