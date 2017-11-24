//
//  main.c
//  msq
//
//  Created by 전지훈 on 2017. 11. 22..
//  Copyright © 2017년 jrabbit. All rights reserved.
//

#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <fcntl.h>
#include <sys/wait.h>
#include <sys/types.h>
#include <sys/ipc.h>
#include <sys/msg.h>
#include <string.h>
#define  BUFF_SIZE   10

typedef struct {
    long  data_type;
    int   data_num;
    char  data_buff[BUFF_SIZE];
} t_data;


int main(int argc, const char * argv[]) {
    int input, output;
    int n;
    int status;
    int      msqid;
    t_data   data;
    
    if ( -1 == ( msqid = msgget( (key_t)1234, IPC_CREAT | 0666)))
    {
        perror( "msgget() 실패");
        exit( 1);
    }
    
    if(fork() == 0) {
        input = open("input",0);
        memset(data.data_buff , '\0', 10);
        while((n = read(input, data.data_buff, 10)) != 0) {
            data.data_type = 1;
            if ( -1 == msgsnd( msqid, &data, sizeof( t_data) - sizeof( long), 0))
            {
                perror( "msgsnd() 실패");
                exit(1);
            }
        }
        close(input);
        exit(0);
    } else {
        output = creat("output", 0666);
        while(1){
            if ( -1 != msgrcv( msqid, &data, sizeof( t_data) - sizeof( long), 1, 0))
            {
                write(output,data.data_buff,10);
              
            } else {
                perror( "msgrcv() 실패");
                exit( 1);
            }
        }
        wait(&status);
    }
    return 0;
}
