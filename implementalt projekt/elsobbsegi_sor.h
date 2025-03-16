#pragma once

#include <iostream>
#include <string>
#include <stdlib.h>     
#include <time.h>

using namespace std;

class PriorityQueue {
private:
    pair<int, string> H[50];
    int size;

public:
    PriorityQueue();

    ~PriorityQueue();
    
    int getSize();

    int parent(int);

    int leftChild(int);

    int rightChild(int);

    void shiftUp(int);

    void shiftDown(int);

    void insert(int, const string&);

    pair<int, string> extractMax();

    void changePriority(int, int);

    pair<int, string> getMax();

    void remove(int);

    void printQueue();
};
