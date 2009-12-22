package iweb2.ch4.clustering.rock;

import iweb2.ch4.model.Cluster;
import iweb2.ch4.model.DataPoint;
import iweb2.ch4.similarity.SimilarityMeasure;
import iweb2.ch4.utils.ObjectToIndexMapping;

import java.util.Arrays;
import java.util.Set;

/**
 * Calculates number of links between data points.
 */
public class LinkMatrix {

    private double th;
    double[][] pointSimilarityMatrix;
    int[][] pointNeighborMatrix;
    int[][] pointLinkMatrix;
    private ObjectToIndexMapping<DataPoint> objToIndexMapping;
    
    
    public LinkMatrix(DataPoint[] points, SimilarityMeasure pointSim, double th) {

        double[][] similarityMatrix = calculatePointSimilarities(points, pointSim);
        init(points, similarityMatrix, th);
    }
    
    public LinkMatrix(DataPoint[] points, double[][] similarityMatrix, double th) {
        init(points, similarityMatrix, th);
    }

    private void init(DataPoint[] points, double[][] similarityMatrix, double th) {
    
        this.th = th;
        
        objToIndexMapping = new ObjectToIndexMapping<DataPoint>();
        
        // Create DataPoint <-> Index mapping.
        for(DataPoint point : points) {
            objToIndexMapping.getIndex(point);
        }
        
        pointSimilarityMatrix = similarityMatrix;
        
        // Identify neighbors: a[i][j] == 1 if (i,j) are neighbors and 0 otherwise.
        int n = points.length;
        
        pointNeighborMatrix = new int[n][n];
        for(int i = 0; i < n; i++) {
            for(int j = i + 1; j < n; j++) {
                if( pointSimilarityMatrix[i][j] >= th ) {
                    pointNeighborMatrix[i][j] = 1;
                }
                else {
                    pointNeighborMatrix[i][j] = 0;
                }
                pointNeighborMatrix[j][i] = pointNeighborMatrix[i][j];
            }
            pointNeighborMatrix[i][i] = 1;
        }
        
        // Calculate number of links between points
        pointLinkMatrix = new int[n][n];
        for(int i = 0; i < n; i++) {
            for(int j = i; j < n; j++) {
                pointLinkMatrix[i][j] = 
                    nLinksBetweenPoints(pointNeighborMatrix, i, j);
                pointLinkMatrix[j][i] = pointLinkMatrix[i][j]; 
            }
        }
        
    }
    
    
    public int getLinks(DataPoint p1, DataPoint p2) {
        int i = objToIndexMapping.getIndex(p1);
        int j = objToIndexMapping.getIndex(p2);
        return pointLinkMatrix[i][j];
    }
    
    /**
     * Calculates number of links between two clusters. Number of links between 
     * two clusters is the sum of links between all point pairs( p1, p2) where   
     * p1 belongs to the first cluster and p2 belongs to the other cluster.
     * 
     * @param clusterX
     * @param clusterY
     * 
     * @return link count between two clusters.
     */
    public int getLinks(Cluster clusterX, Cluster clusterY) {
        Set<DataPoint> itemsX = clusterX.getElements();
        Set<DataPoint> itemsY = clusterY.getElements();
        
        int linkSum = 0;
        
        for(DataPoint x : itemsX) {
            for(DataPoint y : itemsY) {
                linkSum += getLinks(x, y);
            }
        }
        return linkSum;
    }
    
    private int nLinksBetweenPoints(int[][] neighbors, int indexX, int indexY) {
        int nLinks = 0;
        for(int i = 0, n = neighbors.length; i < n; i++) {
            nLinks += neighbors[indexX][i] * neighbors[i][indexY];
        }
        return nLinks;
    }
    
    /*
     * Calculates similarity matrix for all points.
     */
    private double[][] calculatePointSimilarities(
            DataPoint[] points, SimilarityMeasure pointSim) {
        
        int n = points.length;
        double[][] simMatrix = new double[n][n];
        for(int i = 0; i < n; i++) {
            DataPoint itemX = points[i];
            String[] attributesX = itemX.getTextAttrValues();
            for(int j = i + 1; j < n; j++) {
                DataPoint itemY = points[j];
                String[] attributesY = itemY.getTextAttrValues();
                simMatrix[i][j] = pointSim.similarity(
                        attributesX, attributesY);
                simMatrix[j][i] = simMatrix[i][j];
            }
            simMatrix[i][i] = 1.0;
        }

        return simMatrix;
    }
 
    public void printSimilarityMatrix() {
      System.out.println("Point Similarity matrix:");
      for(int i = 0; i < pointSimilarityMatrix.length; i++) {
          System.out.println(Arrays.toString(pointSimilarityMatrix[i]));
      }
    }

    public void printPointNeighborMatrix() {
        System.out.println("Point Neighbor matrix (th=" + String.valueOf(th) + "):");
        for(int i = 0; i < pointNeighborMatrix.length; i++) {
            System.out.println(Arrays.toString(pointNeighborMatrix[i]));
        }
     }

    public void printPointLinkMatrix() {
        System.out.println("Point Link matrix (th=" + String.valueOf(th) + "):");
        for(int i = 0; i < pointLinkMatrix.length; i++) {
            System.out.println(Arrays.toString(pointLinkMatrix[i]));
        }
     }
    
    
}
