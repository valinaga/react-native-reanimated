package com.swmansion.reanimated.nodes;

import java.util.Map;
import java.util.HashMap;
import java.util.Iterator;
import android.util.Log;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMapKeySetIterator;
import com.facebook.react.bridge.ReadableType;
import com.swmansion.reanimated.NodesManager;

public class GetKeyNode extends Node {

  private final String mKey;
  private final int mValueID;

  public GetKeyNode(int nodeID, ReadableMap config, NodesManager nodesManager) {
    super(nodeID, config, nodesManager);
    mKey = config.getString("key");
    mValueID = config.getInt("value");
  }

  private static Map<String, Object> toMap(ReadableMap readableMap) {
    Map<String, Object> map = new HashMap<>();
    ReadableMapKeySetIterator iterator = readableMap.keySetIterator();
    while (iterator.hasNextKey()) {
      String key = iterator.nextKey();
      ReadableType type = readableMap.getType(key);
      switch (type) {
        case Null:
          map.put(key, null);
          break;
        case Boolean:
          map.put(key, readableMap.getBoolean(key));
          break;
        case Number:
          map.put(key, readableMap.getDouble(key));
          break;
        case String:
          map.put(key, readableMap.getString(key));
          break;
        case Map:
          map.put(key, GetKeyNode.toMap(readableMap.getMap(key)));
          break;
        case Array:
          map.put(key, GetKeyNode.toArray(readableMap.getArray(key)));
          break;
      }
    }
    return map;
  }

  private static Object[] toArray(ReadableArray readableArray) {
    Object[] array = new Object[readableArray.size()];
    for (int i = 0; i < readableArray.size(); i++) {
      ReadableType type = readableArray.getType(i);
      switch (type) {
        case Null:
          array[i] = null;
          break;
        case Boolean:
          array[i] = readableArray.getBoolean(i);
          break;
        case Number:
          array[i] = readableArray.getDouble(i);
          break;
        case String:
          array[i] = readableArray.getString(i);
          break;
        case Map:
          array[i] = GetKeyNode.toMap(readableArray.getMap(i));
          break;
        case Array:
          array[i] = GetKeyNode.toArray(readableArray.getArray(i));
          break;
      }
    }
    return array;
  }

  @Override
  protected Object evaluate() {
    Object value = mNodesManager.findNodeById(mValueID, Node.class).value();
    if (!(value instanceof ReadableMap)) {
      Log.d("REANIMATED", String.format("GetKeyNode warning, value is not a map: %s (id %d)", value, mValueID));
      return null;
    }
    Map<String, Object> map = GetKeyNode.toMap((ReadableMap)value);
    if (!map.containsKey(mKey)) {
      Log.d("REANIMATED", String.format("GetKeyNode warning, no key in the map: %s", mKey));
      return null;
    }
    return map.get(mKey);
  }
}
