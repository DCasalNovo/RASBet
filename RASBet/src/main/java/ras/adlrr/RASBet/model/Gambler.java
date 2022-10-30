package ras.adlrr.RASBet.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

@Entity
public class Gambler extends User{
    @Column(nullable = false)
    private String CC; // cartão de cidadão;

    @Column(nullable = false)
    private String nationality;

    @Column(nullable = false)
    private int NIF;

    @Column(nullable = false)
    private LocalDate date_of_birth;

    @Column(nullable = false)
    private String postal_code;

    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private String occupation;

    @Column(nullable = false)
    private int phoneNumber;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "gambler")
    private List<Wallet> wallets;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "gambler")
    private List<Transaction> transactions;

    public Gambler(){}

    @JsonCreator
    public Gambler(@JsonProperty("id") int ID, @JsonProperty("name") String name, @JsonProperty("password") String password, @JsonProperty("cc") String CC, @JsonProperty("nationality") String nationality, @JsonProperty("nif") int NIF, @JsonProperty("occupation") String occupation, @JsonProperty("phone_numer") int phoneNumber,
                   @JsonProperty("date_of_birth") LocalDate date_of_birth, @JsonProperty("email") String email, @JsonProperty("postal_code") String postal_code, @JsonProperty("address") String address, @JsonProperty("city") String city){
        super(ID, name, password,email);
        this.CC = CC;
        this.nationality = nationality;
        this.NIF = NIF;
        this.date_of_birth = date_of_birth;
        this.postal_code = postal_code;
        this.address = address;
        this.occupation = occupation;
        this.phoneNumber = phoneNumber;
        this.wallets = new ArrayList<>();
        this.city = city;
    }

    public Gambler(@JsonProperty("id") int ID, @JsonProperty("name") String name, @JsonProperty("password") String password, @JsonProperty("cc") String CC, @JsonProperty("nationality") String nationality, @JsonProperty("nif") int NIF, @JsonProperty("occupation") String occupation, @JsonProperty("phone_numer") int phoneNumber,
                   @JsonProperty("date_of_birth") LocalDate date_of_birth, @JsonProperty("email") String email, @JsonProperty("postal_code") String postal_code, @JsonProperty("address") String address, @JsonProperty("wallets") List<Wallet> wallets, @JsonProperty("transactions") List<Transaction> transactions, @JsonProperty("city") String city){
        super(ID, name, password,email);
        this.CC = CC;
        this.nationality = nationality;
        this.NIF = NIF;
        this.date_of_birth = date_of_birth;
        this.postal_code = postal_code;
        this.address = address;
        this.occupation = occupation;
        this.phoneNumber = phoneNumber;
        this.wallets = Objects.requireNonNullElseGet(wallets, ArrayList::new);
        this.city = city;
    }

    public Gambler(int nif, String city, String cc, String nationality, String ocupation, int phoneNumber, LocalDate date_of_birth, String postal_code, String address) {
    }

    public void setCC(String cC) {
        this.CC = cC;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public void setNIF(int nIF) {
        this.NIF = nIF;
    }

    public void setDate_of_birth(LocalDate date_of_birth) {
        this.date_of_birth = date_of_birth;
    }

    public void setPostal_code(String postal_code) {
        this.postal_code = postal_code;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public void setPhoneNumber(int phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setWallets(List<Wallet> wallets) {
        this.wallets = wallets;
    }

    public String getCC() {
        return this.CC;
    }

    public String getNationality() {
        return this.nationality;
    }

    public int getNIF() {
        return this.NIF;
    }

    public LocalDate getDate_of_birth() {
        return this.date_of_birth;
    }

    public String getPostal_code() {
        return this.postal_code;
    }

    public String getCity() {
        return this.city;
    }

    public String getAddress() {
        return this.address;
    }

    public List<Wallet> getWallets() {
        return wallets;
    }

    public Gambler clone(){
        return new Gambler(this.getID(), this.getName(), this.getPassword(), CC, nationality, NIF, occupation, phoneNumber, date_of_birth, this.getEmail(), postal_code, address, wallets, transactions,city) ;
    }

    public void addTransaction(Transaction t){
        if(transactions == null) transactions = new ArrayList<>();
        transactions.add(t);
    }

    public void addWallet(Wallet w){
        if(wallets == null) wallets = new ArrayList<>();
        wallets.add(w);
    }
}
