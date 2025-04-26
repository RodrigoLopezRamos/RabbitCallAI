package com.analia.common.util;

import java.util.*;

/**
 * Implementation of PaginatedList backed by an ArrayList
 *
 * @param <T>
 */
public class PaginatedArrayList<T> implements PaginatedList<T> {
    private final ArrayList<T> EMPTY_LIST = new ArrayList<>(0);

    private final List<T> list;

    private List<T> page;

    private final int pageSize;

    private int index;

    /**
     * @param pageSize
     */
    public PaginatedArrayList(int pageSize) {
        this.pageSize = pageSize;
        this.index = 0;
        this.list = new ArrayList<>();
        repaginate();
    }

    /**
     * Constructor to set the initial size and the page size
     *
     * @param initialCapacity - the initial size
     * @param pageSize        - the page size
     */
    public PaginatedArrayList(int initialCapacity, int pageSize) {
        this.pageSize = pageSize;
        this.index = 0;
        this.list = new ArrayList<>(initialCapacity);
        repaginate();
    }

    /**
     * Constructor to create an instance using an existing collection
     *
     * @param c        - the collection to build the instance with
     * @param pageSize - the page size
     */
    public PaginatedArrayList(Collection<? extends T> c, int pageSize) {
        this.pageSize = pageSize;
        this.index = 0;
        this.list = new ArrayList<>(c);
        repaginate();
    }

    /**
     * @param c
     * @param pageSize
     * @param page
     */
    public PaginatedArrayList(Collection<? extends T> c, int pageSize, int page) {
        this.pageSize = pageSize;
        this.index = page;
        this.list = new ArrayList<>(c);
        repaginate();
    }

    /**
     *
     */
    private void repaginate() {
        if (list.isEmpty()) {
            page = EMPTY_LIST;
        } else {
            int start = index * pageSize;
            int end = start + pageSize - 1;
            if (end >= list.size()) {
                end = list.size() - 1;
            }
            if (start >= list.size()) {
                page = EMPTY_LIST;
            } else if (start < 0) {
                index = list.size() / pageSize;
                if (list.size() % pageSize == 0) {
                    index--;
                }
                repaginate();
            } else {
                page = list.subList(start, end + 1);
            }
        }
    }


    public int size() {
        return page.size();
    }


    public boolean isEmpty() {
        return page.isEmpty();
    }


    public boolean contains(Object o) {
        return page.contains(o);
    }


    public Iterator<T> iterator() {
        return page.iterator();
    }


    public Object[] toArray() {
        return page.toArray();
    }

    @SuppressWarnings("hiding")

    public <T> T[] toArray(T[] a) {
        return page.toArray(a);
    }


    public boolean add(T e) {
        boolean b = list.add(e);
        repaginate();
        return b;
    }


    public boolean remove(Object o) {
        boolean b = list.remove(o);
        repaginate();
        return b;
    }


    public boolean containsAll(Collection<?> c) {
        return page.containsAll(c);
    }


    public boolean addAll(Collection<? extends T> c) {
        boolean b = list.addAll(c);
        repaginate();
        return b;
    }


    public boolean addAll(int index, Collection<? extends T> c) {
        boolean b = list.addAll(index, c);
        repaginate();
        return b;
    }


    public boolean removeAll(Collection<?> c) {
        boolean b = list.removeAll(c);
        repaginate();
        return b;
    }


    public boolean retainAll(Collection<?> c) {
        boolean b = list.retainAll(c);
        repaginate();
        return b;
    }


    public void clear() {
        list.clear();
        repaginate();
    }


    public T get(int index) {
        return list.get(index);
    }


    public T set(int index, T element) {
        T o = list.set(index, element);
        repaginate();
        return o;
    }


    public void add(int index, T element) {
        list.add(index, element);
        repaginate();

    }


    public T remove(int index) {
        T o = list.remove(index);
        repaginate();
        return o;
    }


    public int indexOf(Object o) {
        return page.indexOf(o);
    }


    public int lastIndexOf(Object o) {
        return page.lastIndexOf(o);
    }


    public ListIterator<T> listIterator() {
        return page.listIterator();
    }


    public ListIterator<T> listIterator(int index) {
        return page.listIterator();
    }


    public List<T> subList(int fromIndex, int toIndex) {
        return page.subList(fromIndex, toIndex);
    }


    public int getPageSize() {
        return pageSize;
    }


    public boolean isFirstPage() {
        return index == 0;
    }


    public boolean isMiddlePage() {
        return !(isFirstPage() || isLastPage());
    }


    public boolean isLastPage() {
        return list.size() - ((index + 1) * pageSize) < 1;
    }

    public boolean isNextPageAvailable() {
        return !isLastPage();
    }

    public boolean isPreviousPageAvailable() {
        return !isFirstPage();
    }

    public boolean nextPage() {
        if (isNextPageAvailable()) {
            index++;
            repaginate();
            return true;
        }
        return false;
    }

    public boolean previousPage() {
        if (isPreviousPageAvailable()) {
            index--;
            repaginate();
            return true;
        }
        return false;
    }

    public void gotoPage(int pageNumber) {
        index = pageNumber;
        repaginate();
    }

    public int getPageIndex() {
        return index;
    }

    /**
     * @return the page
     */
    public List<T> getCurrentPage() {
        return page;
    }

}
