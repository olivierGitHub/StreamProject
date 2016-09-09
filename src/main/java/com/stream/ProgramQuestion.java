package com.stream;

import com.stream.data.Data;
import com.stream.data.bean.Trader;
import com.stream.data.bean.Transaction;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.maxBy;

/**
 * Created by olivier on 23/10/15.
 */
public class ProgramQuestion {

    public List<Trader> getTraders(){
        List<Transaction> listTransaction = Data.getTransactions();
        return listTransaction.stream()
                    .map(Transaction::getTrader)
                    .distinct()
                    .collect(Collectors.toList());
    }

    public Transaction useReduce(){
        // (Highlander) reduce va merger une stream en fonction d'une opération et renvoyer UN seul élément du au context associatif de l'opération
        // contrairement au map qui renvoit une stream avec le même nombre d'élément mais des éléments différents en fonction de la fonction prédicat
        return Data.getTransactions().stream()
                    .reduce((t1,t2) -> {
                        return t1.getValue() > t2.getValue() ? t1 : t2;
                    }).get();
    }

    //1. Trouver toutes les transactions de 2011 and les trier par valeur croissante
    public void question1(int year){
        List<Transaction> listTransaction = Data.getTransactions();
        listTransaction.stream()
                //.map(Transaction::getYear)
                .filter(transaction -> transaction.getYear() == year)// style déclaratif (par opposition au style impératif des boucles for)
                .sorted(comparing(Transaction::getValue))
                .forEach(System.out::println);
    }

    //2. Trouver toutes les villes où au moins un trader travaille
    public void question2(){
        List<Transaction> listTransaction = Data.getTransactions();
        listTransaction.stream()
                .map(Transaction::getTrader)
                .map(Trader::getCity)
                .distinct()
                .forEach(System.out::println);
    }

    //3. Trouver les traders de Cambridge et les trier par nom
    public void question3(String city){
        List<Transaction> listTransaction = Data.getTransactions();
        listTransaction.stream()
                .map(Transaction::getTrader)
                .filter(trader -> city.equals(trader.getCity()))
                .sorted(comparing(Trader::getName))
                .distinct()
                .forEach(System.out::println);
    }

    //4. Retourner une String contenant les noms des traders dans l’ordre alphabétique
    public void question4(){
        List<Transaction> listTransaction = Data.getTransactions();
        System.out.println(
                listTransaction.stream()
                        .map(Transaction::getTrader)
                        .sorted(comparing(Trader::getName))
                        .map(Trader::getName)
                                //reduce ((acc, name) -> acc + " " + name)
                        .collect(Collectors.joining(","))
        );
    }
    
    //5. Y-a-t’il au moins un trader à Milan ?
    public  boolean question5(String city){
        List<Transaction> listTransaction = Data.getTransactions();
        return   ! listTransaction.stream()
                        .map(Transaction::getTrader)
                        // return   ! listTransaction.stream().map(Transaction::getTrader).map(Trader::getCity).map(city -> {System.out.println(city);return city}).map(String::toLowerCase).anyMatch(city -> "milan".equals(city); // met en valeur le comportement LAZY des stream
                        // return   ! listTransaction.parallelStream().map(Transaction::getTrader).map(Trader::getCity).map(city -> {System.out.println(city);return city}).map(String::toLowerCase).anyMatch(city -> "milan".equals(city); // parallelStream fait du multiThreading
                        //.noneMatch(trader -> "milan".equals(trader.getCity()); // permet d'éviter la nulPointerException
                        .noneMatch(trader -> trader.getCity().equals(city));
    }

    //6. Afficher les valeurs des transactions des traders de Cambridge
    public void question6(String city){
        List<Transaction> listTransaction = Data.getTransactions();
        listTransaction.stream()
                .filter(transaction -> city.equals(transaction.getTrader().getCity()))
                .map(Transaction::getValue)
                .forEach(System.out::println);
    }

    //7. Quelle est la valeur de transaction la plus élevée ?
    public void question7(){
        List<Transaction> listTransaction = Data.getTransactions();
        System.out.println(
                listTransaction.stream()
                        .mapToInt(Transaction::getValue)
                        .max()
                        //.ifPresent(System.out::println)
                        .getAsInt()

                //.get() // le get() impose le retour
        );
    }
    
    //8. Trouver la transaction avec la valeur la plus faible
    public void question8(){
        List<Transaction> listTransaction = Data.getTransactions();
        System.out.println(
                listTransaction.stream()
                        .min(comparing(Transaction::getValue))
                        //.ifPresent(System.out::println)
                        .get() // le get() impose le retour
        );
    }

    //grouper les transactions par ville
    public void pourAllerPlusLoin(){
        Map<Trader,List<Transaction>> transactionsByTrader = Data.getTransactions().stream()
                    .collect(Collectors.groupingBy(Transaction::getTrader));
        /*Map<String,List<Transaction>> transactionsByCities = Data.getTransactions().stream()
                    .collect(Collectors.groupingBy(t -> t.getTrader().getCity()))*/
        getTraders().forEach(trader -> System.out.println(trader.getName() + " : " + transactionsByTrader.get(trader)));
    }

    // trouver la valeur de transaction maximal par vill
    public void pourAllerPlusLoin2(){
        Data.getTransactions().stream()
                    .collect(
                            Collectors.groupingBy(t -> t.getTrader().getCity(),
                                      Collectors.collectingAndThen(
                                              maxBy(comparing(Transaction::getValue)),
                                              Optional::get
                                      )
                            )
                       );
    }

    // trouver la moyenne par Trader
    /*public void pourAllerPlusLoin3(){
        Data.getTransactions().stream()
                .collect(
                        Collectors.groupingBy(t -> t.getTrader().getCity(),
                                Collectors.averagingInt(Transaction::getValue),t -> t.get().getYear())

                        );
    }*/

}
