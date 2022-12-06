#include<iostream>
using namespace std;

void SP(int p, int &q){
    p=3*q;
    q=3*p;

}
int main(){
    int a=1, b=2;
    SP(a,b);
    cout<<a<<" "<<b;


    return 0;


}
