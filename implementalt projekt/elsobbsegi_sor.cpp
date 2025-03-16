#include "elsobbsegi_sor.h"

PriorityQueue::PriorityQueue() {
        size = -1;
}

PriorityQueue::~PriorityQueue(){}

int PriorityQueue::getSize() {
    return size;
}

int PriorityQueue::parent(int i) {
    return (i - 1) / 2;
}

int PriorityQueue::leftChild(int i) {
    return (2 * i) + 1;
}

int PriorityQueue::rightChild(int i) {
    return (2 * i) + 2;
}

void PriorityQueue::shiftUp(int i) {
    while (i > 0 && H[parent(i)].first < H[i].first) {
        swap(H[parent(i)], H[i]);
        i = parent(i);
    }
}

void PriorityQueue::shiftDown(int i) {
    int maxIndex = i;
    int l = leftChild(i);

    if (l <= size && H[l].first > H[maxIndex].first) {
        maxIndex = l;
    }

    int r = rightChild(i);

    if (r <= size && H[r].first > H[maxIndex].first) {
        maxIndex = r;
    }

    if (i != maxIndex) {
        swap(H[i], H[maxIndex]);
        shiftDown(maxIndex);
    }
}

void PriorityQueue::insert(int p, const string& name) {
    if (size < 49) {
        size++;
        H[size] = make_pair(p, name);
        shiftUp(size);
    }
}

pair<int, string> PriorityQueue::extractMax() {
    if (size != - 1) {
        pair<int, string> result = H[0];
        H[0] = H[size];
        size--;
        shiftDown(0);
        return result;
    }
    else {
        pair<int, string> result(0,"");
        return result;
    }
}

void PriorityQueue::changePriority(int i, int p) {
    if (i <= size && i >= 0) {
        int oldp = H[i].first;
        H[i].first = p;
        if (p > oldp) {
            shiftUp(i);
        }
        else {
            shiftDown(i);
        }
    }
}

pair<int, string> PriorityQueue::getMax() {
    if (size != -1) {
        return H[0];
    }
    else {
        pair<int, string> result(0, "");
        return result;
    }
}

void PriorityQueue::remove(int i) {
    if (i <= size && i >= 0) {
        H[i].first = getMax().first+1;
        shiftUp(i);
        extractMax();
    }
}

void PriorityQueue::printQueue() {
    if (size == -1) {
        return;
    }
    for (int i = 0; i <= size; ++i) {
        cout << i+1 << ". " << H[i].second << " (" << H[i].first << ") " << endl;
    }
    cout << endl;
}