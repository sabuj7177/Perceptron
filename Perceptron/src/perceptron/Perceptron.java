/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package perceptron;

import Jama.Matrix;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Sabuj
 */
public class Perceptron {
    
    public static void Basic() throws FileNotFoundException, IOException{
        // Input Data File
        int numData;
        BufferedReader in = null;
        in = new BufferedReader(new FileReader("Train2.txt"));
        String temp="";
        StringTokenizer st = null;
        //StringTokenizer st1 = null;
        temp = in.readLine();
        st = new StringTokenizer(temp);
        int featureNum = Integer.valueOf(st.nextToken());
        int classNum = Integer.valueOf(st.nextToken());
        numData = Integer.valueOf(st.nextToken());
        double[][] features = new double[numData][featureNum];
        int[] classara = new int[numData];
        for(int i = 0 ; i<numData;i++)
        {
            temp = in.readLine();
            st = new StringTokenizer(temp); 
            for(int j=0;j<featureNum;j++){
                features[i][j] = Double.parseDouble(st.nextToken());
            }
            classara[i] = Integer.valueOf(st.nextToken());
        }
        
        //Input Data File Complete
        
        double decision_rate = 0.7;
        double[] weight_vector = new double[featureNum];
        double bias = 0.0;
        for(int i=0;i<featureNum;i++){
            weight_vector[i] = 0.0;
        }
        
        double sum = 0.0;
        int Predict_class = 0;
        
        int errorflag = 0;
        
        while(errorflag==0){
            errorflag = 1;
            for(int instance = 0;instance<numData;instance++)
            {
                sum = 0.0;
                for(int i=0;i<featureNum;i++){
                    sum += features[instance][i]*weight_vector[i];
                }
                sum += bias;
                int multiply;
                
                if(sum<=0){
                    Predict_class = 0;
                    multiply = -1;
                }
                else{
                    Predict_class = 1;
                    multiply = 1;
                }
                
                System.out.println();
                int new_class = classara[instance]-1;
                if(Predict_class!=new_class){
                    for(int i=0;i<featureNum;i++){
                        weight_vector[i] += (new_class-Predict_class)*decision_rate*features[instance][i];
                    }

                    bias += (new_class-Predict_class)*decision_rate;
                    errorflag = 0;
                }

                System.out.println("Instance Num "+instance);
                System.out.println("Actual class "+new_class);
                System.out.println("Predicted class "+Predict_class);

                for(int j=0;j<featureNum;j++){
                    System.out.print(weight_vector[j]+" ");
                }
                System.out.println(bias);
            }
        }
        
        //Test Dataset
        
        
        BufferedReader in2 = null;
        in2 = new BufferedReader(new FileReader("Test2.txt"));
        double[][] testfeatures = new double[numData][featureNum];
        int[] testclassara = new int[numData];
        for(int i = 0 ; i<numData;i++)
        {
            temp = in2.readLine();
            st = new StringTokenizer(temp); 
            for(int j=0;j<featureNum;j++){
                testfeatures[i][j] = Double.parseDouble(st.nextToken());
            }
            testclassara[i] = Integer.valueOf(st.nextToken());
        }
        
        int error_num = 0;
        
        for(int instance = 0;instance<numData;instance++)
            {
                sum = 0.0;
                for(int i=0;i<featureNum;i++){
                    sum += testfeatures[instance][i]*weight_vector[i];
                }
                    sum += bias;

                if(sum<=0){
                    Predict_class = 0;
                }
                else{
                    Predict_class = 1;
                }

                int new_class = testclassara[instance]-1;
                if(Predict_class!=new_class){
                    error_num++;
                    System.out.println("###################################################");
                }

                System.out.println("Instance Num "+instance);
                System.out.println("Actual class "+new_class);
                System.out.println("Predicted class "+Predict_class);
            }
        
        System.out.println("Total error "+error_num);
    }
    
