package com.cpp.mscs.cricscore.services;

import com.cpp.mscs.cricscore.models.City;

import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: jayavardhanpatil
 * Date: 4/24/21
 * Time:  16:33
 */

public class Trie {

    protected Map<Character, Trie> children;
    protected City content;
    protected boolean terminal = false;

    public Trie() {
        this(null);
    }

    private Trie(City content) {
        this.content = content;
        children = new HashMap<Character, Trie>();
    }

    //method to append character
    protected void add(char character, City city) {
        this.content = city;
        children.put(character, new Trie(this.content));
    }


    //method for inserting a new diagnosis
    public void insert(City diagnosis) {
        if (diagnosis == null) {
            throw new IllegalArgumentException("Null diagnoses entries are not valid.");
        }
        Trie node = this;
        for (char c : diagnosis.getCityName().toCharArray()) {
            if (!node.children.containsKey(c)) {
                node.add(c, diagnosis);
            }
            node = node.children.get(c);
        }
        node.content = diagnosis;
        node.terminal = true;
    }

    //method to search for a diagnosis entry
    public City find(String diagnosis) {
        Trie node = this;
        for (char c : diagnosis.toCharArray()) {
            if (!node.children.containsKey(c)) {
                return null;
            }
            node = node.children.get(c);
        }
        return node.content;
    }

    //check for fragment of an entry prefix
    //todo: fragment within the diagnosis string isn't detected.
    //since tries are prefix data structures, I would need to come up with a different data structure
    ///solution here.
    public Collection<City> autoComplete(String prefix) {
        Trie Trienode = this;
        for (char c : prefix.toCharArray()) {
            if (!Trienode.children.containsKey(c)) {
                return Collections.emptyList();
            }
            Trienode = Trienode.children.get(c);
        }
        return Trienode.allPrefixes();
    }

    protected Collection<City> allPrefixes() {
        List<City> diagnosisresults = new ArrayList<City>();
        if (this.terminal) {
            diagnosisresults.add(this.content);
        }
        for (Map.Entry<Character, Trie> entry : children.entrySet()) {
            Trie child = entry.getValue();
            Collection<City> childPrefixes = child.allPrefixes();
            diagnosisresults.addAll(childPrefixes);
        }
        return diagnosisresults;
    }

}