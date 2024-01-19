package projects2;

import java.time.LocalDate;

public abstract class ElementiBiblioteca {
    private long ISBN;
    private String titolo;
    private LocalDate annoPubblicazione;
    private int nPagine;

    public ElementiBiblioteca(String titolo, LocalDate annoPubblicazione, int nPagine) {
        this.titolo = titolo;
        this.annoPubblicazione = annoPubblicazione;
        this.nPagine = nPagine;
    }

    public ElementiBiblioteca(long ISBN, String titolo, LocalDate annoPubblicazione, int nPagine) {
        this.ISBN = ISBN;
        this.titolo = titolo;
        this.annoPubblicazione = annoPubblicazione;
        this.nPagine = nPagine;
    }

    public long getISBN() {
        return ISBN;
    }

    public String getTitolo() {
        return titolo;
    }

    public LocalDate getAnnoPubblicazione() {
        return annoPubblicazione;
    }

    public int getnPagine() {
        return nPagine;
    }

    @Override
    public String toString() {
        return "ElementiBiblioteca{" +
                "ISBN=" + ISBN +
                ", titolo='" + titolo + '\'' +
                ", annoPubblicazione=" + annoPubblicazione +
                ", nPagine=" + nPagine +
                '}';
    }
}
