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
#include <unistd.h>
#include <fcntl.h>

#define MAX_SIZE 10
pthread_mutex_t mutex = PTHREAD_MUTEX_INITIALIZER;
pthread_cond_t buffer_has_space = PTHREAD_COND_INITIALIZER;
pthread_cond_t buffer_has_data = PTHREAD_COND_INITIALIZER;


typedef void* Data;

typedef struct node{ // 데이터와 다음 노드의 주소를 가질 노드 생성
    Data data; //데이터는 어떠한 자료도 받기 위해서 void *로 선언
    struct node* next;
}Node;

typedef struct queue{//큐
    char count;
    Node* front;
    Node* rear;
}Queue;

//함수 프로토 타입 선언
Queue* createQ(void);//큐 생성자
Data dequeue(Queue* Q);//큐 삭제
void enqueue(Queue* Q, Data data);//큐 등록
void consumer(void);
void producer(void);
Queue* Q;//쓰레드에서 사용할 큐 전역으로 선언

Queue* createQ(void) {
    Queue* q = (Queue*) malloc(sizeof(Queue));
    
    q->count = 0;
    q->front = NULL;
    q->rear = NULL;
    
    return q;
}

void enqueue(Queue* Q, Data data) {
    if(Q->count < MAX_SIZE) {//큐의 갯수는 100개로 제한
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

void destroyQ(Queue* Q) { //큐 메모리 해제
    while(Q->count != 0) {
        dequeue(Q);
    }
    free(Q);
    printf("Destroy Queue Success\n");
}

void producer (void)
{
    int i;
    char c;
    char buf[1];
    int n;
    int input;
    
    
//    for (i =0; i < MAX_SIZE; i++) {
//        pthread_mutex_lock(&mutex); // 뮤텍스를 이용해서 이안에서 사용되는 변수 접근 lock
//        if (Q->count == MAX_SIZE) // q의 사이즈가 100개 면 더이상 쓰레드가 동작하지 않음
//            pthread_cond_wait (&buffer_has_space, &mutex);
//
//        enqueue(Q,i);
//        pthread_cond_signal(&buffer_has_data);//condition wait상태인 쓰레드에게 signal을 보냄 아무도 condition wait상태가 아니면 그냥 사라짐
//        pthread_mutex_unlock(&mutex);// mutex lock해제
//    }
//
    input = open("input",0);
     while((n = read(input, buf, 1)) != 0) {
         ptread_mutex_lock(&mutex);
         if(Q->count == MAX_SIZE) 
             ptrhead_cond_wait(&buffer_has_space, &mutex);
         
         enqueue(Q,buf);
         pthread_cond_signal(&buffer_has_data);
         pthread_mutex_unlock(&mutex);
     }
    
    exit(0);
}

void consumer (void)
{
    int i;
    Data data;
    for (i =0; i < MAX_SIZE; i++) {
        pthread_mutex_lock(&mutex);
        if (Q->count == 0) // buffer empty !
            pthread_cond_wait(&buffer_has_data, &mutex);
        
        data = dequeue(Q);
        pthread_cond_signal(&buffer_has_space);
        pthread_mutex_unlock(&mutex);
        printf(" %c\t",data);
    }
}

int main(void)
{
    int i;
    Q=createQ();
    
    pthread_t threads[2]; //쓰레드 변수 선언
    pthread_create(&threads[0], NULL, producer, NULL);//쓰레드생성
    pthread_create(&threads[1], NULL, consumer, NULL);
    for (i=0; i< 2; i++)
        pthread_join(threads[i], NULL);
    
    return 0;
}
