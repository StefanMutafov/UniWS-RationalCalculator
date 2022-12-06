#include<iostream>
#include<cstdlib>
#include<time.h>
using namespace std;
int main(){
   system("chcp 1251 >nul");


//Making variables
int a[100];
int b[100];
int x = 0, y = 0;
int counter = 0;
int answer = 0;
int mistakes= 0;
int variable1 = 0;


for(int k = 0;k<=100;k++){
    a[k]=0;
    b[k]=0;
}

//Choosing random numbers KAPPA

    for(int i=0;i<100;i++){
            variable1 = i;
            srand(time(NULL));
            x = rand() % 10 + 1;
            y = rand() % 10 + 1;

           b[i] = x;




                    //Check if the problem has already been solved
                    for(int k=0;k<=100;k++){
                        if(a[k] == x*y){
                            for(int m=0; m<=10; m++ ){
                                if(b[m] == x){

                                    i--;

                                }
                            }
                        }
                    }
                  if(i == variable1){
                //May have a bug, when the answer doesn't change e.g. 5x6 6x5
                cout<<"Колко е "<<x<<" по "<<y<<"?"<<endl;
                while(answer != x*y){
                cin>>answer;
                if(answer == x * y){
                    cout<<"Браво!"<<endl;
                    a[i]=x * y;

                }else{
                mistakes++;
                cout<<"Опитай пак!"<<endl;
                }
                }
                  }
    }

    cout<<"Ти завърши изпита с "<<mistakes<<" грешки.";



    return 0;
}
