package com.coolpeng.framework.utils.data;

import java.util.*;

/**
 * Created by luanhaipeng on 16/10/10.
 */
public  class MaxSizedMap<K,V>  extends HashMap<K,V> implements Map<K,V>
{
    private LinkedList<K> keyList = new LinkedList<>();
    private int maxCapacity = 0;

    public MaxSizedMap(int maxCapacity) {
        this.maxCapacity = maxCapacity;
    }


    @Override
    public V put(K key, V value) {
        removeOldObject(1);
        this.keyList.add(key);
        return super.put(key,value);
    }


    @Override
    public void putAll(Map<? extends K, ? extends V> m) {
        removeOldObject(m.size());
        this.keyList.addAll(m.keySet());
        super.putAll(m);
    }


    private void removeOldObject(int inCount) {
        //当前0个，进来1个。最大1个。移除 0；
        //当前1个，进来1个。最大1个。移除 1；
        //当前1个，进来1个。最大2个。移除 0；
        //当前1个，进来1个。最大3个。移除 -1；
        int removeCount = (this.size() + inCount) - this.maxCapacity ;
        if (removeCount > 0 ){
            for (int i = 0 ;i<removeCount;i++){
                K key = keyList.pollFirst();
                if (key!=null){
                    this.remove(key);
                }
            }
        }
    }



}