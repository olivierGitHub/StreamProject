package com.stream;

import com.stream.data.Data;
import com.stream.data.bean.Transaction;

import java.util.List;

/**
 * Created by olivier on 23/10/15.
 */
public class Program {

    public static void main (String[] args){

        ProgramQuestion question = new ProgramQuestion();

       //1. Trouver toutes les transactions de 2011 and les trier par valeur croissante
        System.out.println("Question 1:");
        question.question1(2011);
        System.out.println("");

        //2. Trouver toutes les villes où au moins un trader travaille
        System.out.println("Question 2:");
        question.question2();
        System.out.println("");

        //3. Trouver les traders de Cambridge et les trier par nom
        System.out.println("Question 3:");
        question.question3("Cambridge");
        System.out.println("");

        //4. Retourner une String contenant les noms des traders dans l’ordre alphabétique
        System.out.println("Question 4:");
        question.question4();
        System.out.println("");

        //5. Y-a-t’il au moins un trader à Milan ?
        System.out.println("Question 5:");
        System.out.println(question.question5("Milan"));
        System.out.println("");

        //6. Afficher les valeurs des transactions des traders de Cambridge
        System.out.println("Question 6:");
        question.question6("Cambridge");
        System.out.println("");

        //7. Quelle est la valeur de transaction la plus élevée ?
        System.out.println("Question 7:");
        question.question7();
        System.out.println("");

        //8. Trouver la transaction avec la valeur la plus faible
        System.out.println("Question 8:");
        question.question8();
        System.out.println("");
    }
}
