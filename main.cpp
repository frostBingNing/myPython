#include <stdio.h>
#include <iostream>
#include <Winsock2.h>
#include <string.h>
#include <thread>
using namespace std;

typedef struct Sockets {
    SOCKET the_socket;   // socket
}message;

//ʹ���߳�������ÿһ������
DWORD WINAPI receive(const LPVOID arg) {

    message *temp = (message *) arg;
    char recvBuf[MAXBYTE];
    // ��ÿһ���߳����������ܲ���
    while(1)
    {
//        memset(recvBuf, MAXBYTE, sizeof(char));
        int flag = -1;
        flag = recv(temp->the_socket,recvBuf,MAXBYTE,0);
        if (flag > 0)
        {
            cout<<recvBuf<<endl;
        }
        memset(recvBuf, MAXBYTE, sizeof(char));
    }

    return 0;
}



int main()
{
    WORD wVersionRequested;
    WSADATA wsaData;
    int err;

    wVersionRequested = MAKEWORD( 1, 1 );

    err = WSAStartup( wVersionRequested, &wsaData );
    if ( err != 0 ) {
        return 0;
    }
    if ( LOBYTE( wsaData.wVersion ) != 1 || HIBYTE( wsaData.wVersion ) != 1 ) {
        WSACleanup( );
        return 0;
    }
    SOCKET sockClient=socket(AF_INET,SOCK_STREAM,0);

    SOCKADDR_IN addrSrv;
    addrSrv.sin_addr.S_un.S_addr=inet_addr("127.0.0.1");
    addrSrv.sin_family=AF_INET;
    addrSrv.sin_port=htons(6000);

    connect(sockClient,(SOCKADDR*)&addrSrv,sizeof(SOCKADDR));
    // ���涨���Ӧ����-- �����ͻ��˵�socket
    // �����ǵ����� ��ӭ����Ϣ ---- ��ʾ��ǰ�û��Ѿ�����������
    char recvBuf[50];
    recv(sockClient,recvBuf,50,0);
    printf("%s\n",recvBuf);

    // ����socket�ṹ��
    message *temp = new message;
    temp->the_socket = sockClient;
    // ��Ϊ��������̸߳��������Ϣ
    CreateThread(NULL,0,&receive,temp,0,NULL);

    //���������û�ѡ����е��Ļ���Ⱥ��
    while(1)
    {
        // ֻҪ��ǰ�û�û���˳����ӣ���ô�Ϳ�������ѡ������ģʽ
        // Ĭ���Ƕ���Ŷ
        cout<<"ʹ��˵����������ʹ��'S'���õ��ģ�'Q'�˳������������ַ����������"<<endl;
        char choice[10] = "";
        cin.getline(choice,10);

        if(strcmp(choice,"Q") == 0)
        {
            break;
        } else if(strcmp(choice,"S") == 0) // ������ѡ��'S'���뵥��ģʽ
        {
            send(sockClient,choice,strlen(choice)+1,0); // ����ǰ��������Ķ˿ں�
            cout<<"'port message'��ʽ������Ϣ"<<endl;     //  ��ʾ�û��������ʽ
            // ��Ҫ�������������һ�����ж˿ںŵ���Ϣ�����û�ѡ��
            while(1)
            {
                // ������Ϣѭ����
                char message[MAXBYTE] = "";
                cin.getline(message,MAXBYTE);
                //��Ϊû��UI���棬�����������quit ����ǰ�û��˳�
                if(strcmp(message,"quit") == 0 || strcmp(message,"QUIT") == 0)
                {
                    break;
                }
                send(sockClient,message,strlen(message)+1,0);
            }
        }
        else {
            cout<<"���Խ��ж�����"<<endl;
            while(1)
            {
                char message[MAXBYTE] = "";
                cin.getline(message,MAXBYTE);
                //��Ϊû��UI���棬�����������quit ����ǰ�û��˳�
                if(strcmp(message,"quit") == 0 || strcmp(message,"QUIT") == 0)
                {
//                    cout<<strcmp(message,"quit")<<strcmp(message,"QUIT")<<endl;
                    break;
                }
                send(sockClient,message,strlen(message)+1,0);
            }
        }

    }

    closesocket(sockClient);
    WSACleanup();

}
