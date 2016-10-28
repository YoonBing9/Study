package Coupang;
import java.util.LinkedList;
/*
세개의 문자열 배열 (arr1, arr2, arr3)로 하나의 큐를 구현하라.
단, enqueue와 dequeue는 균등하게 순차적으로 해아한다.
*/
public class Coupang1 {
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String[] inputs = {"a","b","c","d","e","f","g","h"};
		String[] outputs = solution(inputs);
		for(int i=0; i<outputs.length; i++){
			System.out.print(outputs[i]+" ");
		}
	}
	
	public static String[] solution(String[] inputs) {
		String[] arr1 = new String[100];
		String[] arr2 = new String[100];
		String[] arr3 = new String[100];
		String[] result = new String[300];
		
		int i1=0,i2=0,i3=0;
		
		for(int i=0; i<inputs.length; i++){
			if(i%3==0){
				arr1[i1]=inputs[i];
				result[i]="[arr1,"+arr1[i1]+"]";
				i1++;
			}else if(i%3==1){
				arr2[i2]=inputs[i];
				result[i]="[arr2,"+arr2[i2]+"]";
				i2++;
			}else if(i%3==2){
				arr3[i3]=inputs[i];
				result[i]="[arr3,"+arr3[i3]+"]";
				i3++;
			}
		}
        return result;
    }
}
