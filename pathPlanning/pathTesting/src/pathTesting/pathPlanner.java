package pathTesting;
import java.util.*;


public class pathPlanner {

	public static void main(String[] args) {
		
		System.out.println("Hello World!");
	}
	
	private static int INFINITY = Integer.MAX_VALUE;
	
	private static class Index{
		int currentVertex;
		Set<Double> vertexSet;
		
		@Override
		public boolean equals(Object object){
			if(this == object){return true;}
			if(object == null || getClass() != object.getClass()){return false;}
			Index index = (Index) object;
			if(currentVertex != index.currentVertex){return false;}
			return !(vertexSet != null ? !vertexSet.equals(index.vertexSet) : index.vertexSet != null);	
		}
		
		@Override
		public int hashCode(){
			int result = currentVertex;
			result = 31 * result + (vertexSet != null ? vertexSet.hashCode() : 0);//recursive until vertexSet is null, at which point we return result
			return result;
		}
		
		private static Index createIndex(int vertex, Set<Double> vertexSet){
			Index i = new Index();
			i.currentVertex = vertex;
			i.vertexSet = vertexSet;
			return i;
		}

	}

	private static class SetSizeComparator implements Comparator<Set<Double>>{
		
		@Override
		public int compare(Set<Double> object1, Set<Double> object2){
			return object1.size() - object2.size();
		}
	}
	
	public double minCost(double[][] distance){
		//key --> Index, value --> integer (which is a distance)
		Map<Index, Double> minVertexCost = new HashMap<>();
		Map<Index, Double> parent = new HashMap<>();
		
		
		
		
		
		
		
		return 0;
		
	}
	

}
