package SkillSwap.service;

import java.io.*;
import java.nio.file.*;

import SkillSwap.dati.*;
import SkillSwap.domain.*;

public class matching {
    
    private static void findOneWayMatches(String student_id){
        Path requestpath = Path.of("dati","");
        Path offerpath = Path.of("dati","offers.csv");
        try(BufferedReader br = new BufferedReader(Files.newBufferedReader(offerpath))){
            
            

        }catch(IOException e){
            System.out.println("Errore in IO");
        }        

    }

    private static void findSwapMatches(){

    }
}
