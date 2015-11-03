package com.jointt.generator.utils;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

public class ListHashtable extends Hashtable<Object, Object> {
	private static final long serialVersionUID = 7872319105329144906L;
	protected List<Object> orderedKeys = new ArrayList<Object>();

	public synchronized void clear() {
		super.clear();
		orderedKeys = new ArrayList<Object>();
	}

	public synchronized Object put(Object aKey, Object aValue) {
		if (orderedKeys.contains(aKey)) {
			int pos = orderedKeys.indexOf(aKey);
			orderedKeys.remove(pos);
			orderedKeys.add(pos, aKey);
		} else {
			if (aKey instanceof Integer) {
				Integer key = (Integer) aKey;
				int pos = getFirstKeyGreater(key.intValue());
				if (pos >= 0)
					orderedKeys.add(pos, aKey);
				else
					orderedKeys.add(aKey);
			} else
				orderedKeys.add(aKey);
		}
		return super.put(aKey, aValue);
	}

	private int getFirstKeyGreater(int aKey) {
		int pos = 0;
		int numKeys = getOrderedKeys().size();
		for (int i = 0; i < numKeys; i++) {
			Integer key = (Integer) getOrderedKey(i);
			int keyval = key.intValue();
			if (keyval < aKey)
				++pos;
			else
				break;
		}
		if (pos >= numKeys)
			pos = -1;
		return pos;
	}

	public synchronized Object remove(Object aKey) {
		if (orderedKeys.contains(aKey)) {
			int pos = orderedKeys.indexOf(aKey);
			orderedKeys.remove(pos);
		}
		return super.remove(aKey);
	}

	public void reorderIntegerKeys() {
		List<Object> keys = getOrderedKeys();
		int numKeys = keys.size();
		if (numKeys <= 0)
			return;

		if (!(getOrderedKey(0) instanceof Integer))
			return;

		List<Object> newKeys = new ArrayList<Object>();
		List<Object> newValues = new ArrayList<Object>();

		for (int i = 0; i < numKeys; i++) {
			Integer key = (Integer) getOrderedKey(i);
			Object val = getOrderedValue(i);
			int numNew = newKeys.size();
			int pos = 0;
			for (int j = 0; j < numNew; j++) {
				Integer newKey = (Integer) newKeys.get(j);
				if (newKey.intValue() < key.intValue())
					++pos;
				else
					break;
			}
			if (pos >= numKeys) {
				newKeys.add(key);
				newValues.add(val);
			} else {
				newKeys.add(pos, key);
				newValues.add(pos, val);
			}
		}
		this.clear();
		for (int l = 0; l < numKeys; l++) {
			put(newKeys.get(l), newValues.get(l));
		}
	}

	public String toString() {
		StringBuffer x = new StringBuffer();
		x.append("Ordered Keys: ");
		int numKeys = orderedKeys.size();
		x.append("[");
		for (int i = 0; i < numKeys; i++) {
			x.append(orderedKeys.get(i) + " ");
		}
		x.append("]\n");

		x.append("Ordered Values: ");
		x.append("[");

		for (int j = 0; j < numKeys; j++) {
			x.append(getOrderedValue(j) + " ");
		}
		x.append("]\n");
		return x.toString();
	}

	public void merge(ListHashtable newTable) {
		int num = newTable.size();
		for (int i = 0; i < num; i++) {
			Object aKey = newTable.getOrderedKey(i);
			Object aVal = newTable.getOrderedValue(i);
			this.put(aKey, aVal);
		}
	}

	public List<Object> getOrderedKeys() {
		return orderedKeys;
	}

	public Object getOrderedKey(int i) {
		return getOrderedKeys().get(i);
	}

	public Object getKeyForValue(Object aValue) {
		int num = getOrderedValues().size();
		for (int i = 0; i < num; i++) {
			Object tmpVal = getOrderedValue(i);
			if (tmpVal.equals(aValue)) {
				return getOrderedKey(i);
			}
		}
		return null;
	}

	public List<Object> getOrderedValues() {
		List<Object> values = new ArrayList<Object>();
		int numKeys = orderedKeys.size();
		for (int i = 0; i < numKeys; i++) {
			values.add(get(getOrderedKey(i)));
		}
		return values;
	}

	public Object getOrderedValue(int i) {
		return get(getOrderedKey(i));
	}
}
