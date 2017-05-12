//
//  main.c
//  test
//
//  Created by 전지훈 on 2017. 5. 11..
//  Copyright © 2017년 jrabbit. All rights reserved.
//

#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#define MAX_SIZE 100

typedef void* Data;

typedef struct node{
    Data data;
    struct node* next;
}Node;

typedef struct queue{
    int count;
    Node* front;
    Node* rear;
}Queue;

Queue* createQ(void);
Data dequeue(Queue* Q);
void enqueue(Queue* Q, Data data);


Queue* createQ(void) {
    Queue* q = (Queue*) malloc(sizeof(Queue));
    
    q->count = 0;
    q->front = NULL;
    q->rear = NULL;
    
    return q;
}

void enqueue(Queue* Q, Data data) {
    if(Q->count < MAX_SIZE) {
        Node* newNode = (Node*)malloc(sizeof(Node));
        newNode->data = data;
        newNode->next = NULL;
        
        if(Q->count == 0) {
            Q->front = newNode;
            Q->rear = newNode;
        } else {
            Q->rear->next = newNode;
            Q->rear = newNode;
        }
        
        Q->count++;
    } else {
        printf("max queue\n");
    }
}

Data dequeue(Queue* Q) {
    if(Q->count == 0) {
        printf("no queue\n");
        return -1;
    } else {
        Node* tmp = Q->front;
        Q->front = tmp->next;
        Data tmpData = tmp->data;
        free(tmp);
        Q->count--;
        return tmpData;
    }
}


int main() {
    Queue* myqueue=createQ();
    
    enqueue(myqueue,"a");
    enqueue(myqueue,4);
    enqueue(myqueue,5);
    enqueue(myqueue,6);
    enqueue(myqueue,7);
    enqueue(myqueue,8);
    enqueue(myqueue,9);
    
    printf("remove_front: %s\n",dequeue(myqueue));
    printf("remove_front: %d\n",dequeue(myqueue));
    
  //  destroy_queue(myqueue);
    
    return 0;
}
