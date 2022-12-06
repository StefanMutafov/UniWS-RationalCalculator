#include<iostream>
#include <bits/stdc++.h>
using namespace std;
int main(){
int arr[50];
int a=0;
int i;
int razmah;
int sbor=0;
int srar;
for(i=0;i<50;i++){}
    arr[i]=0;


cin>>a;
for(i=0;i<a;i++){
    cin>>arr[i];
    sbor=sbor+arr[i];

}
sort(arr, arr + a);

    cout << "\nArray after sorting using "
            "default sort is : \n";
    for ( i = 0; i < a; ++i){
        cout << arr[i] << " ";
    }
razmah=arr[a]-arr[0];
srar= sbor/a;
        cout <<"razmaha e :"<<razmah<<endl;
        cout <<"ar aritmet   "<<srar;

return 0;
}
