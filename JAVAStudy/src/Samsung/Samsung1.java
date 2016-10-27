package Samsung;

import java.util.Collections;
import java.util.LinkedList;
import java.util.Scanner;

//지네게임
/*
 * 2016 삼성sds에서 나온 문제.
지네가 거미를 먹으면 생명력이 3 올라가고 생명력이 0이상이면 꼬리가 1씩 늘어난다.
생명력이 없으면 꼬리가 1씩 줄어든다.
명령을 입력해 지네가 벽에 부딪치거나 자기 꼬리에 부딪치거나 모든 명령을 수행했을때의 시간을 출력
입력 예)..
*/
/*
10
7 7
9 9 9 9 9 9 9
9 0 0 0 0 0 9
9 0 0 0 0 0 9
9 0 0 1 0 3 9
9 3 0 0 0 0 9
9 0 0 3 0 0 9
9 9 9 9 9 9 9
0 1
0 1
2 2
2 2
0 2
2 1
0 2
*/
public class Samsung1 {
	static LinkedList<LinkedList<String>> orders = new LinkedList<LinkedList<String>>();
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner sc = new Scanner(System.in);
		Integer T = sc.nextInt();
		sc.nextLine();
		for(int t=0; t<T; t++){
			String temp = sc.nextLine();
			String[] tempArr = temp.split(" ");
			Map map = new Map();
			map.inputMap(Integer.parseInt(tempArr[0]), sc);
			inputOrder(Integer.parseInt(tempArr[1]), sc);
			
			Jine jine = new Jine(map, orders);
			System.out.println("#"+(t+1)+" "+jine.move(map));
			map.showMap();			
			orders.clear();
		}	
		sc.close();
	}
	
	public static void inputOrder(Integer N, Scanner sc){
		for(int i=0; i<N; i++){
			String temp = sc.nextLine();
			String[] tempArr = temp.split(" ");
			LinkedList<String> tempLinked = new LinkedList<String>();
			Collections.addAll(tempLinked, tempArr);
			orders.add(tempLinked);			
		}
	}
}

class Map{
	private LinkedList<LinkedList<String>> map;
	
	public Map(){
		map = new LinkedList<LinkedList<String>>();
	}
	
	public void init(){
		map.clear();
	}
	
	public void inputMap(Integer M, Scanner sc){
		for(int i=0; i<M; i++){
			String temp = sc.nextLine();
			String[] tempArr = temp.split(" ");
			LinkedList<String> tempLinked = new LinkedList<String>();
			Collections.addAll(tempLinked, tempArr);
			map.add(tempLinked);
		}
	}
	
	public Integer[] findXY(){
		Integer[] xy = new Integer[2];
		for(int i=0; i<map.size(); i++){
			int flag = 0;
			for(int j=0; j<map.get(i).size(); j++){
				if(map.get(i).get(j).equals("1")){					
					xy[0]=i;
					xy[1]=j;
					flag=1;
					break;
				}
			}
			if(flag==1) break;
		}
		return xy;
	}
	
	public String getMapElement(Integer x, Integer y){
		return map.get(y).get(x);
	}
	
	public void setXY(Integer length, LinkedList<Integer[]> positionHistory){		
		for(int i=positionHistory.size()-1; i>=0; i--){			
			if(i==positionHistory.size()-1){
				map.get(positionHistory.get(i)[1]).set(positionHistory.get(i)[0], "1");
			}else if(i>=positionHistory.size()-1-length){
				map.get(positionHistory.get(i)[1]).set(positionHistory.get(i)[0], "2");
			}else{
				map.get(positionHistory.get(i)[1]).set(positionHistory.get(i)[0], "0");
			}			
		}
		
	}
	
	public void showMap(){
		for(int y=0; y<map.size(); y++){
			for(int x=0; x<map.get(y).size(); x++){
				System.out.print(map.get(y).get(x));
			}
			System.out.println("");
		}
	}
}

class Jine{
	private Integer head;
	private Integer length;
	private Integer X,Y;
	private Integer life;
	private LinkedList<LinkedList<String>> orders;
	private LinkedList<Integer[]> positionHistory;
	private boolean game;
	private Integer second;
	
	public Jine(Map map, LinkedList<LinkedList<String>> orders){
		this.second=0;
		this.game=true;
		this.head = 2;
		this.length = 0;
		this.life = 0;
		this.setXY(map);
		this.orders = orders;
		positionHistory = new LinkedList<Integer[]>();
	}
	
	public void setXY(Map map){
		Integer[] xy = map.findXY();
		X = xy[0];
		Y = xy[1];
	}
	
	public void decreaseLife(){
		if(this.life==0){
			this.life=0;
		}else if(this.life>0){
			this.life--;
		}
	}
	
	public void setLength(){
		if(this.life>0){
			this.length++;
		}else if(this.life==0 && this.length>0){
			this.length--;
		}
		
	}
	
	public void setHead(Integer direction){
		switch(head){
		case 1:
			if(direction==1) head=4;
			else if(direction==2) head=2;
			break;
		case 2:
			if(direction==1) head=1;
			else if(direction==2) head=3;
			break;
		case 3:
			if(direction==1) head=2;
			else if(direction==2) head=4;
			break;
		case 4:
			if(direction==1) head=3;
			else if(direction==2) head=1;
			break;
		}
	}
	
	public Integer move(Map map){
		//1. 방향을 정한다.
		//2. 초만큼 반복하여 한칸씩 움직인다
		setPositionHistory(X,Y);
		for(int i=0; i<orders.size(); i++){
			int flag=0;
			setHead(Integer.parseInt(orders.get(i).get(0)));
			for(int j=0; j<Integer.parseInt(orders.get(i).get(1)); j++){
				switch(head){
				case 1:
					Y=Y-1;													
					break;
				case 2:
					X=X+1;						
					break;
				case 3:
					Y=Y+1;					
					break;
				case 4:
					X=X-1;					
					break;
				}
				setPositionHistory(X,Y);
				colSpider(X,Y,map);
				map.setXY(this.length, positionHistory);
				decreaseLife();
				setLength();
				if(!this.game){
					flag=1;
					break;
				}
				this.second++;
				map.showMap();
				System.out.println("");
				System.out.println(this.life);
				System.out.println(this.length);
			}
			if(flag==1) break;
		}
		return this.second;
	}
	
	public void colSpider(Integer x, Integer y, Map map){
		if(map.getMapElement(x, y).equals("3")){
			System.out.println("spider");
			this.life+=3;
		}else if(map.getMapElement(x, y).equals("9") || map.getMapElement(x, y).equals("2")){
			this.game=false;
		}
	}
	
	public void setPositionHistory(Integer X, Integer Y){
		Integer[] xy= new Integer[2];
		xy[0]=X;
		xy[1]=Y;
		this.positionHistory.add(xy);
	}
}