    public static void RewardPunishment() throws FileNotFoundException, IOException{
        // Input Data File
        int numData;
        BufferedReader in = null;
        in = new BufferedReader(new FileReader("Train2.txt"));
        String temp="";
        StringTokenizer st = null;
        //StringTokenizer st1 = null;
        temp = in.readLine();
        st = new StringTokenizer(temp);
        int featureNum = Integer.valueOf(st.nextToken());
        int classNum = Integer.valueOf(st.nextToken());
        numData = Integer.valueOf(st.nextToken());
        double[][] features = new double[numData][featureNum];
        int[] classara = new int[numData];
        for(int i = 0 ; i<numData;i++)
        {
            temp = in.readLine();
            st = new StringTokenizer(temp); 
            for(int j=0;j<featureNum;j++){
                features[i][j] = Double.parseDouble(st.nextToken());
            }
            classara[i] = Integer.valueOf(st.nextToken());
        }
        
        //Input Data File Complete
        
        int threshold = 0;
        double decision_rate = 0.7;
        double[][] weight_vectors = new double[classNum][featureNum];
        double[] biases = new double[classNum];
        for(int i=0;i<classNum;i++){
            for(int j=0;j<featureNum;j++){
                weight_vectors[i][j] = 0.0;
            }
            biases[i] = 0.0;
        }
        
        double[] sums = new double[classNum];
        int Predict_class = 0;
        
        int errorflag = 0;
        int iteration_count = 0;
        
        while(errorflag==0){
            errorflag = 1;
            iteration_count++;
            for(int instance = 0;instance<numData;instance++)
            {
                for(int cl = 0;cl<classNum;cl++){
                    sums[cl] = 0.0;
                    for(int i=0;i<featureNum;i++){
                        sums[cl] += features[instance][i]*weight_vectors[cl][i];
                    }
                    sums[cl] += biases[cl];
                }

                double MaxSum = sums[0];
                for(int cl=1;cl<classNum;cl++){
                    MaxSum = Math.max(MaxSum, sums[cl]);
                }

                for(int cl=0;cl<classNum;cl++){
                    if(MaxSum==sums[cl]){
                        Predict_class = cl;
                        break;
                    }
                }

                int new_class = classara[instance]-1;
                if(Predict_class!=new_class){
                    for(int i=0;i<featureNum;i++){
                        weight_vectors[Predict_class][i] -= decision_rate*features[instance][i];
                        weight_vectors[new_class][i] += decision_rate*features[instance][i];
                    }

                    biases[Predict_class] -= decision_rate;
                    biases[new_class] += decision_rate;
                    errorflag = 0;
                }
            }
        }
        
        
        //System.out.println(iteration_count);
        
        //Test data
        
        BufferedReader in2 = null;
        in2 = new BufferedReader(new FileReader("Test2.txt"));
        double[][] testfeatures = new double[numData][featureNum];
        int[] testclassara = new int[numData];
        for(int i = 0 ; i<numData;i++)
        {
            temp = in2.readLine();
            st = new StringTokenizer(temp); 
            for(int j=0;j<featureNum;j++){
                testfeatures[i][j] = Double.parseDouble(st.nextToken());
            }
            testclassara[i] = Integer.valueOf(st.nextToken());
        }
        
        int error_num = 0;
        
        for(int instance = 0;instance<numData;instance++)
            {
                for(int cl = 0;cl<classNum;cl++){
                    sums[cl] = 0.0;
                    for(int i=0;i<featureNum;i++){
                        sums[cl] += testfeatures[instance][i]*weight_vectors[cl][i];
                    }
                    sums[cl] += biases[cl];
                }

                double MaxSum = sums[0];
                for(int cl=1;cl<classNum;cl++){
                    MaxSum = Math.max(MaxSum, sums[cl]);
                }

                for(int cl=0;cl<classNum;cl++){
                    if(MaxSum==sums[cl]){
                        Predict_class = cl;
                        break;
                    }
                }

                int new_class = testclassara[instance]-1;
                if(Predict_class!=new_class){
                    error_num++;
                    System.out.println("###################################################");
                }

                System.out.println("Instance Num "+instance);
                System.out.println("Max Sum "+MaxSum);
                System.out.println("Actual class "+new_class);
                System.out.println("Predicted class "+Predict_class);
            }
        
        System.out.println("Total error "+error_num);
    }
    
