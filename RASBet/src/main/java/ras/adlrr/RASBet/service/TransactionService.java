package ras.adlrr.RASBet.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ras.adlrr.RASBet.dao.TransactionDAO;
import ras.adlrr.RASBet.dao.TransactionRepository;
import ras.adlrr.RASBet.model.Transaction;
import ras.adlrr.RASBet.model.User;

import java.util.List;

@Service
public class TransactionService {
    /*private final TransactionDAO transactionDAO;

    @Autowired
    public TransactionService(@Qualifier("TransactionDAO") TransactionDAO t){
        this.transactionDAO = t;
    }

    public Transaction getTransaction(int id) {
        return transactionDAO.getTransaction(id);
    }

    public int addTransaction(Transaction t) {
        return transactionDAO.addTransaction(t);
    }

    public List<Transaction> getUserTransactions(int userID) {
        return transactionDAO.getUserTransactions(userID);
    }

    */

    private final TransactionRepository tr;

    @Autowired
    public TransactionService (TransactionRepository tr){
        this.tr = tr;
    }

    public Transaction getTransaction(int id) {
        return tr.findById(id).orElse(null);
    }

    public int addTransaction(Transaction t) {
        tr.save(t);
        return 1;
    }

    public List<Transaction> getUserTransactions(int userID) {
        //return tr.findBy(User.class, userID);
        return null;
    }
}