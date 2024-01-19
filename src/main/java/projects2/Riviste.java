package projects2;

import java.time.LocalDate;

public class Riviste extends ElementiBiblioteca{
    private Periodicità periodicità;

    public Riviste(String titolo, LocalDate annoPubblicazione, int nPagine, Periodicità periodicità) {
        super(titolo, annoPubblicazione, nPagine);
        this.periodicità = periodicità;
    }

    public Riviste(long ISBN, String titolo, LocalDate annoPubblicazione, int nPagine, Periodicità periodicità) {
        super(ISBN, titolo, annoPubblicazione, nPagine);
        this.periodicità = periodicità;
    }

    @Override
    public String toString() {
        return "Riviste{" + super.toString() +
                "periodicità=" + periodicità +
                '}';
    }

    public Periodicità getPeriodicità() {
        return periodicità;
    }

    }
