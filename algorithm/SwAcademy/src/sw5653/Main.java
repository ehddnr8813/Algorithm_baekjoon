package sw5653;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.StringTokenizer;
public class Main {
   static int N, M, K, T;
   static int dx[]= new int[] {1,-1,0,0};
   static int dy[]= new int[] {0,0,1,-1};
   static Queue<Node> alive;
   static Queue<Node> nextAlive;
   static Queue<Node> spread;
   static Queue<Node> nextSpread;
   static PriorityQueue<Node> selected;
   static boolean visited[][];
   public static void main(String[] args) throws IOException {
       BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
       StringTokenizer st= new StringTokenizer(br.readLine());
       T=Integer.parseInt(st.nextToken());
       for(int t=1; t<=T; t++) {
           alive= new LinkedList<Node>();
           nextAlive= new LinkedList<Node>();
           spread= new LinkedList<Node>();
           nextSpread= new LinkedList<Node>();
           selected= new PriorityQueue<Node>();
           st= new StringTokenizer(br.readLine());
           N=Integer.parseInt(st.nextToken());
           M=Integer.parseInt(st.nextToken());
           K=Integer.parseInt(st.nextToken());
           visited= new boolean[651][651];

           for(int i=0; i<N; i++) {
               st= new StringTokenizer(br.readLine());
               for(int j=0; j<M; j++) {
                   int num=Integer.parseInt(st.nextToken());
                   if(num!=0) {
                       nextAlive.add(new Node(300+j, 300+i, num, num, false));
                       visited[300+i][300+j]=true;
                   }
               }
           }
           while(K-->0) {
               go();
           }
           System.out.println("#"+t+" "+nextAlive.size());
       }
   }
   private static void go() {
       alive.addAll(nextAlive);
       spread.addAll(nextSpread);
       nextAlive.clear();
       nextSpread.clear();
       bfs();
       remove();
   }
   private static void remove() {
       while(!selected.isEmpty()) {
    	   Node n=selected.poll();
    	   if(visited[n.y][n.x])continue;
    	   visited[n.y][n.x]=true;
    	   nextAlive.add(new Node(n.x, n.y, n.count, n.count, n.spread));
       }
   }
   private static void bfs() {
      while(!alive.isEmpty()) {
    	  Node n= alive.poll();
    	  if(n.remain==0)continue;
    	  if(n.remain==1 && !n.spread) {
    		  nextSpread.add(new Node(n.x, n.y, n.count, n.remain, true));
    		  nextAlive.add(new Node(n.x, n.y, n.count, n.count-1, true));
    		  continue;
    	  }
    	  nextAlive.add(new Node(n.x, n.y, n.count, n.remain-1, n.spread));
      }
      while(!spread.isEmpty()) {
    	  Node n=spread.poll();
    	  for(int k=0; k<4; k++) {
    		  int nx=n.x+dx[k];
    		  int ny=n.y+dy[k];
    		  if(nx<0 || ny<0 || nx>=650 || ny>=650 || visited[ny][nx])continue;
    		  selected.add(new Node(nx, ny, n.count, n.count, false));
    	  }
      }
   }
   static class Node implements Comparable<Node>{
       int x, y;
       int count;
       int remain;
       boolean spread;
       public Node(int x, int y, int count, int remain, boolean spread) {
           super();
           this.x = x;
           this.y = y;
           this.count = count;
           this.remain=remain;
           this.spread=spread;
       }
       @Override
       public int compareTo(Node o) {

return -1*(count-o.count);
       }
   }
}