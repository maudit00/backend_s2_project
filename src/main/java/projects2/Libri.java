package projects2;

import java.time.LocalDate;

public class Libri extends ElementiBiblioteca{
    private String autore;
    private String  genere;

    public Libri(String titolo, LocalDate annoPubblicazione, int nPagine, String autore, String genere) {
        super(titolo, annoPubblicazione, nPagine);
        this.autore = autore;
        this.genere = genere;
    }

    public Libri(long ISBN, String titolo, LocalDate annoPubblicazione, int nPagine, String autore, String genere) {
        super(ISBN, titolo, annoPubblicazione, nPagine);
        this.autore = autore;
        this.genere = genere;
    }

    public String getAutore() {
        return autore;
    }

    public String getGenere() {
        return genere;
    }

    @Override
    public String toString() {
        return "Libri{" + super.toString() +
                "autore='" + autore + '\'' +
                ", genere='" + genere + '\'' +
                '}';
    }
}