    public static void Pocket(int iteration) throws FileNotFoundException, IOException{
        // Input Data File
        int numData;
        BufferedReader in = null;
        in = new BufferedReader(new FileReader("Train2.txt"));
        String temp="";
        StringTokenizer st = null;
        //StringTokenizer st1 = null;
        temp = in.readLine();
        st = new StringTokenizer(temp);
        int featureNum = Integer.valueOf(st.nextToken());
        int classNum = Integer.valueOf(st.nextToken());
        numData = Integer.valueOf(st.nextToken());
        double[][] features = new double[numData][featureNum];
        int[] classara = new int[numData];
        for(int i = 0 ; i<numData;i++)
        {
            temp = in.readLine();
            st = new StringTokenizer(temp); 
            for(int j=0;j<featureNum;j++){
                features[i][j] = Double.parseDouble(st.nextToken());
            }
            classara[i] = Integer.valueOf(st.nextToken());
        }
        
        //Input Data File Complete
        
        int threshold = 0;
        double decision_rate = 0.7;
        double[][] weight_vectors = new double[classNum][featureNum];
        double[] biases = new double[classNum];
        for(int i=0;i<classNum;i++){
            for(int j=0;j<featureNum;j++){
                weight_vectors[i][j] = 0.0;
            }
            biases[i] = 0.0;
        }
        
        double[] sums = new double[classNum];
        int Predict_class = 0;
        
        double[][] final_weight_vector = new double[classNum][featureNum];
        double[] final_biases = new double[classNum];
        int final_correct = -1;
        
        for(int it = 0;it<iteration;it++){
            int total_correct = 0;
            for(int instance = 0;instance<numData;instance++)
            {
                for(int cl = 0;cl<classNum;cl++){
                    sums[cl] = 0.0;
                    for(int i=0;i<featureNum;i++){
                        sums[cl] += features[instance][i]*weight_vectors[cl][i];
                    }
                    sums[cl] += biases[cl];
                }

                double MaxSum = sums[0];
                for(int cl=1;cl<classNum;cl++){
                    MaxSum = Math.max(MaxSum, sums[cl]);
                }

                for(int cl=0;cl<classNum;cl++){
                    if(MaxSum==sums[cl]){
                        Predict_class = cl;
                        break;
                    }
                }

                int new_class = classara[instance]-1;
                if(Predict_class!=new_class){
                    for(int i=0;i<featureNum;i++){
                        weight_vectors[Predict_class][i] -= decision_rate*features[instance][i];
                        weight_vectors[new_class][i] += decision_rate*features[instance][i];
                    }

                    biases[Predict_class] -= decision_rate;
                    biases[new_class] += decision_rate;
                }
                else{
                    total_correct++;
                }
            }
            
            if(total_correct>final_correct){
                final_weight_vector = weight_vectors;
                final_correct = total_correct;
                final_biases = biases;
            }
        }
        
        //Test data
        
        BufferedReader in2 = null;
        in2 = new BufferedReader(new FileReader("Test2.txt"));
        double[][] testfeatures = new double[numData][featureNum];
        int[] testclassara = new int[numData];
        for(int i = 0 ; i<numData;i++)
        {
            temp = in2.readLine();
            st = new StringTokenizer(temp); 
            for(int j=0;j<featureNum;j++){
                testfeatures[i][j] = Double.parseDouble(st.nextToken());
            }
            testclassara[i] = Integer.valueOf(st.nextToken());
        }
        
        int error_num = 0;
        
        for(int instance = 0;instance<numData;instance++)
            {
                for(int cl = 0;cl<classNum;cl++){
                    sums[cl] = 0.0;
                    for(int i=0;i<featureNum;i++){
                        sums[cl] += testfeatures[instance][i]*final_weight_vector[cl][i];
                    }
                    sums[cl] += final_biases[cl];
                }

                double MaxSum = sums[0];
                for(int cl=1;cl<classNum;cl++){
                    MaxSum = Math.max(MaxSum, sums[cl]);
                }

                for(int cl=0;cl<classNum;cl++){
                    if(MaxSum==sums[cl]){
                        Predict_class = cl;
                        break;
                    }
                }

                int new_class = testclassara[instance]-1;
                if(Predict_class!=new_class){
                    error_num++;
                    System.out.println("###################################################");
                }

                System.out.println("Instance Num "+instance);
                System.out.println("Max Sum "+MaxSum);
                System.out.println("Actual class "+new_class);
                System.out.println("Predicted class "+Predict_class);
            }
        
        System.out.println("Total error "+error_num);
    }
    
