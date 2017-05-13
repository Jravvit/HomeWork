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
#include <pthread.h>

#define MAX_SIZE 100
pthread_mutex_t mutex = PTHREAD_MUTEX_INITIALIZER;
pthread_cond_t buffer_has_space = PTHREAD_COND_INITIALIZER;
pthread_cond_t buffer_has_data = PTHREAD_COND_INITIALIZER;


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
void destroyQ(Queue* Q);
Data eraseQ(Queue* Q,int i);
Queue* myqueue;

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

void destroyQ(Queue* Q) {
    while(Q->count != 0) {
        dequeue(Q);
    }
    free(Q);
    printf("Destroy Queue Success\n");
}

void producer (Queue* Q)
{
    int i;
    for (i =0; i < 100; i++) {
        pthread_mutex_lock(&mutex);
        if (Q->count == 100) // buffer full !
            pthread_cond_wait (&buffer_has_space, &mutex);
        
        enqueue(Q,i);
        pthread_cond_signal(&buffer_has_data);
        pthread_mutex_unlock(&mutex);
    }
}

void consumer (Queue* Q)
{
    int i;
    Data data;
    for (i =0; i < 100; i++) {
        pthread_mutex_lock(&mutex);
        if (Q->count == 0) // buffer empty !
            pthread_cond_wait(&buffer_has_data, &mutex);
        
        data = dequeue(Q);
        pthread_cond_signal(&buffer_has_space);
        pthread_mutex_unlock(&mutex);
        printf("data = %d\n",data);
    }
}

int main(void)
{
    int i;
    myqueue=createQ();
    
    pthread_t threads[2];
    pthread_create(&threads[0], NULL, producer, NULL);
    pthread_create(&threads[1], NULL, consumer, NULL);
    for (i=0; i< 2; i++)
        pthread_join(threads[i], NULL);
    
    return 0;
}