    public static void Keslar() throws FileNotFoundException, IOException{
        // Input Data File
        int numData;
        BufferedReader in = null;
        in = new BufferedReader(new FileReader("Train.txt"));
        String temp="";
        StringTokenizer st = null;
        //StringTokenizer st1 = null;
        temp = in.readLine();
        st = new StringTokenizer(temp);
        int featureNum = Integer.valueOf(st.nextToken());
        int classNum = Integer.valueOf(st.nextToken());
        numData = Integer.valueOf(st.nextToken());
        double[][] features = new double[numData][featureNum];
        int[] classara = new int[numData];
        for(int i = 0 ; i<numData;i++)
        {
            temp = in.readLine();
            st = new StringTokenizer(temp); 
            for(int j=0;j<featureNum;j++){
                features[i][j] = Double.parseDouble(st.nextToken());
            }
            classara[i] = Integer.valueOf(st.nextToken());
        }
        
        //Input Data File Complete
        
        double decision_rate = 0.7;
        int total_length = (featureNum+1)*classNum;
        double[][] weight_vectors = new double[total_length][1];
        double[][] X = new double[total_length][1];
        for(int i=0;i<total_length;i++){
            weight_vectors[i][0] = 0.0;
            X[i][0] = 0.0;
        }
        
        
        int errorflag = 0;
        int iteration_count = 0;
        
        Matrix weight_mat, Xmat, mul_mat;
        int multiplier = featureNum+1;
        
        while(errorflag==0){
            errorflag = 1;
            iteration_count++;
            System.out.println(iteration_count);
            for(int instance = 0;instance<numData;instance++)
            {
                int new_class = classara[instance]-1;
                for(int i=0;i<featureNum;i++){
                    X[new_class*multiplier+i][0] = features[instance][i];
                }
                X[new_class*multiplier+featureNum][0] = 1.0;
                for(int i=0;i<classNum;i++){
                    if(i==new_class){
                        continue;
                    }
                    for(int j=0;j<featureNum;j++){
                        X[i*multiplier+j][0] = -1.0*features[instance][j];
                    }
                    X[i*multiplier+featureNum][0] = -1.0;
                    weight_mat = new Matrix(weight_vectors);
                    Xmat = new Matrix(X);
                    mul_mat = (weight_mat.transpose()).times(Xmat);
                    double[][] mul_ara = mul_mat.getArray();
                    double d = mul_ara[0][0];
                    //System.out.p
                    if(d<=0){
                        for(int j=0;j<featureNum;j++){
                            weight_vectors[new_class*multiplier+j][0] += decision_rate*features[instance][j];
                            weight_vectors[i*multiplier+j][0] -= decision_rate*features[instance][j];
                        }

                        weight_vectors[new_class*multiplier+featureNum][0] += decision_rate;
                        weight_vectors[i*multiplier+featureNum][0] -= decision_rate;
                        errorflag = 0;
                    }
                    for(int j=0;j<featureNum;j++){
                        X[i*multiplier+j][0] = 0.0;
                    }
                    X[i*multiplier+featureNum][0] = 0.0;
                }
                for(int i=0;i<featureNum;i++){
                    X[new_class*multiplier+i][0] = 0.0;
                }
                X[new_class*multiplier+featureNum][0] = 0.0;
            }
        }
        
        
        
        //Test data
        
        BufferedReader in2 = null;
        in2 = new BufferedReader(new FileReader("Test.txt"));
        double[][] testfeatures = new double[numData][featureNum];
        int[] testclassara = new int[numData];
        for(int i = 0 ; i<numData;i++)
        {
            temp = in2.readLine();
            st = new StringTokenizer(temp); 
            for(int j=0;j<featureNum;j++){
                testfeatures[i][j] = Double.parseDouble(st.nextToken());
            }
            testclassara[i] = Integer.valueOf(st.nextToken());
        }
        
        int error_num = 0;
        double total_sum,sum;
        int Predict_class;
        
        for(int instance = 0;instance<numData;instance++)
            {
                total_sum = -1.0;
                Predict_class = -1;
                for(int i=0;i<classNum;i++){
                    sum = 0.0;
                    for(int j=0;j<featureNum;j++){
                        sum += testfeatures[instance][j]*weight_vectors[i*multiplier+j][0];
                    }
                    sum += weight_vectors[i*multiplier+featureNum][0];
                    if(sum>total_sum){
                        total_sum = sum;
                        Predict_class = i;
                    }
                }
                
                int actual_class = testclassara[instance]-1;
                if(Predict_class!=actual_class){
                    error_num++;
                    System.out.println("###################################################");
                }

                System.out.println("Instance Num "+instance);
                System.out.println("Actual class "+actual_class);
                System.out.println("Predicted class "+Predict_class);
            }
        
        System.out.println("Total error "+error_num);
        
    }
    
    public static void main(String[] args) {
        
        try {
            //RewardPunishment();
            //Basic();
            //Pocket(10);
            Keslar();
        } catch (IOException ex) {
            Logger.getLogger(Perceptron.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        

        
        
    }
    
}
